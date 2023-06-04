package matrix

import kotlin.math.pow

/**
 * Returns the sum of two matrices of same size.
 * The resultant is a Matrix.Matrix<Double> type. This is due to difficulties in finding the types of passed matrices.
 * One way to solve this would be function overloading.
 * But the issue is code size increases (ie there is a need for separate function for Int + Int, Int + Float. Int + Double and so on combinations).
 * And also if this function is implemented as an overloaded function then other functions for other operators should be too. So much typing.
 * I guess I'm lazy (could probably be stupid too, I cant find any other way to do this) (This is a very terrible way to do things)
 * @param other The other matrix
 * @exception MatrixError.NotSameSizeMatrix This error is raised when the passed matrix is not of the same size.
 * @return returns a [matrix] of Double Type.
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
 * Returns the scalar product of the Number * Matrix.Matrix
 * @returns This returns a matrix of type Double
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
 * Returns the scalar division of the Matrix.Matrix / Number
 * @return Returns a new matrix of type Double.
 * @exception MatrixError.DivisionByZero Thrown when a Matrix.Matrix is divided by 0.
 */
operator fun <T : Number> Matrix<T>.div(other: Number): Matrix<Double> {
    val num = other.toDouble()
    if (num == 0.0)
        throw MatrixError.DivisionByZero()

    return (1 / num) * this
}

/**
 * Returns the cross product of two matrices as new Matrix.Matrix
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
 * @return the minor matrix as Matrix.Matrix<Double>.
 * @exception MatrixError.NotSquareMatrix Thrown when the passed matrix is not a square matrix (For finding the determinant, a square matrix is required).
 */
inline fun <reified T : Number> Matrix<T>.minorMatrix(): Matrix<Double> {
    if (!this.isSquare())
        throw MatrixError.NotSquareMatrix()

    val m = Matrix.matrixOf(this.rows, this.cols) { 0.0 }

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
 * @return the cofactor matrix as Matrix.Matrix<Double>.
 * @exception MatrixError.NotSquareMatrix Thrown when the passed matrix is not a square matrix (For finding the determinant, a square matrix is required).
 */
inline fun <reified T : Number> Matrix<T>.cofactorMatrix(): Matrix<Double> {
    if (!this.isSquare())
        throw MatrixError.NotSquareMatrix()

    val m = Matrix.matrixOf(this.rows, this.cols) { 0.0 }
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
 * @return the inverse of the given matrix as Matrix.Matrix<Double>.
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
 * This transformation respects the type of Matrix.Matrix. ie if the matrix is of type Int, the transform function should return an Int.
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
 * This transformation respects the type of Matrix.Matrix. ie if the matrix is of type Int, the transform function should return an Int.
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

/**
 * Transforms an existing column of a matrix to a new value using a transform function.
 * This is an In place transformation. The original matrix is modified.
 * This transformation respects the type of Matrix.Matrix. ie if the matrix is of type Int, the transform function should return an Int.
 * @param col The column to transform.
 * @param transform The transform function. That takes in the current value and returns the new value.
 * @exception MatrixError.DimensionOutOfBounds Thrown when the passed row index is out of bounds.
 */
fun <T : Number> Matrix<T>.transformCol(col: Int, transform: (current: T) -> T) {
    if (col < 0 || col > this.cols - 1)
        throw MatrixError.DimensionOutOfBounds()

    for (j in 0 until this.rows) {
        this[j, col] = transform(this[j, col])
    }
}

/**
 * Transforms an existing column of a matrix to a new value using a transform function. Same as [transformCol] but also takes another column as a parameter.
 * This is an in place transformation. The original matrix is modified.
 * This transformation respects the type of Matrix.Matrix. ie if the matrix is of type Int, the transform function should return an Int.
 * @param col The column to transform.
 * @param otherCol The other column to use in the transform function.
 * @param transform The transform function. That takes in the current value, the other corresponding column value and returns the new value.
 * @exception MatrixError.DimensionOutOfBounds Thrown when the passed row index is out of bounds.
 */
fun <T : Number> Matrix<T>.transformCol(col: Int, otherCol: Int, transform: (current: T, other: T) -> T) {
    if (col < 0 || col > this.cols - 1 || otherCol < 0 || otherCol > this.cols - 1)
        throw MatrixError.DimensionOutOfBounds()

    for (j in 0 until this.rows) {
        this[j, col] = transform(this[j, col], this[j, otherCol])
    }
}

/**
 * Swaps two rows [row1] and [row2] in place.
 * @param row1 The index of 1st row to swap
 * @param row2 The index of 2nd row to swap
 * @exception MatrixError.DimensionOutOfBounds Thrown when the given row index is out of bounds.
 */
fun <T : Number> Matrix<T>.swapRow(row1: Int, row2: Int) {
    if (row1 < 0 || row1 > this.rows - 1 || row2 < 0 || row2 > this.rows - 1)
        throw MatrixError.DimensionOutOfBounds()

    for (j in 0 until this.rows) {
        val temp = this[row1, j]
        this[row1, j] = this[row2, j]
        this[row2, j] = temp
    }
}

/**
 * Swaps two columns [col1] and [col2] in place.
 * @param col1 The index of 1st column to swap
 * @param col2 The index of 2nd column to swap
 * @exception MatrixError.DimensionOutOfBounds Thrown when the given col index is out of bounds.
 */
fun <T : Number> Matrix<T>.swapCol(col1: Int, col2: Int) {
    if (col1 < 0 || col1 > this.cols - 1 || col2 < 0 || col2 > this.cols - 1)
        throw MatrixError.DimensionOutOfBounds()

    for (i in 0 until this.cols) {
        val temp = this[i, col1]
        this[i, col1] = this[i, col2]
        this[i, col2] = temp
    }
}

/**
 * For finding the row-echelon form of a matrix. The algorithm used is 'handmade', this may not be the best way to find the row-echelon  (or it might be) but it gets the job done.
 * @return A row-echelon [matrix] of type [Double] and of the same size as the original matrix
 */
fun <T : Number> Matrix<T>.rowEchelonOf(): Matrix<Double> {
    val m = this.toDouble()
    //Loop from left most column to right most column
    var lastPivotIndex = 0
    for (i in 0 until m.cols) {

        var currCol = m.columnAt(i)
        //If the pivot element is zero; then swap rows until pivot is longer 0
        if (currCol[lastPivotIndex] == 0.0) {
            var a = lastPivotIndex
            while (a < currCol.size) {
                if (currCol[a] != 0.0) {
                    m.swapRow(lastPivotIndex, a)
                    //update the currCol when the rows are swapped
                    currCol = m.columnAt(i)
                    break
                }
                a++
            }
        }

        //If It's still 0; then skip this column, as it a zero column
        if (currCol[0] == 0.0 || currCol[lastPivotIndex] == 0.0)
            continue

        //Else get the current column value from the last pivot to no of rows.
        currCol = Array(this.rows - lastPivotIndex) { 0.0 }
        //Get the rest of column elements from i; where i-1 is the last pivot element index.
        for (restRow in lastPivotIndex until this.rows) {
            currCol[restRow - lastPivotIndex] = m[restRow, lastPivotIndex]
        }

        //Break out if the size of elements(elements in currCol) that can be changed is = 1 (1 because, the 1 element in currCol is pivot element, it can't be changed)
        if(currCol.size == 1)
            break

        //For elements after the pivot element in the current col (currCol)
        val pivotElement = currCol[0]
        //For making each element under the pivot element 0;
        for(j in 1 until currCol.size) {
            val currElm = currCol[j]
            //if the current element is 0, then skip (if not skipped a 0/0
            if(currElm == 0.0)
                continue

            val ratio = currElm / pivotElement
            /**
             * Transform the current row of `m` using the transformation;
             * Rc -> Rc + nRp
             * Where Rc is the current row (c is the current row number; `c = lastPivotIndex + j`)
             * n is the ratio. (element of Rc/Rp) ie current row value by pivot row value
             * Rp is the pivot row (p is the pivot row number; `p = lastPivotIndex`)
             */
            m.transformRow(j + lastPivotIndex, lastPivotIndex) { a, b ->
                a - (ratio * b)
            }
        }

        lastPivotIndex++
    }

    return m
}

/**
 * For finding the column-echelon form of a matrix.
 * The column-echelon is found by taking the matrix and finding the [transposed] of that matrix, then finding the [rowEchelonOf] of the transposed. Then the result is again transposed and returned.
 * > **Note**: The col-echelon requires more operations, as two transposed operations + the row echelon is carried out. Prefer [rowEchelonOf] over [colEchelonOf]
 * @return A row-echelon [matrix] of type [Double] and of the same size as the original matrix
 */
fun <T: Number> Matrix<T>.colEchelonOf() = this.transposed().rowEchelonOf().transposed()

fun <T: Number> Matrix<T>.rank(): Int {
    val echelon = this.rowEchelonOf()
    var rank = 0
    for(i in 0 until echelon.rows) {
        val row = echelon.rowAt(i)

        for(i in row) {
            if(i != 0.0) {
                rank ++
                break
            }
        }
    }
    return rank
}