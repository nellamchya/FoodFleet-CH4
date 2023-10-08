package com.shine.foodfleet.presentation.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.shine.foodfleet.data.dummy.DummyCategoryDataSource
import com.shine.foodfleet.data.dummy.DummyCategoryDataSourceImpl
import com.shine.foodfleet.data.local.database.AppDatabase
import com.shine.foodfleet.data.local.database.datasource.MenuDatabaseDataSource
import com.shine.foodfleet.data.repository.MenuRepository
import com.shine.foodfleet.data.repository.MenuRepositoryImpl
import com.shine.foodfleet.databinding.FragmentHomeBinding
import com.shine.foodfleet.presentation.feature.detailmenu.DetailMenuActivity
import com.shine.foodfleet.presentation.feature.home.adapter.HomeAdapter
import com.shine.utils.GenericViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapter: HomeAdapter by lazy {
        HomeAdapter(onMenuClicked = {
            navigateToDetail(it)
        })
    }


    private fun navigateToDetail(item: com.shine.foodfleet.model.Menu) {
        DetailMenuActivity.startActivity(requireContext(), item)
    }

    private val viewModel: HomeViewModel by viewModels {
        val cds: DummyCategoryDataSource = DummyCategoryDataSourceImpl()
        val database = AppDatabase.getInstance(requireContext())
        val menuDao = database.menuDao()
        val menuDataSource = MenuDatabaseDataSource(menuDao)
        val repo: MenuRepository= MenuRepositoryImpl(menuDataSource, cds)
        GenericViewModelFactory.create(HomeViewModel(repo))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        fetchData()
    }

    private fun fetchData() {
        viewModel.homeData.observe(viewLifecycleOwner){
            adapter.submitData(it)
        }
    }

    private fun setupList() {
        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HomeFragment.adapter
        }
    }
}