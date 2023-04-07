package com.lucasalfare.florganizer.ui.inserting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.lucasalfare.florganizer.ExamsUpdate
import com.lucasalfare.florganizer.PatientsUpdate
import com.lucasalfare.florganizer.uiManager

@Composable
fun ExamCheckBox(exam: String) {
  var checked by remember { mutableStateOf(false) }

  DisposableEffect(true) {
    val callback = uiManager.addCallback { event, data ->
      when (event) {
        PatientsUpdate -> {
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
        uiManager.notifyListeners(ExamsUpdate, exam)
      })
      Text(exam)
    }
  }
}