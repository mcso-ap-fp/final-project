package com.example.finalproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.CuisineRowBinding

class RVDiffAdapter()
    : ListAdapter<String,
        RVDiffAdapter.ViewHolder>(Diff())
{
    companion object {
        val tag = "RVDiffAdapter"
    }

    inner class ViewHolder(val cuisineRowBinding: CuisineRowBinding)
        : RecyclerView.ViewHolder(cuisineRowBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CuisineRowBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.cuisineRowBinding
        val cuisine: String = getItem(position)
        binding.cuisineTitle.text = "$cuisine:"
    }

    class Diff : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}

