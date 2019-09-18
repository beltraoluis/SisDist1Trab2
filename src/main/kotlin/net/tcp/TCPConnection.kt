package net.tcp

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.EOFException
import java.io.IOException
import java.net.Socket

class TCPConnection(aClientSocket: Socket) : Thread() {
    private lateinit var dataInput: DataInputStream
    private lateinit var dataOutput: DataOutputStream
    private lateinit var clientSocket: Socket

    init {
        try {
            clientSocket = aClientSocket
            dataInput = DataInputStream(clientSocket.getInputStream())
            dataOutput = DataOutputStream(clientSocket.getOutputStream())
            this.start()
        } catch (e: IOException) {
            println("Connection:" + e.message)
        }

    }

    override fun run() {
        try {                             // an echo server

            val data = dataInput.readUTF()                      // read a line of data from the stream
            dataOutput.writeUTF(data)
        } catch (e: EOFException) {
            println("EOF:" + e.message)
        } catch (e: IOException) {
            println("readline:" + e.message)
        } finally {
            try {
                clientSocket.close()
            } catch (e: IOException) {/*close failed*/
            }

        }


    }
}