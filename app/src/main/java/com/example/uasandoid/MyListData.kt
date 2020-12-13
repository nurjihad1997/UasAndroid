package com.example.uasandoid

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyListData : AppCompatActivity(), RecyclerViewAdapter.dataListener {
    private var recyclerView:RecyclerView? = null
    private var adapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    val database = FirebaseDatabase.getInstance()
    private var dataPenduduk = ArrayList<data_penduduk>()
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_list_data)
        recyclerView = findViewById(R.id.datalist)
        supportActionBar!!.title = "List Data Penduduk"
        auth = FirebaseAuth.getInstance()
        MyRecyclerView()
        GetData()
    }
    private fun GetData(){
        Toast.makeText(applicationContext, "Mohon Tunggu Sebentar...",
        Toast.LENGTH_SHORT).show()
        val getUserID: String = auth?.getCurrentUser()?.getUid().toString()
        val getReference = database.getReference()
        getReference.child("admin").child(getUserID).child("uasandroid")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(datasnapshot: DataSnapshot) {
                    if (datasnapshot.exists()){
                        dataPenduduk.clear()
                        for (snapshot in datasnapshot.children){
                            val penduduk = snapshot.getValue(data_penduduk::class.java)
                            penduduk?.key = snapshot.key
                            dataPenduduk.add(penduduk!!)
                        }
                        adapter = RecyclerViewAdapter(dataPenduduk, this@MyListData)
                        recyclerView?.adapter = adapter
                        (adapter as RecyclerViewAdapter).notifyDataSetChanged()
                        Toast.makeText(applicationContext, "Data Berhasil diMuat",
                        Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(applicationContext, "Data Gagal diMuat",
                    Toast.LENGTH_SHORT).show()
                    Log.e("MyListActivity", databaseError.details+" "+
                    databaseError.message)
                }
            })
    }
    private fun MyRecyclerView(){
        layoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)

        val itemDecoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.line)!!)
        recyclerView?.addItemDecoration(itemDecoration)
    }

    override fun onDeleteData(data: data_penduduk?, position: Int) {
        val getUserID:String= auth?.getCurrentUser()?.getUid().toString()
        val getReference = database.getReference()
        if(getReference != null) {
            getReference.child("admin")
                    .child(getUserID)
                    .child("uasandroid")
                    .child(data?.key.toString())
                    .removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(this@MyListData,"Data Berhasil Dihapus",
                        Toast.LENGTH_SHORT).show()
                    }
        }else{
            Toast.makeText(this@MyListData,"Reference Kosong",
                    Toast.LENGTH_SHORT).show()

        }
    }
}