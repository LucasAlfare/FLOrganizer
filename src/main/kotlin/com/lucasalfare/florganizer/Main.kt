package com.lucasalfare.florganizer// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.lucasalfare.fllistener.UIManager
import com.lucasalfare.fllistener.setupManagers
import com.lucasalfare.florganizer.ui.inserting.InsertPatientPane
import com.lucasalfare.florganizer.ui.managing.ManageExamsPane


val uiManager = UIManager()

@Suppress("UNCHECKED_CAST")
@Composable
fun App() {
  MaterialTheme {
    var inserting by remember { mutableStateOf(true) }
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
          enabled = !inserting,
          onClick = { inserting = true }
        ) {
          Text("insert")
        }

        Button(
          enabled = inserting,
          onClick = { inserting = false }
        ) {
          Text("manage")
        }
      }

      if (inserting) {
        InsertPatientPane()
      } else {
        ManageExamsPane(patients)
      }
    }
  }
}

fun main() = application {
  Window(onCloseRequest = ::exitApplication) {
    LaunchedEffect(true) {
      setupManagers(
        PatientsManager(),
        uiManager
      )
    }

    App()
  }
}
