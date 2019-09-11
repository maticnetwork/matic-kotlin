package network.matic.matick.model

import com.google.gson.annotations.SerializedName

data class HeaderProof (
    @SerializedName("headerRoot")
    val headerRoot: String,
    @SerializedName("value")
    val value: String,
    @SerializedName("valueIndex")
    val valueIndex: String,
    @SerializedName("proof")
    val proof: Array<String>
    )
