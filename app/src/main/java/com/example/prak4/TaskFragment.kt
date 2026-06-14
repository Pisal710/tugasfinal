package com.example.prak4

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prak4.model.DataMahasiswaModel
import com.example.prak4.model.ResponsModel
import com.example.prak4.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskFragment : Fragment() {

    private lateinit var rvMahasiswa: RecyclerView
    private lateinit var adapter: MahasiswaAdapter
    private lateinit var tvTotal: TextView
    private lateinit var btnRefresh: ImageView
    private lateinit var btnTambah: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task, container, false)

        rvMahasiswa = view.findViewById(R.id.rv_mahasiswa)
        tvTotal = view.findViewById(R.id.tv_total_mahasiswa)
        btnRefresh = view.findViewById(R.id.btn_refresh)
        btnTambah = view.findViewById(R.id.btn_tambah_data)

        rvMahasiswa.layoutManager = LinearLayoutManager(requireContext())
        
        adapter = MahasiswaAdapter(
            emptyList(),
            onEditClick = { mahasiswa -> 
                showEditDialog(mahasiswa)
            },
            onDeleteClick = { mahasiswa -> 
                AlertDialog.Builder(requireContext())
                    .setTitle("Hapus Data")
                    .setMessage("Yakin ingin menghapus ${mahasiswa.nama}?")
                    .setPositiveButton("YA") { _, _ -> deleteData(mahasiswa.id ?: "") }
                    .setNegativeButton("TIDAK", null)
                    .show()
            }
        )
        rvMahasiswa.adapter = adapter

        btnRefresh.setOnClickListener { loadData() }
        
        btnTambah.setOnClickListener {
            Toast.makeText(requireContext(), "Gunakan menu Home untuk menambah data", Toast.LENGTH_SHORT).show()
        }

        loadData()
        return view
    }

    private fun loadData() {
        RetrofitClient.api.getMahasiswa().enqueue(object : Callback<List<DataMahasiswaModel>> {
            override fun onResponse(call: Call<List<DataMahasiswaModel>>, response: Response<List<DataMahasiswaModel>>) {
                if (isAdded && response.isSuccessful) {
                    val list = response.body() ?: emptyList()
                    adapter.updateData(list)
                    tvTotal.text = list.size.toString()
                }
            }
            override fun onFailure(call: Call<List<DataMahasiswaModel>>, t: Throwable) {
                if (isAdded) {
                    Toast.makeText(requireContext(), "Gagal memuat data dari database", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun showEditDialog(mahasiswa: DataMahasiswaModel) {
        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.fragment_home, null)
        
        dialogView.findViewById<View>(R.id.tv_judul).visibility = View.GONE
        dialogView.findViewById<View>(R.id.btn_simpan).visibility = View.GONE
        dialogView.findViewById<View>(R.id.btn_tampil).visibility = View.GONE

        val etNama = dialogView.findViewById<EditText>(R.id.et_nama)
        val etNim = dialogView.findViewById<EditText>(R.id.et_nim)
        val etProdi = dialogView.findViewById<EditText>(R.id.et_prodi)
        val etAngkatan = dialogView.findViewById<EditText>(R.id.et_angkatan)

        etNama.setText(mahasiswa.nama)
        etNim.setText(mahasiswa.nim)
        etProdi.setText(mahasiswa.prodi)
        etAngkatan.setText(mahasiswa.angkatan)

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Data Mahasiswa")
            .setView(dialogView)
            .setPositiveButton("UPDATE") { _, _ ->
                RetrofitClient.api.updateMahasiswa(
                    mahasiswa.id ?: "",
                    etNama.text.toString(),
                    etNim.text.toString(),
                    etProdi.text.toString(),
                    etAngkatan.text.toString()
                ).enqueue(object : Callback<ResponsModel> {
                    override fun onResponse(call: Call<ResponsModel>, response: Response<ResponsModel>) {
                        if (isAdded && response.isSuccessful) {
                            Toast.makeText(requireContext(), "Database Berhasil Diperbarui", Toast.LENGTH_SHORT).show()
                            loadData()
                        }
                    }
                    override fun onFailure(call: Call<ResponsModel>, t: Throwable) {}
                })
            }
            .setNegativeButton("BATAL", null)
            .show()
    }

    private fun deleteData(id: String) {
        RetrofitClient.api.deleteMahasiswa(id).enqueue(object : Callback<ResponsModel> {
            override fun onResponse(call: Call<ResponsModel>, response: Response<ResponsModel>) {
                if (isAdded && response.isSuccessful) {
                    Toast.makeText(requireContext(), "Data terhapus dari database", Toast.LENGTH_SHORT).show()
                    loadData()
                }
            }
            override fun onFailure(call: Call<ResponsModel>, t: Throwable) {}
        })
    }
}
