package com.lucasalfare.florganizer.ui.inserting

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lucasalfare.florganizer.OrganizerEvents
import com.lucasalfare.florganizer.exams
import com.lucasalfare.florganizer.uiManager

@Composable
fun InsertPatientPane() {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(4.dp)
  ) {
    Box(modifier = Modifier.weight(0.5f)) {
      MyTextField()
    }

    Box(modifier = Modifier.weight(3f).fillMaxWidth()) {
      ExamsPad(exams)
    }

    Box(modifier = Modifier.weight(0.5f)) {
      Button(onClick = {
        uiManager.notifyListeners(OrganizerEvents.CreatePatient)
      }) {
        Text("+ adicionar")
      }
    }
  }
}