package com.example.projekat_septembar.presentation.view.recycler.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.projekat_septembar.data.models.Car
import com.example.projekat_septembar.databinding.CarItemBinding
import com.example.projekat_septembar.presentation.view.recycler.diff.CarDiffCallback
import com.example.projekat_septembar.presentation.view.recycler.viewholder.CarViewHolder

class CarAdapter (val onClick: (stock: Car) -> Unit) : ListAdapter<Car, CarViewHolder>(CarDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val itemBinding = CarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(itemBinding) { onClick(getItem(it)) }
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}