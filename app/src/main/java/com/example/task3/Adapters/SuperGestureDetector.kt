package com.example.task3.Adapters

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference
import kotlin.math.abs


class SuperGestureDetector(
    context: Context,
    private val recyclerView: RecyclerView,
    private val directions: Int
) :
    GestureDetector.OnGestureListener, Animator.AnimatorListener {
    private val verticalThreshold: Float
    private val widthPixels: Float
    private var leftBorder = 0f
    private var rightBorder = 0f
    private var removeLeftBorder = 0f
    private var removeRightBorder = 0f
    private var callback: WeakReference<Callback?>? = null
    private var currentAnimator: ObjectAnimator? = null
    private var lastHolder: SwipableViewHolder? = null
    private var activeHolder: SwipableViewHolder? = null
    private var animatingHolder: SwipableViewHolder? = null
    private var removingHolder: SwipableViewHolder? = null
    private var startHorizontalDirection: Int? = null
    private var isSwiped = false
    private var isScrolled = false

    fun setCallback(callback: Callback?) {
        this.callback = WeakReference(callback)
    }

    override fun onDown(e: MotionEvent): Boolean {
        isSwiped = false
        isScrolled = false
        getActiveItem(e.x, e.y)
        restorePreviousHolder()
        checkCancelAnimation()
        return true
    }

    private fun getActiveItem(touchX: Float, touchY: Float) {
        activeHolder = null
        val view = recyclerView.findChildViewUnder(touchX, touchY)
        if (view != null) {
            val viewHolder = recyclerView.getChildViewHolder(view)
            if (viewHolder is SwipableViewHolder) {
                activeHolder = viewHolder
            }
        }
    }

    private fun restorePreviousHolder() {
        if (lastHolder == null) {
            return
        }
        if (activeHolder == null || lastHolder!!.adapterPosition != activeHolder!!.adapterPosition) {
            if (lastHolder!!.swipableView != null) {
                animatingHolder = lastHolder
                animatingHolder!!.setIsRecyclable(false)
                currentAnimator =
                    ObjectAnimator.ofFloat(lastHolder!!.swipableView, "translationX", 0f)
                currentAnimator?.addListener(this)
                currentAnimator?.duration = 200
                currentAnimator?.start()
            }
        }
    }

    private fun checkCancelAnimation() {
        if (lastHolder != null && currentAnimator != null && activeHolder != null) {
            if (lastHolder!!.adapterPosition == activeHolder!!.adapterPosition) {
                currentAnimator!!.cancel()
            }
        }
        lastHolder = null
    }

    fun onUp(e: MotionEvent): Boolean {
        startHorizontalDirection = null
        if (activeHolder != null) {
            if (!isScrolled && !isSwiped && e.action != MotionEvent.ACTION_CANCEL) {
                checkBorder(
                    e.x,
                    true
                )
            } else {
                checkBorder(0f, false)
            }
        }
        activeHolder = null
        return false
    }

    private fun checkBorder(
        touchX: Float,
        forced: Boolean
    ) {
        val swipableView = activeHolder!!.swipableView
        if (swipableView == null) {
            if (forced) {
                if (callback != null && callback!!.get() != null) {
                    callback!!.get()!!.onItemTouched(activeHolder!!.adapterPosition)
                }
            }
            return
        }
        val translationX = swipableView.translationX
        if (translationX != 0f) {
            lastHolder = activeHolder
            var to = 0f
            if (translationX < 0) {
                if (directions and LEFT > 0 && translationX <= leftBorder) {
                    if (!forced && translationX <= removeLeftBorder) {
                        restorePreviousHolder()
                        lastHolder = null
                        if (callback != null && callback!!.get() != null) {
                            removeAnimation()
                            return
                        }
                    } else if (forced) {
                        restorePreviousHolder()
                        lastHolder = null
                        if (callback != null && callback!!.get() != null) {
                            if (touchX > widthPixels + leftBorder) {
                                removeAnimation()
                                return
                            } else {
                                callback!!.get()!!.onItemTouched(activeHolder!!.adapterPosition)
                            }
                        }
                    } else {
                        to = leftBorder
                    }
                }
            } else if (directions and RIGHT > 0 && translationX >= rightBorder) {
                if (!forced && translationX >= removeRightBorder) {
                    restorePreviousHolder()
                    lastHolder = null
                    if (callback != null && callback!!.get() != null) {
                        removeAnimation()
                        return
                    }
                } else if (forced) {
                    restorePreviousHolder()
                    lastHolder = null
                    if (callback != null && callback!!.get() != null) {
                        if (touchX > rightBorder) {
                            removeAnimation()
                            return
                        } else {
                            callback!!.get()!!.onItemTouched(activeHolder!!.adapterPosition)
                        }
                    }
                } else {
                    to = rightBorder
                }
            }
            animatingHolder = activeHolder
            animatingHolder!!.setIsRecyclable(false)
            currentAnimator = ObjectAnimator.ofFloat(swipableView, "translationX", to)
            currentAnimator?.addListener(this)
            currentAnimator?.duration = 200
            currentAnimator?.start()
        } else {
            restorePreviousHolder()
            lastHolder = null
            if (forced) {
                if (callback != null && callback!!.get() != null) {
                    callback!!.get()!!.onItemTouched(activeHolder!!.adapterPosition)
                }
            }
        }
    }

    private fun removeAnimation() {
        if (activeHolder == null || activeHolder!!.swipableView == null) {
            return
        }
        if (removingHolder != null) {
            lastHolder = activeHolder
            activeHolder = null
            restorePreviousHolder()
            lastHolder = null
            return
        }
        removingHolder = activeHolder
        removingHolder!!.setIsRecyclable(false)
        val currentAnimator =
            ObjectAnimator.ofFloat(removingHolder!!.swipableView, "translationX", -widthPixels)
        currentAnimator.addListener(this)
        currentAnimator.duration = 200
        currentAnimator.start()
    }

    override fun onShowPress(e: MotionEvent) {}
    override fun onSingleTapUp(e: MotionEvent): Boolean {
        onUp(e)
        return false
    }

    override fun onScroll(
        e1: MotionEvent,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if (abs(distanceY) > abs(distanceX)) {
            if (startHorizontalDirection == null || abs(
                    abs(
                        abs(distanceX) - abs(
                            distanceY
                        )
                    )
                ) > verticalThreshold
            ) {
                isScrolled = true
                return false
            }
        }

        if (distanceX > 0 && directions and LEFT > 0) {
            if (startHorizontalDirection == null) {
                startHorizontalDirection = -1
            }
            isSwiped = true
            moveItem(distanceX, 0f)
            return true
        }

        if (activeHolder != null && activeHolder!!.swipableView != null) {
            if (directions and RIGHT > 0 || activeHolder!!.swipableView != null && activeHolder!!.swipableView!!.translationX != 0f) {
                if (startHorizontalDirection == null) {
                    startHorizontalDirection = 1
                }
                isSwiped = true
                moveItem(distanceX, 0f)
                return true
            }
        }
        return false
    }

    private fun moveItem(
        distanceX: Float,
        distanceY: Float
    ) {
        if (activeHolder != null && activeHolder!!.swipableView != null) {
            val swipableView = activeHolder!!.swipableView
            if (swipableView != null) {
                swipableView.translationX = swipableView.translationX - distanceX
                swipableView.translationY = swipableView.translationY - distanceY
            }
        }
    }

    override fun onLongPress(e: MotionEvent) {}
    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    override fun onAnimationStart(animation: Animator) {}
    override fun onAnimationEnd(animation: Animator) {
        currentAnimator = null
        if (removingHolder != null) {
            callback!!.get()!!.onItemRemove(removingHolder!!.adapterPosition)
        }
        if (animatingHolder != null) {
            if (removingHolder == null || animatingHolder !== removingHolder) {
                animatingHolder!!.setIsRecyclable(true)
            }
        }
        animatingHolder = null
    }

    override fun onAnimationCancel(animation: Animator) {
        onAnimationEnd(animation)
    }

    override fun onAnimationRepeat(animation: Animator) {}
    fun confirmDeletion() {
        if (removingHolder != null) {
            removingHolder!!.setIsRecyclable(true)
            removingHolder!!.swipableView!!.translationX = 0f
        }
        removingHolder = null
    }

    interface Callback {
        fun onItemRemove(position: Int)
        fun onItemTouched(position: Int)
    }

    abstract class SwipableViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        abstract val swipableView: View?
    }

    companion object {
        const val LEFT = 1
        const val RIGHT = 2
    }

    init {
        val displayMetrics = context.resources.displayMetrics
        verticalThreshold = 20 * displayMetrics.density
        widthPixels = displayMetrics.widthPixels.toFloat()
        if (directions and LEFT > 0) {
            leftBorder = -0.25f * displayMetrics.widthPixels
            removeLeftBorder = -0.75f * displayMetrics.widthPixels
        } else {
            leftBorder = 0f
            removeLeftBorder = 0f
        }
        if (directions and RIGHT > 0) {
            rightBorder = 0.25f * displayMetrics.widthPixels
            removeRightBorder = 0.9f * rightBorder
        } else {
            rightBorder = 0f
            removeRightBorder = 0f
        }
    }
}