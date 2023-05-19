/**An error container class that contains all the Errors the library can throw out.
 * @property SizeError An error that is thrown when the size of the matrix does not match the row and column count. For eg: This is thrown when a 3x3 matrix is initialized with a 4x4 array.
 * @property IndexOutOfBound An error that is thrown when the index given is out of bounds. For eg: This is thrown when Matrix.get(4,4) is called on a 3x3 or lower matrix.
 * @property NotSameSize An Error that is thrown when the matrices are not of same size. For eg: This is thrown when addition is performed on a 2x3 and 3x2 matrix.
 * @property MultiplicationDimensionError An error that is thrown when the number of columns of 1st matrix doesn't match number of rows of 2nd matrix. For eg: This is thrown when matrix multiplication is performed on a 2x3 and 2x3 matrix.
 * @property NotSquare An error that is thrown when the matrix is not square. For eg: This is thrown when inverse() is called on a non-square matrix.
 * @property SizeTooSmall An error that is thrown when the matrix is too small to perform an operation. For eg: This is thrown when subSqMatrix() is called on a 1x1 matrix. It can't be divided further.
 * @property DimensionOutOfBounds An error that is thrown when the dimension(row, column) given is out of bounds. For eg: This is thrown when subSqMatrix(4,4) is called on a 3x3 or lower matrix.
 * */
sealed class MatrixError {
    class SizeError(msg: String = "The size of the matrix does not match the row and column count.") :
        Exception(msg)

    class IndexOutOfBound(msg: String = "Tried to access a value with index [i, j] that's outside of the index bounds") :
        Exception(msg)

    class NotSameSize(msg: String = "The matrices need to be of same size. The row and column count of the matrices should be same") :
        Exception(msg)

    class MultiplicationDimensionError(msg: String = "The number of columns in first matrix must be equal to the number of rows in second matrix for matrix multiplication.") :
        Exception(msg)

    class NotSquare(msg: String = "The matrix must be square for this operation.") :
        Exception(msg)

    class SizeTooSmall(msg: String = "The matrix size is too small") :
        Exception(msg)

    class DimensionOutOfBounds(msg: String = "The dimension given is out of bounds") :
        Exception(msg)
}
