import java.util.*;

class Program3{
    public record Result(int numPlatforms, int totalHeight, int[] numStatues) {}
   
    /**
    * Solution to program 3
    * @param n number of statues
    * @param w width of the platform
    * @param heights array of heights of the statues
    * @param widths array of widths of the statues
    * @return Result object containing the number of platforms, total height of the statues and the number of statues on each platform
    */
    private static Result program3(int n, int w, int[] heights, int[] widths) {
        /* Initialize the optimal cost to the maximum possible integer value and the list to store the number of sculptures to platform*/
        int optimalCost = Integer.MAX_VALUE;
        List<Integer> optimalPlatformSculptures = null;

        /*
         * Iterate through all possible partitions (2^(n-1) possible).
         * Partitions are the spaces in between the sculptures.
         */
        for (int partition = 0; partition < ((int) Math.pow(2, n - 1)); partition++) {
            /* Initialize variables for the current partition */
            int cost = 0, currentWidth = 0, maxHeight = 0, sculpturesOnPlatform = 0;
            List<Integer> platformSculptures = new ArrayList<>();
            boolean isValid = true;

            /* Process each sculpture to assign it to a platform */
            for (int i = 0; i < n; i++) {
                /*
                 * Check if adding the current sculpture exceeds the platform's width limit
                 * If it exceeds the platform's width limit, then set isValid to false and break the loop
                 */
                if (currentWidth + widths[i] > w) {
                    isValid = false;
                    break;
                }

                /* Add the current sculpture's width to the platform's total width */
                currentWidth += widths[i];
                /* Update the maximum height for the current platform */
                maxHeight = Math.max(maxHeight, heights[i]);
                /* Increment the count of sculptures on the current platform */
                sculpturesOnPlatform++;

                /*
                 * Check if a partition exists after the current sculpture.
                 * We can do this by checking if the i-th bit in 'partition' is set.
                 */
                if (i < n - 1 && (partition & (1 << i)) != 0) {
                    /* Add the maximum height of the current platform to the total cost */
                    cost += maxHeight;
                    /* Add the number of sculptures on the current platform to the list*/
                    platformSculptures.add(sculpturesOnPlatform);

                    /* Reset variables for the next platform */
                    currentWidth = 0;
                    maxHeight = 0;
                    sculpturesOnPlatform = 0;

                }
            }
            /* If the partition is invalid, then we continue the loop */
            if (!isValid) continue;
            /* After processing all sculptures, we can add the cost and sculpture count for the last platform. */
            cost += maxHeight;
            platformSculptures.add(sculpturesOnPlatform);
            /*
             * If the cost of the current partition is less than the optimalCost,
             * we can update the optimal cost and the optimalPlatformSculptures list
             */
            if (cost < optimalCost) {
                optimalCost = cost;
                optimalPlatformSculptures = new ArrayList<>(platformSculptures);
            }

        }

        /* Stream the values of the list to an array and return the result */
        return new Result(optimalPlatformSculptures.size(), optimalCost, optimalPlatformSculptures.stream().mapToInt(Integer::intValue).toArray());
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
        Result result = program3(n, W, heights, widths);
        System.out.println(result.numPlatforms);
        System.out.println(result.totalHeight);
        for(int i=0; i<result.numStatues.length; i++){
            System.out.println(result.numStatues[i]);
        }
    }
}