package oncall

class DutyController {
    private val model = EmergencyDuty()
    private val view = DutyScheduleView()

    fun start() {
        while (true) {
            try {
                // 월과 시작 요일 입력 받기
                view.displayMessage("비상 근무를 배정할 월과 시작 요일을 입력하세요> ")
                val (month, startDay) = parseMonthAndStartDay(view.getInput())

                // 평일 근무자 순서 입력 받기
                view.displayMessage("평일 비상 근무 순서대로 사원 닉네임을 입력하세요> ")
                val weekdayDutyRoster = parseDutyRoster(view.getInput())

                // 주말 근무자 순서 입력 받기
                view.displayMessage("휴일 비상 근무 순서대로 사원 닉네임을 입력하세요> ")
                val weekendDutyRoster = parseDutyRoster(view.getInput())

                // 근무 스케줄 생성
                val schedule = model.assignDuty(month, startDay, weekdayDutyRoster, weekendDutyRoster)

                // 스케줄 표시
                view.displaySchedule(schedule)

                break
            } catch (e: IllegalArgumentException) {
                // 유효하지 않은 입력 처리
                view.displayError("[ERROR] 유효하지 않은 입력 값입니다. 다시 입력해 주세요.")
            }
        }
    }

    private fun parseMonthAndStartDay(input: String): Pair<Int, String> {
        val validDays = setOf("월", "화", "수", "목", "금", "토", "일")
        val parts = input.split(",").map { it.trim() }
        if (parts.size != 2 || parts[0].toIntOrNull() !in 1..12 || parts[1] !in validDays) {
            throw IllegalArgumentException("잘못된 월 또는 시작 요일 형식입니다.")
        }
        val month = parts[0].toInt()
        val startDay = parts[1]
        return month to startDay
    }

    private fun parseDutyRoster(input: String): MutableList<String> {
        val roster = input.split(",").map { it.trim() }
        if (roster.size !in 5..35) {
            throw IllegalArgumentException("사원 수가 5명에서 35명 사이여야 합니다.")
        }
        return roster.toMutableList()
    }

}