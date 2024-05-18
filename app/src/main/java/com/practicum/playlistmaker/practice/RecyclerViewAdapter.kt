package com.practicum.playlistmaker.practice

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.databinding.RecyclerItemBinding


class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 1
        return ViewHolder(RecyclerItemBinding.inflate(layoutInspector, parent, false))
    }
    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // ...

    class ViewHolder(private val binding: RecyclerItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.title.text = item.text
            binding.field.text = item.field
            // ..
        }
    }

}