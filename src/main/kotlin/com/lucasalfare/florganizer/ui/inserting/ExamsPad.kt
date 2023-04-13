package com.lucasalfare.florganizer.ui.inserting

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ExamsPad(exams: MutableList<String>) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(4.dp)
      .border(width = 3.dp, color = Color.Gray)
  ) {
    exams.forEach { exam ->
      item {
        ExamCheckBox(exam)
        Divider()
      }
    }
  }
}