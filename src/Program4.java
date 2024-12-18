import java.util.*;

class Program4{
    public record Result(int numPlatforms, int totalHeight, int[] numStatues) {}
   
    /**
    * Solution to program 4
    * @param n number of statues
    * @param w width of the platform
    * @param heights array of heights of the statues
    * @param widths array of widths of the statues
    * @return Result object containing the number of platforms, total height of the statues and the number of statues on each platform
    */
    public static Result program4(int n, int w, int[] heights, int[] widths) {
        // Initialize dp array and tracking arrays. dp is the minimum cost to arrange the first i sculptures on k platforms
        int[][] dp = new int[n + 1][n + 1];
        int[][] numStatues = new int[n + 1][n + 1]; //holds the number of statues on k-th platform after placing the first i statues
        int[][] prevPlatformEnd = new int[n + 1][n + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0][0] = 0; // Base case: no sculptures require zero cost

        // Fill the DP table by calculating the minimum height costs for different platform configurations
        calculateMinHeightCost(n, w, heights, widths, dp, numStatues, prevPlatformEnd);

        // Determine the minimum height cost and optimal number of platforms
        int optimalPlatforms = findOptimalPlatformCount(n, dp);
        int totalHeight = dp[n][optimalPlatforms];

        
        //Now reconstruct the list of statues distributes across platforms
        List<Integer> platformsList = new ArrayList<>();
        int currentIndex = n;
        int platformCount = optimalPlatforms;

        //perform backtracking through the dp array to determine how many statues were placede on each platform 
        while (currentIndex > 0 && platformCount > 0) {
            platformsList.add(numStatues[currentIndex][platformCount]);
            currentIndex = prevPlatformEnd[currentIndex][platformCount];
            platformCount--;
        }

        Collections.reverse(platformsList); //reverse because we processed the list from right to left
        
        /// Convert platforms list to an array format for the result
        int[] statuesPerPlatform = platformsList.stream().mapToInt(Integer::intValue).toArray();

        // Return the result with the optimal platform count, minimum height cost, and sculpture distribution
        return new Result(optimalPlatforms, totalHeight, statuesPerPlatform);
    }


    // Calculates the minimum height cost using DP with Θ(n³) complexity
    private static void calculateMinHeightCost(int n, int w, int[] heights, int[] widths, int[][] dp, int[][] numStatues, int[][] prevPlatformEnd) {
        //Iterate over all possible endpoints i and platform counts k
        for (int i = 1; i <= n; i++) {
            for (int k = 1; k <= i; k++) {
                //For each partition, compute the platform's cost (min height) and update the dp array
                computePlatformCost(i, k, w, heights, widths, dp, numStatues, prevPlatformEnd);
            }
        }
    }

    // Computes the minimum cost for placing sculptures from start to i on the platform
    private static void computePlatformCost(int i, int k, int w, int[] heights, int[] widths, int[][] dp, int[][] numStatues, int[][] prevPlatformEnd) {
        int currentWidth = 0;
        int maxHeight = 0;

        //Explore all valid groupings from statues i to some statues j, adding sculptures to the current platform
        for (int j = i; j > 0; j--) {
            currentWidth += widths[j - 1];

            // Stop if platform width exceeds the limit
            if (currentWidth > w) break;

            maxHeight = Math.max(maxHeight, heights[j - 1]);

            // Calculate and update dp[i][k] if this platform arrangement is optimal
            if (dp[j - 1][k - 1] != Integer.MAX_VALUE) {
                int cost = dp[j - 1][k - 1] + maxHeight;
               //a lower cost was found so now we want to update the dp array with this newer value
                if (cost < dp[i][k]) {
                    dp[i][k] = cost;
                    prevPlatformEnd[i][k] = j - 1;
                    numStatues[i][k] = i - j + 1;
                }
            }
        }
    }

    // Finds the optimal number of platforms by examining the dp array
    private static int findOptimalPlatformCount(int n, int[][] dp) {
        int totalHeight = Integer.MAX_VALUE;
        int optimalPlatforms = 0;

        //Iterate over the dp table to find the optimal number of platforms based on minimum cost
        for (int k = 1; k <= n; k++) {
            if (dp[n][k] < totalHeight) {
                totalHeight = dp[n][k];
                optimalPlatforms = k;
            }
        }
        return optimalPlatforms;
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
        Result result = program4(n, W, heights, widths);
        System.out.println(result.numPlatforms);
        System.out.println(result.totalHeight);
        for(int i=0; i<result.numStatues.length; i++){
            System.out.println(result.numStatues[i]);
        }
    }
}
