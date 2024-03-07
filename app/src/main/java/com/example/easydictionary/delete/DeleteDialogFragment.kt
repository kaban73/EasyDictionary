package com.example.easydictionary.delete

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.easydictionary.R

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
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}