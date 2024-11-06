package com.example.projekat_septembar.presentation.view.recycler.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.projekat_septembar.data.models.Car
import com.example.projekat_septembar.databinding.CarItemBinding

class CarViewHolder (private val itemBinding: CarItemBinding, val onClick: (position: Int) -> Unit) : RecyclerView.ViewHolder(itemBinding.root){

    init {
        itemBinding.root.setOnClickListener {
            onClick(layoutPosition)
        }
    }


    @SuppressLint("SetTextI18n")
    fun bind(car: Car){
        itemBinding.carNameTv.text = "Name: " + car.car
        itemBinding.carModelTv.text = "Model: " + car.car_model
        itemBinding.carYearTv.text = "Year: " + car.car_model_year
        itemBinding.carVinTv.text = "Vin: " + car.car_vin

        if (car.availability) {
            itemBinding.carAvailableValueTv.text = " Yes"
            itemBinding.carAvailableValueTv.setTextColor(android.graphics.Color.parseColor("#1ee832"))
        }
        else {
            itemBinding.carAvailableValueTv.text = " No"
            itemBinding.carAvailableValueTv.setTextColor(android.graphics.Color.parseColor("#ff1100"))

        }
    }
}