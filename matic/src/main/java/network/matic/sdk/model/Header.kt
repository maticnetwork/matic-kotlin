package network.matic.sdk.model

import com.google.gson.annotations.SerializedName

data class Header (
    @SerializedName("blockNumber")
    val blockNumber: String,
    @SerializedName("start")
    val start: String,
    @SerializedName("end")
    val end: String,
    @SerializedName("proof")
    val proof: HeaderProof
    )