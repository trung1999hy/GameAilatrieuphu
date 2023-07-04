package com.example.myapplication.dhbatchu.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.viewbinding.ViewBinding
import com.core.BaseViewHolder
import com.core.OnItemClick
import com.core.adapterz.AdapterZ
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemResultBinding

class ResultAdapter : AdapterZ<ItemResultBinding, String>() {


    override fun onCreateViewHolderZ(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewBinding> {
        return BaseViewHolder(
            ItemResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolderZ(holder: BaseViewHolder<ItemResultBinding>, position: Int) {
        val item = items[position] as String
        with(holder) {
            binding.txtValue.text = item
            if (item == "-") holder.binding.root.visibility = View.INVISIBLE
            else holder.binding.root.visibility = View.VISIBLE
            binding.imgResult.setOnImageClickListener {
                onItemClick?.onItemClickListener(item, position)
            }
            if (item == "") binding.imgResult.setImageDrawable(itemView.context.getDrawable(R.drawable.bt_item_result))
            else binding.imgResult.setImageDrawable(itemView.context.getDrawable(R.drawable.bt_item))
        }
    }

    private var onItemClick: OnItemClick<String>? = null

    fun setOnItemClickListener(callback: (item: String, pos: Int) -> Unit) {
        onItemClick = OnItemClick(callback)
    }
}