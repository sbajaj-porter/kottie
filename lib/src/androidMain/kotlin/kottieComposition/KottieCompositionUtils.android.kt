package kottieComposition

import android.graphics.Rect as GraphicsRect
import androidx.compose.runtime.Composable
import com.airbnb.lottie.LottieComposition
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.toComposeRect

@Composable
actual fun bounds(composition: KottieCompositionAnimation): Rect {
    return compositionBounds(composition).toComposeRect()
}

private fun compositionBounds(composition: KottieCompositionAnimation): GraphicsRect = composition.bounds
