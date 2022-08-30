package com.lyft.android.omdbsampleproject.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lyft.android.omdbsampleproject.R
import com.lyft.android.omdbsampleproject.databinding.FragmentSearchingMovieBinding
import com.lyft.android.omdbsampleproject.view.adapter.MovieListAdapter
import com.lyft.android.omdbsampleproject.view_model.SearchingMoviesViewModel
import kotlinx.android.synthetic.main.fragment_searching_movie.*

class SearchingMovieFragment : Fragment() {

    private val viewModel: SearchingMoviesViewModel by viewModels()
    private lateinit var adapter: MovieListAdapter
    private lateinit var binding: FragmentSearchingMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MovieListAdapter(viewModel.movies)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_searching_movie, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = this.viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movie_list_rv.setHasFixedSize(true)
        movie_list_rv.layoutManager = LinearLayoutManager(activity)
        movie_list_rv.adapter = adapter

        viewModel.liveMovies.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }
    }
}