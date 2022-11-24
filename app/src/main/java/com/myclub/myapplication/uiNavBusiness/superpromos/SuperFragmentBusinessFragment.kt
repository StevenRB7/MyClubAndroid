package com.myclub.myapplication.uiNavBusiness.superpromos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myclub.myapplication.R

class SuperFragmentBusinessFragment : Fragment() {

    companion object {
        fun newInstance() = SuperFragmentBusinessFragment()
    }

    private lateinit var viewModel: SuperFragmentBusinessViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_super_fragment_business, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SuperFragmentBusinessViewModel::class.java)
        // TODO: Use the ViewModel
    }

}