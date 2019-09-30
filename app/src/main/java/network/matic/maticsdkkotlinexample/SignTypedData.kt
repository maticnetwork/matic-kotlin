package network.matic.maticsdkkotlinexample

import network.matic.sdk.Matic
import java.io.BufferedReader

object SignTypedData {
  fun signTypedDataExample() {
    val maticInstance = Matic(TestNet1())
    maticInstance.setWallet(ConfigTest.PRIVATE_KEY)
    val jsonFilepath = "signTypedLogin.json"
    val loader = Thread.currentThread().contextClassLoader
    val datastream = loader.getResourceAsStream(jsonFilepath)

    val reader = BufferedReader(datastream!!.reader())
    var content: String
    try {
      content = reader.readText()
    } finally {
      reader.close()
    }
    val signedData = maticInstance.signTypedData(content)
    println("signedData : $signedData")
  }
}