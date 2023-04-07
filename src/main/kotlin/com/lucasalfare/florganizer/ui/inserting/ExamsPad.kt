package com.lucasalfare.florganizer.ui.inserting

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ExamsPad(exams: MutableList<String>) {
  LazyColumn(modifier = Modifier.fillMaxWidth().border(width = 5.dp, color = Color.Gray)) {
    exams.forEach { exam ->
      item {
        ExamCheckBox(exam)
        Divider()
      }
    }
  }
}