
package nu.baud.arbor

import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.PlayerInventory
import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent

object TreeListener : Listener {

    @EventHandler
    fun blockBreak(ev: BlockBreakEvent) {
        val player: Player = ev.player
        val block: Block = ev.block

        if (ev.isCancelled) return
        if (player.gameMode !== GameMode.SURVIVAL) return

        if (player.inventory.itemInMainHand.type !== Material.WOOD_AXE &&
                player.inventory.itemInMainHand.type !== Material.STONE_AXE &&
                player.inventory.itemInMainHand.type !== Material.GOLD_AXE &&
                player.inventory.itemInMainHand.type !== Material.IRON_AXE &&
                player.inventory.itemInMainHand.type !== Material.DIAMOND_AXE) {
            return
        }

        when (block.type) {
            Material.LOG, Material.LOG_2 -> treeFell(block, player)
            else -> return
        }
    }

    fun treeFell(block: Block, player: Player) {
        block.breakNaturally()

        val inv: PlayerInventory = player.inventory
        val above: Location = Location(block.world, block.location.x, block.location.y + 1, block.location.z)

        when (above.block.type) {
            Material.LOG, Material.LOG_2 -> {
                treeFell(above.block, player)

                val dura: Short = (inv.itemInMainHand.durability + 1).toShort()

                if (dura > inv.itemInMainHand.type.maxDurability) {
                    inv.remove(inv.itemInMainHand)
                    player.playSound(player.location, Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F)
                }
                else {
                    inv.itemInMainHand.durability = dura
                }
            }
            else -> return
        }

    }



}