package network.matic.matick.model

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type


internal class MyDeserializer : JsonDeserializer<TransactionModel> {
    @Throws(JsonParseException::class)
    override fun deserialize(je: JsonElement, type: Type, jdc: JsonDeserializationContext): TransactionModel {
        // Get the "content" element from the parsed JSON
        val content = je.asJsonObject.get("tx")
        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        return Gson().fromJson<TransactionModel>(content, TransactionModel::class.java)

    }
}
data class TransactionModel(
    @SerializedName("txId") val id: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("tx.hash") val hash: String,
    @SerializedName("blockNumber") val blockHash: String,
    @SerializedName("blockHash") val paletteColor: String,
    @SerializedName("from") val from: String,
    @SerializedName("to") val to: String,
    @SerializedName("gas") val gas: String,
    @SerializedName("gasPrice") val gasPrice: String,
    @SerializedName("input") val input: String,
    @SerializedName("nonce") val nonce: String
    )