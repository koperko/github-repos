package com.koperko.jll

import com.koperko.jll.interactors.GetRepositoriesInteractor
import com.koperko.jll.model.Repository
import com.koperko.jll.network.GithubApi
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import okhttp3.ResponseBody
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response

import org.assertj.core.api.Assertions.*

class GetRepositoriesInteractorTest {

    private var repositories = listOf(
        Repository(1, "Repo1", ""),
        Repository(2, "Repo2", ""),
        Repository(3, "Repo3", "")
    )

    @Test
    fun successResponseTest() {
        val username = "koperko"
        val githubApiMock = Mockito.mock(GithubApi::class.java)
        val interactor = GetRepositoriesInteractor(githubApiMock)
        whenever(githubApiMock.getRepositories(username)).thenReturn(
            Observable.just(Response.success(repositories))
        )

        val result = interactor.asObservable(GetRepositoriesInteractor.Params(username)).blockingFirst()

        assertThat(result).isInstanceOf(NetworkResult.Success::class.java)
        val data = (result as NetworkResult.Success).data
        assertThat(data?.size).isEqualTo(repositories.size)
    }

    @Test
    fun errorResponseTest() {
        val username = "koperko"
        val githubApiMock = Mockito.mock(GithubApi::class.java)
        val errorCode = 404
        val interactor = GetRepositoriesInteractor(githubApiMock)
        whenever(githubApiMock.getRepositories(username)).thenReturn(
            Observable.just(Response.error(errorCode, ResponseBody.create(null, "")))
        )

        val result = interactor.asObservable(GetRepositoriesInteractor.Params(username)).blockingFirst()

        assertThat(result).isInstanceOf(NetworkResult.Error::class.java)
        assertThat((result as NetworkResult.Error).code).isEqualTo(errorCode)
    }



}