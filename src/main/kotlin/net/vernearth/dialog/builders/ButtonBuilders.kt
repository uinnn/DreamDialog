package net.vernearth.dialog.builders

import net.minecraft.nbt.*
import net.minecraft.server.dialog.*
import net.vernearth.dialog.Dialog
import net.vernearth.dialog.util.*
import java.util.*

/**
 * Creates an [ActionButton] from the given parameters that do not execute any action.
 */
fun buttonOf(label: AdventureComponent, tooltip: AdventureComponent? = null, width: Int = 150): ActionButton {
  return ActionButton(CommonButtonData(label.toNms(), tooltip.wrapToOptional(), width), Optional.empty())
}

/**
 * Creates an [ActionButton] from the given parameters that do not execute any action.
 */
fun buttonOf(label: String, tooltip: String?, width: Int = 150): ActionButton {
  return buttonOf(label.toMiniMessage(), tooltip?.toMiniMessage(), width)
}

/**
 * Creates an [ActionButton] from the given parameters that execute an action.
 */
fun buttonOf(
  label: AdventureComponent, tooltip: AdventureComponent? = null, width: Int = 150, dialog: Dialog? = null, builder: ActionBuilder.() -> Unit
): ActionButton {
  return ActionButton(CommonButtonData(label.toNms(), tooltip.wrapToOptional(), width), Optional.ofNullable(buildAction(dialog, builder)))
}

/**
 * Creates an [ActionButton] from the given parameters that execute an action.
 */
fun buttonOf(label: String, tooltip: String?, width: Int = 150, dialog: Dialog? = null, builder: ActionBuilder.() -> Unit): ActionButton {
  return buttonOf(label.toMiniMessage(), tooltip?.toMiniMessage(), width, dialog, builder)
}

/**
 * Creates an [ActionButton] from the given parameters that execute an action.
 */
fun buttonOf(
  key: String,
  label: AdventureComponent,
  tooltip: AdventureComponent? = null,
  width: Int = 150,
  dialog: Dialog? = null,
  additions: CompoundTag? = null,
  callback: DialogClickCallback
): ActionButton {
  return buttonOf(label, tooltip, width, dialog) {
    call(key, additions, callback)
  }
}

/**
 * Creates an [ActionButton] from the given parameters that execute an action.
 */
fun buttonOf(
  key: String, label: String, tooltip: String?, width: Int = 150, dialog: Dialog? = null, additions: CompoundTag? = null, callback: DialogClickCallback
): ActionButton {
  return buttonOf(label.toMiniMessage(), tooltip?.toMiniMessage(), width, dialog) {
    call(key, additions, callback)
  }
}

/**
 * Adds a key-value pair to the compound tag.
 */
fun CompoundTag?.callInputs(call: Boolean): CompoundTag? {
  if (call) {
    val tag = this ?: CompoundTag()
    tag.putBoolean("call_inputs", true)
    return tag
  } else {
    return this
  }
}
