package com.example.prak4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prak4.model.DataMahasiswaModel

/**
 * Adapter untuk menghubungkan data dari model [DataMahasiswaModel] ke tampilan [item_mahasiswa.xml]
 * di dalam RecyclerView. Mendukung aksi klik untuk edit dan hapus data.
 */
class MahasiswaAdapter(
    private var listMahasiswa: List<DataMahasiswaModel>,
    private val onEditClick: (DataMahasiswaModel) -> Unit,
    private val onDeleteClick: (DataMahasiswaModel) -> Unit
) : RecyclerView.Adapter<MahasiswaAdapter.ViewHolder>() {

    /**
     * ViewHolder untuk menyimpan referensi view dari item_mahasiswa.xml agar tidak dipanggil berulang kali.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tv_item_nama)
        val tvNim: TextView = view.findViewById(R.id.tv_item_nim)
        val tvProdi: TextView = view.findViewById(R.id.tv_item_prodi)
        val tvAngkatan: TextView = view.findViewById(R.id.tv_item_angkatan)
        val btnEdit: ImageView = view.findViewById(R.id.btn_item_edit)
        val btnDelete: ImageView = view.findViewById(R.id.btn_item_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Menginflate layout item_mahasiswa ke dalam View
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mahasiswa, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listMahasiswa[position]
        
        // Memasukkan data mahasiswa ke tampilan TextView
        holder.tvNama.text = item.nama
        holder.tvNim.text = ": ${item.nim}"
        holder.tvProdi.text = ": ${item.prodi}"
        holder.tvAngkatan.text = ": ${item.angkatan}"

        // Menangani aksi klik pada tombol Edit
        holder.btnEdit.setOnClickListener { onEditClick(item) }
        
        // Menangani aksi klik pada tombol Delete
        holder.btnDelete.setOnClickListener { onDeleteClick(item) }
    }

    override fun getItemCount(): Int = listMahasiswa.size

    /**
     * Fungsi untuk memperbarui data di dalam adapter saat terjadi perubahan (misal: refresh data)
     */
    fun updateData(newList: List<DataMahasiswaModel>) {
        listMahasiswa = newList
        notifyDataSetChanged()
    }
}
