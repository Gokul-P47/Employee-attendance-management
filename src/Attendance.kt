import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Attendance(
    val empId: String,
    val checkInDateTime: LocalDateTime,
){

    var checkOutDateTime: LocalDateTime? = null
        private set
    var workingHrs: Duration= Duration.ZERO
        private set

    fun checkOut(checkOutDateTime: LocalDateTime){
        this.checkOutDateTime=checkOutDateTime
        this.workingHrs=Duration.between(this.checkInDateTime,this.checkOutDateTime)
    }

    override fun toString(): String {
        val formatter= DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm")
        return buildString {
            appendLine("empId            : $empId")
            appendLine("CheckInDateTime  : ${checkInDateTime.format(formatter)}")
            appendLine("CheckOutDateTime : ${checkOutDateTime?.format(formatter)?:"N/A"}")
            appendLine("Working hours    : ${workingHrs.toHours()}h ${workingHrs.toMinutesPart()}m")
        }
    }
}