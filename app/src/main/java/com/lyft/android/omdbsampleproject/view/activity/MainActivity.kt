package com.lyft.android.omdbsampleproject.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lyft.android.omdbsampleproject.R
import com.lyft.android.omdbsampleproject.view.fragment.SearchingMovieFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SearchingMovieFragment()).commit()
        }
    }
}