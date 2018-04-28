package com.kevin.dpkotlin

/**
 * The strategy pattern is used to create an interchangeable family of algorithms
 * from which the required process is chosen at run-time.
 * Created by quf93 on 2018-04-24.
 */
class Printer(private val strategy:(String) -> String) {
    fun print(string: String) = println(strategy(string))
}

val lowerCaseFormatter: (String) -> String = {it.toLowerCase()}

val upperCaseFormatter = {it: String -> it.toUpperCase()}

fun main(args: Array<String>){
    val text = "Hello Kotlin !"

    val lowerCasePrinter = Printer(lowerCaseFormatter)
    lowerCasePrinter.print(text)

    val upperCasePrinter = Printer(upperCaseFormatter)
    upperCasePrinter.print(text)

    val prefixCasePrinter = Printer({"prefix: $it"})
    prefixCasePrinter.print(text)
}