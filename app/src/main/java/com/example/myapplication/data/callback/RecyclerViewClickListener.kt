package com.example.myapplication.data.callback

import android.view.View

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClick(view: View, userId: String)
}