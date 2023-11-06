package com.bluevine.plugins.bvplugins.menu

import com.intellij.openapi.project.Project

interface MenuActionsContract {
    enum class MenuAction(val displayName: String, val description: String) {
        CREATE_PR("Create Pull Request", "Create Pull Request"),
        CREATE_PROTECTED_BRANCH("Create Protected Branch", "Create Protected Branch"),
        RUN_DETEKT("Run Detekt", "Run Detekt"),
        RUN_FOR_KOVER("Run For Kover", "Run Unit Test Coverage with Kover"),
        COMING_SOON("Coming Soon", "More Actions will be added soon")
    }

    interface MenuActionSelectedListener {
        fun onActionSelected(menuAction: MenuAction, project: Project?)
    }
}
