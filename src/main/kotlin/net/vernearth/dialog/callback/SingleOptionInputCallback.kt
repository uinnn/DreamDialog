package net.vernearth.dialog.callback

import it.unimi.dsi.fastutil.objects.*
import net.minecraft.nbt.*
import net.vernearth.dialog.util.*
import kotlin.jvm.optionals.*

/**
 * Represents a callback for a single option input.
 */
class SingleOptionInputCallback(val callbacks: MutableMap<String, DialogInputCallback<String>> = Object2ObjectOpenHashMap(4)) : InputCallback {
  override fun call(tag: Tag) {
    val value = tag.asString().getOrNull() ?: return
    callbacks[value]?.invoke(value)
  }
}
