package com.example.myapplication.ui.dashboard.navigation.home.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.callback.RecyclerViewClickListener
import com.example.myapplication.databinding.ItemUserBinding
import com.example.myapplication.util.getProgressDrawable
import com.example.myapplication.util.loadImage

class HomeUserAdapter(private val userList: ArrayList<DataItem>, private val listener: RecyclerViewClickListener) :
    RecyclerView.Adapter<HomeUserAdapter.UserViewHolder>() {

    fun updateUserList(newUserList: List<DataItem>) {
        userList.clear()
        userList.addAll(newUserList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.homeUserBinding.user = userList[position]
        holder.homeUserBinding.imgUser.loadImage(
            userList[position].avatar,
            getProgressDrawable(holder.homeUserBinding.imgUser.context)
        )
        holder.homeUserBinding.root.setOnClickListener {
            listener.onRecyclerViewItemClick(it, userList[position].id.toString())
        }
    }

    override fun getItemCount() = userList.size

    class UserViewHolder(var homeUserBinding: ItemUserBinding) : RecyclerView.ViewHolder(homeUserBinding.root)

}