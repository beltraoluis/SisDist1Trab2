package net.udp

import java.io.IOException
import java.net.SocketException
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.DatagramSocket


object UDPClient {
    @JvmStatic
    fun main(args: Array<String>) {
        // args give message contents and destination hostname
        var aSocket: DatagramSocket? = null
        try {
            aSocket = DatagramSocket()
            val m = args[0].toByteArray()
            val aHost = InetAddress.getByName(args[1])
            //InetAddress aHost = InetAddress.getByName("192.168.106.227");
            val serverPort = 6789
            val request = DatagramPacket(m, args[0].length, aHost, serverPort)
            aSocket.send(request)
            val buffer = ByteArray(1000)
            val reply = DatagramPacket(buffer, buffer.size)
            aSocket.soTimeout = 5000
            aSocket.receive(reply)
            println("Reply: " + String(reply.data))
        } catch (e: SocketException) {
            println("Socket: " + e.message)
        } catch (e: IOException) {
            println("IO: " + e.message)
        } finally {
            aSocket?.close()
        }
    }
}