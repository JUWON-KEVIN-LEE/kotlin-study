package com.kevin.dpkotlin

/**
 * The command pattern is used to express a request,
 * including the call to be made and all of its required parameters, in a command object.
 * The command may then be executed immediately or held for later use.
 * Created by quf93 on 2018-04-24.
 */
interface OrderCommand {
    fun execute()
}

class OrderAddCommand(val id: Long): OrderCommand {
    override fun execute() = println("adding order with id: $id")
}

class OrderPayCommand(val id: Long): OrderCommand {
    override fun execute() = println("paying for order with id: $id")
}

class CommandProcessor {
    private val queue = ArrayList<OrderCommand>()

    fun addToQueue(command: OrderCommand): CommandProcessor =
            apply { queue.add(command) }

    fun processCommands(): CommandProcessor =
            apply {
                queue.forEach {it.execute()}
                queue.clear()
            }
}