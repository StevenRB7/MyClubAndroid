package com.myclub.myapplication.uiNav.superpromos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myclub.myapplication.R

class SuperPromosFragment : Fragment() {

    companion object {
        fun newInstance() = SuperPromosFragment()
    }

    private lateinit var viewModel: SuperPromosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_super_promos, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SuperPromosViewModel::class.java)
        // TODO: Use the ViewModel
    }

}