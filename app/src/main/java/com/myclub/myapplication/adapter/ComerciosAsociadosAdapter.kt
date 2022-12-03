package com.myclub.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myclub.myapplication.Actvity.GeneradorQRActivity
import com.myclub.myapplication.Actvity.ListadoComerciosPlanActivity
import com.myclub.myapplication.dataDto.response.ComercioCategoriasResponseDto
import com.myclub.myapplication.dataDto.response.ConsultarVaucherResponseDto
import com.myclub.myapplication.dataDto.response.CuponComercioResponseDto
import com.myclub.myapplication.databinding.ItemVaucherBinding

class ComerciosAsociadosAdapter(
    val list: MutableList<ComercioCategoriasResponseDto>,
    private val context: Context
) : RecyclerView.Adapter<ComerciosAsociadosAdapter.ViewHolder>(),
    View.OnClickListener {
    private var clickListenert: View.OnClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVaucherBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    class ViewHolder(
        private val binding: ItemVaucherBinding,
        val context: Context
    ):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comercio: ComercioCategoriasResponseDto) {
            if (comercio.IdStateTrade==3.0){
                binding.btncanjear.visibility=View.GONE
                binding.idvaucher.alpha=0.6f
            }
            Log.e("LogoTrade", comercio.LogoTrade.toString());
            Glide.with(binding.imgVaucher)
                .load(comercio.LogoTrade)
                .into(binding.imgVaucher);


            binding.txtvaucherdescripcion.text = comercio.DescriptionTrade.toString()
            binding.txtvaucherdireccion.text = comercio.DirectionTrade.toString()
            binding.txtvaucherciudad.text = comercio.IdCiudadTrade.toString()
            binding.txtvaucherusuario.text = comercio.IdUser.toString()

            binding.btncanjear.setOnClickListener {
                val i = Intent(context, GeneradorQRActivity::class.java)
                i.putExtra("IdCoupon", comercio.IdCoupon.toString())
                i.putExtra("IdTrade", comercio.IdTrade.toString())
                i.putExtra("IdProject", comercio.IdProject.toString())
                i.putExtra("IdUserAssociated", comercio.IdUser.toString())
                i.putExtra("IdPersonTrade", comercio.IdPersonTrade.toString())



                context.startActivity(i)
            }

        }

    }

    fun setOnClickListener(listener: View.OnClickListener) {
        clickListenert = listener
    }

    override fun onClick(p0: View?) {
        clickListenert?.onClick(p0)
    }

}

