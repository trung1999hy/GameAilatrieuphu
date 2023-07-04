package com.example.myapplication.dhbatchu.dialog

import android.os.Bundle
import com.core.BaseDialog
import com.example.myapplication.databinding.ResultDialogBinding
import com.example.myapplication.dhbatchu.controller.AudioController


class DialogResult: BaseDialog<ResultDialogBinding>() {

    private var onPlayAction: () -> Unit = {}
    private var title = ""
    private var audioController: AudioController? = null

    fun setListener(onPlayAction: () -> Unit) {
        this.onPlayAction = onPlayAction
    }

    fun setTitle(title: String){
       this.title = title
    }

    override fun getLayoutBinding(): ResultDialogBinding {
        return ResultDialogBinding.inflate(layoutInflater)
    }

    override fun updateUI(savedInstanceState: Bundle?) {
        dialog?.setCancelable(false)
        audioController = context?.let { AudioController(it){} }
        audioController?.playSoundSuccess()
        binding.txtTitle.text = title
        binding.imgPlay.setOnImageClickListener {
            audioController?.playSoundClick()
            onPlayAction.invoke()
            dismiss()
        }

    }
}