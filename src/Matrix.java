public class Matrix {
    private final Complex[][] data;
    private final int rows;
    private final int cols;

    Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = new Complex(0, 0);
            }
        }
    }

    Complex get(int i, int j) {
        return data[i][j];
    }

    void set(int i, int j, Complex value) {
        data[i][j] = value;
    }

    Matrix plus(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Размеры матриц не совпадают");
        }
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j].plus(other.data[i][j]);
            }
        }
        return result;
    }

    Matrix minus(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Размеры матриц не совпадают");
        }
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j].minus(other.data[i][j]);
            }
        }
        return result;
    }

    Matrix times(Matrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("умножение невозможно. число столбцов A != число строк B");
        }
        Matrix result = new Matrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < this.cols; k++) {
                    sum = sum.plus(this.data[i][k].times(other.data[k][j]));
                }
                result.data[i][j] = sum;
            }
        }
        return result;
    }

    Matrix div(Matrix other) {
        Matrix inv = other.inverse();
        return this.times(inv);
    }

    Matrix transpose() {
        Matrix result = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[j][i] = this.data[i][j];
            }
        }
        return result;
    }

    Complex determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException("матрица должна быть квадратной");
        }
        return detHelper(this.data);
    }

    private Complex detHelper(Complex[][] m) {
        int n = m.length;

        if (n == 1) {
            return m[0][0];
        }
        if (n == 2) {
            return m[0][0].times(m[1][1]).minus(m[0][1].times(m[1][0]));
        }

        Complex result = new Complex(0, 0);
        for (int col = 0; col < n; col++) {
            Complex[][] minor = makeMinor(m, 0, col);
            Complex cofactor = m[0][col].times(detHelper(minor));
            if (col % 2 == 0) {
                result = result.plus(cofactor);
            } else {
                result = result.minus(cofactor);
            }
        }
        return result;
    }

    private Complex[][] makeMinor(Complex[][] m, int row, int col) {
        int n = m.length;
        Complex[][] minor = new Complex[n - 1][n - 1];
        int mi = 0;
        for (int i = 0; i < n; i++) {
            if (i == row) continue;
            int mj = 0;
            for (int j = 0; j < n; j++) {
                if (j == col) continue;
                minor[mi][mj] = m[i][j];
                mj++;
            }
            mi++;
        }
        return minor;
    }

    Matrix inverse() {
        if (rows != cols) {
            throw new IllegalArgumentException("обратная матрица только для квадратных");
        }
        Complex det = determinant();
        if (det.isZero()) {
            throw new ArithmeticException("определитель равен нулю. обратной матрицы нет");
        }

        int n = rows;
        Matrix result = new Matrix(n, n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Complex[][] minor = makeMinor(this.data, i, j);
                Complex cofactor = detHelper(minor);
                if ((i + j) % 2 != 0) {
                    cofactor = new Complex(-cofactor.re, -cofactor.im);
                }
                result.data[j][i] = cofactor.div(det);
            }
        }
        return result;
    }

    void print() {
        for (int i = 0; i < rows; i++) {
            System.out.print("[ ");
            for (int j = 0; j < cols; j++) {
                System.out.print(data[i][j] + "\t");
            }
            System.out.println("]");
        }
    }
}