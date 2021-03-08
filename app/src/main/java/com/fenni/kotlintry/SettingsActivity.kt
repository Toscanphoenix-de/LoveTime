package com.fenni.kotlintry

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.fenni.kotlintry.MainActivity.Companion.DAY
import com.fenni.kotlintry.MainActivity.Companion.ENGAGEMENT_DATE
import com.fenni.kotlintry.MainActivity.Companion.IMAGES
import com.fenni.kotlintry.MainActivity.Companion.IMG_TOGETHER
import com.fenni.kotlintry.MainActivity.Companion.MARRIED_DATE
import com.fenni.kotlintry.MainActivity.Companion.MEET_DATE
import com.fenni.kotlintry.MainActivity.Companion.MONTH
import com.fenni.kotlintry.MainActivity.Companion.NAMES
import com.fenni.kotlintry.MainActivity.Companion.YEAR
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class SettingsActivity : AppCompatActivity(),DatePickerDialog.OnDateSetListener {

    var year = 0;
    var month = 0
    var day = 0

    private val CHANNEL_ID = "fenni_danni"
    private val notificationID = 610

    private var dateTogether = false
    private var dateEngaged = false
    private var dateMarried = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        createNotificationChannel()
        resetImage()
        pickImage()
        goHome()
        updateDate()
        updateEngagementDate()
        updateMarriageDate()
        updateNames()
        resetApp()

    }

    private fun resetImage() {
        findViewById<Button>(R.id.btn_reset_image).setOnClickListener {
            sendNotification()
        }
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

    private fun sendNotification(){
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.heart_idea_icon)
            .setContentTitle("Love Time")
            .setContentText(" A special Day is today!!!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationID, builder.build())
        }
    }


    private fun updateMarriageDate() {
        findViewById<Button>(R.id.btn_change_date_marriage).setOnClickListener {
            getDateCalender()
            dateMarried = true
            DatePickerDialog(this,this,year,month,day).show()
        }
    }


    //---------------------------------------------------------------------------------------------Reset
    private fun resetApp() {
        findViewById<Button>(R.id.btn_reset).setOnClickListener{

            reset()

            val intent = Intent(this, MainActivity::class.java)
            finish()
            startActivity(intent)
        }
    }


    private fun reset() {

        val sharedPreferencesMain = SharedPreferences(this,MEET_DATE)
        sharedPreferencesMain.clearSharedPref()

       /* val sharedPreferencesEngagement = getSharedPreferences(ENGAGEMENT_DATE, Context.MODE_PRIVATE)
        val editorEngagement = sharedPreferencesEngagement.edit()
        editorEngagement.clear()
        editorEngagement.apply()*/

        /*val sharedPreferencesMarried = getSharedPreferences(MARRIED_DATE, Context.MODE_PRIVATE)
        val editorMarried = sharedPreferencesMarried.edit()
        editorMarried.clear()
        editorMarried.apply()*/

        val sharedPreferencesNames = getSharedPreferences(NAMES, Context.MODE_PRIVATE)
        val editorNames = sharedPreferencesNames.edit()
        editorNames.clear()
        editorNames.apply()

    }



    private fun updateEngagementDate() {
        findViewById<Button>(R.id.btn_change_date_engagement).setOnClickListener{
            getDateCalender()
            dateEngaged = true
            DatePickerDialog(this,this,year,month,day).show()
        }
    }

    private fun updateNames() {
        findViewById<Button>(R.id.edit_Names).setOnClickListener{

            val sharedPreferences = this. getSharedPreferences(NAMES, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.edit_text_layout, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.et_editText)

            with(builder){
                setTitle("Enter your Names")
                setPositiveButton("OK"){
                        _, _ ->
                    val names = editText.text.toString()
                    editor.putString("name", names)
                    editor.apply()
                    Toast.makeText(this@SettingsActivity,"Names successfully applied", Toast.LENGTH_SHORT).show()
                }
                setNegativeButton("Cancel"){
                        _, _ ->
                    Log.d("Main","Cancelled ")
                }
                setView(dialogLayout)
                show()


            }


        }
    }


    private fun updateDate() {
        findViewById<Button>(R.id.btn_change_date).setOnClickListener{
            getDateCalender()
            dateTogether = true
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
        val saveMonth = month + 1

        when {
            dateTogether -> {
                dateTogether = false
                val sharedPreferences = SharedPreferences(this, MEET_DATE)

                sharedPreferences.save(YEAR,saveYear)
                sharedPreferences.save(MONTH,saveMonth)
                sharedPreferences.save(DAY,dayOfMonth)

                Toast.makeText(this, "Date successfully changed", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            dateEngaged -> {
                dateEngaged = false
                val sharedPreferences = SharedPreferences(this, ENGAGEMENT_DATE)

                sharedPreferences.save(YEAR,saveYear)
                sharedPreferences.save(MONTH,saveMonth)
                sharedPreferences.save(DAY,dayOfMonth)


                Toast.makeText(this, "Date successfully changed", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainEngagedActivity::class.java)
                startActivity(intent)
                finish()
            }
            dateMarried -> {
                val sharedPreferences = SharedPreferences(this, MARRIED_DATE)

                sharedPreferences.save(YEAR,saveYear)
                sharedPreferences.save(MONTH,saveMonth)
                sharedPreferences.save(DAY,dayOfMonth)

                Toast.makeText(this, "Marriage successfully changed", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainMarriedActivity::class.java)
                startActivity(intent)
                finish()
            }
            else -> {
                Toast.makeText(this, "Oops", Toast.LENGTH_SHORT)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }







    //Home Button
    private fun goHome() {
        findViewById<FloatingActionButton>(R.id.go_home).setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // image Picker
    private fun pickImage() {
        findViewById<Button>(R.id.btn_select_image).setOnClickListener {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                startActivityForResult(intent, REQUEST_CODE)
            }
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    pickImage()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val sharedPreferences = this.getSharedPreferences(IMAGES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
             if (data != null) {
                 val mImageUri = data.data
                 editor.putString(IMG_TOGETHER,mImageUri.toString())
                 editor.apply()
                 Toast.makeText(this, "Image successfully saved", Toast.LENGTH_SHORT).show()
            }
        }
    }



    companion object {
        private val REQUEST_CODE = 610
        private val PERMISSION_CODE = 100

    }

}


