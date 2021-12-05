package com.piu.solar_energy_usage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar

class MainWindowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_window)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }
    }

    fun onLogOutButtonClicked(item: MenuItem) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()   
    }

}