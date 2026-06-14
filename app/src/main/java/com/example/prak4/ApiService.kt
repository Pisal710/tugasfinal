package com.example.prak4

import com.example.prak4.model.DataMahasiswaModel
import com.example.prak4.model.ResponsModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Interface untuk mendefinisikan endpoint API Retrofit.
 * Menambahkan fungsi update dan delete agar perubahan di aplikasi langsung merubah database.
 */
interface ApiService {
    
    // Fungsi untuk mengambil semua data mahasiswa dari database (READ)
    @GET("read.php")
    fun getMahasiswa(): Call<List<DataMahasiswaModel>>

    // Fungsi untuk menambah data mahasiswa baru (CREATE)
    @FormUrlEncoded
    @POST("insert.php")
    fun simpanMahasiswa(
        @Field("nama") nama: String,
        @Field("nim") nim: String,
        @Field("prodi") prodi: String,
        @Field("angkatan") angkatan: String,
        @Field("jkel") jk: String,
        @Field("hobi") hobi: String,
        @Field("alamat") alamat: String,
        @Field("kota") kota: String,
        @Field("provinsi") provinsi: String
    ): Call<ResponsModel>

    // Fungsi untuk memperbarui data mahasiswa berdasarkan ID (UPDATE)
    @FormUrlEncoded
    @POST("update.php")
    fun updateMahasiswa(
        @Field("id") id: String,
        @Field("nama") nama: String,
        @Field("nim") nim: String,
        @Field("prodi") prodi: String,
        @Field("angkatan") angkatan: String
    ): Call<ResponsModel>

    // Fungsi untuk menghapus data mahasiswa berdasarkan ID (DELETE)
    @FormUrlEncoded
    @POST("delete.php")
    fun deleteMahasiswa(
        @Field("id") id: String
    ): Call<ResponsModel>
}
