package net.vernearth.dialog.util

import net.minecraft.nbt.*
import net.minecraft.server.dialog.*
import net.minecraft.world.item.*

/**
 * Type alias for [Dialog]
 */
typealias NmsDialog = Dialog

/**
 * Type alias for [ItemStack]
 */
typealias NmsItemStack = ItemStack

/**
 * Type alias for [ClosedFloatingPointRange]
 */
typealias FloatRange = ClosedFloatingPointRange<Float>

/**
 * Represents a callback for a dialog.
 */
typealias DialogCallback = () -> Unit

/**
 * Represents a global callback for a dialog button click.
 */
typealias DialogGlobalClickCallback = (String, CompoundTag) -> Unit

/**
 * Represents a callback for a dialog button click.
 */
typealias DialogClickCallback = (CompoundTag) -> Unit

/**
 * Represents a callback for a dialog input.
 */
typealias DialogInputCallback<T> = (T) -> Unit
