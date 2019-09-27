package com.example.trendingrepo.ui.main


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
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

    private lateinit var shimmer: ShimmerFrameLayout
    private lateinit var toolbar : Toolbar
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
        setToolbar(view)
        initShimmer(view)
        setAdapter()
    }

    private fun setToolbar(view: View) {
        toolbar = view.findViewById(R.id.my_toolbar)
        toolbar.inflateMenu(R.menu.main_menu)
        toolbar.setOnMenuItemClickListener {item->
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

    private fun initShimmer(view: View) {
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

    override fun setTrendingData(list: List<TrendingResponse>) {
        trendingList  = list
        trendingAdapter.run {
            items = trendingList
            notifyDataSetChanged()
        }
    }

    override fun showLoadingState(loading: Boolean) {
        if (loading) {
            shimmer.startShimmerAnimation()
        } else {
            shimmer.stopShimmerAnimation()
            shimmer.visibility = View.GONE
        }
    }

    override fun onCellClick(cell: Cell) {
    }

}
