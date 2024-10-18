import java.util.*;

class Program2{
    public record Result(int numPlatforms, int totalHeight, int[] numStatues) {}
   
    /**
    * Solution to program 2
    * @param n number of statues
    * @param w width of the platform
    * @param heights array of heights of the statues
    * @param widths array of widths of the statues
    * @return Result object containing the number of platforms, total height of the statues and the number of statues on each platform
    */
    public static Result program2(int n, int w, int[] heights, int[] widths) {
        /* Starting variables which are used to keep track of current platform's width, cost (height), max height on each platform and total platforms needed. */
        int currentWidth = 0, cost = 0, maxHeight = 0, platforms = 0;

        /* Map used to keep track of how many statues on each platform.
         *  Key: platform index, Value: number of statues */
        Map<Integer, Integer> platformStatueMap = new HashMap<>();
        int currentStatueCount = 0;

        /* Since the statues are in an unimodal structure, we have a boolean to know if the structures are decreasing in height or increasing */
        boolean isDecreasing = true;
        int previousHeight = heights[0];

        for (int i = 0; i < n; i++) {

            if (currentWidth + widths[i] > w) { /* Check if adding a new sculpture goes over the width limit */
                /* Creating a new platform:
                 * Put platform on the platformStatueMap with currentStatueCount as value
                 * Add maxHeight of the platform to the cost
                 * Increment the platforms by 1
                 */
                platformStatueMap.put(platforms, currentStatueCount);
                cost += maxHeight;
                platforms++;

                /* Reset variables to prepare for new platform */
                currentWidth = 0;
                maxHeight = 0;
                currentStatueCount = 0;
                isDecreasing = true;
            }


            if (currentStatueCount > 0 && isDecreasing && heights[i] > previousHeight) { /* Check if the heights of statues go from decreasing to increasing */
                /* Creating a new platform:
                 * Put platform on the platformStatueMap with currentStatueCount as value
                 * Add maxHeight of the platform to the cost
                 * Increment the platforms by 1
                 */
                platformStatueMap.put(platforms, currentStatueCount);
                cost += maxHeight;
                platforms++;

                /* Reset variables to prepare for new platform */
                currentWidth = 0;
                maxHeight = 0;
                currentStatueCount = 0;
                isDecreasing = false; /* Set isDecreasing to false */
            }

            /* Add current statue to the platform */
            currentWidth += widths[i];
            maxHeight = Math.max(maxHeight, heights[i]); /* We set the maxHeight of the platform to the max out of maxHeight or heights[i] */
            currentStatueCount++;

            /* Check current and previous heights to update isDecreasing */
            if (i > 0 && heights[i] > previousHeight) isDecreasing = false;

            previousHeight = heights[i];
        }

        /* Finally we add the last platform */
        if (currentStatueCount > 0) {
            platformStatueMap.put(platforms, currentStatueCount);
            cost += maxHeight;
            platforms++;
        }

        /* Use stream to create an array */
        int[] statuesCount = platformStatueMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .mapToInt(Map.Entry::getValue)
                .toArray();

        return new Result(platforms, cost, statuesCount);
    }




    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int W = sc.nextInt();
        int[] heights = new int[n];
        int[] widths = new int[n];
        for(int i=0; i<n; i++){
            heights[i] = sc.nextInt();
        }
        for(int i=0; i<n; i++){
            widths[i] = sc.nextInt();
        }
        sc.close();
        Result result = program2(n, W, heights, widths);
        System.out.println(result.numPlatforms);
        System.out.println(result.totalHeight);
        for(int i=0; i<result.numStatues.length; i++){
            System.out.println(result.numStatues[i]);
        }
    }
}