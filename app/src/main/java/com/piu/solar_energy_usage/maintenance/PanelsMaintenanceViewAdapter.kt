package com.piu.solar_energy_usage.maintenance

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.maintenance.model.PanelMaitenanceModel


class PanelsMaintenanceViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mTitle = arrayOf(
        " Are my solar panels generating as much energy as they should?",
        "How do I clean my solar panels?",
        "Can I use solar energy at night?",
        "How often you should service your solar system?",
        "How much a solar panel service costs?",
        "Who performs the service?"
    )
    private val mLinks = arrayOf(
        "https://www.eonenergy.com/solar-panels/maintenance-and-aftercare.html",
        "https://www.eonenergy.com/solar-panels/maintenance-and-aftercare.html",
        "https://www.eonenergy.com/solar-panels/maintenance-and-aftercare.html",
        "https://www.choice.com.au/home-improvement/energy-saving/solar/articles/solar-panel-maintenance",
        "https://www.choice.com.au/home-improvement/energy-saving/solar/articles/solar-panel-maintenance",
        "https://www.choice.com.au/home-improvement/energy-saving/solar/articles/solar-panel-maintenance"
    )
    private val ids = arrayOf(1, 2, 3, 4, 5, 6)
    private val mDescription = arrayOf(
        "If we’ve installed your solar panels recently, you’ll see how much energy you’re generating on our app. But other, older systems don’t give you that instant feedback, which means you’ll have to keep an eye on your generation meter instead – it should be by your fuse board.\n" + "\n" + "Exactly how much electricity your solar panels generate depends on how many panels you have, the direction they’re facing and whether they’re shaded by trees or buildings",
        "You shouldn’t need to. They’re designed to be self-cleaning. And anyone who tells you otherwise is selling you something you might not need.\n" + "If your solar panels really need cleaning, you’ll probably be able to see what’s causing the problem – like (sorry about this) lots of bird droppings or fallen leaves – which could make the panels less efficient. If so, there are some specialist cleaners that can help. If you get in touch with them directly, they’ll quote for the cost of cleaning your solar panels.",
        "Yes, but only if your system has a battery. Batteries store up the energy you generate during the day, meaning you can keep using it at night (and keep more of that energy for yourself rather than sending it back to the grid).\n" + "\n" + "People often ask us how to look after their batteries. Like the rest of our solar systems, your batteries more or less look after themselves. Of course, they also come with the latest warranties and guarantees if anything does go wrong.  Our solar panels (and the people installing them) are certified by MCS, which means they always work to the industry standard.",
        "The Clean Energy Council (CEC) recommends setting up an annual maintenance schedule to ensure your system is safe and functioning efficiently. But annual servicing can get expensive, and some experts think this is probably overkill.",
        "A standard service by an accredited solar installer costs roughly \$200 to \$300 depending on the size of the system and the individual provider. If repairs are required, the costs will vary, but many repairs may be covered by your warranty.",
        "A solar panel system is a complex and potentially dangerous piece of technology and it's recommended that any service or maintenance should only be performed by a licensed electrician or a Clean Energy Council-accredited solar panel system installer.  \n" + "\n" + "Barnes says if you're looking for somebody to perform the service, the simplest option may be to use the same company that installed your system if you're happy with their work."
    )

    private var maintenaceSuggestions: MutableList<PanelMaitenanceModel> = arrayListOf(
        PanelMaitenanceModel(ids[0], mTitle[0], mDescription[0], mLinks[0]),
        PanelMaitenanceModel(ids[1], mTitle[1], mDescription[1], mLinks[1]),
        PanelMaitenanceModel(ids[2], mTitle[2], mDescription[2], mLinks[2]),
        PanelMaitenanceModel(ids[3], mTitle[3], mDescription[3], mLinks[3]),
        PanelMaitenanceModel(ids[4], mTitle[4], mDescription[4], mLinks[4]),
        PanelMaitenanceModel(ids[5], mTitle[5], mDescription[5], mLinks[5])
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var textTitle: TextView

        init {
            img = itemView.findViewById(R.id.maintenanceImage)
            textTitle = itemView.findViewById(R.id.maintenanceTitle)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.maintenance_card, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder as ViewHolder
        holder.textTitle.text = maintenaceSuggestions[position].title

        holder.itemView.setOnClickListener { v: View ->
            var intent = Intent(v.context, PanelsMaintenanceDetails::class.java)
            intent.putExtra("maintenanceTitle", maintenaceSuggestions[holder.adapterPosition].title)
            intent.putExtra(
                "maintenanceDescription",
                maintenaceSuggestions[holder.adapterPosition].description
            )
            intent.putExtra("maintenanceLink", maintenaceSuggestions[holder.adapterPosition].link)
            v.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return maintenaceSuggestions.size
    }
}