# MatrixKt

## Introduction

MatrixKt is a maths library that enables the use of matrices and matrix operations in Kotlin.
The documentations are available at [Docs.md](./Docs.md). 

		This is a hobby project and needs some more documentations. The code is almost complete, but lacks documentations

## Features

This library supports almost all the basics matrix operations like: <br>
`Addition`, `Subtraction`, `Multiplication`, `Scalar Multiplication`,` Negation`, `Transpose`, `Determinant`, `Inverse`, `Minors`, `Cofactors`, `Adjoint`, `Echelon forms`, `Rank`

The library also provide util function like:

`Individual row/column getters`, `Row/column transforms`, `Row/column swapping`, `Iterators`, `forEach`, `forEachIndexed` etc...

## Basic Usage

All the matrix operations are done using the `Matrix` class.
To create a matrix, use the following code;
```kotlin
import matrix.Matrix

val A = Matrix(arrayOf(
	1,2,
	3,4
), 2, 2)
```
This creates a 2x2 matrix.

### Accessing elements

For accessing the element at `(i,j)` use `A[i,j]`<br>
Where `A` is a Matrix.

	NOTE: indices start from 0.

The matrix generated is also mutable. You can update/set value at `(i,j)` by using
`A[i,j] = b`<br>
Where `A` is a Matrix instance, 
`b` is `Number`

## Basic Operations

You add, subtract, cross multiply two matrices. You can use `+`, `-` and `*` for the operations.
Example:
```kotlin
import matrix.Matrix
import matrix.minus
import matrix.plus
import matrix.times

val A = Matrix(arrayOf(
    1,2,3,
    4,5,6,
    7,8,9
), 3, 3)
val B = Matrix(arrayOf(
    4,3,1,
    8,9,4,
    1,3,5
), 3, 3)

//Addition
val C = A + B
//Subraction
val D = A - B
//Multiplication
val E = A * B
```
Here `C` holds the matrix sum of `A` and `B`<br>
`D` holds the matrix subtraction of `A` and `B`<br>
`E` holds the matrix cross product between `A` and `B`

Operations are as simple as that!

You can also do stuff like finding the transpose, minor matrix, cofactor, adjoint, determinant, row and column transforms, rank etc.

For the full docs read [here](./Docs.md)

## TODO

- [ ] Documentation
- [ ] Testings
- [ ] Publications
