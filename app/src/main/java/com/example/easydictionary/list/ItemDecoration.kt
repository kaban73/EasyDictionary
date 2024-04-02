package com.example.easydictionary.list

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = SPACE
        outRect.right = SPACE
        outRect.bottom = SPACE * 2

        if (parent.getChildAdapterPosition(view) == 0)
            outRect.top = SPACE * 2
        else
            outRect.top = 0
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }

    companion object {
        private const val SPACE = 10
    }
}