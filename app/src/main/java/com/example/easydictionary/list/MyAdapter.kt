package com.example.easydictionary.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.easydictionary.databinding.TranslateLayoutBinding
import com.example.easydictionary.delete.DeleteTranslateUi

class MyAdapter(private val deleteTranslateUi: DeleteTranslateUi) : RecyclerView.Adapter<MyViewHolder>() {
    private val list = mutableListOf<TranslateUi>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            TranslateLayoutBinding.inflate(LayoutInflater.from(parent.context)),
            deleteTranslateUi
        )

    override fun getItemCount(): Int = list.size
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun update(newList: List<TranslateUi>) {
        val myDiffUtil = MyDiffUtil(list, newList)
        val diffResult = DiffUtil.calculateDiff(myDiffUtil)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class MyDiffUtil(
        private val oldList: List<TranslateUi>,
        private val newList: List<TranslateUi>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].areItemsTheSame(newList[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].areContentsTheSame(newList[newItemPosition])
        }
    }
}

class MyViewHolder(
    private val layoutBinding: TranslateLayoutBinding,
    private val deleteTranslateUi: DeleteTranslateUi
) : RecyclerView.ViewHolder(layoutBinding.root) {
    fun bind(translateUi: TranslateUi) {
        translateUi.show(layoutBinding.elementTextView)
        itemView.setOnClickListener {
            translateUi.delete(deleteTranslateUi)
        }
    }
}