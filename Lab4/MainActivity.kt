package com.manhduc.shop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manhduc.shop.ui.theme.ShopTheme
import kotlin.math.sqrt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "",
                        modifier = Modifier.padding(innerPadding)
                    )
                    QuadraticEquationSolver()
//                   TriangleCalculator()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        color = Color.Yellow
    )
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShopTheme {
        Greeting("Android11333")
    }
}


// Bài tập 1
@Composable
fun QuadraticEquationSolver() {
    // State quản lý dữ liệu đầu vào
    var aStr by remember { mutableStateOf("") }
    var bStr by remember { mutableStateOf("") }
    var cStr by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("Nhập hệ số để giải") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Giải Phương Trình Bậc 2", style = MaterialTheme.typography.displayLarge)

        OutlinedTextField(value = aStr, onValueChange = {it : String ->  aStr = it }, label = { Text("Hệ số a") })
        OutlinedTextField(value = bStr, onValueChange = {it : String-> bStr = it }, label = { Text("Hệ số b") })
        OutlinedTextField(value = cStr, onValueChange = {it  : String ->  cStr = it }, label = { Text("Hệ số c") })

        Button(
            onClick = {
                val a = aStr.toDoubleOrNull()
                val b = bStr.toDoubleOrNull()
                val c = cStr.toDoubleOrNull()

                if (a == null || b == null || c == null) {
                    result = "Vui lòng nhập số hợp lệ!"
                } else if (a == 0.0) {
                    result = if (b == 0.0) "PT vô nghiệm hoặc vô số nghiệm" else "PT bậc 1: x = ${-c / b}"
                } else {
                    val delta = b * b - 4 * a * c
                    result = when {
                        delta > 0 -> {
                            val x1 = (-b + sqrt(delta)) / (2 * a)
                            val x2 = (-b - sqrt(delta)) / (2 * a)
                            "x1 = $x1, x2 = $x2"
                        }
                        delta == 0.0 -> "Nghiệm kép: x = ${-b / (2 * a)}"
                        else -> "Phương trình vô nghiệm thực"
                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Giải")
        }

        Text(text = result, modifier = Modifier.padding(top = 16.dp), color = Color.Blue)
    }
}

@Composable
fun TriangleCalculator(modifier: Modifier = Modifier) {
    // State cho 3 cạnh
    var sideA by remember { mutableStateOf("") }
    var sideB by remember { mutableStateOf("") }
    var sideC by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("Nhập 3 cạnh để tính") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Tính Toán Tam Giác", style = MaterialTheme.typography.bodySmall, color = Color.Blue)

        // Ô nhập liệu cho từng cạnh
        TriangleInputSection("Cạnh a", sideA) { sideA = it }
        TriangleInputSection("Cạnh b", sideB) { sideB = it }
        TriangleInputSection("Cạnh c", sideC) { sideC = it }

        Button(
            onClick = {
                val a = sideA.toDoubleOrNull() ?: 0.0
                val b = sideB.toDoubleOrNull() ?: 0.0
                val c = sideC.toDoubleOrNull() ?: 0.0

                if (a + b > c && a + c > b && b + c > a) {
                    val p = a + b + c // Chu vi
                    val sHalf = p / 2 // Nửa chu vi
                    val area = sqrt(sHalf * (sHalf - a) * (sHalf - b) * (sHalf - c)) // Công thức Heron
                    result = "Chu vi: ${"%.2f".format(p)}\nDiện tích: ${"%.2f".format(area)}"
                } else {
                    result = "Lỗi: 3 cạnh này không tạo thành tam giác!"
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Tính toán ngay")
        }

        // Hiển thị kết quả

        Card (
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = result,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun TriangleInputSection(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // Chỉ nhận số và dấu chấm
            if (newValue.all { it.isDigit() || it == '.' }) onValueChange(newValue)
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}