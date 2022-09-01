package com.lyft.android.omdbsampleproject.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lyft.android.omdbsampleproject.R
import com.lyft.android.omdbsampleproject.databinding.FragmentSearchingMovieBinding
import com.lyft.android.omdbsampleproject.view.adapter.MovieListAdapter
import com.lyft.android.omdbsampleproject.view_model.SearchingMoviesViewModel
import kotlinx.android.synthetic.main.fragment_searching_movie.*

class SearchingMovieFragment : Fragment(), SearchingMoviesViewModel.SearchingListener {

    private val viewModel: SearchingMoviesViewModel by viewModels()
    private lateinit var adapter: MovieListAdapter
    private lateinit var binding: FragmentSearchingMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MovieListAdapter(viewModel)
        viewModel.listener = this
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
        operateKeyBoard()
    }

    private fun operateKeyBoard() {
        addFocusChangeListener(title_et)
        addFocusChangeListener(year_et)
    }

    private fun addFocusChangeListener(et: EditText) {
        et.setOnFocusChangeListener { view, b ->
            val imm: InputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (b) imm.showSoftInput(view, 0)
            else imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun resetScroll() {
        movie_list_rv.scrollToPosition(0)
    }
}