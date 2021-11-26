package com.example.todoappcleanarchitecture.fragments.add

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoappcleanarchitecture.R
import com.example.todoappcleanarchitecture.data.ToDoDatabase
import com.example.todoappcleanarchitecture.data.repository.ToDoRepository
import com.example.todoappcleanarchitecture.data.viewmodel.ToDoViewModel
import com.example.todoappcleanarchitecture.data.viewmodel.ToDoViewModelFactory
import com.example.todoappcleanarchitecture.databinding.FragmentAddBinding
import com.google.android.material.snackbar.Snackbar


class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding

    private val sharedViewModel: ToDoViewModel by activityViewModels {
        ToDoViewModelFactory(
            ToDoRepository(ToDoDatabase.getInstance(requireContext()).toDoDao)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)
        binding.viewModel = sharedViewModel
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertNoteToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertNoteToDb() {
        if (sharedViewModel.verifyData(
                binding.titleEditText.text.toString(),
                binding.descriptionEditText.text.toString()
            )) {
            sharedViewModel.insertData(
                binding.titleEditText.text.toString(),
                binding.prioritySpinner.selectedItem.toString(),
                binding.descriptionEditText.text.toString()
            )

            findNavController().navigate(AddFragmentDirections.actionAddFragmentToListFragment())
        } else {
            Snackbar.make(
                activity?.window!!.decorView.findViewById(android.R.id.content),
                getString(R.string.invalid),
                Snackbar.LENGTH_SHORT
            ).show()
        }


    }
}