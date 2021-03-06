package network.matic.sdk.model

import com.google.gson.annotations.SerializedName

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