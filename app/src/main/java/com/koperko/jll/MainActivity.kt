package com.koperko.jll

import org.koin.android.viewmodel.ext.android.viewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import com.koperko.jll.model.Repository
import com.koperko.jll.viewmodels.RepositoryViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.Koin
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private val repositoriesViewModel by inject<RepositoryViewModel>()
    private val disposables = CompositeDisposable()
    private val repositoriesAdapter = RepositoriesAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repositoriesView.layoutManager = LinearLayoutManager(this)
        repositoriesView.adapter = repositoriesAdapter
    }

    override fun onResume() {
        super.onResume()
        disposables.addAll(
            repositoriesViewModel.getRepositoriesObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateRepositories),
            repositoriesViewModel.getFlashMessagesObservable()
                .subscribe(this::showMessage),
            usernameView.afterTextChangeEvents()
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .map { it.editable?.toString() ?: "" }
                .subscribe { repositoriesViewModel.loadRepositories(it) }
        )
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }

    private fun updateRepositories(repositories: List<Repository>) {
        repositoriesAdapter.repositories = repositories
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}
