package com.bluevine.plugins.bvplugins.actions

import com.bluevine.plugins.bvplugins.ui.BVDialogWrapper
import com.bluevine.plugins.bvplugins.ui.BVTextField
import com.bluevine.plugins.bvplugins.utils.runGradleTask
import com.intellij.openapi.project.Project
import org.jdesktop.swingx.JXRadioGroup
import java.awt.Component
import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel

private enum class BranchType(val labelValue: String) {
    Feature("Feature"),
    Release("Release")
}

class CreateProtectedBranchAction : PluginAction {

    override fun invoke(project: Project?, title: String, description: String) {

        var branchName = ""

        val branchTypes = createBranchTypeItems()
        val items: List<Component> = listOf(
            branchTypes,
            createBranchNameItem {
                branchName = it
            }
        )

        val result = showDialog(project, title, items)

        if (result) {
            project?.runGradleTask(
                taskName = "createProtectedBranch",
                taskParameters = arrayOf(
                    "branchType" to branchTypes.selectedValue?.labelValue.orEmpty(),
                    "branchName" to branchName
                )
            )
        }
    }

    private fun createBranchTypeItems(): JXRadioGroup<BranchType> {
        val labelGroup = JXRadioGroup.create(BranchType.values())
        labelGroup.layout = BoxLayout(labelGroup, BoxLayout.Y_AXIS)
        return labelGroup
    }

    private fun createBranchNameItem(textChangedCallback: (String) -> Unit): JPanel {
        val editTextDesc = BVTextField("") {
            textChangedCallback.invoke(it)
        }
        val descriptionPanel = JPanel()
        descriptionPanel.layout = BoxLayout(descriptionPanel, BoxLayout.Y_AXIS)
        descriptionPanel.add(JLabel("Branch Bane: "))
        descriptionPanel.add(editTextDesc)
        return descriptionPanel
    }

    private fun showDialog(project: Project?, title: String, items: List<Component>): Boolean {
        return BVDialogWrapper(
            project,
            title,
            items
        ).showAndGet()
    }

}