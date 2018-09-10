package com.ahe.recyclerviewenhanced

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.support.v7.widget.LinearLayoutManager
import android.view.View


class MainActivity : AppCompatActivity(),RecyclerTouchListener.RecyclerTouchListenerHelper {

    private var touchListener: OnActivityTouchListener? = null
    var mRecyclerView: RecyclerView? = null
    var mAdapter: MainAdapter? = null
    private var onTouchListener: RecyclerTouchListener? = null

    override fun setOnActivityTouchListener(listener: OnActivityTouchListener?) {
        this.touchListener = listener;
    }

    override fun onResume() {
        super.onResume()
        mRecyclerView!!.addOnItemTouchListener(onTouchListener);
    }

    override fun onPause() {
        super.onPause()
        mRecyclerView!!.removeOnItemTouchListener(onTouchListener);
    }

    fun  getData():List<RowModel> {
        var list =  ArrayList<RowModel>()
        for (i in 0..25) {
            list.add( RowModel("Row " + (i + 1), "Some Text... "));
        }
        return list;
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (touchListener != null) touchListener!!.getTouchCoordinates(ev!!);
        return super.dispatchTouchEvent(ev)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById(R.id.recyclerView)
        mAdapter = MainAdapter(this, getData())
        mRecyclerView!!.adapter = mAdapter
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)


        onTouchListener = RecyclerTouchListener(this,mRecyclerView)

        onTouchListener!!
                //.setIndependentViews(R.id.rowButton)
                //.setViewsToFade(R.id.rowButton)
                .setClickable(object : RecyclerTouchListener.OnRowClickListener{
                    override fun onRowClicked(position: Int) {
                        ToastUtil.makeToast(applicationContext, "Row " + (position + 1) + " clicked!");
                    }

                    override fun onIndependentViewClicked(independentViewID: Int, position: Int) {
                        ToastUtil.makeToast(getApplicationContext(), "Button in row " + (position + 1) + " clicked!");
                    }

                })
                .setLongClickable(true,object : RecyclerTouchListener.OnRowLongClickListener{
                    override fun onRowLongClicked(position: Int) {
                        ToastUtil.makeToast(getApplicationContext(), "Row " + (position + 1) + " long clicked!");
                    }

                })
                .setSwipeOptionViews(R.id.add, R.id.edit, R.id.change)
                .setSwipeable(R.id.rowFG, R.id.rowBG, object :RecyclerTouchListener.OnSwipeOptionsClickListener{
                    override fun onSwipeOptionClicked(viewID: Int, position: Int) {
                        var message = "";
                        if (viewID == R.id.add) {
                            message += "Add";
                        } else if (viewID == R.id.edit) {
                            message += "Edit";
                        } else if (viewID == R.id.change) {
                            message += "Change";
                        }
                        message += " clicked for row " + (position + 1);
                        ToastUtil.makeToast(getApplicationContext(), message);
                    }

                })




    }


}
