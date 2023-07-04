package com.widget

/**
 * Created by KO Huyn on 01/08/2021.
 */
import android.util.TypedValue
import android.view.*
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.*
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.core.BaseViewHolder
import com.core.R
import com.core.databinding.SpinnerLayoutBinding
import com.core.databinding.SpinnerLayoutItemBinding
import com.utils.UIHelper
import com.utils.ext.clickWithDebounce
import com.utils.ext.setTextColorz

class BaseSpinner<T : Any> constructor(private val view: View) {
    private var onSelectedItem: OnSelectedItemSpinner<T>? = null
    private var items: MutableList<T?> = mutableListOf()
    private var isAll: Boolean = false
    private var popupWindow = PopupWindow(view.context)
    private val adapterSpinner by lazy { SpinnerAdapter<T>(textSize) }

    private var isShowUp = false

    @Dimension
    private var widthWindow = 0

    @Dimension
    private var textSize =
        if (view is TextView) view.textSize else view.resources.getDimension(R.dimen.text_16)

    private var isDivider = false

    @ColorRes
    private var backgroundColor: Int = R.color.colorGray

    fun setDataSource(itemsSpinner: List<T>): BaseSpinner<T> {
        this.items = itemsSpinner.toMutableList()
        return this
    }

    fun setChoiceAll(isAll: Boolean): BaseSpinner<T> {
        this.isAll = isAll
        return this
    }

    fun setBackGroundSpinner(@ColorRes color: Int): BaseSpinner<T> {
        this.backgroundColor = color
        return this
    }

    fun setDividerItem(isDivider: Boolean): BaseSpinner<T> {
        this.isDivider = isDivider
        return this
    }

    fun setTextSize(@Dimension textSize: Float): BaseSpinner<T> {
        this.textSize = textSize
        return this
    }

    fun setTextColor(@ColorRes textColor: Int): BaseSpinner<T> {
        adapterSpinner.textColor = textColor
        return this
    }

    fun setShowUp(isShowUp: Boolean = false): BaseSpinner<T> {
        this.isShowUp = isShowUp
        return this
    }

    fun setWidthWindow(@Dimension width: Float): BaseSpinner<T> {
        this.widthWindow = width.toInt()
        return this
    }

    fun build(): BaseSpinner<T> {
        val layout = SpinnerLayoutBinding.inflate(LayoutInflater.from(view.context))
        with(layout) {
            body.setBackgroundColor(
                ResourcesCompat.getColor(
                    root.resources,
                    backgroundColor,
                    null
                )
            )
            recyclerViewSpinner.run {
                if (isDivider) {
                    addItemDecoration(
                        DividerItemDecoration(
                            context,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                }
                adapterSpinner.items = items
                if (isAll) {
                    items.add(0, null)
                }
                adapter = adapterSpinner
                layoutManager = LinearLayoutManager(context)
            }
        }
        popupWindow.run {
            contentView = layout.root
            width = if (widthWindow != 0) {
                widthWindow
            } else {
                view.width
            }
            this.height = WindowManager.LayoutParams.WRAP_CONTENT
            isOutsideTouchable = true
            isFocusable = true
            elevation = 10F
            setBackgroundDrawable(null)
        }
        popupWindow.contentView.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        val heightScreen = UIHelper.getScreenHeight(view.context)
        val out = IntArray(2)
        view.getLocationOnScreen(out)
        val partScreen = (heightScreen * 2) / 3
        if (out[1] > partScreen || isShowUp) {
            popupWindow.animationStyle = R.style.StyleDropUpSpinner
            val heightPopup =
                if (popupWindow.contentView.measuredHeight > heightScreen) heightScreen else popupWindow.contentView.measuredHeight
            popupWindow.showAsDropDown(
                view,
                0,
                -view.height - heightPopup
            )
        } else {
            popupWindow.animationStyle = R.style.StyleDropDownSpinner
            popupWindow.showAsDropDown(view)
        }
        adapterSpinner.onSelectedItem =
            OnSelectedItemSpinner { position, item ->
                if (view is TextView) {
                    view.text = item?.toString() ?: view.resources.getString(R.string.text_all)
                }
                onSelectedItem?.setOnSelectedItem(position, item)
                popupWindow.dismiss()
            }
        return this
    }

    fun setOnSelectedItemCallback(listener: (item: T?) -> Unit) {
        onSelectedItem =
            OnSelectedItemSpinner<T> { _, item -> listener.invoke(item) }
    }

    class SpinnerAdapter<T : Any>(private val textSize: Float) :
        RecyclerView.Adapter<BaseViewHolder<SpinnerLayoutItemBinding>>() {
        var items = mutableListOf<T?>()
            set(value) {
                field = value
                notifyDataSetChanged()
            }
        var onSelectedItem: OnSelectedItemSpinner<T>? = null

        @ColorRes
        var textColor: Int = R.color.colorBlack
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): BaseViewHolder<SpinnerLayoutItemBinding> =
            BaseViewHolder(
                SpinnerLayoutItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(
            holder: BaseViewHolder<SpinnerLayoutItemBinding>,
            position: Int
        ) {
            with(holder) {
                binding.txtTitleSpinner.setTextColorz(textColor)
                binding.txtTitleSpinner.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
                binding.txtTitleSpinner.text =
                    items[position]?.toString()
                        ?: holder.itemView.context.getString(R.string.text_all)
                binding.root.clickWithDebounce {
                    onSelectedItem?.setOnSelectedItem(
                        position,
                        items[position]
                    )
                }
            }
        }
    }

    fun interface OnSelectedItemSpinner<T : Any> {
        fun setOnSelectedItem(position: Int, item: T?)
    }
}