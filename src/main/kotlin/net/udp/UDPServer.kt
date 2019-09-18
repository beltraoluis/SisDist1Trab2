package net.udp

import java.io.IOException
import java.net.SocketException
import java.net.DatagramPacket
import java.net.DatagramSocket


object UDPServer {
    @JvmStatic
    fun main(args: Array<String>) {
        var aSocket: DatagramSocket? = null
        try {
            aSocket = DatagramSocket(6789)
            // create socket at agreed port
            val buffer = ByteArray(1000)
            while (true) {
                val request = DatagramPacket(buffer, buffer.size)
                aSocket.receive(request)
                val reply = DatagramPacket(
                    request.data, request.length,
                    request.address, request.port
                )
                aSocket.send(reply)
            }
        } catch (e: SocketException) {
            println("Socket: " + e.message)
        } catch (e: IOException) {
            println("IO: " + e.message)
        } finally {
            aSocket?.close()
        }
    }
}