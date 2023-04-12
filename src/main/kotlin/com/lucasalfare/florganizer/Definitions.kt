package com.lucasalfare.florganizer

import com.lucasalfare.fllistener.AppEvent
import com.lucasalfare.fllistener.eventFactory

enum class OrganizerEvents(val appEvent: AppEvent) {
  PatientIdUpdate(eventFactory("PatientIdUpdate")),
  ExamsUpdate(eventFactory("ExamsUpdate")),
  CreatePatient(eventFactory("CreatePatient")),
  RemovePatient(eventFactory("RemovePatient")),
  PatientsUpdate(eventFactory("PatientsUpdate")),
  ExamResultUpdate(eventFactory("ExamResultUpdate"))
}

val exams = mutableListOf(
  "BHC", "HAG", "HCV", "HIV", "VDR", "GRH"
)

data class Exam(val name: String, var result: String = "")

data class Patient(val id: String, val exams: MutableList<Exam> = mutableListOf())