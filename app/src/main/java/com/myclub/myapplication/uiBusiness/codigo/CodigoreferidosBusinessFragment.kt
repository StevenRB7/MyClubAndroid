package com.myclub.myapplication.uiBusiness.codigo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myclub.myapplication.R

class CodigoreferidosBusinessFragment : Fragment() {

    companion object {
        fun newInstance() = CodigoreferidosBusinessFragment()
    }

    private lateinit var viewModel: CodigoreferidosBusinessViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_codigoreferidos_business, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CodigoreferidosBusinessViewModel::class.java)
        // TODO: Use the ViewModel
    }

}