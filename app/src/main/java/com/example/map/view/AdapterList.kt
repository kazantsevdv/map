package com.example.map.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.map.databinding.DataItemBinding
import com.example.map.model.Marker

class AdapterList(
    private var onListItemClickListener: OnListItemClickListener,
    private var onButtonDeleteListener: OnListItemClickListener
) :
    RecyclerView.Adapter<AdapterList.RecyclerItemViewHolder>() {

    private lateinit var bindingItem: DataItemBinding
    private var data: MutableList<Marker> = mutableListOf()

    fun updateData(newData: List<Marker>) {
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

        fun bind(data: Marker) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                binding.tvLatitude.text = data.latitudeStr
                binding.tvLongitude.text = data.longitudeStr
                binding.tvTitle.text = data.title
                binding.tvAnnotation.text = data.info
                binding.btDelete.setOnClickListener { onButtonDeleteListener.onItemClick(data) }
                binding.root.setOnClickListener { onListItemClickListener.onItemClick(data) }
            }
        }
    }


    inner class DataDiffCallback(val newData: List<Marker>, val oldData: List<Marker>) :
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
    fun onItemClick(data: Marker)
}