package com.fenni.kotlintry

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import androidx.annotation.RequiresApi
import com.fenni.kotlintry.MainActivity.Companion.DAY
import com.fenni.kotlintry.MainActivity.Companion.MONTH
import com.fenni.kotlintry.MainActivity.Companion.YEAR
import java.time.LocalDate
import java.time.Period

class DateCalculation(context: Context) : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateCheckAndSet(string_Prefs : String, banner : Int, amount_days : Int, amount_month:Int, amount_years : Int, mainOut: Int) {

        val sharedPreferences = SharedPreferences(this,string_Prefs)

        val savedYear = sharedPreferences.getValueInt(YEAR)
        val savedMonth = sharedPreferences.getValueInt(MONTH)
        val savedDay = sharedPreferences.getValueInt(DAY)


        findViewById<TextView>(banner).text = "$savedDay.$savedMonth.$savedYear"

        val dateToday = LocalDate.now()
        when {
            savedMonth < 10 -> {
                if (savedDay < 10) {
                    val dateString = "$savedYear-0$savedMonth-0$savedDay"
                    onDateSetText(dateCalc(dateString, dateToday),amount_days,amount_month,amount_years,mainOut)
                } else {
                    val dateString = "$savedYear-0$savedMonth-$savedDay"
                    onDateSetText(dateCalc(dateString, dateToday),amount_days,amount_month,amount_years,mainOut)
                }
            }
            savedDay < 10 -> {
                val dateString = "$savedYear-$savedMonth-0$savedDay"
                onDateSetText(dateCalc(dateString, dateToday),amount_days,amount_month,amount_years,mainOut)
            }
            else -> {
                val dateString = "$savedYear-$savedMonth-$savedDay"
                onDateSetText(dateCalc(dateString, dateToday),amount_days,amount_month,amount_years,mainOut)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun onDateSetText(period: Period?, amount_days : Int, amount_month:Int, amount_years : Int, mainOut :Int) {
        if (period != null) {

            val days = period.days
            val months = period.months
            val years = period.years


            findViewById<TextView>(amount_days).text = days.toString()
            findViewById<TextView>(amount_month).text = months.toString()
            findViewById<TextView>(amount_years).text = years.toString()


            //output
            if (years > 0) {

                if (years > 1 && months > 1)
                    findViewById<TextView>(mainOut).text =
                        getString(R.string.been_together_years_months, years, months)
                else if (years >= 1 && months == 1)
                    findViewById<TextView>(mainOut).text =
                        getString(R.string.been_together_years_month, years, months)
                else if (years > 1 && months == 0 && days > 1)
                    findViewById<TextView>(mainOut).text =
                        getString(R.string.been_together_years_days, years, days)
                else if (years > 1 && months == 0 && days == 1)
                    findViewById<TextView>(mainOut).text =
                        getString(R.string.been_together_years_day, years, days)
                else if (years > 1 && months == 0 && days == 0)
                    findViewById<TextView>(mainOut).text =
                        getString(R.string.been_together_years, years)
                else if (years == 1 && months > 1)
                    findViewById<TextView>(mainOut).text =
                        getString(R.string.been_together_year_months, years, months)
                else if (years == 1 && months == 1)
                    findViewById<TextView>(mainOut).text =
                        getString(R.string.been_together_year_month, years, months)
                else if (years == 1 && months == 0 && days > 1)
                    findViewById<TextView>(mainOut).text =
                        getString(R.string.been_together_year_days, years, days)
                else if (years == 1 && months == 0 && days == 1)
                    findViewById<TextView>(mainOut).text =
                        getString(R.string.been_together_year_day, years, days)
                else if (years == 1 && months == 0 && days == 0)
                    findViewById<TextView>(mainOut).text =
                        getString(R.string.been_together_year, years)
            }

            if (months > 0 && years == 0) {
                if (months > 1 && days > 1)
                    findViewById<TextView>(mainOut).text =
                        getString(R.string.been_together_months_days, months, days)
                else if (years > 1 && days == 1)
                    findViewById<TextView>(mainOut).text =
                        getString(R.string.been_together_months_day, months, days)
                else if (months > 1 && days == 0)
                    findViewById<TextView>(mainOut).text =
                        getString(R.string.been_together_months, months)
                else if (months == 1 && days > 1)
                    findViewById<TextView>(mainOut).text =
                        getString(R.string.been_together_month_days, months, days)
                else if (months == 1 && days == 1)
                    findViewById<TextView>(mainOut).text =
                        getString(R.string.been_together_month_day, months, days)
                else if (months == 1 && days == 0)
                    findViewById<TextView>(mainOut).text =
                        getString(R.string.been_together_month, months)
            }

            if (days > 0 && months == 0 && years == 0) {
                if (days > 1) {
                    findViewById<TextView>(mainOut).text =
                        getString(R.string.been_together_days, days)
                }
                if (days == 1) {
                    findViewById<TextView>(mainOut).text =
                        getString(R.string.been_together_day, days)

                }

            }


        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun dateCalc(string_ourDate: String, date2: LocalDate): Period? {

        println(string_ourDate)
        val date = LocalDate.parse(string_ourDate)


        return Period.between(date, date2)
    }


}