package com.kotlin.poc.ui.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
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

        if(checkIfAllFieldsBlank(newsFeed)){
            viewHolder.setItemVisibility(false)
        }
        else{
            viewHolder.setItemVisibility(true)

            if(newsFeed.image == null || newsFeed.image.isEmpty()){
                viewHolder.imgNews.visibility = View.GONE
            }
            else{
                viewHolder.imgNews.visibility = View.VISIBLE
                Glide.with(mContext)
                        .load(newsFeed.image)
                        .placeholder(R.drawable.ic_image_placeholder)
                        .error(R.drawable.ic_image_error)
                        .centerCrop()
                        .into(viewHolder.imgNews)
            }
        }
    }

    /**
     * will return true if all field are blank
     */
    private fun checkIfAllFieldsBlank(newsFeed: NewsFeed): Boolean{
        if((newsFeed.title == null || newsFeed.title.isEmpty())
                        && (newsFeed.description == null || newsFeed.description.isEmpty())
                        && (newsFeed.image == null || newsFeed.image.isEmpty())){
            return true
        }
        return false
    }

    override fun getItemCount(): Int {
        return mItems!!.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        val tvTitle: TextView = v.findViewById(R.id.tvNewsTitle) as TextView
        val tvDescription: TextView = v.findViewById(R.id.tvNewsDescription) as TextView
        val imgNews: ImageView = v.findViewById(R.id.imgNews) as ImageView
        val itemContainer: ConstraintLayout = v.findViewById(R.id.itemContainer) as ConstraintLayout

        fun setItemVisibility(isVisible: Boolean){
            val itemParam = itemContainer.layoutParams as RecyclerView.LayoutParams
            if (isVisible) {
                itemParam.height = LinearLayout.LayoutParams.WRAP_CONTENT
                itemParam.width = LinearLayout.LayoutParams.MATCH_PARENT
                itemContainer.visibility = View.VISIBLE
            } else {
                itemContainer.visibility = View.GONE
                itemParam.height = 0
                itemParam.width = 0
            }
            itemContainer.layoutParams = itemParam
        }
    }
}
