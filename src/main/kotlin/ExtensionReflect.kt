package org.zaproxy.zap.extension.reflect

import org.parosproxy.paros.extension.ExtensionAdaptor
import org.parosproxy.paros.extension.ExtensionHook
import org.parosproxy.paros.network.HttpSender

class ExtensionReflect : ExtensionAdaptor(NAME) {

    private val reflectPanel = ReflectPanel()

    companion object {
        const val NAME = "Reflect"
    }

    override fun hook(extensionHook: ExtensionHook?) {
        super.hook(extensionHook)
        if (view != null) {
            HttpSender.addListener(ReflectListener(reflectPanel))
            extensionHook?.hookView?.addStatusPanel(reflectPanel)
        }
    }

    override fun unload() {
        HttpSender.removeListener(ReflectListener(reflectPanel))
    }

    override fun canUnload(): Boolean = true

    override fun getAuthor(): String = "Caleb Kinney"

    override fun getDescription(): String = "Finds reflected parameters."

    override fun postInstall() {
        super.postInstall()

        if (view != null) {
            reflectPanel.setTabFocus()
        }
    }
}

