import io.reactivex.internal.operators.single.SingleDoOnSuccess
import kotlinx.coroutines.sync.Mutex
import java.sql.Timestamp
import java.util.UUID
import kotlin.random.Random


class Transaction(val mutex: Mutex, val id: UUID = UUID(Random.nextLong(), Random.nextLong())) {
    // sealed class é um enum melhorado
    sealed class State(val value: String) {
        class Active: State("active")
        class PartiallyCommitted: State("partially_committed")
        class Committed: State( "committed")
        class Aborted: State("aborted")
    }
    var state: State = State.Active()
        set(value){
            registerLog("State_change:${value.value}")
            field = value
        }
    val logs = hashMapOf<Int, String>()
    var index = 0
    var doctor: TimeFrame? = null
    var anesthetist: TimeFrame? = null

    fun start() = id.toString()

    fun checkDoctor(startTime: Timestamp, endTime: Timestamp): State{
        when(state){
            is State.PartiallyCommitted, is State.Active -> {
                state = State.PartiallyCommitted()
                //todo Retrofit calls
                logs[index++] = "check_doctor:${startTime.time}:${endTime.time}"
            }
            else -> {}
        }
        return state
    }

    fun checkAnesthetist(startTime: Timestamp, endTime: Timestamp): State{
        when(state){
            is State.PartiallyCommitted, is State.Active -> {
                state = State.PartiallyCommitted()
                //todo Retrofit calls
                logs[index++] = "check_anesthetist:${startTime.time}:${endTime.time}"
            }
            else -> {}
        }
        return state
    }

    private fun abort(){
        state
    }

    fun commit(onSuccess: () -> Unit = {}): State{
        if(state !is State.Aborted && doctor != null && anesthetist != null){
            //todo Retrofit calls
            onSuccess()
            return State.Committed()
        }
        return State.Aborted()
    }

    private fun registerLog(line: String){
        // usar ":" como delimitador
        // todo: guardar logs com shared preferences de modo que o id seja a key
    }

}