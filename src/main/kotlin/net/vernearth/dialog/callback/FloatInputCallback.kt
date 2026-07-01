package net.vernearth.dialog.callback

import net.minecraft.nbt.*
import net.vernearth.dialog.util.*
import kotlin.jvm.optionals.*

/**
 * Represents a callback for a float input.
 */
class FloatInputCallback(val callback: DialogInputCallback<Float>) : InputCallback {
  override fun call(tag: Tag) {
    val value = tag.asFloat().getOrDefault(0f)
    callback(value)
  }
}
