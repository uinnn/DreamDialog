package net.vernearth.dialog.builders

import it.unimi.dsi.fastutil.objects.*
import net.minecraft.server.dialog.*
import net.minecraft.server.dialog.input.*
import net.vernearth.dialog.Dialog
import net.vernearth.dialog.util.*
import java.util.*
import kotlin.reflect.*

/**
 * Creates a [BooleanInput] from the given parameters
 */
fun booleanInputOf(label: AdventureComponent, initial: Boolean, onTrue: String, onFalse: String): BooleanInput {
  return BooleanInput(label.toNms(), initial, onTrue, onFalse)
}

/**
 * Creates a [BooleanInput] from the given parameters
 */
fun booleanInputOf(label: String, initial: Boolean, onTrue: String, onFalse: String): BooleanInput {
  return BooleanInput(label.toMiniMessage().toNms(), initial, onTrue, onFalse)
}

/**
 * Creates a [TextInput] from the given parameters
 */
fun textInputOf(
  label: AdventureComponent,
  width: Int = 200,
  labelVisible: Boolean = true,
  initial: String = "",
  maxLength: Int = 32,
  multiline: TextInput.MultilineOptions? = null
): TextInput {
  return TextInput(width, label.toNms(), labelVisible, initial, maxLength, Optional.ofNullable(multiline))
}

/**
 * Creates a [TextInput] from the given parameters
 */
fun textInputOf(
  label: String, width: Int = 200, labelVisible: Boolean = true, initial: String = "", maxLength: Int = 32, multiline: TextInput.MultilineOptions? = null
): TextInput {
  return TextInput(width, label.toMiniMessage().toNms(), labelVisible, initial, maxLength, Optional.ofNullable(multiline))
}

/**
 * Creates a [TextInput] from the given parameters
 */
fun textInputOf(
  label: AdventureComponent,
  width: Int = 200,
  labelVisible: Boolean = true,
  initial: String = "",
  maxLength: Int = 32,
  maxLines: Int? = null,
  height: Int? = null
): TextInput {
  return TextInput(width, label.toNms(), labelVisible, initial, maxLength, Optional.ofNullable(multilineOptionsOf(maxLines, height)))
}

/**
 * Creates a [TextInput] from the given parameters
 */
fun textInputOf(
  label: String, width: Int = 200, labelVisible: Boolean = true, initial: String = "", maxLength: Int = 32, maxLines: Int? = null, height: Int? = null
): TextInput {
  return TextInput(width, label.toMiniMessage().toNms(), labelVisible, initial, maxLength, Optional.ofNullable(multilineOptionsOf(maxLines, height)))
}

/**
 * Creates a [TextInput.MultilineOptions] from the given parameters
 */
fun multilineOptionsOf(maxLines: Int? = null, height: Int? = null): TextInput.MultilineOptions? {
  if (maxLines == null && height == null) return null
  return TextInput.MultilineOptions(Optional.ofNullable(maxLines), Optional.ofNullable(height))
}

/**
 * Creates a [NumberRangeInput] from the given parameters
 */
fun numberRangeInputOf(
  label: AdventureComponent,
  rangeInfo: NumberRangeInput.RangeInfo,
  width: Int = 200,
  labelFormat: String = "options.generic_value",
): NumberRangeInput {
  return NumberRangeInput(width, label.toNms(), labelFormat, rangeInfo)
}

/**
 * Creates a [NumberRangeInput] from the given parameters
 */
fun numberRangeInputOf(
  label: String,
  rangeInfo: NumberRangeInput.RangeInfo,
  width: Int = 200,
  labelFormat: String = "options.generic_value",
): NumberRangeInput {
  return NumberRangeInput(width, label.toMiniMessage().toNms(), labelFormat, rangeInfo)
}

/**
 * Creates a [NumberRangeInput.RangeInfo] from the given parameters
 */
fun rangeInfoOf(start: Float, end: Float, initial: Float? = null, step: Float? = null): NumberRangeInput.RangeInfo {
  return NumberRangeInput.RangeInfo(start, end, Optional.ofNullable(initial), Optional.ofNullable(step))
}

/**
 * Creates a [SingleOptionInput] from the given parameters
 */
fun singleOptionInputOf(
  label: AdventureComponent, entries: List<SingleOptionInput.Entry>, width: Int = 200, labelVisible: Boolean = true
): SingleOptionInput {
  return SingleOptionInput(width, entries, label.toNms(), labelVisible)
}

/**
 * Creates a [SingleOptionInput] from the given parameters
 */
fun singleOptionInputOf(
  label: String, entries: List<SingleOptionInput.Entry>, width: Int = 200, labelVisible: Boolean = true
): SingleOptionInput {
  return SingleOptionInput(width, entries, label.toMiniMessage().toNms(), labelVisible)
}

/**
 * Creates a [SingleOptionInput.Entry] from the given parameters
 */
fun optionEntryOf(id: String, display: AdventureComponent? = null, initial: Boolean = false): SingleOptionInput.Entry {
  return SingleOptionInput.Entry(id, Optional.ofNullable(display?.toNms()), initial)
}

/**
 * Creates a [SingleOptionInput.Entry] from the given parameters
 */
fun optionEntryOf(id: String, display: String? = null, initial: Boolean = false): SingleOptionInput.Entry {
  return SingleOptionInput.Entry(id, display?.toMiniMessage()?.wrapToOptional() ?: Optional.empty(), initial)
}

/**
 * Creates an [Input] from the given parameters
 */
fun inputOf(key: String, control: InputControl): Input {
  return Input(key, control)
}

/**
 * Builder for [Input]
 */
class InputBuilder(val dialog: Dialog? = null) {

  /**
   * All the inputs
   */
  val inputs: MutableList<Input> = ObjectArrayList(4)

  /**
   * Adds an input to the list
   */
  fun add(input: Input): InputBuilder {
    inputs.add(input)
    return this
  }

  /**
   * Adds a boolean input to the list without call any callback.
   */
  fun boolean(key: String, label: AdventureComponent, initial: Boolean = false, onTrue: String = "true", onFalse: String = "false"): InputBuilder {
    return add(inputOf(key, booleanInputOf(label, initial, onTrue, onFalse)))
  }

  /**
   * Adds a boolean input to the list without call any callback.
   */
  fun boolean(key: String, label: String, initial: Boolean = false, onTrue: String = "true", onFalse: String = "false"): InputBuilder {
    return add(inputOf(key, booleanInputOf(label, initial, onTrue, onFalse)))
  }

  /**
   * Adds a boolean input to the list that will stores and executes [callback] after the dialog is closed.
   */
  fun boolean(
    key: String,
    label: AdventureComponent,
    initial: Boolean = false,
    onTrue: String = "true",
    onFalse: String = "false",
    callback: DialogInputCallback<Boolean>
  ): InputBuilder {
    dialog?.addBooleanInputCallback(key, callback)
    return add(inputOf(key, booleanInputOf(label, initial, onTrue, onFalse)))
  }

  /**
   * Adds a boolean input to the list that will stores and executes [callback] after the dialog is closed.
   */
  fun boolean(
    key: String, label: String, initial: Boolean = false, onTrue: String = "true", onFalse: String = "false", callback: DialogInputCallback<Boolean>
  ): InputBuilder {
    dialog?.addBooleanInputCallback(key, callback)
    return add(inputOf(key, booleanInputOf(label, initial, onTrue, onFalse)))
  }

  /**
   * Adds a boolean input to the list that will stores, reflect [property] and executes [callback] after the dialog is closed.
   */
  fun boolean(
    key: String,
    label: AdventureComponent,
    property: KMutableProperty0<Boolean>,
    onTrue: String = "true",
    onFalse: String = "false",
    callback: DialogInputCallback<Boolean>? = null
  ): InputBuilder {
    dialog?.addBooleanInputCallback(key) {
      property.set(it)
      callback?.invoke(it)
    }
    return add(inputOf(key, booleanInputOf(label, property.get(), onTrue, onFalse)))
  }

  /**
   * Adds a boolean input to the list that will stores, reflect [property] and executes [callback] after the dialog is closed.
   */
  fun boolean(
    key: String,
    label: String,
    property: KMutableProperty0<Boolean>,
    onTrue: String = "true",
    onFalse: String = "false",
    callback: DialogInputCallback<Boolean>? = null
  ): InputBuilder {
    dialog?.addBooleanInputCallback(key) {
      property.set(it)
      callback?.invoke(it)
    }
    return add(inputOf(key, booleanInputOf(label, property.get(), onTrue, onFalse)))
  }

  /**
   * Adds a boolean input to the list that will stores, reflect [getter] and executes [callback] after the dialog is closed.
   */
  fun boolean(
    key: String,
    label: AdventureComponent,
    getter: () -> Boolean,
    setter: (Boolean) -> Unit,
    onTrue: String = "true",
    onFalse: String = "false",
    callback: DialogInputCallback<Boolean>? = null
  ): InputBuilder {
    dialog?.addBooleanInputCallback(key) {
      setter(it)
      callback?.invoke(it)
    }
    return add(inputOf(key, booleanInputOf(label, getter(), onTrue, onFalse)))
  }

  /**
   * Adds a boolean input to the list that will stores, reflect [getter] and executes [callback] after the dialog is closed.
   */
  fun boolean(
    key: String,
    label: String,
    getter: () -> Boolean,
    setter: (Boolean) -> Unit,
    onTrue: String = "true",
    onFalse: String = "false",
    callback: DialogInputCallback<Boolean>? = null
  ): InputBuilder {
    dialog?.addBooleanInputCallback(key) {
      setter(it)
      callback?.invoke(it)
    }
    return add(inputOf(key, booleanInputOf(label, getter(), onTrue, onFalse)))
  }

  /**
   * Adds a text input to the list without call any callback.
   */
  fun text(
    key: String,
    label: AdventureComponent,
    width: Int = 200,
    labelVisible: Boolean = true,
    initial: String = "",
    maxLength: Int = 32,
    multiline: TextInput.MultilineOptions? = null
  ): InputBuilder {
    return add(inputOf(key, textInputOf(label, width, labelVisible, initial, maxLength, multiline)))
  }

  /**
   * Adds a text input to the list without call any callback.
   */
  fun text(
    key: String,
    label: String,
    width: Int = 200,
    labelVisible: Boolean = true,
    initial: String = "",
    maxLength: Int = 32,
    multiline: TextInput.MultilineOptions? = null
  ): InputBuilder {
    return add(inputOf(key, textInputOf(label, width, labelVisible, initial, maxLength, multiline)))
  }

  /**
   * Adds a text input to the list without call any callback.
   */
  fun text(
    key: String,
    label: AdventureComponent,
    width: Int = 200,
    labelVisible: Boolean = true,
    initial: String = "",
    maxLength: Int = 32,
    maxLines: Int? = null,
    height: Int? = null
  ): InputBuilder {
    return add(inputOf(key, textInputOf(label, width, labelVisible, initial, maxLength, maxLines, height)))
  }

  /**
   * Adds a text input to the list without call any callback.
   */
  fun text(
    key: String,
    label: String,
    width: Int = 200,
    labelVisible: Boolean = true,
    initial: String = "",
    maxLength: Int = 32,
    maxLines: Int? = null,
    height: Int? = null
  ): InputBuilder {
    return add(inputOf(key, textInputOf(label, width, labelVisible, initial, maxLength, maxLines, height)))
  }

  /**
   * Adds a text input to the list that will stores and executes [callback] after the dialog is closed.
   */
  fun text(
    key: String,
    label: AdventureComponent,
    width: Int = 200,
    labelVisible: Boolean = true,
    initial: String = "",
    maxLength: Int = 32,
    multiline: TextInput.MultilineOptions? = null,
    callback: DialogInputCallback<String>
  ): InputBuilder {
    dialog?.addStringInputCallback(key, callback)
    return add(inputOf(key, textInputOf(label, width, labelVisible, initial, maxLength, multiline)))
  }

  /**
   * Adds a text input to the list that will stores and executes [callback] after the dialog is closed.
   */
  fun text(
    key: String,
    label: String,
    width: Int = 200,
    labelVisible: Boolean = true,
    initial: String = "",
    maxLength: Int = 32,
    multiline: TextInput.MultilineOptions? = null,
    callback: DialogInputCallback<String>
  ): InputBuilder {
    dialog?.addStringInputCallback(key, callback)
    return add(inputOf(key, textInputOf(label, width, labelVisible, initial, maxLength, multiline)))
  }

  /**
   * Adds a text input to the list that will stores and executes [callback] after the dialog is closed.
   */
  fun text(
    key: String,
    label: AdventureComponent,
    width: Int = 200,
    labelVisible: Boolean = true,
    initial: String = "",
    maxLength: Int = 32,
    maxLines: Int? = null,
    height: Int? = null,
    callback: DialogInputCallback<String>
  ): InputBuilder {
    dialog?.addStringInputCallback(key, callback)
    return add(inputOf(key, textInputOf(label, width, labelVisible, initial, maxLength, maxLines, height)))
  }

  /**
   * Adds a text input to the list that will stores and executes [callback] after the dialog is closed.
   */
  fun text(
    key: String,
    label: String,
    width: Int = 200,
    labelVisible: Boolean = true,
    initial: String = "",
    maxLength: Int = 32,
    maxLines: Int? = null,
    height: Int? = null,
    callback: DialogInputCallback<String>
  ): InputBuilder {
    dialog?.addStringInputCallback(key, callback)
    return add(inputOf(key, textInputOf(label, width, labelVisible, initial, maxLength, maxLines, height)))
  }

  /**
   * Adds a text input to the list that will stores, reflect [property] and executes [callback] after the dialog is closed.
   */
  fun text(
    key: String,
    label: AdventureComponent,
    property: KMutableProperty0<String>,
    width: Int = 200,
    labelVisible: Boolean = true,
    maxLength: Int = 32,
    multiline: TextInput.MultilineOptions? = null,
    callback: DialogInputCallback<String>
  ): InputBuilder {
    dialog?.addStringInputCallback(key) {
      property.set(it)
      callback(it)
    }
    return add(inputOf(key, textInputOf(label, width, labelVisible, property.get(), maxLength, multiline)))
  }

  /**
   * Adds a text input to the list that will stores, reflect [property] and executes [callback] after the dialog is closed.
   */
  fun text(
    key: String,
    label: String,
    property: KMutableProperty0<String>,
    width: Int = 200,
    labelVisible: Boolean = true,
    maxLength: Int = 32,
    multiline: TextInput.MultilineOptions? = null,
    callback: DialogInputCallback<String>
  ): InputBuilder {
    dialog?.addStringInputCallback(key) {
      property.set(it)
      callback(it)
    }
    return add(inputOf(key, textInputOf(label, width, labelVisible, property.get(), maxLength, multiline)))
  }

  /**
   * Adds a text input to the list that will stores, reflect [property] and executes [callback] after the dialog is closed.
   */
  fun text(
    key: String,
    label: AdventureComponent,
    property: KMutableProperty0<String>,
    width: Int = 200,
    labelVisible: Boolean = true,
    maxLength: Int = 32,
    maxLines: Int? = null,
    height: Int? = null,
    callback: DialogInputCallback<String>
  ): InputBuilder {
    dialog?.addStringInputCallback(key) {
      property.set(it)
      callback(it)
    }
    return add(inputOf(key, textInputOf(label, width, labelVisible, property.get(), maxLength, maxLines, height)))
  }

  /**
   * Adds a text input to the list that will stores, reflect [property] and executes [callback] after the dialog is closed.
   */
  fun text(
    key: String,
    label: String,
    property: KMutableProperty0<String>,
    width: Int = 200,
    labelVisible: Boolean = true,
    maxLength: Int = 32,
    maxLines: Int? = null,
    height: Int? = null,
    callback: DialogInputCallback<String>
  ): InputBuilder {
    dialog?.addStringInputCallback(key) {
      property.set(it)
      callback(it)
    }
    return add(inputOf(key, textInputOf(label, width, labelVisible, property.get(), maxLength, maxLines, height)))
  }

  /**
   * Adds a text input to the list that will stores, reflect [getter] and executes [callback] after the dialog is closed.
   */
  fun text(
    key: String,
    label: AdventureComponent,
    getter: () -> String,
    setter: (String) -> Unit,
    width: Int = 200,
    labelVisible: Boolean = true,
    maxLength: Int = 32,
    multiline: TextInput.MultilineOptions? = null,
    callback: DialogInputCallback<String>? = null
  ): InputBuilder {
    dialog?.addStringInputCallback(key) {
      setter(it)
      callback?.invoke(it)
    }
    return add(inputOf(key, textInputOf(label, width, labelVisible, getter(), maxLength, multiline)))
  }

  /**
   * Adds a text input to the list that will stores, reflect [getter] and executes [callback] after the dialog is closed.
   */
  fun text(
    key: String,
    label: String,
    getter: () -> String,
    setter: (String) -> Unit,
    width: Int = 200,
    labelVisible: Boolean = true,
    maxLength: Int = 32,
    multiline: TextInput.MultilineOptions? = null,
    callback: DialogInputCallback<String>? = null
  ): InputBuilder {
    dialog?.addStringInputCallback(key) {
      setter(it)
      callback?.invoke(it)
    }
    return add(inputOf(key, textInputOf(label, width, labelVisible, getter(), maxLength, multiline)))
  }

  /**
   * Adds a text input to the list that will stores, reflect [getter] and executes [callback] after the dialog is closed.
   */
  fun text(
    key: String,
    label: AdventureComponent,
    getter: () -> String,
    setter: (String) -> Unit,
    width: Int = 200,
    labelVisible: Boolean = true,
    maxLength: Int = 32,
    maxLines: Int? = null,
    height: Int? = null,
    callback: DialogInputCallback<String>? = null
  ): InputBuilder {
    dialog?.addStringInputCallback(key) {
      setter(it)
      callback?.invoke(it)
    }
    return add(inputOf(key, textInputOf(label, width, labelVisible, getter(), maxLength, maxLines, height)))
  }

  /**
   * Adds a text input to the list that will stores, reflect [getter] and executes [callback] after the dialog is closed.
   */
  fun text(
    key: String,
    label: String,
    getter: () -> String,
    setter: (String) -> Unit,
    width: Int = 200,
    labelVisible: Boolean = true,
    maxLength: Int = 32,
    maxLines: Int? = null,
    height: Int? = null,
    callback: DialogInputCallback<String>? = null
  ): InputBuilder {
    dialog?.addStringInputCallback(key) {
      setter(it)
      callback?.invoke(it)
    }
    return add(inputOf(key, textInputOf(label, width, labelVisible, getter(), maxLength, maxLines, height)))
  }

  /**
   * Adds a number range input to the list without call any callback.
   */
  fun numberRange(
    key: String, label: AdventureComponent, rangeInfo: NumberRangeInput.RangeInfo, width: Int = 200, labelFormat: String = "options.generic_value"
  ): InputBuilder {
    return add(inputOf(key, numberRangeInputOf(label, rangeInfo, width, labelFormat)))
  }

  /**
   * Adds a number range input to the list without call any callback.
   */
  fun numberRange(
    key: String, label: String, rangeInfo: NumberRangeInput.RangeInfo, width: Int = 200, labelFormat: String = "options.generic_value"
  ): InputBuilder {
    return add(inputOf(key, numberRangeInputOf(label, rangeInfo, width, labelFormat)))
  }

  /**
   * Adds a number range input to the list without call any callback.
   */
  fun numberRange(
    key: String,
    label: AdventureComponent,
    range: FloatRange,
    initial: Float? = null,
    step: Float? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value"
  ): InputBuilder {
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.start, range.endInclusive, initial, step), width, labelFormat)))
  }

  /**
   * Adds a number range input to the list without call any callback.
   */
  fun numberRange(
    key: String,
    label: String,
    range: FloatRange,
    initial: Float? = null,
    step: Float? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value"
  ): InputBuilder {
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.start, range.endInclusive, initial, step), width, labelFormat)))
  }

  /**
   * Adds a number range input to the list that will stores and executes [callback] after the dialog is closed.
   */
  fun numberRange(
    key: String,
    label: AdventureComponent,
    range: FloatRange,
    initial: Float? = null,
    step: Float? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Float>
  ): InputBuilder {
    dialog?.addFloatInputCallback(key, callback)
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.start, range.endInclusive, initial, step), width, labelFormat)))
  }

  /**
   * Adds a number range input to the list that will stores and executes [callback] after the dialog is closed.
   */
  fun numberRange(
    key: String,
    label: String,
    range: FloatRange,
    initial: Float? = null,
    step: Float? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Float>
  ): InputBuilder {
    dialog?.addFloatInputCallback(key, callback)
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.start, range.endInclusive, initial, step), width, labelFormat)))
  }

  /**
   * Adds a number range input to the list that will stores, reflect [property] and executes [callback] after the dialog is closed.
   */
  fun numberRange(
    key: String,
    label: AdventureComponent,
    range: FloatRange,
    property: KMutableProperty0<Float>,
    step: Float? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Float>? = null
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) {
      property.set(it)
      callback?.invoke(it)
    }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.start, range.endInclusive, property.get(), step), width, labelFormat)))
  }

  /**
   * Adds a number range input to the list that will stores, reflect [property] and executes [callback] after the dialog is closed.
   */
  fun numberRange(
    key: String,
    label: String,
    range: FloatRange,
    property: KMutableProperty0<Float>,
    step: Float? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Float>? = null
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) {
      property.set(it)
      callback?.invoke(it)
    }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.start, range.endInclusive, property.get(), step), width, labelFormat)))
  }

  /**
   * Adds a number range input to the list that will stores, reflect [getter] and executes [callback] after the dialog is closed.
   */
  fun numberRange(
    key: String,
    label: AdventureComponent,
    range: FloatRange,
    getter: () -> Float,
    setter: (Float) -> Unit,
    step: Float? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Float>? = null
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) {
      setter(it)
      callback?.invoke(it)
    }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.start, range.endInclusive, getter(), step), width, labelFormat)))
  }

  /**
   * Adds a number range input to the list that will stores, reflect [getter] and executes [callback] after the dialog is closed.
   */
  fun numberRange(
    key: String,
    label: String,
    range: FloatRange,
    getter: () -> Float,
    setter: (Float) -> Unit,
    step: Float? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Float>? = null
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) {
      setter(it)
      callback?.invoke(it)
    }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.start, range.endInclusive, getter(), step), width, labelFormat)))
  }

  /**
   * Adds an int range input to the list without call any callback.
   */
  @JvmName("intRange")
  fun numberRange(
    key: String,
    label: AdventureComponent,
    range: IntRange,
    initial: Int? = null,
    step: Int? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value"
  ): InputBuilder {
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.first.toFloat(), range.last.toFloat(), initial?.toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds an int range input to the list without call any callback.
   */
  @JvmName("intRange")
  fun numberRange(
    key: String,
    label: String,
    range: IntRange,
    initial: Int? = null,
    step: Int? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value"
  ): InputBuilder {
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.first.toFloat(), range.last.toFloat(), initial?.toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds an int range input to the list that will stores and executes [callback] after the dialog is closed.
   */
  @JvmName("intRange")
  fun numberRange(
    key: String,
    label: AdventureComponent,
    range: IntRange,
    initial: Int? = null,
    step: Int? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Int>
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) { callback(it.toInt()) }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.first.toFloat(), range.last.toFloat(), initial?.toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds an int range input to the list that will stores and executes [callback] after the dialog is closed.
   */
  @JvmName("intRange")
  fun numberRange(
    key: String,
    label: String,
    range: IntRange,
    initial: Int? = null,
    step: Int? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Int>
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) { callback(it.toInt()) }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.first.toFloat(), range.last.toFloat(), initial?.toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds an int range input to the list that will stores, reflect [property] and executes [callback] after the dialog is closed.
   */
  @JvmName("intRange")
  fun numberRange(
    key: String,
    label: AdventureComponent,
    range: IntRange,
    property: KMutableProperty0<Int>,
    step: Int? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Int>? = null
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) {
      property.set(it.toInt())
      callback?.invoke(it.toInt())
    }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.first.toFloat(), range.last.toFloat(), property.get().toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds an int range input to the list that will stores, reflect [property] and executes [callback] after the dialog is closed.
   */
  @JvmName("intRange")
  fun numberRange(
    key: String,
    label: String,
    range: IntRange,
    property: KMutableProperty0<Int>,
    step: Int? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Int>? = null
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) {
      property.set(it.toInt())
      callback?.invoke(it.toInt())
    }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.first.toFloat(), range.last.toFloat(), property.get().toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds an int range input to the list that will stores, reflect [getter] and executes [callback] after the dialog is closed.
   */
  @JvmName("intRange")
  fun numberRange(
    key: String,
    label: AdventureComponent,
    range: IntRange,
    getter: () -> Int,
    setter: (Int) -> Unit,
    step: Int? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Int>? = null
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) {
      setter(it.toInt())
      callback?.invoke(it.toInt())
    }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.first.toFloat(), range.last.toFloat(), getter().toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds an int range input to the list that will stores, reflect [getter] and executes [callback] after the dialog is closed.
   */
  @JvmName("intRange")
  fun numberRange(
    key: String,
    label: String,
    range: IntRange,
    getter: () -> Int,
    setter: (Int) -> Unit,
    step: Int? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Int>? = null
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) {
      setter(it.toInt())
      callback?.invoke(it.toInt())
    }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.first.toFloat(), range.last.toFloat(), getter().toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds a double range input to the list without call any callback.
   */
  @JvmName("doubleRange")
  fun numberRange(
    key: String,
    label: AdventureComponent,
    range: ClosedFloatingPointRange<Double>,
    initial: Double? = null,
    step: Double? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value"
  ): InputBuilder {
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.start.toFloat(), range.endInclusive.toFloat(), initial?.toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds a double range input to the list without call any callback.
   */
  @JvmName("doubleRange")
  fun numberRange(
    key: String,
    label: String,
    range: ClosedFloatingPointRange<Double>,
    initial: Double? = null,
    step: Double? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value"
  ): InputBuilder {
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.start.toFloat(), range.endInclusive.toFloat(), initial?.toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds a double range input to the list that will stores and executes [callback] after the dialog is closed.
   */
  @JvmName("doubleRange")
  fun numberRange(
    key: String,
    label: AdventureComponent,
    range: ClosedFloatingPointRange<Double>,
    initial: Double? = null,
    step: Double? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Double>
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) { callback(it.toDouble()) }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.start.toFloat(), range.endInclusive.toFloat(), initial?.toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds a double range input to the list that will stores and executes [callback] after the dialog is closed.
   */
  @JvmName("doubleRange")
  fun numberRange(
    key: String,
    label: String,
    range: ClosedFloatingPointRange<Double>,
    initial: Double? = null,
    step: Double? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Double>
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) { callback(it.toDouble()) }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.start.toFloat(), range.endInclusive.toFloat(), initial?.toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds a double range input to the list that will stores, reflect [property] and executes [callback] after the dialog is closed.
   */
  @JvmName("doubleRange")
  fun numberRange(
    key: String,
    label: AdventureComponent,
    range: ClosedFloatingPointRange<Double>,
    property: KMutableProperty0<Double>,
    step: Double? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Double>? = null
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) {
      property.set(it.toDouble())
      callback?.invoke(it.toDouble())
    }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.start.toFloat(), range.endInclusive.toFloat(), property.get().toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds a double range input to the list that will stores, reflect [property] and executes [callback] after the dialog is closed.
   */
  @JvmName("doubleRange")
  fun numberRange(
    key: String,
    label: String,
    range: ClosedFloatingPointRange<Double>,
    property: KMutableProperty0<Double>,
    step: Double? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Double>? = null
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) {
      property.set(it.toDouble())
      callback?.invoke(it.toDouble())
    }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.start.toFloat(), range.endInclusive.toFloat(), property.get().toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds a double range input to the list that will stores, reflect [getter] and executes [callback] after the dialog is closed.
   */
  @JvmName("doubleRange")
  fun numberRange(
    key: String,
    label: AdventureComponent,
    range: ClosedFloatingPointRange<Double>,
    getter: () -> Double,
    setter: (Double) -> Unit,
    step: Double? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Double>? = null
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) {
      setter(it.toDouble())
      callback?.invoke(it.toDouble())
    }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.start.toFloat(), range.endInclusive.toFloat(), getter().toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds a double range input to the list that will stores, reflect [getter] and executes [callback] after the dialog is closed.
   */
  @JvmName("doubleRange")
  fun numberRange(
    key: String,
    label: String,
    range: ClosedFloatingPointRange<Double>,
    getter: () -> Double,
    setter: (Double) -> Unit,
    step: Double? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Double>? = null
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) {
      setter(it.toDouble())
      callback?.invoke(it.toDouble())
    }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.start.toFloat(), range.endInclusive.toFloat(), getter().toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds a long range input to the list without call any callback.
   */
  @JvmName("longRange")
  fun numberRange(
    key: String,
    label: AdventureComponent,
    range: LongRange,
    initial: Long? = null,
    step: Long? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value"
  ): InputBuilder {
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.first.toFloat(), range.last.toFloat(), initial?.toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds a long range input to the list without call any callback.
   */
  @JvmName("longRange")
  fun numberRange(
    key: String,
    label: String,
    range: LongRange,
    initial: Long? = null,
    step: Long? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value"
  ): InputBuilder {
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.first.toFloat(), range.last.toFloat(), initial?.toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds a long range input to the list that will stores and executes [callback] after the dialog is closed.
   */
  @JvmName("longRange")
  fun numberRange(
    key: String,
    label: AdventureComponent,
    range: LongRange,
    initial: Long? = null,
    step: Long? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Long>
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) { callback(it.toLong()) }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.first.toFloat(), range.last.toFloat(), initial?.toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds a long range input to the list that will stores and executes [callback] after the dialog is closed.
   */
  @JvmName("longRange")
  fun numberRange(
    key: String,
    label: String,
    range: LongRange,
    initial: Long? = null,
    step: Long? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Long>
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) { callback(it.toLong()) }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.first.toFloat(), range.last.toFloat(), initial?.toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds a long range input to the list that will stores, reflect [property] and executes [callback] after the dialog is closed.
   */
  @JvmName("longRange")
  fun numberRange(
    key: String,
    label: AdventureComponent,
    range: LongRange,
    property: KMutableProperty0<Long>,
    step: Long? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Long>? = null
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) {
      property.set(it.toLong())
      callback?.invoke(it.toLong())
    }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.first.toFloat(), range.last.toFloat(), property.get().toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds a long range input to the list that will stores, reflect [property] and executes [callback] after the dialog is closed.
   */
  @JvmName("longRange")
  fun numberRange(
    key: String,
    label: String,
    range: LongRange,
    property: KMutableProperty0<Long>,
    step: Long? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Long>? = null
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) {
      property.set(it.toLong())
      callback?.invoke(it.toLong())
    }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.first.toFloat(), range.last.toFloat(), property.get().toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds a long range input to the list that will stores, reflect [getter] and executes [callback] after the dialog is closed.
   */
  @JvmName("longRange")
  fun numberRange(
    key: String,
    label: AdventureComponent,
    range: LongRange,
    getter: () -> Long,
    setter: (Long) -> Unit,
    step: Long? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Long>? = null
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) {
      setter(it.toLong())
      callback?.invoke(it.toLong())
    }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.first.toFloat(), range.last.toFloat(), getter().toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds a long range input to the list that will stores, reflect [getter] and executes [callback] after the dialog is closed.
   */
  @JvmName("longRange")
  fun numberRange(
    key: String,
    label: String,
    range: LongRange,
    getter: () -> Long,
    setter: (Long) -> Unit,
    step: Long? = null,
    width: Int = 200,
    labelFormat: String = "options.generic_value",
    callback: DialogInputCallback<Long>? = null
  ): InputBuilder {
    dialog?.addFloatInputCallback(key) {
      setter(it.toLong())
      callback?.invoke(it.toLong())
    }
    return add(inputOf(key, numberRangeInputOf(label, rangeInfoOf(range.first.toFloat(), range.last.toFloat(), getter().toFloat(), step?.toFloat()), width, labelFormat)))
  }

  /**
   * Adds a single option input to the list.
   */
  inline fun options(
    key: String, label: AdventureComponent, width: Int = 200, labelVisible: Boolean = true, builder: SingleOptionInputBuilder.() -> Unit
  ): InputBuilder {
    return add(inputOf(key, singleOptionInputOf(label, buildSingleOptionInput(dialog, key, builder), width, labelVisible)))
  }

  /**
   * Adds a single option input to the list.
   */
  inline fun options(
    key: String, label: String, width: Int = 200, labelVisible: Boolean = true, builder: SingleOptionInputBuilder.() -> Unit
  ): InputBuilder {
    return add(inputOf(key, singleOptionInputOf(label, buildSingleOptionInput(dialog, key, builder), width, labelVisible)))
  }

  /**
   * Builds the list of inputs
   */
  fun build(): List<Input> {
    return inputs
  }
}

/**
 * Builds a list of [Input] from the given builder
 */
class SingleOptionInputBuilder(val dialog: Dialog? = null, val key: String) {

  /**
   * The entries of the input
   */
  val entries = ObjectArrayList<SingleOptionInput.Entry>(4)

  /**
   * Sets the global callback for the input.
   */
  fun call(callback: DialogInputCallback<String>) {
    dialog?.addStringInputCallback(key, callback)
  }

  /**
   * Adds an entry to the list without call any callback.
   */
  fun add(value: String, label: AdventureComponent, initial: Boolean = false): SingleOptionInputBuilder {
    entries.add(optionEntryOf(value, label, initial))
    return this
  }

  /**
   * Adds an entry to the list without call any callback.
   */
  fun add(value: String, label: String, initial: Boolean = false): SingleOptionInputBuilder {
    entries.add(optionEntryOf(value, label, initial))
    return this
  }

  /**
   * Adds an entry to the list that will stores and executes [callback] after the dialog is closed.
   */
  fun add(value: String, label: AdventureComponent, initial: Boolean = false, callback: DialogInputCallback<String>): SingleOptionInputBuilder {
    dialog?.addSingleOptionInputCallback(key, value, callback)
    add(value, label, initial)
    return this
  }

  /**
   * Adds an entry to the list that will stores and executes [callback] after the dialog is closed.
   */
  fun add(value: String, label: String, initial: Boolean = false, callback: DialogInputCallback<String>): SingleOptionInputBuilder {
    dialog?.addSingleOptionInputCallback(key, value, callback)
    add(value, label, initial)
    return this
  }

  /**
   * Adds an entry to the list that will stores and executes [callback] after the dialog is closed.
   */
  fun add(label: AdventureComponent, initial: Boolean = false, callback: DialogInputCallback<String>): SingleOptionInputBuilder {
    return add(label.serializeToPlainText(), label, initial, callback)
  }

  /**
   * Adds an entry to the list that will stores and executes [callback] after the dialog is closed.
   */
  fun add(label: String, initial: Boolean = false, callback: DialogInputCallback<String>): SingleOptionInputBuilder {
    return add(label, label, initial, callback)
  }

  /**
   * Adds an entry to the list that will stores, reflect [property] and executes [callback] after the dialog is closed.
   */
  fun add(
    value: String, label: AdventureComponent, property: KMutableProperty0<String>, initial: Boolean = false, callback: DialogInputCallback<String>
  ): SingleOptionInputBuilder {
    dialog?.addSingleOptionInputCallback(key, value) {
      property.set(it)
      callback(it)
    }
    add(value, label, initial)
    return this
  }

  /**
   * Adds an entry to the list that will stores, reflect [property] and executes [callback] after the dialog is closed.
   */
  fun add(
    value: String, label: String, property: KMutableProperty0<String>, initial: Boolean = false, callback: DialogInputCallback<String>
  ): SingleOptionInputBuilder {
    dialog?.addSingleOptionInputCallback(key, value) {
      property.set(it)
      callback(it)
    }
    add(value, label, initial)
    return this
  }

  /**
   * Adds an entry to the list that will stores, reflect [property] and executes [callback] after the dialog is closed.
   */
  fun add(
    label: AdventureComponent, property: KMutableProperty0<String>, initial: Boolean = false, callback: DialogInputCallback<String>
  ): SingleOptionInputBuilder {
    return add(label.serializeToPlainText(), label, property, initial, callback)
  }

  /**
   * Adds an entry to the list that will stores, reflect [property] and executes [callback] after the dialog is closed.
   */
  fun add(label: String, property: KMutableProperty0<String>, initial: Boolean = false, callback: DialogInputCallback<String>): SingleOptionInputBuilder {
    return add(label, label, property, initial, callback)
  }

  /**
   * Adds an entry to the list that will stores, reflect [getter] and executes [callback] after the dialog is closed.
   */
  fun add(
    value: String,
    label: AdventureComponent,
    getter: () -> String,
    setter: (String) -> Unit,
    initial: Boolean = false,
    callback: DialogInputCallback<String>? = null
  ): SingleOptionInputBuilder {
    dialog?.addSingleOptionInputCallback(key, value) {
      setter(it)
      callback?.invoke(it)
    }
    add(value, label, initial)
    return this
  }

  /**
   * Adds an entry to the list that will stores, reflect [getter] and executes [callback] after the dialog is closed.
   */
  fun add(
    value: String, label: String, getter: () -> String, setter: (String) -> Unit, initial: Boolean = false, callback: DialogInputCallback<String>? = null
  ): SingleOptionInputBuilder {
    dialog?.addSingleOptionInputCallback(key, value) {
      setter(it)
      callback?.invoke(it)
    }
    add(value, label, initial)
    return this
  }

  /**
   * Adds an entry to the list that will stores, reflect [getter] and executes [callback] after the dialog is closed.
   */
  fun add(
    label: AdventureComponent, getter: () -> String, setter: (String) -> Unit, initial: Boolean = false, callback: DialogInputCallback<String>? = null
  ): SingleOptionInputBuilder {
    return add(label.serializeToPlainText(), label, getter, setter, initial, callback)
  }

  /**
   * Adds an entry to the list that will stores, reflect [getter] and executes [callback] after the dialog is closed.
   */
  fun add(
    label: String, getter: () -> String, setter: (String) -> Unit, initial: Boolean = false, callback: DialogInputCallback<String>? = null
  ): SingleOptionInputBuilder {
    return add(label, label, getter, setter, initial, callback)
  }

  /**
   * Adds all entries from the given enum class.
   * The enum name is used as the value, and the enum name is used as the label.
   */
  inline fun <reified T : Enum<T>> fromEnum(initial: T? = null, noinline callback: DialogInputCallback<String>? = null): SingleOptionInputBuilder {
    enumValues<T>().forEach { enumValue ->
      val value = enumValue.name
      val isInitial = initial?.name == value
      if (callback != null) {
        add(value, value, isInitial, callback)
      } else {
        add(value, value, isInitial)
      }
    }
    return this
  }

  /**
   * Adds all entries from the given enum class with custom labels.
   * The enum name is used as the value, and the label function is used to generate the label.
   */
  inline fun <reified T : Enum<T>> fromEnum(
    label: (T) -> String, initial: T? = null, noinline callback: DialogInputCallback<String>? = null
  ): SingleOptionInputBuilder {
    enumValues<T>().forEach { enumValue ->
      val value = enumValue.name
      val labelText = label(enumValue)
      val isInitial = initial?.name == value
      if (callback != null) {
        add(value, labelText, isInitial, callback)
      } else {
        add(value, labelText, isInitial)
      }
    }
    return this
  }

  /**
   * Adds all entries from the given list.
   * The list item is used as both the value and the label.
   */
  fun fromList(items: List<String>, initial: String? = null, callback: DialogInputCallback<String>? = null): SingleOptionInputBuilder {
    items.forEach { item ->
      val isInitial = item == initial
      if (callback != null) {
        add(item, item, isInitial, callback)
      } else {
        add(item, item, isInitial)
      }
    }
    return this
  }

  /**
   * Adds all entries from the given list with custom labels.
   */
  fun fromList(
    items: List<String>, label: (String) -> String, initial: String? = null, callback: DialogInputCallback<String>? = null
  ): SingleOptionInputBuilder {
    items.forEach { item ->
      val labelText = label(item)
      val isInitial = item == initial
      if (callback != null) {
        add(item, labelText, isInitial, callback)
      } else {
        add(item, labelText, isInitial)
      }
    }
    return this
  }

  /**
   * Adds all entries from the given list with getter/setter reflection.
   */
  fun fromList(
    items: List<String>, getter: () -> String, setter: (String) -> Unit, initial: String? = null, callback: DialogInputCallback<String>? = null
  ): SingleOptionInputBuilder {
    items.forEach { item ->
      val isInitial = item == initial
      add(item, item, getter, setter, isInitial, callback)
    }
    return this
  }

  /**
   * Adds all entries from the given list with custom labels and getter/setter reflection.
   */
  fun fromList(
    items: List<String>,
    label: (String) -> String,
    getter: () -> String,
    setter: (String) -> Unit,
    initial: String? = null,
    callback: DialogInputCallback<String>? = null
  ): SingleOptionInputBuilder {
    items.forEach { item ->
      val labelText = label(item)
      val isInitial = item == initial
      add(item, labelText, getter, setter, isInitial, callback)
    }
    return this
  }

  /**
   * Adds all entries from the given map.
   * The map key is used as the value, and the map value is used as the label.
   */
  @JvmName("fromMap")
  fun fromMap(map: Map<String, String>, initial: String? = null, callback: DialogInputCallback<String>? = null): SingleOptionInputBuilder {
    map.forEach { (value, labelText) ->
      val isInitial = value == initial
      if (callback != null) {
        add(value, labelText, isInitial, callback)
      } else {
        add(value, labelText, isInitial)
      }
    }
    return this
  }

  /**
   * Adds all entries from the given map with getter/setter reflection.
   */
  @JvmName("fromMapWithGetterSetter")
  fun fromMap(
    map: Map<String, String>, getter: () -> String, setter: (String) -> Unit, initial: String? = null, callback: DialogInputCallback<String>? = null
  ): SingleOptionInputBuilder {
    map.forEach { (value, labelText) ->
      val isInitial = value == initial
      add(value, labelText, getter, setter, isInitial, callback)
    }
    return this
  }

  /**
   * Adds all entries from the given map with AdventureComponent labels.
   */
  @JvmName("fromMapAdventure")
  fun fromMap(map: Map<String, AdventureComponent>, initial: String? = null, callback: DialogInputCallback<String>? = null): SingleOptionInputBuilder {
    map.forEach { (value, labelText) ->
      val isInitial = value == initial
      if (callback != null) {
        add(value, labelText, isInitial, callback)
      } else {
        add(value, labelText, isInitial)
      }
    }
    return this
  }

  /**
   * Adds all entries from the given map with AdventureComponent labels and getter/setter reflection.
   */
  @JvmName("fromMapAdventureWithGetterSetter")
  fun fromMap(
    map: Map<String, AdventureComponent>,
    getter: () -> String,
    setter: (String) -> Unit,
    initial: String? = null,
    callback: DialogInputCallback<String>? = null
  ): SingleOptionInputBuilder {
    map.forEach { (value, labelText) ->
      val isInitial = value == initial
      add(value, labelText, getter, setter, isInitial, callback)
    }
    return this
  }

  /**
   * Adds all entries from the given enum class with property reflection.
   */
  inline fun <reified T : Enum<T>> fromEnum(
    property: KMutableProperty0<String>, initial: T? = null, noinline callback: DialogInputCallback<String>
  ): SingleOptionInputBuilder {
    enumValues<T>().forEach { enumValue ->
      val value = enumValue.name
      val isInitial = initial?.name == value
      add(value, value, property, isInitial, callback)
    }
    return this
  }

  /**
   * Adds all entries from the given enum class with custom labels and property reflection.
   */
  inline fun <reified T : Enum<T>> fromEnum(
    label: (T) -> String, property: KMutableProperty0<String>, initial: T? = null, noinline callback: DialogInputCallback<String>
  ): SingleOptionInputBuilder {
    enumValues<T>().forEach { enumValue ->
      val value = enumValue.name
      val labelText = label(enumValue)
      val isInitial = initial?.name == value
      add(value, labelText, property, isInitial, callback)
    }
    return this
  }

  /**
   * Adds all entries from the given enum class with getter/setter reflection.
   */
  inline fun <reified T : Enum<T>> fromEnum(
    noinline getter: () -> String, noinline setter: (String) -> Unit, initial: T? = null, noinline callback: DialogInputCallback<String>? = null
  ): SingleOptionInputBuilder {
    enumValues<T>().forEach { enumValue ->
      val value = enumValue.name
      val isInitial = initial?.name == value
      add(value, value, getter, setter, isInitial, callback)
    }
    return this
  }

  /**
   * Adds all entries from the given enum class with custom labels and getter/setter reflection.
   */
  inline fun <reified T : Enum<T>> fromEnum(
    label: (T) -> String,
    noinline getter: () -> String,
    noinline setter: (String) -> Unit,
    initial: T? = null,
    noinline callback: DialogInputCallback<String>? = null
  ): SingleOptionInputBuilder {
    enumValues<T>().forEach { enumValue ->
      val value = enumValue.name
      val labelText = label(enumValue)
      val isInitial = initial?.name == value
      add(value, labelText, getter, setter, isInitial, callback)
    }
    return this
  }

  /**
   * Builds the list of entries
   */
  fun build(): ObjectArrayList<SingleOptionInput.Entry> {
    return entries
  }
}

/**
 * Builds a list of [Input] from the given builder
 */
inline fun buildInput(dialog: Dialog? = null, builder: InputBuilder.() -> Unit): List<Input> {
  val inputBuilder = InputBuilder(dialog)
  inputBuilder.builder()
  return inputBuilder.build()
}

/**
 * Builds a list of [Input] from the given builder
 */
inline fun buildSingleOptionInput(
  dialog: Dialog? = null, key: String, builder: SingleOptionInputBuilder.() -> Unit
): ObjectArrayList<SingleOptionInput.Entry> {
  val singleOptionInputBuilder = SingleOptionInputBuilder(dialog, key)
  singleOptionInputBuilder.builder()
  return singleOptionInputBuilder.build()
}
