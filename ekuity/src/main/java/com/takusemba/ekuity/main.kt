package com.takusemba.ekuity

class Skreen : CliktCommand() {

    override fun run() {
        context.obj = "hogehoge"
    }
}

fun main(args: Array<String>) = Skreen()
    .subcommands(Shot(), Record())
    .main(args)

