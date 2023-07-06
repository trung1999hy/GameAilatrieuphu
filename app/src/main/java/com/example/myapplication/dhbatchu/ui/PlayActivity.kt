package com.example.myapplication.dhbatchu.ui


import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.core.BaseActivity
import com.example.myapplication.Constants.EXTENSION
import com.example.myapplication.Constants.FOLDER_ASSETS
import com.example.myapplication.Constants.TOTAL_REQUEST
import com.example.myapplication.MainApp
import com.example.myapplication.Preference
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityPlayBinding
import com.example.myapplication.dhbatchu.ContainActivity
import com.example.myapplication.dhbatchu.ContainActivity.Companion.getInstance
import com.example.myapplication.dhbatchu.adapter.ResultAdapter
import com.example.myapplication.dhbatchu.adapter.ValueAdapter
import com.example.myapplication.dhbatchu.controller.AudioController
import com.example.myapplication.dhbatchu.dialog.DialogResult
import com.example.myapplication.dhbatchu.utils.Utils
import com.utils.ext.clickWithDebounce
import java.io.IOException

class PlayActivity : BaseActivity<ActivityPlayBinding>() {

    private var pos = 1
    private var manager: AssetManager? = null
    private var resultValue: ResultValue? = null
    private val resultAdapter by lazy { ResultAdapter() }
    private val valueAdapter by lazy { ValueAdapter() }

    private var audioController: AudioController? = null
    private var listValue = mutableListOf<ItemValue>()
    private var listTextResult = mutableListOf<String>()
    private var listTextSelect = mutableListOf<String>()

    private val dialogResult by lazy { DialogResult() }
    private lateinit var prefsHelper: Preference

    private var isHelp = false


    override fun getLayoutBinding(): ActivityPlayBinding {
        return ActivityPlayBinding.inflate(layoutInflater)
    }

    override fun updateUI(savedInstanceState: Bundle?) {
        manager = assets
        prefsHelper = Preference.buildInstance(this)!!
        audioController = AudioController(this) {}
        dialogResult.setListener {
            prefsHelper?.setCoin(prefsHelper?.getCoin()!! - 1)
            startGame()
        }
        binding.imgBack.clickWithDebounce {
            onBackPressed()
        }
        binding.imgHelp.setOnImageClickListener {
            isHelp = true
            toast("Vui lòng chọn ô kết quả để mở")
            val animShake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
            binding.listResult.startAnimation(animShake)
        }
        binding.imgCoinInput.setOnImageClickListener {
            openScreenWithContainer(this, StoreFragment::class.java, null)
        }
        pos = getAmountListRequest()
        startGame()
    }

    override fun onResume() {
        super.onResume()
        binding.txtGold.text = prefsHelper?.getCoin().toString()
    }

    private fun getAmountListRequest(): Int {
//        val file: Array<String> = manager?.list(FOLDER_ASSETS) as Array<String>
//        return file.size
        return prefsHelper?.getPosActive()!!
    }

    private fun startGame() {
        audioController?.playSoundNewGame()
        initData()
        readResult()
        setListValue()
        prefsHelper?.setPosActive(prefsHelper.getPosActive()+1)
        binding.txtLevel.text = String.format(
            resources.getString(R.string.level),
            TOTAL_REQUEST - prefsHelper?.getPosActive()!! -1
        )
        binding.txtGold.text = prefsHelper.getCoin().toString()

    }

    private fun initData() {
        try {
            val imgStream = manager?.open("$FOLDER_ASSETS/$pos$EXTENSION")
            val d: Drawable? = Drawable.createFromStream(imgStream, null)
            binding.imgQuestion.setImageDrawable(d)
        } catch (e: IOException) {
            e.printStackTrace()
            onBackPressed()
        }
    }

    private fun setHelpUser(pos: Int) {
        resultAdapter.notifyItemChangedZ(pos, listTextResult[pos])
        prefsHelper?.setCoin(prefsHelper?.getCoin()!! - 1)
        binding.txtGold.text = prefsHelper?.getCoin().toString()
    }

    private fun readResult() {
        val result = resources.openRawResource(R.raw.result).bufferedReader().use { it.readText() }
        val value = result.split("}")
        resultValue = Utils.getResultValue(value[pos - 1])
        resultValue?.let { dialogResult.setTitle(it.titleSub) }
        listTextResult = Utils.getListResult(resultValue?.title)
        binding.listResult.layoutManager = GridLayoutManager(this, 7)
        binding.listResult.adapter = resultAdapter
        resultAdapter.items = Utils.getListResultDf(resultValue?.title)
        resultAdapter.setOnItemClickListener { item, pos ->
            audioController?.playSoundClick()
            if (isHelp) {
                isHelp = false
                setHelpUser(pos)
            } else if (item != "") {
                resultAdapter.notifyItemChangedZ(pos, "")
                reverseValueList(item)
            }
        }
    }

    private fun setListValue() {
        binding.listSelect.layoutManager = GridLayoutManager(this, 7)
        binding.listSelect.adapter = valueAdapter
        listValue = Utils.setListValue(resultValue?.title)
        valueAdapter.items = listValue
        valueAdapter.setOnItemClickListener { item, pos ->
            audioController?.playSoundClick()
            if (!checkValueSelectEnd()) {
                for (i in listTextSelect.indices) {
                    if (listTextSelect[i] == "") {
                        resultAdapter.notifyItemChangedZ(i, item)
                        break
                    }
                }
                valueAdapter.items[pos].selected = true
                valueAdapter.notifyItemChangedZ(pos)
                checkValueSelectEnd()
            }
        }
    }

    private fun reverseValueList(value: String) {
        for (i in listValue.indices) {
            if (value == listValue[i].characters && listValue[i].selected) {
                val itemValue = valueAdapter.items[i]
                itemValue.selected = false
                valueAdapter.notifyItemChangedZ(i)
                break
            }
        }
    }

    private fun checkValueSelectEnd(): Boolean {
        listTextSelect = resultAdapter.items
        for (i in listTextSelect.indices) {
            if (listTextSelect[i] == "") return false
        }
        if (listTextSelect.hashCode() == listTextResult.hashCode()) {
            showDiaLogResult(true)
        } else showDiaLogResult(false)
        return true
    }

    private fun showDiaLogResult(isSuccess: Boolean) {
        if (isSuccess) dialogResult.show(this.supportFragmentManager, DialogResult::class.java.name)
        else audioController?.playSoundFalse()
    }

    override fun onPause() {
        super.onPause()
        audioController?.releaseSound()
    }

    private fun openScreenWithContainer(
        context: Context,
        fragmentClazz: Class<*>,
        bundle: Bundle?,
    ) {
        getInstance(fragmentClazz, bundle)?.let {
            it.open(true)
        }.run {
            context.startActivity(Intent(context, ContainActivity::class.java))
        }
    }

}