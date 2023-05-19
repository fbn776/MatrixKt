/**
 * A data class that contains a 1D matrix and its size information.
 * This is used to represent matrix as 1D list (The Matrix class treats a `matrix` as 2D array and does calculations with it)
 * @param matrix A flat list that contains the matrix elements
 * @param rows No of rows
 * @param cols Nof of cols
 */
data class Matrix1D<T>(val matrix: Array<T>, val rows: Int, val cols: Int)

/**An error container class that contains all the Errors the library can throw out.*/
sealed class MatrixError {
    class SizeError(msg: String = "The size of the matrix does not match the row and column count.") :
        Exception(msg)

    class IndexOutOfBound(msg: String = "Tried to access a value with index [i, j] that's outside of the index bounds") :
        Exception(msg)

    class NotSameSizeError(msg: String = "The matrices need to be of same size. The row and column count of the matrices should be same") :
        Exception(msg)

    class MatrixDimensionError(msg: String = "The number of columns in first matrix must be equal to the number of rows in second matrix for matrix multiplication.") :
        Exception(msg)
}

/**
 * Matrix representation class that coronations/represents a single matrix.
 * This class holds the properties of the matrix such and no of rows, columns, size etc.
 * This also holds different operation methods and other matrix methods
 * @param m A 1D [Array] of type uniform [Number] (Non-uniform numbers raise an error)
 * @param rows The number of rows of the matrix.
 * @param cols The number of columns of the matrix
 */
class Matrix<T : Number>(private val m: Array<T>, val rows: Int, val cols: Int) {
    /**This is the main matrix array. Operations are done on this array.
     * This is a copy of the passed array as when changing the non-copied array [m] can change the original array that passed as an argument
     */
    private val matrix1D = m.copyOf()

    init {
        //Check if the size of the matrix values is in accordance with the given rows and cols value;
        if (rows * cols != matrix1D.size) {
            throw MatrixError.SizeError()
        }
    }

    /*--------General methods/properties--------*/
    /**
     * Getter function for accessing matrix by [i, j] format
     * @param i This denotes the i-th row of the matrix
     * @param j This denotes the j-th column of the matrix
     * @return This returns the value of the matrix at (i, j) as a Double
     */
    operator fun get(i: Int, j: Int): T {
        if ((i < 0) || (i > rows - 1) || (j < 0) || (j > cols - 1))
            throw MatrixError.IndexOutOfBound()

        return matrix1D[convert2dToIndex(i, j)]
    }

    /**
     * Set a value specific value of the matrix at (i, j) and returns the new value;
     * @param i the i-th position
     * @param j the j-th position
     * @param newValue the set value
     */
    operator fun set(i: Int, j: Int, newValue: T): T {
        if ((i < 0) || (i > rows - 1) || (j < 0) || (j > cols - 1))
            throw MatrixError.IndexOutOfBound()

        matrix1D[convert2dToIndex(i, j)] = newValue
        return newValue
    }

    /**
     * Checks if a [Matrix] and an item is equal or not.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Matrix<*>
        if (this.rows != other.rows || this.cols != other.cols) return false
        return matrix1D.contentEquals(other.matrix1D)
    }

    /**
     * Clones a matrix and returns it.
     */
    fun clone(): Matrix<T> = Matrix(this.matrix1D.copyOf(), rows, cols)

    /**
     * Check if two matrices are of same size.
     * @param other the other matrix to do the check
     * @return true if rows and cols of both matrices are equal, else false
     */
    fun isOfSameSize(other: Matrix<*>) = (this.rows == other.rows && this.cols == other.cols)

    /**
     * Checks if two matrix can be cross multiplied or not
     * @return If two matrix A(n×m) and B(p×q) can be multiplied not. ie checks for m = p
     */
    fun canMult(other: Matrix<*>) = (this.cols == other.rows)

    /**Returns the matrix size**/
    val size = rows * cols

    /*--------Operators methods--------*/
    /**
     * Returns the sum of two matrices of same size.
     * The resultant is a Matrix<Double> type. This is due to difficulties in finding the types of passed matrices.
     * One way to solve this would be function overloading.
     * But the issue is code size increases (ie there is a need for separate function for Int + Int, Int + Float. Int + Double and so on combinations).
     * And also if this function is implemented as an overloaded function then other functions for other operators should be too. So much typing.
     * I guess I'm lazy (could probably be stupid too, I cant find any other way to do this) (This is a very terrible way to do things)
     * @param other The other matrix
     * @exception MatrixError.NotSameSizeError This error is raised when the passed matrix is not of the same size.
     * @return returns a [Matrix] of Double Type.
     */
    operator fun plus(other: Matrix<*>): Matrix<Double> {
        if (!this.isOfSameSize(other))
            throw MatrixError.NotSameSizeError()

        val m1 = this.getMatrix1D()
        val m2 = other.getMatrix1D()
        val result = Array(this.size) { 0.0 }
        result.forEachIndexed { i, _ ->
            result[i] = m1[i].toDouble() + m2[i].toDouble()
        }
        return Matrix(result, this.rows, this.cols)
    }

    /**
     * Returns the negative of the matrix.
     * @return This returns a matrix of type double.
     */
    operator fun unaryMinus() = (-1 * this)

    /**
     * Returns the difference of two matrices.
     * @return This returns a matrix of type double
     */
    operator fun minus(other: Matrix<*>) = this + (-other)

    /**
     * Returns the cross product of two matrices as new Matrix
     * @return The cross product of two matrices and returns a new matrix.
     * @exception MatrixError.MatrixDimensionError This error is thrown if the number of columns of 1st matrix doesn't match number of rows of 2nd matrix
     */
    operator fun times(other: Matrix<*>): Matrix<Double> {
        if (!this.canMult(other))
            throw MatrixError.MatrixDimensionError()

        val c = Matrix(Array(this.rows * other.cols) { 0.0 }, this.rows, other.cols)
        for (i in 0 until this.rows) {
            for (j in 0 until other.cols) {
                for (k in 0 until this.cols) {
                    c[i, j] = c[i, j] + (this[i, k].toDouble() * other[k, j].toDouble())
                }
            }
        }
        return c
    }

    /**
     * This returns the transposed matrix.
     * @return The transposed matrix
     */
    fun transposed(): Matrix<T> {
        val m = Matrix(this.matrix1D.copyOf(), this.cols, this.rows)
        for(i in 0 until this.cols){
            for(j in 0 until this.rows){
                m[i,j] = this[j, i]
            }
        }
        return m
    }

    /*--------Utils methods--------*/
    /**Returns the 1D matrix array*/
    fun getMatrix1D() = matrix1D

    /**Returns the 2D matrix array*/
    fun getMatrix2D(): Array<Array<Number>> {
        val matrix2D = Array(rows) { Array(cols) { 0 as Number } }
        //Packs the 1D matrix with the `rows` and `cols` information into a 2D matrix.
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                matrix2D[i][j] = matrix1D[i * cols + j] as T
            }
        }
        return matrix2D
    }

    /**
     * Converts the index of a 1D list to a 2D list (i,j)
     * @param index the index from the 1D list
     */
    private fun convertIndexTo2d(index: Int) = (index / cols) to (index % cols)

    /**
     * Converts a (i,j) of a 2D array into an index of a 1D array
     * @param i The i-th element of the outer array
     * @param j The j-th element of the inner arrays
     */
    private fun convert2dToIndex(i: Int, j: Int) = i * cols + j

    /**
     * Returns the formatted matrix;
     */
    override fun toString(): String {
        var str = "\n"
        for (i in 0 until rows) {
            var rowStr = ""
            for (j in 0 until cols) {
                rowStr += "${matrix1D[i * cols + j]} "
            }
            str += rowStr + '\n'
        }
        return str
    }

    /**Flattens the matrix(2D array) to a 1D matrix array
     * @return [Matrix1D] Returns the a [Matrix1D] data class that contains flattened 1D array, rows, columns.
     **/
    fun flatten() = Matrix1D(matrix1D, rows, cols)

    override fun hashCode(): Int {
        var result = matrix1D.contentHashCode()
        result = 31 * result + rows
        result = 31 * result + cols
        return result
    }
}

/**
 * Returns the scalar product of the Number * Matrix
 * @returns This returns a matrix of type double
 */
operator fun Number.times(other: Matrix<*>): Matrix<Double> {
    val matrix = other.getMatrix1D()
    val arr = Array(other.size) { 0.0 }
    arr.forEachIndexed { index, t ->
        arr[index] = (this.toDouble() * matrix[index].toDouble())
    }
    return Matrix(arr, other.rows, other.cols)
}