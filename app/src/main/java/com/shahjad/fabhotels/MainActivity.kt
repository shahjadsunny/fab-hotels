package com.shahjad.fabhotels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.shahjad.fabhotels.data.local.AppSharedPreference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var appSharedPreference: AppSharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment)
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.main_activity_nav_graph)
        if (appSharedPreference.getUserToken()!="")
            graph.setStartDestination(R.id.newsFragment)
        else
            graph.setStartDestination(R.id.loginFragment)
        val bundle = Bundle()
        navHostFragment.navController.graph = graph

    }
}