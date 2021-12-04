package com.example.mysubmissiongithubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mysubmissiongithubuser.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailUserViewModel

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        viewModel.setUserDetail(username)
        viewModel.getUserDetail().observe(this, {
            if (it != null){
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = "${it.followers}"
                    tvFollowing.text = "${it.following}"
                    tvRepository.text = "${it.public_repos}"
                    tvCompany.text = it.company
                    tvLocation.text = it.location
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .circleCrop()
                        .into(ivProfil)
                }
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count>0){
                        binding.btnToogle.isChecked = true
                        _isChecked = true
                    }  else {
                        binding.btnToogle.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.btnToogle.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked){
                viewModel.addToFavorite(username, id, avatarUrl)
            } else  {
                viewModel.removeFromFavorite(id)
            }

            binding.btnToogle.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewpager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewpager)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}