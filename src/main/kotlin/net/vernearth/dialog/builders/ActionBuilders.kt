package net.vernearth.dialog.builders

import net.minecraft.core.*
import net.minecraft.nbt.*
import net.minecraft.network.chat.*
import net.minecraft.resources.*
import net.minecraft.server.dialog.action.*
import net.vernearth.dialog.*
import net.vernearth.dialog.util.*
import java.net.*
import java.util.*

/**
 * DSL builder for [Action]
 */
class ActionBuilder(val dialog: Dialog? = null) {

  /**
   * The action to be performed when the button is clicked
   */
  var action: Action? = null

  /**
   * Creates a [StaticAction] from a [ClickEvent]
   */
  fun static(event: ClickEvent): ActionBuilder {
    action = StaticAction(event)
    return this
  }

  /**
   * Creates a [StaticAction] from a [ClickEvent.ChangePage]
   */
  fun changePage(page: Int): ActionBuilder {
    action = StaticAction(ClickEvent.ChangePage(page))
    return this
  }

  /**
   * Creates a [StaticAction] from a [ClickEvent.CopyToClipboard]
   */
  fun copyToClipboard(value: String): ActionBuilder {
    action = StaticAction(ClickEvent.CopyToClipboard(value))
    return this
  }

  /**
   * Creates a [StaticAction] from a [ClickEvent.Custom]
   */
  fun custom(id: String, payload: Tag? = null): ActionBuilder {
    action = StaticAction(ClickEvent.Custom(Identifier.fromNamespaceAndPath("vernearth", id), Optional.ofNullable(payload)))
    return this
  }

  /**
   * Creates a [StaticAction] from a [ClickEvent.OpenFile]
   */
  fun openFile(file: String): ActionBuilder {
    action = StaticAction(ClickEvent.OpenFile(file))
    return this
  }

  /**
   * Creates a [StaticAction] from a [ClickEvent.OpenUrl]
   */
  fun openUrl(uri: URI): ActionBuilder {
    action = StaticAction(ClickEvent.OpenUrl(uri))
    return this
  }

  /**
   * Creates a [StaticAction] from a [ClickEvent.RunCommand]
   */
  fun runCommand(command: String): ActionBuilder {
    action = StaticAction(ClickEvent.RunCommand(command))
    return this
  }

  /**
   * Creates a [StaticAction] from a [ClickEvent.SuggestCommand]
   */
  fun suggestCommand(command: String): ActionBuilder {
    action = StaticAction(ClickEvent.SuggestCommand(command))
    return this
  }

  /**
   * Creates a [StaticAction] from a [ClickEvent.ShowDialog]
   */
  fun showDialog(dialog: Holder<NmsDialog>): ActionBuilder {
    action = StaticAction(ClickEvent.ShowDialog(dialog))
    return this
  }

  /**
   * Creates a [CustomAll] from the given parameters
   */
  fun customAll(id: String, additions: CompoundTag? = null): ActionBuilder {
    action = CustomAll(Identifier.fromNamespaceAndPath("vernearth", id), Optional.ofNullable(additions))
    return this
  }

  /**
   * Creates a [CustomAll] from the given parameters and adds a callback to the dialog
   */
  fun call(id: String, additions: CompoundTag? = null, callback: DialogClickCallback): ActionBuilder {
    action = CustomAll(Identifier.fromNamespaceAndPath("vernearth", id), Optional.ofNullable(additions))
    dialog?.addButtonCallback(id, callback)
    return this
  }

  /**
   * Returns the created [Action]
   */
  fun build(): Action? {
    return action
  }
}

/**
 * DSL builder for [Action]
 */
inline fun buildAction(dialog: Dialog? = null, block: ActionBuilder.() -> Unit): Action? {
  return ActionBuilder(dialog).apply(block).build()
}
