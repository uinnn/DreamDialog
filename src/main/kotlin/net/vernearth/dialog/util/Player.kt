package net.vernearth.dialog.util

import net.minecraft.core.*
import net.minecraft.server.dialog.*
import net.vernearth.dialog.Dialog
import net.vernearth.dialog.builders.*
import net.vernearth.dialog.handler.*
import org.bukkit.entity.*

/**
 * Opens a dialog to the player with channel handler injection
 */
fun Player.openDialog(dialog: NmsDialog) {
  handler.openDialog(Holder.direct(dialog))
}

/**
 * Closes the current dialog and removes the channel handler
 */
fun Player.closeCustomDialog() {
  ejectDialogHandler()
  closeDialog()
}

/**
 * Ejects the dialog handler from the player's channel
 */
fun Player.ejectDialogHandler() {
  channel.eject(DIALOG_HANDLER_NAME)
}

/**
 * Injects the dialog handler into the player's channel
 */
fun Player.injectDialogHandler(dialog: Dialog) {
  val current = dialogHandlerOrNull()
  if (current == null) {
    channel.injectBefore("packet_handler", DIALOG_HANDLER_NAME, DialogHandler(dialog))
  } else {
    current.dialog = dialog
  }
}

/**
 * Gets the dialog handler from the player's channel
 */
fun Player.dialogHandlerOrNull(): DialogHandler? {
  return channel.handler()
}

/**
 * Gets the dialog from the player's channel
 */
fun Player.dialogOrNull(): Dialog? {
  return dialogHandlerOrNull()?.dialog
}

/**
 * DSL builder for [ConfirmationDialog]
 */
inline fun Player.openConfirmationDialog(sync: Boolean = false, builder: ConfirmationDialogBuilder.() -> Unit) {
  buildConfirmationDialog(sync, builder).open(this)
}

/**
 * DSL builder for [DialogListDialog]
 */
inline fun Player.openListDialog(sync: Boolean = false, builder: DialogListDialogBuilder.() -> Unit) {
  buildDialogListDialog(sync, builder).open(this)
}

/**
 * DSL builder for [MultiActionDialog]
 */
inline fun Player.openMultiActionDialog(sync: Boolean = false, builder: MultiActionDialogBuilder.() -> Unit) {
  buildMultiActionDialog(sync, builder).open(this)
}

/**
 * DSL builder for [NoticeDialog]
 */
inline fun Player.openNoticeDialog(sync: Boolean = false, builder: NoticeDialogBuilder.() -> Unit) {
  buildNoticeDialog(sync, builder).open(this)
}

/**
 * DSL builder for [ServerLinksDialog]
 */
inline fun Player.openServerLinksDialog(sync: Boolean = false, builder: ServerLinksDialogBuilder.() -> Unit) {
  buildServerLinksDialog(sync, builder).open(this)
}
