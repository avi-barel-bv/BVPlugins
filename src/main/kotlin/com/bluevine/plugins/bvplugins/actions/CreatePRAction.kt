package com.bluevine.plugins.bvplugins.actions

import com.bluevine.plugins.bvplugins.ui.BVDialogWrapper
import com.bluevine.plugins.bvplugins.ui.BVTextField
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.openapi.externalSystem.model.ProjectSystemId
import com.intellij.openapi.externalSystem.model.execution.ExternalSystemTaskExecutionSettings
import com.intellij.openapi.externalSystem.service.execution.ProgressExecutionMode
import com.intellij.openapi.externalSystem.util.ExternalSystemUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import org.jdesktop.swingx.JXRadioGroup
import java.awt.Component
import javax.swing.*

private enum class PRLabel(val labelValue: String) {
    Ready_For_Review("rr"),
    Ready_For_Merge("rm"),
    Ready_For_Test("rt"),
    Work_In_Progress("wip"),
    Do_Not_Mrege("dm")
}

class CreatePRAction : PluginAction {

    override fun invoke(project: Project?, title: String, description: String) {
        val labelGroup = JXRadioGroup.create(PRLabel.values())
        labelGroup.layout = BoxLayout(labelGroup, BoxLayout.Y_AXIS)


        var prDescription = ""
        val editTextDesc = BVTextField("") {
            prDescription = it
        }
        val descriptionPanel = JPanel()
        descriptionPanel.layout = BoxLayout(descriptionPanel, BoxLayout.Y_AXIS)
        descriptionPanel.add(JLabel("Description: "))
        descriptionPanel.add(editTextDesc)

        val items: List<Component> = listOf<Component>()
                .plus(arrayOf(
                        labelGroup,
                        descriptionPanel
                ))

        val result = BVDialogWrapper(
                project,
                title,
                items
        ).showAndGet()

        if (result) {
            project?.let {
                createSubmitPRTask(
                        project,
                        labelGroup.selectedValue?.labelValue.orEmpty(),
                        prDescription
                )
            } ?: run {
                Messages.showMessageDialog(
                        null,
                        "EMPTY PROJECT",
                        "ERROR",
                        Messages.getErrorIcon()
                )
            }
        }

    }

    private fun createSubmitPRTask(project: Project, label: String, description: String) {
        val settings = ExternalSystemTaskExecutionSettings()
        settings.externalProjectPath = project.basePath
        settings.taskNames = listOf("submitPr")
        settings.scriptParameters = "-P bv.label=$label -P bv.desc=$description"
        settings.externalSystemIdString = /*GradleConstants.SYSTEM_ID.id*/ProjectSystemId("GRADLE").id

        ExternalSystemUtil.runTask(
                settings, DefaultRunExecutor.EXECUTOR_ID, project, /*GradleConstants.SYSTEM_ID*/ProjectSystemId("GRADLE"),
                null, ProgressExecutionMode.IN_BACKGROUND_ASYNC, false)
    }

}