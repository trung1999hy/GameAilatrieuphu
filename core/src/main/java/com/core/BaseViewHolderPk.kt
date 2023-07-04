package com.core

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class ViewBindingVH<VB : ViewBinding> constructor(val binding: VB) : RecyclerView.ViewHolder(binding.root)
