package com.example.map.view

import com.example.map.data.MarkerEntity

interface DialogListener {
    fun onOkClick(data: MarkerEntity)
}