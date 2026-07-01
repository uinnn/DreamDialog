package net.vernearth.dialog.util

import io.papermc.paper.adventure.*
import net.kyori.adventure.text.format.*
import net.kyori.adventure.text.minimessage.*
import net.kyori.adventure.text.serializer.legacy.*
import net.kyori.adventure.text.serializer.plain.*
import net.minecraft.network.chat.*

/**
 * Type alias for [net.minecraft.network.chat.Component]
 */
typealias NmsComponent = Component

/**
 * Type alias for [net.kyori.adventure.text.Component]
 */
typealias AdventureComponent = net.kyori.adventure.text.Component

/**
 * Removes the italic decoration from the component.
 */
fun AdventureComponent.noItalic(): AdventureComponent {
  return this.decoration(TextDecoration.ITALIC, false)
}

/**
 * Parses a string to an adventure component, either using legacy section or minimessage.
 */
internal fun String.parseLegacyAndMiniMessage(): AdventureComponent {
  return if (contains('§')) {
    LegacyComponentSerializer.legacySection().deserialize(this).noItalic()
  } else {
    MiniMessage.miniMessage().deserialize(this).noItalic()
  }
}

/**
 * Parses a string to an adventure component, either using legacy section or minimessage.
 */
fun String.toMiniMessage() = parseLegacyAndMiniMessage()

/**
 * Converts an adventure component to an nms component.
 */
fun AdventureComponent.toNms(): NmsComponent = PaperAdventure.asVanilla(this)

/**
 * Serializes an adventure component to a string using the PlainText serializer.
 */
fun AdventureComponent.serializeToPlainText() = PlainTextComponentSerializer.plainText().serialize(this)
