package com.dicoding.butgetin.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.butgetin.R
import com.dicoding.butgetin.model.profile.ProfileMenu

class ProfileMenuAdapter(
    private val menuList: List<ProfileMenu>,
    private val onItemClick: (ProfileMenu) -> Unit
) : RecyclerView.Adapter<ProfileMenuAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgIconAccount: ImageView = itemView.findViewById(R.id.img_icon_account)
        val tvAccount: TextView = itemView.findViewById(R.id.tv_account)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_profile_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = menuList[position]
        holder.imgIconAccount.setImageResource(menu.icon)
        holder.tvAccount.text = menu.name
        holder.itemView.setOnClickListener { onItemClick(menu) }
    }

    override fun getItemCount() = menuList.size
}