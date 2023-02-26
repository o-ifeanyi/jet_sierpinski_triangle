package com.example.sierpinskitriangle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.unit.dp
import com.example.sierpinskitriangle.ui.theme.SierpinskiTriangleTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sliderValue = remember {
                mutableStateOf(0f)
            }
            val numOfPoints = remember(sliderValue.value) {
                sliderValue.value.toInt()
            }
            SierpinskiTriangleTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = {
                            Text(text = "Sierpinski Triangle ($numOfPoints Points)")
                        })
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = it.calculateTopPadding())
                            .fillMaxSize()
                    ) {
                        Slider(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            value = sliderValue.value,
                            onValueChange = { newVal ->
                                sliderValue.value = newVal
                            },
                            valueRange = 0f..25000f,
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp)
                                .background(color = Color.Black)
                        ) {
                            SierpinskiTriangle(numOfPoints = numOfPoints)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun SierpinskiTriangle(numOfPoints: Int) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val outerTrianglePoints = arrayOf(
                Offset(size.width / 2, 0f), // top middle
                Offset(0f, size.height), // bottom left
                Offset(size.width, size.height), // bottom right
            )

            val points = mutableListOf(
                *outerTrianglePoints,
                center
            )

            for (i in 0..numOfPoints) {
                val lastDrawnPoint = points.last()
                val randomOuterPoint = outerTrianglePoints[Random.nextInt(3)]

                val x = (lastDrawnPoint.x + randomOuterPoint.x) / 2
                val y = (lastDrawnPoint.y + randomOuterPoint.y) / 2
                points.add(Offset(x, y))
            }
            drawPoints(
                points = points,
                pointMode = PointMode.Points,
                color = Color.White,
                strokeWidth = 5f
            )
        }
    }
}
