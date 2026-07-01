package net.vernearth.dialog

import it.unimi.dsi.fastutil.objects.*
import net.minecraft.nbt.*
import net.vernearth.dialog.callback.*
import net.vernearth.dialog.util.*
import org.bukkit.entity.*

/**
 * Represents a dialog that can be shown to a player.
 */
class Dialog {

  /**
   * The wrapper for the dialog.
   */
  lateinit var wrapper: NmsDialog

  /**
   * Whether the dialog has a wrapper.
   */
  val hasWrapper: Boolean
    get() = ::wrapper.isInitialized

  /**
   * Whether the dialog is currently open.
   */
  var isOpen = false

  /**
   * Callbacks to be executed when the dialog is opened
   */
  var openCallback: DialogCallback? = null

  /**
   * Callbacks to be executed when the dialog is closed.
   */
  var closeCallback: DialogCallback? = null

  /**
   * Callbacks to be executed when a button is clicked.
   */
  var globalClickCallback: DialogGlobalClickCallback? = null

  /**
   * Callbacks to be executed when a tick event is received.
   */
  var tickCallback: DialogCallback? = null

  /**
   * Whether the dialog is sync or async.
   */
  var sync: Boolean = false

  /**
   * Map of button IDs to their click callbacks.
   */
  var buttonsCallbacks: MutableMap<String, DialogClickCallback> = Object2ObjectOpenHashMap(4)

  /**
   * Map of input IDs to their type-safe callbacks.
   */
  var inputsCallbacks: MutableMap<String, InputCallback> = Object2ObjectOpenHashMap(4)

  /**
   * The player that is currently interacting with the dialog.
   */
  lateinit var player: Player

  /**
   * Whether the dialog has a player.
   */
  val hasPlayer: Boolean
    get() = ::player.isInitialized

  /**
   * Opens the dialog for the player.
   */
  fun open(player: Player) {
    if (isOpen) {
      return
    }

    if (!hasWrapper) {
      throw IllegalStateException("Dialog has no wrapper")
    }

    this.player = player
    isOpen = true
    player.injectDialogHandler(this)
    player.openDialog(wrapper)
    openCallback?.invoke()
  }

  /**
   * Closes the dialog for the player.
   */
  fun close() {
    if (!isOpen) {
      return
    }

    isOpen = false
    player.closeCustomDialog()
    closeCallback?.invoke()
  }

  /**
   * Called when a button is clicked.
   */
  fun onClick(id: String, payload: CompoundTag) {
    globalClickCallback?.invoke(id, payload)
    buttonsCallbacks[id]?.invoke(payload)
    if (payload.contains("call_inputs")) {
      callInputs(payload)
    }
  }

  /**
   * Calls the input callbacks for the given payload.
   */
  fun callInputs(payload: CompoundTag) {
    for ((key, value) in payload.entrySet()) {
      inputsCallbacks[key]?.call(value)
    }
  }

  /**
   * Called when a tick event is received.
   */
  fun onTick() {
    tickCallback?.invoke()
  }

  /**
   * Sets the open callback for the dialog.
   */
  fun onOpen(callback: DialogCallback) {
    openCallback = callback
  }

  /**
   * Sets the close callback for the dialog.
   */
  fun onClose(callback: DialogCallback) {
    closeCallback = callback
  }

  /**
   * Sets the click callback for the dialog.
   */
  fun onGlobalClick(callback: DialogGlobalClickCallback) {
    globalClickCallback = callback
  }

  /**
   * Sets the tick callback for the dialog.
   */
  fun onTick(callback: DialogCallback) {
    tickCallback = callback
  }

  /**
   * Adds a callback for a button.
   */
  fun addButtonCallback(id: String, callback: DialogClickCallback) {
    buttonsCallbacks[id] = callback
  }

  /**
   * Adds a callback for a boolean input.
   */
  fun addBooleanInputCallback(id: String, callback: DialogInputCallback<Boolean>) {
    inputsCallbacks[id] = BooleanInputCallback(callback)
  }

  /**
   * Adds a callback for a string input.
   */
  fun addStringInputCallback(id: String, callback: DialogInputCallback<String>) {
    inputsCallbacks[id] = StringInputCallback(callback)
  }

  /**
   * Adds a callback for a float input.
   */
  fun addFloatInputCallback(id: String, callback: DialogInputCallback<Float>) {
    inputsCallbacks[id] = FloatInputCallback(callback)
  }

  /**
   * Adds a callback for a single option input.
   */
  fun addSingleOptionInputCallback(id: String, key: String, callback: DialogInputCallback<String>) {
    val present = inputsCallbacks.getOrPut(id) { SingleOptionInputCallback() }
    if (present is SingleOptionInputCallback) {
      present.callbacks[key] = callback
    }
  }
}
