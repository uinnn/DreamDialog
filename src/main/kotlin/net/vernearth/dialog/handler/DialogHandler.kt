package net.vernearth.dialog.handler

import io.netty.channel.*
import net.minecraft.nbt.*
import net.minecraft.network.protocol.common.*
import net.minecraft.network.protocol.game.*
import net.vernearth.dialog.*
import net.vernearth.dialog.util.*
import kotlin.jvm.optionals.*

/**
 * Dialog handler name
 */
const val DIALOG_HANDLER_NAME = "dialog_handler"

/**
 * Handles dialog events for a player.
 */
class DialogHandler(var dialog: Dialog) : ChannelInboundHandlerAdapter() {

  /**
   * Handles incoming packets from the player.
   */
  override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
    when (msg) {
      is ServerboundCustomClickActionPacket -> handleClick(msg)
      is ServerboundClientTickEndPacket -> handleTick(msg)
    }
    super.channelRead(ctx, msg)
  }

  /**
   * Handles a click event from the player.
   */
  fun handleClick(packet: ServerboundCustomClickActionPacket) {
    val payload = packet.payload.getOrNull()
    if (payload != null && payload is CompoundTag) {
      val id = packet.id.path
      if (!dialog.sync) {
        dialog.onClick(id, payload)
        dialog.close()
      } else {
        runNextTick {
          dialog.onClick(id, payload)
          dialog.close()
        }
      }
    }
  }

  /**
   * Handles a tick event from the player.
   */
  fun handleTick(packet: ServerboundClientTickEndPacket) {
    dialog.onTick()
  }
}
