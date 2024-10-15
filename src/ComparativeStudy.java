import java.util.Random;

public class ComparativeStudy {

    private static final Random random = new Random();

    public static int[][] generateTestData(int n, int maxWidth, int maxHeight) {
        final int[] heights = new int[n];
        final int[] widths = new int[n];

        for (int i = 0; i < n; i++) {
            heights[i] = random.nextInt(maxHeight) + 1;
            widths[i] = random.nextInt(maxWidth) + 1;
        }

        return new int[][]{heights, widths};
    }

    public static int[][] generateUnimodalTestData(int n, int maxWidth, int maxHeight) {
        final int[] heights = new int[n];
        final int[] widths = new int[n];

        for (int i = 0; i < n / 2; i++) {
            heights[i] = maxHeight - random.nextInt(maxHeight / 2);
        }
        for (int i = n / 2; i < n; i++) {
            heights[i] = random.nextInt(maxHeight / 2) + (maxHeight / 2);
        }
        for (int i = 0; i < n; i++) {
            widths[i] = random.nextInt(maxWidth) + 1;
        }

        return new int[][]{heights, widths};
    }

    public static void runExperiments(int[] inputSizes, int maxWidth, int maxHeight) {
        System.out.println("Input Size\tProgram1 Time (ms)\tProgram2 Time (ms)");

        for (int n : inputSizes) {
            final int[][] testData = generateTestData(n, maxWidth, maxHeight);
            final int[][] unimodalTestData = generateUnimodalTestData(n, maxWidth, maxHeight);

            long startTime1 = System.currentTimeMillis();
            Program1.program1(n, maxWidth, testData[0], testData[1]);
            long endTime1 = System.currentTimeMillis();
            long duration1 = (endTime1 - startTime1);

            long startTime2 = System.currentTimeMillis();
            Program2.program2(n, maxWidth, unimodalTestData[0], unimodalTestData[1]);
            long endTime2 = System.currentTimeMillis();
            long duration2 = (endTime2 - startTime2);

            System.out.println(n + "\t\t" + duration1 + "\t\t\t" + duration2);
        }
    }

    public static void main(String[] args) {
        int[] inputSizes = {100000, 250000, 500000, 1000000, 10000000};
        int maxWidth = 100;
        int maxHeight = 100;

        /* Run each program once to "warm" it up and make sure first result is accurate due to Java using JIT compilation */
        final int[][] testData = generateTestData(inputSizes[0], maxWidth, maxHeight);
        Program1.program1(inputSizes[0], maxWidth, testData[0], testData[1]);
        Program2.program2(inputSizes[0], maxWidth, testData[0], testData[1]);

        runExperiments(inputSizes, maxWidth, maxHeight);
    }
}
