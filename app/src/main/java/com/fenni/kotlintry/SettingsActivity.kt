package com.fenni.kotlintry

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.fenni.kotlintry.MainActivity.Companion.ENGAGEMENT_DATE
import com.fenni.kotlintry.MainActivity.Companion.MARRIED_DATE
import com.fenni.kotlintry.MainActivity.Companion.MEET_DATE
import com.fenni.kotlintry.MainActivity.Companion.NAMES
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class SettingsActivity : AppCompatActivity(),DatePickerDialog.OnDateSetListener {

    var year = 0;
    var month = 0
    var day = 0

    private var dateTogether = false
    private var dateEngaged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        pickImage()
        goHome()
        updateDate()
        updateEngagementDate()
        updateNames()
        resetApp()
    }

    private fun resetApp() {
        findViewById<Button>(R.id.btn_reset).setOnClickListener{

            reset()

            val intent = Intent(this, MainActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    //---------------------------------------------------------------------------------------------Reset
    private fun reset() {

        val sharedPreferencesMain = getSharedPreferences(MEET_DATE, Context.MODE_PRIVATE)
        val editorMain = sharedPreferencesMain.edit()
        editorMain.clear()
        editorMain.apply()

        val sharedPreferencesEngagement = getSharedPreferences(ENGAGEMENT_DATE, Context.MODE_PRIVATE)
        val editorEngagement = sharedPreferencesEngagement.edit()
        editorEngagement.clear()
        editorEngagement.apply()

        val sharedPreferencesMarried = getSharedPreferences(MARRIED_DATE, Context.MODE_PRIVATE)
        val editorMarried = sharedPreferencesMarried.edit()
        editorMarried.clear()
        editorMarried.apply()

        val sharedPreferencesNames = getSharedPreferences(NAMES, Context.MODE_PRIVATE)
        val editorNames = sharedPreferencesNames.edit()
        editorNames.clear()
        editorNames.apply()

    }



    private fun updateEngagementDate() {
        findViewById<Button>(R.id.btn_change_date_engagement).setOnClickListener{
            getDateCalender()
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
        val saveDay = dayOfMonth

        when {
            dateTogether -> {
                dateTogether = false
                val sharedPref = this.getSharedPreferences("meetDate", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putInt("year", saveYear)
                editor.putInt("month", saveMonth)
                editor.putInt("day", saveDay)

                editor.apply()

                Toast.makeText(this, "Date successfully changed", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            dateEngaged -> {
                dateEngaged = false
                val sharedPref = this.getSharedPreferences("engagementDate", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putInt("year", saveYear)
                editor.putInt("month", saveMonth)
                editor.putInt("day", saveDay)

                editor.apply()

                Toast.makeText(this, "Date successfully changed", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainEngagedActivity::class.java)
                startActivity(intent)
                finish()
            }
            else -> {
                val sharedPref = this.getSharedPreferences("marriageDate", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putInt("year", saveYear)
                editor.putInt("month", saveMonth)
                editor.putInt("day", saveDay)

                editor.apply()

                Toast.makeText(this, "Date successfully changed", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainMarriedActivity::class.java)
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
            //check runtime permission
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //denied
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            } else {
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
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            /* if (data != null) {

                // This is the key line item, URI specifies the name of the data
                 val mImageUri = data.data

                // Removes Uri Permission so that when you restart the device, it will be allowed to reload.
                grantUriPermission(
                    this.packageName,
                    mImageUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                if (mImageUri != null) {
                    this.contentResolver.takePersistableUriPermission(mImageUri, takeFlags)
                }

                // Saves image URI as string to Default Shared Preferences
                val preferences: SharedPreferences = this.getSharedPreferences("images",Context.MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putString("image", java.lang.String.valueOf(mImageUri))
                editor.apply()
            }*/
        }
    }



    companion object {
        private val IMAGE_PICK_CODE = 100
        private val PERMISSION_CODE = 100

    }

}


