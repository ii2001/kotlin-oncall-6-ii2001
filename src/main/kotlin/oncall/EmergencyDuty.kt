package oncall

class EmergencyDuty {
    private val holidays = setOf("1/1", "3/1", "5/5", "6/6", "8/15", "10/3", "10/9", "12/25")

    // 비상 근무 스케줄을 할당하는 함수
    fun assignDuty(month: Int, startDay: String, weekdayDuties: List<String>, weekendDuties: List<String>): List<String> {
        val schedule = mutableListOf<String>()
        val totalDays = getDaysInMonth(month)
        val dayOffset = getDayOffset(startDay)

        var weekdayIndex = 0
        var weekendIndex = 0
        var lastAssigned = "" // 마지막으로 할당된 근무자를 추적

        for (day in 1..totalDays) {
            val dayOfWeek = (day + dayOffset - 1) % 7
            val date = "${month}/${day}"
            val isHoliday = holidays.contains(date)
            val isWeekend = dayOfWeek == 5 || dayOfWeek == 6

            // 현재 날짜에 할당할 근무자 결정
            var currentDuty = if (isWeekend || isHoliday) {
                weekendDuties[weekendIndex % weekendDuties.size]
            } else {
                weekdayDuties[weekdayIndex % weekdayDuties.size]
            }

            // 연속 근무 방지 로직
            if (currentDuty == lastAssigned) {
                if (isWeekend || isHoliday) {
                    currentDuty = weekendDuties[++weekendIndex % weekendDuties.size]
                } else {
                    currentDuty = weekdayDuties[++weekdayIndex % weekdayDuties.size]
                }
            }

            // 다음 날짜에 사용하기 위해 마지막으로 할당된 근무자 업데이트
            lastAssigned = currentDuty

            // 날짜 문자열 생성
            val dateString = "${month}월 ${day}일 ${convertDayOfWeekToString(dayOfWeek)}"
            val holidayNote = if (isHoliday) "(휴일)" else ""
            schedule.add("$dateString$holidayNote $currentDuty")
        }

        return schedule
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