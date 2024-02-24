package com.graduation.core.base.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.graduation.core.base.network.BaseResponse
import com.graduation.core.base.network.ResponseState
import com.graduation.core.base.network.globalNetworkCall
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.Response

open class BaseViewModel : ViewModel() {

    val error = MutableSharedFlow<String?>()
    protected fun <T : BaseResponse> networkCall(
        action: suspend () -> Response<T>,
        onReply: (suspend (ResponseState<T>) -> Unit)? = null,
        doAfter: (suspend (isSuccess: Boolean) -> Unit)? = null,
        onToken: (suspend (String) -> Unit)? = null,
        showLoading: Boolean = true,
    ): Job {
        return globalNetworkCall(
            action = action,
            onReply = onReply,
            onToken = onToken,
            doAfter = doAfter,
            showLoading = showLoading,
            scope = viewModelScope
        )
    }

}