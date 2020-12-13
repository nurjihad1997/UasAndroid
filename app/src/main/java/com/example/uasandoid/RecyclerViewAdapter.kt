package com.example.uasandoid

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter (private val listdata_penduduk: ArrayList<data_penduduk>, context: Context) :
RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){
    private val context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val Nokk: TextView
        val NamaKepala: TextView
        val Alamat: TextView
        val Rt: TextView
        val Lurah: TextView
        val Camat: TextView
        val Kota: TextView
        val Provinsi: TextView
        val ListItem: LinearLayout

        init {
            Nokk = itemView.findViewById(R.id.nokk)
            NamaKepala = itemView.findViewById(R.id.namakepala)
            Alamat = itemView.findViewById(R.id.alamat)
            Rt = itemView.findViewById(R.id.rt)
            Lurah = itemView.findViewById(R.id.lurah)
            Camat = itemView.findViewById(R.id.camat)
            Kota = itemView.findViewById(R.id.kota)
            Provinsi = itemView.findViewById(R.id.provinsi)
            ListItem = itemView.findViewById(R.id.list_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val V:View = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_design,
        parent, false)
        return ViewHolder(V)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Nokk: String? = listdata_penduduk.get(position).nokk
        val NamaKepala: String? = listdata_penduduk.get(position).namakepala
        val Alamat: String? = listdata_penduduk.get(position).alamat
        val Rt: String? = listdata_penduduk.get(position).rt
        val Lurah: String? = listdata_penduduk.get(position).lurah
        val Camat: String? = listdata_penduduk.get(position).camat
        val Kota: String? = listdata_penduduk.get(position).kota
        val Provinsi: String? = listdata_penduduk.get(position).provinsi

        holder.Nokk.text = "No Kartu Keluarga : $Nokk"
        holder.NamaKepala.text = "Nama Kepala Keluarga : $NamaKepala"
        holder.Alamat.text = "Alamat : $Alamat"
        holder.Rt.text = "RT/RW : $Rt"
        holder.Lurah.text = "Kelurahan : $Lurah"
        holder.Camat.text = "Kecamatan : $Camat"
        holder.Kota.text = "Kota : $Kota"
        holder.Provinsi.text = "Provinsi : $Provinsi"
        holder.ListItem.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                holder.ListItem.setOnLongClickListener { view ->
                    val action = arrayOf("Update", "Delete")
                    val alert: AlertDialog.Builder = AlertDialog.Builder(view.context)
                    alert.setItems(action, DialogInterface.OnClickListener { dialog, i ->
                        when (i) {0 ->{
                            val bundle = Bundle()
                            bundle.putString("dataNoKK", listdata_penduduk[position].nokk)
                            bundle.putString("dataNamaKepala", listdata_penduduk[position].namakepala)
                            bundle.putString("dataAlamat", listdata_penduduk[position].alamat)
                            bundle.putString("dataRt", listdata_penduduk[position].rt)
                            bundle.putString("dataLurah", listdata_penduduk[position].lurah)
                            bundle.putString("dataCamat", listdata_penduduk[position].camat)
                            bundle.putString("dataKota", listdata_penduduk[position].kota)
                            bundle.putString("dataProvinsi", listdata_penduduk[position].provinsi)
                            bundle.putString("getPrimaryKey", listdata_penduduk[position].key)
                            val intent = Intent(view.context, UpdateData::class.java)
                            intent.putExtras(bundle)
                            context.startActivity(intent)
                        }1->{
                            listener?.onDeleteData(listdata_penduduk.get(position), position)
                        }
                        }
                    })
                    alert.create()
                    alert.show()
                    true
                }
                return true
            }
        })

    }

    override fun getItemCount(): Int {
        return listdata_penduduk.size
    }
    var listener: dataListener?=null
    init {
        this.context = context
        this.listener= context as MyListData
    }
    interface dataListener {
        fun onDeleteData(data: data_penduduk?, position: Int )
    }
}