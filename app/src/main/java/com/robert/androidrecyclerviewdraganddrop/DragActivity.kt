package com.robert.androidrecyclerviewdraganddrop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class DragActivity : AppCompatActivity(), DragListener {
    var mAdapter: RecyclerViewAdapter? = null
    var touchHelper: ItemTouchHelper? = null
    var recyclerView: RecyclerView? = null
    var items = ArrayList<String?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_drag)
        recyclerView = findViewById(R.id.recyclerView)

        bindData()
    }

    private fun bindData() {

        for (i in 1..15) {
            items.add("Item $i")
        }

        mAdapter = RecyclerViewAdapter(items, this)
        val callback: ItemTouchHelper.Callback = ItemMoveCallback(mAdapter!!)
        touchHelper = ItemTouchHelper(callback)
        touchHelper!!.attachToRecyclerView(recyclerView)
        recyclerView!!.adapter = mAdapter
    }

    override fun requestDrag(viewHolder: RecyclerView.ViewHolder?) {
        touchHelper!!.startDrag(viewHolder!!)
    }
}