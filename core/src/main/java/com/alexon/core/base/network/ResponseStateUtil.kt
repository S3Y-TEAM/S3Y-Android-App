package com.alexon.core.base.network

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T, responseState : ResponseState<T>> globalReduceAndObserveState(
    lifecycleOwner: LifecycleOwner,
    observedData: LiveData<responseState>,
    onAny: ((resultHelper: ResultHelper<T>) -> Unit)? = null,
    onSuccess: ((data: T?) -> Unit)? = null,
    onSuccessSingleTime: ((data: T?) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onUnHandledError: ((message: String?, errors: SimpleErrors?) -> Unit)? = null,
    onHandledOrUnHandledError: ((hasBeenHandled: Boolean, message: String?, errors: SimpleErrors?) -> Unit)? = null,
    onHandledUnCompleteUserData: (() -> Unit)? = null,
    onNetworkError: (() -> Unit)? = null,
    onUnAuthorizedError: (() -> Unit)? = null,
    onEmptyState: (() -> Unit)? = null,
    onUnKnownError: (() -> Unit)? = null,
) {
    observedData.observe(lifecycleOwner) {
        globalReduceState(
            observedData = it,
            onAny = onAny,
            onSuccess = onSuccess,
            onSuccessSingleTime = onSuccessSingleTime,
            onLoading = onLoading,
            onUnHandledError = onUnHandledError,
            onHandledOrUnHandledError = onHandledOrUnHandledError,
            onUnCompleteUserData = onHandledUnCompleteUserData,
            onNetworkError = onNetworkError,
            onUnAuthorizedError = onUnAuthorizedError,
            onEmptyState = onEmptyState,
            onUnKnownError = onUnKnownError
        )
    }
}

fun <T> globalReduceState(
    observedData: ResponseState<T>,
    onAny: ((resultHelper: ResultHelper<T>) -> Unit)? = null,
    onSuccess: ((data: T?) -> Unit)? = null,
    onSuccessSingleTime: ((data: T?) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onUnHandledError: ((message: String?, errors: SimpleErrors?) -> Unit)? = null,
    onHandledOrUnHandledError: ((hasBeenHandled: Boolean, message: String?, errors: SimpleErrors?) -> Unit)? = null,
    onUnCompleteUserData: (() -> Unit)? = null,
    onUnCompleteUserDataSingleTime: (() -> Unit)? = null,
    onSocialFirstTime: (() -> Unit)? = null,
    onNetworkError: (() -> Unit)? = null,
    onUnAuthorizedError: (() -> Unit)? = null,
    onEmptyState: (() -> Unit)? = null,
    onUnKnownError: (() -> Unit)? = null,
) {
    onAny?.invoke(
        ResultHelper(
            isLoading = observedData.isLoading,
            isError = observedData.isError,
            isSuccess = observedData.isSuccess,
            result = observedData.data,
            message = observedData.message,
            errors = observedData.errors,
        )
    )

    when (observedData) {
        is ResponseState.Error<*> -> {
            onHandledOrUnHandledError?.invoke(
                observedData.hasBeenHandled,
                observedData.message,
                observedData.errors?.toSimpleErrors()
            )
            observedData.getStateIfNotHandled {
                onUnHandledError?.invoke(
                    observedData.message,
                    observedData.errors?.toSimpleErrors()
                )
            }
        }
        is ResponseState.Loading<*> -> onLoading?.invoke()
        is ResponseState.NetworkError<*> -> {
            observedData.getStateIfNotHandled { onNetworkError?.invoke() }
        }
        is ResponseState.NotAuthorized<*> -> {
            onUnAuthorizedError?.invoke()
        }
        is ResponseState.Success<T> -> {
            if (observedData.lastStep == "verify") {
                onUnCompleteUserData?.invoke()
                observedData.getStateIfNotHandled { onUnCompleteUserDataSingleTime?.invoke() }
                return
            }

            onSuccess?.invoke(observedData.data)
            if (!observedData.hasBeenHandled) {
                onSuccessSingleTime?.invoke(observedData.data)
                observedData.hasBeenHandled = true
            }
        }
        is ResponseState.Empty<*> -> {
            onEmptyState?.invoke()
        }
        is ResponseState.UnKnownError -> {
            onUnKnownError?.invoke()
        }
    }
}

private fun <T> ResponseState<T>.getStateIfNotHandled(action: () -> Unit) {
    if (!this.hasBeenHandled) {
        action()
        this.hasBeenHandled = true
    }
}

data class ResultHelper<T>(
    val isLoading: Boolean,
    val isError: Boolean,
    val isSuccess: Boolean,
    val result: T?,
    val message: String?,
    val errors: Errors?,
)
