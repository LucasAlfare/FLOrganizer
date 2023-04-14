package com.lucasalfare.florganizer// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.lucasalfare.fllistener.UIManager
import com.lucasalfare.fllistener.setupManagers
import com.lucasalfare.florganizer.core.OrganizerEvents
import com.lucasalfare.florganizer.core.Patient
import com.lucasalfare.florganizer.core.PatientsManager
import com.lucasalfare.florganizer.ui.inserting.InsertPatientPane
import com.lucasalfare.florganizer.ui.listing.Listing
import com.lucasalfare.florganizer.ui.managing.ManageExamsTableResultsPane


val uiManager = UIManager()

@Suppress("UNCHECKED_CAST")
@Composable
fun App() {
  MaterialTheme {
    // 0=inserting; 1=managing; 2=listing
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

    Column(modifier = Modifier.fillMaxSize()) {
      Row(modifier = Modifier.padding(4.dp)) {
        Button(
          modifier = Modifier.padding(4.dp),
          enabled = organizerMode == 1 || organizerMode == 2,
          onClick = { organizerMode = 0 }
        ) {
          Text("inserir")
        }

        Button(
          modifier = Modifier.padding(4.dp),
          enabled = organizerMode == 0 || organizerMode == 2,
          onClick = { organizerMode = 1 }
        ) {
          Text("gerenciar")
        }

        Button(
          modifier = Modifier.padding(4.dp),
          enabled = organizerMode == 0 || organizerMode == 1,
          onClick = { organizerMode = 2 }
        ) {
          Text("ver lista")
        }
      }

      Box(modifier = Modifier.padding(4.dp)) {
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
}

fun main() = application {
  Window(
    onCloseRequest = ::exitApplication,
    state = WindowState(
      placement = WindowPlacement.Maximized,
      position = WindowPosition(Alignment.Center),
      size = DpSize(400.dp, 700.dp)
    )
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
