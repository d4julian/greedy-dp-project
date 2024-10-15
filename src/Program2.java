import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
        int leftPtr = 0, rightPtr = heights.length - 1;
        int currentWidth = 0, cost = 0, maxHeight = 0, platforms = 0;
        Map<Integer, Integer> platformStatueMap = new HashMap<>();
        int currentStatueCount = 0;

        while (leftPtr <= rightPtr) {
            if (heights[leftPtr] >= heights[rightPtr]) {
                if (currentWidth + widths[leftPtr] > w) { /* Platform is full, creating a new platform */
                    platformStatueMap.put(platforms, currentStatueCount);
                    platforms++;
                    cost += maxHeight;
                    maxHeight = 0;
                    currentWidth = 0;
                    currentStatueCount = 0;
                } else {
                    currentWidth += widths[leftPtr];
                    maxHeight = Math.max(heights[leftPtr], maxHeight);
                    leftPtr += 1;
                    currentStatueCount++;
                }
            } else /* if (heights[leftPtr] < heights[rightPtr]) */ {
                if (currentWidth + widths[rightPtr] > w) {
                    platformStatueMap.put(platforms, currentStatueCount);
                    platforms++;
                    cost += maxHeight;
                    maxHeight = 0;
                    currentWidth = 0;
                    currentStatueCount = 0;
                } else {
                    currentWidth += widths[rightPtr];
                    maxHeight = Math.max(heights[rightPtr], maxHeight);
                    rightPtr -= 1;
                    currentStatueCount++;
                }
            }
        }

        platformStatueMap.put(platforms, currentStatueCount);
        cost += maxHeight;

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
        Result result = program2(n, W, heights, widths);
        System.out.println(result.numPlatforms);
        System.out.println(result.totalHeight);
        for(int i=0; i<result.numStatues.length; i++){
            System.out.println(result.numStatues[i]);
        }
    }
}