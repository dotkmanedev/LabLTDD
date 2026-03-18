package com.manhduc.shop
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue //get value of state
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue // set value of state
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background, ) {
                    TextForm()
                }
            }
        }
    }
}



@Composable
// tao thành phần Form
fun TextForm () {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // Hàm dùng chung để mở màn hình 2 (Gửi Tiêu đề + Nội dung)
    fun openDetail(name: String, nameId: String) {
        val intent = Intent(context, StudentView::class.java).apply {
            putExtra("NAME", name)
            putExtra("NAMEID", nameId)
        }
        context.startActivity(intent)
    }

    var name by remember { mutableStateOf("") }
    var studentNumber by remember { mutableStateOf("") }
    var studentData by remember { mutableStateOf<Student?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = name,
            label = {Text("Họ và Tên")},
            modifier = Modifier.padding(2.dp),
            onValueChange = {newValue -> name = newValue},
        )

        TextField(
            value = studentNumber,
            label = {Text("Mã sinh viên")},
            modifier = Modifier.padding(2.dp),
            onValueChange = {newValue -> studentNumber = newValue},
        )

        Button(onClick = {openDetail(name, studentNumber)}) {
            Text("Đăng ký")
        }
    }



}