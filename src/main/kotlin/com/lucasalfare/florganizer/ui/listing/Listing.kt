package com.lucasalfare.florganizer.ui.listing

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucasalfare.florganizer.OrganizerEvents
import com.lucasalfare.florganizer.Patient
import com.lucasalfare.florganizer.uiManager


// TODO: add a dedicated button to copy info about related patient
@Composable
fun Listing(patients: SnapshotStateList<Patient>) {
  Box {
    LazyColumn {
      patients.forEach { patient ->
        item {
          Column {
            Row(
              verticalAlignment = Alignment.CenterVertically
            ) {
              TextButton(
                modifier = Modifier.size(30.dp),
                onClick = {
                  uiManager.notifyListeners(
                    OrganizerEvents.RemovePatient, patient.id
                  )
                }) {
                Text("-")
              }

              Text(
                text = patient.id,
                fontWeight = FontWeight.Bold
              )
            }

            Row(modifier = Modifier.padding(start = 12.dp)) {
              patient.exams.forEach { exam ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    TextButton(
                      modifier = Modifier.size(30.dp),
                      onClick = {
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
                    Text(
                      text = exam.name,
                      fontSize = 12.sp
                    )
                  }
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