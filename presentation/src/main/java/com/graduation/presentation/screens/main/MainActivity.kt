package com.graduation.presentation.screens.main

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.graduation.core.base.ui.SharedViewModel
import com.graduation.core.extensions.navigation.onBackPress
import com.graduation.core.extensions.screen.changeStatusBarColor
import com.graduation.core.utils.sharedPrefernces.EncryptedSharedPreference
import com.graduation.core.utils.sharedPrefernces.SharedPreferenceHelper
import com.graduation.presentation.Constants
import com.graduation.presentation.Constants.DEVELOPER_KEY
import com.graduation.presentation.Constants.SKILLED_KEY

import com.graduation.presentation.R
import com.graduation.presentation.databinding.ActivityMainBinding
import com.graduation.presentation.screens.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.NonCancellable.start
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    @Inject
    lateinit var encryptedSharedPreference: EncryptedSharedPreference

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerToggle: ActionBarDrawerToggle

    val sharedViewModel: SharedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observation()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStatusBar()
        setAppBar()
        checkRole()
        setDrawerNavigation()

        binding.appBarMain.contentMainId.logoutButton.apply {
            setOnClickListener {
                start()
                encryptedSharedPreference.loggedIn = ""
                complete(true)
                successLogin()
            }
        }
    }

    private fun successLogin() {
        startActivity(Intent(this, AuthActivity::class.java))
        this.finish()
    }

    private fun checkRole() {
        if (encryptedSharedPreference.userData.role.equals(DEVELOPER_KEY)
            ||
            encryptedSharedPreference.userData.role.equals(SKILLED_KEY)
        ) {
            binding.appBarMain.contentMainId.homeBottomNavigation.visibility = View.VISIBLE
            setBottomNavigation(true)

        } else {
            binding.appBarMain.contentMainId.coordinator.visibility = View.VISIBLE
            setBottomNavigation(false)
        }
    }

    private fun setBottomNavigation(dev: Boolean) {
        if (dev) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.home_bottom_navigation_host_fragment) as NavHostFragment
            val navInflater = navHostFragment.navController.navInflater
            val navGraph = navInflater.inflate(R.navigation.home_graph)
            navHostFragment.navController.graph = navGraph

            val bottomNavigationView =
                findViewById<BottomNavigationView>(R.id.home_bottom_navigation)
            NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)

            val colorStateList = ColorStateList.valueOf(resources.getColor(R.color.main_color))

            bottomNavigationView.itemIconTintList = ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_checked),
                    intArrayOf(-android.R.attr.state_checked)
                ),
                intArrayOf(
                    colorStateList.defaultColor,
                    ContextCompat.getColor(this, R.color.main_color_3)
                )
            )
        } else {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.home_bottom_navigation_host_fragment) as NavHostFragment
            val navInflater = navHostFragment.navController.navInflater
            val navGraph = navInflater.inflate(R.navigation.client_graph)
            navHostFragment.navController.graph = navGraph

            val bottomNavigationView =
                findViewById<BottomNavigationView>(R.id.navView)
            NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)

            val colorStateList = ColorStateList.valueOf(resources.getColor(R.color.white))

            bottomNavigationView.itemIconTintList = ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_checked),
                    intArrayOf(-android.R.attr.state_checked)
                ),
                intArrayOf(
                    colorStateList.defaultColor,
                    ContextCompat.getColor(this, R.color.main_color_3)
                )
            )

        }
    }

    private fun setDrawerNavigation() {


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration =
            AppBarConfiguration(
                navGraph = navController.graph,
                drawerLayout
            )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        setupDrawerToggle(drawerLayout)

        navController.addOnDestinationChangedListener { _, _, _ ->
            binding.appBarMain.mainAppBar.toolbar.setNavigationIcon(R.drawable.app_logo_small)
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            // Handle navigation view item clicks here
            when (menuItem.itemId) {
                R.id.nav_profile -> {
                    navigationAction()
                    navController.navigate(R.id.nav_profile)
                }

                R.id.nav_bookmarks -> {
                    navigationAction()
                    navController.navigate(R.id.nav_bookmarks)
                }

                R.id.nav_about_app -> {
                    navigationAction()
                    navController.navigate(R.id.nav_about_app)
                }

                R.id.nav_about_team -> {
                    navigationAction()
                    navController.navigate(R.id.nav_about_team)
                }

                R.id.nav_contact_us -> {
                    navigationAction()
                    navController.navigate(R.id.nav_contact_us)
                }

                R.id.nav_setting -> {
                    navigationAction()
                    navController.navigate(R.id.nav_setting)
                }

                R.id.nav_rate_us -> {
                    navigationAction()
                    navController.navigate(R.id.nav_rate_us)
                }
                R.id.nav_logout ->{
                    startActivity(Intent(this, AuthActivity::class.java))
                    this.finish()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun setAppBar() {
        setSupportActionBar(binding.appBarMain.mainAppBar.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.appBarMain.mainAppBar.appBarBackArrow.visibility = View.GONE
    }

    private fun setStatusBar() {
        changeStatusBarColor(
            R.color.background,
            isContentLight = false,
            isTransparent = false
        )
    }

    private fun navigationAction() {
        binding.appBarMain.contentMainId.navHostFragmentContentMain.visibility = View.VISIBLE
        binding.appBarMain.contentMainId.homeBottomNavigation.visibility = View.GONE
        binding.appBarMain.contentMainId.coordinator.visibility = View.GONE
        binding.appBarMain.contentMainId.homeBottomNavigationHostFragment.visibility = View.GONE
    }

    private fun observation() {
        sharedViewModel.textAppBar.observe(this) { newText ->
            Log.d("MainActivity", "Observed text: $newText")
            binding.appBarMain.mainAppBar.appBarTitle.text = newText
        }
    }

    private fun setupDrawerToggle(drawerLayout: DrawerLayout) {
        drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.appBarMain.mainAppBar.toolbar,
            R.string.open,
            R.string.close
        )

        // Disable the default toggle icon and set a custom one
        drawerToggle.isDrawerIndicatorEnabled = false
        binding.appBarMain.mainAppBar.toolbar.setNavigationIcon(R.drawable.app_logo_small)
        binding.appBarMain.mainAppBar.toolbar.setNavigationOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        onBackPress {
            this.onBackPressedDispatcher.addCallback(this) {
                binding.appBarMain.contentMainId.navHostFragmentContentMain.visibility = View.GONE
                //to do dev or client
                if (encryptedSharedPreference.userData.role.equals(DEVELOPER_KEY)
                    ||
                    encryptedSharedPreference.userData.role.equals(SKILLED_KEY)
                ) {
                    binding.appBarMain.contentMainId.homeBottomNavigation.visibility = View.VISIBLE
                } else {
                    binding.appBarMain.contentMainId.coordinator.visibility = View.VISIBLE
                }

                binding.appBarMain.contentMainId.homeBottomNavigationHostFragment.visibility =
                    View.VISIBLE
                return@addCallback
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
