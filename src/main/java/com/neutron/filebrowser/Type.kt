package com.neutron.filebrowser

enum class Type {
	OTHER,
	TEXT,
	SHELL,
	IMG
}

fun String.toType(): Type = when(this.lowercase().removePrefix(".")) {
	"txt" -> Type.TEXT
	"sh" -> Type.SHELL
	"png", "jpg", "jpeg" -> Type.IMG
	else -> Type.OTHER
}
