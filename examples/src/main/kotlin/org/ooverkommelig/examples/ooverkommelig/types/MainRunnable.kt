package org.ooverkommelig.examples.ooverkommelig.types

class MainRunnable(private val reportSender: ReportSender, private val report: String) : Runnable {
    override fun run() {
        reportSender.send(report)
    }
}
