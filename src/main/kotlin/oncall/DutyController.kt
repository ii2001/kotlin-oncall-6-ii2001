package oncall

class DutyController {
    private val model = EmergencyDuty()
    private val view = DutyScheduleView()

    fun start() {
        var month: Int
        var startDay: String
        var weekdayDutyRoster: MutableList<String>
        var weekendDutyRoster: MutableList<String>

        // 월과 시작 요일 입력 받기
        while (true) {
            try {
                view.displayMessage("비상 근무를 배정할 월과 시작 요일을 입력하세요> ")
                val monthAndStartDay = parseMonthAndStartDay(view.getInput())
                month = monthAndStartDay.first
                startDay = monthAndStartDay.second
                break
            } catch (e: IllegalArgumentException) {
                // 유효하지 않은 입력 처리
                view.displayError("[ERROR] 유효하지 않은 입력 값입니다. 다시 입력해 주세요.")
            }
        }

// 평일 근무자 순서와 휴일 근무자 순서를 입력 받기
        while (true) {
            try {
                view.displayMessage("평일 비상 근무 순번대로 사원 닉네임을 입력하세요> ")
                weekdayDutyRoster = parseDutyRoster(view.getInput())

                view.displayMessage("휴일 비상 근무 순번대로 사원 닉네임을 입력하세요> ")
                weekendDutyRoster = parseDutyRoster(view.getInput())

                if (weekdayDutyRoster.isNotEmpty() && weekendDutyRoster.isNotEmpty()) {
                    break
                } else {
                    view.displayError("[ERROR] 유효하지 않은 입력 값입니다. 다시 입력해 주세요.")
                }
            } catch (e: IllegalArgumentException) {
                // 유효하지 않은 입력 처리
                view.displayError("[ERROR] 유효하지 않은 입력 값입니다. 다시 입력해 주세요.")
            }
        }


        // 근무 스케줄 생성
        val schedule = model.assignDuty(month, startDay, weekdayDutyRoster, weekendDutyRoster)

        // 스케줄 표시
        view.displaySchedule(schedule)
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

        // 중복된 닉네임 확인
        val uniqueRoster = roster.distinct()

        if (roster.size != uniqueRoster.size || roster.size !in 5..35 || roster.any { it.length >= 6 }) {
            throw IllegalArgumentException("사원 수가 5명에서 35명 사이이며, 사원 닉네임은 6글자 미만이고 중복되지 않아야 합니다.")
        }

        return uniqueRoster.toMutableList()
    }
}
