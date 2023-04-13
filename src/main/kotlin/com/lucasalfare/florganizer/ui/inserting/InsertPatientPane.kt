package com.lucasalfare.florganizer.ui.inserting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lucasalfare.florganizer.OrganizerEvents
import com.lucasalfare.florganizer.exams
import com.lucasalfare.florganizer.uiManager

@Composable
fun InsertPatientPane() {
  Column(modifier = Modifier.fillMaxSize()) {

    Box(modifier = Modifier.weight(0.5f)) {
      MyTextField()
    }

    Box(modifier = Modifier.weight(1.5f).fillMaxWidth()) {
      ExamsPad(exams)
    }

    Box(modifier = Modifier.weight(0.5f)) {
      Button(onClick = {
        uiManager.notifyListeners(OrganizerEvents.CreatePatient)
      }) {
        Text("add")
      }
    }
  }
}