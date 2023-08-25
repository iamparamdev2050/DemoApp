package com.tpss.mpapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TableAdapter(var data: List<TableRowData>) : RecyclerView.Adapter<TableAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_table, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rowData: TableRowData = data!![position]
        holder.header1TextView.text = rowData.header1
        holder.header2TextView.text = rowData.header2
        holder.header3TextView.text = rowData.header3
        holder.header4TextView.text = rowData.header4
        holder.header5TextView.text = rowData.header5
        holder.header6TextView.text = rowData.header6
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var header1TextView: TextView
        var header2TextView: TextView
        var header3TextView: TextView
        var header4TextView: TextView
        var header5TextView: TextView
        var header6TextView: TextView

        init {
            header1TextView = itemView.findViewById(R.id.header1TextView)
            header2TextView = itemView.findViewById(R.id.header2TextView)
            header3TextView = itemView.findViewById(R.id.header3TextView)
            header4TextView = itemView.findViewById(R.id.header4TextView)
            header5TextView = itemView.findViewById(R.id.header5TextView)
            header6TextView = itemView.findViewById(R.id.header6TextView)
        }
    }


}