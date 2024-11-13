import java.util.*;

class Program5A{
    public record Result(int numPlatforms, int totalHeight, int[] numStatues) {}
   
    /**
    * Solution to program 5A
    * @param n number of statues
    * @param w width of the platform
    * @param heights array of heights of the statues
    * @param widths array of widths of the statues
    * @return Result object containing the number of platforms, total height of the statues and the number of statues on each platform
    */
     private static Result program5A(int n, int w, int[] heights, int[] widths) {
        // Memoization array to store minimum height cost up to each sculpture position
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1); // Initialize with -1 to indicate undefined values

        // Memoization arrays to keep track of the number of statues per platform and previous platform endpoint
        int[] numStatues = new int[n + 1];
        int[] prevPlatformEnd = new int[n + 1];

        // Start the recursive process from the last sculpture (position "n")
        int totalHeight = minCost(n, w, heights, widths, memo, numStatues, prevPlatformEnd);

        // Backtrack to determine the number of platforms and distribution of sculptures
        List<Integer> platformsList = new ArrayList<>(); // To store statue counts per platform
        int currentIndex = n; // Start from the last sculpture

        // Trace back to determine the number of sculptures on each platform
        while (currentIndex > 0) {
            platformsList.add(numStatues[currentIndex]); // Add the statue count for the current platform
            currentIndex = prevPlatformEnd[currentIndex]; // Move to the next platform's end position
        }

        // Reverse the list to get platforms in order from first to last
        Collections.reverse(platformsList);

        // Convert list to array format for final output
        int[] statuesPerPlatform = platformsList.stream().mapToInt(Integer::intValue).toArray();
        int numPlatforms = platformsList.size(); // Number of platforms used

        // Return the result with the number of platforms, total height, and statues per platform
        return new Result(numPlatforms, totalHeight, statuesPerPlatform);
    }

    // Helper method for computing the minimum height cost recursively
    private static int minCost(int end, int w, int[] heights, int[] widths, int[] memo, int[] numStatues, int[] prevPlatformEnd) {
        // Base case: If no sculptures are placed, cost is zero
        if (end == 0) return 0;

        // If we have already computed the minimum cost for "end", return the memoized result
        if (memo[end] != -1) return memo[end];

        // Initialize variables to keep track of current platform properties
        int currentWidth = 0; // Width of the current platform
        int maxHeight = 0;    // Height of the tallest sculpture on the current platform
        int minimumCost = Integer.MAX_VALUE; // Initialize minimum cost to a large value

        // Try different starting positions "start" for the platform ending at "end"
        for (int start = end; start > 0; start--) {
            currentWidth = currentWidth + widths[start - 1]; // Add width of current sculpture to the platform width

            // If adding the current sculpture exceeds the platform width, exit the loop
            if (currentWidth > w) break;

            // Update maxHeight to be the tallest sculpture in the current platform
            maxHeight = Math.max(maxHeight, heights[start - 1]);

            // Calculate the cost if we place sculptures from "start" to "end" on this platform
            int cost = minCost(start - 1, w, heights, widths, memo, numStatues, prevPlatformEnd) + maxHeight;

            // Update minimum cost and relevant tracking if this arrangement is optimal
            if (cost < minimumCost) {
                minimumCost = cost;
                prevPlatformEnd[end] = start - 1; // Record the starting index for optimal platform
                numStatues[end] = end - start + 1; // Record the number of statues on this platform
            }
        }

        // Store the result in the memo array and return the minimum cost
        memo[end] = minimumCost;
        return minimumCost;
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
        Result result = program5A(n, W, heights, widths);
        System.out.println(result.numPlatforms);
        System.out.println(result.totalHeight);
        for(int i=0; i<result.numStatues.length; i++){
            System.out.println(result.numStatues[i]);
        }
    }
}
