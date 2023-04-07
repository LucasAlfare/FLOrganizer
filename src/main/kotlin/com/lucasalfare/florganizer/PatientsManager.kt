package com.lucasalfare.florganizer

import com.lucasalfare.fllistener.AppEvent
import com.lucasalfare.fllistener.EventManageable

class PatientsManager : EventManageable() {

  private val patients = mutableListOf<Patient>()

  private var tmpId = ""
  private val tmpExams = mutableListOf<String>()

  override fun init() {

  }

  override fun onEvent(event: AppEvent, data: Any?, origin: Any?) {
    when (event) {
      PatientIdUpdate -> {
        tmpId = data as String
      }

      ExamsUpdate -> {
        val exam = data as String
        if (!tmpExams.contains(exam)) {
          tmpExams += exam
        } else {
          tmpExams -= exam
        }
      }

      CreatePatient -> {
        if (tmpId.isNotEmpty() && tmpExams.isNotEmpty()) {
          val nextExams = mutableListOf<String>()
          tmpExams.forEach { nextExams += it }
          patients += Patient(id = tmpId, exams = nextExams)

          println()
          patients.forEach {
            println(it)
          }

          notifyListeners(PatientsUpdate, patients)

          tmpId = ""
          tmpExams.clear()
        }
      }
    }
  }
}