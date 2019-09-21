package network.matic.sdk.model

import com.google.gson.annotations.SerializedName

data class TxProofModel(
    @SerializedName("txId") val hash: String,
    @SerializedName("proof") val proof: Proof
)

