package com.example.uasandoid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.TextUtils.isEmpty
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update_data.*

class UpdateData : AppCompatActivity() {
    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var ceknokk: String? = null
    private var ceknamakepala: String? = null
    private var cekalamat: String? = null
    private var cekrt: String? = null
    private var ceklurah: String? = null
    private var cekcamat: String? = null
    private var cekkota: String? = null
    private var cekprovinsi: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        supportActionBar!!.title = "Update Data Penduduk"

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        data
        update.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                ceknokk = new_nokk.getText().toString()
                ceknamakepala = new_namakepala.getText().toString()
                cekalamat = new_alamat.getText().toString()
                cekrt = new_rt.getText().toString()
                ceklurah = new_lurah.getText().toString()
                cekcamat = new_camat.getText().toString()
                cekkota = new_kota.getText().toString()
                cekprovinsi = new_provinsi.getText().toString()

                if (isEmpty(ceknokk) || isEmpty(ceknamakepala) || isEmpty(cekalamat) || isEmpty(cekrt) || isEmpty(ceklurah) || isEmpty(cekcamat) || isEmpty(cekkota) || isEmpty(cekprovinsi)){
                    Toast.makeText(this@UpdateData, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }else{
                    val setdatadata_penduduk = data_penduduk()
                    setdatadata_penduduk.nokk = new_nokk.getText().toString()
                    setdatadata_penduduk.namakepala = new_namakepala.getText().toString()
                    setdatadata_penduduk.alamat = new_alamat.getText().toString()
                    setdatadata_penduduk.rt = new_rt.getText().toString()
                    setdatadata_penduduk.lurah = new_lurah.getText().toString()
                    setdatadata_penduduk.camat = new_camat.getText().toString()
                    setdatadata_penduduk.kota = new_kota.getText().toString()
                    setdatadata_penduduk.provinsi = new_provinsi.getText().toString()
                    updateDP(setdatadata_penduduk)
                }
            }
        })
    }
    private fun isEmpty(s: String): Boolean{
        return TextUtils.isEmpty(s)
    }

    private val data: Unit
    private get(){
        val getnokk = intent.extras!!.getString("dataNoKK")
        val getnamakepala = intent.extras!!.getString("dataNamaKepala")
        val getalamat = intent.extras!!.getString("dataAlamat")
        val getrt = intent.extras!!.getString("dataRt")
        val getlurah = intent.extras!!.getString("dataLurah")
        val getcamat = intent.extras!!.getString("dataCamat")
        val getkota = intent.extras!!.getString("dataKota")
        val getprovinsi = intent.extras!!.getString("dataProvinsi")

        new_nokk!!.setText(getnokk)
        new_namakepala!!.setText(getnamakepala)
        new_alamat!!.setText(getalamat)
        new_rt!!.setText(getrt)
        new_lurah!!.setText(getlurah)
        new_camat!!.setText(getcamat)
        new_kota!!.setText(getkota)
        new_provinsi!!.setText(getprovinsi)
    }
    private fun updateDP(dataPenduduk: data_penduduk){
        val userID = auth!!.uid
        val getkey = intent.extras!!.getString("getPrimaryKey")
        database!!.child("admin")
            .child(userID!!)
            .child("uasandroid")
            .child(getkey!!)
            .setValue(dataPenduduk)
            .addOnSuccessListener {
                new_nokk!!.setText("")
                new_namakepala!!.setText("")
                new_alamat!!.setText("")
                new_rt!!.setText("")
                new_lurah!!.setText("")
                new_camat!!.setText("")
                new_kota!!.setText("")
                new_provinsi!!.setText("")
                Toast.makeText(this@UpdateData, "Data Berhasil Diubah", Toast. LENGTH_SHORT).show()
                finish()
            }
    }
}