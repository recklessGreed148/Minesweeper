package de.recklessGreed;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by recklessGreed on 08.08.16.
 */
public class tnt_Listeners implements Listener
{
    tnt_Listeners instance;
    tnt_Main mainClass;

    public tnt_Listeners(tnt_Main main)
    {
        mainClass = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event)
    {

    }

    public Inventory createFieldInventory()
    {
        Inventory creator = Bukkit.createInventory(null, 63, "Feldgröße");
        ItemStack plus = new ItemStack(Material.EMERALD_BLOCK, 1);
        ItemStack minus = new ItemStack(Material.REDSTONE_BLOCK,1);
        plus.getItemMeta().setDisplayName("+1");
        minus.getItemMeta().setDisplayName("-1");
        ItemStack width = new ItemStack(Material.COAL_BLOCK, 1);
        ItemStack height = new ItemStack(Material.COAL_BLOCK, 1);
        creator.setItem(11, minus);
        creator.setItem(13, widthBlock);
        creator.setItem(15, plus);
        creator.setItem(29, minus);
        creator.setItem(31, heightBlock);
        creator.setItem(33, plus);
        creator.setItem(47, minus);
        creator.setItem(49, tntCount);
        creator.setItem(51, plus);

    }


    public tnt_Listeners getInstance()
    {
        return instance;
    }
}
