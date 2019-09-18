import net.PhaseKingProcess

fun main(args : Array<String>) {
    val process = mutableListOf<PhaseKingProcess>()
    for(i in 1..5){
        process.add(PhaseKingProcess(id = i, n = 5))
    }
    process.forEach { it.start() }
}