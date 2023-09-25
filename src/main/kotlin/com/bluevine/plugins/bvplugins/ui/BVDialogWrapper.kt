package com.bluevine.plugins.bvplugins.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import java.awt.Component
import java.awt.FlowLayout
import javax.swing.BoxLayout
import javax.swing.JComponent
import javax.swing.JPanel

class BVDialogWrapper(
    project: Project?,
    title: String,
    private val components: List<Component>
) : DialogWrapper(project) {

    init {
        this.init()
        this.title = title
    }

    override fun createCenterPanel(): JComponent {
        val panel = JPanel(FlowLayout())
        val layout = BoxLayout(panel, BoxLayout.Y_AXIS)
        panel.layout = layout

        components.forEach { panel.add(it) }
        return panel
    }

}
