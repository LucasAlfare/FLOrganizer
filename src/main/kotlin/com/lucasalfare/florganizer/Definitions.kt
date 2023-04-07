package com.lucasalfare.florganizer

import com.lucasalfare.fllistener.AppEvent

val PatientIdUpdate = AppEvent("com.lucasalfare.florganizer.getPatientIdUpdate")
val ExamsUpdate = AppEvent("com.lucasalfare.florganizer.getExamsUpdate")
val CreatePatient = AppEvent("com.lucasalfare.florganizer.getCreatePatient")
val RemovePatient = AppEvent("com.lucasalfare.florganizer.getRemovePatient")
val PatientsUpdate = AppEvent("com.lucasalfare.florganizer.getPatientsUpdate")

val exams = mutableListOf(
  "BHC", "HAG", "HCV", "HIV", "VDR", "GRH"
)

data class Patient(val id: String, val exams: MutableList<String>)