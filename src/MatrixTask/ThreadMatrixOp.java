package MatrixTask;

import java.util.ArrayList;
import java.util.List;

import static MatrixTask.Main.SIZE;
import static MatrixTask.Main.THREAD_COUNT;

public class ThreadMatrixOp {

    public static int[][] multiply( int [][] a , int [][]b ){
        List<Thread> threadList = new ArrayList<>();
        int[][] c = new int[SIZE][SIZE];

        // create Thread in loop
        for (int i = 0; i < SIZE; i++ ) {

            //make row operation
            Thread thread = new Thread(new RowOperation(a, b, c, i));
            thread.start();
            threadList.add(thread);

            // finish thread creating
            if (threadList.size() < THREAD_COUNT) {
                // wait others processes
                waitThreads(threadList);
                threadList.clear();
            }
        }
        return c;
    }
    private static void waitThreads(List<Thread> threadList) {

        for (Thread thr : threadList) {
            try {
                thr.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
