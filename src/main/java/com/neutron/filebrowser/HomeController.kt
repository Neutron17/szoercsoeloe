package com.neutron.filebrowser

import com.neutron.filebrowser.App.Companion.isDebug
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Pane
import javafx.stage.Stage
import java.io.File
import java.net.URL
import java.util.*
import kotlin.io.path.Path
import kotlin.system.exitProcess

class HomeController : Initializable {
	var currDir = ""

	@FXML
	lateinit var fileTable: TableView<FileElement>
	@FXML
	lateinit var go_field: TextField

	@FXML
	fun go() {
		dirLoad(go_field.text)
	}
	@FXML
	fun search() {
	}
	@FXML
	fun close() {
		exitProcess(0)
	}
	@FXML
	fun home() {
		dirLoad(System.getProperty("user.home"))
	}

	private fun imageCorres(isDir: Boolean) = when(isDir) {
		true -> "/dir2.png"
		else -> "/file2.png"
	}

	private fun dirLoad(path: String) {
		dirClear()
		val file = File(path)
		file.listFiles()?.forEach {
			if(isDebug)
				println(it.name)
			fileTable.items.add(
				FileElement(
					ImageView(Image(imageCorres(it.isDirectory))),
					it.name, it.length(), it.extension.toType(),
					Date(it.lastModified()), it.isDirectory
				)
			);
		}
		go_field.text = file.path
		currDir = file.path
	}
	private fun dirClear() {
		fileTable.items.clear()
	}
	fun renamer() { // TODO finish
		val cell = fileTable.items[fileTable.selectionModel.selectedCells[0].row]
		val stage = Stage()
		stage.width = 400.0
		stage.height = 100.0
		stage.isResizable = false
		val pane = Pane()
		val label = Label("Hello World")
		pane.children.add(label)
		val scene = Scene(pane)
		stage.scene = scene;
		stage.show()
	}
	fun proper() { // TODO
		val cell = fileTable.items[fileTable.selectionModel.selectedCells[0].row]
	}

	private fun dirContextMenu() {
		val open = MenuItem("Open")
		open.onAction = EventHandler { dirOpenSelected() }
		val rename = MenuItem("Rename")
		rename.onAction = EventHandler { renamer() }
		val properties = MenuItem("Properties")
		properties.onAction = EventHandler { proper() }
		fileTable.contextMenu = ContextMenu(open, rename, properties)
	}

	private fun dirOpenSelected() {
		val cell = fileTable.items[fileTable.selectionModel.selectedCells[0].row]
		if (cell.isDir)
			dirLoad("$currDir/${cell.name}")
		else // TODO
			println("file")
	}
	private fun dirCreateColls() {
		val nomen = arrayOf("image", "name", "size", "type", "modified")
		val cols = arrayOf(
			TableColumn<FileElement, ImageView>(),
			TableColumn<FileElement, String>("name"),
			TableColumn<FileElement, Long>("size"),
			TableColumn<FileElement, Type>("type"),
			TableColumn<FileElement, Date>("modified"),
		)
		cols[0].prefWidth = 50.0
		cols[1].prefWidth = 200.0
		cols[1].minWidth = 20.0
		cols.forEachIndexed { x, it ->
			it.cellValueFactory = PropertyValueFactory(nomen[x])
		}
		fileTable.columns.addAll(cols)
	}
	private fun dirOnClick() {
		fileTable.onMouseClicked = EventHandler<MouseEvent> {
			if(fileTable.selectionModel.selectedCells.size == 0 || it.clickCount < 2)
				return@EventHandler
			if(it.button == MouseButton.PRIMARY)
				dirOpenSelected()
			// Secondary handled by context menu callback
		}

	}
	private fun dirSetup() {
		dirCreateColls()
		dirContextMenu()
		dirOnClick()
	}

	override fun initialize(url: URL?, res: ResourceBundle?) {
		if(isDebug)
			System.err.println("init")
		dirSetup()
		dirLoad("/home/neutron17")
	}
}