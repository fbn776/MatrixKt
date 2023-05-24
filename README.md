# MatrixKt

### Introduction
MatrixKt is a Kotlin library for matrix operations. This is an effort to learn Kotlin and to implement some of the matrix operations in Kotlin.
I learned a lot by creating this. I learned about generic classes, operator overloading, extension functions, lambdas and lot more.

This is well documented code. Every method are documented using [KDocs](https://kotlinlang.org/docs/kotlin-doc.html).

### Features
This library supports almost all the basics matrix operations. The following are the list of operations supported by this library;
* Addition
* Subtraction
* Multiplication
* Scalar Multiplication
* Negation
* Transpose
* Determinant
* Inverse
* Minors
* Cofactors
* Adjoint
* Echelon forms
* Rank

If there isnt an operation you want the library also provides stuff like:
* Individual row/column getters
* Row/column transforms
* Row/column swapping
* Iterators
* forEach, forEachIndexed etc.
* And more!

### File Structure
The main matrix code is in
```
src
|---main
    |---kotlin
        |---Matrix.kt
        |---MatrixOperatators.kt
        |---MatrixUtils.kt
        |---MatrixError.kt
        |---Test.kt
```
`Matrix.kt` is the main file. It contains the actual matrix class.<br>
`MatrixOperatators.kt` contains the matrix operators code.<br>
`MatrixUtils.kt` contains the utility functions to support the matrix class.<br>
`MatrixError.kt` contains all the errors that the Matrix class can throw.<br>
`Test.kt` this is a temporary file, used for some minor testing.<br>

### Basic Usage
All the matrix operations are done using the `Matrix` class. To create a matrix you can use the following;
```kotlin
val matrix1 = Matrix(arrayOf(1,2,3,4), 2, 2)
```
This creates a 2x2 matrix of the form;
```
1 2
3 4
```
The `Matrix` class takes in 3 parameters, all of which are required.<br>
The first parameter is the values/elements of matrix itself. This is a 1D uniform `Array` with size of `rows * cols`<br>
The second is the number of rows of the matrix.<br>
The third is the number of columns of the matrix.<br>

    Note: If the no of rows and cols doesn't match the size of the array. An error is thrown

For getting values like the number of rows, columns, size, element at (i,j) etc. Use;
```kotlin
//Number of rows of the matrix
val rows = matrix1.rows //2
//Number of columns of the matrix
val cols = matrix1.cols //2
//The size/no of elements in the matrix
val size = matrix1.size //4
//The element at (1,1)
val a11 = matrix1[1,1] //4
```

    note: The matrix index start (i,j) starts at (0,0) and extends to (rows-1, cols-1)

The matrix elements can be accessed by using i,j pair. ie element at (i,j) is `matrix1[i, j]`
For setting a values, use the same;
```kotlin
//Set the element at (1,1) to 10
matrix1[1, 1] = 7
println(matrix1)
/*Prints:
1 2
3 7
*/
```
    Note: Matrix are mutable.

### Basic Operations
For addition use `+`, for subtraction use `-`, for multiplication use `/`
Example:
```kotlin
val m1 = Matrix(arrayOf(1,2,3,4), 2, 2)
val m2 = Matrix(arrayOf(5,6,7,8), 2, 2)

val add = m1 + m2
val sub = m1 - m2
val mult = m1 * m3
val scalarMult = 2 * m1
val scalarDiv = m1 / 2
```

Operations are as simple as this!



