package deeplearningGroup1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Matrix is a 2D array of doubles with defined matrix operations
 * @author Steven Rose
 * @version 1.0
 */
public class Matrix {
	
	/**
	 * Number of rows in the matrix
	 */
	private final int M;
	
	/**
	 * Number of columns in the matrix
	 */
    private final int N;
    
    /**
     * 2D array of data in matrix
     */
    private final double[][] data;   // M-by-N array

    /**
     * Creates an empty m x n matrix
     * @param M
     * 		Height of matrix
     * @param N
     * 		Width of matrix
     */
    public Matrix(int M, int N) {
        this.M = M;
        this.N = N;
        data = new double[M][N];
    }

    /**
     * Creates a matrix from existing 2D array
     * @param data
     * 		2D array of data to make matrix from
     */
    public Matrix(double[][] data) {
        M = data.length;
        N = data[0].length;
        this.data = new double[M][N];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                    this.data[i][j] = data[i][j];
    }

    /**
     * Returns m x n matrix of random values [0..1)
     * @param M
     * 		Height of matrix
     * @param N
     * 		Width of matrix
     * @return
     * 		Matrix with random [0..1) values
     */
    public static Matrix random(int M, int N, int max) {
        Matrix A = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++) {
            	A.data[i][j] = Math.random() * max * (Math.random() < 0.5 ? -1 : 1);
            }
        return A;
    }

    /**
     * Creates n x n identity matrix
     * @param N
     * 		Size of matrix
     * @return
     * 		Identity matrix
     */
    public static Matrix identity(int N) {
        Matrix I = new Matrix(N, N);
        for (int i = 0; i < N; i++)
            I.data[i][i] = 1;
        return I;
    }

    /**
     * Returns transpose of the matrix
     * @return
     * 	Transpose of matrix
     */
    public Matrix transpose() {
        Matrix A = new Matrix(N, M);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[j][i] = this.data[i][j];
        return A;
    }

    /**
     * Returns Matrix A + B
     * @param B
     * 		Matrix to add
     * @return
     * 		Sum of matrices
     */
    public Matrix plus(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = A.data[i][j] + B.data[i][j];
        return C;
    }

    /**
     * Returns Matrix A - B
     * @param B
     * 		Matrix to subtract
     * @return
     * 		Difference of matrices
     */
    public Matrix minus(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = A.data[i][j] - B.data[i][j];
        return C;
    }

    /**
     * Checks if two matrices are equal
     * @param B
     * 		Matrix to check against
     * @return
     * 		True if matrices are equal, false otherwise
     */
    public boolean isEqual(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                if (A.data[i][j] != B.data[i][j]) return false;
        return true;
    }

    /**
     * Returns matrix A * B
     * @param B
     * 		Matrix to multiply by
     * @return
     * 		Product of matrices
     */
    public Matrix multiply(Matrix B) {
        Matrix A = this;
        if (A.N != B.M) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(A.M, B.N);
        for (int i = 0; i < C.M; i++)
            for (int j = 0; j < C.N; j++)
                for (int k = 0; k < A.N; k++)
                    C.data[i][j] += (A.data[i][k] * B.data[k][j]);
        return C;
    }
    
    /**
     * Returns matrix A * b
     * @param b
     * 		Scalar to multiply by
     * @return
     * 		Product of matrix and scalar
     */
    public Matrix multiply(double b) {
    	Matrix A = this;
    	Matrix C = new Matrix(A.M, A.N);
        for (int i = 0; i < C.M; i++)
            for (int j = 0; j < C.N; j++)
            	C.data[i][j] = A.data[i][j] * b;
        return C;
    }
    
    /**
     * Returns element by element product of A .* B
     * @param B
     * 		Matrix to multiply by
     * @return
     * 		Element wise product of matrices
     */
    public Matrix elementMultiply(Matrix B) {
    	Matrix A = this;
    	if (A.M != B.M || A.N != B.N) throw new RuntimeException("Illegal matrix dimensions.");
    	Matrix C = new Matrix(A.M, A.N);
    	for (int i = 0; i < A.M; i++)
            for (int j = 0; j < A.N; j++)
                C.data[i][j] = A.data[i][j] * B.data[i][j];
    	return C;
    }
    
    /**
     * Returns Matrix elements raised to a power
     * @param p
     * 		power to raise matrix element to
     * @return
     * 		Element wise matrix raised to the power p
     */
    public Matrix pow(double p) {
    	Matrix A = this;
    	Matrix B = new Matrix(A.M, A.N);
    	for (int i = 0; i < A.M; i++) {
    		for (int j = 0; j < A.N; j++) {
    			B.set(i, j, Math.pow(A.get(i, j), p));
    		}
    	}
    	return B;
    }
    
    public Matrix divide(double b) {
    	Matrix A = this;
    	Matrix C = new Matrix(A.M, A.N);
        for (int i = 0; i < C.M; i++)
            for (int j = 0; j < C.N; j++)
            	C.data[i][j] = A.data[i][j] / b;
        return C;
    }
    
    /**
     * Returns the 2D array of data of matrix
     * @return
     * 		2D array of data
     */
    public double[][] getData() {
    	return data;
    }
    
    /**
     * Returns a single element of a matrix
     * @param y
     * 		Y location of element
     * @param x
     * 		X location of element
     * @return
     * 		Value of matrix at location (x, y)
     */
    public double get(int y, int x) {
    	return data[y][x];
    }
    
    /**
     * Sets a single element of a matrix
     * @param y
     * 		Y location of element
     * @param x
     * 		X location of element
     * @param num
     * 		Number to set as new element at location (y, x)
     */
    public void set(int y, int x, double num) {
    	data[y][x] = num;
    }
    
    /**
     * Returns height of matrix
     * @return
     * 		Number of rows in matrix
     */
    public int getM() {
    	return M;
    }
    
    /**
     * Returns width of matrix
     * @return
     * 		Number of columns in matrix
     */
    public int getN() {
    	return N;
    }

    /**
     * Prints all the values of a matrix to the console
     */
    public void print() {
    	if (Jarvis.debugging) {
    		PrintWriter out = null;
    		try {
    			out = new PrintWriter(new FileWriter("log_file.txt", true));
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		for (int i = 0; i < M; i++) {
    			for (int j = 0; j < N; j++) {
    				System.out.printf("%9.4f ", data[i][j]);
    				out.printf("%9.4f ", data[i][j]);
    			}
    			System.out.println();
    			out.println();
    		}
    		out.close();
    	}
    }

}
