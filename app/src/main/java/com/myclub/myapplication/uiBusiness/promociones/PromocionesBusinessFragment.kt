package com.myclub.myapplication.uiBusiness.promociones

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myclub.myapplication.R

class PromocionesBusinessFragment : Fragment() {

    companion object {
        fun newInstance() = PromocionesBusinessFragment()
    }

    private lateinit var viewModel: PromocionesBusinessViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_promociones_business, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PromocionesBusinessViewModel::class.java)
        // TODO: Use the ViewModel
    }

}