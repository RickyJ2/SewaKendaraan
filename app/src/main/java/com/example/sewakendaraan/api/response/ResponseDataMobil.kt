package com.example.sewakendaraan.api.response

import com.example.sewakendaraan.data.DaftarMobil
import com.google.gson.annotations.SerializedName

data class ResponseDataMobil(
    @SerializedName("status") val stt:String,
    @SerializedName("error") val e:Boolean,
    @SerializedName("message") val msg:String,
    val totalData: Int,
    val data: DaftarMobil
)
