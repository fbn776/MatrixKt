data class MatrixError(val errorName: String, val hasError: Boolean, val displayText: String) {
    fun throwError() {
        throw Error(displayText)
    }
}

class Matrix(matrix1D: List<Number>, rows: Int, cols: Int) {
    private val matrixSizeError =
        MatrixError("Matrix Size Error", false, "The size of the matrix, doesn't match the rows and columns")


    //    fun map1DTo2D(array1D: IntArray, rows: Int, columns: Int): Array<Array<Int>> {
//        val array2D = Array(rows) { Array(columns) { 0 } }
//
//        for (i in 0 until rows) {
//            for (j in 0 until columns) {
//                array2D[i][j] = array1D[i * columns + j]
//            }
//        }
//
//        return array2D
//    }

    //Create a 2D array that `rows` rows and `cols` columns and fill each cell with 0;
    val matrix2D = Array(rows) { Array(cols) { 0 } }

    init {
        //Check if the size of the matrix values is in accordance with the given rows and cols value;
        if (rows * cols != matrix1D.size) {
            matrixSizeError.throwError()
        }

//        for (i in 1..rows) {
//            val row = mutableListOf<Number>()
//            for (j in 1..cols) {
//                row.add(values[i * j])
//            }
//        }
    }

    //A formatted matrix string;
    override fun toString(): String {
        var str = ""
        for(i in matrix2D) {
            var str_temp = ""
            for(cell in i) {
                str_temp += "$cell "
            }
            str += "$str_temp\n"
        }
        return str
    }
}