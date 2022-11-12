package com.myclub.myapplication.ui.membresias

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.FragmentMembresiasBinding
import com.myclub.myapplication.databinding.FragmentVaucherBinding
import com.myclub.myapplication.ui.vaucher.VaucherViewModel

class MembresiasFragment : Fragment() {

    private var _binding: FragmentMembresiasBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val MembresiasViewModel =
            ViewModelProvider(this).get(MembresiasViewModel::class.java)

        _binding = FragmentMembresiasBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}