package com.bluevine.plugins.bvplugins.actions

import com.bluevine.plugins.bvplugins.ui.BVDialogWrapper
import com.bluevine.plugins.bvplugins.ui.BVTextField
import com.bluevine.plugins.bvplugins.utils.runGradleTask
import com.intellij.openapi.project.Project
import org.jdesktop.swingx.JXRadioGroup
import java.awt.Component
import javax.swing.*

private enum class PRLabel(val labelValue: String) {
    Ready_For_Review("rr"),
    Ready_For_Merge("rm"),
    Ready_For_Test("rt"),
    Work_In_Progress("wip"),
    Do_Not_Mrege("dm")
}

class CreatePRAction : PluginAction {

    override fun invoke(project: Project?, title: String, description: String) {
        var prDescription = ""
        val labelGroup = createLabelsItems()
        val items: List<Component> = listOf(
                labelGroup,
                createDescriptionItem {
                    prDescription = it
                }
        )
        val result = showDialog(project, title, items)

        if (result) {
            project.runGradleTask(
                    taskName = "submitPr",
                    taskParameters = arrayOf(
                            "bv.label" to labelGroup.selectedValue?.labelValue.orEmpty(),
                            "bv.desc" to prDescription
                    )
            )
        }

    }

    private fun createLabelsItems(): JXRadioGroup<PRLabel> {
        val labelGroup = JXRadioGroup.create(PRLabel.values())
        labelGroup.layout = BoxLayout(labelGroup, BoxLayout.Y_AXIS)
        return labelGroup
    }

    private fun createDescriptionItem(textChangedCallback: (String) -> Unit): JPanel {
        val editTextDesc = BVTextField("") {
            textChangedCallback.invoke(it)
        }
        val descriptionPanel = JPanel()
        descriptionPanel.layout = BoxLayout(descriptionPanel, BoxLayout.Y_AXIS)
        descriptionPanel.add(JLabel("Description: "))
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