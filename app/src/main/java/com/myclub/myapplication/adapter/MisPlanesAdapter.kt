package com.myclub.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myclub.myapplication.Actvity.ListadoComerciosPlanActivity
import com.myclub.myapplication.R
import com.myclub.myapplication.dataDto.response.MisPlanesResponseDto
import com.myclub.myapplication.databinding.ItemCouponBinding

class MisPlanesAdapter(
    val listvaucher: MutableList<MisPlanesResponseDto>,
    private val context: Context

) : RecyclerView.Adapter<MisPlanesAdapter.ViewHolder>(), View.OnClickListener {
    private var clickListenert: View.OnClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCouponBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listvaucher[position])


    }

    override fun getItemCount() = listvaucher.size

    class ViewHolder(
        private val binding: ItemCouponBinding,
        val context: Context

    ):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(myPlan: MisPlanesResponseDto) {


            when(myPlan.IdCoupon?.toInt()){
                1->{
                    binding.CardBackground.setBackgroundResource(R.drawable.membresiablue)
                }
                2->{
                    binding.CardBackground.setBackgroundResource(R.drawable.membresiaplus)
                }
                3->{
                    binding.CardBackground.setBackgroundResource(R.drawable.membresiapremium)
                }
            }

            binding.txtDescripcionCupon.text = myPlan.DescriptionPlan
            binding.idbtnverdetalle.setOnClickListener {
                val i = Intent(context, ListadoComerciosPlanActivity::class.java)
                i.putExtra("IdPerson", myPlan.IdPerson.toString())
                i.putExtra("IdCoupon", myPlan.IdCoupon.toString())
                context.startActivity(i)
            }
            binding.IdbtnCoupon.setOnClickListener {
                val i = Intent(context, ListadoComerciosPlanActivity::class.java)
                i.putExtra("IdPerson", myPlan.IdPerson.toString())
                i.putExtra("IdCoupon", myPlan.IdCoupon.toString())
                context.startActivity(i)
            }
        }
    }

    override fun onClick(p0: View?) {
        clickListenert?.onClick(p0)
    }

}


