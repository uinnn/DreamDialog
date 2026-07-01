package net.vernearth.dialog.builders

import it.unimi.dsi.fastutil.objects.*
import net.minecraft.core.*
import net.minecraft.nbt.*
import net.minecraft.server.dialog.*
import net.minecraft.server.dialog.body.*
import net.vernearth.dialog.Dialog
import net.vernearth.dialog.util.*
import java.util.*

/**
 * Interface for all dialog builders
 */
interface IDialogBuilder {

  /**
   * Builds the dialog
   */
  fun build(): Dialog

  /**
   * Builds the dialog wrapper
   */
  fun buildWrapper(): NmsDialog
}

/**
 * Base class for all dialog builders
 */
abstract class BaseDialogBuilder : IDialogBuilder {

  /**
   * The dialog being built
   */
  var dialog = Dialog()

  /**
   * Common dialog data
   */
  var common: CommonDialogData? = null

  /**
   * Sets the common dialog data using parameters
   */
  fun common(
    title: String,
    externalTitle: String? = null,
    canCloseWithEscape: Boolean = false,
    pause: Boolean = true,
    afterAction: DialogAction = DialogAction.CLOSE,
    body: List<DialogBody> = emptyList(),
    inputs: List<Input> = emptyList()
  ) {
    common = commonDialogData(title, externalTitle, canCloseWithEscape, pause, afterAction, body, inputs)
  }

  /**
   * Sets the common dialog data using parameters with default values
   */
  fun common(
    title: String,
    externalTitle: String = title,
    canCloseWithEscape: Boolean = false,
    pause: Boolean = true,
    afterAction: DialogAction = DialogAction.CLOSE,
    builder: CommonDialogDataBuilder.() -> Unit
  ) {
    common = buildCommonDialogData(dialog) {
      title(title)
      externalTitle(externalTitle)
      canCloseWithEscape(canCloseWithEscape)
      pause(pause)
      afterAction(afterAction)
      builder()
    }
  }

  /**
   * Sets the sync flag for the dialog
   */
  fun sync(value: Boolean) {
    dialog.sync = value
  }

  /**
   * Sets the dialog to be async
   */
  fun setAsync() {
    dialog.sync = false
  }

  /**
   * Sets the dialog to be sync
   */
  fun setSync() {
    dialog.sync = true
  }

  /**
   * Sets the open callback for the dialog.
   */
  fun onOpen(callback: () -> Unit) {
    dialog.openCallback = callback
  }

  /**
   * Sets the close callback for the dialog.
   */
  fun onClose(callback: () -> Unit) {
    dialog.closeCallback = callback
  }

  /**
   * Sets the click callback for the dialog.
   */
  fun onGlobalClick(callback: DialogGlobalClickCallback) {
    dialog.globalClickCallback = callback
  }

  /**
   * Sets the tick callback for the dialog.
   */
  fun onTick(callback: () -> Unit) {
    dialog.tickCallback = callback
  }

  /**
   * Adds a callback for a button.
   */
  fun addButtonCallback(id: String, callback: DialogClickCallback) {
    dialog.buttonsCallbacks[id] = callback
  }

  /**
   * Adds a callback for a boolean input.
   */
  fun addBooleanInputCallback(id: String, callback: DialogInputCallback<Boolean>) {
    dialog.addBooleanInputCallback(id, callback)
  }

  /**
   * Adds a callback for a string input.
   */
  fun addStringInputCallback(id: String, callback: DialogInputCallback<String>) {
    dialog.addStringInputCallback(id, callback)
  }

  /**
   * Adds a callback for a float input.
   */
  fun addFloatInputCallback(id: String, callback: DialogInputCallback<Float>) {
    dialog.addFloatInputCallback(id, callback)
  }

  /**
   * Builds the dialog
   */
  override fun build(): Dialog {
    val wrapper = buildWrapper()
    dialog.wrapper = wrapper
    return dialog
  }
}

/**
 * Base class for all dialog builders that have an exit button
 */
abstract class ExitableDialogBuilder : BaseDialogBuilder() {

  /**
   * The exit button that does not execute any action
   */
  var exitButton: ActionButton? = null

  /**
   * Number of columns for the dialog
   */
  var columns: Int = 2

  /**
   * Sets the exit button that does not execute any action
   */
  fun exitButton(label: AdventureComponent, tooltip: AdventureComponent? = null, width: Int = 150) {
    exitButton = buttonOf(label, tooltip, width)
  }

  /**
   * Sets the exit button that does not execute any action
   */
  fun exitButton(label: String, tooltip: String? = null, width: Int = 150) {
    exitButton = buttonOf(label, tooltip, width)
  }

  /**
   * Sets the exit button that executes an action
   */
  fun exitButtonWith(label: AdventureComponent, tooltip: AdventureComponent? = null, width: Int = 150, builder: ActionBuilder.() -> Unit) {
    exitButton = buttonOf(label, tooltip, width, dialog, builder)
  }

  /**
   * Sets the exit button that executes an action
   */
  fun exitButtonWith(label: String, tooltip: String? = null, width: Int = 150, builder: ActionBuilder.() -> Unit) {
    exitButton = buttonOf(label, tooltip, width, dialog, builder)
  }

  /**
   * Sets the exit button that executes an action with callback
   */
  fun exitButton(
    key: String,
    label: AdventureComponent,
    tooltip: AdventureComponent? = null,
    width: Int = 150,
    callInputs: Boolean = false,
    additions: CompoundTag? = null,
    callback: DialogClickCallback
  ) {
    exitButton = buttonOf(key, label, tooltip, width, dialog, additions.callInputs(callInputs), callback)
  }

  /**
   * Sets the exit button that executes an action with callback
   */
  fun exitButton(
    key: String,
    label: String,
    tooltip: String? = null,
    width: Int = 150,
    callInputs: Boolean = false,
    additions: CompoundTag? = null,
    callback: DialogClickCallback
  ) {
    exitButton = buttonOf(key, label, tooltip, width, dialog, additions.callInputs(callInputs), callback)
  }

  /**
   * Sets the number of columns for the dialog
   */
  fun columns(value: Int) {
    columns = value
  }
}

/**
 * DSL builder for [ConfirmationDialog]
 */
class ConfirmationDialogBuilder : BaseDialogBuilder() {

  /**
   * The yes button that does not execute any action
   */
  var yesButton: ActionButton? = null

  /**
   * The no button that does not execute any action
   */
  var noButton: ActionButton? = null

  /**
   * Sets the yes button that does not execute any action
   */
  fun yesButton(label: AdventureComponent, tooltip: AdventureComponent? = null, width: Int = 150) {
    yesButton = buttonOf(label, tooltip, width)
  }

  /**
   * Sets the yes button that does not execute any action
   */
  fun yesButton(label: String, tooltip: String? = null, width: Int = 150) {
    yesButton = buttonOf(label, tooltip, width)
  }

  /**
   * Sets the yes button that executes an action
   */
  fun yesButtonWith(label: AdventureComponent, tooltip: AdventureComponent? = null, width: Int = 150, builder: ActionBuilder.() -> Unit) {
    yesButton = buttonOf(label, tooltip, width, dialog, builder)
  }

  /**
   * Sets the yes button that executes an action
   */
  fun yesButtonWith(label: String, tooltip: String? = null, width: Int = 150, builder: ActionBuilder.() -> Unit) {
    yesButton = buttonOf(label, tooltip, width, dialog, builder)
  }

  /**
   * Sets the yes button that executes an action with callback
   */
  fun yesButton(
    key: String,
    label: AdventureComponent,
    tooltip: AdventureComponent? = null,
    width: Int = 150,
    callInputs: Boolean = true,
    additions: CompoundTag? = null,
    callback: DialogClickCallback
  ) {
    yesButton = buttonOf(key, label, tooltip, width, dialog, additions.callInputs(callInputs), callback)
  }

  /**
   * Sets the yes button that executes an action with callback
   */
  fun yesButton(
    key: String,
    label: String,
    tooltip: String? = null,
    width: Int = 150,
    callInputs: Boolean = true,
    additions: CompoundTag? = null,
    callback: DialogClickCallback
  ) {
    yesButton = buttonOf(key, label, tooltip, width, dialog, additions.callInputs(callInputs), callback)
  }

  /**
   * Sets the no button that does not execute any action
   */
  fun noButton(label: AdventureComponent, tooltip: AdventureComponent? = null, width: Int = 150) {
    noButton = buttonOf(label, tooltip, width)
  }

  /**
   * Sets the no button that does not execute any action
   */
  fun noButton(label: String, tooltip: String? = null, width: Int = 150) {
    noButton = buttonOf(label, tooltip, width)
  }

  /**
   * Sets the no button that executes an action
   */
  fun noButtonWith(label: AdventureComponent, tooltip: AdventureComponent? = null, width: Int = 150, builder: ActionBuilder.() -> Unit) {
    noButton = buttonOf(label, tooltip, width, dialog, builder)
  }

  /**
   * Sets the no button that executes an action
   */
  fun noButtonWith(label: String, tooltip: String? = null, width: Int = 150, builder: ActionBuilder.() -> Unit) {
    noButton = buttonOf(label, tooltip, width, dialog, builder)
  }

  /**
   * Sets the no button that executes an action with callback
   */
  fun noButton(
    key: String,
    label: AdventureComponent,
    tooltip: AdventureComponent? = null,
    width: Int = 150,
    callInputs: Boolean = false,
    additions: CompoundTag? = null,
    callback: DialogClickCallback
  ) {
    noButton = buttonOf(key, label, tooltip, width, dialog, additions.callInputs(callInputs), callback)
  }

  /**
   * Sets the no button that executes an action with callback
   */
  fun noButton(
    key: String,
    label: String,
    tooltip: String? = null,
    width: Int = 150,
    callInputs: Boolean = false,
    additions: CompoundTag? = null,
    callback: DialogClickCallback
  ) {
    noButton = buttonOf(key, label, tooltip, width, dialog, additions.callInputs(callInputs), callback)
  }

  /**
   * Builds the confirmation dialog
   */
  override fun buildWrapper(): ConfirmationDialog {
    val common = common ?: error("No 'Common Dialog Data' provided in a confirmation dialog")
    val yesButton = yesButton ?: error("No 'Yes Button' provided in a confirmation dialog")
    val noButton = noButton ?: error("No 'No button' provided in a confirmation dialog")
    return ConfirmationDialog(common, yesButton, noButton)
  }
}

/**
 * DSL builder for [DialogListDialog]
 */
class DialogListDialogBuilder : ExitableDialogBuilder() {

  /**
   * Dialogs to be displayed in the dialog list
   */
  var dialogs: HolderSet<NmsDialog>? = null

  /**
   * Width of the buttons in the dialog
   */
  var buttonWidth: Int = 150

  /**
   * Sets the dialogs to be displayed in the dialog list
   */
  fun dialogs(value: HolderSet<NmsDialog>) {
    dialogs = value
  }

  /**
   * Sets the button width
   */
  fun buttonWidth(value: Int) {
    buttonWidth = value
  }

  /**
   * Builds the dialog list dialog
   */
  override fun buildWrapper(): DialogListDialog {
    val common = common ?: error("No 'Common Dialog Data' provided in a dialog list dialog")
    val dialogs = dialogs ?: error("No 'Dialogs' provided in a dialog list dialog")
    return DialogListDialog(common, dialogs, Optional.ofNullable(exitButton), columns, buttonWidth)
  }
}

/**
 * DSL builder for [MultiActionDialog]
 */
class MultiActionDialogBuilder : ExitableDialogBuilder() {

  /**
   * All buttons in the dialog
   */
  var buttons: MutableList<ActionButton> = ObjectArrayList(4)

  /**
   * Adds a button to the dialog
   */
  fun add(button: ActionButton) {
    buttons.add(button)
  }

  /**
   * Adds a button to the dialog that does not execute any action
   */
  fun add(label: AdventureComponent, tooltip: AdventureComponent? = null, width: Int = 150) {
    add(buttonOf(label, tooltip, width))
  }

  /**
   * Adds a button to the dialog that does not execute any action
   */
  fun add(label: String, tooltip: String? = null, width: Int = 150) {
    add(buttonOf(label, tooltip, width))
  }

  /**
   * Adds a button to the dialog that executes an action
   */
  fun addWith(label: AdventureComponent, tooltip: AdventureComponent? = null, width: Int = 150, builder: ActionBuilder.() -> Unit) {
    add(buttonOf(label, tooltip, width, dialog, builder))
  }

  /**
   * Adds a button to the dialog that executes an action
   */
  fun addWith(label: String, tooltip: String? = null, width: Int = 150, builder: ActionBuilder.() -> Unit) {
    add(buttonOf(label, tooltip, width, dialog, builder))
  }

  /**
   * Adds a button to the dialog that executes an action with callback
   */
  fun add(
    key: String,
    label: AdventureComponent,
    tooltip: AdventureComponent? = null,
    width: Int = 150,
    callInputs: Boolean = true,
    additions: CompoundTag? = null,
    callback: DialogClickCallback
  ) {
    add(buttonOf(key, label, tooltip, width, dialog, additions.callInputs(callInputs), callback))
  }

  /**
   * Adds a button to the dialog that executes an action with callback
   */
  fun add(
    key: String,
    label: String,
    tooltip: String? = null,
    width: Int = 150,
    callInputs: Boolean = true,
    additions: CompoundTag? = null,
    callback: DialogClickCallback
  ) {
    add(buttonOf(key, label, tooltip, width, dialog, additions.callInputs(callInputs), callback))
  }

  /**
   * Builds the multi-action dialog
   */
  override fun buildWrapper(): MultiActionDialog {
    val common = common ?: error("No 'Common Dialog Data' provided in a multi-action dialog")
    return MultiActionDialog(common, buttons, Optional.ofNullable(exitButton), columns)
  }
}

/**
 * DSL builder for [NoticeDialog]
 */
class NoticeDialogBuilder : BaseDialogBuilder() {

  /**
   * The button that does not execute any action
   */
  var button: ActionButton? = null

  /**
   * Sets the button that does not execute any action
   */
  fun button(label: AdventureComponent, tooltip: AdventureComponent? = null, width: Int = 150) {
    button = buttonOf(label, tooltip, width)
  }

  /**
   * Sets the button that does not execute any action
   */
  fun button(label: String, tooltip: String? = null, width: Int = 150) {
    button = buttonOf(label, tooltip, width)
  }

  /**
   * Sets the button that executes an action
   */
  fun buttonWith(label: AdventureComponent, tooltip: AdventureComponent? = null, width: Int = 150, builder: ActionBuilder.() -> Unit) {
    button = buttonOf(label, tooltip, width, dialog, builder)
  }

  /**
   * Sets the button that executes an action
   */
  fun buttonWith(label: String, tooltip: String? = null, width: Int = 150, builder: ActionBuilder.() -> Unit) {
    button = buttonOf(label, tooltip, width, dialog, builder)
  }

  /**
   * Sets the button that executes an action with callback
   */
  fun button(
    key: String,
    label: AdventureComponent,
    tooltip: AdventureComponent? = null,
    width: Int = 150,
    callInputs: Boolean = true,
    additions: CompoundTag? = null,
    callback: DialogClickCallback
  ) {
    button = buttonOf(key, label, tooltip, width, dialog, additions.callInputs(callInputs), callback)
  }

  /**
   * Sets the button that executes an action with callback
   */
  fun button(
    key: String,
    label: String,
    tooltip: String? = null,
    width: Int = 150,
    callInputs: Boolean = true,
    additions: CompoundTag? = null,
    callback: DialogClickCallback
  ) {
    button = buttonOf(key, label, tooltip, width, dialog, additions.callInputs(callInputs), callback)
  }

  /**
   * Builds the notice dialog
   */
  override fun buildWrapper(): NoticeDialog {
    val common = common ?: error("No 'Common Dialog Data' provided in a notice dialog")
    val action = button ?: error("No 'Action' provided in a notice dialog")
    return NoticeDialog(common, action)
  }
}

/**
 * DSL builder for [ServerLinksDialog]
 */
class ServerLinksDialogBuilder : ExitableDialogBuilder() {

  /**
   * Width of the buttons in the dialog
   */
  var buttonWidth: Int = 150

  /**
   * Sets the width of the buttons in the dialog
   */
  fun buttonWidth(value: Int) {
    buttonWidth = value
  }

  /**
   * Builds the server links dialog
   */
  override fun buildWrapper(): ServerLinksDialog {
    val common = common ?: error("No 'Common Dialog Data' provided in a server links dialog")
    return ServerLinksDialog(common, Optional.ofNullable(exitButton), columns, buttonWidth)
  }
}

/**
 * DSL builder for [ConfirmationDialog]
 */
inline fun buildConfirmationDialog(sync: Boolean = false, builder: ConfirmationDialogBuilder.() -> Unit): Dialog {
  return ConfirmationDialogBuilder().apply {
    sync(sync)
    builder()
  }.build()
}

/**
 * DSL builder for [DialogListDialog]
 */
inline fun buildDialogListDialog(sync: Boolean = false, builder: DialogListDialogBuilder.() -> Unit): Dialog {
  return DialogListDialogBuilder().apply {
    sync(sync)
    builder()
  }.build()
}

/**
 * DSL builder for [MultiActionDialog]
 */
inline fun buildMultiActionDialog(sync: Boolean = false, builder: MultiActionDialogBuilder.() -> Unit): Dialog {
  return MultiActionDialogBuilder().apply {
    sync(sync)
    builder()
  }.build()
}

/**
 * DSL builder for [NoticeDialog]
 */
inline fun buildNoticeDialog(sync: Boolean = false, builder: NoticeDialogBuilder.() -> Unit): Dialog {
  return NoticeDialogBuilder().apply {
    sync(sync)
    builder()
  }.build()
}

/**
 * DSL builder for [ServerLinksDialog]
 */
inline fun buildServerLinksDialog(sync: Boolean = false, builder: ServerLinksDialogBuilder.() -> Unit): Dialog {
  return ServerLinksDialogBuilder().apply {
    sync(sync)
    builder()
  }.build()
}
