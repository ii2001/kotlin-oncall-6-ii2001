package oncall

class EmergencyDuty {
    fun assignDuty(month: Int, startDay: String, weekdayDuties: List<String>, weekendDuties: List<String>): List<String> {
        val schedule = mutableListOf<String>()
        val calendar = createCalendar(month, startDay)

        val weekdayIterator = weekdayDuties.iterator().withCycle()
        val weekendIterator = weekendDuties.iterator().withCycle()

        for (date in calendar) {
            val isWeekend = date.isWeekendOrHoliday()
            val nextDuty = if (isWeekend) weekendIterator.next() else weekdayIterator.next()

            // 연속 근무 방지 로직
            if (!schedule.isEmpty() && schedule.last().endsWith(nextDuty)) {
                if (isWeekend) {
                    schedule.add(date to weekendIterator.next())
                } else {
                    schedule.add(date to weekdayIterator.next())
                }
            } else {
                schedule.add(date to nextDuty)
            }
        }

        return schedule.map { (date, duty) -> "$date $duty" }
    }

    private fun createCalendar(month: Int, startDay: String): List<String> {
        // 주어진 월과 시작 요일에 따라 날짜 목록 생성
    }

    private fun String.isWeekendOrHoliday(): Boolean {
        // 주말 또는 공휴일 여부 확인
    }

    private fun <T> Iterator<T>.withCycle(): Iterator<T> {
        // 순환 반복자 생성
    }
}
