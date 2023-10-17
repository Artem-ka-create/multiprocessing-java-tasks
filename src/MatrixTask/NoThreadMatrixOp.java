package MatrixTask;

import static MatrixTask.Main.SIZE;

public class NoThreadMatrixOp {

    public static int[][] fillMatrix(int size, int max, int min) {
        int[][] arr = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int b = 0; b < size; b++) {
                arr[i][b] = (int) Math.floor(Math.random() * (max - min + 1) + min);
            }
        }
        return arr;
    }
    public static int[][] ZeroMatrix(int size) {
        int[][] arr = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int b = 0; b < size; b++) {
                arr[i][b] = 0;
            }
        }
        return arr;
    }

    public static int[][] multipleMatrixes(int[][] a, int[][] b) {
        int c[][] = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {

            for (int j = 0; j < SIZE; j++) {
                c[i][j] = 0;

                for (int k = 0; k < SIZE; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
//                System.out.print(c[i][j] + " ");  //printing matrix element
            }
//            System.out.println();//new line
        }
        return c;
    }

    public static int[][] addMatrixes(int size, int[][] a, int[][] b) {
        int c[][] = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                c[i][j] = a[i][j]+b[i][j];
            }
        }
        return c;
    }
}
