package com.koperko.jll

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koperko.jll.model.Repository
import kotlinx.android.synthetic.main.item_repository.view.*
import kotlin.properties.Delegates

class RepositoriesAdapter(repositories: List<Repository>): RecyclerView.Adapter<ViewHolder>() {

    var repositories by Delegates.observable(repositories) { _, _, _ ->
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false))

    override fun getItemCount(): Int = repositories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = repositories[position]

        holder.itemView.nameView.text = item.name
        holder.itemView.descriptionView.text = item.description
    }

}


class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {

}