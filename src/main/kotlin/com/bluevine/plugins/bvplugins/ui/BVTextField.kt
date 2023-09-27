package com.bluevine.plugins.bvplugins.ui

import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class BVTextField(
    initialValue: String = "",
    private val changeListener: (String) -> Unit
) : JTextField(), DocumentListener {

    init {
        this.text = initialValue
    }

    override fun addNotify() {
        super.addNotify()
        document.addDocumentListener(this)
    }

    override fun removeNotify() {
        super.removeNotify()
        document.removeDocumentListener(this)
    }

    override fun insertUpdate(e: DocumentEvent?) {
        publish()
    }

    override fun removeUpdate(e: DocumentEvent?) {
        publish()
    }

    override fun changedUpdate(e: DocumentEvent?) {
        publish()
    }

    private fun publish() {
        changeListener.invoke(this.text)
    }

}