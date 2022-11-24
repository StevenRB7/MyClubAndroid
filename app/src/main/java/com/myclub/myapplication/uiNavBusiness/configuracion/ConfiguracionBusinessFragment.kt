package com.myclub.myapplication.uiNavBusiness.configuracion

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myclub.myapplication.R

class ConfiguracionBusinessFragment : Fragment() {

    companion object {
        fun newInstance() = ConfiguracionBusinessFragment()
    }

    private lateinit var viewModel: ConfiguracionBusinessViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_configuracion_business, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ConfiguracionBusinessViewModel::class.java)
        // TODO: Use the ViewModel
    }

}