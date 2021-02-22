package com.fenni.kotlintry


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.fenni.kotlintry.FirstStartActivity.DbConstants.key_day
import com.fenni.kotlintry.FirstStartActivity.DbConstants.key_month
import com.fenni.kotlintry.FirstStartActivity.DbConstants.key_year
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class FirstStartActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    object DbConstants{
        const val key_year = "year"
        const val key_month = "month"
        const val key_day = "day"
    }

    var year = 0;
    var month = 0
    var day = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_start)

        loadData()
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
        var saveYear = year
        var saveMonth = month +1
        var saveDay = dayOfMonth

        var dateString = "$saveYear-$saveMonth-$saveDay"

        val ref = FirebaseDatabase.getInstance().getReference("Dates")

        ref.child("beenTogether").setValue(dateString).addOnCompleteListener {
            Toast.makeText(applicationContext, "Date saved successfully",Toast.LENGTH_SHORT)
        }

        loadData()


    }

    private fun loadData(){



    }


}