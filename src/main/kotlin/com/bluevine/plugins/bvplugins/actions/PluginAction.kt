package com.bluevine.plugins.bvplugins.actions

import com.intellij.openapi.project.Project

interface PluginAction {
    fun invoke(project: Project?, title: String, description: String)
}