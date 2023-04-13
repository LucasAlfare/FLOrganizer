package com.lucasalfare.florganizer.ui.managing

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import com.lucasalfare.florganizer.Exam
import com.lucasalfare.florganizer.OrganizerEvents
import com.lucasalfare.florganizer.uiManager

@Composable
fun ExamInfo(exam: Exam, relatedPatientId: String) {
  var text by remember { mutableStateOf(exam.result) }

  Row {
    Text("${exam.name}:")
    TextField(value = text, onValueChange = {
      text = it
      uiManager.notifyListeners(
        OrganizerEvents.ExamResultUpdate,
        arrayOf(
          relatedPatientId,
          exam.name,
          text
        )
      )
    })
  }
}