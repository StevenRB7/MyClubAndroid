package com.myclub.myapplication.uiBusiness.vaucher

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myclub.myapplication.R

class VaucherBusinessFragment : Fragment() {

    companion object {
        fun newInstance() = VaucherBusinessFragment()
    }

    private lateinit var viewModel: VaucherBusinessViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vaucher_business, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VaucherBusinessViewModel::class.java)
        // TODO: Use the ViewModel
    }

}