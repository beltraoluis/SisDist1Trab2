package net.multicast

import io.reactivex.subjects.PublishSubject
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket


class MulticastReceiver(val host: String = "230.0.0.0", val port: Int = 5000) : Thread() {
    val group = InetAddress.getByName(host)
    var socket  = MulticastSocket(port)
    var buf = ByteArray(256)
    var message = PublishSubject.create<String>()
    var running = true

    init {
        start()
    }

    fun end(){
        running = false
    }

    override fun run() {
        socket.joinGroup(group)
        while (running) {
            try {
                val packet = DatagramPacket(buf, buf.size)
                socket.receive(packet)
                val received = String(
                    packet.data, 0, packet.length
                )
                if(received.isNotBlank()){
                    message.onNext(received)
                }
            }catch (e: Exception){}
        }
        socket.leaveGroup(group)
        socket.close()
    }
}