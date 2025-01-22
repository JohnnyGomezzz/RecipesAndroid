package com.example.recipesandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.recipesandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<CategoriesListFragment>(R.id.mainContainer)
            }
        }
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFavorites.setOnClickListener {
            supportFragmentManager.commit {
                replace<FavoritesListFragment>(R.id.mainContainer)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        binding.btnCategories.setOnClickListener {
            supportFragmentManager.commit {
                replace<CategoriesListFragment>(R.id.mainContainer)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }
    }
}