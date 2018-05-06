package com.kevin.navmedia.ui.main.adapter

import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kevin.navmedia.R
import com.kevin.navmedia.data.entity.Video
import com.kevin.navmedia.databinding.ItemTop100Binding
import kotlinx.android.synthetic.main.item_top100.view.*

/**
 * Created by quf93 on 2018-04-15.
 */
class TopRecyclerViewAdapter constructor(private val videoList : List<Video>): RecyclerView.Adapter<TopViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopViewHolder = TopViewHolder(parent)

    override fun getItemCount(): Int = videoList.size

    override fun onBindViewHolder(holder: TopViewHolder, position: Int) {
        videoList[position].let {
            with(holder) {
                Glide.with(itemView.context)
                        .load(it.thumbnailUrl)
                        .apply(RequestOptions.centerCropTransform())
                        .into(thumbnail)

                time.text = it.time.toString()
                title.text = it.title
                rank.text = it.rank.toString()
                rankVariance(rankVar, it.rankVar)
                rankVar.text = it.rankVar.toString()
                program.text = it.program
                watchers.text = counting(count = it.playCount)
                lovers.text = counting(count = it.loveCount)
            }
        }
    }

    private fun rankVariance(rankVarView : TextView, rankVar : Int) {
        when {
            rankVar > 0 -> {
                rankVarView.setTextColor(ContextCompat.getColor(rankVarView.context, R.color.Red))
                rankVarView.text = rankVar.toString()
            }
            rankVar < 0 -> {
                rankVarView.setTextColor(ContextCompat.getColor(rankVarView.context, R.color.RoyalBlue))
                rankVarView.text = rankVar.toString().substring(1)
            }
            else -> {
                rankVarView.setTextColor(ContextCompat.getColor(rankVarView.context, R.color.LightSlateGray))
                rankVarView.text = "-"
            }
        }
    }

    private fun counting(count : Int) : String {
        return when {
            count in 0..999 -> {
                count.toString()
            }
            count in 1000..9999 -> {
                val watchers = count.toString()
                watchers[0]+","+watchers.substring(1..3)
            }
            count > 9999 -> {
                val m : Int = count / 10000
                val t : Int = (count / 1000) % 10
                "$m.$t"+"만"
            }
            else -> "0"
        }
    }
}

class TopViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        DataBindingUtil
                .inflate<ItemTop100Binding>(LayoutInflater.from(parent.context),
                        R.layout.item_top100,
                        parent,
                        false)
                .root) {

    val thumbnail : ImageView = itemView.thumbnail
    val time : TextView = itemView.time
    val rank : TextView = itemView.rank
    val rankVar : TextView = itemView.rankVar
    val title : TextView = itemView.title
    val program : TextView  = itemView.program
    val watchers : TextView = itemView.watchers
    val lovers : TextView = itemView.lovers
}