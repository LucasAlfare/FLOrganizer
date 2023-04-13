package com.lucasalfare.florganizer

import com.lucasalfare.fllistener.AppEvent
import com.lucasalfare.fllistener.eventFactory

enum class OrganizerEvents(val appEvent: AppEvent) {
  PatientIdUpdate(eventFactory("PatientIdUpdate")),
  ExamsUpdate(eventFactory("ExamsUpdate")),
  CreatePatient(eventFactory("CreatePatient")),
  RemovePatient(eventFactory("RemovePatient")),
  RemovePatientExam(eventFactory("RemovePatientExam")),
  PatientsUpdate(eventFactory("PatientsUpdate")),
  ExamResultUpdate(eventFactory("ExamResultUpdate")),
  SavePatients(eventFactory("SavePatients"))
}

val exams = mutableListOf(
  "BHC",
  "HAG",
  "HCV",
  "HIV",
  "VDR",
  "BRU",
  "Widal",
  "Dengue (IgG/IgM)",
  "GRH"
)

data class Exam(val name: String, var result: String = "")

data class Patient(val id: String, val exams: MutableList<Exam> = mutableListOf())