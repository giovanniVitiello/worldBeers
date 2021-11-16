package com.example.worldbeers.ui.home

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.worldbeers.R
import com.example.worldbeers.databinding.ItemResultBinding
import com.example.worldbeers.ui.home.model.BeerDomain
import com.example.worldbeers.utils.loadImageFromUrl

class PagingBeersAdapter(
    private val onClickListener: OnClickListener,
    private val resources: Resources
)  : PagingDataAdapter<BeerDomain, PagingBeersAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onClickListener, resources) }
    }

    class OnClickListener(val clickListener: (BeerDomain) -> Unit) {
        fun onClick(content: BeerDomain) = clickListener(content)
    }

    class ViewHolder(private val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(beerItem: BeerDomain, onClickListener: OnClickListener, resources: Resources) {
            with(binding) {
                tvNameBeer.text = beerItem.name
                ivBeer.loadImageFromUrl(beerItem.imageUrl)
                tvAbvBeer.text = String.format(resources.getString(R.string.abv_), beerItem.abv ?: resources.getString(R.string.n_a))
                tvIbuBeer.text = String.format(resources.getString(R.string.ibu_), beerItem.ibu ?: resources.getString(R.string.n_a))
                tvDescriptionBeer.text = beerItem.description

                beerCard.setOnClickListener {
                    onClickListener.onClick(beerItem)
                }
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<BeerDomain>() {
            override fun areItemsTheSame(oldItem: BeerDomain, newItem: BeerDomain): Boolean =
                oldItem.name == newItem.name

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: BeerDomain, newItem: BeerDomain): Boolean =
                oldItem == newItem
        }
    }
}
