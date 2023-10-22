package com.shine.foodfleet.presentation.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.shine.foodfleet.data.network.firebase.auth.FirebaseAuthDataSource
import com.shine.foodfleet.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.shine.foodfleet.data.repository.UserRepository
import com.shine.foodfleet.data.repository.UserRepositoryImpl
import com.shine.foodfleet.databinding.FragmentProfileBinding
import com.shine.foodfleet.presentation.feature.login.LoginActivity
import com.shine.foodfleet.presentation.feature.profile.editprofile.EditProfileActivity
import com.shine.utils.GenericViewModelFactory


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels {
        val firebaseAuth = FirebaseAuth.getInstance()
        val dataSource: FirebaseAuthDataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val repo: UserRepository = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(ProfileViewModel(repo))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
        showDataUser()
    }

    private fun setClickListeners() {
        binding.ivEditProfile.setOnClickListener {
            navigateToEditProfile()
        }
        binding.btnLogout.setOnClickListener {
            doLogout()
        }
    }

    private fun navigateToEditProfile() {
        val intent = Intent(requireContext(), EditProfileActivity::class.java)
        startActivity(intent)
    }

    private fun doLogout() {
        AlertDialog.Builder(requireContext())
            .setMessage(
                "Do you want to logout ?"
            )
            .setPositiveButton("Yes") { _, _ ->
                viewModel.doLogout()
                navigateToLogin()
            }.setNegativeButton("No") { _, _ ->

            }.create().show()
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun showDataUser() {
        binding.tvPersonalName.text = viewModel.getCurrentUser()?.fullName
        binding.tvPersonalEmail.text = viewModel.getCurrentUser()?.email
    }
}

