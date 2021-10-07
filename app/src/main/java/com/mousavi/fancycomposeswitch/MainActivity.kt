package com.mousavi.fancycomposeswitch

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mousavi.fancycomposeswitch.ui.theme.FancyComposeSwitchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FancyComposeSwitchTheme {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    FancySwitch()
                }
            }
        }
    }
}

@Composable
fun FancySwitch(
    size: Dp = 100.dp
) {
    var checked by remember {
        mutableStateOf(false)
    }

    val anim = animateFloatAsState(
        targetValue = if (checked) {
            with(LocalDensity.current) {
                (size.toPx() / 80f)
            }
        } else {
            with(LocalDensity.current) {
                (size.toPx() / 6f)
            }
        },
        animationSpec = tween(durationMillis = 1000, easing = {
            OvershootInterpolator(4f).getInterpolation(it)
        })
    )

    val animColor = animateColorAsState(
        targetValue = if (checked) Color.Green else Color.Red,
        animationSpec = tween(durationMillis = 1000)
    )

    Box(modifier = Modifier
        .size(size)
        .clickable {
            checked = !checked
        }) {

        Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(
                color = animColor.value
            )
            val path = Path().apply {
                addOval(
                    oval = Rect(
                        topLeft = Offset(
                            x = center.x - anim.value,
                            y = center.y - size.toPx() / 6f
                        ),
                        bottomRight = Offset(
                            x = center.x + anim.value,
                            y = center.y + size.toPx() / 6f
                        )
                    )
                )
            }
            drawPath(
                path = path,
                color = Color.White,
                style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
            )

        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FancyComposeSwitchTheme {
        FancySwitch()
    }
}