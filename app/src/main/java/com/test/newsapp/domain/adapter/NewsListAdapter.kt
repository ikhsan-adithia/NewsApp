package com.test.newsapp.domain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.newsapp.data.models.News
import com.test.newsapp.databinding.ItemNewsCardBinding
import com.test.newsapp.presentation.utils.loadImage

class NewsListAdapter: RecyclerView.Adapter<NewsListViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder =
        NewsListViewHolder(ItemNewsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        val news = differ.currentList[holder.adapterPosition]
        holder.bind(news)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(news) }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener: ((News) -> Unit)? = null
    fun setOnItemClickListener(listener: (News) -> Unit) {
        onItemClickListener = listener
    }
}

class NewsListViewHolder(private val binding: ItemNewsCardBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(news: News) {
        with(binding) {
            if (news.banner.isEmpty()) {
                tvNoImageAvailable.visibility = View.VISIBLE
            } else {
                tvNoImageAvailable.visibility = View.GONE
            }

            imvBanner.loadImage(news.banner)
            tvTitle.text = news.title
            tvContent.text = news.content
            val footer = "${news.author} • ${news.source} • ${news.publishDate}"
            tvFooter.text = footer
        }
    }
}