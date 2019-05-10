package com.koperko.jll.di.modules

import com.koperko.jll.viewmodels.RepositoryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { RepositoryViewModel(get()) }
}