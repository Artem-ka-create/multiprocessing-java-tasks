package MatrixTask;

public class RowOperation implements Runnable{

    private final int[][] c;
    private int[][] a;
    private int[][] b;
    private final int row;

    public RowOperation (int[][] a, int[][] b, int[][] result, int row) {
        this.c = result;
        this.a = a;
        this.b = b;
        this.row = row;
    }

    @Override
    public void run() {
        for (int i = 0; i < b[0].length; i++) {
            c[row][i] = 0;
            for (int k = 0; k < a[row].length; k++) {
                c[row][i] += a[row][k] * b[k][i];
            }
        }

    }
}
