package com.piu.solar_energy_usage.solar_panels

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.solar_panels.model.SolarPanel

class SolarPanelsActivity : AppCompatActivity(), ItemClickListener {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solar_panels)

        val btnBack = findViewById<Button>(R.id.btn_back_solar_panels)
        btnBack.setOnClickListener {
            finish()
        }

        val view = findViewById<RecyclerView>(R.id.panelsList)

        layoutManager = LinearLayoutManager(this)
        view.layoutManager = layoutManager

        adapter = SolarPanelViewAdapter()
        view.adapter = adapter

        val bottomSheetFragment = BottomSheetFragment()

        val btnAdd = findViewById<FloatingActionButton>(R.id.btn_solarPanelAdd)

        btnAdd.setOnClickListener {
            bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
        }


        val layoutBottom = findViewById<LinearLayout>(R.layout.layout_bottom_sheet)
//        val btnFinishAdd = findViewById<Button>(R.id.solarPanelFinishAdd)
//        btnFinishAdd.setOnClickListener(){
//            val newTitle = layoutBottom.findViewById<TextView>(R.id.panelBottomSheetTitle).text
//            val newDescription = layoutBottom.findViewById<TextView>(R.id.panelBottomSheetDescription).text
//
//            (adapter as SolarPanelViewAdapter).addNewPanel(SolarPanel((adapter as SolarPanelViewAdapter).itemCount+10,
//                newTitle as String,
//                newDescription as String,
//                "Some larger description about the band",
//                15,
//                0))
//
//            Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show()
//            bottomSheetFragment.dismiss()
//        }
    }

    override fun onItemClick(bundle: Bundle?) {
        val newTitle = bundle!!.getString("title")
        val newDescription = bundle!!.getString("description")

        (adapter as SolarPanelViewAdapter).addNewPanel(
            SolarPanel((adapter as SolarPanelViewAdapter).itemCount+10,
                newTitle as String,
                newDescription as String,
                "Some larger description about the band",
                15,
                0))

        Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show()
    }


}