package net.tcp

import java.io.IOException
import java.net.ServerSocket

object TCPServer {
    @JvmStatic
    fun main(args: Array<String>) {
        try {
            val serverPort = 7896 // the server port
            val listenSocket = ServerSocket(serverPort)
            while (true) {
                val clientSocket = listenSocket.accept()
                val c = TCPConnection(clientSocket)
            }
        } catch (e: IOException) {
            println("Listen socket:" + e.message)
        }

    }
}