package com.fenni.kotlintry

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import java.time.LocalDate
import java.time.Period
import java.util.*

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    var day = 0
    var month = 0
    var year = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var toolbar: Toolbar = findViewById(R.id.header)
        setSupportActionBar(toolbar);

        pickDate()




    }



    /** Date Picker **/

    private fun pickDate() {
        findViewById<Button>(R.id.datePicker).setOnClickListener{
            getDateCalender()

            DatePickerDialog(this, this,year,month,day).show()
        }
    }

    private fun getDateCalender(){
        val calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month= calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month+1
        savedYear = year

        getDateCalender()

        findViewById<TextView>(R.id.DateBanner).text = getString(R.string.date, savedDay, savedMonth, savedYear)


        val dateToday = LocalDate.now()
        when {
            savedMonth <10 -> {
                if (savedDay < 10){
                    val dateString = "$savedYear-0$savedMonth-0$savedDay"
                    onDateSetText(dateCalc(dateString,dateToday))
                }else{
                    val dateString = "$savedYear-0$savedMonth-$savedDay"
                    onDateSetText(dateCalc(dateString,dateToday))
                }
            }
            savedDay < 10 -> {
                val dateString = "$savedYear-$savedMonth-0$savedDay"
                onDateSetText(dateCalc(dateString,dateToday))
            }
            else -> {
                val dateString = "$savedYear-$savedMonth-$savedDay"
                onDateSetText(dateCalc(dateString,dateToday))
            }
        }




    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onDateSetText(period: Period?){
        if (period != null) {

            var days = period.days
            var months = period.months
            var years = period.years

            println(days)
            println(months)
            println(years)

            findViewById<TextView>(R.id.amount_days).text = days.toString()
            findViewById<TextView>(R.id.amount_month).text = months.toString()
            findViewById<TextView>(R.id.amount_years).text = years.toString()



                if (years > 0) {

                     if (years > 1 && months > 1)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_years_months,years,months)
                    else if (years >= 1 && months == 1)
                        findViewById<TextView>(R.id.mainOut).text =getString(R.string.been_together_years_month,years,months)
                    else if (years > 1 && month == 0 && days > 1)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_years_days,years, days)
                    else if (years > 1 && month == 0 && days == 1)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_years_day,years, days)
                    else if (years > 1 && month == 0 && days == 0)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_years,years)

                    else if (years == 1 && months > 1)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_year_months,years,months)
                    else if (years == 1 && months == 1)
                        findViewById<TextView>(R.id.mainOut).text =getString(R.string.been_together_year_month,years,months)
                    else if (years == 1 && month == 0 && days > 1)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_year_days,years, days)
                    else if (years == 1 && month == 0 && days == 1)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_year_day ,years, days)
                    else if (years == 1 && month == 0 && days == 0)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_year, years)
                }

                if (months > 0 && years == 0) {
                    if (months > 1 && days > 1)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_months_days,months,days)
                    else if (years > 1 && days == 1)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_months_day,months,days)
                    else if (months > 1 && days == 0)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_months,months)
                    else if (months == 1 && days > 1)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_month_days,months,days)
                    else if (months == 1 && days == 1)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_month_day,months,days)
                    else if (months == 1 && days == 0)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_month,months)
                }

                if (days > 0 && months == 0 && years == 0) {
                    if (days > 1) {
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_days, days)
                    }
                    if (days == 1) {
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_day, days)

                    }

                }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        var inflater = menuInflater
        inflater.inflate(R.menu.main_menu,menu)
        return true
    }
}

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dateCalc(string_ourDate: String, date2: LocalDate): Period? {

        println(string_ourDate)
        val date = LocalDate.parse(string_ourDate)


        return Period.between(date, date2)
    }






