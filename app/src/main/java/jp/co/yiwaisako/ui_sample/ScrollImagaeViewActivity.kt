package jp.co.yiwaisako.ui_sample

import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout


class ScrollImagaeViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_imagae_view)

        val image = findViewById<ImageView>(R.id.sushi)
        image.doOnLayout {
            var repeatCount = 0
            val drawableWidth = image.drawable.intrinsicWidth
            val imageWidth = image.width
            var isScrollLeftToRight = true
            var currentX = 0

            val handler = Handler()
            val r = object : Runnable {
                override fun run() {

                    if (drawableWidth < currentX + imageWidth) {
                        isScrollLeftToRight = false
                    }

                    if (currentX < 0) {
                        repeatCount++
                        isScrollLeftToRight = true
                    }

                    if (repeatCount == 2) return

                    val scrollX = getScrollX(isScrollLeftToRight)
                    currentX += scrollX

                    image.scrollBy(
                        scrollX,
                        0
                    )

                    handler.postDelayed(this, 10)
                }
            }
            handler.post(r)
        }
    }

    private fun getScrollX(isScrollLeftToRight: Boolean): Int {
        val value = 1
        return if (isScrollLeftToRight) value else -value
    }
}
