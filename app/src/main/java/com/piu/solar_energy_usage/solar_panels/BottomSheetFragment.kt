package com.piu.solar_energy_usage.solar_panels

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.piu.solar_energy_usage.R

class BottomSheetFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private var mListener: ItemClickListener? = null
    private var title: String? = null
    private var description: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<Button>(R.id.solarPanelFinishAdd).setOnClickListener(this)
        title = view.findViewById<TextView>(R.id.panelBottomSheetTitle).text as String
        description = view.findViewById<TextView>(R.id.panelBottomSheetDescription).text as String
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is ItemClickListener) {
            context
        } else {
            throw RuntimeException(
                context.toString() + "MUST IMPLEMENT ITEMCLICK"
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onClick(v: View?) {
        var bundle = Bundle()
        bundle.putString("title", title)
        bundle.putString("description", description)
        mListener!!.onItemClick(bundle)
        dismiss()
    }

}