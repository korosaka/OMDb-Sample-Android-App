package com.lyft.android.omdbsampleproject.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lyft.android.omdbsampleproject.R
import com.lyft.android.omdbsampleproject.databinding.MovieListItemViewBinding
import com.lyft.android.omdbsampleproject.model.MovieData
import kotlinx.android.synthetic.main.movie_list_item_view.view.*

class MovieListAdapter(private val movies: List<MovieData>) :
    RecyclerView.Adapter<MovieListAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: MovieListItemViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(
            MovieListItemViewBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = movies[position]
        holder.binding.movieData = movie
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}