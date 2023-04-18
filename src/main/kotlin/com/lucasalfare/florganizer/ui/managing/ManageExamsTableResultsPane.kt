package com.lucasalfare.florganizer.ui.managing

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucasalfare.florganizer.*
import com.lucasalfare.florganizer.core.Exam
import com.lucasalfare.florganizer.core.OrganizerEvents
import com.lucasalfare.florganizer.core.Patient
import com.lucasalfare.florganizer.core.exams


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
        Text("salvar")
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
fun ExamColumn(headerText: String, patients: SnapshotStateList<Patient>) {
  Column(
    modifier = Modifier
      .fillMaxHeight()
      .border(width = 1.dp, color = Color.Gray)
  ) {
    Box(
      modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth()
        .height(50.dp)
    ) {
      Text(
        text = headerText,
        modifier = Modifier.align(Alignment.Center),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
      )

      Divider(
        modifier = Modifier.align(Alignment.BottomCenter),
        color = Color.DarkGray,
        thickness = 2.dp
      )
    }

    LazyColumn {
      patients.forEach { patient ->
        patient.exams.forEach { patientExam ->
          if (patientExam.name == headerText) {
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
      Text(
        text = patient.id,
        fontSize = 12.sp
      )
    }

    Box(
      modifier = Modifier
        .padding(start = 8.dp, end = 8.dp)
        .width(150.dp)
    ) {
      TextField(
        value = text,
        textStyle = LocalTextStyle.current.copy(
          fontSize = 12.sp
        ),
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