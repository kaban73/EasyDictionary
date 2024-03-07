package com.example.easydictionary.add

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.easydictionary.R
import com.example.easydictionary.core.ProvideViewModel

class AddDialogFragment : DialogFragment(R.layout.add_layout) {
    private lateinit var viewModel: AddViewModel
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(AddViewModel::class.java)
        dialog.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                viewModel.comeback()
                dismiss()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText = view.findViewById<EditText>(R.id.translateInputEditText)
        val radioGroup = view.findViewById<RadioGroup>(R.id.langRadioGroup)
        val addButton = view.findViewById<Button>(R.id.translateButton)
        val textView = view.findViewById<TextView>(R.id.translateStatusTextView)

        addButton.setOnClickListener {
            val text = editText.text.toString()
            val targetLang = view.findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString()
            if (targetLang == "ru")
                viewModel.add(EN, targetLang, text)
            else
                viewModel.add(RU, targetLang, text)
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            it.apply(textView)
        }

    }
    companion object {
        private const val EN = "en"
        private const val RU = "ru"
    }
}