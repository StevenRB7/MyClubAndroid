package com.myclub.myapplication.uiBusiness.membresias

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myclub.myapplication.R

class MembresiasBusinessFragment : Fragment() {

    companion object {
        fun newInstance() = MembresiasBusinessFragment()
    }

    private lateinit var viewModel: MembresiasBusinessViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_membresias_business, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MembresiasBusinessViewModel::class.java)
        // TODO: Use the ViewModel
    }

}