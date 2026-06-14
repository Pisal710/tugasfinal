package com.example.prak4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.prak4.databinding.FragmentHomeBinding
import com.example.prak4.model.ResponsModel
import com.example.prak4.network.RetrofitClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val listKota = arrayOf("Makassar", "Jakarta", "Surabaya", "Medan")
    private val listProv = arrayOf("Sulawesi Selatan", "DKI Jakarta", "Jawa Timur", "Sumatera Utara")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spKota.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listKota)
        binding.spProvinsi.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listProv)

        binding.btnSimpan.setOnClickListener {
            simpanKeDatabase()
        }

        binding.btnTampil.setOnClickListener {
            val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
            bottomNav.selectedItemId = R.id.menu_task
        }
    }

    private fun simpanKeDatabase() {
        val nama = binding.etNama.text?.toString()?.trim() ?: ""
        val nim = binding.etNim.text?.toString()?.trim() ?: ""
        val prodi = binding.etProdi.text?.toString()?.trim() ?: ""
        val angkatan = binding.etAngkatan.text?.toString()?.trim() ?: ""
        val alamat = binding.editAlamat.text?.toString()?.trim() ?: ""

        val selectedJkId = binding.rgJk.checkedRadioButtonId
        val jenisKelaminText = if (selectedJkId != -1) {
            binding.root.findViewById<RadioButton>(selectedJkId).text.toString()
        } else {
            ""
        }
        val jenisKelaminDB = if (jenisKelaminText == "Laki-laki") "L" else "P"

        val hobiList = mutableListOf<String>()
        if (binding.cb1.isChecked) hobiList.add(binding.cb1.text.toString())
        if (binding.cb2.isChecked) hobiList.add(binding.cb2.text.toString())
        if (binding.cb3.isChecked) hobiList.add(binding.cb3.text.toString())
        val hobi = hobiList.joinToString(", ")
        val kota = binding.spKota.selectedItem.toString()
        val provinsi = binding.spProvinsi.selectedItem.toString()

        if (nama.isEmpty() || nim.isEmpty() || prodi.isEmpty() || angkatan.isEmpty() || jenisKelaminText.isEmpty()) {
            Toast.makeText(requireContext(), "Harap isi semua data!", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPref = requireActivity().getSharedPreferences("biodata", 0)
        val editor = sharedPref.edit()
        editor.putString("nama", nama)
        editor.putString("jk", jenisKelaminText)
        editor.putString("hobi", hobi)
        editor.putString("alamat", alamat)
        editor.putString("kota", kota)
        editor.putString("provinsi", provinsi)
        editor.apply()

        // --- PERBAIKAN 2: Mengirim jenisKelaminDB (L/P) ke Retrofit ---
        RetrofitClient.api.simpanMahasiswa(
            nama, nim, prodi, angkatan, jenisKelaminDB, hobi, alamat, kota, provinsi
        ).enqueue(object : Callback<ResponsModel> {
            override fun onResponse(call: Call<ResponsModel>, response: Response<ResponsModel>) {
                if (isAdded && response.isSuccessful) {
                    Toast.makeText(requireContext(), "Biodata Berhasil Disimpan!", Toast.LENGTH_SHORT).show()
                    clearForm()
                }
            }
            override fun onFailure(call: Call<ResponsModel>, t: Throwable) {
                if (isAdded) Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun clearForm() {
        binding.etNama.setText("")
        binding.etNim.setText("")
        binding.etProdi.setText("")
        binding.etAngkatan.setText("")
        binding.editAlamat.setText("")
        binding.rgJk.clearCheck()
        binding.cb1.isChecked = false
        binding.cb2.isChecked = false
        binding.cb3.isChecked = false
        binding.spKota.setSelection(0)
        binding.spProvinsi.setSelection(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
