/**An error container class that contains all the Errors the library can throw out.
 * @property SizeError An error that is thrown when the size of the matrix does not match the row and column count. For eg: This is thrown when a 3x3 matrix is initialized with a 4x4 array.
 * @property IndexOutOfBound An error that is thrown when the index given is out of bounds. For eg: This is thrown when Matrix.get(4,4) is called on a 3x3 or lower matrix.
 * @property NotSameSizeMatrix An Error that is thrown when the matrices are not of same size. For eg: This is thrown when addition is performed on a 2x3 and 3x2 matrix.
 * @property MultiplicationDimensionError An error that is thrown when the number of columns of 1st matrix doesn't match number of rows of 2nd matrix. For eg: This is thrown when matrix multiplication is performed on a 2x3 and 2x3 matrix.
 * @property NotSquareMatrix An error that is thrown when the matrix is not square. For eg: This is thrown when inverse() is called on a non-square matrix.
 * @property SizeTooSmall An error that is thrown when the matrix is too small to perform an operation. For eg: This is thrown when subSqMatrix() is called on a 1x1 matrix. It can't be divided further.
 * @property DimensionOutOfBounds An error that is thrown when the dimension(row, column) given is out of bounds. For eg: This is thrown when subSqMatrix(4,4) is called on a 3x3 or lower matrix.
 * @property NoSuchElement An error that is thrown when there is no next element to access. For eg: This is thrown when next() is called on an empty matrix.
 * @property SizeInvalid An error that is thrown when the size of the matrix is invalid. For eg: This is thrown when a 0x3 matrix is initialized or -1×3 matrix is initialized.
 * */
sealed class MatrixError {
    class SizeError(msg: String = "The size of the matrix does not match the row and column count. ", note: String = "") :
        Exception(formatMessage(msg, note))

    class IndexOutOfBound(msg: String = "Tried to access a value with index that's outside of the index bounds", note: String = "") :
        Exception(formatMessage(msg, note))

    class NotSameSizeMatrix(msg: String = "The matrices need to be of same size. The row and column count of the matrices should be same", note: String = "") :
        Exception(formatMessage(msg, note))

    class MultiplicationDimensionError(msg: String = "The number of columns in first matrix must be equal to the number of rows in second matrix for matrix multiplication.", note: String = "") :
        Exception(formatMessage(msg, note))

    class NotSquareMatrix(msg: String = "The matrix must be square for this operation.", note: String = "") :
        Exception(formatMessage(msg, note))

    class SizeTooSmall(msg: String = "The matrix size is too small", note: String = "") :
        Exception(formatMessage(msg, note))

    class DimensionOutOfBounds(msg: String = "The dimension given is out of bounds;", note: String = "") :
        Exception(formatMessage(msg, note))

    class NoSuchElement(msg: String = "Unable to access next element as there is no next element", note: String = "") :
        Exception(formatMessage(msg, note))

    class SizeInvalid(msg: String = "The size of the matrix is invalid", note: String = "") :
        Exception(formatMessage(msg, note))
}


private fun formatMessage(msg: String, note: String): String {
    return msg + (if(note != "") "\nNote: $note" else "")
}