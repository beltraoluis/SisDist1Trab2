package security.keys

import java.io.File

object KeyHelper {
    const val pub = "src/main/kotlin/security/keys/pub"
    const val priv = "src/main/kotlin/security/keys/priv"
    // list of public private key
    val keys = mutableListOf<Pair<String,String>>(
        "$pub/36A3EC4579282AD7CEDBDE0669FCCE5C5632CDEF.asc" to "$priv/36A3EC4579282AD7CEDBDE0669FCCE5C5632CDEF.asc",
        "$pub/0183D66BB173E0B092F6FA9AF99621155584530B.asc" to "$priv/0183D66BB173E0B092F6FA9AF99621155584530B.asc",
        "$pub/D1885AD7FCE1401A517CD1ADB85B18D25C79A899.asc" to "$priv/D1885AD7FCE1401A517CD1ADB85B18D25C79A899.asc",
        "$pub/DAF6C85ECF2EF1A21DC83087A4F6B8964808042C.asc" to "$priv/DAF6C85ECF2EF1A21DC83087A4F6B8964808042C.asc",
        "$pub/FF3F49C3690989340578979B554B200D6B2942CD.asc" to "$priv/FF3F49C3690989340578979B554B200D6B2942CD.asc"
    )

    fun publicKey(id: Int): File {
        return when (id) {
            in 1..5 -> File(keys[id].first)
            else -> File(keys[5].first)
        }
    }

    fun privateKey(id: Int): File {
        return when (id) {
            in 1..5 -> File(keys[id].second)
            else -> File(keys[5].second)
        }
    }
}