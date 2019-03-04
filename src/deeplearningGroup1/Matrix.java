package deeplearningGroup1;

public class Matrix {
	
	private final int M;             // number of rows
    private final int N;             // number of columns
    private final double[][] data;   // M-by-N array

    // create M-by-N matrix of 0's
    public Matrix(int M, int N) {
        this.M = M;
        this.N = N;
        data = new double[M][N];
    }

    // create matrix based on 2d array
    public Matrix(double[][] data) {
        M = data.length;
        N = data[0].length;
        this.data = new double[M][N];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                    this.data[i][j] = data[i][j];
    }

    // create and return a random M-by-N matrix with values between 0 and 1
    public static Matrix random(int M, int N) {
        Matrix A = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[i][j] = Math.random();
        return A;
    }

    // create and return the N-by-N identity matrix
    public static Matrix identity(int N) {
        Matrix I = new Matrix(N, N);
        for (int i = 0; i < N; i++)
            I.data[i][i] = 1;
        return I;
    }

    // create and return the transpose of the invoking matrix
    public Matrix transpose() {
        Matrix A = new Matrix(N, M);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[j][i] = this.data[i][j];
        return A;
    }

    // return C = A + B
    public Matrix plus(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = A.data[i][j] + B.data[i][j];
        return C;
    }


    // return C = A - B
    public Matrix minus(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = A.data[i][j] - B.data[i][j];
        return C;
    }

    // does A == B exactly?
    public boolean isEqual(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                if (A.data[i][j] != B.data[i][j]) return false;
        return true;
    }

    // return C = A * B
    public Matrix multiply(Matrix B) {
        Matrix A = this;
        if (A.N != B.M) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(A.M, B.N);
        for (int i = 0; i < C.M; i++)
            for (int j = 0; j < C.N; j++)
                for (int k = 0; k < A.N; k++)
                    C.data[i][j] = (A.data[i][k] * B.data[k][j]);
        return C;
    }
    
    public Matrix multiply(double b) {
    	Matrix A = this;
    	Matrix C = new Matrix(A.M, A.N);
        for (int i = 0; i < C.M; i++)
            for (int j = 0; j < C.N; j++)
                for (int k = 0; k < A.N; k++)
                    C.data[i][j] = A.data[i][k] * b;
        return C;
    }
    
    public Matrix elementMultiply(Matrix B) {
    	Matrix A = this;
    	if (A.M != B.M || A.N != B.N) throw new RuntimeException("Illegal matrix dimensions.");
    	Matrix C = new Matrix(A.M, A.N);
    	for (int i = 0; i < A.M; i++)
            for (int j = 0; j < A.N; j++)
                C.data[i][j] = A.data[i][j] * B.data[i][j];
    	return C;
    }
    
    /*
    // return B = e^A
    public Matrix exp() {
    	Matrix A = this;
    	Matrix B = new Matrix(A.M, A.N);
    	for (int i = 0; i < A.M; i++)
    		for (int j = 0; j < A.N; j++)
    			B.data[i][j] = Math.exp(A.data[i][j]);
    	return B;
    }
    */
    
    // return B = A^p
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
    
    public double[][] getData() {
    	return data;
    }
    
    public double get(int y, int x) {
    	return data[y][x];
    }
    
    public void set(int y, int x, double num) {
    	data[y][x] = num;
    }
    
    public int getM() {
    	return M;
    }
    
    public int getN() {
    	return N;
    }

    public void print() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) 
                System.out.printf("%9.4f ", data[i][j]);
            System.out.println();
        }
    }

}
