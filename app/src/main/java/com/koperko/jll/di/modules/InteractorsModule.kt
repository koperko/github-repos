package com.koperko.jll.di.modules

import com.koperko.jll.interactors.GetRepositoriesInteractor
import org.koin.dsl.module

val interactorsModule = module {
    factory { GetRepositoriesInteractor(get()) }
}