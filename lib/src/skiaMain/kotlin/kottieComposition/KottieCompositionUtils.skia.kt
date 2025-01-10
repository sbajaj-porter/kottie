package kottieComposition

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Rect
import org.jetbrains.skia.Point

@Composable
actual fun bounds(composition: KottieCompositionAnimation): Rect = composition.size.toRect()

private fun Point.toRect() = Rect(0f, 0f, x, y)
