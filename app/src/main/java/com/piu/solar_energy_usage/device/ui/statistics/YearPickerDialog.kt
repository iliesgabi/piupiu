package com.piu.solar_energy_usage.device.ui.statistics

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.piu.solar_energy_usage.R
import java.util.*

class YearPickerDialog : DialogFragment() {

    private lateinit var listener: DatePickerDialog.OnDateSetListener

    fun setListener(listener: DatePickerDialog.OnDateSetListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.year_picker, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        val view = requireActivity().layoutInflater.inflate(R.layout.year_picker, null)
        val yearPicker = view.findViewById<NumberPicker>(R.id.yearPicker)

        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        yearPicker.minValue = year - 5
        yearPicker.maxValue = year
        yearPicker.value = year

        builder.setView(view)
            .setPositiveButton("OK") { _, _ ->
                listener.onDateSet(null, yearPicker.value, 0, 1)
            }
            .setNegativeButton("CANCEL") { _, _ ->
                this.dialog?.cancel()
            }

        return builder.create()
    }
}