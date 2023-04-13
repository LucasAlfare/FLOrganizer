package com.lucasalfare.florganizer.ui.managing

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.lucasalfare.florganizer.Patient


@Composable
fun ManageExamsPane(patients: SnapshotStateList<Patient>) {
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