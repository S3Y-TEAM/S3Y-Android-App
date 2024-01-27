package com.graduation.core.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.graduation.core.R
import com.graduation.core.base.BaseEvent
import com.graduation.core.base.network.ResponseState
import com.graduation.core.base.network.ResultHelper
import com.graduation.core.base.network.SimpleErrors
import com.graduation.core.base.network.globalReduceAndObserveState
import com.graduation.core.base.network.globalReduceState
import com.graduation.core.extensions.navigation.onBackPress
import com.graduation.core.utils.sharedPrefernces.EncryptedSharedPreference
import com.graduation.core.utils.sharedPrefernces.SharedPreferenceHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding>(
    private val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    //binding
    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    abstract val viewModel: BaseViewModel

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    @Inject
    lateinit var encryptedSharedPreference: EncryptedSharedPreference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (_binding == null) {
            _binding = inflateMethod.invoke(inflater, container, false)
        }
        return binding.root
    }

    /** LiveData Observers **/
    fun <T> LiveData<BaseEvent<T>>.observeIfNotHandled(result: (T) -> Unit) {
        this.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                result(it)
            }
        }
    }

    fun <T> LiveData<T>.observeIfNotNull(observe: (T) -> Unit) {
        this.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            observe(it)
        }
    }

    fun <T, responseState : ResponseState<T>> reduceAndObserveState(
        observedData: LiveData<responseState>,
        showNetworkError: Boolean = true,
        showUnknownError: Boolean = true,
        showUnAuthorizedError: Boolean = true,
        onAny: ((resultHelper: ResultHelper<T>) -> Unit)? = null,
        onSuccess: ((data: T?) -> Unit)? = null,
        onSuccessSingleTime: ((data: T?) -> Unit)? = null,
        onLoading: (() -> Unit)? = null,
        onUnHandledError: ((message: String?, errors: SimpleErrors?) -> Unit)? = null,
        onHandledOrUnHandledError: ((hasBeenHandled: Boolean, message: String?, errors: SimpleErrors?) -> Unit)? = null,
        onHandledUnCompleteUserData: (() -> Unit)? = null,
        onEmptyState: (() -> Unit)? = null,
    ) {
        globalReduceAndObserveState(
            lifecycleOwner = viewLifecycleOwner,
            observedData = observedData,
            onAny = onAny,
            onSuccess = onSuccess,
            onSuccessSingleTime = onSuccessSingleTime,
            onLoading = onLoading,
            onUnHandledError = { message, errors ->
                onUnHandledError?.invoke(message, errors)
            },
            onHandledOrUnHandledError = onHandledOrUnHandledError,
            onHandledUnCompleteUserData = onHandledUnCompleteUserData,
            onNetworkError = {
                if (showNetworkError) {
                    lifecycleScope.launch { viewModel.error.emit(getString(R.string.network_error)) }
                }
            },
            onUnAuthorizedError = {
                if (showUnAuthorizedError) {
                    lifecycleScope.launch { viewModel.error.emit(getString(R.string.un_authorized)) }
                }
                logout()
            }, onUnKnownError = {
                if (showUnknownError) {
                    lifecycleScope.launch { viewModel.error.emit(getString(R.string.unkown_error)) }
                }
            },
            onEmptyState = onEmptyState
        )
    }

    /** Flow Collectors **/

    fun <T> StateFlow<T>.awareCollect(action: suspend (value: T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                this@awareCollect.collectLatest { action(it) }
            }
        }
    }

    fun <T> StateFlow<T>.collectIfNotNull(action: suspend (value: T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                this@collectIfNotNull.collectLatest {
                    if (it == null) return@collectLatest
                    else action(it)
                }
            }
        }
    }

    fun <T> SharedFlow<T>.awareCollect(action: suspend (value: T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                this@awareCollect.collectLatest { action(it) }
            }
        }
    }

    fun <T> StateFlow<ResponseState<T>>.awareCollectWithReduce(
        showNetworkError: Boolean = true,
        showUnknownError: Boolean = true,
        showUnAuthorizedError: Boolean = true,
        showGeneralError: Boolean = true,
        onAny: ((resultHelper: ResultHelper<T>) -> Unit)? = null,
        onSuccess: ((data: T?) -> Unit)? = null,
        onSuccessSingleTime: ((data: T?) -> Unit)? = null,
        onLoading: (() -> Unit)? = null,
        onUnHandledError: ((message: String?, errors: SimpleErrors?) -> Unit)? = null,
        onHandledOrUnHandledError: ((hasBeenHandled: Boolean, message: String?, errors: SimpleErrors?) -> Unit)? = null,
        onHandledUnCompleteUserData: (() -> Unit)? = null,
        onUnAuthorizedError: (() -> Unit)? = null,
        onNetworkError: (() -> Unit)? = null,
        onEmptyState: (() -> Unit)? = null,
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                this@awareCollectWithReduce.collectLatest {
                    globalReduceState(
                        observedData = it,
                        onAny = onAny,
                        onSuccess = onSuccess,
                        onSuccessSingleTime = onSuccessSingleTime,
                        onLoading = onLoading,
                        onUnHandledError = { message, errors ->
                            showErrorIf(showGeneralError, message)
                            onUnHandledError?.invoke(message, errors)
                        },
                        onHandledOrUnHandledError = onHandledOrUnHandledError,
                        onUnCompleteUserData = onHandledUnCompleteUserData,
                        onNetworkError = {
                            showErrorIf(showNetworkError, getString(R.string.network_error))
                            onNetworkError?.invoke()
                        },
                        onUnAuthorizedError = {
                            showErrorIf(showUnAuthorizedError, getString(R.string.un_authorized))
                            if (showUnAuthorizedError) logout()
                            onUnAuthorizedError?.invoke()
                        },
                        onEmptyState = onEmptyState,
                        onUnKnownError = {
                            showErrorIf(showUnknownError, getString(R.string.unkown_error))
                        }
                    )
                }
            }
        }
    }

    fun <T> reduceState(
        observedData: ResponseState<T>,
        showNetworkError: Boolean = true,
        showUnknownError: Boolean = true,
        showUnAuthorizedError: Boolean = true,
        showGeneralError: Boolean = true,
        onAny: ((resultHelper: ResultHelper<T>) -> Unit)? = null,
        onSuccess: ((data: T?) -> Unit)? = null,
        onSuccessSingleTime: ((data: T?) -> Unit)? = null,
        onLoading: (() -> Unit)? = null,
        onUnHandledError: ((message: String?, errors: SimpleErrors?) -> Unit)? = null,
        onHandledOrUnHandledError: ((hasBeenHandled: Boolean, message: String?, errors: SimpleErrors?) -> Unit)? = null,
        onUnCompleteUserData: (() -> Unit)? = null,
        onUnCompleteUserDataSingleTime: (() -> Unit)? = null,
        onSocialFirstTime: (() -> Unit)? = null,
        onEmptyState: (() -> Unit)? = null,
        onUnAuthorizedError: (() -> Unit)? = null,
        onNetworkError: (() -> Unit)? = null,
        onUnknownError: (() -> Unit)? = null,
    ) {
        globalReduceState(
            observedData = observedData,
            onAny = onAny,
            onSuccess = onSuccess,
            onSuccessSingleTime = onSuccessSingleTime,
            onLoading = onLoading,
            onSocialFirstTime = onSocialFirstTime,
            onUnHandledError = { message, errors ->
                showErrorIf(showGeneralError, message)
                onUnHandledError?.invoke(message, errors)
            },
            onHandledOrUnHandledError = onHandledOrUnHandledError,
            onUnCompleteUserDataSingleTime = onUnCompleteUserDataSingleTime,
            onUnCompleteUserData = onUnCompleteUserData,
            onNetworkError = {
                showErrorIf(
                    showNetworkError,
                    getString(R.string.network_error)
                )
                onNetworkError?.invoke()
            },
            onUnAuthorizedError = {
                showErrorIf(
                    showUnAuthorizedError,
                    getString(R.string.un_authorized)
                )
                if (showUnAuthorizedError) logout()
                onUnAuthorizedError?.invoke()

            },
            onEmptyState = onEmptyState,
            onUnKnownError = {
                showErrorIf(
                    showUnknownError,
                    getString(R.string.unkown_error)
                )
                onUnknownError?.invoke()
            }
        )
    }

    private fun showErrorIf(condition: Boolean, error: String?) {
        if (error == null) return
        if (!condition) return
        lifecycleScope.launch { viewModel.error.emit(error) }
    }

   abstract fun logout()

    fun doubleBackToExitApp() {
        var exitCounter = 0
        onBackPress {
            if (exitCounter == 1) {
                requireActivity().finish()
                return@onBackPress
            }
            exitCounter++
//            Toast.makeText(
//                requireActivity(), getString(R.string.click_one_more_to_exit), Toast.LENGTH_LONG
//            ).show()
            lifecycleScope.launch {
                delay(1500)
                exitCounter = 0
            }
        }
    }

}
