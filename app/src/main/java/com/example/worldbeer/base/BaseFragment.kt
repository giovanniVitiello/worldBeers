package com.example.regolo.base

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import timber.log.Timber

@Suppress("MethodOverloading")
abstract class BaseFragment : Fragment() {
    private var navController: NavController? = null

    abstract val tAG: String?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Timber.d(tAG, "onViewCreated >> $tAG")

        try {
            navController = Navigation.findNavController(view)
        } catch (e: Exception) {
            Timber.e(tAG, "onViewCreated: $e")
        }
    }

    protected fun popBackStack() {
        if (navController != null) {
            navController?.popBackStack()
        }
    }

    val isMultipleLoad: Boolean
        get() = true

    fun popBackStack(idRes: Int, inclusive: Boolean) {
        navController?.popBackStack(idRes, inclusive)
    }

    /*
     * safe navigator
     * */
    fun safeNavigate(uri: Uri?) {
        try {
            navController?.navigate(uri!!)
        } catch (e: Exception) {
            Timber.e(tAG, "safeNavigate: $e")
        }
    }

    fun safeNavigate(idRes: Int) {
        try {
            navController?.navigate(idRes)
        } catch (e: Exception) {
            Timber.e(tAG, "safeNavigate: $e")
        }
    }

    fun safeNavigate(idRes: Int, args: Bundle?) {
        try {
            navController?.navigate(idRes, args)
        } catch (e: Exception) {
            Timber.e(tAG, "safeNavigate: $e")
        }
    }

    fun safeNavigate(idRes: Int, args: Bundle?, navOptions: NavOptions?) {
        try {
            navController?.navigate(idRes, args, navOptions)
        } catch (e: Exception) {
            Timber.e(tAG, "safeNavigate: $e")
        }
    }

    fun safeNavigate(idRes: Int, args: Bundle?, navOptions: NavOptions?, navigatorExtras: Navigator.Extras?) {
        try {
            navController?.navigate(idRes, args, navOptions, navigatorExtras)
        } catch (e: Exception) {
            Timber.e(tAG, "safeNavigate: $e")
        }
    }

    fun safeNavigate(idRes: Int, args: Bundle?, navigatorExtras: Navigator.Extras?) {
        try {
            navController?.navigate(idRes, args, null, navigatorExtras)
        } catch (e: Exception) {
            Timber.e(tAG, "safeNavigate: $e")
        }
    }

    fun safeNavigate(idRes: Int, navigatorExtras: Navigator.Extras?) {
        try {
            navController?.navigate(idRes, null, null, navigatorExtras)
        } catch (e: Exception) {
            Timber.e(tAG, "safeNavigate: $e")
        }
    }

    fun safeNavigate(idRes: Int, navOptions: NavOptions?) {
        try {
            navController?.navigate(idRes, null, navOptions)
        } catch (e: Exception) {
            Timber.e(tAG, "safeNavigate: $e")
        }
    }

    fun safeNavigate(direction: NavDirections?, navOptions: NavOptions?) {
        try {
            navController?.navigate(direction!!, navOptions)
        } catch (e: Exception) {
            Timber.e(tAG, "safeNavigate: $e")
        }
    }

    fun safeNavigate(direction: NavDirections?) {
        try {
            navController?.navigate(direction!!)
        } catch (e: Exception) {
            Timber.e(tAG, "safeNavigate: $e")
        }
    }
}
