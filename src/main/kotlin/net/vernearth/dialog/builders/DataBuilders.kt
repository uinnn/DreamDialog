package net.vernearth.dialog.builders

import net.minecraft.server.dialog.*
import net.minecraft.server.dialog.body.*
import net.vernearth.dialog.util.*
import java.util.*

/**
 * Wraps an [AdventureComponent] to an [Optional] [NmsComponent]
 */
fun AdventureComponent?.wrapToOptional(): Optional<NmsComponent> {
  return Optional.ofNullable(this?.toNms())
}

/**
 * Creates a [CommonDialogData] from the given parameters
 */
fun commonDialogData(
  title: AdventureComponent,
  externalTitle: AdventureComponent? = null,
  canCloseWithEscape: Boolean = false,
  pause: Boolean = true,
  afterAction: DialogAction = DialogAction.CLOSE,
  body: List<DialogBody> = emptyList(),
  inputs: List<Input> = emptyList()
): CommonDialogData {
  return CommonDialogData(
    title.toNms(), externalTitle.wrapToOptional(), canCloseWithEscape, pause, afterAction, body, inputs
  )
}

/**
 * Creates a [CommonDialogData] from the given parameters
 */
fun commonDialogData(
  title: String,
  externalTitle: String? = null,
  canCloseWithEscape: Boolean = false,
  pause: Boolean = true,
  afterAction: DialogAction = DialogAction.CLOSE,
  body: List<DialogBody> = emptyList(),
  inputs: List<Input> = emptyList(),
): CommonDialogData {
  return CommonDialogData(
    title.toMiniMessage().toNms(), externalTitle?.toMiniMessage().wrapToOptional(), canCloseWithEscape, pause, afterAction, body, inputs
  )
}

/**
 * DSL builder for [CommonDialogData]
 */
inline fun buildCommonDialogData(dialog: net.vernearth.dialog.Dialog? = null, block: CommonDialogDataBuilder.() -> Unit): CommonDialogData {
  return CommonDialogDataBuilder(dialog).apply(block).build()
}

/**
 * DSL builder for [CommonDialogData]
 */
class CommonDialogDataBuilder(val dialog: net.vernearth.dialog.Dialog? = null) {

  /**
   * The title of the dialog
   */
  var title: AdventureComponent? = null

  /**
   * The external title of the dialog
   */
  var externalTitle: AdventureComponent? = null

  /**
   * If the dialog can be closed with escape
   */
  var canCloseWithEscape: Boolean = false

  /**
   * If the dialog should pause the game
   */
  var pause: Boolean = true

  /**
   * The action to perform after the dialog is closed
   */
  var afterAction: DialogAction = DialogAction.CLOSE

  /**
   * The body of the dialog
   */
  var body: List<DialogBody> = emptyList()

  /**
   * The inputs of the dialog
   */
  var inputs: List<Input> = emptyList()

  /**
   * Sets the title of the dialog
   */
  fun title(value: AdventureComponent) {
    title = value
  }

  /**
   * Sets the title of the dialog
   */
  fun title(value: String) {
    title = value.toMiniMessage()
  }

  /**
   * Sets the external title of the dialog
   */
  fun externalTitle(value: AdventureComponent) {
    externalTitle = value
  }

  /**
   * Sets the external title of the dialog
   */
  fun externalTitle(value: String) {
    externalTitle = value.toMiniMessage()
  }

  /**
   * Sets if the dialog can be closed with escape
   */
  fun canCloseWithEscape(value: Boolean) {
    canCloseWithEscape = value
  }

  /**
   * Sets if the dialog should pause the game
   */
  fun pause(value: Boolean) {
    pause = value
  }

  /**
   * Sets the action to perform after the dialog is closed
   */
  fun afterAction(value: DialogAction) {
    afterAction = value
  }

  /**
   * Sets the body of the dialog
   */
  fun body(vararg value: DialogBody) {
    body = value.toList()
  }

  /**
   * Sets the body of the dialog
   */
  fun body(value: List<DialogBody>) {
    body = value
  }

  /**
   * Sets the inputs of the dialog
   */
  fun inputs(vararg value: Input) {
    inputs = value.toList()
  }

  /**
   * Sets the inputs of the dialog
   */
  fun inputs(value: List<Input>) {
    inputs = value
  }

  /**
   * DSL builder for [DialogBody]
   */
  fun body(builder: BodyBuilder.() -> Unit) {
    body = buildBody(builder)
  }

  /**
   * DSL builder for [Input]
   */
  fun inputs(builder: InputBuilder.() -> Unit) {
    inputs = buildInput(dialog, builder)
  }

  /**
   * Builds the [CommonDialogData]
   */
  fun build(): CommonDialogData {
    val title = title ?: error("No 'Title' provided in common dialog data")
    return CommonDialogData(
      title.toNms(), externalTitle.wrapToOptional(), canCloseWithEscape, pause, afterAction, body, inputs
    )
  }
}
