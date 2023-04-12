package com.lucasalfare.florganizer

import com.lucasalfare.fllistener.EventManageable

class PatientsManager : EventManageable() {

  private var tmpId = ""
  private val tmpExams = mutableListOf<Exam>()
  private val patients = mutableListOf<Patient>()

  override fun onNotInitiated() {
    this.initiated = true
  }

  override fun onInitiated() {

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
          patients += Patient(id = tmpId, exams = nextExams)

          notifyListeners(OrganizerEvents.PatientsUpdate, patients)

          tmpId = ""
          tmpExams.clear()
        }
      }
    }
  }
}