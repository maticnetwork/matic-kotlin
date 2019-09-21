package network.matic.sdk.model

import com.google.gson.annotations.SerializedName

data class WatcherModel(

    @SerializedName("id")
    val hash: String,
    @SerializedName("number")
    val number: String,
    @SerializedName("start")
    val start: String,
    @SerializedName("end")
    val end: String,
    @SerializedName("proposer")
    val proposer: String,
    @SerializedName("root")
    val root: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)