package net.multicast

import io.reactivex.disposables.Disposable

class MulticastPeer(val host: String = "230.0.0.0", val port: Int = 5000) {
    val sender = MulticastPublisher(host, port)
    val receiver = MulticastReceiver(host, port)
    private var disp: Disposable? = null

    // envia uma menssagem
    fun send(message: String){
        sender.send(message)
    }

    // escuta menssagens no canal
    fun onMessage(action : (String) -> Any){
        receiver.message.subscribe{
            action(it)
        }
    }

    fun stop(){
        receiver.end()
        disp?.dispose() // unsubscribe
    }
}