package com.neutron.filebrowser

import javafx.scene.image.ImageView
import java.util.Date

data class FileElement(val image: ImageView, val name: String, val size: Long, val type: Type, val modified: Date, val isDir: Boolean)
