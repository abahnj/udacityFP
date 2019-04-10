package com.norvera.confession.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.norvera.confession.data.AppRepository
import com.norvera.confession.utils.InjectorUtils

class MainViewModelFactory private constructor(private val mAppRepository: AppRepository) :
    ViewModelProvider.NewInstanceFactory() {

    // Note: This can be reused with minor modifications
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return MainViewModel(mAppRepository) as T
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: MainViewModelFactory? = null

        fun getInstance(context: Context): MainViewModelFactory? {

            if (INSTANCE == null) {
                synchronized(MainViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            MainViewModelFactory(
                                InjectorUtils.provideAppRepository(
                                    InjectorUtils.provideDatabase(context),
                                    InjectorUtils.provideAppExecutor()
                                )
                            )
                    }
                }
            }
            return INSTANCE
        }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
