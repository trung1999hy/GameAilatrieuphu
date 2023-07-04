package com.core

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.core.rx.Buser
import com.core.rx.RxBus
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.utils.DisposeBag
import com.utils.ToastKey
import com.utils.ext.disposedBy
import com.utils.ext.setDrawableStart
import com.widget.Boast
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.Subject


abstract class BaseFragment<T : ViewBinding> : Fragment(),
    ViewTreeObserver.OnGlobalLayoutListener, MvpView {

    private var rootView: View? = null

    lateinit var binding: T

    protected val bag by lazy { DisposeBag.create() }

    protected abstract fun getLayoutBinding(): T

    protected abstract fun updateUI(savedInstanceState: Bundle?)

    override fun onGlobalLayout() {
        rootView!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    fun addDispose(vararg disposables: Disposable) {
        bag.add(*disposables)
    }

    fun Disposable.addToBag() {
        bag.add(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView != null) {
            val parent = rootView!!.parent as ViewGroup?
            parent?.removeView(rootView)
        } else {
            try {
                binding = getLayoutBinding()
                rootView = binding.root
                rootView!!.viewTreeObserver.addOnGlobalLayoutListener(this)
            } catch (e: InflateException) {
                e.printStackTrace()
            }
        }
        return rootView
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setHideKeyboardFocus(view: View) {
        if (view !is EditText) {
            view.setOnTouchListener { _: View?, _: MotionEvent? ->
                if (activity != null) {
                    hideKeyboard()
                }
                false
            }
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setHideKeyboardFocus(innerView)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI(savedInstanceState)
    }

    override fun onDestroy() {
        bag.dispose()
        super.onDestroy()
    }

    fun <VH : RecyclerView.ViewHolder> setUpRcv(
        rcv: RecyclerView,
        adapter: RecyclerView.Adapter<VH>
    ) {
        rcv.setHasFixedSize(true)
        rcv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rcv.adapter = adapter
    }

    fun <VH : RecyclerView.ViewHolder> setUpRcv(
        rcv: RecyclerView, adapter:
        RecyclerView.Adapter<VH>,
        isHasFixedSize: Boolean,
        isNestedScrollingEnabled: Boolean
    ) {
        rcv.setHasFixedSize(isHasFixedSize)
        rcv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rcv.adapter = adapter
        rcv.isNestedScrollingEnabled = isNestedScrollingEnabled
    }

    fun <VH : RecyclerView.ViewHolder> setUpRcv(
        rcv: RecyclerView, adapter:
        RecyclerView.Adapter<VH>,
        isNestedScrollingEnabled: Boolean
    ) {
        rcv.setHasFixedSize(true)
        rcv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rcv.adapter = adapter
        rcv.isNestedScrollingEnabled = isNestedScrollingEnabled
    }

    @Throws
    open fun openFragment(
        resId: Int,
        fragmentClazz: Class<*>,
        args: Bundle?,
        addBackStack: Boolean
    ) {
        val tag = fragmentClazz.simpleName
        try {
            val isExisted =
                childFragmentManager.popBackStackImmediate(tag, 0)    // IllegalStateException
            if (!isExisted) {
                val fragment: Fragment
                try {
                    fragment = (fragmentClazz.asSubclass(Fragment::class.java)).newInstance()
                        .apply { arguments = args }

                    val transaction = childFragmentManager.beginTransaction()
                    transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                    transaction.add(resId, fragment, tag)

                    if (addBackStack) {
                        transaction.addToBackStack(tag)
                    }
                    transaction.commit()

                } catch (e: java.lang.InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws
    open fun openFragment(
        resId: Int, fragmentClazz: Class<*>, args: Bundle?, addBackStack: Boolean,
        vararg aniInt: Int
    ) {
        val tag = fragmentClazz.simpleName
        try {
            val isExisted =
                childFragmentManager.popBackStackImmediate(tag, 0)    // IllegalStateException
            if (!isExisted) {
                val fragment: Fragment
                try {
                    fragment = (fragmentClazz.asSubclass(Fragment::class.java)).newInstance()
                        .apply { arguments = args }

                    val transaction = childFragmentManager.beginTransaction()
                    transaction.setCustomAnimations(aniInt[0], aniInt[1], aniInt[2], aniInt[3])

                    transaction.add(resId, fragment, tag)

                    if (addBackStack) {
                        transaction.addToBackStack(tag)
                    }
                    transaction.commitAllowingStateLoss()

                } catch (e: java.lang.InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    open fun removeFragment(frag: Class<*>) {
        parentFragmentManager.fragments.findLast { it::class.java.simpleName == frag.simpleName }
            ?.let { fragment ->
                hideKeyboard()
                parentFragmentManager.beginTransaction().remove(fragment).commit()
                parentFragmentManager.popBackStack()
            }
    }

    fun popBackTo(clazz: Class<*>) {
        parentFragmentManager.popBackStack(clazz.simpleName,0)
    }

    fun toast(msg: String?) {
        context?.let {
            if (msg.isNullOrEmpty()) return
            Boast.makeText(it, msg).show()
        }
    }

    fun toast(msg: String, duration: Int, cancelCurrent: Boolean) {
        context?.let {
            Boast.makeText(it, msg, duration).show(cancelCurrent)
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    protected var toast: Toast? = null
    @SuppressLint("MissingInflatedId")
     fun customToast(msg: String, code: Int) {
        val dp16 = dpToPx(24)
        val dp8 = dpToPx(16)
        context?.let {
            toast = Toast(it)
            toast?.let { toast ->
                val view: View = LayoutInflater.from(context).inflate(R.layout.custom_toast, null)
                val tvToast = view.findViewById<View>(R.id.tvToast) as TextView
                val llToast = view.findViewById<View>(R.id.llToast) as LinearLayout
                context?.let { context ->
                    tvToast.setPadding(dp16,dp8,dp16,dp8)
                    when (code) {
                        ToastKey.INFO -> {
                            tvToast.setTextColor(it.resources.getColor(R.color.md_white_1000))
                            tvToast.setDrawableStart(R.drawable.ic_toast_info)
                            llToast.setBackgroundResource(R.drawable.bg_toast_info)
                        }
                        ToastKey.ERROR -> {
                            tvToast.setTextColor(it.resources.getColor(R.color.md_white_1000))
                            tvToast.setDrawableStart(R.drawable.ic_toast_error)
                            llToast.setBackgroundResource(R.drawable.bg_toast_error)
                        }
                        ToastKey.WARNING -> {
                            tvToast.setTextColor(it.resources.getColor(R.color.lightBlack))
                            tvToast.setDrawableStart(R.drawable.ic_toast_warning)
                            llToast.setBackgroundResource(R.drawable.bg_toast_warning)
                        }
                        ToastKey.DONE -> {
                            tvToast.setTextColor(it.resources.getColor(R.color.md_white_1000))
                            tvToast.setDrawableStart(R.drawable.ic_toast_done)
                            llToast.setBackgroundResource(R.drawable.bg_toast_done)
                        }
                        ToastKey.DEFAULT -> {
                            tvToast.setTextColor(it.resources.getColor(R.color.md_white_1000))
                            llToast.setBackgroundResource(R.drawable.bg_toast_default)
                        }
                    }
                }


                tvToast.text = msg
                toast.view = view
                toast.duration = Toast.LENGTH_SHORT

                toast.setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 0)
                toast.show()
            }
        }
    }

    fun showDialog() {
        activity?.let {
            if (it is BaseActivity<*>) {
                it.showDialog()
            }
        }
    }

    fun hideDialog() {
        activity?.let {
            if (it is BaseActivity<*>) {
                it.hideDialog()
            }
        }
    }

    fun hideKeyboard() {
        activity?.let {
            if (it is BaseActivity<*>) {
                it.hideKeyboard()
            }
        }
    }

    fun hideKeyboardOutSide(view: View) {
        activity?.let {
            if (it is BaseActivity<*>) {
                it.hideKeyboardOutSide(view)
            }
        }
    }

    fun hideKeyboardOutSideText(view: View) {
        activity?.let {
            if (it is BaseActivity<*>) {
                it.hideKeyboardOutSideText(view)
            }
        }
    }

    fun onBackPressed() {
        hideKeyboard()
        activity?.onBackPressed()
    }

    open fun clearAllBackStack() {
        activity?.let {
            if (it is BaseActivity<*>) {
                it.clearAllBackStack()
            }
        }
    }

    fun finish() {
        activity?.finish()
    }

    fun runOnUiThread(action: () -> Unit) {
        activity?.runOnUiThread { action() } ?: action()
    }

    fun Subject<String>.receiveTextChangesFrom(editText: EditText) {
        RxTextView.textChanges(editText)
            .subscribe { newText -> this.onNext(newText.toString()) }
            .disposedBy(bag)
    }

    fun Subject<Unit>.receiveClicksFrom(view: View) {
        RxView.clicks(view)
            .subscribe { this.onNext(Unit) }
            .disposedBy(bag)
    }

    override fun sendBuser(rxBus: RxBus, key: String?, values: Any?) {
        rxBus.send(Buser(key, values))
    }

    override fun registerBuser(rxBus: RxBus, onMessageReceived: RxBus.OnMessageReceived?) {
        rxBus.registerBuser(onMessageReceived)
    }

    override fun unRegisterBuser(rxBus: RxBus) {
        rxBus.unRegisterBuser()
    }
}