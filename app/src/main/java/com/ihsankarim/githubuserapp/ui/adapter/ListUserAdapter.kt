package com.ihsankarim.githubuserapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ihsankarim.githubuserapp.data.remote.response.GithubResponse
import com.ihsankarim.githubuserapp.databinding.ItemRowUserBinding

class ListUserAdapter(
    private var listUser: ArrayList<GithubResponse>,
    private val onItemClick: (GithubResponse) -> Unit
) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    fun setUserList(userList: ArrayList<GithubResponse>) {
        listUser = userList
        notifyDataSetChanged()
    }

    inner class ListViewHolder(var binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    private fun ImageView.setImageGlide(context: Context, imageUrl: String) {
        Glide.with(context)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]

        holder.itemView.setOnClickListener {
            onItemClick(user)
        }

        holder.binding.apply {
            tvUsername.text = user.login
            ivAvatarUrl.setImageGlide(holder.itemView.context, user.avatarUrl)
        }
    }
}