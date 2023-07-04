package com.core.adapterz

import androidx.annotation.DrawableRes


sealed class StatusAdapterZ(val itemType: Int) {
    companion object {
        const val TYPE_LOADING = Int.MAX_VALUE - 1
        const val TYPE_LOAD_MORE = Int.MAX_VALUE - 2
        const val TYPE_REFRESH = Int.MAX_VALUE - 3
        const val TYPE_ERROR = Int.MAX_VALUE - 4
        const val TYPE_NONE = Int.MAX_VALUE - 5
        const val TYPE_TO_ITEM = Int.MAX_VALUE - 6
        val RANGE_TYPE: IntRange = TYPE_NONE..TYPE_LOADING

        const val REQUEST_LOAD_MORE_FAIL = 1
        const val REQUEST_RETRY = 2
        const val REQUEST_NO_DATA = 3
    }

    data class Loading(val isLoading: Boolean) :
        StatusAdapterZ(if (isLoading) TYPE_LOADING else TYPE_NONE)

    data class LoadMore(val isLoading: Boolean) :
        StatusAdapterZ(if (isLoading) TYPE_LOAD_MORE else TYPE_NONE)

    data class Refresh(val isLoading: Boolean) :
        StatusAdapterZ(if (isLoading) TYPE_REFRESH else TYPE_NONE)

    data class Error(
        val msg: String? = null,
        @DrawableRes val img: Int? = null,
        val showButton: Boolean = false,
        val textButton: String? = null,
        val requestCode: Int = -1
    ) :
        StatusAdapterZ(TYPE_ERROR)

    class None : StatusAdapterZ(TYPE_NONE)
}