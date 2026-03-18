package com.manhduc.shop

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


class StudentView : ComponentActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Nhận dữ liệu gửi sang
        val name = intent.getStringExtra("NAME") ?: "Không có tiêu đề"
        val nameId = intent.getStringExtra("NAMEID") ?: "Không có nội dung"

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        Text("Thông tin của bạn là:")
                        Text(name)
                        Text(nameId)
//                        Button { onClick = { finish() }) { Text("Quay lại màn hình chính") })
                        Button({finish()}) {  Text("Quay lại màn hình chính") }
                    }
                }
            }
        }
    }
}