package org.ooverkommelig.examples.ooverkommelig.types

class NotifyingReportSender : ReportSender {
    private val listeners = mutableListOf<ReportSender>()

    fun addListener(listener: ReportSender) {
        listeners += listener
    }
    override fun send(report: String) {
        listeners.forEach { listener ->
            println("Notifying: ${listener.javaClass.name}")
            listener.send(report) }
    }
}
