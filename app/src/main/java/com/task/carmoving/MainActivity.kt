package com.task.carmoving

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import com.task.carmoving.utils.path.EPointF
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList
import com.task.carmoving.utils.getMaxX
import com.task.carmoving.utils.getMaxY
import com.task.carmoving.view.RouteView
import java.util.*


class MainActivity : AppCompatActivity(), View.OnTouchListener, SeekBar.OnSeekBarChangeListener {


    private var routView: RouteView? = null
    private var complexity: Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setCarInRandomCoordinates()
        root.setOnTouchListener(this)
        setupSpeedSeekBar()
        setupRoutComplexitySeekBar()
    }

    private fun setupSpeedSeekBar() {
        speed_seek_bar.max = (MAX_SPEED - MIN_SPEED) / SPEED_STEP
        speed_seek_bar.setOnSeekBarChangeListener(this)
    }

    private fun setupRoutComplexitySeekBar() {
        complexity_seek_bar.max = (MAX_COMPLEXITY - MIN_COMPLEXITY) / COMPLEXITY_STEP
        complexity_seek_bar.setOnSeekBarChangeListener(this)
    }

    override fun onTouch(view: View?, event: MotionEvent): Boolean {
        val endX = event.x
        val endY = event.y
        val centreX = car.x + car.width / 2
        val centreY = car.y + car.height / 2

        val points = ArrayList<EPointF>()
        val startPoint = EPointF(centreX, centreY)
        val endPoint = EPointF(endX, endY)
        points.add(startPoint)
        complexity = complexity_seek_bar.progress
        repeat(complexity) {
            val x = Random().nextFloat() * 1000
            val y = Random().nextFloat() * 1000
            points.add(EPointF(x, y))
        }
        points.add(endPoint)
        removeRoute()
        addRoute(points)
        return false
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
        if (seekBar?.id == R.id.speed_seek_bar) {
            val progressValue = MIN_SPEED + (progress * SPEED_STEP)
            routView?.step = progressValue.toFloat()
        } else {
            val progressValue = MAX_COMPLEXITY + (progress * COMPLEXITY_STEP)
            complexity = progressValue
        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }

    private fun addRoute(points: List<EPointF>) {
        routView = RouteView(points, this)
        routView!!.step = speed_seek_bar.progress.toFloat()
        routView!!.animationStoped = {
            root.setOnTouchListener(this)
        }
        root.addView(routView)
        car.visibility = View.GONE
        root.setOnTouchListener(null)
    }

    private fun removeRoute() {
        root.removeView(routView)
        setCarInRandomCoordinates()
        car.visibility = View.VISIBLE
    }

    private fun setCarInRandomCoordinates() {
        car.x = ((1..getMaxX(this)).shuffled().first()).toFloat()
        car.y = ((1..getMaxY(this)).shuffled().first()).toFloat()
    }

    companion object {
        private const val MIN_SPEED = 0
        private const val MAX_SPEED = 30
        private const val SPEED_STEP = 1

        private const val MIN_COMPLEXITY = 2
        private const val MAX_COMPLEXITY = 20
        private const val COMPLEXITY_STEP = 1
    }
}
