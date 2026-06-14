package com.example.prak4.model

import com.google.gson.annotations.SerializedName


data class DataMahasiswaModel(
    @SerializedName("id")
    val id: String?,

    @SerializedName("nama")
    val nama: String?,

    @SerializedName("nim")
    val nim: String?,

    @SerializedName("prodi")
    val prodi: String?,

    @SerializedName("angkatan")
    val angkatan: String?,

    @SerializedName("jkel")
    val jk: String?,

    @SerializedName("hobi")
    val hobi: String?,

    @SerializedName("alamat")
    val alamat: String?,

    @SerializedName("kota")
    val kota: String?,

    @SerializedName("provinsi")
    val provinsi: String?
)
