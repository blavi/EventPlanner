package com.example.pimpmywed.bindings

import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pimpmywed.utils.SearchResult
import com.example.pimpmywed.utils.ValidResult
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("recyclerViewData")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, items: List<T>?) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        items?.let {
            (recyclerView.adapter as BindableAdapter<T>).setData(items)
        }
    }
}

@BindingAdapter("recyclerViewDataSearch")
fun <T> setRecyclerViewSearchProperties(recyclerView: RecyclerView, items: SearchResult?) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        items?.let {
            if (items is ValidResult) {
                (recyclerView.adapter as BindableAdapter<T>).setData(items.result as List<T>)
            } else {
                (recyclerView.adapter as BindableAdapter<T>).setData(emptyList())
            }
        }
    }
}

@BindingAdapter("message")
fun <T> setMessage(layout: TextInputLayout, stringId: Int?) {
    if (stringId != null) {
        layout.error = layout.context.getString(stringId)
    } else {
        layout.error = ""
    }
}

@BindingAdapter("textChangedListener")
fun bindTextWatcher(editText: TextInputEditText, textWatcher: TextWatcher?) {
    editText.addTextChangedListener(textWatcher)
}
