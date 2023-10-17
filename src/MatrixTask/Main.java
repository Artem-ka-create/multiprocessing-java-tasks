package MatrixTask;

import java.util.Arrays;

public class Main {

    //Size of Matrixes
    public static int SIZE = 1000;

    // Change Thread_Count to use Multi-Threading
    public static int THREAD_COUNT = 5;

    public static void main(String[] args) {

        // create matrixes
        Matrix a = new Matrix(SIZE);
        Matrix b = new Matrix(SIZE);
        Matrix res = new Matrix(SIZE);

        //fill matrixes by random numbers
        a.setMatrix(NoThreadMatrixOp.fillMatrix(a.getSize(), 100, 1));
        b.setMatrix(NoThreadMatrixOp.fillMatrix(b.getSize(), 100, 1));
        res.setMatrix(NoThreadMatrixOp.ZeroMatrix(res.getSize()));


        long startTime = System.nanoTime();
        multMatrixes(a,b,res);
        long howLong  =(System.nanoTime() - startTime)/100_000_000;

        //show matrix
        System.out.println(Arrays.deepToString(res.getMatrix()));

        System.out.println( "Time : " + howLong + " Milliseconds");


    }

    public static void multMatrixes(Matrix a ,Matrix b ,Matrix res ){
        if (THREAD_COUNT > 0){

            // multiple matrixes with Threads
            res.setMatrix(ThreadMatrixOp.multiply(a.getMatrix(),b.getMatrix()));
        }else{

            // multiple matrixes without Threads
            res.setMatrix(NoThreadMatrixOp.multipleMatrixes(a.getMatrix(),b.getMatrix()));
        }
    }
}

