package com.tlannigan.dimensionwarp

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.CommandAPIConfig
import dev.jorel.commandapi.arguments.EntitySelector
import dev.jorel.commandapi.arguments.EntitySelectorArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class DimensionWarp : JavaPlugin() {

    override fun onLoad() {
        CommandAPI.onLoad(CommandAPIConfig().verboseOutput(true))

        // Teleport command
        CommandAPICommand("warp")
            .withArguments(EntitySelectorArgument<Player>("player", EntitySelector.ONE_PLAYER))
            .withPermission("dw.warp")
            .executesPlayer(PlayerCommandExecutor { player: Player, args: Array<Any?> ->
                val targetPlayer = args[0] as Player
                val targetDimension = targetPlayer.world.environment

                val senderDimension = player.world.environment

                if (player.uniqueId == targetPlayer.uniqueId) {
                    player.sendMessage("Why would you want to teleport to yourself?")
                } else if (senderDimension != targetDimension) {
                    player.sendMessage("Teleporting you to ${targetPlayer.name}")
                    player.teleport(targetPlayer)
                } else {
                    player.sendMessage("You can't teleport to a player in the same dimension as you.")
                }
            })
            .register()
    }
    override fun onEnable() {
        CommandAPI.onEnable(this)
    }

}
