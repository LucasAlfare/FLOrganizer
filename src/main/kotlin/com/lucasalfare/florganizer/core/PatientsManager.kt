package com.lucasalfare.florganizer.core

import androidx.compose.runtime.mutableStateListOf
import com.lucasalfare.fllistener.EventManageable
import com.lucasalfare.florganizer.core.io.storePatients

class PatientsManager : EventManageable() {

  private var tmpId = ""
  private val tmpExams = mutableListOf<Exam>()

  private val patients = mutableStateListOf<Patient>()

  override fun onNotInitiated() {
    this.initiated = true
  }

  override fun onInitiated() {
    println("${this.javaClass.name} is properly initiated.")
  }

  override fun onEvent(event: Any, data: Any?, origin: Any?) {
    when (event) {
      OrganizerEvents.PatientIdUpdate -> {
        tmpId = data as String
      }

      OrganizerEvents.ExamsUpdate -> {
        val examName = data as String

        if (tmpExams.none { it.name == examName }) {
          tmpExams += Exam(name = examName)
        } else {
          tmpExams.removeIf { it.name == examName }
        }
      }

      OrganizerEvents.ExamResultUpdate -> {
        val props = data as Array<*>
        val relatedPatientId = props[0] as String
        val targetExamName = props[1] as String
        val text = props[2] as String

        val targetPatient = patients.first { it.id == relatedPatientId }
        val targetExam = targetPatient.exams.first { it.name == targetExamName }
        targetExam.result = text

        notifyListeners(OrganizerEvents.PatientsUpdate, patients)
      }

      OrganizerEvents.CreatePatient -> {
        if (tmpId.isNotEmpty() && tmpExams.isNotEmpty()) {
          val nextExams = mutableListOf<Exam>()
          tmpExams.forEach { nextExams += it }

          val searchPatient = patients.firstOrNull { it.id == tmpId }
          if (searchPatient != null) {
            nextExams.forEach {
              if (!searchPatient.exams.contains(it)) {
                searchPatient.exams += it
              }
            }
          } else {
            patients += Patient(id = tmpId, exams = nextExams)
          }

          notifyListeners(OrganizerEvents.PatientsUpdate, patients)

          tmpId = ""
          tmpExams.clear()
        }
      }

      OrganizerEvents.RemovePatient -> {
        val patId = data as String
        patients.removeIf { it.id == patId }
        notifyListeners(OrganizerEvents.PatientsUpdate, patients)
      }

      OrganizerEvents.RemovePatientExam -> {
        val props = data as Array<*>
        val patientId = props[0] as String
        val examName = props[1] as String

        patients
          .first { it.id == patientId }
          .exams
          .removeIf { it.name == examName }

        notifyListeners(OrganizerEvents.PatientsUpdate, patients)
      }

      OrganizerEvents.SavePatients -> {
        storePatients(patients)
      }
    }
  }
}