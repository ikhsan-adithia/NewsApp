package com.test.newsapp.presentation.news_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.newsapp.databinding.FragmentNewsDetailBinding
import com.test.newsapp.presentation.utils.loadImage

class NewsDetailFragment: Fragment() {

    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val news = NewsDetailFragmentArgs.fromBundle(it).news

            with(binding) {
                imvBanner.loadImage(news.banner)
                tvTitle.text = news.title
                val authorPublisherDate = "${news.author} • ${news.source} • ${news.publishDate}"
                tvAuthorPublisherDate.text = authorPublisherDate
                tvContent.text = news.content
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}