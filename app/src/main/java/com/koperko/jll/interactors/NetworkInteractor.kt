package com.koperko.jll.interactors

import com.koperko.jll.NetworkResult
import com.koperko.jll.toResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Response

/**
 * Network interactor interface
 *
 * @param <T> result type
 * @param <E> params type
 */
abstract class NetworkInteractor<T, in E> : ObservableInteractor<NetworkResult<T>, E> {


    /**
     * Returns observable that will be invoked on IO scheduler
     *
     * @param params params to pass to observable
     * @return the observable
     */
    override fun asObservable(params: E): Observable<NetworkResult<T>> {
        return createObservable(params)
            .map { it.toResult() }

    }

    /**
     * Create network observable.
     *
     * @param params the params to pass to observable
     * @return the observable
     */
    protected abstract fun createObservable(params: E): Observable<Response<T>>

}
