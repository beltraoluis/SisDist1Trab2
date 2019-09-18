package net.tcp

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.EOFException
import java.io.IOException
import java.net.Socket
import java.net.UnknownHostException

object TCPClient {
    @JvmStatic
    fun main(args: Array<String>) {
        // arguments supply message and hostname
        var s: Socket? = null
        try {
            val serverPort = 7896
            s = Socket(args[1], serverPort)
            val `in` = DataInputStream(s.getInputStream())
            val out = DataOutputStream(s.getOutputStream())
            out.writeUTF(args[0])        // UTF is a string encoding see Sn. 4.4
            val data = `in`.readUTF()        // read a line of data from the stream
            println("Received: $data")
        } catch (e: UnknownHostException) {
            println("Socket:" + e.message)
        } catch (e: EOFException) {
            println("EOF:" + e.message)
        } catch (e: IOException) {
            println("readline:" + e.message)
        } finally {
            if (s != null) try {
                s.close()
            } catch (e: IOException) {
                println("close:" + e.message)
            }

        }
    }
}