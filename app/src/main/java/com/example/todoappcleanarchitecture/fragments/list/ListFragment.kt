package com.example.todoappcleanarchitecture.fragments.list

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todoappcleanarchitecture.R
import com.example.todoappcleanarchitecture.adapter.ToDoAdapter
import com.example.todoappcleanarchitecture.data.ToDoDatabase
import com.example.todoappcleanarchitecture.data.repository.ToDoRepository
import com.example.todoappcleanarchitecture.data.viewmodel.ToDoViewModel
import com.example.todoappcleanarchitecture.data.viewmodel.ToDoViewModelFactory
import com.example.todoappcleanarchitecture.databinding.FragmentListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentListBinding

    private lateinit var adapter: ToDoAdapter

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
            lifecycleOwner = viewLifecycleOwner

            adapter = ToDoAdapter { data ->
                findNavController().navigate(
                    ListFragmentDirections.actionListFragmentToUpdateFragment(data)
                )
            }
            recyclerView.adapter = adapter
            recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> showDialog()
            R.id.menu_priority_high -> sortDataHighToLowPriority()
            R.id.menu_priority_low -> sortDataLowToHighPriority()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sortDataLowToHighPriority() {
        sharedViewModel.sortLowToHigh().observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun sortDataHighToLowPriority() {
        sharedViewModel.sortHighToLow().observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            actionSearch(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            actionSearch(query)
        }
        return true
    }

    private fun actionSearch(query: String) {
        val querySearch = "%$query%"
        sharedViewModel.searchDatabase(querySearch).observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
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
        val dataList = sharedViewModel.allData.value!!
        sharedViewModel.deleteAllData(dataList)
        Snackbar.make(
            requireActivity().window.decorView.findViewById(android.R.id.content),
            getString(R.string.note_deleted),
            Snackbar.LENGTH_LONG
        ).setAction(getString(R.string.undo)) {
            sharedViewModel.insertMuchData(dataList)
        }.show()
    }

    fun navigateToAddFragment() {
        findNavController().navigate(
            ListFragmentDirections.actionListFragmentToAddFragment()
        )
    }

}