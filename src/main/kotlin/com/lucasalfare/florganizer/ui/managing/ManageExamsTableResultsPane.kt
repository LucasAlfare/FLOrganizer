package com.lucasalfare.florganizer.ui.managing

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucasalfare.florganizer.*


@Composable
fun ManageExamsTableResultsPane(patients: SnapshotStateList<Patient>) {
  Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
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
  LazyRow(modifier = modifier) {
    exams.forEach { exam ->
      item {
        ExamColumn(exam, patients)
      }
    }
  }
}

@Composable
fun ExamColumn(header: String, patients: SnapshotStateList<Patient>) {
  Column(
    modifier = Modifier
      .fillMaxHeight()
      .width(140.dp)
      .border(width = 1.dp, color = Color.Gray)
  ) {
    Box(modifier = Modifier.padding(12.dp).fillMaxWidth()) {
      Text(
        text = header,
        modifier = Modifier.align(Alignment.Center),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
      )
    }

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

  Row(
    modifier = Modifier.padding(4.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
      Text(patient.id)
    }

    Box(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
      TextField(
        value = text,
        onValueChange = {
          text = it
          uiManager.notifyListeners(
            OrganizerEvents.ExamResultUpdate,
            arrayOf(
              patient.id,
              relatedExam.name,
              text
            )
          )
        }
      )
    }
  }
}