package com.bluevine.plugins.bvplugins.actions

import com.bluevine.plugins.bvplugins.ui.BVDialogWrapper
import com.bluevine.plugins.bvplugins.ui.BVTextField
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import java.awt.Component
import javax.swing.JCheckBox
import javax.swing.JLabel

class CreatePRAction : PluginAction {

    override fun invoke(project: Project?, title: String, description: String) {
        val runTest = JCheckBox("Run Test?", Messages.getQuestionIcon())
        val runDetekt = JCheckBox("Run Detekt?", Messages.getQuestionIcon())

        val paramsList = mutableListOf("One", "Two", "Three")
        val items = listOf<Component>()
                .plus(arrayOf(
                    JLabel(description),
                    runTest,
                    runDetekt
                ))
                .plus(
                        (0 until paramsList.size).map { index ->
                            BVTextField(paramsList[index]) {
                                paramsList[index] = it
                            }.apply {
                                toolTipText = "Bla $index"
                            }
                        }
                )
        val result = BVDialogWrapper(
                project,
                title,
                items
        ).showAndGet()

        when (result) {
            true -> {
                Messages.showMessageDialog(
                        project,
                        "Run Test: ${runTest.isSelected}\nRun Detekt: ${runDetekt.isSelected}\nParams: $paramsList",
                        "RUN TASK",
                        Messages.getInformationIcon()
                )
            }
            false -> {
                Messages.showMessageDialog(
                        project,
                        "CANCELLED",
                        "RUN TASK CANCELLED",
                        Messages.getErrorIcon()
                )
            }
        }

    }

}