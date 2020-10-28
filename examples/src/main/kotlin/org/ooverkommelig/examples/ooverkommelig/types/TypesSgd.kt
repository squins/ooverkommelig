package org.ooverkommelig.examples.ooverkommelig.types

import org.ooverkommelig.Once
import org.ooverkommelig.SubGraphDefinition
import org.ooverkommelig.req
import javax.swing.JOptionPane

@Suppress("MemberVisibilityCanBePrivate")
class TypesSgd : SubGraphDefinition() {

    val javaVendor by Once { System.getProperty("java.vendor") }

    val javaVersion by Once { System.getProperty("java.version") }

    val jvmReport by Once { "${req(javaVendor)} ${req(javaVersion)}" }

    val reportToStandardOutSender by Once<ReportSender> {
        object : ReportSender {
            override fun send(report: String) {
                println(report)
            }
        }
    }

    val reportToMessageDialogSender by Once<ReportSender> {
        object : ReportSender {
            override fun send(report: String) {
                JOptionPane.showMessageDialog(null, report)
            }
        }
    }

    val compoundReportSender by Once<ReportSender> {
        object : ReportSender {
            override fun send(report: String) {
                req(reportToStandardOutSender).send(report)
                req(reportToMessageDialogSender).send(report)
            }
        }
    }

    val outgoingReportLoggingSender by Once<ReportSender> {
        object : ReportSender {
            override fun send(report: String) {
                println("LOG: Outgoing report: $report")
                req(compoundReportSender).send(report)
            }
        }
    }.eager().wire {
        req(notifyingReportSender).addListener(it)
    }

    val notifyingReportSender by Once { NotifyingReportSender() }

    val mainRunnable by Once { MainRunnable(req(notifyingReportSender), req(jvmReport)) }
}
