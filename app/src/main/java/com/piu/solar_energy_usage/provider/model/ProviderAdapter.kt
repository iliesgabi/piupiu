package com.piu.solar_energy_usage.provider.model

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.piu.solar_energy_usage.databinding.ProviderFragmentBinding
import com.piu.solar_energy_usage.provider.ProviderDetailActivity
import com.piu.solar_energy_usage.provider.Util

object Detail {
    const val NAME = "name"
    const val CONTACT = "contact"
    const val DESCRIPTION = "description"
}

class ProviderAdapter(private val providers: List<Provider>) :
    ListAdapter<Provider, ProviderAdapter.ProviderViewHolder>(ProviderDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderViewHolder {
        val binding =
            ProviderFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProviderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProviderViewHolder, position: Int) {
        val item = providers[position]

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ProviderDetailActivity::class.java)

            intent.putExtra(Detail.NAME, item.name)
            intent.putExtra(Detail.CONTACT, item.contact)
            intent.putExtra(Detail.DESCRIPTION, item.description)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            it.context.startActivity(intent)
        }

        holder.bind(item)
    }

    override fun getItemCount(): Int = providers.size

    inner class ProviderViewHolder(
        private val binding: ProviderFragmentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Provider) {
            binding.apply {

                tvProviderName.text = item.name
                ivProvider.setBackgroundResource(Util.getProviderIcon(item.name))
                tvProviderContact.text = item.contact
                tvRating.text = item.rating.toString()

            }
        }
    }

    object ProviderDiffCallback : DiffUtil.ItemCallback<Provider>() {
        override fun areItemsTheSame(oldItem: Provider, newItem: Provider): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Provider, newItem: Provider): Boolean =
            oldItem == newItem
    }
}