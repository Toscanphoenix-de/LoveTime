package com.fenni.kotlintry

import android.Manifest
import android.R.attr
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        pickImage()
        goHome()
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
            startActivityForResult(intent, 6)
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
            if (data != null) {

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
            }
        }
    }



    companion object {
        private val IMAGE_PICK_CODE = 100
        private val PERMISSION_CODE = 100

    }

}