package com.myclub.myapplication.ui.redimir

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myclub.myapplication.databinding.FragmentRedimirBinding

class RedimirFragment : Fragment() {
    private var _binding: FragmentRedimirBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val RedimirViewModel =
            ViewModelProvider(this).get(RedimirViewModel::class.java)

        _binding = FragmentRedimirBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}