package com.lucasalfare.florganizer.core.io

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.lucasalfare.flbinary.Reader
import com.lucasalfare.flbinary.Writer
import com.lucasalfare.florganizer.core.Exam
import com.lucasalfare.florganizer.core.Patient
import com.lucasalfare.florganizer.core.getCurrentDateTime
import java.io.File
import java.nio.file.Files
import java.text.SimpleDateFormat
import kotlin.io.path.Path


private const val FILE_NAME = "florganizer.data"

@OptIn(ExperimentalUnsignedTypes::class)
fun storePatients(patients: SnapshotStateList<Patient>) {
  val writer = Writer()

  writer.write4Bytes(getCurrentDateTime().time)
  writer.write2Bytes(patients.size)

  patients.forEach { patient ->
    writer.write1Byte(patient.id.length)
    writer.writeString(patient.id)

    writer.write1Byte(patient.exams.size)

    patient.exams.forEach { exam ->
      writer.write1Byte(exam.name.length)
      writer.writeString(exam.name)
      writer.write1Byte(exam.result.length)
      writer.writeString(exam.result)
    }
  }

  Files.deleteIfExists(Path(FILE_NAME))

  val file = File(FILE_NAME)
  file.writeBytes(writer.getData().toByteArray())
}

@OptIn(ExperimentalUnsignedTypes::class)
fun loadPatients(): SnapshotStateList<Patient> {
  val file = File(FILE_NAME)
  val reader: Reader
  val res = mutableStateListOf<Patient>()

  if (file.exists()) {
    reader = Reader(file.readBytes().toUByteArray())
  } else {
    return res
  }

  val routineDate = reader.read4Bytes()
  println("Reading the routine of date ${SimpleDateFormat("dd/MM/YYYY").format(getCurrentDateTime().time - routineDate)}...")

  val nPatients = reader.read2Bytes()
  var i = 0
  while (i < nPatients) {
    val patientIdLength = reader.read1Byte()
    val patientId = reader.readString(patientIdLength)!!
    val nextPatient = Patient(id = patientId)
    val nExams = reader.read1Byte()

    var j = 0
    while (j < nExams) {
      val examNameLength = reader.read1Byte()
      val examName = reader.readString(examNameLength)!!
      val examResultLength = reader.read1Byte()
      val examResult = reader.readString(examResultLength)!!

      nextPatient.exams += Exam(name = examName, result = examResult)

      j++
    }

    res += nextPatient

    i++
  }

  return res
}
