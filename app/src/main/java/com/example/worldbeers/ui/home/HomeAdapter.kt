package com.example.worldbeers.ui.home

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.worldbeers.R
import com.example.worldbeers.ui.home.model.BeerDomain
import com.example.worldbeers.utils.loadImageFromUrl

class HomeAdapter(
    private val content: MutableList<BeerDomain>,
    private val onClickListener: OnClickListener,
    private val resources: Resources
) : RecyclerView.Adapter<DataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int = content.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(content[position], onClickListener, resources)

    class OnClickListener(val clickListener: (BeerDomain) -> Unit) {
        fun onClick(content: BeerDomain) = clickListener(content)
    }
}

class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageBeer: AppCompatImageView = itemView.findViewById(R.id.ivBeer)
    private val beerCard: CardView = itemView.findViewById(R.id.beerCard)
    private val nameBeer: TextView = itemView.findViewById(R.id.tvNameBeer)
    private val abvBeer: TextView = itemView.findViewById(R.id.tvAbvBeer)
    private val ibuBeer: TextView = itemView.findViewById(R.id.tvIbuBeer)
    private val descriptionBeer: TextView = itemView.findViewById(R.id.tvDescriptionBeer)

    fun bind(
        beerItem: BeerDomain,
        onClickListener: HomeAdapter.OnClickListener,
        resources: Resources
    ) {

        nameBeer.text = beerItem.name
        abvBeer.text = String.format(resources.getString(R.string.abv_), beerItem.abv ?: resources.getString(R.string.n_a))
        ibuBeer.text = String.format( resources.getString(R.string.ibu_), beerItem.ibu ?: resources.getString(R.string.n_a))
        descriptionBeer.text = beerItem.description

        imageBeer.loadImageFromUrl(beerItem.imageUrl)

        beerCard.setOnClickListener {
            onClickListener.onClick(beerItem)
        }
    }
}


