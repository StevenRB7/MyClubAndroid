package com.myclub.myapplication.uiBusiness.soporte

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myclub.myapplication.R

class SoporteBusinessFragment : Fragment() {

    companion object {
        fun newInstance() = SoporteBusinessFragment()
    }

    private lateinit var viewModel: SoporteBusinessViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_soporte_business, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SoporteBusinessViewModel::class.java)
        // TODO: Use the ViewModel
    }

}