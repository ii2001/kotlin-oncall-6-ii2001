package oncall

class DutyController {
    private val model = EmergencyDuty()
    private val view = DutyScheduleView()

    fun start() {
        while (true) {
            try {
                // 월과 시작 요일 입력 받기
                view.displayMessage("월과 시작 요일을 입력하세요 (예: 5,월):")
                val (month, startDay) = parseMonthAndStartDay(view.getInput())

                // 평일 근무 로스터 입력 받기
                view.displayMessage("평일 근무 순번을 쉼표로 구분하여 입력하세요:")
                val weekdayDutyRoster = parseDutyRoster(view.getInput())

                // 주말 근무 로스터 입력 받기
                view.displayMessage("주말 근무 순번을 쉼표로 구분하여 입력하세요:")
                val weekendDutyRoster = parseDutyRoster(view.getInput())

                // 근무 스케줄 생성
                val schedule = model.assignDuty(month, startDay, weekdayDutyRoster, weekendDutyRoster)

                // 스케줄 표시
                view.displaySchedule(schedule)

                break
            } catch (e: Exception) {
                // 오류 처리 (예: 잘못된 입력)
                view.displayError(e.message ?: "오류가 발생했습니다")
            }
        }
    }

    private fun parseMonthAndStartDay(input: String): Pair<Int, String> {
        val parts = input.split(",").map { it.trim() }
        if (parts.size != 2) throw IllegalArgumentException("월과 시작 요일 형식이 잘못되었습니다.")
        val month = parts[0].toIntOrNull() ?: throw IllegalArgumentException("잘못된 월입니다.")
        val startDay = parts[1]
        return month to startDay
    }

    private fun parseDutyRoster(input: String): List<String> {
        return input.split(",").map { it.trim() }
    }
}