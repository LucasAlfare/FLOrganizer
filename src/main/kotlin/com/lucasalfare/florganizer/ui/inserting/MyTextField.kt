package com.lucasalfare.florganizer.ui.inserting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.lucasalfare.florganizer.OrganizerEvents
import com.lucasalfare.florganizer.uiManager

@Composable
fun MyTextField() {
  var currentPatientId by remember { mutableStateOf("") }

  DisposableEffect(true) {
    val callback = uiManager.addCallback { event, data ->
      when (event) {
        OrganizerEvents.PatientIdUpdate -> {
          currentPatientId = ""
        }

        OrganizerEvents.PatientsUpdate -> {
          currentPatientId = ""
        }

        else -> {}
      }
    }

    onDispose { uiManager.removeCallback(callback) }
  }

  Box {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text("ID paciente:")
      TextField(currentPatientId, onValueChange = {
        currentPatientId = it
        uiManager.notifyListeners(OrganizerEvents.PatientIdUpdate, currentPatientId)
      })
    }
  }
}