package com.lucasalfare.florganizer.ui.managing

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import com.lucasalfare.florganizer.Exam
import com.lucasalfare.florganizer.OrganizerEvents
import com.lucasalfare.florganizer.Patient
import com.lucasalfare.florganizer.uiManager

@Composable
fun ExamInfo(exam: Exam, relatedPatientId: String) {
  var text by remember { mutableStateOf("") }

  Row {
    Text("${exam.name}:")
    TextField(value = text, onValueChange = {
      text = it
      uiManager.notifyListeners(OrganizerEvents.ExamResultUpdate, arrayOf(relatedPatientId, exam.name, text))
    })
  }
}

@Composable
fun ManageExamsPane(patients: MutableList<Patient>) {
  LazyColumn {
    patients.forEach { patient ->
      item {
        Text("ID: ${patient.id}")
        patient.exams.forEach { exam ->
          ExamInfo(exam, patient.id)
          Divider()
        }
      }
    }
  }
}