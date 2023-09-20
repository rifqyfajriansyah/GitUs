package com.example.submission5setengah.ui.main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission5setengah.MyApplication
import com.example.submission5setengah.R
import com.example.submission5setengah.core.domain.model.UserKu
import com.example.submission5setengah.core.ui.MainAdapter
import com.example.submission5setengah.databinding.ActivityMainBinding
import com.example.submission5setengah.ui.ViewModelFactory
import com.example.submission5setengah.ui.detail.DetailActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: MainViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val layoutManager = LinearLayoutManager(this)
        binding?.mainRecycle?.layoutManager = layoutManager


        binding?.mainSearch?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.findList(query).observe(this@MainActivity){ result ->
                    if (result != null) {
                        when (result) {
                            is com.example.submission5setengah.core.data.Resource.Loading -> {
                                showLoading(true)
                            }
                            is com.example.submission5setengah.core.data.Resource.Success -> {
                                showLoading(false)
                                setListData(result.data)
                            }
                            is com.example.submission5setengah.core.data.Resource.Error -> {
                                showLoading(false)
                                Toast.makeText(
                                    this@MainActivity,
                                    "Terjadi kesalahan" + result.error,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_listlove ->{
                //val intent = Intent(this, FavoritActivity::class.java)
                //startActivity(intent)

                val uri = Uri.parse("submission5setengah://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun setListData(userList: List<UserKu>) {

        val adapter = MainAdapter(userList) { userInfo ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, userInfo)
            startActivity(intent)
        }
        binding?.mainRecycle?.adapter = adapter

    }

    private fun showLoading(isLoading: Boolean) {

        binding?.mainProgBar?.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}