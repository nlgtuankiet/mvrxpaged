package com.example.mvrxpaged.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.example.mvrxpaged.R
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainFragment : BaseMvRxFragment() {

    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory

    @Inject
    lateinit var mainActivityController: MainController

    private lateinit var recyclerView: EpoxyRecyclerView

    val viewModel: MainViewModel by fragmentViewModel()
    private var toast: Toast? = null
    private var messageEventDisposable: Disposable? = null

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        messageEventDisposable = viewModel.messageEvent.subscribe {
            toast?.cancel()
            toast = Toast.makeText(requireContext(), it, Toast.LENGTH_LONG)
            toast?.show()
        }
        return inflater.inflate(R.layout.main_fragment, container, false).apply {
            recyclerView = findViewById<EpoxyRecyclerView>(R.id.content_recycler_view).apply {
                setController(mainActivityController)
            }
        }
    }


    override fun invalidate() {
        val list = withState(viewModel) {
            it.modelListPaged()
        }
        mainActivityController.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        messageEventDisposable?.dispose()
    }


}