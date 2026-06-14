package com.example.prak4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val tvNama = view.findViewById<TextView>(R.id.tv_output_nama)
        val tvJk = view.findViewById<TextView>(R.id.tv_output_jk)
        val tvHobi = view.findViewById<TextView>(R.id.tv_output_hobi)
        val tvAlamat = view.findViewById<TextView>(R.id.tv_output_alamat)
        val tvKota = view.findViewById<TextView>(R.id.tv_output_kota)
        val tvProvinsi = view.findViewById<TextView>(R.id.tv_output_provinsi)
        val sharedPref = requireActivity().getSharedPreferences("biodata", 0)
        tvNama.text = "Nama: ${sharedPref.getString("nama", "-")}"
        tvJk.text = "Jenis Kelamin: ${sharedPref.getString("jk", "-")}"
        tvHobi.text = "Hobi: ${sharedPref.getString("hobi", "-")}"
        tvAlamat.text = "Alamat: ${sharedPref.getString("alamat", "-")}"
        tvKota.text = "Kota: ${sharedPref.getString("kota", "-")}"
        tvProvinsi.text = "Provinsi: ${sharedPref.getString("provinsi", "-")}"

        return view
    }
}
