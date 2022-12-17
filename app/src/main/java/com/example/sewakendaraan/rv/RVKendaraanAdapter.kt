package com.example.sewakendaraan.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sewakendaraan.R
import com.example.sewakendaraan.data.DaftarMobil
import kotlinx.android.synthetic.main.rv_item_kendaraan.view.*

class RVKendaraanAdapter(private val data: ArrayList<DaftarMobil>, private val listener: OnAdapterListener): RecyclerView.Adapter<RVKendaraanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_kendaraan, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]
        holder.view.tvNamaPemilik.text = currentItem.model
        holder.view.tvJenisKendaraan.text = currentItem.deskripsi
        holder.view.iconEdit.setOnClickListener{
            listener.onUpdate(currentItem)
        }
        holder.itemView.iconDelete.setOnClickListener{
            listener.onDelete(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
    inner class ViewHolder( val view: View) :
        RecyclerView.ViewHolder(view)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<DaftarMobil>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(daftarMobil: DaftarMobil)
        fun onUpdate(daftarMobil: DaftarMobil)
        fun onDelete(daftarMobil: DaftarMobil)
    }
    /*class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvNamaPemilik: TextView = itemView.findViewById(R.id.tvNamaPemilik)
        val tvDetails: TextView = itemView.findViewById(R.id.tvDetails)
    }*/
}