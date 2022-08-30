package com.lyft.android.omdbsampleproject.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lyft.android.omdbsampleproject.R
import com.lyft.android.omdbsampleproject.model.MovieData
import kotlinx.android.synthetic.main.movie_list_item_view.view.*

class MovieListAdapter(private val movies: List<MovieData>) :
    RecyclerView.Adapter<MovieListAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_list_item_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = movies[position]

        holder.itemView.movie_title.text = movie.title
        holder.itemView.movie_year.text = movie.year
        movie.poster?.let {
            holder.itemView.movie_poster_image.setImageBitmap(it)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}