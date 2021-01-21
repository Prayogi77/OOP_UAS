package com.example.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.Database.Konsumen
import kotlinx.android.synthetic.main.activity_konsumen_adapter.view.*

class KonsumenAdapter (private val AllKonsumen: ArrayList<Konsumen>, private val listener: OnAdapterListener) : RecyclerView.Adapter<KonsumenAdapter.KonsumenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KonsumenViewHolder {
        return KonsumenViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.activity_konsumen_adapter, parent, false)
        )
    }

    override fun getItemCount() = AllKonsumen.size

    override fun onBindViewHolder(holder: KonsumenViewHolder, position: Int) {
        val konsumen = AllKonsumen[position]
        holder.view.text_username.text = konsumen.username
        holder.view.text_username.setOnClickListener {
            listener.onClick(konsumen)
        }

        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(konsumen)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(konsumen)
        }
    }

    class KonsumenViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Konsumen>) {
        AllKonsumen.clear()
        AllKonsumen.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(konsumen: Konsumen)
        fun onDelete(konsumen: Konsumen)
        fun onUpdate(konsumen: Konsumen)
    }

}