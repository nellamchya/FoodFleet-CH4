package com.shine.foodfleet.presentation.feature.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shine.foodfleet.data.repository.CartRepository
import com.shine.foodfleet.utils.ResultWrapper
import com.tools.MainCoroutineRule
import com.tools.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CartViewModelTest {

    @MockK
    private lateinit var cartRepository: CartRepository

    private lateinit var viewModel: CartViewModel

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { cartRepository.getCartData() } returns flow {
            emit(
                ResultWrapper.Success(
                    Pair(
                        listOf(
                            mockk(relaxed = true),
                            mockk(relaxed = true),
                            mockk(relaxed = true)
                        ),
                        150000
                    )
                )
            )
        }
        viewModel = spyk(CartViewModel(cartRepository))

        val resultMock = flow {
            emit(ResultWrapper.Success(true))
        }
        coEvery { cartRepository.increaseCart(any()) } returns resultMock
        coEvery { cartRepository.decreaseCart(any()) } returns resultMock
        coEvery { cartRepository.setCartNotes(any()) } returns resultMock
        coEvery { cartRepository.deleteCart(any()) } returns resultMock
    }

    @Test
    fun `test cart list`() {
        val result = viewModel.cartList.getOrAwaitValue()
        assertEquals(result.payload?.first?.size, 3)
        assertEquals(result.payload?.second, 150000)
    }

    @Test
    fun `test increase cart`() {
        viewModel.increaseCart(mockk())
        coVerify { cartRepository.increaseCart(any()) }
    }

    @Test
    fun `test decrease cart`() {
        viewModel.decreaseCart(mockk())
        coVerify { cartRepository.decreaseCart(any()) }
    }

    @Test
    fun `test set cart notes cart`() {
        viewModel.setCartNotes(mockk())
        coVerify { cartRepository.setCartNotes(any()) }
    }

    @Test
    fun `test delete cart`() {
        viewModel.deleteCart(mockk())
        coVerify { cartRepository.deleteCart(any()) }
    }
}
