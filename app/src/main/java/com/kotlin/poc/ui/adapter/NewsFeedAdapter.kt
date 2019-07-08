package com.kotlin.poc.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.kotlin.poc.R
import com.kotlin.poc.model.NewsFeed
import java.util.*

/**
 * Adapter that will create news feed row and set the data
 */
class NewsFeedAdapter constructor(private val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mItems: MutableList<NewsFeed>? = null

    init {
        this.mItems = ArrayList()
    }

    fun setData(data: ArrayList<NewsFeed>) {
        this.mItems = ArrayList()
        this.mItems!!.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_news_feed, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val newsFeed = mItems!![position]
        val viewHolder = holder as ViewHolder
        viewHolder.tvTitle.text = newsFeed.title
        viewHolder.tvDescription.text = newsFeed.description

        if(newsFeed.image == null || (newsFeed.image.isEmpty())){
            viewHolder.imgNews.visibility = View.GONE
        }
        else{
            viewHolder.imgNews.visibility = View.VISIBLE
            Glide.with(mContext)
                    .load(newsFeed.image)
                    .placeholder(R.drawable.ic_place_holder)
                    .error(R.drawable.ic_error)
                    .centerCrop()
                    .into(viewHolder.imgNews)
        }
    }

    override fun getItemCount(): Int {
        return mItems!!.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvTitle: TextView = v.findViewById(R.id.tvNewsTitle) as TextView
        val tvDescription: TextView = v.findViewById(R.id.tvNewsDescription) as TextView
        val imgNews: ImageView = v.findViewById(R.id.imgNews) as ImageView
    }
}
