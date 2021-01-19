package com.robert.androidrecyclerviewdraganddrop

import androidx.recyclerview.widget.RecyclerView

interface DragListener {
    fun requestDrag(viewHolder: RecyclerView.ViewHolder?)
}