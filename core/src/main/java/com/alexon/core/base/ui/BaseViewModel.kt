package com.alexon.core.base.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexon.core.base.network.BaseResponse
import com.alexon.core.base.network.ResponseState
import com.alexon.core.base.network.globalNetworkCall
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.Response

open class BaseViewModel : ViewModel() {

    val error = MutableSharedFlow<String?>()
    protected fun <T : BaseResponse> networkCall(
        action: suspend () -> Response<T>,
        onReply: (suspend (ResponseState<T>) -> Unit)? = null,
        doAfter: (suspend (isSuccess: Boolean) -> Unit)? = null,
        showLoading: Boolean = true,
    ): Job {
        return globalNetworkCall(
            action = action,
            onReply = onReply,
            doAfter = doAfter,
            showLoading = showLoading,
            scope = viewModelScope
        )
    }

}