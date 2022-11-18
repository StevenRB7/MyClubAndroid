package com.myclub.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.myclub.myapplication.Actvity.ListadoShopsActivity
import com.myclub.myapplication.dataDto.response.ConsultarShopsResponseDto
import com.myclub.myapplication.databinding.ItemShopBinding

class ShopsAdapter(
    val listshop: MutableList<ConsultarShopsResponseDto>,
    private val context: Context

) : RecyclerView.Adapter<ShopsAdapter.ViewHolder>(),
    View.OnClickListener {
    private var clickListenert: View.OnClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemShopBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listshop[position])
    }

    override fun getItemCount() = listshop.size

    class ViewHolder(
        private val binding: ItemShopBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(consultarshop: ConsultarShopsResponseDto) {

            binding.txtshopdescripcion.text = consultarshop.Description
            binding.txtshopdireccion.text = consultarshop.Direction
            binding.txtshopciudad.text = consultarshop.IdCity.toString()

            //Toast.makeText(context, "${consultarshop.Direction}", Toast.LENGTH_SHORT).show()


        }

    }

    override fun onClick(p0: View?) {
        clickListenert?.onClick(p0)
    }

}
