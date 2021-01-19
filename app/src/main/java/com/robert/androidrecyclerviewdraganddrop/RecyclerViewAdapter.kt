package com.robert.androidrecyclerviewdraganddrop

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.robert.androidrecyclerviewdraganddrop.ItemMoveCallback.ItemTouchHelperContract
import com.robert.androidrecyclerviewdraganddrop.RecyclerViewAdapter.MyViewHolder
import kotlin.collections.ArrayList
import java.util.Collections

class RecyclerViewAdapter(private val data: ArrayList<String?>,
                          private val mDragListener: DragListener) : RecyclerView.Adapter<MyViewHolder>(), ItemTouchHelperContract {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_row, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mTitle.text = data[position]

        holder.rowView.setOnTouchListener { _, event ->

            if (event.action == MotionEvent.ACTION_DOWN) {
                mDragListener.requestDrag(holder)
            }
            false
        }

        holder.imageView.setOnTouchListener { _, event ->

            if (event.action == MotionEvent.ACTION_DOWN) {
                mDragListener.requestDrag(holder)
            }
            false
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(data, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(data, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onRowSelected(myViewHolder: MyViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY)
    }

    override fun onRowClear(myViewHolder: MyViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE)
    }

    inner class MyViewHolder(var rowView: View) : RecyclerView.ViewHolder(rowView) {
        val mTitle: TextView = itemView.findViewById(R.id.txtTitle)
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}