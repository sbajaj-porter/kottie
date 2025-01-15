import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import contentScale.ContentScale
import org.jetbrains.skia.Rect
import org.jetbrains.skia.skottie.Animation
import org.jetbrains.skia.sksg.InvalidationController

internal fun Modifier.drawAnimationOnCanvas(
    animation: Animation?,
    time: Float,
    invalidationController: InvalidationController,
    contentScale: ContentScale,
    clipToCompositionBounds: Boolean = false
): Modifier = this then drawWithContent {
    drawIntoCanvas { canvas ->
        animation?.seekFrameTime(time, invalidationController)

        // Calculate destination rectangle based on contentScale
        val dst = animation?.let {
            calculateDestinationRect(
                animationWidth = it.width,
                animationHeight = it.height,
                containerWidth = size.width,
                containerHeight = size.height,
                contentScale = contentScale
            )
        } ?: Rect.makeWH(size.width, size.height)

        // Apply clipping if enabled
        if (clipToCompositionBounds) {
            canvas.clipRect(0f, 0f, size.width, size.height)
        }

        animation?.render(
            canvas = canvas.nativeCanvas,
            dst = dst
        )
    }
}

/**
 * Calculate the destination rectangle based on the contentScale.
 */
private fun calculateDestinationRect(
    animationWidth: Float,
    animationHeight: Float,
    containerWidth: Float,
    containerHeight: Float,
    contentScale: ContentScale
): Rect {
    return when (contentScale) {
        ContentScale.Fit -> {
            val scale = minOf(containerWidth / animationWidth, containerHeight / animationHeight)
            val width = animationWidth * scale
            val height = animationHeight * scale
            Rect.makeXYWH(
                l = (containerWidth - width) / 2,
                t = (containerHeight - height) / 2,
                w = width,
                h = height
            )
        }
        ContentScale.Crop -> {
            val scale = maxOf(containerWidth / animationWidth, containerHeight / animationHeight)
            val width = animationWidth * scale
            val height = animationHeight * scale
            Rect.makeXYWH(
                l = (containerWidth - width) / 2,
                t = (containerHeight - height) / 2,
                w = width,
                h = height
            )
        }
        ContentScale.FillBounds -> {
            Rect.makeWH(containerWidth, containerHeight)
        }
        ContentScale.FitWidth -> {
            val scale = containerWidth / animationWidth
            val height = animationHeight * scale
            Rect.makeXYWH(
                l = 0f,
                t = (containerHeight - height) / 2,
                w = containerWidth,
                h = height
            )
        }
        ContentScale.FitHeight -> {
            val scale = containerHeight / animationHeight
            val width = animationWidth * scale
            Rect.makeXYWH(
                l = (containerWidth - width) / 2,
                t = 0f,
                w = width,
                h = containerHeight
            )
        }
    }
}
