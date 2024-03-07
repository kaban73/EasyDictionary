package com.example.easydictionary.delete

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.easydictionary.R
import com.example.easydictionary.core.ProvideViewModel

class DeleteDialogFragment : DialogFragment(R.layout.delete_layout) {
    companion object {
        fun newInstance(itemId : Long) : DeleteDialogFragment {
            val instance = DeleteDialogFragment()
            instance.arguments = Bundle().apply {
                putLong(KEY, itemId)
            }
            return instance
        }
        private const val KEY = "itemIdKey"
    }
    private lateinit var viewModel: DeleteViewModel
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(DeleteViewModel::class.java)
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
        val textView = view.findViewById<TextView>(R.id.translateTextView)
        val button = view.findViewById<Button>(R.id.deleteButton)
        val id = requireArguments().getLong(KEY)

        button.setOnClickListener {
            viewModel.delete(id)
            dismiss()
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            textView.text = it.first + " - " + it.second
        }

        viewModel.init(id)
    }

}