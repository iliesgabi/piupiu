package com.piu.solar_energy_usage.solar_panels

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.solar_panels.model.SolarPanel

class SolarPanelViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemTitles = arrayOf("JA Solar", "LG", "Sunpower", "LG", "Panasonic")
    private val itemDescriptions = arrayOf(
        "Warranty Left: 11 years\nPower Produced: 60 kW",
        "Warranty Left: 20 years\nPower Produced: 423 kW",
        "Warranty Left: 21 years\nPower Produced: 57 kW",
        "Warranty Left: 25 years\nPower Produced: 5 kW",
        "Warranty Left: 25 years\nPower Produced: 3 kW"
    )
    private val ids = arrayOf(1, 2, 3, 4, 5)
    private val largeDescriptions = arrayOf(
        "JA Solar makes a range of solar pv panels for homes and commercial buildings, including smart solar panels and panels which can generate electricity from both sides (bifacial). It's based in Shanghai, China.\n",
        "LG might be best-known for its TVs, but it's now bringing its 50 years of electrical technology experience to solar panels. Its solar panel range includes high-efficiency panels, matt black panels and solar panels which generate electricity on both sides (bifacial). \n",
        "Sunpower has been making solar panels for 35 years and says its solar panels have high efficiency and are more reliable than conventional solar panels. Among the six popular brands listed on this page, it's one of only two to offer 25-year product and service warranties. \n",
        "LG might be best-known for its TVs, but it's now bringing its 50 years of electrical technology experience to solar panels. Its solar panel range includes high-efficiency panels, matt black panels and solar panels which generate electricity on both sides (bifacial). \n",
        "Panasonic's choice of solar panels includes smaller and slimmer panels for small or complicated roofs, matt-black panels and higher efficiency panels. It has been making solar pv panels since 1975, under the Sanyo brand until 2012\n"
    )

    private var solarPanels: MutableList<SolarPanel> = arrayListOf(
        SolarPanel(ids[0], itemTitles[0], itemDescriptions[0], largeDescriptions[0], 11, 60),
        SolarPanel(ids[1], itemTitles[1], itemDescriptions[1], largeDescriptions[1], 20, 423),
        SolarPanel(ids[2], itemTitles[2], itemDescriptions[2], largeDescriptions[2], 21, 57),
        SolarPanel(ids[3], itemTitles[3], itemDescriptions[3], largeDescriptions[3], 25, 5),
        SolarPanel(ids[4], itemTitles[4], itemDescriptions[4], largeDescriptions[4], 25, 3)
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var textTitle: TextView
        var textDes: TextView
        var deleteIcon: ImageView

        init {
            img = itemView.findViewById(R.id.panelImage)
            textTitle = itemView.findViewById(R.id.panelTitle)
            textDes = itemView.findViewById(R.id.panelDescription)
            deleteIcon = itemView.findViewById(R.id.deletePanel)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.panel_card, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder as ViewHolder
        holder.textTitle.text = solarPanels[position].title
        holder.textDes.text = solarPanels[position].description

        holder.deleteIcon.setOnClickListener {
            var alert = AlertDialog.Builder(it.context)
            alert.setTitle("Delete panel")
            alert.setMessage("Are you sure you want to delete?")
            alert.setPositiveButton(android.R.string.yes) { dialog, which ->
                solarPanels.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)
                notifyItemRangeChanged(holder.adapterPosition - 1, solarPanels.size + 1)
            }

            alert.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(
                    it.context,
                    "Canceled", Toast.LENGTH_SHORT
                ).show()
            }
            alert.show()
        }

        holder.itemView.setOnClickListener { v: View ->
            var intent = Intent(v.context, SolarPanelsDetails::class.java)
            intent.putExtra("solarPanelTitle", solarPanels[holder.adapterPosition].title)
            intent.putExtra(
                "solarPanelShortDescription",
                solarPanels[holder.adapterPosition].description
            )
            intent.putExtra(
                "solarPanelLargeDescription",
                solarPanels[holder.adapterPosition].largeDescriptions
            )
            v.context.startActivity(intent)
        }

    }

    fun addNewPanel(panel: SolarPanel) {
        solarPanels.add(panel)
        notifyItemRangeChanged(0, solarPanels.size + 1)
    }

    override fun getItemCount(): Int {
        return solarPanels.size
    }


}