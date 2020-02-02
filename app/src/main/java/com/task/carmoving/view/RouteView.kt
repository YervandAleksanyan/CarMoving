package com.task.carmoving.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.View
import com.task.carmoving.R
import com.task.carmoving.utils.path.EPointF
import com.task.carmoving.utils.path.PolyBezierPathUtil.computePathThroughKnots
import kotlin.math.atan2


@SuppressLint("ViewConstructor")
class RouteView constructor(private val points: List<EPointF>, context: Context) : View(context) {

    var step: Float = 0.toFloat()
    var animationStoped: () -> Unit = {}

    private lateinit var paint: Paint
    private lateinit var bm: Bitmap
    private var bmOffsetX: Int = 0
    private var bmOffsetY: Int = 0
    private lateinit var animPath: Path
    private lateinit var pathMeasure: PathMeasure
    private var pathLength: Float = 0.toFloat()

    private var distance: Float = 0.toFloat()

    private lateinit var pos: FloatArray
    private lateinit var tan: FloatArray

    private lateinit var mMatrix: Matrix

    init {
        initMyView()
    }

    private fun initMyView() {
        paint = Paint()
        paint.color = Color.RED
        paint.strokeWidth = 20f
        paint.style = Paint.Style.STROKE

        bm = BitmapFactory.decodeResource(resources, R.drawable.ic_car)
        bmOffsetX = bm.width / 2
        bmOffsetY = bm.height / 2

        animPath = Path()
        animPath = computePathThroughKnots(points)

        pathMeasure = PathMeasure(animPath, false)
        pathLength = pathMeasure.length


        step = 0f
        distance = 0f
        pos = FloatArray(2)
        tan = FloatArray(2)

        mMatrix = Matrix()
    }

    override fun onDraw(canvas: Canvas) {

        canvas.drawPath(animPath, paint)

        if (distance < pathLength) {
            pathMeasure.getPosTan(distance, pos, tan)

            mMatrix.reset()
            val degrees =
                (atan2(tan[1].toDouble(), tan[0].toDouble()) * 180.0 / Math.PI).toFloat()
            mMatrix.postRotate(degrees, bmOffsetX.toFloat(), bmOffsetY.toFloat())
            mMatrix.postTranslate(pos[0] - bmOffsetX, pos[1] - bmOffsetY)
            canvas.drawBitmap(bm, mMatrix, null)
            distance += step
            invalidate()
        } else {
            canvas.drawBitmap(bm, mMatrix, null)
            distance = 0f
            animationStoped.invoke()
        }
    }
}