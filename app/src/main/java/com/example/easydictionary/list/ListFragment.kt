package com.example.easydictionary.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.easydictionary.core.AbstractFragment
import com.example.easydictionary.core.ProvideViewModel
import com.example.easydictionary.databinding.ListLayoutBinding
import com.example.easydictionary.delete.DeleteTranslateUi

class ListFragment : AbstractFragment<ListLayoutBinding>() {
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): ListLayoutBinding =
        ListLayoutBinding.inflate(inflater, container, false)

    private val adapter = MyAdapter(deleteTranslateUi = object : DeleteTranslateUi {
        override fun delete(itemId: Long) {
            viewModel.delete(itemId)
        }
    })
    private lateinit var viewModel: ListViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(ListViewModel::class.java)

        b.recyclerView.adapter = adapter
        b.recyclerView.addItemDecoration(ItemDecoration())
        b.addButton.setOnClickListener {
            viewModel.add()
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            adapter.update(it)
        }

        viewModel.init()
    }
}