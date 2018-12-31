import java.util.Scanner;

public class apacSuper2048 {

    // general swap method
    private void swapInMatrix(int[][] matrix, int i1, int j1, int i2, int j2) {
        int temp = matrix[i1][j1];
        matrix[i1][j1] = matrix[i2][j2];
        matrix[i2][j2] = temp;
    }


    // method to find transpose of a given matrix
    private void matrixTranspose(int[][] matrix) {
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = i; j < matrix[0].length; j++) {
                this.swapInMatrix(matrix, i, j, j, i);
            }
        }
    }


    //method to rotate a matrix
    private void rotateMatrix(int[][] matrix, String DIR) {

        int N = matrix.length;

        if (DIR.equals("left")) {
            this.matrixTranspose(matrix);
            for (int i = 0; i < N/2; i++) {
                for (int j = 0; j < N; j++) {
                    this.swapInMatrix(matrix, i, j, N-1-i, j);
                }
            }
        }

        if (DIR.equals("right")) {
            this.matrixTranspose(matrix);
            for (int i = 0; i < N/2; i++) {
                for (int j = 0; j < N; j++) {
                    this.swapInMatrix(matrix, j, i, j, N-1-i);
                }
            }
        }

        if (DIR.equals("up")) {
            for (int i = 0; i < N; i++) {
                for (int j = i; j < N; j++) {
                    this.swapInMatrix(matrix, i, j, N-1-i, N-1-j);
                }
            }

            //because first and last element get swiped twice
            this.swapInMatrix(matrix, 0, 0, N-1, N-1);
        }
    }


    // method to find the resultant matrix after swapping
    private void swipeMatrix(int[][] matrix) {
        int N = matrix.length;
        for (int i = 0; i < matrix.length; i++) {
            int iterator = N-1, anchor = N-1;
            int[] column = new int[N];
            column[anchor] = matrix[iterator--][i];
            while (iterator > -1) {
                while (iterator > -1 && matrix[iterator][i] == 0) {
                    iterator--;
                }
                if (iterator == -1) {
                    break;
                }
                if (matrix[iterator][i] == column[anchor]) {
                    column[anchor] *= 2;
                    matrix[iterator][i] = 0;
                    anchor--;
                    iterator--;
                } else if(column[anchor] == 0){
                    column[anchor] = matrix[iterator][i];
                    iterator--;
                } else {
                    anchor--;
                    column[anchor] = matrix[iterator][i];
                    iterator--;
                }
            }
            for ( int k = N-1; k > -1; k--) {
                matrix[k][i] = column[k];
            }
        }
    }


    // method to print a matrix
    private void printMatrix(int[][] matrix) {
        for (int[] matrix1 : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix1[j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        //Generic way ot take inputs. Take care of not converting Scanner object from int to String (not reverse) .
        Scanner sc = new Scanner(System.in);
        int testCases = Integer.parseInt(sc.next());
        int Case = 1;
        while (testCases-- > 0) {
            int N = Integer.parseInt(sc.next());
            String DIR = sc.next();
            int[][] grid = new int[N][N];
            for (int i =0; i<N; i++) {
                for (int j =0; j<N; j++) {
                    grid[i][j] = sc.nextInt();
                }
            }
            apacSuper2048 a = new apacSuper2048();

            if (DIR.equals("left")) {
                a.rotateMatrix(grid, "left");
                a.swipeMatrix(grid);
                a.rotateMatrix(grid, "right");
            } else if (DIR.equals("right")) {
                a.rotateMatrix(grid, "right");
                a.swipeMatrix(grid);
                a.rotateMatrix(grid, "left");
            } else if (DIR.equals("down")) {
                a.swipeMatrix(grid);
            } else if (DIR.equals("up")) {
                a.rotateMatrix(grid, "right");
                a.rotateMatrix(grid, "right");
                a.swipeMatrix(grid);
                a.rotateMatrix(grid, "right");
                a.rotateMatrix(grid, "right");

            }
            System.out.printf("Case #%d:\n", Case++);
            a.printMatrix(grid);
            grid = null;
        }
    }
}
