package com.example.rmaproject2.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rmaproject2.R
import com.example.rmaproject2.databinding.FragmentCourseBinding
import com.example.rmaproject2.presentation.contract.CourseContract
import com.example.rmaproject2.presentation.view.recycler.adapter.CourseAdapter
import com.example.rmaproject2.presentation.view.states.CourseState
import com.example.rmaproject2.presentation.viewModels.CourseViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration

class CourseFragment : Fragment(R.layout.fragment_course) {

    private val courseViewModel: CourseContract.ViewModel by sharedViewModel<CourseViewModel>()
    private var _binding: FragmentCourseBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CourseAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        initRecycler()
        initListeners()
        initObservers()
        initSpinners(view)
    }

    private fun initSpinners(view: View) {
        ArrayAdapter.createFromResource(
            view.context,
            R.array.days,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinnerDaySelect.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            view.context,
            R.array.groups,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinnerGroupSelect.adapter = adapter
        }
    }


    private fun initRecycler() {
        binding.courseRv.layoutManager = LinearLayoutManager(context)
        adapter = CourseAdapter()
        binding.courseRv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        binding.courseRv.adapter = adapter
    }

    private fun initListeners() {
        binding.searchButton.setOnClickListener {
            val day = binding.spinnerDaySelect.selectedItem.toString()
            val group = binding.spinnerGroupSelect.selectedItem.toString()
            val profGroup = binding.filterEt.text.toString()

            courseViewModel.getByFilter(profGroup, group, day)
        }
    }

    private fun initObservers() {
        courseViewModel.courseState.observe(viewLifecycleOwner, Observer { courseState ->
            Timber.e(courseState.toString())
            renderState(courseState)
        })
        courseViewModel.getAllCourses()
        courseViewModel.fetchAllCourses()
    }

    private fun renderState(state: CourseState) {
        when (state) {
            is CourseState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.courses)
            }
            is CourseState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is CourseState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG)
                    .show()
            }
            is CourseState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.filterEt.isVisible = !loading
        binding.courseRv.isVisible = !loading
        binding.loadingPb.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}