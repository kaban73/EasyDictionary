package com.example.easydictionary.add

import androidx.fragment.app.FragmentManager
import com.example.easydictionary.main.Screen

object AddScreen  : Screen.Add(AddDialogFragment::class.java) {
    override fun show(supportFragmentManager: FragmentManager, containerId: Int) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(AddDialogFragment().toString())
            .commit()
        AddDialogFragment().show(supportFragmentManager,"AddDialog")
    }
}