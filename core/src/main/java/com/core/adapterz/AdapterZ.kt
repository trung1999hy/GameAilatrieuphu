package com.core.adapterz

import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.core.BaseViewHolder

abstract class AdapterZ<V : ViewBinding, O : Any> :
    RecyclerView.Adapter<BaseViewHolder<ViewBinding>>() {

    var items: MutableList<O> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var isReverse: Boolean = false

    var isClearWhenLoading = true

    var type: StatusAdapterZ = StatusAdapterZ.None()
        private set

    fun setStatus(status: StatusAdapterZ) {
        val posNeedUpdate = if (isReverse) 0 else items.size
        Handler(Looper.getMainLooper()).post {
            if (status is StatusAdapterZ.Refresh) {
                refreshLayout?.isRefreshing = status.isLoading
            } else {
                if (refreshLayout?.isRefreshing == true) refreshLayout?.isRefreshing = false
            }
            if (isClearWhenLoading && status is StatusAdapterZ.Loading && status.isLoading) {
                items.clear()
                notifyDataSetChanged()
            }
            type = status
            notifyItemChanged(posNeedUpdate)
        }
    }

    private var refreshLayout: SwipeRefreshLayout? = null
    fun attachWithRefreshLayout(refreshLayout: SwipeRefreshLayout) {
        this.refreshLayout = refreshLayout
    }

    override fun getItemViewType(position: Int): Int {
        return if (isReverse) {
            if (position == 0) type.itemType else getItemViewTypeZ(position - 1)
        } else {
            if (position == itemCountZ) type.itemType else getItemViewTypeZ(position)
        }
    }

    @IntRange(from = 0, to = StatusAdapterZ.TYPE_TO_ITEM.toLong())
    open fun getItemViewTypeZ(position: Int): Int {
        return 0
    }

    open fun getSpanSize(pos: Int, rowStatus: Int, rowNormal: (viewType: Int) -> Int = { 1 }): Int {
        return when (getItemViewType(pos)) {
            in StatusAdapterZ.RANGE_TYPE -> rowStatus
            else -> rowNormal(getItemViewType(pos))
        }
    }

    override fun getItemCount(): Int {
        return itemCountZ
    }

    open val itemCountZ: Int get() = items.size

    open fun layoutLoadingVH(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding>? {
        return null
    }

    open fun layoutLoadMoreVH(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding>? {
        return null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding> {
        return onCreateViewHolderZ(parent, viewType)
    }

    abstract fun onCreateViewHolderZ(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding>

    abstract fun onBindViewHolderZ(holder: BaseViewHolder<V>, position: Int)
    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding>, position: Int) {
        when (getItemViewType(position)) {
            in 0..StatusAdapterZ.TYPE_TO_ITEM -> {
                onBindViewHolderZ(
                    holder as BaseViewHolder<V>,
                    getCurrentPositionZ(position)
                )
            }
//            StatusAdapterZ.TYPE_ERROR -> {
//                with(holder as BaseViewHolder<ItemBaseErrorBinding>) {
//                    val item =
//                        if (type is StatusAdapterZ.Error) type as StatusAdapterZ.Error else null
//                    item?.msg?.let { binding.txtMsg.text = it }
//                    binding.txtMsg.isVisible = item?.msg != null
//                    binding.imgNoData.isVisible = item?.img != null
//                    item?.img?.let { binding.imgNoData.setImageResource(it) }
//                    binding.btnRetry.isVisible = item?.showButton == true
//                    binding.btnRetry.applyClickShrink()
//                    item?.textButton?.let { binding.btnRetry.text = it }
//                    binding.btnRetry.setOnClickListener {
//                        onActionWhenErrorCallback?.setOnActionWhenErrorListener(
//                            item?.requestCode ?: -1
//                        )
//                    }
//                }
//            }
        }
    }

    fun onActionWhenErrorListener(callback: (resultCode: Int) -> Unit) {
        onActionWhenErrorCallback = OnActionWhenErrorCallback(callback)
    }

    private fun interface OnActionWhenErrorCallback {
        fun setOnActionWhenErrorListener(resultCode: Int)
    }

    private var onActionWhenErrorCallback: OnActionWhenErrorCallback? = null

    fun getCurrentPositionZ(position: Int): Int = if (isReverse) position - 1 else position

    fun getPositionFromHolderZ(position: Int): Int = if (isReverse) position + 1 else position

    fun notifyItemRemovedZ(position: Int) =
        notifyItemRemoved(getPositionFromHolderZ(position))

    fun notifyItemInsertedZ(position: Int) =
        notifyItemInserted(getPositionFromHolderZ(position))

    fun notifyItemRangeInsertedZ(positionStart: Int, positionEnd: Int) = notifyItemRangeInserted(
        getPositionFromHolderZ(positionStart),
        getPositionFromHolderZ(positionEnd),
    )

    fun notifyItemRangeRemovedZ(positionStart: Int, positionEnd: Int) = notifyItemRangeRemoved(
        getPositionFromHolderZ(positionStart),
        getPositionFromHolderZ(positionEnd),
    )

    fun notifyItemRangeChangedZ(positionStart: Int, positionEnd: Int) = notifyItemRangeChanged(
        if (isReverse) positionStart + 1 else positionStart,
        if (isReverse) positionEnd + 1 else positionEnd,
    )

    fun notifyItemChangedZ(position: Int) =
        notifyItemChanged(if (isReverse) position + 1 else position)

    fun notifyItemMovedZ(positionStart: Int, positionEnd: Int) = notifyItemMoved(
        if (isReverse) positionStart + 1 else positionStart,
        if (isReverse) positionEnd + 1 else positionEnd,
    )

    fun notifyItemChangedZ(position: Int, value: O){
        items[position] = value
        notifyItemChangedZ(position)
    }
}