package com.ihsankarim.githubuserapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ihsankarim.githubuserapp.R
import com.ihsankarim.githubuserapp.data.local.entity.FavoriteUserEntity
import com.ihsankarim.githubuserapp.databinding.ItemRowUserBinding
import com.ihsankarim.githubuserapp.ui.activity.DetailUserActivity

class FavoriteUserAdapter :
    ListAdapter<FavoriteUserEntity, FavoriteUserAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favoriteUser = getItem(position)
        holder.bind(favoriteUser)
    }

    inner class ViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUserEntity) {
            binding.apply {
                Glide.with(itemView)
                    .load(favoriteUser.avatarUrl)
                    .placeholder(R.drawable.ic_users)
                    .into(binding.ivAvatarUrl)

                tvUsername.text = favoriteUser.username

                itemView.setOnClickListener {
                    val context = itemView.context
                    val intent = Intent(context, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_USERNAME, favoriteUser.username)
                    context.startActivity(intent)
                }
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<FavoriteUserEntity>() {
        override fun areItemsTheSame(
            oldItem: FavoriteUserEntity,
            newItem: FavoriteUserEntity
        ): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(
            oldItem: FavoriteUserEntity,
            newItem: FavoriteUserEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}
