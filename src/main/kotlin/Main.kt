import net.PhaseKingProcess

/**
 * Classe principal do programa
 *
 * Cria uma lista de 5 processos cada um com a respectiva id
 *
 */

fun main(args : Array<String>) {
   
    //Cria a lista de processos
    val process = mutableListOf<PhaseKingProcess>()
    for(i in 1..5){
        process.add(PhaseKingProcess(id = i, n = 5))
    }

    //Para cada processo da lista, chama a sua função start
    process.forEach { it.start() }
}