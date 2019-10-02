package com.example.trendingrepo.ui.main


import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.transition.TransitionManager
import com.example.trendingrepo.R
import com.example.trendingrepo.base.common.Cell
import com.example.trendingrepo.model.TrendingResponse
import com.example.trendingrepo.ui.main.adapter.TrendingAdapter
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*


class MainFragment : AbstractMainFragment(), TrendingAdapter.CellListener {

    override fun getLayoutRes() = R.layout.main_fragment

    private lateinit var trendingAdapter: TrendingAdapter
    private lateinit var trendingList: List<TrendingResponse>


    companion object {
        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun viewInitialization(view: View) {
        super.viewInitialization(view)
        setToolbar()
        setSwipeRefresh()
        setAdapter()
        setClickListener()
    }

    private fun setClickListener() {
        tv_retry.setOnClickListener {
            shimmer_view_container.visibility = View.VISIBLE
            swipe_refresh_layout.visibility = View.VISIBLE
            cl_error_view.visibility = View.GONE
            fetchData("kotlin")
        }
    }

    private fun setSwipeRefresh() {
        swipe_refresh_layout.setOnRefreshListener {
            fetchData("kotlin")
            shimmer_view_container.startShimmerAnimation()
        }
    }

    private fun setToolbar() {
        my_toolbar.inflateMenu(R.menu.main_menu)
        my_toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_sort_name -> sortByName()
                R.id.action_sort_star -> sortByStars()
            }
            true
        }
    }


    private fun sortByStars() {
        val starSortedList = trendingList.sortedWith(compareBy { it.stars })
        setTrendingData(starSortedList)
    }

    private fun sortByName() {
        val nameSortedList = trendingList.sortedWith(compareBy { it.name })
        setTrendingData(nameSortedList)
    }

    override fun onPause() {
        super.onPause()
        shimmer_view_container.stopShimmerAnimation()
    }


    private fun setAdapter() {
        trendingAdapter = TrendingAdapter(listOf(), this)
        with(trendingRecycler) {
            adapter = trendingAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    override fun setTrendingData(list: List<TrendingResponse>) {
        trendingList = list
        trendingAdapter.run {
            items = trendingList
            notifyDataSetChanged()
        }
    }

    override fun showLoadingState(loading: Boolean) {
        if (loading) {
            shimmer_view_container.startShimmerAnimation()
        } else {
            shimmer_view_container.stopShimmerAnimation()
            shimmer_view_container.visibility = View.GONE
            swipe_refresh_layout.isRefreshing = false
        }
    }

    override fun onFailRetry() {
        cl_error_view.visibility = View.VISIBLE
        shimmer_view_container.visibility = View.GONE
        shimmer_view_container.stopShimmerAnimation()
        swipe_refresh_layout.visibility = View.GONE
    }

    override fun onCellClick(cell: Cell, currPos:Int) {
        trendingAdapter.notifyDataSetChanged()
    }

}
