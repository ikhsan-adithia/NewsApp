package com.test.newsapp.presentation.news_preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.newsapp.databinding.FragmentNewsPreviewBinding

class NewsPreviewFragment: Fragment() {

    private lateinit var binding: FragmentNewsPreviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsPreviewBinding.inflate(inflater)
        return binding.root
    }
}