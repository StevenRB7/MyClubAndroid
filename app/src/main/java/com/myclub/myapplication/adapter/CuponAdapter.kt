package com.myclub.myapplication.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myclub.myapplication.Actvity.ListadoShopsActivity
import com.myclub.myapplication.dataDto.response.ConsultarCuponResponseDto
import com.myclub.myapplication.databinding.ItemCouponBinding


class CuponAdapter(
    val list: MutableList<ConsultarCuponResponseDto>,
    private val context: Context
) : RecyclerView.Adapter<CuponAdapter.ViewHolder>(),
    View.OnClickListener {
    private var clickListenert: View.OnClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCouponBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    class ViewHolder(
        private val binding: ItemCouponBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(consultar: ConsultarCuponResponseDto) {

            binding.txtDscripcionCupon.text = consultar.DescripcionCupon?.toString()
            binding.txtIdCupon.text = consultar.IdCupon.toString()

            binding.idbtntarjetacupon.setOnClickListener {
                val i = Intent(context, ListadoShopsActivity::class.java)
                context.startActivity(i)
            }
            binding.idbtnverdetalle.setOnClickListener {
                val i = Intent(context, ListadoShopsActivity::class.java)
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


