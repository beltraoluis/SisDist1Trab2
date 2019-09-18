package net

import net.multicast.MulticastPeer
import kotlin.concurrent.thread
import kotlin.math.floor
import kotlin.random.Random

class PhaseKingProcess(val id: Int, val n: Int = 5) {
    private val f = (floor(n/4.0)).toInt()
    //grupo de broadcast geral
    val group = MulticastPeer(port = 4000)
    // broadcast do rei
    val king = MulticastPeer(port = 5000)
    val value = mutableListOf<Int>()
    var tiebreaker: Int? = null

    init {
        // chamadas assincronas
        group.onMessage {
            value.add(it.toInt())
        }
        king.onMessage {
            tiebreaker = it.toInt()
            true // serve apenas para atender o retorno
        }
    }

    fun start(){
        // gera o valor aleatoriamente
        var v = Random(System.currentTimeMillis()).nextInt(0, 2)
        thread {
            for (phase in 1..(f+1).toInt()) {
                // actions in round one of each phase
                group.send(v.toString())
                while (value.size < 5) {
                    Thread.sleep(100L)
                }
                // retorna 1 se mais da metade for 1 e 0 caso contrario
                val soma = value.sum()
                val majority = if (soma >= floor(n / 2.0) + 1) {
                    1
                } else {
                    0
                }
                // calcula as ocorrencias
                val mult = when (majority) {
                    1 -> soma
                    else -> n - soma
                }
                // actions in round two of each phase
                if (id == phase) king.send(majority.toString()) // se for o rei transmite
                if (mult > n / 2 + f) {
                    v = majority
                } else {
                    while (tiebreaker == null) Thread.sleep(100L) // espera o rei responder
                    v = tiebreaker!!
                }
                if (phase == f + 1) {
                    print("decision$id is $v\n")
                    group.stop()
                    king.stop()
                }
            }
        }
    }

}