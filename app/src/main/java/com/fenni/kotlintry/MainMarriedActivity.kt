package com.fenni.kotlintry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import kotlin.math.abs

class MainMarriedActivity : AppCompatActivity(),GestureDetector.OnGestureListener {

    lateinit var gestureDetector:GestureDetector
    var x2 = 0.0f
    var x1 = 0.0f
    var y2 = 0.0f
    var y1 = 0.0f

    companion object {
        const val MIN_DISTANCE = 150
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_married)

        gestureDetector = GestureDetector(this,this)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        gestureDetector.onTouchEvent(event)
        when (event?.action) {

            0 -> {
                x1 = event.x
                y1 = event.y
            }
            1 -> {
                x2 = event.x
                y2 = event.y


                val valueX = x2 - x1
                val valueY = y2 - y1

                if (abs(valueX) > MainActivity.MIN_DISTANCE){
                    if(x2>x1){
                        val intent = Intent(this, MainEngagedActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
                        finish()
                    }else{
                        Toast.makeText(this, " Not implemented yet",Toast.LENGTH_SHORT).show()
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


}
