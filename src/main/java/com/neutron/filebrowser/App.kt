package com.neutron.filebrowser

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.io.File
import java.io.IOException
import java.io.PrintStream
import java.util.*
import java.util.logging.Logger
import kotlin.system.exitProcess

class App : Application() {
	@Throws(IOException::class)
	private fun loadView(locale: Locale): Parent {
		val loader = FXMLLoader()
		loader.resources = ResourceBundle.getBundle("langs.lang", locale)
		return FXMLLoader.load(
			Objects.requireNonNull(
				App::class.java.getResource("/view.fxml")
			)
		)
	}
	override fun start(stage: Stage) {
		try {
			val root = loadView(Locale("en", "US"))
			val scene = Scene(root, 800.0, 600.0)
			stage.title = "Hello!"
			stage.scene = scene
			stage.show()
		} catch(e: Exception) {
			panic(e);
		}
	}
	companion object {
		@JvmStatic
		fun panic(e: Exception) {
			e.printStackTrace()
			e.printStackTrace(PrintStream("log.txt"))
			exitProcess(1);
		}
		@JvmStatic
		fun main(args: Array<String>) {
			launch(App::class.java, *args)
		}
		const val isDebug = true
	}
}


