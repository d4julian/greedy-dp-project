import java.util.Scanner;

class Program5B{
    public record Result(int numPlatforms, int totalHeight, int[] numStatues) {}
   
    /**
    * Solution to program 5B
    * @param n number of statues
    * @param w width of the platform
    * @param heights array of heights of the statues
    * @param widths array of widths of the statues
    * @return Result object containing the number of platforms, total height of the statues and the number of statues on each platform
    */
    private static Result program5B(int n, int w, int[] heights, int[] widths) {
        // Initialize the DP array to store minimum height costs for arranging first 'i' sculptures
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE); // Set each element to a large value initially
        dp[0] = 0; // Base case: no sculptures require zero cost

        // Array to keep track of the number of statues on each platform
        int[] numStatues = new int[n + 1]; // Tracks how many sculptures each platform holds
        int[] prevPlatformEnd = new int[n + 1]; // Tracks the endpoint index of each platform

        // Outer loop: iterate over each sculpture s_i were it could be the potential endpoint of a platform
        for (int i = 1; i <= n; i++) {
            // Initialize variables to keep track of current platform properties
            int currentWidth = 0; // Width of the current platform
            int maxHeight = 0;    // Height of the tallest sculpture on the current platform

            // Inner loop: consider each sculpture starting at s_i and moving to the beginning of the list. Determine possible starting position for platform ending at sculpture 'i'
            for (int j = i; j > 0; j--) {
                // Accumulate the width of the platform if we include sculpture at position 'j-1'
                currentWidth += widths[j - 1];

                // Break if adding the current sculpture would exceed platform's max width
                if (currentWidth > w) {
                    break;
                }

                // Update maxHeight to ensure it represents the tallest sculpture in the platform
                maxHeight = Math.max(maxHeight, heights[j - 1]);

                // Calculate the cost of current platform arrangement and update dp[i] if it's better
                if (dp[j - 1] + maxHeight < dp[i]) {
                    dp[i] = dp[j - 1] + maxHeight; // Store the minimum height cost up to sculpture i
                    prevPlatformEnd[i] = j - 1;    // Record the starting index of this optimal platform
                    numStatues[i] = i - j + 1;     // Record the number of sculptures on this platform
                }
            }
        }

        // Backtrack to determine the number of platforms and distribution of sculptures
        List<Integer> platformsList = new ArrayList<>(); // List to store counts of sculptures per platform
        int currentIndex = n; // Start from the last sculpture

        // Trace back from last sculpture to determine where each platform ends
        while (currentIndex > 0) {
            platformsList.add(numStatues[currentIndex]); // Add count for current platform
            currentIndex = prevPlatformEnd[currentIndex]; // Move to the next platform's end position
        }

        // Reverse to get platforms in order from first to last
        Collections.reverse(platformsList); // Since we collected platforms from right to left in the list

        // Convert list to array for final output
        int[] statuesPerPlatform = platformsList.stream().mapToInt(Integer::intValue).toArray();
        int numPlatforms = platformsList.size(); // Total number of platforms used
        int totalHeight = dp[n]; // Total minimum height cost for the arrangement

        //return result
        return new Result(numPlatforms, totalHeight, statuesPerPlatform);

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
        Result result = program5B(n, W, heights, widths);
        System.out.println(result.numPlatforms);
        System.out.println(result.totalHeight);
        for(int i=0; i<result.numStatues.length; i++){
            System.out.println(result.numStatues[i]);
        }
    }
}
