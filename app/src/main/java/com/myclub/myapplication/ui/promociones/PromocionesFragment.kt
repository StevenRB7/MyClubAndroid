package com.myclub.myapplication.ui.promociones

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.FragmentPromocionesBinding
import com.myclub.myapplication.databinding.FragmentVaucherBinding
import com.myclub.myapplication.ui.vaucher.VaucherViewModel

class PromocionesFragment : Fragment() {

    private var _binding: FragmentPromocionesBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val PromocionesViewModel =
            ViewModelProvider(this).get(PromocionesViewModel::class.java)

        _binding = FragmentPromocionesBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}