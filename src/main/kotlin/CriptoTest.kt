import java.io.ByteArrayOutputStream
import org.bouncycastle.util.io.Streams
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.BouncyGPG
import java.io.BufferedOutputStream
import java.io.ByteArrayInputStream
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.keyrings.KeyringConfigs
import security.keys.KeyHelper





fun main(args : Array<String>) {
    val original_message = "I love deadlines. I like the whooshing sound they make as they fly by. Douglas Adams"

    // Most likely you will use  one of the KeyringConfigs.... methods.
    // These are wrappers for the test.
    val keyringConfigOfSender = KeyringConfigs
        .withKeyRingsFromFiles(KeyHelper.publicKey(1),KeyHelper.privateKey(1)){
            "".toCharArray()
        }

    val result = ByteArrayOutputStream()

    BufferedOutputStream(result, 16384 * 1024).use { bufferedOutputStream ->
        BouncyGPG
            .encryptToStream()
            .withConfig(keyringConfigOfSender)
            .withStrongAlgorithms()
            .toRecipients("sd1a@beltraoluis.com", "sd1a@beltraoluis.com")
            .andSignWith("sd1a@beltraoluis.com")
            .binaryOutput()
            .andWriteTo(bufferedOutputStream).use { outputStream ->
                ByteArrayInputStream(original_message.toByteArray()).use { `is` ->
                    Streams.pipeAll(`is`, outputStream)
                    // It is very important that outputStream is closed before the result stream is read.
                    // The reason is that GPG writes the signature at the end of the stream.
                    // This is triggered by closing the stream.
                    // In this example outputStream is closed via the try-with-resources mechanism of Java
                }
            }
    }// Maybe read a file or a webservice?

    result.close()
    val chipertext = result.toByteArray()

    //////// Now decrypt the stream and check the signature

    // Most likely you will use  one of the KeyringConfigs.... methods.
    // These are wrappers for the test.
    val keyringConfigOfRecipient = KeyringConfigs
        .withKeyRingsFromFiles(KeyHelper.publicKey(1),KeyHelper.privateKey(1)){
            "".toCharArray()
        }

    val output = ByteArrayOutputStream()
    ByteArrayInputStream(chipertext).use { cipherTextStream ->
        BufferedOutputStream(output).use { bufferedOut ->
            BouncyGPG
                .decryptAndVerifyStream()
                .withConfig(keyringConfigOfRecipient)
                .andRequireSignatureFromAllKeys("sender@example.com")
                .fromEncryptedInputStream(cipherTextStream)
                .use { plaintextStream -> Streams.pipeAll(plaintextStream, bufferedOut) }
        }
    }

    output.close()
    val decrypted_message = String(output.toByteArray())
    print(decrypted_message)
}





