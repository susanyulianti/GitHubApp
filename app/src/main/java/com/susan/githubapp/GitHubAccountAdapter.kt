package com.susan.githubapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.susan.githubapp.databinding.ItemRowAccountBinding

class GitHubAccountAdapter : RecyclerView.Adapter<GitHubAccountAdapter.UserViewHolder>() {

    private val list_accountgithub = ArrayList<GitHubAccount>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setListGitHubAccount(githubaccounts: ArrayList<GitHubAccount>) {
        list_accountgithub.clear()
        list_accountgithub.addAll(githubaccounts)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemRowAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gitHubAccount: GitHubAccount) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(gitHubAccount)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(gitHubAccount.avatar_url)
                    .centerCrop()
                    .into(photo)
                idAccount.text = "ID : ${gitHubAccount.id}"
                tvUsername.text = gitHubAccount.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemRowAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list_accountgithub[position])
    }

    override fun getItemCount(): Int = list_accountgithub.size

    interface OnItemClickCallback {
        fun onItemClicked(data: GitHubAccount)
    }

}