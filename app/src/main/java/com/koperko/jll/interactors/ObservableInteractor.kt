package com.koperko.jll.interactors

import io.reactivex.Observable

/**
 * The interface interactors that can behave like observables.
 *
 * @param <T> the type parameter for observable type
 * @param <E> the type parameter for params object holding parameters to pass to observable
 */
interface ObservableInteractor<T, in E> {

    /**
     * Returns current object as observable to which clients can subscribe.
     *
     * @param params input parameters
     * @return the observable
     */
    fun asObservable(params: E): Observable<T>
}
