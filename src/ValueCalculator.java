import java.util.stream.IntStream;

public class ValueCalculator {

    private int arraySize = 1_000_000;
    private float[] array = new float[arraySize];
    private int halfArraySize =  arraySize / 2;


    public void execute() throws InterruptedException {
        long start = System.currentTimeMillis();

        for (int i = 0; i < arraySize; i++){
            array[i] += 1;
        }

        float[] firstArrayHalf = new float[halfArraySize];
        float[] secondArrayHalf = new float[halfArraySize];

        System.arraycopy(array,0, firstArrayHalf, 0, halfArraySize);
        System.arraycopy(array, halfArraySize, secondArrayHalf, 0, halfArraySize);

        createNewThread(firstArrayHalf);
        createNewThread(secondArrayHalf);

        System.arraycopy(firstArrayHalf,0, array, 0, halfArraySize);
        System.arraycopy(secondArrayHalf, 0, array, halfArraySize, halfArraySize);

        long stop = System.currentTimeMillis();

        System.out.printf("Було витрачено : %d мс", stop - start);
    }

    private void createNewThread(float[] array) throws InterruptedException {
        Runnable newThread = () ->
        {
            for (int i = 0; i < array.length; i++){
                array[i] = (float)(array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        };

        Thread run = new Thread(newThread);

        run.start();
        run.join();
    }
}

