package com.example.wonderland.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainTabsViewModel: ViewModel() {
    private var _tabsState = mutableStateOf(0)
    val tabsState = _tabsState

    init{
        _tabsState.value = 0
    }

    fun setTabState(state: Int) {
        _tabsState.value = state
    }
}