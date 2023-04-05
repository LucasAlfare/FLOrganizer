// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


var tmpId = ""
val tmpExams = mutableListOf<String>()
val patients = mutableListOf<Patient>()

val uiManager = UIManager()


@Composable
fun MyTextField() {
  var currentPatientId by remember { mutableStateOf("") }

  DisposableEffect(true) {
    val callback = uiManager.addCallback { event, data ->
      when (event) {
        AppEvent.PatientsUpdate -> {
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
        uiManager.notifyListeners(AppEvent.PatientIdUpdate, currentPatientId)
      })
    }
  }
}

@Composable
fun ExamCheckBox(exam: String) {
  var checked by remember { mutableStateOf(false) }

  DisposableEffect(true) {
    val callback = uiManager.addCallback { event, data ->
      when (event) {
        AppEvent.PatientsUpdate -> {
          checked = false
        }

        else -> {}
      }
    }

    onDispose { uiManager.removeCallback(callback) }
  }

  Box {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Checkbox(checked, onCheckedChange = {
        checked = it
        uiManager.notifyListeners(AppEvent.ExamsUpdate, exam)
      })
      Text(exam)
    }
  }
}

@Composable
fun ExamsPad(exams: MutableList<String>) {
  LazyColumn(modifier = Modifier.background(Color.LightGray)) {
    exams.forEach { exam ->
      item {
        ExamCheckBox(exam)
      }
    }
  }
}

@Composable
fun App() {
  MaterialTheme {
    Column(modifier = Modifier.fillMaxSize()) {
      Box(modifier = Modifier.weight(0.5f)) {
        MyTextField()
      }

      Box(modifier = Modifier.weight(1.5f)) {
        ExamsPad(exams)
      }

      Box(modifier = Modifier.weight(0.5f)) {
        Button(onClick = {
         uiManager.notifyListeners(AppEvent.CreatePatient)
        }) {
          Text("adicionar")
        }
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
