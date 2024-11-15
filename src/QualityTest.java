import java.util.Random;

public class QualityTest {

    private static final Random random = new Random();

    public static int[][] generateTestData(int n, int maxWidth, int maxHeight) {
        int[] heights = new int[n];
        int[] widths = new int[n];

        for (int i = 0; i < n; i++) {
            heights[i] = random.nextInt(maxHeight) + 1;
            widths[i] = random.nextInt(maxWidth) + 1;
        }
        return new int[][]{heights, widths};
    }

    public static void testOutputQuality(int[] inputSizes, int maxWidth, int maxHeight) {
        System.out.println("Input Size\tMetric (hg - ho)/ho");

        for (int n : inputSizes) {
            int[][] testData = generateTestData(n, maxWidth, maxHeight);

            Program1.Result greedyResult = Program1.program1(n, maxWidth, testData[0], testData[1]);
            double hg = greedyResult.totalHeight();

            Program4.Result optimalResult = Program4.program4(n, maxWidth, testData[0], testData[1]);
            double ho = optimalResult.totalHeight();

            double metric = (hg - ho) / ho;

            System.out.println(n + "\t\t" + metric);
        }
    }

    public static void main(String[] args) {
        int[] inputSizes = {1000, 2500, 5000, 7500, 10000};
        int maxWidth = 100;
        int maxHeight = 100;

        testOutputQuality(inputSizes, maxWidth, maxHeight);
    }
}
