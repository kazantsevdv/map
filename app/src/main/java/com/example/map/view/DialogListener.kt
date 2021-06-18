package com.example.map.view

import com.example.map.model.Marker

interface DialogListener {
    fun onOkClick(data: Marker)
}