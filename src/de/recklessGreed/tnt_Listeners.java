package de.recklessGreed;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
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

    ItemStack widthBlock = new ItemStack(Material.COAL_BLOCK, 5);
    ItemStack heightBlock = new ItemStack(Material.COAL_BLOCK, 5);
    ItemStack tntCount = new ItemStack(Material.COAL_BLOCK,10);

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

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        Inventory inv = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if(inv.getName().equals("Feldgröße"))
        {
            if (clickedItem.getType() == Material.COAL_BLOCK)
            {
                if (event.getSlot() / 9 == 1)
                {

                }
                else if (event.getSlot() / 9 == 3)
                {

                }
                else if (event.getSlot() / 9 == 5)
                {

                }

            }
            else if (clickedItem.getType() == Material.REDSTONE_BLOCK)
            {
                ItemStack copyOf;
                if (event.getSlot() / 9 == 1)
                {
                    copyOf = event.getCurrentItem().clone();
                    if(copyOf.getAmount() >= 6)
                        copyOf.setAmount(copyOf.getAmount()-1);
                    inv.setItem(event.getSlot(), copyOf);
                }
                else if (event.getSlot() / 9 == 3)
                {
                    copyOf = event.getCurrentItem().clone();
                    if(copyOf.getAmount() >= 6)
                        copyOf.setAmount(copyOf.getAmount()-1);
                    inv.setItem(event.getSlot(), copyOf);
                }
                else if (event.getSlot() / 9 == 5)
                {
                    copyOf = event.getCurrentItem().clone();
                    if(copyOf.getAmount() >= (inv.getItem(13).getAmount() * inv.getItem(31).getAmount())/10)
                        copyOf.setAmount(copyOf.getAmount()-1);
                    inv.setItem(event.getSlot(), copyOf);
                }
            }
            else if (clickedItem.getType() == Material.EMERALD_BLOCK)
            {
                ItemStack copyOf;
                if (event.getSlot() / 9 == 1)
                {
                    copyOf = event.getCurrentItem().clone();
                    if(copyOf.getAmount() <= 11)
                        copyOf.setAmount(copyOf.getAmount()+1);
                    inv.setItem(event.getSlot(), copyOf);
                }
                else if (event.getSlot() / 9 == 3)
                {
                    copyOf = event.getCurrentItem().clone();
                    if(copyOf.getAmount() <= 11)
                        copyOf.setAmount(copyOf.getAmount()+1);
                    inv.setItem(event.getSlot(), copyOf);
                }
                else if (event.getSlot() / 9 == 5)
                {
                    copyOf = event.getCurrentItem().clone();
                    if(copyOf.getAmount() < (inv.getItem(13).getAmount() * inv.getItem(31).getAmount())*0.9)
                        copyOf.setAmount(copyOf.getAmount()+1);
                    inv.setItem(event.getSlot(), copyOf);
                }
            }
            event.setCancelled(true);
        }
    }

    public Inventory createFieldInventory()
    {
        Inventory creator = Bukkit.createInventory(null, 63, "Feldgröße");
        ItemStack plus = new ItemStack(Material.EMERALD_BLOCK, 1);
        ItemStack minus = new ItemStack(Material.REDSTONE_BLOCK,1);
        plus.getItemMeta().setDisplayName("+1");
        minus.getItemMeta().setDisplayName("-1");

        creator.setItem(11, minus);
        creator.setItem(13, widthBlock);
        creator.setItem(15, plus);
        creator.setItem(29, minus);
        creator.setItem(31, heightBlock);
        creator.setItem(33, plus);
        creator.setItem(47, minus);
        creator.setItem(49, tntCount);
        creator.setItem(51, plus);
        return creator;
    }


    public tnt_Listeners getInstance()
    {
        return instance;
    }
}
