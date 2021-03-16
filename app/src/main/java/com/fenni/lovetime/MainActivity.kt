package com.fenni.lovetime

/**
 * @author : Toscanhpoenix
 * @version: 3.0
 * @since: 2021-2-20*/

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.time.LocalDate
import java.time.Period
import kotlin.math.abs

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {

    lateinit var gestureDetector: GestureDetector
    private val dateCalculation = DateCalculation(this)
    private var alreadySend = false
    var x2 = 0.0f
    var x1 = 0.0f


    private val notificationID = 610


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = SharedPreferences(this, MEET_DATE)


        if (sharedPreferences.getValueInt(YEAR) == -1) {
            val intent = Intent(this, FirstStartActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val mMainView: View = layoutInflater.inflate(R.layout.activity_main,null)
            setContentView(mMainView)
            val toolbar: Toolbar? = findViewById(R.id.header)
            setSupportActionBar(toolbar)
            dateCheckAndSetTwo()
            namesCheckAndSet()
        }


        createNotificationChannel()



        gestureDetector = GestureDetector(this, this)



    }



    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "LoveTime"
            val descriptionTest = "LoveTime"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionTest
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }

    }



    override fun onTouchEvent(event: MotionEvent?): Boolean {

        gestureDetector.onTouchEvent(event)
        when (event?.action) {

            0 -> {
                x1 = event.x

            }
            1 -> {
                x2 = event.x

                
                val valueX = x2 - x1

                if (abs(valueX) > MIN_DISTANCE){
                    if(x2>x1){
                        Toast.makeText(this,"Nothing here ", Toast.LENGTH_SHORT).show()
                    }else{
                        val intent = Intent(this, MainEngagedActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
                    }
                }
            }

        }

        return super.onTouchEvent(event)
    }


    private fun namesCheckAndSet() {
        val sharedPreferencesNames = SharedPreferences(this, NAMES)
        findViewById<TextView>(R.id.dateBanner2).text = sharedPreferencesNames.getValueString(NAME)

    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun picture(){
        pictureCheckAndSet()
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun pictureCheckAndSet() {
        try {
            val sharedPreferences = this.getSharedPreferences(IMAGES, Context.MODE_PRIVATE)
            val dataPref = sharedPreferences.getString(IMG_TOGETHER, null)

            if (checkSelfPermission(Manifest.permission.ACCESS_MEDIA_LOCATION) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                val permissionRead = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissionRead, PERMISSION_CODE)
                val permissionMedia = arrayOf(Manifest.permission.ACCESS_MEDIA_LOCATION)
                requestPermissions(permissionMedia, PERMISSION_CODE)
            }
            if (dataPref != null) {
                val uri = Uri.parse(dataPref)

                findViewById<ImageView>(R.id.photo).setImageURI(uri)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pictureCheckAndSet()
                }else{
                    Toast.makeText(this, "Permission was denied ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    companion object {
        private val IMAGE_PICK_CODE = 100
        private val PERMISSION_CODE = 100

        const val MIN_DISTANCE = 150

        const val NOTIFICATION_ID = 610
        const val CHANNEL_ID = "fenni_danni"

        const val MEET_DATE: String = "meetDate"
        const val ENGAGEMENT_DATE:String = "engagementDate"
        const val MARRIED_DATE: String = "marriedDate"
        const val NAMES:String = "names"

        const val YEAR = "year"
        const val MONTH = "month"
        const val DAY= "day"

        const val NAME = "name"

        const val IMAGES = "images"
        const val IMG_TOGETHER = "img_together"

    }


    //-------------------------------------------------------------------------------------------------Date
    @RequiresApi(Build.VERSION_CODES.O)
    private fun dateCheckAndSetTwo() {
        val sharedPreferencesDate = SharedPreferences(this, MEET_DATE)
        val calc = DateCalculation(this)

        val savedYear = sharedPreferencesDate.getValueInt(YEAR,)
        val savedMonth = sharedPreferencesDate.getValueInt(MONTH)
        val savedDay = sharedPreferencesDate.getValueInt(DAY)

        if (sharedPreferencesDate.getValueInt(YEAR) == -1){
            val intent = Intent(this,FirstStartActivity::class.java)
            startActivity(intent)
        }else{
        findViewById<TextView>(R.id.dateBanner).text = "$savedDay.$savedMonth.$savedYear"}


        val dateToday = LocalDate.now()
        when {
            savedMonth < 10 -> {
                if (savedDay < 10) {
                    val dateString = "$savedYear-0$savedMonth-0$savedDay"
                    onDateSetText(calc.dateCalc(dateString, dateToday))
                } else {
                    val dateString = "$savedYear-0$savedMonth-$savedDay"
                    onDateSetText(calc.dateCalc(dateString, dateToday))
                }
            }
            savedDay < 10 -> {
                val dateString = "$savedYear-$savedMonth-0$savedDay"
                onDateSetText(calc.dateCalc(dateString, dateToday))
            }
            else -> {
                val dateString = "$savedYear-$savedMonth-$savedDay"
                onDateSetText(calc.dateCalc(dateString, dateToday))
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)

    private fun onDateSetText(period: Period?) {

        if (period != null) {


            isItWorthAnNotification(period)



            val days = period.days
            val months = period.months
            val years = period.years


            findViewById<TextView>(R.id.amount_days_together).text = days.toString()
            findViewById<TextView>(R.id.amount_month_together).text = months.toString()
            findViewById<TextView>(R.id.amount_years_together).text = years.toString()


            //output
            if (years > 0) {

                if (years > 1 && months > 1)
                    findViewById<TextView>(R.id.mainOut).text =
                        getString(R.string.been_together_years_months, years, months)
                else if (years >= 1 && months == 1)
                    findViewById<TextView>(R.id.mainOut).text =
                        getString(R.string.been_together_years_month, years, months)
                else if (years > 1 && months == 0 && days > 1)
                    findViewById<TextView>(R.id.mainOut).text =
                        getString(R.string.been_together_years_days, years, days)
                else if (years > 1 && months == 0 && days == 1)
                    findViewById<TextView>(R.id.mainOut).text =
                        getString(R.string.been_together_years_day, years, days)
                else if (years > 1 && months == 0 && days == 0)
                    findViewById<TextView>(R.id.mainOut).text =
                        getString(R.string.been_together_years, years)
                else if (years == 1 && months > 1)
                    findViewById<TextView>(R.id.mainOut).text =
                        getString(R.string.been_together_year_months, years, months)
                else if (years == 1 && months == 1)
                    findViewById<TextView>(R.id.mainOut).text =
                        getString(R.string.been_together_year_month, years, months)
                else if (years == 1 && months == 0 && days > 1)
                    findViewById<TextView>(R.id.mainOut).text =
                        getString(R.string.been_together_year_days, years, days)
                else if (years == 1 && months == 0 && days == 1)
                    findViewById<TextView>(R.id.mainOut).text =
                        getString(R.string.been_together_year_day, years, days)
                else if (years == 1 && months == 0 && days == 0)
                    findViewById<TextView>(R.id.mainOut).text =
                        getString(R.string.been_together_year, years)
            }

            if (months > 0 && years == 0) {
                if (months > 1 && days > 1)
                    findViewById<TextView>(R.id.mainOut).text =
                        getString(R.string.been_together_months_days, months, days)
                else if (years > 1 && days == 1)
                    findViewById<TextView>(R.id.mainOut).text =
                        getString(R.string.been_together_months_day, months, days)
                else if (months > 1 && days == 0)
                    findViewById<TextView>(R.id.mainOut).text =
                        getString(R.string.been_together_months, months)
                else if (months == 1 && days > 1)
                    findViewById<TextView>(R.id.mainOut).text =
                        getString(R.string.been_together_month_days, months, days)
                else if (months == 1 && days == 1)
                    findViewById<TextView>(R.id.mainOut).text =
                        getString(R.string.been_together_month_day, months, days)
                else if (months == 1 && days == 0)
                    findViewById<TextView>(R.id.mainOut).text =
                        getString(R.string.been_together_month, months)
            }

            if (days > 0 && months == 0 && years == 0) {
                if (days > 1) {
                    findViewById<TextView>(R.id.mainOut).text =
                        getString(R.string.been_together_days, days)
                }
                if (days == 1) {
                    findViewById<TextView>(R.id.mainOut).text =
                        getString(R.string.been_together_day, days)

                }

            }


        }

    }


    //Menu
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




    @RequiresApi(Build.VERSION_CODES.O)
    fun isItWorthAnNotification(period: Period?) {

        val dateCalculation = DateCalculation(this)
        val daysSince = dateCalculation.getDaysSince(this,MEET_DATE)

        if(period != null){

            val years = period.years
            val months = period.months
            val days = period.days

            if(years != 0 ) {
                if ((years % 0) == 0 && months == 0 && days == 0) {
                    sendNotifications("Today you have been together $years years")
                }
            }
            else if(months != 0){
                if(years<1){
                    if ((months%1) == 0 && days == 0){
                        sendNotifications("Today you have been together $months months ")
                    }
                }
                else if( years >= 1){
                    if ((months%3)== 0 && days == 0){
                        sendNotifications("Today you have been together $years years and $months months")
                    }
                }
            }
            else if(years == 0 && months == 0) {
                if(days == 30){
                    sendNotifications("Today you have been together for $days days")
                }
            }
            else if( daysSince% 50 == 0 && daysSince< 500){
                sendNotifications("Today you have been together for $daysSince days")
            }
            else if (daysSince > 500 && daysSince%100 == 0){
                sendNotifications("Today you have been together for $daysSince days")
            }
            else if (months==5 && daysSince == 3){
                sendNotifications("Today you have been together for $daysSince days")
            }
            else if(daysSince%11 == 0){
                sendNotifications("Today you have been together for $daysSince days")
            }
            else if(daysSince%111 == 0){
                sendNotifications("Today you have been together for $daysSince days")
            }


        }

    }

    private fun sendNotifications(string: String) {

        val intent = Intent(this, MainActivity::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK  or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this,0,intent,0)

        val bitmap = BitmapFactory.decodeResource(this.resources,R.drawable.heart_idea_icon)

        val  builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.heart_idea_icon)
            .setContentTitle("Love Time ")
            .setContentText("A special day is today")
            .setStyle(NotificationCompat.BigTextStyle().bigText(string))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(NOTIFICATION_ID, builder.build())
        }

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
}






