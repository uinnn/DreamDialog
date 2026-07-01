package net.vernearth.dialog.callback

import net.minecraft.nbt.*

/**
 * Represents a callback for a dialog input.
 */
fun interface InputCallback {

  /**
   * Called when the input is received.
   */
  fun call(tag: Tag)
}
