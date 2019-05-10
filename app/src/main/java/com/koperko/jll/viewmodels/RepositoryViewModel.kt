package com.koperko.jll.viewmodels

import androidx.lifecycle.ViewModel
import com.koperko.jll.NetworkResult
import com.koperko.jll.interactors.GetRepositoriesInteractor
import com.koperko.jll.model.Repository
import com.koperko.jll.network.GithubApi
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class RepositoryViewModel(
    private val getRepositoriesInteractor: GetRepositoriesInteractor
) : ViewModel() {

    private var repositoriesSubject = BehaviorSubject.createDefault(listOf<Repository>())
    private var flashMessagesSubject = PublishSubject.create<String>()
    private var repositoriesDisposable: Disposable? = null

    fun loadRepositories(username: String) {
        repositoriesDisposable?.dispose()

        repositoriesDisposable = getRepositoriesInteractor.asObservable(GetRepositoriesInteractor.Params(username))
            .subscribe { result ->
                when (result) {
                    is NetworkResult.Success -> result.data?.let { repositoriesSubject.onNext(it) }
                    is NetworkResult.Error -> {
                        repositoriesSubject.onNext(listOf())
                        flashMessagesSubject.onNext(result.message)
                    }
                }
            }
    }

    fun getRepositoriesObservable(): Observable<List<Repository>>
            = repositoriesSubject.toFlowable(BackpressureStrategy.BUFFER).toObservable()

    fun getFlashMessagesObservable(): Observable<String>
            = flashMessagesSubject.toFlowable(BackpressureStrategy.BUFFER).toObservable()

    override fun onCleared() {
        super.onCleared()
        repositoriesDisposable?.dispose()
    }

}