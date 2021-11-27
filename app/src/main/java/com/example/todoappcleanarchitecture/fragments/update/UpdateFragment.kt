package com.example.todoappcleanarchitecture.fragments.update

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoappcleanarchitecture.R
import com.example.todoappcleanarchitecture.data.ToDoDatabase
import com.example.todoappcleanarchitecture.data.repository.ToDoRepository
import com.example.todoappcleanarchitecture.data.viewmodel.ToDoViewModel
import com.example.todoappcleanarchitecture.data.viewmodel.ToDoViewModelFactory
import com.example.todoappcleanarchitecture.databinding.FragmentUpdateBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private val args : UpdateFragmentArgs by navArgs()
    private val sharedViewModel: ToDoViewModel by activityViewModels {
        ToDoViewModelFactory(
            ToDoRepository(ToDoDatabase.getInstance(requireContext()).toDoDao)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update, container, false)
        binding.apply {
            viewModel = sharedViewModel
            data = args.data
            lifecycleOwner = this@UpdateFragment
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                updateData()
            }
            R.id.menu_delete -> showDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.ask_remove))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteData()
            }
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .show()
    }

    private fun deleteData() {
        sharedViewModel.deleteData(args.data)
        findNavController().navigate(
            UpdateFragmentDirections.actionUpdateFragmentToListFragment()
        )

        Snackbar.make(
            requireActivity().window.decorView.findViewById(android.R.id.content),
            getString(R.string.note_deleted),
            Snackbar.LENGTH_LONG
        ).setAction(getString(R.string.undo)) {
            sharedViewModel.insert(args.data)
        }.show()
    }

    private fun updateData() {
        if (sharedViewModel.verifyData(
                binding.titleEditText.text.toString(),
                binding.descriptionEditText.text.toString()
            )) {
            sharedViewModel.updateData(
                args.data.id,
                binding.titleEditText.text.toString(),
                binding.prioritySpinner.selectedItem.toString(),
                binding.descriptionEditText.text.toString()
            )

            findNavController().navigate(UpdateFragmentDirections.actionUpdateFragmentToListFragment())
        } else {
            Snackbar.make(
                activity?.window!!.decorView.findViewById(android.R.id.content),
                getString(R.string.invalid),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}