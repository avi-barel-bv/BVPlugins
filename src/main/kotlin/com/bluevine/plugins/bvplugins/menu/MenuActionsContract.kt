package com.bluevine.plugins.bvplugins.menu

import com.intellij.openapi.project.Project

interface MenuActionsContract {
    enum class MenuAction(val displayName: String, val description: String) {
        CREATE_PR("Create Pull Request", "Create Pull Request"),
        RUN_DETEKT("Run Detekt", "Run Detekt"),
        COMING_SOON("Coming Soon", "More Actions will be added soon")
    }

    interface MenuActionSelectedListener {
        fun onActionSelected(menuAction: MenuAction, project: Project?)
    }
}
