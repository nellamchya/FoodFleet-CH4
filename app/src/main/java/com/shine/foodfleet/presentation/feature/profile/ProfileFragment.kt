package com.shine.foodfleet.presentation.feature.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shine.foodfleet.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val profileText: TextView = binding.profileText
        val textUsername: TextView = binding.textUsername
        val textPassword: TextView = binding.textPassword
        val textEmail: TextView = binding.textEmail
        val textTelepon: TextView = binding.textTelepon

        // Observasi LiveData dan menghubungkannya dengan tampilan
        viewModel.profileText.observe(viewLifecycleOwner) { newText ->
            profileText.text = newText
        }

        viewModel.username.observe(viewLifecycleOwner) { newUsername ->
            textUsername.text = newUsername
        }

        viewModel.password.observe(viewLifecycleOwner) { newPassword ->
            textPassword.text = newPassword
        }

        viewModel.email.observe(viewLifecycleOwner) { newEmail ->
            textEmail.text = newEmail
        }

        viewModel.telepon.observe(viewLifecycleOwner) { newTelepon ->
            textTelepon.text = newTelepon
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
