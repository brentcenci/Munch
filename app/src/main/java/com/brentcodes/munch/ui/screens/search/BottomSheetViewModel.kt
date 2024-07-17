package com.brentcodes.munch.ui.screens.search

import androidx.lifecycle.ViewModel

class BottomSheetViewModel(currentSelected : Set<String> = setOf()) : ViewModel() {

    private var mySet = mutableSetOf<String>().apply { addAll(currentSelected) }

    fun updateSet(filter : String) {
        if (mySet.contains(filter)) {
            mySet.remove(filter)
        } else mySet.add(filter)
    }

    fun getSet() = mySet

}