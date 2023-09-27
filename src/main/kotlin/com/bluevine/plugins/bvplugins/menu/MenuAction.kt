package com.bluevine.plugins.bvplugins.menu

import com.bluevine.plugins.bvplugins.actions.ComingSoonAction
import com.bluevine.plugins.bvplugins.actions.CreatePRAction
import com.bluevine.plugins.bvplugins.actions.RunDetektAction
import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory

class MenuAction : AnAction(), MenuActionsContract.MenuActionSelectedListener {

    override fun actionPerformed(e: AnActionEvent) {
        JBPopupFactory.getInstance().createActionGroupPopup(
                e.presentation.text, MenuActionGroup(this, e.project), e.dataContext,
                JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                false, null, -1, null,
                ActionPlaces.getActionGroupPopupPlace(e.place)
        ).apply {
            e.project?.let {
                showCenteredInCurrentWindow(it)
            } ?: showInFocusCenter()
        }
    }

    override fun onActionSelected(menuAction: MenuActionsContract.MenuAction, project: Project?) {
        val pluginAction = when (menuAction) {
            MenuActionsContract.MenuAction.CREATE_PR -> CreatePRAction()
            MenuActionsContract.MenuAction.RUN_DETEKT -> RunDetektAction()
            MenuActionsContract.MenuAction.COMING_SOON -> ComingSoonAction()
        }

        pluginAction.invoke(project, menuAction.displayName, menuAction.description)
    }

}