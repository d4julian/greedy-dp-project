import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

class Program1{
    public record Result(int numPlatforms, int totalHeight, int[] numStatues) {}

    /**
    * Solution to program 1
    * @param n number of statues
    * @param w width of the platform
    * @param heights array of heights of the statues
    * @param widths array of widths of the statues
    * @return Result object containing the number of platforms, total height of the statues and the number of statues on each platform
    */
    public static Result program1(int n, int w, int[] heights, int[] widths) {
        /* Starting variables which are used to keep track of current platform's width, cost (height), max height on each platform and total platforms needed. */
        int currentWidth = 0, cost = 0, maxHeight = 0, platforms = 0;

        /* Map used to keep track of how many statues on each platform.
        *  Key: platform index, Value: number of statues */
        Map<Integer, Integer> platformStatueMap = new HashMap<>();
        int currentStatueCount = 0;


        for (int i = 0; i < n; i++) { /* Iterate through all the statues */
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
                maxHeight = 0;
                currentWidth = 0;
                currentStatueCount = 0;
            }
            /* Add current statue to the platform */
            currentWidth += widths[i];
            maxHeight = Math.max(maxHeight, heights[i]); /* We set the maxHeight of the platform to the max out of maxHeight or heights[i] */
            currentStatueCount++;
        }

        /* Finally we add the last platform */
        platformStatueMap.put(platforms, currentStatueCount);
        cost += maxHeight;

        /* Use stream to create an array */
        int[] statuesSizeArray = platformStatueMap.values()
                .stream()
                .mapToInt(Integer::intValue)
                .toArray();

        return new Result(platforms + 1, cost, statuesSizeArray);
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
        Result result = program1(n, W, heights, widths);
        System.out.println(result.numPlatforms);
        System.out.println(result.totalHeight);
        for(int i=0; i<result.numStatues.length; i++){
            System.out.println(result.numStatues[i]);
        }
    }
}