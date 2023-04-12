package com.lucasalfare.florganizer// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.lucasalfare.fllistener.UIManager
import com.lucasalfare.fllistener.setupManagers
import com.lucasalfare.florganizer.ui.inserting.InsertPatientPane
import com.lucasalfare.florganizer.ui.managing.ManageExamsPane


val uiManager = UIManager()

@Composable
fun App() {
  var inserting by remember { mutableStateOf(true) }
  var patients = remember { mutableStateListOf<Patient>() }

  DisposableEffect(true) {
    val callback = uiManager.addCallback { event, data ->
      when (event) {
        OrganizerEvents.PatientsUpdate -> {
          patients.clear()
          val pat = data as MutableList<Patient>
          println(pat)
          patients.addAll(pat)
        }

        else -> {}
      }
    }

    onDispose { uiManager.removeCallback(callback) }
  }

  MaterialTheme {
    Column {
      Box {
        Row {
          Button(
            enabled = !inserting,
            onClick = {
              inserting = true
            }
          ) {
            Text("Cadastrar")
          }

          Button(
            enabled = inserting,
            onClick = {
              inserting = false
            }
          ) {
            Text("Ver")
          }
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
