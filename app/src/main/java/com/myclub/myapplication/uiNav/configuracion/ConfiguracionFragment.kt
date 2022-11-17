package com.myclub.myapplication.uiNav.configuracion

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.FragmentConfiguracionBinding

class ConfiguracionFragment : Fragment() {

    private var _binding: FragmentConfiguracionBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val ConfiguracionViewModel =
            ViewModelProvider(this).get(ConfiguracionViewModel::class.java)

        _binding = FragmentConfiguracionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}