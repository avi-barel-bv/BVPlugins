package com.bluevine.plugins.bvplugins.utils

import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.openapi.externalSystem.model.ProjectSystemId
import com.intellij.openapi.externalSystem.model.execution.ExternalSystemTaskExecutionSettings
import com.intellij.openapi.externalSystem.service.execution.ProgressExecutionMode
import com.intellij.openapi.externalSystem.util.ExternalSystemUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

fun Project?.runGradleTask(taskName: String, vararg taskParameters: Pair<String, String>) {
    this?.let {
        runTask(taskName, *taskParameters)
    } ?: run {
        displayProjectError()
    }
}

private fun Project.runTask(taskName: String, vararg taskParameters: Pair<String, String>) {
    val settings = ExternalSystemTaskExecutionSettings()
    settings.externalProjectPath = this.basePath
    settings.taskNames = listOf(taskName)
    settings.scriptParameters = taskParameters.joinToString(" ") { "-P ${it.first}=${it.second}" }
    settings.externalSystemIdString = ProjectSystemId("GRADLE").id

    ExternalSystemUtil.runTask(
            settings, DefaultRunExecutor.EXECUTOR_ID, this, ProjectSystemId("GRADLE"),
            null, ProgressExecutionMode.IN_BACKGROUND_ASYNC, false)
}

fun displayProjectError() {
    Messages.showMessageDialog(
            null,
            "EMPTY PROJECT",
            "ERROR",
            Messages.getErrorIcon()
    )
}
