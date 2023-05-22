import kotlin.math.pow

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
operator fun <T : Number> Matrix<T>.plus(other: Matrix<*>): Matrix<Double> {
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
operator fun <T : Number> Matrix<T>.unaryMinus() = (-1 * this)

/**
 * Returns the difference of two matrices.
 * @return This returns a matrix of type double
 */
operator fun <T : Number> Matrix<T>.minus(other: Matrix<*>) = this + (-other)

/**
 * Returns the scalar product of the Number * Matrix
 * @returns This returns a matrix of type double
 */
operator fun Number.times(other: Matrix<*>): Matrix<Double> {
    val matrix = other.getMatrix1D()
    val arr = Array(other.size) { 0.0 }
    arr.forEachIndexed { index, _ ->
        arr[index] = (this.toDouble() * matrix[index].toDouble())
    }
    return Matrix(arr, other.rows, other.cols)
}

/**
 * Returns the cross product of two matrices as new Matrix
 * @return The cross product of two matrices and returns a new matrix.
 * @exception MatrixError.MultiplicationDimensionError This error is thrown if the number of columns of 1st matrix doesn't match number of rows of 2nd matrix
 */
operator fun <T : Number> Matrix<T>.times(other: Matrix<*>): Matrix<Double> {
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
fun <T : Number> Matrix<T>.transposed(): Matrix<T> {
    val m = Matrix(getMatrix1D().copyOf(), this.cols, this.rows)
    for (i in 0 until this.cols) {
        for (j in 0 until this.rows) {
            m[i, j] = this[j, i]
        }
    }
    return m
}

/**
 * This returns a new matrix with 1 row and 1 column less than the original matrix.
 * @param rowToExclude The row to exclude from the matrix.
 * @param colToExclude The column to exclude from the matrix.
 * @return The new matrix with 1 row and 1 column less than the original matrix.
 * @exception MatrixError.NotSquareMatrix This error is thrown if the matrix is not a square matrix.
 * @exception MatrixError.SizeTooSmall This error is thrown if the matrix is too small to exclude a row and column.
 * @exception MatrixError.DimensionOutOfBounds This error is thrown if the row or column to exclude is out of bounds.
 */
inline fun <reified T : Number> Matrix<T>.subSqMatrix(rowToExclude: Int, colToExclude: Int): Matrix<T> {
    if (!this.isSquare())
        throw MatrixError.NotSquareMatrix()

    if (this.rows <= 1)
        throw MatrixError.SizeTooSmall()

    if (rowToExclude > this.rows - 1 || colToExclude > this.cols - 1)
        throw MatrixError.DimensionOutOfBounds()

    val m = Matrix(Array((this.rows - 1) * (this.cols - 1)) { this.getValueAtIndex(0) }, this.rows - 1, this.cols - 1)

    for (n in 0 until this.size) {
        var (ith, jth) = this.convertIndexTo2d(n)
        val curr = this[ith, jth]

        if (ith == rowToExclude || jth == colToExclude)
            continue

        if (ith > rowToExclude)
            ith--

        if (jth > colToExclude)
            jth--

        m[ith, jth] = curr
    }

    return m
}

/**
 * Returns the determinant of a square matrix
 * @return the determinant the given matrix as a double.
 * @exception MatrixError.NotSquareMatrix Thrown when the passed matrix is not a square matrix (For finding the determinant, a square matrix is required).
 */
fun <T : Number> Matrix<T>.determinant(): Double {
    if (!this.isSquare())
        throw MatrixError.NotSquareMatrix()

    //If it's a 1×1 matrix, then determinant is itself
    if (this.rows == 1)
        return this[0, 0].toDouble()

    //If it's a 2x2 matrix, then determinant is;
    if (this.rows == 2)
        return (this[0, 0].toDouble() * this[1, 1].toDouble()) - (this[0, 1].toDouble() * this[1, 0].toDouble())

    var det = 0.0
    for (j in 0 until this.rows) {
        val subMatrix = -(-this).subSqMatrix(0, j)
        val subDet = subMatrix.determinant()

        val sign = if (j % 2 == 0) 1.0 else -1.0

        det += (sign * this[0, j].toDouble() * subDet)
    }

    return det
}

/**
 * Returns the minor of a square matrix at (i,j)
 * @param i The row of the element
 * @param j The column of the element
 * @return the minor of the given matrix at (i,j) as a double.
 * @exception MatrixError.NotSquareMatrix Thrown when the passed matrix is not a square matrix (For finding the determinant, a square matrix is required).
 * @exception MatrixError.IndexOutOfBound Thrown when the passed index is out of bound.
 */
inline fun <reified T : Number> Matrix<T>.minorAt(i: Int, j: Int): Double {
    if (!this.isSquare())
        throw MatrixError.NotSquareMatrix()

    if ((i < 0) || (i > rows - 1) || (j < 0) || (j > cols - 1))
        throw MatrixError.IndexOutOfBound()

    return this.subSqMatrix(i, j).determinant()
}

/**
 * Returns the minor matrix of a square matrix
 * @return the minor matrix as Matrix<Double>.
 * @exception MatrixError.NotSquareMatrix Thrown when the passed matrix is not a square matrix (For finding the determinant, a square matrix is required).
 */
inline fun <reified T : Number> Matrix<T>.minorMatrix(): Matrix<Double> {
    if (!this.isSquare())
        throw MatrixError.NotSquareMatrix()

    val m = Matrix.typedMatrixOf(this.rows, this.cols) { 0.0 }

    m.forEachIndexed2d { (i, j), _ ->
        m[i, j] = this.minorAt(i, j)
    }
    return m
}

/**
 * Returns the cofactor of a square matrix at (i,j)
 * @param i The row index
 * @param j The column index
 * @return the cofactor of the given matrix at (i,j) as a double.
 * @exception MatrixError.NotSquareMatrix Thrown when the passed matrix is not a square matrix (For finding the determinant, a square matrix is required).
 * @exception MatrixError.IndexOutOfBound Thrown when the passed index is out of bound.
 */
inline fun <reified T : Number> Matrix<T>.cofactorAt(i: Int, j: Int): Double {
    val minorAtIJ = this.minorAt(i, j)

    return (-1.0).pow(i + j) * minorAtIJ
}

/**
 * Returns the cofactor matrix of a square matrix
 * @return the cofactor matrix as Matrix<Double>.
 * @exception MatrixError.NotSquareMatrix Thrown when the passed matrix is not a square matrix (For finding the determinant, a square matrix is required).
 */
inline fun <reified T : Number> Matrix<T>.cofactorMatrix(): Matrix<Double> {
    if (!this.isSquare())
        throw MatrixError.NotSquareMatrix()

    val m = Matrix.typedMatrixOf(this.rows, this.cols) { 0.0 }
    m.forEachIndexed2d { (i, j), _ ->
        m[i, j] = this.cofactorAt(i, j)
    }
    return m
}

/**
 * Returns the adjoint of a square matrix
 * @exception MatrixError.NotSquareMatrix Thrown when the passed matrix is not a square matrix.
 */
inline fun <reified T : Number> Matrix<T>.adjoint(): Matrix<Double> {
    return this.cofactorMatrix().transposed()
}

/**
 * Returns the inverse of a square matrix
 * @return the inverse of the given matrix as Matrix<Double>.
 * @exception MatrixError.NotSquareMatrix Thrown when the passed matrix is not a square matrix.
 * @exception MatrixError.SingularMatrix Thrown when the passed matrix is a singular matrix.
 */
inline fun <reified T : Number> Matrix<T>.inverse(): Matrix<Double> {
    val det = this.determinant()

    if (det == 0.0)
        throw MatrixError.SingularMatrix()

    return (1 / det) * this.adjoint()
}

/**
 * Transforms an existing row of a matrix to a new value using a transform function.
 * This is an In place transformation. The original matrix is modified.
 * This transformation respects the type of Matrix. ie if the matrix is of type Int, the transform function should return an Int.
 * @param row The row to transform.
 * @param transform The transform function. That takes in the current value and returns the new value.
 * @exception MatrixError.DimensionOutOfBounds Thrown when the passed row index is out of bounds.
 */
fun <T : Number> Matrix<T>.transformRow(row: Int, transform: (current: T) -> T) {
    if (row < 0 || row > this.rows - 1)
        throw MatrixError.DimensionOutOfBounds()

    for (j in 0 until this.cols) {
        this[row, j] = transform(this[row, j])
    }
}

/**
 * Transforms an existing row of a matrix to a new value using a transform function. Same as [transformRow] but also takes another row as a parameter.
 * This is an In place transformation. The original matrix is modified.
 * This transformation respects the type of Matrix. ie if the matrix is of type Int, the transform function should return an Int.
 * @param row The row to transform.
 * @param otherRow The other row to use in the transform function.
 * @param transform The transform function. That takes in the current value, the other corresponding row value and returns the new value.
 * @exception MatrixError.DimensionOutOfBounds Thrown when the passed row index is out of bounds.
 */
fun <T : Number> Matrix<T>.transformRow(row: Int, otherRow: Int, transform: (current: T, other: T) -> T) {
    if (row < 0 || row > this.rows - 1 || otherRow < 0 || otherRow > this.rows - 1)
        throw MatrixError.DimensionOutOfBounds()

    for (j in 0 until this.cols) {
        this[row, j] = transform(this[row, j], this[otherRow, j])
    }
}

