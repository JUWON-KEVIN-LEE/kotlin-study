package com.kevin.gitrepos.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kevin.gitrepos.R
import com.kevin.gitrepos.data.model.Repo
import com.kevin.gitrepos.util.ResourcesWrapper

/**
 * Created by quf93 on 2018-04-18.
 */
class RepoAdapter(private val resourcesWrapper: ResourcesWrapper): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val repos = mutableListOf<Repo?>()

    override fun getItemViewType(position: Int): Int =
            if(repos[position] == null) VIEW_TYPE_PROGRESS
            else VIEW_TYPE_ITEM

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_ITEM -> ItemViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_repo, parent, false))
            VIEW_TYPE_PROGRESS -> ProgressViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_loading, parent, false))
            else -> throw Exception("Unknown view type")
        }
    }

    override fun getItemCount(): Int = repos.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repo = repos[position]
        when(holder?.itemViewType) {
            VIEW_TYPE_ITEM -> {
                with(holder as ItemViewHolder) {
                    name.text = repo?.name
                    language.text = String.format("%s: %s", resourcesWrapper.getString(R.string.language), repo?.language)
                    forks.text = String.format("%s: %s", resourcesWrapper.getString(R.string.forks), repo?.forks_count)
                    watchers.text = String.format("%s: %s", resourcesWrapper.getString(R.string.watchers), repo?.watchers_count)
                    openIssues.text = String.format("%s: %s", resourcesWrapper.getString(R.string.open_issues), repo?.open_issues_count)
                }
            }
        }
    }


    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val language: TextView = itemView.findViewById(R.id.language)
        val forks: TextView = itemView.findViewById(R.id.forks)
        val watchers: TextView = itemView.findViewById(R.id.watchers)
        val openIssues: TextView = itemView.findViewById(R.id.open_issues)
    }

    inner class ProgressViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_PROGRESS = 1
    }
}