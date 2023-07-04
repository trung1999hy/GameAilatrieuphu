package com.example.myapplication.dhbatchu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.core.BaseViewHolder
import com.core.OnItemClick
import com.core.adapterz.AdapterZ
import com.example.myapplication.databinding.ItemValueBinding
import com.example.myapplication.dhbatchu.ui.ItemValue

class ValueAdapter : AdapterZ<ItemValueBinding, ItemValue>() {

    override fun onCreateViewHolderZ(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<ViewBinding> {
        return BaseViewHolder(
            ItemValueBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolderZ(holder: BaseViewHolder<ItemValueBinding>, position: Int) {
        val item = items[position]
        with(holder) {
            binding.txtValue.text = item.characters
            if (item.selected) binding.root.visibility = View.INVISIBLE
            else binding.root.visibility = View.VISIBLE
            binding.imgResult.setOnImageClickListener {
                onItemClick?.onItemClickListener(item.characters, position)
//                binding.root.visibility = View.INVISIBLE
            }
        }
    }

    private var onItemClick: OnItemClick<String>? = null

    fun setOnItemClickListener(callback: (item: String, pos: Int) -> Unit) {
        onItemClick = OnItemClick(callback)
    }
}