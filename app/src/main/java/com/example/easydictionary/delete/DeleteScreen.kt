package com.example.easydictionary.delete

import androidx.fragment.app.FragmentManager
import com.example.easydictionary.add.AddDialogFragment
import com.example.easydictionary.main.Screen

class DeleteScreen(private val itemId: Long) : Screen.Add(DeleteDialogFragment::class.java) {
    override fun show(supportFragmentManager: FragmentManager, containerId: Int) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(AddDialogFragment().toString())
            .commit()
        DeleteDialogFragment.newInstance(itemId).show(supportFragmentManager,"DeleteDialog")
    }
}