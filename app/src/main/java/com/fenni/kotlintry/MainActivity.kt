package com.fenni.kotlintry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var toolbar: Toolbar = findViewById(R.id.header)
        setSupportActionBar(toolbar);
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        var inflator: MenuInflater = menuInflater
        inflator.inflate(R.menu.main_menu,menu)

        return true
    }
}