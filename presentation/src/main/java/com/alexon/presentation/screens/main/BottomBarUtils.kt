//package com.alexon.presentation.screens.main
//
//import android.content.Intent
//import android.view.View
//import android.view.ViewGroup
//import androidx.annotation.DrawableRes
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat
//import androidx.transition.ChangeBounds
//import androidx.transition.TransitionManager
//import androidx.transition.TransitionSet
//import com.alexon.common.utils.sharedPrefernces.SharedPreferenceHelper
//import com.alexon.presentation.databinding.ActivityMainBinding
//import com.alexon.presentation.R
//
///** This class used to setup the bottom bar item's title , icon , color , click and animation **/
//
//class BottomBarUtils(
//    private val activity: AppCompatActivity,
//    private val navController: NavController,
//    private val binding: ActivityMainBinding,
//    private val sharedPreferenceHelper: SharedPreferenceHelper
//) {
//
//    private val tag = "BottomBarUtils"
//    private val clickWithThrottleDuration = 1000L
//    private val bottomBarItemAnimationDuration = 50L
//    private var selectedDestination = -1
//
//    fun setupBottomBar() {
//        setupItemTitleIcon()
//        setupOnItemClick()
//        setupWhenDestinationChanged()
//    }
//
//    /** setup title , icon **/
//    private fun setupItemTitleIcon() {
//        val resources = binding.bottomBar.root.context.resources
//        //home
//        setTitleAndIconToItem(
//            binding.bottomBar.homeFragment,
//            resources.getString(R.string.home),
//            R.drawable.footer_home
//        )
//        //reservations
//        setTitleAndIconToItem(
//            binding.bottomBar.myReservationsFragment,
//            resources.getString(R.string.reservation),
//            R.drawable.footer_calender
//        )
//        //profile
//        setTitleAndIconToItem(
//            binding.bottomBar.notificationFragment,
//            resources.getString(R.string.notifications),
//            R.drawable.footer_notifications
//        )
//        //more
//        setTitleAndIconToItem(
//            binding.bottomBar.profileFragment,
//            resources.getString(R.string.profile),
//            R.drawable.footer_profile
//        )
//    }
//
//    private fun setTitleAndIconToItem(
//        item: MainBottomBarItemBinding,
//        title: String,
//        @DrawableRes icon: Int,
//    ) {
//        item.title.apply {
//            text = title
//            setColorOfText(R.color.blue_6) // default unselected color
//        }
//        item.icon.apply {
//            setImageResource(icon)
//            setColorOfImage(R.color.blue_6) // default unselected color
//        }
//    }
//
//    /** click **/
//    //clickWithThrottle added to avoid crashes
//    private fun setupOnItemClick() {
//        doOnItemClicked(R.id.homeFragment, binding.bottomBar.homeFragment)
//        doOnItemClicked(R.id.myReservationsFragment, binding.bottomBar.myReservationsFragment)
//        doOnItemClicked(R.id.notificationsFragment, binding.bottomBar.notificationFragment)
//        doOnItemClicked(R.id.profileFragment, binding.bottomBar.profileFragment)
//    }
//
//    private fun doOnItemClicked(
//        destination: Int,
//        item: MainBottomBarItemBinding? = null,
//    ) {
//
//        //myReservations
//        if (item == binding.bottomBar.myReservationsFragment) {
//            item.root.clickWithThrottle(clickWithThrottleDuration) {
//                if (sharedPreferenceHelper.token == "") {
//                    //show dialog
//                    DialogNormalActions(
//                        onLoginClicked = {
//                            activity.startActivity(
//                                Intent(
//                                    activity,
//                                    AuthActivity::class.java
//                                ).putExtra(Constants.AUTH_DESTINATION_KEY, AUTH_DESTINATION_LOGIN)
//                            )
//                        },
//                    ).show(
//                        activity.supportFragmentManager,
//                        DialogNormalActions.TAG
//                    )
//                } else {
//                    if (selectedDestination != -1) {
//                        if (selectedDestination == destination) return@clickWithThrottle
//                    }
//                    selectedDestination = destination
//
//                    navController.navigate(destination)
//                    animateSelectedItem(item)
//                }
//            }
//            return
//        }
//
//        //notifications
//        if (item == binding.bottomBar.notificationFragment) {
//            item.root.clickWithThrottle(clickWithThrottleDuration) {
//                if (sharedPreferenceHelper.token == "") {
//                    DialogNormalActions(
//                        onLoginClicked = {
//                            activity.startActivity(
//                                Intent(
//                                    activity,
//                                    AuthActivity::class.java
//                                ).putExtra(Constants.AUTH_DESTINATION_KEY, AUTH_DESTINATION_LOGIN)
//                            )
//                        },
//                    ).show(
//                        activity.supportFragmentManager,
//                        DialogNormalActions.TAG
//                    )
//                } else {
//                    if (selectedDestination != -1) {
//                        if (selectedDestination == destination) return@clickWithThrottle
//                    }
//                    selectedDestination = destination
//
//                    navController.navigate(destination , )
//                    animateSelectedItem(item)
//
//                }
//            }
//            return
//        }
//
//
//        item?.root?.clickWithThrottle(clickWithThrottleDuration) {
//            if (selectedDestination != -1) {
//                if (selectedDestination == destination) return@clickWithThrottle
//            }
//            selectedDestination = destination
//            navController.navigate(destination)
//
//            animateSelectedItem(item)
//        }
//    }
//
//    /** animation **/
//    private fun animateSelectedItem(
//        item: MainBottomBarItemBinding? = null,
//    ) {
//        try {
//            if (item != null) {
//                val ts = TransitionSet()
//                ts.addTransition(ChangeBounds())
//                ts.duration = 100
//
//                TransitionManager.beginDelayedTransition((item.root as ViewGroup), ts)
//            }
//        } catch (e: Exception) {
//            logMe(tag, "Catch error ${e.message}")
//        }
//    }
//
//    /** color **/
//    private fun setSelectedItemChip(selectedItem: MainBottomBarItemBinding) {
//        val bottomBarItems = listOf(
//            binding.bottomBar.homeFragment,
//            binding.bottomBar.myReservationsFragment,
//            binding.bottomBar.notificationFragment,
//            binding.bottomBar.profileFragment
//        )
//
//        for (item in bottomBarItems) {
//            if (item == selectedItem) {
//                item.icon.setColorOfImage(R.color.white)
//                item.title.setColorOfText(R.color.white)
//                item.container.backgroundTintList = ContextCompat.getColorStateList(
//                    item.container.context, R.color.blue_6
//                )
//                item.title.visibility = View.VISIBLE
//            } else {
//                item.icon.setColorOfImage(R.color.blue_6)
//                item.title.setColorOfText(R.color.blue_6)
//                item.container.backgroundTintList = ContextCompat.getColorStateList(
//                    item.container.context, R.color.white
//                )
//                item.title.visibility = View.GONE
//            }
//        }
//    }
//
//    private fun setupWhenDestinationChanged() {
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            selectedDestination = destination.id
//            setupDisplayOfBottomBarInFragments(destination.id)
//            colorSelectedDestination(destination.id)
//        }
//    }
//
//    private fun setupDisplayOfBottomBarInFragments(destination: Int) {
//
//        when (destination) {
//            //show bottom bar on these fragments
//            R.id.homeFragment,
//            R.id.notificationsFragment,
//            R.id.profileFragment,
//            R.id.myReservationsFragment -> {
//                binding.bottomBar.root.visibility = View.VISIBLE
//            }
//            //hide bottom bar for other fragments
//            else -> {
//                binding.bottomBar.root.visibility = View.GONE
//            }
//        }
//    }
//
//    private fun colorSelectedDestination(destination: Int) {
//        when (destination) {
////            R.id.homeFragment -> {
////                setSelectedItemChip(binding.bottomBar.homeFragment)
////            }
////
////            R.id.myReservationsFragment -> {
////                setSelectedItemChip(binding.bottomBar.myReservationsFragment)
////            }
////
////            R.id.notificationsFragment -> {
////                setSelectedItemChip(binding.bottomBar.notificationFragment)
////            }
////
////            R.id.profileFragment -> {
////                setSelectedItemChip(binding.bottomBar.profileFragment)
////            }
//
//        }
//    }
//}