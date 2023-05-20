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
     * @return This returns the value of the matrix at (i, j)
     */
    operator fun get(i: Int, j: Int): T {
        if ((i < 0) || (i > rows - 1) || (j < 0) || (j > cols - 1))
            throw MatrixError.IndexOutOfBound()

        return matrix1D[convert2dToIndex(i, j)]
    }

    /**
     * Returns the ith element of the matrix
     * @param i The position of element to be returned.
     * @return element at ith position
     * @exception MatrixError.IndexOutOfBound Thrown when the passed index is out of bounds.
     */
    fun getValueAtIndex(i: Int): T {
        if(i > size || i < 0)
            throw MatrixError.IndexOutOfBound()

        return matrix1D[i]
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

    /**
     * Checks if a matrix is square or not.
     * @return true if the matrix is square, else false
     */
    fun isSquare() = (this.rows == this.cols)

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
     * @exception MatrixError.NotSameSizeMatrix This error is raised when the passed matrix is not of the same size.
     * @return returns a [Matrix] of Double Type.
     */
    operator fun plus(other: Matrix<*>): Matrix<Double> {
        if (!this.isOfSameSize(other))
            throw MatrixError.NotSameSizeMatrix()

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
     * @exception MatrixError.MultiplicationDimensionError This error is thrown if the number of columns of 1st matrix doesn't match number of rows of 2nd matrix
     */
    operator fun times(other: Matrix<*>): Matrix<Double> {
        if (!this.canMult(other))
            throw MatrixError.MultiplicationDimensionError()

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
        for (i in 0 until this.cols) {
            for (j in 0 until this.rows) {
                m[i, j] = this[j, i]
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
    fun convertIndexTo2d(index: Int) = (index / cols) to (index % cols)

    /**
     * Converts a (i,j) of a 2D array into an index of a 1D array
     * @param i The i-th element of the outer array
     * @param j The j-th element of the inner arrays
     */
    fun convert2dToIndex(i: Int, j: Int) = i * cols + j

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

