import java.util.Scanner;

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
    private static Result program4(int n, int w, int[] heights, int[] widths) {
        /************************
         * ADD YOUR CODE HERE *
         ***********************/

        // return new Result(0, 0, new int[0]);//replace with your own result
        return new Result(n, 0, heights);
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