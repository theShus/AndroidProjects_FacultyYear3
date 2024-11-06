package com.example.projekat_septembar.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.projekat_septembar.data.models.Car

class CarDiffCallback : DiffUtil.ItemCallback<Car>(){

    override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
        return  oldItem.car == newItem.car &&
                oldItem.car_model == newItem.car_model &&
                oldItem.car_model_year == newItem.car_model_year &&
                oldItem.car_vin == newItem.car_vin &&
                oldItem.availability == newItem.availability
    }
}