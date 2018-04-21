package com.kevin.fp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Lazysoul 님 Kotlin with FP 강의자료 보고 정리
 * https://medium.com/@lazysoul/kotlin-with-fp-2aae6f64016f
 */
class MainActivity : AppCompatActivity() {

    /**
     * First-class citizen
     * - 객체의 인자로 전달할 수 있어야 한다.
     * - 객체의 반환값으로 반환할 수 있어야 한다.
     * - 자료구조에 넣을 수 있어야 한다.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        function({ println("Hello World") })

        val value = function()
    }

    /**
     * 객체의 인자로 전달할 수 있어야 한다.
     */
    fun function(param:() -> Unit) {
        param()
    }

    /**
     * 객체의 반환값으로 반환할 수 있어야 한다.
     */
    fun function(): () -> Unit {
        return { println("Hello World") }
    }

    /**
     *
     */
    fun pureFunction(str: String): String = str + "Test"

    private val builder: StringBuilder = StringBuilder()

    fun nonePureFunction(str: String): String = str + builder.append(str).toString()

    /**
     *
     */
    private fun simpleHighOrderFunction(func: (Int, Int) -> Int, a: Int, b: Int ): Int{
        return func(a,b)
    }

    private val sum: (Int, Int) -> Int = {a, b -> a + b}

    private val multiply: (Int, Int) -> Int = {a, b -> a * b}

    private val subtract: (Int, Int) -> Int = {a, b -> a - b}

    fun simple() {
        simpleHighOrderFunction(sum, 10, 20) // 30
        simpleHighOrderFunction(multiply, 10, 20) // 200
        simpleHighOrderFunction(subtract, 10, 20) // -10

        simpleHighOrderFunction({a, b -> 2 * (a + b)}, 10, 20) // 60

        callPrinter()
        returnPrinter() // nothing
        
        // Functional Programming
        returnPrinter().invoke()
        returnPrinter()()
    }

    /**
     *
     */
    private val printer: () -> Unit = { println("Functional Programming") }

    fun callPrinter() {
        return printer()
    }

    fun returnPrinter(): () -> Unit {
        return printer
    }
}
