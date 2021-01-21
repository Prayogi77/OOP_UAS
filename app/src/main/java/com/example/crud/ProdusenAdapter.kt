package com.example.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.Database.Produsen
import kotlinx.android.synthetic.main.activity_produsen_adapter.view.*

class ProdusenAdapter (private val allProdusen: ArrayList<Produsen>, private val listener: OnAdapterListener) : RecyclerView.Adapter<ProdusenAdapter.ProdusenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdusenViewHolder {
        return ProdusenViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.activity_produsen_adapter, parent, false)
        )
    }

    override fun getItemCount() = allProdusen.size

    override fun onBindViewHolder(holder: ProdusenViewHolder, position: Int) {
        val produsen = allProdusen[position]
        holder.view.text_nama.text = produsen.nama
        holder.view.text_nama.setOnClickListener {
            listener.onClick(produsen)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(produsen)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(produsen)
        }

    }

    class ProdusenViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Produsen>) {
        allProdusen.clear()
        allProdusen.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(produsen: Produsen)
        fun onDelete(produsen: Produsen)
        fun onUpdate(produsen: Produsen)
    }
}