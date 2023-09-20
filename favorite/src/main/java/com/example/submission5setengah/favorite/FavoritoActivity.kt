package com.example.submission5setengah.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission5setengah.MyApplication
import com.example.submission5setengah.core.data.Resource
import com.example.submission5setengah.core.domain.model.UserKu
import com.example.submission5setengah.core.ui.MainAdapter
import com.example.submission5setengah.favorite.databinding.ActivityFavoritoBinding
import com.example.submission5setengah.ui.detail.DetailActivity
import javax.inject.Inject

class FavoritoActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: FavoritoViewModel

    private var _binding: ActivityFavoritoBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = (applicationContext as MyApplication).appComponent
        DaggerFavoritoComponent.factory().create(appComponent).inject(this)

        super.onCreate(savedInstanceState)
        _binding = ActivityFavoritoBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "FAVORITO"

        val layoutManager = LinearLayoutManager(this)
        binding?.favRecycle?.layoutManager = layoutManager

        viewModel.getListFavo().observe(this){ result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        setListData(result.data)
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        Toast.makeText(
                            this,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {

        binding?.favProgbar?.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    private fun setListData(userList: List<UserKu>) {

        val adapter = MainAdapter(userList) { userInfo ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, userInfo)
            startActivity(intent)

        }
        binding?.favRecycle?.adapter = adapter

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}