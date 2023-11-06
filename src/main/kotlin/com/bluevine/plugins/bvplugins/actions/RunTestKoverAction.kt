package com.bluevine.plugins.bvplugins.actions

import com.bluevine.plugins.bvplugins.utils.runGradleTask
import com.intellij.openapi.project.Project

class RunTestKoverAction : PluginAction {

    override fun invoke(project: Project?, title: String, description: String) {
        project.runGradleTask("unitTestsCoverage")
    }

}