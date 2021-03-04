package com.fenni.kotlintry

/**
 * @author : Toscanhpoenix
 * @version: 1.0
 * @since: 2021-03-03*/

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.GestureDetector
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.fenni.kotlintry.MainActivity.Companion.DAY
import com.fenni.kotlintry.MainActivity.Companion.ENGAGEMENT_DATE
import com.fenni.kotlintry.MainActivity.Companion.MONTH
import com.fenni.kotlintry.MainActivity.Companion.NAME
import com.fenni.kotlintry.MainActivity.Companion.NAMES
import com.fenni.kotlintry.MainActivity.Companion.YEAR
import com.google.android.gms.dynamic.IFragmentWrapper
import java.time.LocalDate
import java.time.Period
import kotlin.math.abs

class MainEngagedActivity : AppCompatActivity(),GestureDetector.OnGestureListener {

    lateinit var gestureDetector:GestureDetector
    var x2 = 0.0f
    var x1 = 0.0f



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences(ENGAGEMENT_DATE, Context.MODE_PRIVATE)

        if (sharedPreferences.getInt(YEAR,-1)==-1){
            val intent = Intent(this, FirstEngagementActivity::class.java)
            startActivity(intent)
            finish()
        }else {
            setContentView(R.layout.activity_main_engaged)
            val toolbar: Toolbar? = findViewById(R.id.header)
            setSupportActionBar(toolbar)
        }

        dateCheckAndSet()
        namesCheckAndSet()

        gestureDetector = GestureDetector(this,this)


    }


    //----------------------------------------------------------------------------------------------Name
    private fun namesCheckAndSet() {
        val sharedPreferences = this.getSharedPreferences(NAMES, Context.MODE_PRIVATE)

        val name =
            sharedPreferences.getString(NAME, "Here could be your name. Change it in settings")

        findViewById<TextView>(R.id.bannerName).text = name

    }

    //----------------------------------------------------------------------------------------------Option
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.help -> {
                val intent = Intent(this, HelpActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //----------------------------------------------------------------------------------------------Date
    @RequiresApi(Build.VERSION_CODES.O)
    private fun dateCheckAndSet() {
        val sharedPref = this.getSharedPreferences(ENGAGEMENT_DATE, Context.MODE_PRIVATE)

        val savedYear = sharedPref.getInt(YEAR, 0)
        val savedMonth = sharedPref.getInt(MONTH, 0)
        val savedDay = sharedPref.getInt(DAY, 0)

        if (sharedPref.getInt(YEAR,-1) == -1){
            findViewById<TextView>(R.id.dateBannerEngaged).text= "Oh-Oh Something went wrong"
        }else
        findViewById<TextView>(R.id.dateBannerEngaged).text = "$savedDay.$savedMonth.$savedYear"


        val dateToday = LocalDate.now()
        when {
            savedMonth < 10 -> {
                if (savedDay < 10) {
                    val dateString = "$savedYear-0$savedMonth-0$savedDay"
                    onDateSetText(dateCalc(dateString, dateToday))
                } else {
                    val dateString = "$savedYear-0$savedMonth-$savedDay"
                    onDateSetText(dateCalc(dateString, dateToday))
                }
            }
            savedDay < 10 -> {
                val dateString = "$savedYear-$savedMonth-0$savedDay"
                onDateSetText(dateCalc(dateString, dateToday))
            }
            else -> {
                val dateString = "$savedYear-$savedMonth-$savedDay"
                onDateSetText(dateCalc(dateString, dateToday))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateCalc(string_ourDate: String, date2: LocalDate): Period? {

        println(string_ourDate)
        val date = LocalDate.parse(string_ourDate)


        return Period.between(date, date2)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onDateSetText(period: Period?) {
        if (period != null) {

            val days = period.days
            val months = period.months
            val years = period.years


            findViewById<TextView>(R.id.amount_days_engagement).text = days.toString()
            findViewById<TextView>(R.id.amount_month_engagement).text = months.toString()
            findViewById<TextView>(R.id.amount_years_engagement).text = years.toString()


            //output
            if (years > 0) {

                if (years > 1 && months > 1)
                    findViewById<TextView>(R.id.mainOutEngagement).text =
                        getString(R.string.been_together_years_months, years, months)
                else if (years >= 1 && months == 1)
                    findViewById<TextView>(R.id.mainOutEngagement).text =
                        getString(R.string.been_together_years_month, years, months)
                else if (years > 1 && months == 0 && days > 1)
                    findViewById<TextView>(R.id.mainOutEngagement).text =
                        getString(R.string.been_together_years_days, years, days)
                else if (years > 1 && months == 0 && days == 1)
                    findViewById<TextView>(R.id.mainOutEngagement).text =
                        getString(R.string.been_together_years_day, years, days)
                else if (years > 1 && months == 0 && days == 0)
                    findViewById<TextView>(R.id.mainOutEngagement).text =
                        getString(R.string.been_together_years, years)
                else if (years == 1 && months > 1)
                    findViewById<TextView>(R.id.mainOutEngagement).text =
                        getString(R.string.been_together_year_months, years, months)
                else if (years == 1 && months == 1)
                    findViewById<TextView>(R.id.mainOutEngagement).text =
                        getString(R.string.been_together_year_month, years, months)
                else if (years == 1 && months == 0 && days > 1)
                    findViewById<TextView>(R.id.mainOutEngagement).text =
                        getString(R.string.been_together_year_days, years, days)
                else if (years == 1 && months == 0 && days == 1)
                    findViewById<TextView>(R.id.mainOutEngagement).text =
                        getString(R.string.been_together_year_day, years, days)
                else if (years == 1 && months == 0 && days == 0)
                    findViewById<TextView>(R.id.mainOutEngagement).text =
                        getString(R.string.been_together_year, years)
            }

            if (months > 0 && years == 0) {
                if (months > 1 && days > 1)
                    findViewById<TextView>(R.id.mainOutEngagement).text =
                        getString(R.string.been_together_months_days, months, days)
                else if (years > 1 && days == 1)
                    findViewById<TextView>(R.id.mainOutEngagement).text =
                        getString(R.string.been_together_months_day, months, days)
                else if (months > 1 && days == 0)
                    findViewById<TextView>(R.id.mainOutEngagement).text =
                        getString(R.string.been_together_months, months)
                else if (months == 1 && days > 1)
                    findViewById<TextView>(R.id.mainOutEngagement).text =
                        getString(R.string.been_together_month_days, months, days)
                else if (months == 1 && days == 1)
                    findViewById<TextView>(R.id.mainOutEngagement).text =
                        getString(R.string.been_together_month_day, months, days)
                else if (months == 1 && days == 0)
                    findViewById<TextView>(R.id.mainOutEngagement).text =
                        getString(R.string.been_together_month, months)
            }

            if (days > 0 && months == 0 && years == 0) {
                if (days > 1) {
                    findViewById<TextView>(R.id.mainOutEngagement).text =
                        getString(R.string.been_together_days, days)
                }
                if (days == 1) {
                    findViewById<TextView>(R.id.mainOutEngagement).text =
                        getString(R.string.been_together_day, days)

                }

            }


        }

    }


    //----------------------------------------------------------------------------------------------Gestures

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        gestureDetector.onTouchEvent(event)
        when (event?.action) {

            0 -> {
                x1 = event.x

            }
            1 -> {
                x2 = event.x



                val valueX = x2 - x1


                if (abs(valueX) > MainActivity.MIN_DISTANCE){
                    if(x2>x1){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        val intent = Intent(this, MainMarriedActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
                    }
                }
            }

        }

        return super.onTouchEvent(event)
    }


    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent?) {
        // TODO("Not yet implemented")
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
        // TODO("Not yet implemented")
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

}


