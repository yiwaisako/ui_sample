package jp.co.yiwaisako.ui_sample

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.ImageView

class AutoHorizontalScrollImageView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ImageView(context, attrs, defStyleAttr, defStyleRes) {

    init {
        scaleType = ScaleType.MATRIX
    }

    // スクロールを繰り返す上限回数
    var maxRepeatCount = 1

    // 1回のスクロール量
    var scrollValueXForOneTime = 1

    // スクロールした回数
    private var repeatCount = 0

    // 現在のX位置
    private var currentX = 0

    // 繰り返すかどうか
    private val isAutoScroll: Boolean
        get() {
            return repeatCount < maxRepeatCount
        }

    // スクロール方向
    private var direction: Direction = Direction.TO_RIGHT

    // スクロール量
    private val scrollValueX: Int
        get() {
            return when (direction) {
                Direction.TO_RIGHT -> {
                    scrollValueXForOneTime
                }
                Direction.TO_LEFT -> {
                    -scrollValueXForOneTime
                }
            }
        }

    enum class Direction {
        TO_RIGHT,
        TO_LEFT
    }

    override fun onDraw(canvas: Canvas?) {
        if (isAutoScroll) {
            scrollTo(scrollValueX)
            // scrollTo()の後に実行してください
            decideDirectionAfterScrolled()
        }
        super.onDraw(canvas)
    }

    private fun scrollTo(scrollValueX: Int) {
        currentX += scrollValueX
        scrollBy(scrollValueX, 0)
    }

    private fun decideDirectionAfterScrolled() {
        if (currentX <= 0) {
            repeatCount++
            direction = Direction.TO_RIGHT
        }
        // 右端まで表示したらスクロール方向を左へ変更する
        if (drawable.intrinsicWidth <= currentX + width) {
            direction = Direction.TO_LEFT
        }
    }
}