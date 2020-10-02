package com.example.pimpmywed.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("recyclerViewData")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, items: List<T>?) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        items?.let {
            (recyclerView.adapter as BindableAdapter<T>).setData(items)
        }
    }
}