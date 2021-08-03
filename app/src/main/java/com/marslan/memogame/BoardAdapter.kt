package com.marslan.memogame

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marslan.memogame.data.Card
import com.marslan.memogame.databinding.ItemCardBinding

class BoardAdapter(private val clickListener: (Int) -> Unit) :
    ListAdapter<Card, RecyclerView.ViewHolder>(ItemCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemCardBinding.inflate(inflate, parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CardViewHolder).bind(currentList[position], position, clickListener)
    }

    inner class CardViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card, position: Int, clickListener: (Int) -> Unit) {
            val drawable = AppCompatResources.getDrawable(
                binding.root.context,
                if (card.isActive)
                    card.imageID
                else
                    R.drawable.card_background
            )
            binding.cardImage.setImageDrawable(drawable)
            binding.cardImage.setOnClickListener { clickListener(position) }
        }
    }
    private class ItemCallBack: DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card) =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Card, newItem: Card) =
            oldItem == newItem

    }
}