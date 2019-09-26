package com.example.trendingrepo.ui.main


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trendingrepo.R
import com.example.trendingrepo.base.common.Cell
import com.example.trendingrepo.model.TrendingResponse
import com.example.trendingrepo.ui.main.adapter.TrendingAdapter
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : AbstractMainFragment(), TrendingAdapter.CellListener {

    override fun getLayoutRes() = R.layout.main_fragment

    private lateinit var trendingAdapter: TrendingAdapter

    companion object {
        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun viewInitialization(view: View) {
        super.viewInitialization(view)
        setListener()
        setAdapter()
    }


    private fun setAdapter() {
        trendingAdapter = TrendingAdapter(listOf(), this)
        with(trendingRecycler) {
            adapter = trendingAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun setListener() {

    }

    override fun setTrendingData(list: List<TrendingResponse>) {
        trendingAdapter.run {
            items = list
            notifyDataSetChanged()
        }
    }

    override fun onCellClick(cell: Cell) {
    }

}
