package com.graduation.core.extensions.navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

fun Fragment.onBackPress(action: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                action()
            }
        })
}

/**** NavigateAndClear ****/

fun Fragment.navigateAndClearStack(popBackDestination: Int, withRestart: Boolean = false) {
    findNavController().popBackStack(popBackDestination, false)
    if (withRestart) findNavController().navigate(popBackDestination)
}

fun Fragment.startActivityAndClearStack(destinationActivity: Class<*>) {
    requireActivity().startActivity(
        Intent(
            requireActivity(),
            destinationActivity
        ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    )
}

fun Fragment.navigateTo(destination: Int) {
    findNavController().navigate(destination)
}

/**
 * This method used to navigate to destination with maps of keys to parse it as bundle
 *
 * @param destination The destination to be opended
 * @param extras map of Pair(key,value)
 */
fun Fragment.navigateToWithBundle(
    destination: Int,
    extras: Map<String, Any>,
) {
    val bundle = Bundle().apply {
        extras.forEach {
            //string
            if (it.value is String) {
                putString(it.key, it.value.toString())
            }
            //int
            if (it.value is Int) {
                putInt(it.key, it.value as Int)
            }
            //boolean
            if (it.value is Boolean) {
                putBoolean(it.key, it.value as Boolean)
            }
        }
    }
    findNavController().navigate(destination, bundle)
}

/**
 * This method used to navigate to another fragment from another graph (GlobalAction)
 *
 * @param newDestinationGraphId pass the id of graph that holds the fragment you want to route to it
 * @param newDestinationId the fragment you want to navigate to
 *
 * Note:
 * go to fragment that you want to navigate to and right click then action then global
 *
 * Warning:
 * if you use any helper or util that needs activity or any context
 * in the newFragment it will crash as it is not starting Activity
 * it put the newFragment top of the current fragment
 */
fun NavController.navigateGlobalToFragment(
    newDestinationGraphId: Int, newDestinationId: Int
) {
    setGraph(newDestinationGraphId)
    navigate(newDestinationId)
}

fun NavController.navigateToAction(
    action: NavDirections,
    popBackToDestination: Int = -1,
    isInclusive: Boolean = false,
    saveState: Boolean = false,
    haveAnimation: Boolean = true,
    navOptions: NavOptions.Builder = NavOptions.Builder(),
) {

    try {
        if (!haveAnimation) {
            navigate(action, navOptions.build())
            return
        }

        navOptions.apply {
            setEnterAnim(android.R.anim.fade_in)
            setPopEnterAnim(android.R.anim.fade_in)
            if (popBackToDestination != -1) setPopUpTo(popBackToDestination, isInclusive, saveState)
        }

        navigate(action, navOptions.build())
    } catch (e: Exception) {
        Log.e("navigateToAction", e.localizedMessage ?: "navigateToAction Unknown error")
    }
}


/**** PopWithBundle ****/
fun <T> Fragment.observePoppedStackBundle(key: String, onResult: (T) -> Unit) {
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
        ?.observe(viewLifecycleOwner) {
            onResult.invoke(it)
        }
}

fun Fragment.popBackStackWithBundle(key: String, value: String) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, value)
    findNavController().navigateUp()
}
