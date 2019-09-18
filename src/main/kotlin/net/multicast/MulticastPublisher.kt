package net.multicast

import io.reactivex.Single
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.DatagramSocket
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class MulticastPublisher(val host: String = "230.0.0.0", val port: Int = 5000) {
    var group: InetAddress = InetAddress.getByName(host)

    @Throws(IOException::class)
    fun send(multicastMessage: String) {
        try {
            val socket: DatagramSocket = DatagramSocket()
            val buf = multicastMessage.toByteArray()
            val packet = DatagramPacket(buf, buf.size, group, port)
            socket.send(packet)
            socket.close()
        }catch (e: Exception){
            // em caso de colisão espera um tempo aleatorio e retransmite
            val time = Random(System.currentTimeMillis()).nextLong(100, 501)
            Single.just(Unit).delay(time, TimeUnit.MILLISECONDS)
            send(multicastMessage)
        }
    }
}