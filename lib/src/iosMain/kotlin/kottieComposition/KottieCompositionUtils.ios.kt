@file:OptIn(ExperimentalForeignApi::class)

package kottieComposition

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Rect
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRect

@Composable
actual fun bounds(composition: KottieCompositionAnimation): Rect {
    return compositionBounds(composition).toComposeRect()
}

private fun compositionBounds(composition: KottieCompositionAnimation): CGRect =
    composition.frame.useContents { this }

private fun CGRect.toComposeRect(): Rect = Rect(
    left = this.origin.x.toFloat(),
    top = this.origin.y.toFloat(),
    right = (this.origin.x + this.size.width).toFloat(),
    bottom = (this.origin.y + this.size.height).toFloat()
)
