package com.example.uasandoid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View

import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var auth:FirebaseAuth? = null
    private val RC_SIGN_IN = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logout.setOnClickListener(this)
        save.setOnClickListener(this)
        show_data.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()
    }
    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }

    override fun onClick(v: View){
        when (v.getId()) {
            R.id.save -> {
                val getUserID = auth!!.currentUser!!.uid
                val database = FirebaseDatabase.getInstance()

                val getNokk: String = nokk.getText().toString()
                val getNamaKepala: String = namakepala.getText().toString()
                val getAlamat: String = alamat.getText().toString()
                val getRt: String = rt.getText().toString()
                val getLurah: String = lurah.getText().toString()
                val getCamat: String = camat.getText().toString()
                val getKota: String = kota.getText().toString()
                val getProvinsi: String = provinsi.getText().toString()

                val getReference: DatabaseReference
                getReference = database.reference

                if (isEmpty(getNokk) || isEmpty(getNamaKepala)|| isEmpty(getAlamat)|| isEmpty(getRt)|| isEmpty(getLurah)|| isEmpty(getCamat)|| isEmpty(getKota)|| isEmpty(getProvinsi)) {
                    Toast.makeText(this@MainActivity, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
                }else {
                    getReference.child("admin").child(getUserID).child("uasandroid").push()
                        .setValue(data_penduduk(getNokk, getNamaKepala, getAlamat, getRt, getLurah, getCamat, getKota, getProvinsi))
                        .addOnCompleteListener(this){
                            nokk.setText("")
                            namakepala.setText("")
                            alamat.setText("")
                            rt.setText("")
                            lurah.setText("")
                            camat.setText("")
                            kota.setText("")
                            provinsi.setText("")
                            Toast.makeText(this@MainActivity, "Data Tersimpan", Toast.LENGTH_SHORT).show()
                        }
                }


            }
            R.id.logout -> {
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(object : OnCompleteListener<Void> {
                            override fun onComplete(p0: Task<Void>) {
                                Toast.makeText(this@MainActivity, "Logout Berhasil",
                                Toast.LENGTH_SHORT).show()
                                intent = Intent(applicationContext, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        })
            }
            R.id.show_data -> {
                startActivity(Intent(this@MainActivity, MyListData::class.java))
            }
        }
        }


}
