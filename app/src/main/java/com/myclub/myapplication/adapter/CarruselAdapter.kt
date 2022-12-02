package com.myclub.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myclub.myapplication.databinding.CarruselItemBinding
import kotlinx.coroutines.GlobalScope

class CarruselAdapter(private var carrusellist: List<Carrusel>): RecyclerView.Adapter<CarruselAdapter.CarruselVievHolder>(){
    class CarruselVievHolder(val binding: CarruselItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarruselVievHolder {
        val binding = CarruselItemBinding.inflate(LayoutInflater.from(parent.context), parent, false
        )
        return CarruselVievHolder(binding)
    }

    override fun onBindViewHolder(holder: CarruselVievHolder, position: Int) {
        val movie  = carrusellist[position]
        holder.binding.apply {
            Glide.with(imagen).load(carrusellist[position].image).into(imagen)
        }
    }

    override fun getItemCount(): Int = carrusellist.size

}
