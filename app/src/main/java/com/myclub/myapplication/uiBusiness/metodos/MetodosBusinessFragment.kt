package com.myclub.myapplication.uiBusiness.metodos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myclub.myapplication.R

class MetodosBusinessFragment : Fragment() {

    companion object {
        fun newInstance() = MetodosBusinessFragment()
    }

    private lateinit var viewModel: MetodosBusinessViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_metodos_business, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MetodosBusinessViewModel::class.java)
        // TODO: Use the ViewModel
    }

}