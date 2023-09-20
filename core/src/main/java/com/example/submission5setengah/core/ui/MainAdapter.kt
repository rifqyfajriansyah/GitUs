package com.example.submission5setengah.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission5setengah.core.domain.model.UserKu
import com.example.submission5setengah.core.databinding.ItemMainBinding

class MainAdapter (private val userList: List<UserKu>, private val itemClickListener: (UserKu)->Unit) : RecyclerView.Adapter<MainAdapter.UserKuHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserKuHolder {
        val itemBinding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserKuHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: UserKuHolder, position: Int) {
        holder.bind(userList[position], itemClickListener)
    }

    override fun getItemCount(): Int = userList.size

    inner class UserKuHolder(private val itemBinding: ItemMainBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(domUser: UserKu, itemClickListener: (UserKu) -> Unit) {

            itemBinding.userInfoNama.text = domUser.username
            Glide.with(itemBinding.root)
                .load(domUser.imageUrl)
                .into(itemBinding.userInfoImage)
            itemBinding.root.setOnClickListener { itemClickListener(domUser) }

        }
    }
}