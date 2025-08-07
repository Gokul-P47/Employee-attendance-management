import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class EmployeeManager {
    companion object {
        val employeeList = EmployeeList() //To store employees
        private val attendanceList = AttendanceList()  //To store attendance records
    }

    init {
        addInitialEmployees()
    }

    fun addInitialEmployees() {
        employeeList.addAll(
            listOf(
                Employee( "Gokul", "P", Role.DEVELOPER, Manager.PIEQM1001),
                Employee( "Mark", "Lee", Role.DEVELOPER, Manager.PIEQM1001),
                Employee( "Jack", "Lee", Role.INTERN, Manager.PIEQM1003),
                Employee( "Ashok", "Kumar", Role.DESIGNER, Manager.PIEQM1002),
                Employee( "Bob", "John", Role.DESIGNER,Manager.PIEQM1003),
                Employee("Tim", "David", Role.INTERN, Manager.PIEQM1003)
            )
        )
        employeeList.forEach { it.generateEmpId() }
    }

    //Add new Employee
    fun addEmployee(employee: Employee) :String{
        //Validate the employee object and add it to the list if it is valid
        if(employee.validate()){
            employeeList.add(employee)
            return "Employee Added SuccessFully!\n$employee"
        }
        return employee.getErrorMessage()+". Failed to add an employee"
    }

    //Remove an employee
    fun deleteEmployee(empId: String): Boolean{
       return employeeList.delete(empId)
    }


    fun getEmployeesList(): String {
        return employeeList.toString()
    }

    fun checkIn(empId: String, checkInDateTime: String): String {
        if (employeeList.employeeExists(empId)==null){
            return "Employee ID not found. Check in failed."  //Employee not found with the given id
        }

       val parsedDateTime = validateDateTime(checkInDateTime)

        if (parsedDateTime == null) {
          return "Invalid dateTime. Check in failed."
        }

        if(attendanceList.add(empId, parsedDateTime)){
            val formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
            return "Check in successful!\nEmployee Id: $empId DateTime: ${parsedDateTime.format(formatter)}"
        }

        return "Employee has already checked in today. Check in failed."
    }

    private fun validateDateTime(inputDateTime: String): LocalDateTime?{
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")

        return if (inputDateTime.isEmpty()) {
            LocalDateTime.now()
        } else {
            try {
                val dateTime = LocalDateTime.parse(inputDateTime, formatter)
                if (dateTime.isAfter(LocalDateTime.now())) {  //Future date time
                    null
                } else dateTime
            } catch (e:DateTimeException) {  //Invalid format
                null
            }
        }
    }

    fun checkOut(empId: String,checkOutDateTime: String): String{
        if (employeeList.employeeExists(empId)==null){
            return "Employee ID not found. Check out failed."     //Employee not found with the given id
        }

        val parsedCheckOutDateTime= validateDateTime(checkOutDateTime)
        if (parsedCheckOutDateTime == null) {
            return "Invalid dateTime. Check out failed."
        }

        val attendance: Attendance?= attendanceList.validateCheckOut(empId,parsedCheckOutDateTime)
        if(attendance== null){
            return "No valid check-in yet"    //Invalid check-out
        }

        attendance.checkOut(parsedCheckOutDateTime)  //Valid check out
        return "Check out successful!\n$attendance"
    }


    fun getAttendanceList(): String{
       return attendanceList.toString()
    }

    //Return list of Attendances, where checked in only, no checked out.
    fun getIncompleteAttendances(): List<Attendance> {
        return attendanceList.filter { it.checkOutDateTime == null }
    }

    //Returns cumulative working hrs of employees between the given dates
    fun getTotalWorkingHrsBetween(startingDate: String,endingDate: String): List<String>?{
        val startDate = parseDate(startingDate)
        if(startDate==null){
            return null
        }

        val endDate = parseDate(endingDate)
        if(endDate==null){
            return null
        }

        return attendanceList.summaryOfWorkingHrs(startDate,endDate)
    }

    fun parseDate(input: String): LocalDate? {
        val formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return try {
            LocalDate.parse(input, formatter)
        } catch (e: DateTimeParseException) {
            null
        }
    }

}
