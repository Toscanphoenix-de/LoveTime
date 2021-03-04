package com.fenni.kotlintry


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.fenni.kotlintry.MainActivity.Companion.MEET_DATE
import java.util.*


class FirstStartActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {


    var year = 0;
    var month = 0
    var day = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_start)
        0
        pickDate()
    }

    private fun pickDate() {
        findViewById<Button>(R.id.btn_pickDate).setOnClickListener{
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

    @SuppressLint("ShowToast")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val saveYear = year
        val saveMonth = month +1

        val sharedPref = this.getSharedPreferences(MEET_DATE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putInt("year", saveYear)
        editor.putInt("month", saveMonth)
        editor.putInt("day", dayOfMonth)

        editor.apply()

        Toast.makeText(this,"Date successfully stored", Toast.LENGTH_SHORT).show()


        // Thread.sleep(2000)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }




}