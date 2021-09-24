package com.example.worldbeers.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.worldbeers.R
import com.example.worldbeers.ui.home.model.BeerDomain

interface ListenerSearchInterface {
    fun onClickSearch(itemElement: BeerDomain)
}

class SearchItemAdapter(private val listenerSearchInterface: ListenerSearchInterface) :
    ListAdapter<BeerDomain, SearchItemAdapter.ValueViewHolder>(
        ProjectDiffCallback()
    ) {

    class ValueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val itemDescription = itemView.findViewById<AppCompatTextView>(R.id.search_product_result_text)

        fun bind(value: BeerDomain, listenerSearchInterface: ListenerSearchInterface, listPosition: Int) {
            itemDescription.text = value.name
            itemDescription.setOnClickListener {
                listenerSearchInterface.onClickSearch(value)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item_result, parent, false)
        return ValueViewHolder(view)
    }

    override fun onBindViewHolder(holder: ValueViewHolder, listPosition: Int) {
        val value = getItem(listPosition)
        holder.bind(value, listenerSearchInterface, listPosition)
    }

    class ProjectDiffCallback : DiffUtil.ItemCallback<BeerDomain>() {
        override fun areItemsTheSame(oldItem: BeerDomain, newItem: BeerDomain): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BeerDomain, newItem: BeerDomain): Boolean {
            return oldItem == newItem
        }
    }
}
