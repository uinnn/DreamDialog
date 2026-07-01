package net.vernearth.dialog.util

import io.netty.channel.*
import net.minecraft.network.*
import net.minecraft.server.level.*
import net.minecraft.server.network.*
import org.bukkit.craftbukkit.entity.*
import org.bukkit.entity.*

/**
 * Gets the NMS handler of this player.
 */
inline val Player.handler: ServerPlayer
  get() = (this as CraftPlayer).handle

/**
 * Extension property to retrieve the PlayerConnection of the player.
 */
inline val Player.packetListener: ServerGamePacketListenerImpl
  get() = handler.connection

/**
 * Extension property to retrieve the Connection associated with the player's connection.
 */
inline val Player.packetConnection: Connection
  get() = packetListener.connection

/**
 * Extension property to retrieve the Channel associated with the player's network manager.
 */
inline val Player.channel: Channel
  get() = packetConnection.channel

/**
 * Executes the given action in the event loop.
 */
fun Channel.execute(action: Runnable) {
  val loop = eventLoop()
  if (loop.inEventLoop()) {
    action.run()
  } else {
    loop.execute(action)
  }
}

/**
 * Retrieves the handler of the specified type.
 */
inline fun <reified T : ChannelHandler> Channel.handler(): T? {
  return pipeline().get(T::class.java)
}

/**
 * Injects a handler before the specified handler.
 */
fun Channel.injectBefore(before: String, handlerName: String, handler: ChannelHandler) {
  execute {
    pipeline().addBefore(before, handlerName, handler)
  }
}

/**
 * Ejects a handler by name.
 */
fun Channel.eject(handler: String) {
  execute {
    pipeline().remove(handler)
  }
}
