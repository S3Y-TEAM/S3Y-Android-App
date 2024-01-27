package com.graduation.core.extensions.navigation

import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun FragmentActivity.onBackPress(action: () -> Unit) {
    onBackPressedDispatcher.addCallback(this,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                action()
            }
        })
}

fun AppCompatActivity.startActivity(activity: AppCompatActivity, finish: Boolean = false) {
    startActivity(Intent(this, activity::class.java))
    if (finish) finish()
}

fun AppCompatActivity.startActivityWithExtras(
    activity: AppCompatActivity,
    extras: Map<String, Any>,
    finish: Boolean = false
) {
    val intent = Intent(this, activity::class.java)
    extras.forEach {
        //string
        if (it.value is String) {
            intent.putExtra(it.key, it.value.toString())
        }
        //int
        if (it.value is Int) {
            intent.putExtra(it.key, it.value as Int)
        }
        //boolean
        if (it.value is Boolean) {
            intent.putExtra(it.key, it.value as Boolean)
        }
    }
    startActivity(intent)
    if (finish) finish()
}

/**** Destination ****/

fun AppCompatActivity.setStartDestination(
    @IdRes navHostId: Int, @NavigationRes graphId: Int, @IdRes destinationId: Int
) {
    val navHostFragment = supportFragmentManager.findFragmentById(navHostId) as NavHostFragment
    val graph = navHostFragment.navController.navInflater.inflate(graphId)
    graph.setStartDestination(destinationId)
    navHostFragment.navController.graph = graph
}

/**** NavController ****/
fun AppCompatActivity.getNavController(navHost: Int): NavController {
    val navHostFragment = this.supportFragmentManager.findFragmentById(navHost) as NavHostFragment
    return navHostFragment.navController
}