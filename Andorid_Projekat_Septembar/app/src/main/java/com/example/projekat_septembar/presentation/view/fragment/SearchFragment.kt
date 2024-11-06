package com.example.projekat_septembar.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projekat_septembar.data.models.Car
import com.example.projekat_septembar.databinding.FragmentSearchBinding
import com.example.projekat_septembar.presentation.contract.CarContract
import com.example.projekat_septembar.presentation.view.recycler.adapters.CarAdapter
import com.example.projekat_septembar.presentation.view.states.CarState
import com.example.projekat_septembar.presentation.viewmodel.CarViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment() {

    private val carViewModel: CarContract.ViewModel by sharedViewModel<CarViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CarAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        initView()
        initObservers()
    }

    private fun initRecycler() {
        binding.searchRv.layoutManager = LinearLayoutManager(context)
        adapter = CarAdapter(::onItemClick)
        binding.searchRv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        binding.searchRv.adapter = adapter
    }

    private fun onItemClick(car: Car){//iskoriscen je isti adapter za sve RV zato je ovo prazno
    }

    private fun initView() {
        binding.searchBtn.setOnClickListener{

            val radioButton = view?.findViewById<RadioButton>(binding.searchRadioGroup.checkedRadioButtonId)

            if (radioButton == null || binding.searchEt.text.toString() == "") {
                Toast.makeText(context, "Please fill in fields properly", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val searchKey: String = capitalize(binding.searchEt.text.toString())

            when(radioButton.text.toString()){
                "Search by name"  -> carViewModel.search("name", searchKey)
                "Search by model" -> carViewModel.search("model", searchKey)
                "Search by color" -> carViewModel.search("color", searchKey)
                else -> System.err.println("Error while inputting search")
            }
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
            is CarState.Searched -> {

                if(binding.yearSwitch.isChecked){

                    if(binding.searchYearEt.text.toString() == ""){
                       Toast.makeText(activity, "Please put in wanted year", Toast.LENGTH_SHORT).show()
                        return
                    }

                    val year: Int = binding.searchYearEt.text.toString().toInt()
                    val sortedList: ArrayList<Car> = arrayListOf()

                    state.cars.forEach {
                        if (it.car_model_year == year)
                            sortedList.add(it)
                    }
                    adapter.submitList(sortedList)
                }
                else adapter.submitList(state.cars)
            }
            else -> Timber.e("Error")
        }
    }

    private fun capitalize(str: String): String {
        return str.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }
}