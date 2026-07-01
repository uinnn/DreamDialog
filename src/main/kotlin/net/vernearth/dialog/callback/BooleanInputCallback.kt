package net.vernearth.dialog.callback

import net.minecraft.nbt.*
import net.vernearth.dialog.util.*
import kotlin.jvm.optionals.*

/**
 * Represents a callback for a boolean input.
 */
class BooleanInputCallback(val callback: DialogInputCallback<Boolean>) : InputCallback {
  override fun call(tag: Tag) {
    val value = tag.asBoolean().getOrDefault(false)
    callback(value)
  }
}
