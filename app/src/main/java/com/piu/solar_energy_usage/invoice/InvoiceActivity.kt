package com.piu.solar_energy_usage.invoice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.piu.solar_energy_usage.R

class InvoiceActivity : AppCompatActivity() {

    private lateinit var invoiceAdapter: InvoiceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)

        val btnBack = findViewById<Button>(R.id.btn_back_invoice)
        btnBack.setOnClickListener {
            finish()
        }

        val invoiceRecyclerView = findViewById<RecyclerView>(R.id.invoicesRecyclerView)

        invoiceAdapter = InvoiceAdapter(context = this)

        invoiceRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = invoiceAdapter
        }

        if (Util.first == 0) {
            Util.dataSource = Invoice.getInvoicesFromFile("invoices.json", this)
            Util.first = 1
            invoiceAdapter.setDataSource(Util.dataSource)
        } else {
            if (intent.getSerializableExtra("invoice") != null) {
                val invoice = intent.getSerializableExtra("invoice") as Invoice
                val builder = AlertDialog.Builder(this)
                builder.setTitle("you paid invoice no. " + invoice.number)
                    .setNegativeButton("close", null)
                    .create().show()
                invoiceAdapter.setDataSource(Util.dataSource)
            } else {
                invoiceAdapter.setDataSource(Util.dataSource)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data?.getSerializableExtra("invoice") != null) {
            val invoice = data?.getSerializableExtra("invoice") as Invoice

            val builder = AlertDialog.Builder(this)
            builder.setTitle("you paid invoice no. " + invoice.number)
                .setNegativeButton("close", null)
                .create().show()

            invoiceAdapter.setPaid(invoice)
        } else {
            invoiceAdapter.setDataSource(Util.dataSource)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}