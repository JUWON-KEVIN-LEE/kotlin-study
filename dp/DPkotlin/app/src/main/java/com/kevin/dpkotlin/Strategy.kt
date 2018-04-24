package com.kevin.dpkotlin

/**
 * Created by quf93 on 2018-04-24.
 */
class Printer(val strategy:(String) -> String) {
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