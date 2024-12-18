package com.capstone.maternalcare.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.maternalcare.data.model.History
import com.capstone.maternalcare.databinding.ItemRowHistoryBinding
import com.capstone.maternalcare.util.changeTimeFormat
import java.util.ArrayList

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {

    private var listHistory= ArrayList<History>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setHistory(data: List<History>?) {
        if (data == null) return
        this.listHistory.clear()
        this.listHistory.addAll(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (
            _,
            _ ,
            date_to_born ,
            status ,
            _ ,
            created_at ,
            _ ) = listHistory[position]
        holder.binding.historyTitle.text = "Diagnose - ${position+1}"
        holder.binding.historyText.text = status
        holder.binding.dateStart.text =  changeTimeFormat(created_at.toString())
        holder.binding.dateEnd.text = changeTimeFormat(date_to_born)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listHistory[position])
        }
    }

    override fun getItemCount(): Int = listHistory.size

    interface OnItemClickCallback {
        fun onItemClicked(data: History)
    }

    class ListViewHolder(var binding: ItemRowHistoryBinding) : RecyclerView.ViewHolder(binding.root)
}