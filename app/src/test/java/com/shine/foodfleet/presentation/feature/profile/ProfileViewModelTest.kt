package com.shine.foodfleet.presentation.feature.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shine.foodfleet.data.repository.UserRepository
import com.shine.foodfleet.model.User
import com.shine.foodfleet.utils.ResultWrapper
import com.tools.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ProfileViewModelTest {

    @MockK
    lateinit var repo: UserRepository

    private lateinit var viewModel: ProfileViewModel

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(
        UnconfinedTestDispatcher()
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(ProfileViewModel(repo))
        val updateResult = flow {
            emit(ResultWrapper.Success(true))
        }
        coEvery { repo.getCurrentUser() } returns User("fullname", "url", "email")
        coEvery { repo.sendChangePasswordRequestByEmail() } returns true
        coEvery { repo.doLogout() } returns true
        coEvery { repo.updateProfile(any(), any()) } returns updateResult
    }

    @Test
    fun`get current user`() {
        viewModel.getCurrentUser()
        coVerify { repo.getCurrentUser() }
    }

    @Test
    fun`do logout`() {
        viewModel.doLogout()
        coVerify { repo.doLogout() }
    }
}
