package org.ooverkommelig.examples.ooverkommelig.types

import org.ooverkommelig.ProvidedBase
import org.ooverkommelig.Singleton
import org.ooverkommelig.SubGraphDefinition
import org.ooverkommelig.req
import javax.swing.JOptionPane

class TypesSgd(provided: Provided) : SubGraphDefinition(provided) {
    interface Provided : ProvidedBase

    val javaVendor by Singleton { System.getProperty("java.vendor") }

    val javaVersion by Singleton { System.getProperty("java.version") }

    val jvmReport by Singleton { "${req(javaVendor)} ${req(javaVersion)}" }

    val reportToStandardOutSender by Singleton<ReportSender> {
        object : ReportSender {
            override fun send(report: String) {
                println(report)
            }
        }
    }

    val reportToMessageDialogSender by Singleton<ReportSender> {
        object : ReportSender {
            override fun send(report: String) {
                JOptionPane.showMessageDialog(null, report)
            }
        }
    }

    val compoundReportSender by Singleton<ReportSender> {
        object : ReportSender {
            override fun send(report: String) {
                req(reportToStandardOutSender).send(report)
                req(reportToMessageDialogSender).send(report)
            }
        }
    }

    val outgoingReportLoggingSender by Singleton<ReportSender> {
        object : ReportSender {
            override fun send(report: String) {
                println("LOG: Outgoing report: $report")
                req(compoundReportSender).send(report)
            }
        }
    }.wire {
        req(notifyingReportSender).addListener(it)
    }

    val notifyingReportSender by Singleton { NotifyingReportSender() }

    val mainRunnable by Singleton { MainRunnable(req(notifyingReportSender), req(jvmReport)) }

    override fun objectsToCreateEagerly() = listOf(outgoingReportLoggingSender)
}
