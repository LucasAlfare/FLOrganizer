package com.lucasalfare.florganizer.ui.managing

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lucasalfare.florganizer.*


@Composable
fun ManageExamsPane(patients: SnapshotStateList<Patient>) {
  Column(modifier = Modifier.fillMaxSize()) {
    ExamsTable(patients, Modifier.weight(3f))

    Box(modifier = Modifier.weight(0.5f)) {
      Button(
        onClick = {
          uiManager.notifyListeners(OrganizerEvents.SavePatients)
        }
      ) {
        Text("save")
      }
    }
  }
}

@Composable
fun ExamsTable(patients: SnapshotStateList<Patient>, modifier: Modifier) {
  Row(modifier = modifier) {
    exams.forEach { exam ->
      ExamColumn(exam, patients)
    }
  }
}

@Composable
fun ExamColumn(header: String, patients: SnapshotStateList<Patient>) {
  Column(
    modifier = Modifier
      .fillMaxHeight()
      .width(150.dp)
      .border(width = 1.dp, color = Color.Gray)
  ) {
    Text(header)

    LazyColumn {
      patients.forEach { patient ->
        patient.exams.forEach { patientExam ->
          if (patientExam.name == header) {
            item {
              ExamItem(patient, patientExam)
              Divider()
            }
          }
        }
      }
    }
  }
}

@Composable
fun ExamItem(patient: Patient, relatedExam: Exam) {
  var text by remember { mutableStateOf(relatedExam.result) }

  Row {
    Text(patient.id)
    TextField(value = text, onValueChange = {
      text = it
      uiManager.notifyListeners(
        OrganizerEvents.ExamResultUpdate,
        arrayOf(
          patient.id,
          relatedExam.name,
          text
        )
      )
    })
  }
}