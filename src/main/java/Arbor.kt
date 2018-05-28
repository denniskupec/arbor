
package nu.baud.arbor

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

fun main(args: Array<String>){}

class Arbor : JavaPlugin() {

    companion object {
        var instance: Arbor? = null
        private set
    }

    override fun onEnable() {
        Bukkit.getLogger().info("Arbor plugin enabled.")
        Bukkit.getPluginManager().registerEvents(TreeListener, this)

        instance = this
    }

    override fun onDisable() {
        Bukkit.getLogger().info("Arbor plugin disabled")
    }


}