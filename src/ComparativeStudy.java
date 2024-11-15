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
        System.out.println("Input Size\tProgram1\tProgram2\tProgram3\tProgram4\tProgram5A\tProgram5B");

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

            long startTime3 = System.currentTimeMillis();
            Program3.program3(n, maxWidth, testData[0], testData[1]);
            long endTime3 = System.currentTimeMillis();
            long duration3 = (endTime3 - startTime3);

            long startTime4 = System.currentTimeMillis();
            Program4.program4(n, maxWidth, testData[0], testData[1]);
            long endTime4 = System.currentTimeMillis();
            long duration4 = (endTime4 - startTime4);

            long startTime5A = System.currentTimeMillis();
            Program5A.program5A(n, maxWidth, testData[0], testData[1]);
            long endTime5A = System.currentTimeMillis();
            long duration5A = (endTime5A - startTime5A);

            long startTime5B = System.currentTimeMillis();
            Program5B.program5B(n, maxWidth, testData[0], testData[1]);
            long endTime5B = System.currentTimeMillis();
            long duration5B = (endTime5B - startTime5B);

            System.out.println(n + "\t\t" + duration1 + "\t\t\t" + duration2 + "\t\t\t" + duration3 + "\t\t\t" + duration4 + "\t\t\t" + duration5A + "\t\t\t" + duration5B);

        }
    }

    public static void main(String[] args) {
        int[] inputSizes = {1000, 2500, 5000, 7500, 10000};
        int maxWidth = 100;
        int maxHeight = 100;

        /* Run each program once to "warm" it up and make sure first result is accurate due to Java using JIT compilation */
        final int[][] warmupData = generateTestData(inputSizes[0], maxWidth, maxHeight);
        Program1.program1(inputSizes[0], maxWidth, warmupData[0], warmupData[1]);
        Program2.program2(inputSizes[0], maxWidth, warmupData[0], warmupData[1]);
        Program3.program3(inputSizes[0], maxWidth, warmupData[0], warmupData[1]);
        Program4.program4(inputSizes[0], maxWidth, warmupData[0], warmupData[1]);
        Program5A.program5A(inputSizes[0], maxWidth, warmupData[0], warmupData[1]);
        Program5B.program5B(inputSizes[0], maxWidth, warmupData[0], warmupData[1]);

        runExperiments(inputSizes, maxWidth, maxHeight);
    }
}
