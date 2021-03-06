package com.robert.androidrecyclerviewdraganddrop

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class DragFragment : Fragment(), DragListener {
    var mAdapter: RecyclerViewAdapter? = null
    var touchHelper: ItemTouchHelper? = null
    var recyclerView: RecyclerView? = null
    var items = ArrayList<String?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("DragFragment", "---> is calling...")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_drag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)

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

    companion object {
        fun newInstance(): DragFragment {
            return DragFragment()
        }
    }
}