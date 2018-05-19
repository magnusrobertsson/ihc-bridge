package com.jayway.ihc.dummy

import java.awt.EventQueue
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.WindowConstants

class Light {
}

fun create() = Runnable {
    val frame = JFrame("DummyLight")
    frame.setSize(300, 200)
//    frame.setBounds(200, 100, 100, 100)
    frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

    val button = JButton("Click me")

    frame.add(button)
    frame.isVisible = true
    frame.pack()
}

/*
fun main(args: Array<String>) {
    EventQueue.invokeLater({
        create()
    })
    Thread.sleep(30000)
    println("DONE")
}
*/