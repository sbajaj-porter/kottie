package kottieComposition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import lottie.lottieComposition.LottieCompositionSpec
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun kottieComposition(
    spec: KottieCompositionSpec
): Any? {
    var skiaSpec by remember {  mutableStateOf<LottieCompositionSpec?>( null) }
    LaunchedEffect(spec){
        skiaSpec = when(spec){
            is KottieCompositionSpec.File -> {
                LottieCompositionSpec.File(spec.path)
            }
            is KottieCompositionSpec.Url -> {
                LottieCompositionSpec.Url(spec.url)
            }
//            is KottieCompositionSpec.JsonString -> {
//                LottieCompositionSpec.JsonString(spec.jsonString)
//            }
        }
    }
    return skiaSpec
}