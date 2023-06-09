package com.lucasalfare.florganizer.core

import com.lucasalfare.fllistener.AppEvent
import com.lucasalfare.fllistener.eventFactory
import java.text.SimpleDateFormat
import java.util.*


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

data class Exam(
  val name: String,
  var result: String = ""
)

data class Patient(
  val id: String,
  val exams: MutableList<Exam> = mutableListOf()
)

// TODO: fix
fun getCurrentDateTime(): Date {
  return Calendar.getInstance().time
}