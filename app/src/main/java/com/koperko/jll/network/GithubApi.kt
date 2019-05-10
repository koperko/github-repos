package com.koperko.jll.network

import com.koperko.jll.model.Repository
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("/users/{username}/repos")
    fun getRepositories(@Path("username") username: String): Observable<Response<List<Repository>>>

}