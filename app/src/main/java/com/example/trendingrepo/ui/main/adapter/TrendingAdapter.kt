package com.example.trendingrepo.ui.main.adapter

import com.example.trendingrepo.base.common.Cell
import com.example.trendingrepo.ui.main.delegate.TrendingDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class TrendingAdapter(items: List<Cell>, cellListener: CellListener) :
    ListDelegationAdapter<List<Cell>>() {

    interface CellListener {
        fun onCellClick(cell: Cell)
    }

    init {
        delegatesManager.addDelegate(TrendingDelegate(cellListener))
        setItems(items)
    }
}