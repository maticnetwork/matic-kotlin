package network.matic.sdk.model

import com.google.gson.annotations.SerializedName

data class Proof(
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("blockNumber") val blockNumber: String,
    @SerializedName("blockHash") val blockHash: String,
    @SerializedName("parentNodes") val parentNodes: String,
    @SerializedName("root") val root: String,
    @SerializedName("path") val path: String,
    @SerializedName("value") val value: String,
    @SerializedName("blockTimestamp") val blockTimestamp: String
)