package com.example.submission5setengah.ui.detail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission5setengah.MyApplication
import com.example.submission5setengah.core.domain.model.UserKu
import com.example.submission5setengah.core.ui.MainAdapter
import com.example.submission5setengah.databinding.FragmentFollowBinding
import com.example.submission5setengah.ui.ViewModelFactory
import javax.inject.Inject

class FollowFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: DetailViewModel by viewModels {
        factory
    }

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FrameLayout? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        binding?.fragmentRec?.layoutManager = layoutManager
        val username = arguments?.getString(USERNAME)
        val param = arguments?.getInt(PARAMETER)?:0

        if (username != null) {
            viewModel.findFollow(username, param).observe(requireActivity()){result ->
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
                                activity,
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }}
        }

    }

    private fun setListData(userList: List<UserKu>) {

        val adapter = MainAdapter(userList){

        }
        binding?.fragmentRec?.adapter = adapter

    }

    private fun showLoading(isLoading: Boolean) {

        binding?.fragmentBar?.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    companion object {
        const val USERNAME = "username"
        const val PARAMETER = "param"
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}