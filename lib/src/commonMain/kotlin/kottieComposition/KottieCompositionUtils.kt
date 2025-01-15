package kottieComposition

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Rect

@Composable
expect fun bounds(composition: KottieCompositionAnimation): Rect
