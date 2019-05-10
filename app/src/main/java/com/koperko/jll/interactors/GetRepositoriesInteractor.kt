package com.koperko.jll.interactors

import com.koperko.jll.model.Repository
import com.koperko.jll.network.GithubApi
import io.reactivex.Observable
import retrofit2.Response

class GetRepositoriesInteractor(
    private val githubApi: GithubApi
) : NetworkInteractor<List<Repository>, GetRepositoriesInteractor.Params>() {

    data class Params(val username: String)

    override fun createObservable(params: Params): Observable<Response<List<Repository>>> {
        return githubApi.getRepositories(params.username)
    }
}