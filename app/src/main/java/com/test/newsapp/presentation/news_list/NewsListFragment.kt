package com.test.newsapp.presentation.news_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.test.newsapp.R
import com.test.newsapp.databinding.FragmentNewsListBinding
import com.test.newsapp.domain.adapter.NewsListAdapter
import com.test.newsapp.presentation.utils.MarginItemDecorationVertical
import com.test.newsapp.presentation.utils.listenForPagination
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsListFragment: Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<NewsListViewModel>()

    private lateinit var newsAdapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        newsAdapter = NewsListAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rv.apply {
                adapter = newsAdapter
                addItemDecoration(MarginItemDecorationVertical(20))
                listenForPagination {
                    viewModel.loadMoreNews()
                }
            }

            newsAdapter.setOnItemClickListener {
                val dir = NewsListFragmentDirections.actionListNewsToDetailNews(it)
                findNavController().navigate(dir)
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.state.collectLatest { state ->
                            newsAdapter.differ.submitList(state.news)

                            if (state.isLoading) {
                                progressBar.visibility = View.VISIBLE
                            } else {
                                progressBar.visibility = View.GONE
                            }
                        }
                    }

                    launch {
                        viewModel.errorMessage.collectLatest { message ->
                            Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}