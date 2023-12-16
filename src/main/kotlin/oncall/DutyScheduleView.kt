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

    // 근무 스케줄을 문자열로 반환
    fun createScheduleString(schedule: List<String>): String {
        return schedule.joinToString(separator = "\n") { it.trim() }
    }

    // 근무 스케줄 표시 (콘솔에 출력)
    fun displaySchedule(schedule: List<String>) {
        println(createScheduleString(schedule))
    }


    // 오류 메시지 표시
    fun displayError(message: String) {
        println("$message")
    }
}
