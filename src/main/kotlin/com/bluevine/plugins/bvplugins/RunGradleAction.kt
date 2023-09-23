package com.bluevine.plugins.bvplugins

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class RunGradleAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        Messages.showMessageDialog(
                e.project,
                "Wow, shigaon!!! - V2!!!",
                "Good For You...",
                Messages.getInformationIcon()
        )
    }

}