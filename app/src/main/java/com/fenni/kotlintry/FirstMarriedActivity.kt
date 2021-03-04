package com.fenni.kotlintry

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.Toast
import com.fenni.kotlintry.MainActivity.Companion.DAY
import com.fenni.kotlintry.MainActivity.Companion.MARRIED_DATE
import com.fenni.kotlintry.MainActivity.Companion.MONTH
import com.fenni.kotlintry.MainActivity.Companion.YEAR
import java.util.*

class FirstMarriedActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener{

    var year = 0
    var month = 0
    var day = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_married)

        pickDate()
    }

    private fun pickDate() {
        findViewById<Button>(R.id.btn_pickMarriageDate).setOnClickListener {
            getDateCalender()
            DatePickerDialog(this,this,year,month,day).show()
        }
    }

    private fun getDateCalender() {
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val saveYear = year
        val saveMonth = month + 1

        val sharedPreferences = this.getSharedPreferences(MARRIED_DATE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt(YEAR,saveYear)
        editor.putInt(MONTH,saveMonth)
        editor.putInt(DAY,dayOfMonth)

        editor.apply()

        Toast.makeText(this,"Date successfully applied", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, MainMarriedActivity::class.java )
        startActivity(intent)
        finish()
    }
}