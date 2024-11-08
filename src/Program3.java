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
        int[] backtrack = new int[n + 1];
        int[] dp = calculateOptimalTotalHeight(n, w, heights, widths, backtrack);
        return arrangePlatforms(n, dp, backtrack);
    }

    private static int[] calculateOptimalTotalHeight(int n, int w, int[] heights, int[] widths, int[] backtrack) {
        int[] dp = new int[n + 1]; /* Array to store minimum height cost for first i statues */
        dp[0] = 0; /* Base case: no statues has zero cost */

        for (int i = 1; i < n + 1; i++) {
            dp[i] = Integer.MAX_VALUE; /* Initialize with max value to find minimum cost */
            int totalWidth = 0, maxHeight = 0; /* Reset width and height for each platform ending at i */

            for (int j = i; j >= 1; j--) { /* Consider all possible platforms ending at statue i */
                totalWidth += widths[j - 1]; /* Accumulate width of statues from j to i */
                if (totalWidth > w) break; /* Stop if platform width exceeds limit */
                maxHeight = Math.max(maxHeight, heights[j - 1]); /* Track the tallest statue on current platform */
                int cost = dp[j - 1] + maxHeight; /* Calculate height cost if platform ends at i */
                if (cost < dp[i]) { /* If new cost is lower, update dp and record start index */
                    dp[i] = cost;
                    backtrack[i] = j - 1; /* Record starting index of platform ending at i */
                }
            }
        }

        return dp;
    }

    private static Result arrangePlatforms(int n, int[] dp, int[] backtrack) {
        List<Integer> platformCounts = new ArrayList<>(); /* Stores the number of sculptures on each platform */
        int index = n; /* Start from last sculpture and trace back */
        while (index > 0) {
            int startIndex = backtrack[index]; /* Get start index of platform */
            int scupltureCountOnPlatform = index - startIndex; /* Calculate number of sculptures on this platform */
            platformCounts.add(scupltureCountOnPlatform); /* Add count to list */
            index = startIndex; /* Move to the start of the previous platform */
        }
        Collections.reverse(platformCounts); /* Reverse to get platforms in order */

        int[] sculpturesPerPlatform = platformCounts.stream().mapToInt(Integer::intValue).toArray(); /* Convert list to array */

        return new Result(platformCounts.size(), dp[n], sculpturesPerPlatform); /* Return result with platforms, total height, and counts */
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