package com.bluevine.plugins.bvplugins.actions

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

class ComingSoonAction : PluginAction {

    override fun invoke(project: Project?, title: String, description: String) {
        val result = Messages.showYesNoDialog(
                project,
                "Check again in a few minutes?",
                "Coming Soon....",
                Messages.getQuestionIcon()
        )

        when (result) {
            Messages.NO -> {
                Messages.showErrorDialog(
                        project,
                        "What do you mean no... \uD83D\uDE21",
                        "FUCK YOU"
                )
            }
        }
    }

}