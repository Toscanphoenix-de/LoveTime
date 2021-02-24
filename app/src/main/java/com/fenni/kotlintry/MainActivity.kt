package com.fenni.kotlintry

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import java.lang.NullPointerException
import java.time.LocalDate
import java.time.Period
import java.util.*

class MainActivity : AppCompatActivity(){



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("meetDate", Context.MODE_PRIVATE)
        val toolbar: Toolbar? = findViewById(R.id.header)
        setSupportActionBar(toolbar)



        if (sharedPreferences.getInt("year", -1) == -1) {
            val intent = Intent(this, FirstStartActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            setContentView(R.layout.activity_main)
        }


        dateCheckAndSet()
        pickImage()
    }

    // image Picker
    private fun pickImage() {
        findViewById<Button>(R.id.img_picker).setOnClickListener{
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    //denied
                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE)
                }
                else{
                    pickImageFromGallery()
                }

            }
            else{
                pickImageFromGallery()
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }
                else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            findViewById<ImageView>(R.id.photo).setImageURI(data?.data)
        }
    }

    companion object {
        private val IMAGE_PICK_CODE = 100
        private val PERMISSION_CODE = 100

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dateCheckAndSet() {
        val sharedPref = this.getSharedPreferences("meetDate", Context.MODE_PRIVATE)

        val savedYear = sharedPref.getInt("year",0)
        val savedMonth = sharedPref.getInt("month",0)
        val savedDay = sharedPref.getInt("day",0)

        findViewById<TextView>(R.id.dateBanner).text="$savedDay.$savedMonth.$savedYear"

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

            val days = period.days
            val months = period.months
            val years = period.years

            /*println(days)
            println(months)
            println(years)*/

            findViewById<TextView>(R.id.amount_days).text = days.toString()
            findViewById<TextView>(R.id.amount_month).text = months.toString()
            findViewById<TextView>(R.id.amount_years).text = years.toString()



            // Ausgabe entsprechend der menge an einehiten mit oder ohne s
                if (years > 0) {

                     if (years > 1 && months > 1)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_years_months,years,months)
                    else if (years >= 1 && months == 1)
                        findViewById<TextView>(R.id.mainOut).text =getString(R.string.been_together_years_month,years,months)
                    else if (years > 1 && months == 0 && days > 1)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_years_days,years, days)
                    else if (years > 1 && months == 0 && days == 1)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_years_day,years, days)
                    else if (years > 1 && months == 0 && days == 0)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_years,years)

                    else if (years == 1 && months > 1)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_year_months,years,months)
                    else if (years == 1 && months == 1)
                        findViewById<TextView>(R.id.mainOut).text =getString(R.string.been_together_year_month,years,months)
                    else if (years == 1 && months == 0 && days > 1)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_year_days,years, days)
                    else if (years == 1 && months == 0 && days == 1)
                        findViewById<TextView>(R.id.mainOut).text = getString(R.string.been_together_year_day ,years, days)
                    else if (years == 1 && months == 0 && days == 0)
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


    //Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu,menu)
        return true
    }
}

    //Calculator
    @RequiresApi(Build.VERSION_CODES.O)
    private fun dateCalc(string_ourDate: String, date2: LocalDate): Period? {

        println(string_ourDate)
        val date = LocalDate.parse(string_ourDate)


        return Period.between(date, date2)
    }






