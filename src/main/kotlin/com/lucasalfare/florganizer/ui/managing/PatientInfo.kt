package com.lucasalfare.florganizer.ui.managing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import com.lucasalfare.florganizer.OrganizerEvents
import com.lucasalfare.florganizer.Patient
import com.lucasalfare.florganizer.uiManager

@Composable
fun PatientInfo(patient: Patient) {
  Column {
    Row {
      TextButton(onClick = {
        uiManager.notifyListeners(OrganizerEvents.RemovePatient, patient.id)
      }) {
        Text("-")
      }

      Text("ID: ${patient.id}")
    }
    patient.exams.forEach { exam ->
      ExamInfo(exam, patient.id)
    }
  }
}