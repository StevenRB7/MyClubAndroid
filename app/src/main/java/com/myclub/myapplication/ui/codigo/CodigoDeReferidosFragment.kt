package com.myclub.myapplication.ui.codigo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.FragmentCodigoDeReferidosBinding
import com.myclub.myapplication.databinding.FragmentRedimirBinding
import com.myclub.myapplication.ui.redimir.RedimirViewModel

class CodigoDeReferidosFragment : Fragment() {

    private var _binding: FragmentCodigoDeReferidosBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val CodigoDeReferidosViewModel =
            ViewModelProvider(this).get(CodigoDeReferidosViewModel::class.java)

        _binding = FragmentCodigoDeReferidosBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}