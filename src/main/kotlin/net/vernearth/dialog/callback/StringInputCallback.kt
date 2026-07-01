package net.vernearth.dialog.callback

import net.minecraft.nbt.*
import net.vernearth.dialog.util.*
import kotlin.jvm.optionals.*

/**
 * Represents a callback for a string input.
 */
class StringInputCallback(val callback: DialogInputCallback<String>) : InputCallback {
  override fun call(tag: Tag) {
    val value = tag.asString().getOrDefault("")
    callback(value)
  }
}
