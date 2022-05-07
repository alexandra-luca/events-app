package com.example.eventsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.eventsapp.ui.dashboard.DashboardViewModel
import com.example.eventsapp.ui.home.HomeViewModel
import com.example.eventsapp.ui.notifications.NotificationsViewModel
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {
    @get:Rule
    public var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun MainActivity_Loads() {
        MainActivity();
    }

    @Test
    fun HomeViewModel_Loads() {
        HomeViewModel();
    }

    @Test
    fun DashboardViewModel_Loads() {
        DashboardViewModel();
    }

    @Test
    fun NotificationsViewModel_Loads() {
        NotificationsViewModel();
    }
}