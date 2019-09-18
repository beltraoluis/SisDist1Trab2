import net.PhaseKingProcess

val process = mutableListOf<PhaseKingProcess>()
for(i in 1..5){
    // não encontra a definição da classe
    process.add(PhaseKingProcess(id = i, n = 5))
}
process.forEach { it.start() }