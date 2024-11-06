package com.example.projekat_septembar.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projekat_septembar.data.models.Car
import com.example.projekat_septembar.databinding.FragmentSavedBinding
import com.example.projekat_septembar.presentation.contract.CarContract
import com.example.projekat_septembar.presentation.view.recycler.adapters.CarAdapter
import com.example.projekat_septembar.presentation.view.states.CarState
import com.example.projekat_septembar.presentation.viewmodel.CarViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


class SavedFragment : Fragment() {

    private val carViewModel: CarContract.ViewModel by sharedViewModel<CarViewModel>()
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CarAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        initView()
        initObservers()
    }

    private fun initRecycler() {
        binding.savedRv.layoutManager = LinearLayoutManager(context)
        adapter = CarAdapter(::onItemClick)
        binding.savedRv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        binding.savedRv.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.savedRv)
    }

    private fun onItemClick(car: Car){//iskoriscen je isti adapter za sve RV zato je ovo prazno
    }

    private fun initView() {
        carViewModel.getAll()
    }

    private var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP) {
        override fun onMove(recyclerView: RecyclerView,viewHolder: RecyclerView.ViewHolder,target: RecyclerView.ViewHolder): Boolean {
            println("ON MOVE")
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            println("SWIPED")
            val position = viewHolder.adapterPosition
            carViewModel.deleteCar(adapter.currentList[position].id)//brise se iz baze sacuvani
            carViewModel.getAll()
        }
    }

    private fun initObservers() {
        carViewModel.carState.observe(viewLifecycleOwner) { carState ->
            Timber.e(carState.toString())
            renderState(carState)
        }
    }

    private fun renderState(state: CarState) {
        when (state) {
            is CarState.Saved -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is CarState.LocalSuccess -> {
                adapter.submitList(state.cars)
            }
            else -> Timber.e("Error")
        }
    }

}