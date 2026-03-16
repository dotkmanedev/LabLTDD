package com.manhduc.shop

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainAppContent()
                }
            }
        }
    }
}

@Composable
fun MainAppContent() {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // Hàm dùng chung để mở màn hình 2 (Gửi Tiêu đề + Nội dung)
    fun openDetail(title: String, content: String) {
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra("TITLE", title)
            putExtra("CONTENT", content)
        }
        context.startActivity(intent)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("BÀI TẬP ANDROID (CHUYỂN MÀN HÌNH)", style = MaterialTheme.typography.headlineMedium)

        // --- BÀI 1: GIẢI PHƯƠNG TRÌNH BẬC 2 ---
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Bài 1: Giải PT Bậc 2", style = MaterialTheme.typography.titleLarge)
                var a by remember { mutableStateOf("") }
                var b by remember { mutableStateOf("") }
                var c by remember { mutableStateOf("") }

                OutlinedTextField(value = a, onValueChange = { a = it }, label = { Text("Hệ số a") })
                OutlinedTextField(value = b, onValueChange = { b = it }, label = { Text("Hệ số b") })
                OutlinedTextField(value = c, onValueChange = { c = it }, label = { Text("Hệ số c") })

                Button(onClick = {
                    try {
                        val va = a.toDouble(); val vb = b.toDouble(); val vc = c.toDouble()
                        val res = if (va == 0.0) {
                            if (vb == 0.0) "Vô nghiệm" else "x = ${-vc/vb}"
                        } else {
                            val delta = vb*vb - 4*va*vc
                            when {
                                delta < 0 -> "Vô nghiệm"
                                delta == 0.0 -> "Nghiệm kép x = ${-vb/(2*va)}"
                                else -> "x1 = ${(-vb + sqrt(delta))/(2*va)}\nx2 = ${(-vb - sqrt(delta))/(2*va)}"
                            }
                        }
                        // Tính xong, gửi sang Màn hình 2 luôn
                        openDetail("Kết Quả Phương Trình", res)
                    } catch (e: Exception) {
                        openDetail("Lỗi Nhập Liệu", "Vui lòng nhập số hợp lệ ở Bài 1!")
                    }
                }, modifier = Modifier.padding(top = 8.dp)) { Text("Giải và xem kết quả ở màn 2") }
            }
        }

        // --- BÀI 2: TAM GIÁC ---
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Bài 2: Tính Tam Giác", style = MaterialTheme.typography.titleLarge)
                var c1 by remember { mutableStateOf("") }
                var c2 by remember { mutableStateOf("") }
                var c3 by remember { mutableStateOf("") }

                OutlinedTextField(value = c1, onValueChange = { c1 = it }, label = { Text("Cạnh 1") })
                OutlinedTextField(value = c2, onValueChange = { c2 = it }, label = { Text("Cạnh 2") })
                OutlinedTextField(value = c3, onValueChange = { c3 = it }, label = { Text("Cạnh 3") })

                Button(onClick = {
                    try {
                        val x = c1.toDouble(); val y = c2.toDouble(); val z = c3.toDouble()
                        val res = if (x+y>z && x+z>y && y+z>x) {
                            val p = (x+y+z)/2
                            val s = sqrt(p*(p-x)*(p-y)*(p-z))
                            "Chu vi: ${x+y+z}\nDiện tích: %.2f".format(s)
                        } else { "3 cạnh không tạo thành tam giác!" }
                        // Tính xong, gửi sang Màn hình 2
                        openDetail("Kết Quả Tam Giác", res)
                    } catch (e: Exception) {
                        openDetail("Lỗi Nhập Liệu", "Vui lòng nhập số hợp lệ ở Bài 2!")
                    }
                }, modifier = Modifier.padding(top = 8.dp)) { Text("Tính và xem kết quả ở màn 2") }
            }
        }

        // --- BÀI 3: TRUYỀN DỮ LIỆU SINH VIÊN ---
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Bài 3: Quản lý Sinh Viên", style = MaterialTheme.typography.titleLarge)
                var maSV by remember { mutableStateOf("") }
                var tenSV by remember { mutableStateOf("") }

                OutlinedTextField(value = maSV, onValueChange = { maSV = it }, label = { Text("Mã SV") })
                OutlinedTextField(value = tenSV, onValueChange = { tenSV = it }, label = { Text("Tên SV") })

                Button(onClick = {
                    val res = "Mã Sinh Viên: $maSV\nTên Sinh Viên: $tenSV"
                    openDetail("Thông tin Sinh Viên", res)
                }, modifier = Modifier.padding(top = 8.dp)) { Text("Gửi dữ liệu sang màn hình 2") }
            }
        }
    }
}

// =====================================================================
// MÀN HÌNH 2 (ĐƯỢC DÙNG CHUNG CHO CẢ 3 BÀI TẬP)
// =====================================================================
class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Nhận dữ liệu gửi sang
        val title = intent.getStringExtra("TITLE") ?: "Không có tiêu đề"
        val content = intent.getStringExtra("CONTENT") ?: "Không có nội dung"

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        Text(title, style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(20.dp))

                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(content, style = MaterialTheme.typography.titleLarge)
                            }
                        }

                        Spacer(modifier = Modifier.height(30.dp))
                        Button(onClick = { finish() }) { Text("Quay lại màn hình chính") }
                    }
                }
            }
        }
    }
}