package com.myclub.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.myclub.myapplication.Actvity.GeneradorQRActivity
import com.myclub.myapplication.Actvity.ListadoShopsActivity
import com.myclub.myapplication.dataDto.response.ConsultarShopsResponseDto
import com.myclub.myapplication.dataDto.response.ConsultarVaucherResponseDto
import com.myclub.myapplication.databinding.ItemVaucherBinding

data class VaucherAdapter(
    val listvaucher: MutableList<ConsultarVaucherResponseDto>,
    private val context: Context

) : RecyclerView.Adapter<VaucherAdapter.ViewHolder>(),
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
        holder.bind(listvaucher[position])
    }

    override fun getItemCount() = listvaucher.size

    class ViewHolder(
        private val binding: ItemVaucherBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ConsultarVaucher: ConsultarVaucherResponseDto) {

            binding.txtvaucherdescripcion.text = ConsultarVaucher.DescriptionShop.toString()
            binding.txtvaucherdireccion.text = ConsultarVaucher.Direction.toString()
            binding.txtvaucherciudad.text = ConsultarVaucher.IdCity.toString()
            binding.txtvaucherusuario.text = ConsultarVaucher.IdUser.toString()

            binding.btncanjear.setOnClickListener {
                val i = Intent(context, GeneradorQRActivity::class.java)
                i.putExtra("IdVaucher",(ArrayList<ConsultarVaucherResponseDto>())
                )
                context.startActivity(i)
            }

            //Toast.makeText(context, "${ConsultarVaucher.Direction}", Toast.LENGTH_SHORT).show()


        }

    }

    override fun onClick(p0: View?) {
        clickListenert?.onClick(p0)
    }

}


