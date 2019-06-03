package com.example.mvrxpaged.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvrxpaged.R
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MainFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory

    val viewModel: MainViewModel by viewModels(factoryProducer = { viewModelFactory })

    private var toast: Toast? = null

    private lateinit var recyclerViewAdapter: MainAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewLifecycleOwner
        viewModel.run {
            messageEvent.observe(viewLifecycleOwner) {
                toast?.cancel()
                toast = Toast.makeText(requireContext(), it, Toast.LENGTH_LONG)
                toast?.show()
            }
            models.observe(viewLifecycleOwner) {
                recyclerViewAdapter.submitList(it)
            }
        }

        return inflater.inflate(R.layout.main_fragment, container, false).apply {
            findViewById<RecyclerView>(R.id.content_recycler_view).apply {
                recyclerViewAdapter = MainAdapter()
                adapter = recyclerViewAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }
}