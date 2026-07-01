package net.vernearth.dialog.util

import net.minecraft.server.*

/**
 * Schedules a task to run on the next tick in the main server thread.
 */
fun runNextTick(runnable: Runnable) {
  MinecraftServer.getServer().processQueue.add(runnable)
}
