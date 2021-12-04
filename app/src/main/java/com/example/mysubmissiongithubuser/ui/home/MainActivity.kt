package com.example.mysubmissiongithubuser.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissiongithubuser.ui.detail.DetailActivity
import com.example.mysubmissiongithubuser.R
import com.example.mysubmissiongithubuser.model.User
import com.example.mysubmissiongithubuser.databinding.ActivityMainBinding
import com.example.mysubmissiongithubuser.favorite.FavoriteActivity
import com.example.mysubmissiongithubuser.ui.settings.ActivitySettings

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : MainViewModel
    private lateinit var adapter : UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }

        })

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            tvSearch.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    searchUser()
                    return@setOnKeyListener true
                }

                return@setOnKeyListener false
            }
        }

        viewModel.getSearchUsers().observe(this, {
            if (it!=null){
                adapter.setList(it)
                showLoading(false)
                showImageView(false)
                showContent(false)
            }
        })
    }

    private fun searchUser(){
        binding.apply {
            val query = tvSearch.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.change_setting ->
                Intent(Settings.ACTION_LOCALE_SETTINGS).also {
                    startActivity(it)
                }

            R.id.reminder ->
                Intent(this, ActivitySettings::class.java).also {
                    startActivity(it)
                }

            R.id.menu_favorit -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
/*
        if (item.itemId == R.id.change_setting) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }*/

        return super.onOptionsItemSelected(item)
    }

    private fun showImageView(state: Boolean) {
        if (state) {
            binding.imageView.visibility = View.VISIBLE
        } else {
            binding.imageView.visibility = View.GONE
        }
    }

    private fun showContent(state: Boolean){
        if (state) {
            binding.textContent.visibility = View.VISIBLE
        } else {
            binding.textContent.visibility = View.GONE
        }
    }
}