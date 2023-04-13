package com.lucasalfare.florganizer.ui.listing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.lucasalfare.florganizer.OrganizerEvents
import com.lucasalfare.florganizer.Patient
import com.lucasalfare.florganizer.uiManager

@Composable
fun Listing(patients: SnapshotStateList<Patient>) {
  Box {
    LazyColumn {
      patients.forEach { patient ->
        item {
          Column {
            Row {
              Button(onClick = {
                uiManager.notifyListeners(
                  OrganizerEvents.RemovePatient, patient.id
                )
              }) {
                Text("-")
              }
              Text(patient.id)
            }

            Row {
              patient.exams.forEach { exam ->
                Row {
                  Button(onClick = {
                    uiManager.notifyListeners(
                      OrganizerEvents.RemovePatientExam,
                      arrayOf(
                        patient.id,
                        exam.name
                      )
                    )
                  }) {
                    Text("-")
                  }
                  Text(exam.name)
                }
              }
            }
          }
          Divider()
        }
      }
    }
  }
}