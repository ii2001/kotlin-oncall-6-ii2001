package oncall

class EmergencyDuty {
    fun assignDuty(month: Int, startDay: String, weekdayDuties: List<String>, weekendDuties: List<String>): List<String> {
        val schedule = mutableListOf<String>()
        val totalDays = getDaysInMonth(month)
        val dayOffset = getDayOffset(startDay)

        var weekdayIndex = 0
        var weekendIndex = 0

        for (day in 1..totalDays) {
            val dayOfWeek = (day + dayOffset - 1) % 7
            val isWeekend = dayOfWeek == 5 || dayOfWeek == 6 // 토요일(5) 또는 일요일(6)인 경우

            val currentDuty = if (isWeekend) weekendDuties[weekendIndex % weekendDuties.size] else weekdayDuties[weekdayIndex % weekdayDuties.size]

            // 연속 근무 방지
            if (schedule.isNotEmpty() && schedule.last().endsWith(currentDuty)) {
                if (isWeekend) {
                    weekendIndex++
                } else {
                    weekdayIndex++
                }

                continue
            }

            val dateString = "${month}월 ${day}일 ${convertDayOfWeekToString(dayOfWeek)}"
            schedule.add("$dateString $currentDuty")

            if (isWeekend) {
                weekendIndex++
            } else {
                weekdayIndex++
            }
        }

        return schedule
    }

    private fun getDaysInMonth(month: Int): Int {
        // 2월은 28일, 나머지는 30 또는 31일로 계산
        return when (month) {
            2 -> 28
            4, 6, 9, 11 -> 30
            else -> 31
        }
    }

    private fun getDayOffset(startDay: String): Int {
        // 요일에 따라 숫자로 변환 (월요일: 0, 화요일: 1, ..., 일요일: 6)
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

    private fun convertDayOfWeekToString(dayOfWeek: Int): String {
        // 숫자를 요일 문자열로 변환
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
