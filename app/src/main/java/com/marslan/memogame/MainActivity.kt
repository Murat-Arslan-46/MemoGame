package com.marslan.memogame

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.marslan.memogame.data.Card
import com.marslan.memogame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BoardAdapter
    private lateinit var list: ArrayList<Card>
    private var lastPosition: Int = -1
    private var handler: Handler = Handler(Looper.getMainLooper())


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        GridLayoutManager(
            this,
            4
        ).apply {
            binding.board.layoutManager = this
        }
        list = arrayListOf(
            Card(1, R.drawable.icon1),
            Card(2, R.drawable.icon1),
            Card(3, R.drawable.icon2),
            Card(4, R.drawable.icon2),
            Card(5, R.drawable.icon3),
            Card(6, R.drawable.icon3),
            Card(7, R.drawable.icon4),
            Card(8, R.drawable.icon4),
            Card(9, R.drawable.icon5),
            Card(10, R.drawable.icon5),
            Card(11, R.drawable.icon6),
            Card(12, R.drawable.icon6),
            Card(13, R.drawable.icon7),
            Card(14, R.drawable.icon7),
            Card(15, R.drawable.icon8),
            Card(16, R.drawable.icon8)
        ).apply {
            shuffle()
        }
        adapter = BoardAdapter(this::onClick)
        adapter.submitList(list)
        binding.board.adapter = adapter
    }

    private fun onClick(position: Int) {
        val currentCard = list[position].copy()
        if (!currentCard.isActive) {
            if (lastPosition != -1) {
                val lastCard = list[lastPosition].copy()
                if (lastCard.imageID == currentCard.imageID) {
                    lastCard.isFind = true
                    currentCard.isFind = true
                    list[lastPosition] = lastCard
                    list[position] = currentCard
                    checkIfGameFinished()
                } else {
                    laterHideCard(currentCard,position,lastCard,lastPosition)
                }
                lastPosition = -1
            }
            else
                lastPosition = position
            currentCard.isActive = true
            list[position] = currentCard
            adapter.submitList(list)
            adapter.notifyItemChanged(position)
        }
    }

    private fun laterHideCard(
        currentCard: Card,
        currentPosition: Int,
        lastCard: Card,
        lastPosition: Int
    ){
        handler.postDelayed({
            list[currentPosition] = currentCard
            adapter.notifyItemChanged(currentPosition)
            lastCard.isActive = false
            currentCard.isActive = false
            list[currentPosition] = currentCard
            list[lastPosition] = lastCard
            adapter.submitList(list)
            adapter.notifyItemChanged(currentPosition)
            adapter.notifyItemChanged(lastPosition)
        }, 500)
    }

    private fun checkIfGameFinished(){
        var finishFlag = true

        for (card in list)
            if (!card.isFind)
                finishFlag = false
        if (finishFlag) {
            // game over
            val anim= AnimationUtils.loadAnimation(this,R.anim.anim_card)
            handler.postDelayed(Runnable {
                list.shuffle()
                for (card in list) {
                    card.isFind = false
                    card.isActive = false
                }
                binding.board.startAnimation(anim)
                adapter.submitList(list)
                adapter.notifyDataSetChanged()
            },5000)
            // game over
        }
    }
}