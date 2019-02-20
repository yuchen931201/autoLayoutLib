package com.tz.autolayoutlib

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * @ComputerCode: YD-YF-2015083113-1
 * @Author: TianZhen
 * @QQ: 959699751
 * @CreateTime: Created on 2019/2/19 15:25
 * @Package: com.tz.autolayout
 * @Description:
 **/
class AutoScreen{

    companion object {
        var displayWidth: Int = 0
        var displayHeight: Int = 0

        private var designWidth: Int = 0
        private var designHeight: Int = 0

        private var textPixelsRate: Double = 0.toDouble()

        fun setSize(act: Activity?, hasStatusBar: Boolean, designWidth: Int, designHeight: Int) {

            if (act == null || designWidth < 1 || designHeight < 1)
                return

            val display = act.windowManager.defaultDisplay
            val width = display.width
            var height = display.height


            if (hasStatusBar) {
                height -= getStatusBarHeight(act)
            }

            AutoScreen.displayWidth = width
            AutoScreen.displayHeight = height

            AutoScreen.designWidth = designWidth
            AutoScreen.designHeight = designHeight

            val displayDiagonal = Math.sqrt(
                Math.pow(
                    AutoScreen.displayWidth.toDouble(),
                    2.0
                ) + Math.pow(AutoScreen.displayHeight.toDouble(), 2.0)
            )
            val designDiagonal = Math.sqrt(
                Math.pow(
                    AutoScreen.designWidth.toDouble(),
                    2.0
                ) + Math.pow(AutoScreen.designHeight.toDouble(), 2.0)
            )

            AutoScreen.textPixelsRate = displayDiagonal / designDiagonal

        }


        fun getStatusBarHeight(context: Context): Int {

            var result = 0
            try {
                val resourceId = context.resources.getIdentifier(
                    "status_bar_height", "dimen", "android"
                )

                if (resourceId > 0) {
                    result = context.resources.getDimensionPixelSize(resourceId)
                }

            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
            }

            return result

        }


        fun auto(act: Activity?) {

            if (act == null || displayWidth < 1 || displayHeight < 1)
                return

            val view = act.window.decorView
            auto(view)
        }


        fun auto(view: View?) {

            if (view == null || displayWidth < 1 || displayHeight < 1)
                return

            AutoScreen.autoTextSize(view)
            AutoScreen.autoSize(view)
            AutoScreen.autoPadding(view)
            AutoScreen.autoMargin(view)


            if (view is ViewGroup) {
                auto((view as ViewGroup?)!!)
            }


        }


        private fun auto(viewGroup: ViewGroup) {

            val count = viewGroup.childCount

            for (i in 0 until count) {
                val child = viewGroup.getChildAt(i)

                if (child != null) {
                    auto(child)
                }
            }

        }


        fun autoMargin(view: View) {

            if (view.layoutParams !is ViewGroup.MarginLayoutParams)
                return


            val lp = view.layoutParams as ViewGroup.MarginLayoutParams ?: return


            lp.leftMargin = getDisplayWidthValue(lp.leftMargin)
            lp.topMargin = getDisplayHeightValue(lp.topMargin)
            lp.rightMargin = getDisplayWidthValue(lp.rightMargin)
            lp.bottomMargin = getDisplayHeightValue(lp.bottomMargin)

        }


        fun autoPadding(view: View) {

            var l = view.paddingLeft
            var t = view.paddingTop
            var r = view.paddingRight
            var b = view.paddingBottom


            l = getDisplayWidthValue(l)
            t = getDisplayHeightValue(t)
            r = getDisplayWidthValue(r)
            b = getDisplayHeightValue(b)

            view.setPadding(l, t, r, b)
        }


        fun autoSize(view: View) {

            val lp = view.layoutParams ?: return

            var isSquare = false
            if (lp.width == lp.height) {
                isSquare = true
            }

            if (lp.width > 0) {
                lp.width = getDisplayWidthValue(lp.width)
            }

            if (lp.height > 0) {
                lp.height = getDisplayHeightValue(lp.height)
            }

            if (isSquare) {
                if (lp.width > lp.height) {
                    lp.width = lp.height
                } else {
                    lp.height = lp.width
                }
            }

        }


        fun autoTextSize(view: View) {

            if (view is TextView) {

                val designPixels = view.textSize.toDouble()
                val displayPixels = textPixelsRate * designPixels

                view.includeFontPadding = false
                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, displayPixels.toFloat())

            }

        }


        fun getDisplayWidthValue(designWidthValue: Int): Int {

            return if (Math.abs(designWidthValue) < 2) {
                designWidthValue
            } else designWidthValue * displayWidth / designWidth

        }


        fun getDisplayHeightValue(designHeightValue: Int): Int {

            return if (Math.abs(designHeightValue) < 2) {
                designHeightValue
            } else designHeightValue * displayHeight / designHeight

        }

        fun getDisplayTextSize(designTextSize: Float): Float {
            return (AutoScreen.textPixelsRate * designTextSize).toFloat()
        }

    }


}