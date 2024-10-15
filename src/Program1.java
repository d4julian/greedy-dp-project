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
        int currentWidth = 0, cost = 0, maxHeight = 0, platforms = 0;
        Map<Integer, Integer> platformStatueMap = new HashMap<>();
        int currentStatueCount = 0;

        for (int i = 0; i < n; i++) {
            if (currentWidth + widths[i] > w) {
                platformStatueMap.put(platforms, currentStatueCount);
                platforms++;

                cost += maxHeight;

                maxHeight = 0;
                currentWidth = 0;
                currentStatueCount = 0;
            }
            currentWidth += widths[i];
            maxHeight = Math.max(maxHeight, heights[i]);
            currentStatueCount++;
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
        Result result = program1(n, W, heights, widths);
        System.out.println(result.numPlatforms);
        System.out.println(result.totalHeight);
        for(int i=0; i<result.numStatues.length; i++){
            System.out.println(result.numStatues[i]);
        }
    }
}