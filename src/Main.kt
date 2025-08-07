
fun main() {
    val employeeManager= EmployeeManager()

    while (true) {
        println("1.Add Employee")
        println("2.Delete Employee")
        println("3.Get Employees List")
        println("4.Check In")
        println("5.Check Out")
        println("6.Get Attendance List")
        println("7.Get Incomplete Attendances")
        println("8.Get Total Working Hours Between two Dates")
        println("9.Delete Attendance Entry")
        println("10.Exit")
        print("Choose an option: ")
        when (readln().toIntOrNull()) {
            1->{
                addEmployee(employeeManager)
            }
            2->{
                deleteEmployee(employeeManager)
            }
            3 -> {
                getEmployeeList(employeeManager)
            }
            4 -> {
                checkIn(employeeManager)
            }
            5 -> {
                checkOut(employeeManager)
            }
            6 -> {
                getAttendanceList(employeeManager)
            }
            7->{
                getIncompleteAttendance(employeeManager)
            }
            8->{
                getTotalWorkingHrsBetweenDate(employeeManager)
            }
            9->{
                deleteAttendanceEntry(employeeManager)
            }
            10->{
                break
            }
            else -> println("Invalid option")
        }
        println()
    }
}


fun addEmployee(employeeManager: EmployeeManager){
    println("First name: ")
    val empFirstName=readln()
    println("Last name: ")
    val empLastName=readln()
    println("Role: ")
    val empRole=readln()
    println("Reporting to: ")
    val reportingTo=readln()
    val employee=Employee(empFirstName,empLastName,Role.from(empRole),Manager.from(reportingTo))
    println(employeeManager.addEmployee(employee))
}

fun deleteEmployee(employeeManager: EmployeeManager){
    println("Enter employee ID to be deleted")
    val empId=readln()
    if(employeeManager.deleteEmployee(empId)){
        println("Employee deleted successfully!")
    }
    else{
        println("Employee ID not found. Failed to delete")
    }
}

fun getEmployeeList(employeeManager: EmployeeManager){
    println("----- Employee List -----")
    println(employeeManager.getEmployeesList())
}

fun checkIn(employeeManager: EmployeeManager){
    print("Enter employee ID: ")
    val empId = readln().trim()

    print("Enter date and time (dd-MM-yyyy HH:mm) : ")
    val inputDateTime = readln().trim()

    println(employeeManager.checkIn(empId, inputDateTime))
}

fun checkOut(employeeManager: EmployeeManager){
    print("Enter Employee ID: ")
    val empId = readln().trim()

    print("Enter date and time (dd-MM-yyyy HH:mm) : ")
    val inputDateTime = readln().trim()

    println(employeeManager.checkOut(empId, inputDateTime))
}

fun getAttendanceList(employeeManager: EmployeeManager){
    println("----- Attendance List -----")
    println(employeeManager.getAttendanceList())
}

fun getIncompleteAttendance(employeeManager: EmployeeManager){
    println("Incomplete attendances")
    val incompleteAttendances=employeeManager.getIncompleteAttendances()
    if(incompleteAttendances.isEmpty()){
        println("No incomplete attendances")
    }
    else{
        incompleteAttendances.forEach{println(it)}
    }
}

fun getTotalWorkingHrsBetweenDate(employeeManager: EmployeeManager){
    print("Enter starting date(dd-MM-yyyy): ")
    val startingDate=readln().trim()
    print("Enter ending date(dd-MM-yyyy): ")
    val endingDate=readln().trim()

    val result=employeeManager.getTotalWorkingHrsBetween(startingDate,endingDate)
    if(result==null){
        println("Invalid starting or ending date. Failed to get Details")
    }
    else {
        println("Employee working hours")
        println("Employee Id | Working hours")
        result.forEach { println(it) }
    }
}

fun deleteAttendanceEntry(employeeManager: EmployeeManager){
    print("Enter employee id:")
    val empId=readln().trim()
    print("Enter date (dd-MM-yyyy):")
    val date=readln().trim()
    println(employeeManager.deleteAttendanceEntry(empId,date))
}