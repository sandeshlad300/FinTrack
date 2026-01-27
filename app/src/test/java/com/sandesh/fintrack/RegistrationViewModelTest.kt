package com.sandesh.fintrack


import com.sandesh.fintrack.ui.screens.auth.AuthEvent
import com.sandesh.fintrack.ui.screens.auth.RegistrationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class RegistrationViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login success updates state`() = runTest {
        val vm = RegistrationViewModel(FakeAuthRepository(true))

        vm.onEvent(AuthEvent.EmailChanged("test@mail.com"))
        vm.onEvent(AuthEvent.PasswordChanged("123456"))
        vm.onEvent(AuthEvent.SubmitLogin)

        advanceUntilIdle()

        assertTrue(vm.state.value.success)
        assertEquals("Sandesh", vm.state.value.name)
    }

    @Test
    fun `login failure shows error`() = runTest {
        val vm = RegistrationViewModel(FakeAuthRepository(false))

        vm.onEvent(AuthEvent.EmailChanged("x@mail.com"))
        vm.onEvent(AuthEvent.PasswordChanged("wrong"))
        vm.onEvent(AuthEvent.SubmitLogin)

        advanceUntilIdle()

        assertNotNull(vm.state.value.errorMessage)
    }

    @Test
    fun `register password mismatch shows error`() = runTest {
        val vm = RegistrationViewModel(FakeAuthRepository(true))

        vm.onEvent(AuthEvent.NameChanged("User"))
        vm.onEvent(AuthEvent.EmailChanged("u@mail.com"))
        vm.onEvent(AuthEvent.PasswordChanged("123"))
        vm.onEvent(AuthEvent.ConfirmPasswordChanged("456"))
        vm.onEvent(AuthEvent.SubmitRegistration)

        advanceUntilIdle()

        assertEquals("Passwords do not match", vm.state.value.errorMessage)
    }
}
