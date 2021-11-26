package com.example.todoappcleanarchitecture.fragments.list

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todoappcleanarchitecture.R
import com.example.todoappcleanarchitecture.adapter.ToDoAdapter
import com.example.todoappcleanarchitecture.data.ToDoDatabase
import com.example.todoappcleanarchitecture.data.repository.ToDoRepository
import com.example.todoappcleanarchitecture.data.viewmodel.ToDoViewModel
import com.example.todoappcleanarchitecture.data.viewmodel.ToDoViewModelFactory
import com.example.todoappcleanarchitecture.databinding.FragmentListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    private val sharedViewModel: ToDoViewModel by activityViewModels {
        ToDoViewModelFactory(
            ToDoRepository(ToDoDatabase.getInstance(requireContext()).toDoDao)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        binding.apply {
            viewModel = sharedViewModel
            listFragment = this@ListFragment
            lifecycleOwner = this@ListFragment

            recyclerView.adapter = ToDoAdapter { data ->
                findNavController().navigate(
                    ListFragmentDirections.actionListFragmentToUpdateFragment(data)
                )
            }
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> showDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.ask_delete_all))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteAllData()
            }
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .show()
    }

    private fun deleteAllData() {
        val datas = sharedViewModel.allData.value!!
        sharedViewModel.deleteAllData(datas)
        Snackbar.make(
            requireActivity().window.decorView.findViewById(android.R.id.content),
            getString(R.string.note_deleted),
            Snackbar.LENGTH_LONG
        ).setAction(getString(R.string.undo)) {
            sharedViewModel.insertMuchData(datas)
        }.show()
    }

    fun navigateToAddFragment() {
        findNavController().navigate(
            ListFragmentDirections.actionListFragmentToAddFragment()
        )
    }
}