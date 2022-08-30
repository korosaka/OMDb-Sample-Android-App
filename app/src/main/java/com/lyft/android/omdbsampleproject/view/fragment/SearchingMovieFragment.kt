package com.lyft.android.omdbsampleproject.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lyft.android.omdbsampleproject.R
import com.lyft.android.omdbsampleproject.view.adapter.MovieListAdapter
import com.lyft.android.omdbsampleproject.view_model.SearchingMoviesViewModel
import kotlinx.android.synthetic.main.fragment_searching_movie.*

class SearchingMovieFragment : Fragment() {

    private val viewModel: SearchingMoviesViewModel by viewModels()
    private lateinit var adapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MovieListAdapter(viewModel.movies)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_searching_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchMovies {
            adapter.notifyDataSetChanged()
        }

        movie_list_rv.setHasFixedSize(true)
        movie_list_rv.layoutManager = LinearLayoutManager(activity)
        movie_list_rv.adapter = adapter
    }
}