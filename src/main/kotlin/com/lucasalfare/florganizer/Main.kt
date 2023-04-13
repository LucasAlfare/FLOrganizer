package com.lucasalfare.florganizer// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.lucasalfare.fllistener.UIManager
import com.lucasalfare.fllistener.setupManagers
import com.lucasalfare.florganizer.ui.inserting.InsertPatientPane
import com.lucasalfare.florganizer.ui.listing.Listing
import com.lucasalfare.florganizer.ui.managing.ManageExamsTableResultsPane


val uiManager = UIManager()

@Suppress("UNCHECKED_CAST")
@Composable
fun App() {
  MaterialTheme {
    var organizerMode by remember { mutableStateOf(0) }
    val patients = remember { mutableStateListOf<Patient>() }

    DisposableEffect(true) {
      val callback = uiManager.addCallback { event, data ->
        when (event) {
          OrganizerEvents.PatientsUpdate -> {
            patients.clear()
            val pat = data as SnapshotStateList<Patient>
            patients.addAll(pat)
          }

          else -> {}
        }
      }

      onDispose { uiManager.removeCallback(callback) }
    }

    Column {
      Row {
        Button(
          enabled = organizerMode == 1 || organizerMode == 2,
          onClick = { organizerMode = 0 }
        ) {
          Text("insert")
        }

        Button(
          enabled = organizerMode == 0 || organizerMode == 2,
          onClick = { organizerMode = 1 }
        ) {
          Text("manage")
        }

        Button(
          enabled = organizerMode == 0 || organizerMode == 1,
          onClick = { organizerMode = 2 }
        ) {
          Text("list")
        }
      }

      when (organizerMode) {
        0 -> {
          InsertPatientPane()
        }

        1 -> {
          ManageExamsTableResultsPane(patients)
        }

        else -> {
          Listing(patients)
        }
      }
    }
  }
}

fun main() = application {
  Window(
    onCloseRequest = ::exitApplication,
    state = WindowState(position = WindowPosition(Alignment.Center), size = DpSize(400.dp, 700.dp))
  ) {
    LaunchedEffect(true) {
      setupManagers(
        PatientsManager(),
        uiManager
      )
    }

    App()
  }
}
