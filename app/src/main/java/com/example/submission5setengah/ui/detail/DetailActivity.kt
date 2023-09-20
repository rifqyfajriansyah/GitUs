package com.example.submission5setengah.ui.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.submission5setengah.MyApplication
import com.example.submission5setengah.R
import com.example.submission5setengah.core.domain.model.DetailKu
import com.example.submission5setengah.core.domain.model.UserKu
import com.example.submission5setengah.databinding.ActivityDetailBinding
import com.example.submission5setengah.ui.SectionPagerAdapter
import com.example.submission5setengah.ui.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var detailKu: DetailKu
    lateinit var userInfo: UserKu
    lateinit var icon : MenuItem

    var paramLove = 0

    private val viewModel: DetailViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = TITLE
        supportActionBar?.elevation = 0f

        userInfo = intent.getParcelableExtra<UserKu>(EXTRA_DATA)!!
        viewModel.getDetail(userInfo.username).observe(this){ result ->
            if (result != null) {
                when (result) {
                    is com.example.submission5setengah.core.data.Resource.Loading -> {
                        showLoading(true)
                    }
                    is com.example.submission5setengah.core.data.Resource.Success -> {
                        showLoading(false)
                        setData(result.data)
                    }
                    is com.example.submission5setengah.core.data.Resource.Error -> {
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

        val sectionsPagerAdapter = SectionPagerAdapter(this, userInfo.username)
        binding?.detailPager?.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding!!.detailTab, binding!!.detailPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {


        menuInflater.inflate(R.menu.menu_detail, menu)

        icon = menu.getItem(0)

        viewModel.getFavo(userInfo.username).observe(this){
            setLoved(it)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_love -> {
                viewModel.addFavo(userInfo, paramLove)
            }

            R.id.action_share -> {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.setType("text/plain")
                shareIntent.setPackage("com.whatsapp")
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Cek deh profil "+detailKu.name+" mempunyai "+detailKu.follower+" follower  dan  "+detailKu.following+" following")


                try {
                    startActivity(shareIntent)
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(this, "Whatsapp have not been installed.", Toast.LENGTH_LONG).show()
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setData(userDetail : DetailKu){

        detailKu = userDetail

        binding?.apply {

            detailNama.text = userDetail.name
            detailUser.text = userDetail.username
            detailFollower.text = String.format(getString(R.string.text_follower), userDetail.follower)
            detailFollowing.text = String.format(getString(R.string.text_following), userDetail.following)

        }

        Glide.with(this)
            .load(userDetail.imageUrl)
            .into(binding?.detailFoto!!)

    }

    private fun setLoved(data: List<UserKu>){

        if(data.isEmpty()){
            paramLove = 0
            icon.icon = ContextCompat.getDrawable(this, R.drawable.ic_unlove)
        }else{
            paramLove = 1
            icon.icon = ContextCompat.getDrawable(this, R.drawable.ic_loved)
        }
    }



    private fun showLoading(isLoading: Boolean) {

        binding?.detailProgBar?.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val TITLE = "Detail User"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followe,
            R.string.follow
        )
    }
}