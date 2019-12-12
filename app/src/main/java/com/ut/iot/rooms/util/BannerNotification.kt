package com.ut.iot.rooms.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import com.ut.iot.rooms.R
import kotlinx.android.synthetic.main.banner_notification.view.*
import timber.log.Timber


/**
 * Created by Saeed on 17/11/2019.
 */
class BannerNotification : CoordinatorLayout {

    private var _bannerTitle: String = ""

    private var _bannerContent: String = ""

    @StringRes
    private var _rightButtonText: Int = R.string.ok

    @StringRes
    private var _leftButtonText: Int = R.string.dismiss


    var bannerTitle: String
        get() = _bannerTitle
        set(value) {
            _bannerTitle = value
            banner_title.text = value
        }

    var bannerContent: String
        get() = _bannerContent
        set(value) {
            _bannerContent = value
            banner_content.text = value
        }

    var rightButtonText: Int
        get() = _rightButtonText
        set(value) {
            _rightButtonText = value
            right_button.setText(value)
        }

    var leftButtonText: Int
        get() = _leftButtonText
        set(value) {
            _leftButtonText = value
            left_button.setText(value)
        }



    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }


    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        View.inflate(context, R.layout.banner_notification, this)

        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.BannerNotification, defStyle, 0
        )
        typedArray.recycle()

        left_button.setOnClickListener {
            dismiss()
        }

    }


    fun dismiss() {
        if (isVisible) {
            Timber.d("Banner hide()")
            this.collapse()
            this.visibility = View.GONE
        }
    }

    fun show() {

        Timber.d("Banner show()")
        this.expand()
    }

    fun setLeftButtonAction(action: () -> Unit) = left_button.setOnClickListener { action() }
    fun setRightButtonAction(action: () -> Unit) = right_button.setOnClickListener {
        dismiss()
        action()
    }


    private fun View.expand() {
        if (isVisible) {
            return
        }
        this@expand.measure(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val targetHeight = this@expand.measuredHeight

        this@expand.layoutParams.height = 0
        this@expand.visibility = View.VISIBLE
        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                this@expand.layoutParams.height = if (interpolatedTime == 1f)
                    ViewGroup.LayoutParams.WRAP_CONTENT
                else
                    (targetHeight * interpolatedTime).toInt()
                this@expand.requestLayout()
            }

            override fun willChangeBounds(): Boolean = true
        }

        animation.duration =
            (targetHeight / this@expand.context.resources.displayMetrics.density).toInt().toLong()
        this@expand.startAnimation(animation)
    }

    override fun setBackgroundResource(resid: Int) {
        super.setBackgroundResource(resid)
        banner_layout.setBackgroundResource(resid)
    }

    private fun View.collapse() {
        val initialHeight = this.measuredHeight

        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) {
                    this@collapse.visibility = View.GONE
                } else {
                    this@collapse.layoutParams.height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                    this@collapse.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean = true
        }

        animation.duration =
            (initialHeight / this.context.resources.displayMetrics.density).toInt().toLong()
        this.startAnimation(animation)
    }
}