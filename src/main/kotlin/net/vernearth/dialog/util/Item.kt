package net.vernearth.dialog.util

import org.bukkit.craftbukkit.inventory.*
import org.bukkit.inventory.*

/**
 * Unwraps a Bukkit [ItemStack] to a NMS [ItemStack]
 */
fun ItemStack.unwrap(): NmsItemStack {
  return CraftItemStack.unwrap(this)
}
