package com.example.map.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.map.data.MarkerEntity
import com.example.map.databinding.DialogEditBinding

class EditDialog : DialogFragment() {
    private var _viewBinding: DialogEditBinding? = null
    private val binding get() = checkNotNull(_viewBinding)

    private val data: MarkerEntity? by lazy { arguments?.getParcelable(ARG_PARAM1) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = DialogEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setBtnListeners()

    }

    private fun setupUI() {

        data?.let {
            binding.tvLatitude.text = String.format("%.5f", it.latitude)
            binding.tvLongitude.text = String.format("%.5f", it.latitude)
            binding.tvTitle.setText(it.title)
            binding.tvAnnotation.setText(it.info)
        }
    }

    fun setBtnListeners() {

        binding.btOk.setOnClickListener {
            saveData()
            dismiss()
        }

        binding.btCancel.setOnClickListener { dismiss() }
    }

    private fun saveData() {
        data?.let { data ->
            (activity as? DialogListener)?.onOkClick(
                MarkerEntity(
                    data.latitude,
                    data.longitude,
                    binding.tvTitle.text.toString(),
                    binding.tvAnnotation.text.toString()
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

    companion object {
        const val ARG_PARAM1 = "ARG_PARAM1"

        @JvmStatic
        fun newInstance(data: MarkerEntity) =
            EditDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, data)
                }
            }
    }


}