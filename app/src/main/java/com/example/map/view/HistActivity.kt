package com.example.map.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.map.data.MarkerEntity
import com.example.map.databinding.ActivityHitsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistActivity : AppCompatActivity(), DialogListener {
    private lateinit var binding: ActivityHitsBinding
    private val viewModel: HistActivityViewModel by viewModel()
    private val adapter: AdapterList by lazy {
        AdapterList(
            onListItemClickListener,
            onButtonDeleteListener
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHitsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        binding.data.adapter = adapter

    }

    private fun setupObservers() {
        viewModel.getMarkers().observe(this, { it ->
            it?.let { markers ->
                if (!markers.isNullOrEmpty()) {
                    binding.data.visibility = View.VISIBLE
                    binding.tvNoData.visibility = View.GONE
                    adapter.updateData(markers)
                } else {
                    binding.data.visibility = View.GONE
                    binding.tvNoData.visibility = View.VISIBLE
                }
            }
        })
    }

    fun showDialog(data: MarkerEntity) {
        val newFragment = EditDialog.newInstance(data)
        newFragment.show(supportFragmentManager, "dialog")
    }

    private val onListItemClickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(data: MarkerEntity) {
                showDialog(data)
            }
        }
    private val onButtonDeleteListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(data: MarkerEntity) {
                viewModel.removeMarker(data)
            }
        }

    override fun onOkClick(data: MarkerEntity) {
        viewModel.updateMarker(data)
    }
}