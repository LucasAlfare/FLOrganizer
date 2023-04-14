package com.lucasalfare.florganizer.ui.inserting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lucasalfare.florganizer.core.OrganizerEvents
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

  Box(modifier = Modifier.padding(4.dp)) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text("ID paciente:")
      TextField(currentPatientId, onValueChange = {
        currentPatientId = it
        uiManager.notifyListeners(OrganizerEvents.PatientIdUpdate, currentPatientId)
      })
    }
  }
}