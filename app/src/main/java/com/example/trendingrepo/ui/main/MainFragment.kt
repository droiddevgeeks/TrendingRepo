package com.example.trendingrepo.ui.main


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trendingrepo.R
import com.example.trendingrepo.base.common.Cell
import com.example.trendingrepo.model.TrendingResponse
import com.example.trendingrepo.ui.main.adapter.TrendingAdapter
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : AbstractMainFragment(), TrendingAdapter.CellListener {

    override fun getLayoutRes() = R.layout.main_fragment

    private lateinit var trendingAdapter: TrendingAdapter

    private lateinit var shimmer : ShimmerFrameLayout

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
        initShimmer(view)
        setListener()
        setAdapter()
    }

    private fun initShimmer(view:View) {
        shimmer = view.findViewById(R.id.shimmer_view_container)
    }

    override fun onPause() {
        super.onPause()
        shimmer.stopShimmerAnimation()
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

    override fun showLoadingState(loading: Boolean) {
        if(loading){
            shimmer.startShimmerAnimation()
        }else{
            shimmer.stopShimmerAnimation()
            shimmer.visibility = View.GONE
        }
    }

    override fun onCellClick(cell: Cell) {
    }

}
