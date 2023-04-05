val exams = mutableListOf(
  "BHC", "HAG", "HCV", "HIV", "VDR"
)

data class Patient(val id: String, val exams: MutableList<String>)