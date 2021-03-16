package com.fenni.lovetime

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import androidx.annotation.RequiresApi
import com.fenni.lovetime.MainActivity.Companion.DAY
import com.fenni.lovetime.MainActivity.Companion.MONTH
import com.fenni.lovetime.MainActivity.Companion.YEAR
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import kotlin.math.abs

class DateCalculation(context: Context) : AppCompatActivity() {

    /*@RequiresApi(Build.VERSION_CODES.O)
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
*/

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDaysSince(context: Context, string: String): Int {
        val sharedPreferences = SharedPreferences(context, string)

        val savedYear = sharedPreferences.getValueInt(YEAR)
        val savedMonth = sharedPreferences.getValueInt(MONTH)
        val savedDay = sharedPreferences.getValueInt(DAY)

        if (sharedPreferences.getValueInt(YEAR) == -1) {
            val date1 = "$savedDay-$savedMonth-$savedYear"
            val date2 = LocalDate.now().toString()
            val dates = SimpleDateFormat("dd-MM-yyyy")
            val finalDate = dates.parse(date1)
            val finalDate2 = dates.parse(date2)

            val difference: Long = abs(finalDate.time - finalDate2.time)

            val days = difference / (24 * 60 * 60 * 1000)

            return (days.toInt())
        }else{
            return 0
        }




    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onDateSetText(period: Period?, mMainView: View, amount_days: Int, amount_month: Int,amount_years: Int,mainOut: Int) {
        if (period != null) {



            val days = period.days
            val months = period.months
            val years = period.years

            val textDay = mMainView.findViewById<TextView>(amount_days)
            val textMonth = mMainView.findViewById<TextView>(amount_month)
            val textYear = mMainView.findViewById<TextView>(amount_years)

            val output = mMainView.findViewById<TextView>(mainOut)

            textDay.text = days.toString()
            textMonth.text = months.toString()
            textYear.text = years.toString()


            //output
            if (years > 0) {

                if (years > 1 && months > 1)
                    output.text =
                        getString(R.string.been_together_years_months, years, months)
                else if (years >= 1 && months == 1)
                    output.text =
                        getString(R.string.been_together_years_month, years, months)
                else if (years > 1 && months == 0 && days > 1)
                    output.text =
                        getString(R.string.been_together_years_days, years, days)
                else if (years > 1 && months == 0 && days == 1)
                    output.text =
                        getString(R.string.been_together_years_day, years, days)
                else if (years > 1 && months == 0 && days == 0)
                    output.text =
                        getString(R.string.been_together_years, years)
                else if (years == 1 && months > 1)
                    output.text =
                        getString(R.string.been_together_year_months, years, months)
                else if (years == 1 && months == 1)
                    output.text =
                        getString(R.string.been_together_year_month, years, months)
                else if (years == 1 && months == 0 && days > 1)
                    output.text =
                        getString(R.string.been_together_year_days, years, days)
                else if (years == 1 && months == 0 && days == 1)
                    output.text =
                        getString(R.string.been_together_year_day, years, days)
                else if (years == 1 && months == 0 && days == 0)
                    output.text =
                        getString(R.string.been_together_year, years)
            }

            if (months > 0 && years == 0) {
                if (months > 1 && days > 1)
                    output.text =
                        getString(R.string.been_together_months_days, months, days)
                else if (years > 1 && days == 1)
                    output.text =
                        getString(R.string.been_together_months_day, months, days)
                else if (months > 1 && days == 0)
                    output.text =
                        getString(R.string.been_together_months, months)
                else if (months == 1 && days > 1)
                    output.text =
                        getString(R.string.been_together_month_days, months, days)
                else if (months == 1 && days == 1)
                    output.text =
                        getString(R.string.been_together_month_day, months, days)
                else if (months == 1 && days == 0)
                    output.text =
                        getString(R.string.been_together_month, months)
            }

            if (days > 0 && months == 0 && years == 0) {
                if (days > 1) {
                    output.text =
                        getString(R.string.been_together_days, days)
                }
                if (days == 1) {
                    output.text =
                        getString(R.string.been_together_day, days)

                }

            }


        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun dateCalc(string_ourDate: String, date2: LocalDate): Period? {

        println(string_ourDate)
        val date = LocalDate.parse(string_ourDate)


        return Period.between(date, date2)
    }


}