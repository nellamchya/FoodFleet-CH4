package com.shine.foodfleet.presentation.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.shine.foodfleet.R
import com.shine.foodfleet.databinding.FragmentHomeBinding
import com.shine.foodfleet.model.Menu
import com.shine.foodfleet.presentation.feature.detailmenu.DetailMenuActivity
import com.shine.foodfleet.presentation.feature.home.adapter.subadapter.CategoryListAdapter
import com.shine.foodfleet.presentation.feature.home.adapter.subadapter.MenuListAdapter
import com.shine.foodfleet.presentation.feature.home.adapter.subadapter.MenuListAdapter.Companion.GRID_LAYOUT
import com.shine.foodfleet.presentation.feature.home.adapter.subadapter.MenuListAdapter.Companion.LINEAR_LAYOUT
import com.shine.foodfleet.utils.AssetWrapper
import com.shine.foodfleet.utils.proceedWhen
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter {
            viewModel.getMenus(it.categoryName.lowercase())
        }
    }
    private val menuAdapter: MenuListAdapter by lazy {
        MenuListAdapter(LINEAR_LAYOUT) {
            navigateToDetail(it)
        }
    }

    private val viewModel: HomeViewModel by viewModel()

    private val assetWrapper: AssetWrapper by inject()

    private fun navigateToDetail(menu: Menu) {
        DetailMenuActivity.startActivity(requireContext(), menu)
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
        setClickListener()
        getData()
        observeData()
    }

    private fun observeData() {
        viewModel.categories.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutStateCategory.root.isVisible = false
                    binding.layoutStateCategory.pbLoading.isVisible = false
                    binding.layoutStateCategory.tvError.isVisible = false
                    binding.rvListCategories.apply {
                        isVisible = true
                        adapter = categoryAdapter
                    }
                    it.payload?.let { data -> categoryAdapter.submitData(data) }
                },
                doOnLoading = {
                    binding.layoutStateCategory.root.isVisible = true
                    binding.layoutStateCategory.pbLoading.isVisible = true
                    binding.layoutStateCategory.tvError.isVisible = false
                    binding.rvListCategories.isVisible = false
                },
                doOnError = {
                    binding.layoutStateCategory.root.isVisible = true
                    binding.layoutStateCategory.pbLoading.isVisible = false
                    binding.layoutStateCategory.tvError.isVisible = true
                    binding.layoutStateCategory.tvError.text = it.exception?.message.orEmpty()
                    binding.rvListCategories.isVisible = false
                },
                doOnEmpty = {
                    binding.rvListCategories.isVisible = false
                    binding.layoutStateCategory.root.isVisible = true
                    binding.layoutStateCategory.pbLoading.isVisible = false
                    binding.layoutStateCategory.tvError.isVisible = true
                    binding.layoutStateCategory.tvError.text =
                        assetWrapper.getString(R.string.text_category_not_found)
                }
            )
        }
        viewModel.menus.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = {
                binding.layoutStateMenu.root.isVisible = false
                binding.layoutStateMenu.pbLoading.isVisible = false
                binding.layoutStateMenu.tvError.isVisible = false
                setUpMenuRv()
                it.payload?.let { data -> menuAdapter.submitData(data) }
            }, doOnLoading = {
                    binding.layoutStateMenu.root.isVisible = true
                    binding.layoutStateMenu.pbLoading.isVisible = true
                    binding.layoutStateMenu.tvError.isVisible = false
                    binding.rvListMenus.isVisible = false
                }, doOnError = {
                    binding.layoutStateMenu.root.isVisible = true
                    binding.layoutStateMenu.pbLoading.isVisible = false
                    binding.layoutStateMenu.tvError.isVisible = true
                    binding.layoutStateMenu.tvError.text = it.exception?.message.orEmpty()
                    binding.rvListMenus.isVisible = false
                }, doOnEmpty = {
                    binding.layoutStateMenu.root.isVisible = true
                    binding.layoutStateMenu.pbLoading.isVisible = false
                    binding.layoutStateMenu.tvError.isVisible = true
                    binding.layoutStateMenu.tvError.text = R.string.text_menu_not_found.toString()
                    binding.rvListMenus.isVisible = false
                })
        }
    }

    override fun onResume() {
        super.onResume()
        getProfileData()
    }
    private fun getProfileData() {
        viewModel.getProfileData()
        observeDataProfile()
    }

    private fun observeDataProfile() {
        viewModel.getProfileResult.observe(viewLifecycleOwner) {
            binding.textUsername.text = viewModel.getCurrentUser()?.fullName
            viewModel.getProfileData()
        }
    }

    private fun setUpMenuRv() {
        viewModel.userLayoutMode.observe(viewLifecycleOwner) { layoutMode ->
            binding.rvListMenus.apply {
                isVisible = true
                adapter = menuAdapter
                layoutManager = GridLayoutManager(requireContext(), layoutMode)
            }
            menuAdapter.layoutMode = layoutMode
        }
    }

    private fun setClickListener() {
        binding.ibSwitchMode.setOnClickListener {
            changeAdapterLayoutMode()
        }
    }

    private fun getData() {
        binding.textUsername.text = viewModel.getCurrentUser()?.fullName
        viewModel.getCategories()
        viewModel.getMenus()
    }

    private fun changeAdapterLayoutMode() {
        if (menuAdapter.layoutMode == LINEAR_LAYOUT) {
            menuAdapter.layoutMode = GRID_LAYOUT
            binding.ibSwitchMode.load(R.drawable.ic_grid)
            viewModel.setUserLayoutMode(GRID_LAYOUT)
            (binding.rvListMenus.layoutManager as GridLayoutManager).spanCount = 2
        } else if (menuAdapter.layoutMode == GRID_LAYOUT) {
            menuAdapter.layoutMode = LINEAR_LAYOUT
            binding.ibSwitchMode.load(R.drawable.ic_list)
            viewModel.setUserLayoutMode(LINEAR_LAYOUT)
            (binding.rvListMenus.layoutManager as GridLayoutManager).spanCount = 1
        }
        menuAdapter.refreshList()
    }
}
