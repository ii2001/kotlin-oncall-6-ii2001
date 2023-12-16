package oncall

class EmergencyDuty {
    private val holidays = setOf("1/1", "3/1", "5/5", "6/6", "8/15", "10/3", "10/9", "12/25")
    fun assignDuty(month: Int, startDay: String, weekdayDuties: MutableList<String>, weekendDuties: MutableList<String>): List<String> {
        val schedule = mutableListOf<String>()
        val totalDays = getDaysInMonth(month)
        val dayOffset = getDayOffset(startDay)
        var lastAssigned = ""
        for (day in 1..totalDays) {
            schedule.add(assignDutyForDay(month, day, dayOffset, lastAssigned, weekdayDuties, weekendDuties).also {
                lastAssigned = it.split(" ").last()
            })
        }

        return schedule
    }
    private fun assignDutyForDay(month: Int, day: Int, dayOffset: Int, lastAssigned: String, weekdayDuties: MutableList<String>, weekendDuties: MutableList<String>): String {
        val dayOfWeek = (day + dayOffset - 1) % 7
        val isHoliday = holidays.contains("${month}/${day}")
        val isWeekend = dayOfWeek == 5 || dayOfWeek == 6
        val dutyList = if (isWeekend || isHoliday) weekendDuties else weekdayDuties
        var currentDuty = preventionContinuousWork(lastAssigned, dutyList)
        val dateString = "${month}월 ${day}일 ${convertDayOfWeekToString(dayOfWeek)}" + if (isHoliday) "(휴일)" else ""

        dutyList.rotate()

        return "$dateString $currentDuty".trim()
    }
    // 연속 근무 방지 로직
    private fun preventionContinuousWork(lastAssigned: String,dutyList: MutableList<String>): String {
        var currentDuty = dutyList.first()
        if (currentDuty == lastAssigned) {
            // 첫 번째 근무자와 두 번째 근무자의 순서를 교환
            dutyList.swap(0, 1)
            currentDuty = dutyList.first()
        }
        return currentDuty
    }

    private fun MutableList<String>.swap(index1: Int, index2: Int) {
        val temp = this[index1]
        this[index1] = this[index2]
        this[index2] = temp
    }

    private fun MutableList<String>.rotate() {
        if (this.size > 1) {
            val first = this.removeAt(0)
            this.add(first)
        }
    }

// 주어진 월의 총 일수를 반환하는 함수
    private fun getDaysInMonth(month: Int): Int {
        return when (month) {
            2 -> 28
            4, 6, 9, 11 -> 30
            else -> 31
        }
    }

    // 시작 요일을 숫자로 변환하는 함수 (월요일: 0, 화요일: 1, ..., 일요일: 6)
    private fun getDayOffset(startDay: String): Int {
        return when (startDay) {
            "월" -> 0
            "화" -> 1
            "수" -> 2
            "목" -> 3
            "금" -> 4
            "토" -> 5
            "일" -> 6
            else -> throw IllegalArgumentException("잘못된 시작 요일")
        }
    }

    // 숫자를 요일 문자열로 변환하는 함수
    private fun convertDayOfWeekToString(dayOfWeek: Int): String {
        return when (dayOfWeek) {
            0 -> "월"
            1 -> "화"
            2 -> "수"
            3 -> "목"
            4 -> "금"
            5 -> "토"
            6 -> "일"
            else -> throw IllegalArgumentException("잘못된 요일")
        }
    }
}