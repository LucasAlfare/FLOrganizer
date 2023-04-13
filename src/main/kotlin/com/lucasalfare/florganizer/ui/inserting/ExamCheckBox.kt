package com.lucasalfare.florganizer.ui.inserting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lucasalfare.florganizer.OrganizerEvents
import com.lucasalfare.florganizer.uiManager

@Composable
fun ExamCheckBox(exam: String) {
  var checked by remember { mutableStateOf(false) }

  DisposableEffect(true) {
    val callback = uiManager.addCallback { event, _ ->
      when (event) {
        OrganizerEvents.PatientsUpdate -> {
          checked = false
        }

        else -> {}
      }
    }

    onDispose { uiManager.removeCallback(callback) }
  }

  Box(modifier = Modifier
    .fillMaxSize()
    .clickable {
      checked = !checked
      notify(exam)
    }
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically
    ) {
      Checkbox(checked, onCheckedChange = {
        checked = it
        notify(exam)
      })
      Text(exam)
    }
  }
}

private fun notify(exam: String) {
  uiManager.notifyListeners(OrganizerEvents.ExamsUpdate, exam)
}