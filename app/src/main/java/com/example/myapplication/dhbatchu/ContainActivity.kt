package com.example.myapplication.dhbatchu

import android.os.Bundle
import com.core.BaseActivity
import com.example.myapplication.databinding.ActivityContainBinding

class ContainActivity : BaseActivity<ActivityContainBinding>() {

    companion object {
        private var fragmentClazz: Class<*>? = null
        private var bundle: Bundle? = null
        private var instance: ContainActivity? = null

        @JvmStatic
        fun getInstance(fragmentClazz: Class<*>, bundle: Bundle?): ContainActivity? {
            Companion.fragmentClazz = fragmentClazz
            Companion.bundle = bundle
            return instance
        }
    }

    override fun getLayoutBinding(): ActivityContainBinding {
        return ActivityContainBinding.inflate(layoutInflater)
    }

    override fun updateUI(savedInstanceState: Bundle?) {
        instance = this
        open(false)
    }

    fun open(addBackStack: Boolean) {
        fragmentClazz?.let { openFragment(binding.frameLayout.id, it, bundle, addBackStack) }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentClazz = null
        bundle = null
        instance = null
    }
}