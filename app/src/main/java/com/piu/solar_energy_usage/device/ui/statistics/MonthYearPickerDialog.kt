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
import java.text.DateFormatSymbols
import java.util.*

class MonthYearPickerDialog : DialogFragment() {

    private lateinit var listener: DatePickerDialog.OnDateSetListener

    fun setListener(listener: DatePickerDialog.OnDateSetListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.month_picker, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        val view = requireActivity().layoutInflater.inflate(R.layout.month_picker, null)
        val monthPicker = view.findViewById<NumberPicker>(R.id.monthPicker)
        val yearPicker = view.findViewById<NumberPicker>(R.id.mYearPicker)

        val calendar = Calendar.getInstance()

        monthPicker.minValue = 0
        monthPicker.maxValue = 11
        monthPicker.value = calendar.get(Calendar.MONTH)
        monthPicker.displayedValues = DateFormatSymbols().months

        val year = calendar.get(Calendar.YEAR)
        yearPicker.minValue = year - 5
        yearPicker.maxValue = year
        yearPicker.value = year

        builder.setView(view)
            .setPositiveButton("OK") { _, _ ->
                listener.onDateSet(null, yearPicker.value, monthPicker.value, 1)
            }
            .setNegativeButton("CANCEL") { _, _ ->
                this.dialog?.cancel()
            }

        return builder.create()
    }
}