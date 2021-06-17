package com.example.map.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.map.data.MarkerEntity
import com.example.map.databinding.DataItemBinding

class AdapterList(
    private var onListItemClickListener: OnListItemClickListener,
    private var onButtonDeleteListener: OnListItemClickListener
) :
    RecyclerView.Adapter<AdapterList.RecyclerItemViewHolder>() {

    private lateinit var bindingItem: DataItemBinding
    private var data: MutableList<MarkerEntity> = mutableListOf()

    fun updateData(newData: List<MarkerEntity>) {
        val diffResult = DiffUtil.calculateDiff(DataDiffCallback(newData, data))
        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        bindingItem = DataItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerItemViewHolder(bindingItem)
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class RecyclerItemViewHolder(val binding: DataItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MarkerEntity) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                binding.tvLatitude.text = String.format("%.5f", data.latitude)
                binding.tvLongitude.text = String.format("%.5f", data.latitude)
                binding.tvTitle.text = data.title
                binding.tvAnnotation.text = data.info
                binding.btDelete.setOnClickListener { onButtonDeleteListener.onItemClick(data) }
                binding.root.setOnClickListener { onListItemClickListener.onItemClick(data) }
            }
        }
    }


    inner class DataDiffCallback(val newData: List<MarkerEntity>, val oldData: List<MarkerEntity>) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldData.size
        }

        override fun getNewListSize(): Int {
            return newData.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (newData[newItemPosition].latitude == oldData[oldItemPosition].latitude)
                    && ((newData[newItemPosition].longitude == oldData[oldItemPosition].longitude))
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return newData[newItemPosition] == oldData[oldItemPosition]
        }

    }
}

interface OnListItemClickListener {
    fun onItemClick(data: MarkerEntity)
}