package com.bluevine.plugins.bvplugins.menu

import com.intellij.ide.plugins.CountIcon
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.project.Project

class MenuActionGroup(
        private val menuActionSelectedListener: MenuActionsContract.MenuActionSelectedListener,
        private val project: Project?
) : ActionGroup() {

    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        return e?.let {
            MenuActionsContract.MenuAction.values().map {
                object : AnAction(it.displayName, it.displayName, CountIcon()) {
                    override fun actionPerformed(e: AnActionEvent) {
                        menuActionSelectedListener.onActionSelected(it, project)
                    }
                }
            }.toTypedArray()

        } ?: EMPTY_ARRAY
    }

}