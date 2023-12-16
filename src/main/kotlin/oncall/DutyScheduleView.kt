package oncall
import camp.nextstep.edu.missionutils.Console

class DutyScheduleView {
    // 사용자에게 메시지 표시
    fun displayMessage(message: String) {
        println(message)
    }

    // 사용자 입력 받기
    fun getInput(): String {
        return Console.readLine()
    }

    // 근무 스케줄 표시
    fun displaySchedule(schedule: List<String>) {
        schedule.forEach { println(it) }
    }

    // 오류 메시지 표시
    fun displayError(message: String) {
        println("[ERROR] $message")
    }
}
