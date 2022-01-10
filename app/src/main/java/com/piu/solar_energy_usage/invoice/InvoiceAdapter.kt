package com.piu.solar_energy_usage.invoice

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.piu.solar_energy_usage.R

class InvoiceAdapter(
    private val context: Context
) : RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder>() {

    private val dataSource = mutableListOf<Invoice>()
    private var design = 1

    inner class InvoiceViewHolder(
        itemView: View,
        design: Int
    ) : RecyclerView.ViewHolder(itemView) {

        private var invoiceNumber: TextView
        private var invoicePrice: TextView
        private var invoiceButton: Button

        init {
            invoiceNumber = itemView.findViewById(R.id.invoiceNumber)
            invoicePrice = itemView.findViewById(R.id.invoicePrice)
            invoiceButton = itemView.findViewById(R.id.invoiceButton)
        }

        fun bindData(item: Invoice) {
            val number = "Invoice no. " + item.number
            invoiceNumber.text = number
            val price = "Price: " + item.price + " RON"
            invoicePrice.text = price
            if (item.isPaid) {
                invoiceButton.text = "Paid"
                invoiceButton.setBackgroundColor(Color.parseColor("#519259"))
            } else {
                invoiceButton.text = "Pay"
                invoiceButton.setBackgroundColor(Color.parseColor("#A91818"))
            }
            invoiceButton.setOnClickListener {
                if (!item.isPaid) {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("please confirm")
                        .setMessage("are you sure?")
                        .setPositiveButton("pay") { _, _ ->
                            item.isPaid = !item.isPaid
                            val intent = Intent(context, PaymentActivity::class.java)
                            intent.putExtra("invoice", item)
                            (context as Activity).startActivityForResult(intent, 1002)
                        }
                        .setNegativeButton("cancel", null)
                        .create().show()
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int = R.layout.invoice_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val elementView = LayoutInflater.from(parent.context).inflate(
            viewType,
            parent,
            false
        )

        return InvoiceViewHolder(elementView, viewType)
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        val item = dataSource[position]
        holder.bindData(item)
        holder.itemView.setOnClickListener {
            println(item)
        }
    }

    override fun getItemCount(): Int = dataSource.size

    fun addWeather(invoice: Invoice) {
        dataSource.add(invoice)
        notifyDataSetChanged()
    }

    fun setDataSource(invoice: List<Invoice>) {
        dataSource.clear()
        println(invoice)
        dataSource.addAll(invoice)
        Util.dataSource = dataSource
        notifyDataSetChanged()
    }

    fun setPaid(invoice: Invoice) {
        for(inv in dataSource) {
            if (inv.number.equals(invoice.number)) {
                inv.isPaid = invoice.isPaid
            }
        }
        notifyDataSetChanged()
    }

    fun getDataSource(): List<Invoice> {
        return dataSource
    }

}