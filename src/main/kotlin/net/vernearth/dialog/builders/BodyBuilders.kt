package net.vernearth.dialog.builders

import it.unimi.dsi.fastutil.objects.*
import net.minecraft.server.dialog.body.*
import net.vernearth.dialog.util.*
import org.bukkit.inventory.*
import java.util.*

/**
 * Creates a [PlainMessage] from the given parameters
 */
fun plainMessageOf(contents: AdventureComponent, width: Int = 200): PlainMessage {
  return PlainMessage(contents.toNms(), width)
}

/**
 * Creates a [PlainMessage] from the given parameters
 */
fun plainMessageOf(contents: String, width: Int = 200): PlainMessage {
  return PlainMessage(contents.toMiniMessage().toNms(), width)
}

/**
 * Creates a [PlainMessage] from the given parameters
 */
fun nullablePlainMessageOf(contents: AdventureComponent? = null, width: Int = 200): PlainMessage? {
  return contents?.let { PlainMessage(it.toNms(), width) }
}

/**
 * Creates a [PlainMessage] from the given parameters
 */
fun nullablePlainMessageOf(contents: String? = null, width: Int = 200): PlainMessage? {
  return contents?.let { PlainMessage(it.toMiniMessage().toNms(), width) }
}

/**
 * Creates an [ItemBody] from the given parameters
 */
fun itemBodyOf(
  item: NmsItemStack, description: PlainMessage? = null, showDecorations: Boolean = true, showTooltip: Boolean = true, width: Int = 16, height: Int = 16
): ItemBody {
  return ItemBody(item, Optional.ofNullable(description), showDecorations, showTooltip, width, height)
}

/**
 * Creates an [ItemBody] from the given parameters
 */
fun itemBodyOf(
  item: ItemStack, description: PlainMessage? = null, showDecorations: Boolean = true, showTooltip: Boolean = true, width: Int = 16, height: Int = 16
): ItemBody {
  return ItemBody(item.unwrap(), Optional.ofNullable(description), showDecorations, showTooltip, width, height)
}

/**
 * Builder for [DialogBody]
 */
class BodyBuilder {

  /**
   * All the bodies
   */
  val bodies: MutableList<DialogBody> = ObjectArrayList(4)

  /**
   * Adds a body to the list
   */
  fun add(body: DialogBody): BodyBuilder {
    bodies.add(body)
    return this
  }

  /**
   * Adds a [PlainMessage] to the list
   */
  fun text(contents: AdventureComponent, width: Int = 200): BodyBuilder {
    return add(plainMessageOf(contents, width))
  }

  /**
   * Adds a [PlainMessage] to the list
   */
  fun text(contents: String, width: Int = 200): BodyBuilder {
    return add(plainMessageOf(contents, width))
  }

  /**
   * Adds an [ItemBody] to the list
   */
  fun item(
    item: NmsItemStack, description: PlainMessage? = null, showDecorations: Boolean = true, showTooltip: Boolean = true, width: Int = 16, height: Int = 16
  ): BodyBuilder {
    return add(itemBodyOf(item, description, showDecorations, showTooltip, width, height))
  }

  /**
   * Adds an [ItemBody] to the list
   */
  fun item(
    item: ItemStack, description: PlainMessage? = null, showDecorations: Boolean = true, showTooltip: Boolean = true, width: Int = 16, height: Int = 16
  ): BodyBuilder {
    return add(itemBodyOf(item, description, showDecorations, showTooltip, width, height))
  }

  /**
   * Adds an [ItemBody] to the list
   */
  fun item(
    item: NmsItemStack,
    description: String? = null,
    descriptionWidth: Int = 200,
    showDecorations: Boolean = true,
    showTooltip: Boolean = true,
    width: Int = 16,
    height: Int = 16
  ): BodyBuilder {
    return add(itemBodyOf(item, nullablePlainMessageOf(description, descriptionWidth), showDecorations, showTooltip, width, height))
  }

  /**
   * Adds an [ItemBody] to the list
   */
  fun item(
    item: ItemStack,
    description: String? = null,
    descriptionWidth: Int = 200,
    showDecorations: Boolean = true,
    showTooltip: Boolean = true,
    width: Int = 16,
    height: Int = 16
  ): BodyBuilder {
    return add(itemBodyOf(item, nullablePlainMessageOf(description, descriptionWidth), showDecorations, showTooltip, width, height))
  }

  /**
   * Builds the list of bodies
   */
  fun build(): List<DialogBody> {
    return bodies
  }
}

/**
 * Builds a list of [DialogBody] from the given builder
 */
inline fun buildBody(builder: BodyBuilder.() -> Unit): List<DialogBody> {
  val bodyBuilder = BodyBuilder()
  bodyBuilder.builder()
  return bodyBuilder.build()
}
