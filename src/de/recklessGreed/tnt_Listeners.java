package de.recklessGreed;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

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

        Bukkit.getScheduler().scheduleSyncDelayedTask(mainClass,
                () -> player.openInventory(createFieldInventory(player)),40L);

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();

        if(mainClass.playerWorldMap.containsKey(player))
        {
            player.kickPlayer(ChatColor.AQUA + "Danke das du MineSweeper gespielt hast!");
            World world = mainClass.playerWorldMap.get(player);
            Bukkit.unloadWorld(world, false);
            //delete World!!!!
            //google !
        }
        else
        {
            // Do Stuff
        }
    }

    /*
        Finished Join/Leave Section
     */

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event)
    {
        Inventory inv = event.getInventory();
        Player player = (Player) event.getPlayer();

        if(inv.getName().equals("Feldgröße"))
        {
            int width = inv.getItem(13).getAmount();
            int height = inv.getItem(31).getAmount();
            int numberOfFields = width*height;
            int tntCount = inv.getItem(49).getAmount();
            double percentage = tntCount / numberOfFields;

            //create New World
            File srcFolder = new File("./template");
            File destFolder = new File("./gamemap_"+player.getName());
            try
            {
                mainClass.copyFolder(srcFolder, destFolder);
                World gameWorld = Bukkit.createWorld(new WorldCreator("gamemap_"+player.getName()));
                //create Field with correct Size
                Location spawn = gameWorld.getSpawnLocation();

                for(int w = 0; w < width; w++)
                {
                    for(int h = 0; h < height; h++)
                    {
                        Location currentGame = new Location(gameWorld, spawn.getX()+ w, spawn.getY()-1, spawn.getZ()+ h);
                        Location currentCover = new Location(gameWorld, spawn.getX()+ w, spawn.getY(), spawn.getZ()+ h);

                        //cover up
                        currentCover.getBlock().setType(Material.SAND);
                    }
                }

                //createMines(); // The question is how!

                //give Player the correct Items
                player.teleport(spawn);
            } catch (IOException e)
            {
                e.printStackTrace();
                System.out.println(ChatColor.RED + "Either Source or Destination not accesable!");
            }


        }
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
            else if (clickedItem.getType() == Material.REDSTONE_BLOCK) //decrease
            {
                ItemStack copyOf;
                if (event.getSlot() / 9 == 1) //width
                {
                    copyOf = inv.getItem(13).clone();
                    if(copyOf.getAmount() >= 6)
                        copyOf.setAmount(copyOf.getAmount()-1);
                    inv.setItem(13, copyOf);
                }
                else if (event.getSlot() / 9 == 3) //height
                {
                    copyOf = inv.getItem(31).clone();
                    if(copyOf.getAmount() >= 6)
                        copyOf.setAmount(copyOf.getAmount()-1);
                    inv.setItem(31, copyOf);
                }
                else if (event.getSlot() / 9 == 5) //tntCount
                {
                    copyOf = inv.getItem(49).clone();
                    if(copyOf.getAmount() >= (inv.getItem(13).getAmount() * inv.getItem(31).getAmount())/10)
                        copyOf.setAmount(copyOf.getAmount()-1);
                    inv.setItem(49, copyOf);
                }
            }
            else if (clickedItem.getType() == Material.EMERALD_BLOCK) //increase
            {
                ItemStack copyOf;
                if (event.getSlot() / 9 == 1) //width
                {
                    copyOf = inv.getItem(13).clone();
                    if(copyOf.getAmount() <= 11)
                        copyOf.setAmount(copyOf.getAmount()+1);
                    inv.setItem(13, copyOf);
                }
                else if (event.getSlot() / 9 == 3) //height
                {
                    copyOf = inv.getItem(31).clone();
                    if(copyOf.getAmount() <= 11)
                        copyOf.setAmount(copyOf.getAmount()+1);
                    inv.setItem(31, copyOf);
                }
                else if (event.getSlot() / 9 == 5) //tntCount
                {
                    copyOf = inv.getItem(49).clone();
                    if(copyOf.getAmount() < (inv.getItem(13).getAmount() * inv.getItem(31).getAmount())*0.9)
                        copyOf.setAmount(copyOf.getAmount()+1);
                    inv.setItem(49, copyOf);
                }
            }
            event.setCancelled(true);
        }
    }

    public Inventory createFieldInventory(Player player)
    {
        Inventory creator = Bukkit.createInventory(player, 54, "Feldgröße");


        ItemStack plus = new ItemStack(Material.EMERALD_BLOCK, 1);
        ItemStack minus = new ItemStack(Material.REDSTONE_BLOCK,1);
        plus.getItemMeta().setDisplayName("+1");
        minus.getItemMeta().setDisplayName("-1");


        ItemStack widthBlock = new ItemStack(Material.COAL_BLOCK, 5);
        ItemStack heightBlock = new ItemStack(Material.COAL_BLOCK, 5);
        ItemStack tntCount = new ItemStack(Material.COAL_BLOCK,10);

        widthBlock.getItemMeta().setDisplayName("Feld Breite");
        heightBlock.getItemMeta().setDisplayName("Feld Höhe");
        tntCount.getItemMeta().setDisplayName("Anzahl an Minen");

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

    /*
        Finished Inventory Section
     */

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if(block.getType().equals(Material.SAND))
        {
            Location checkAt = block.getLocation().add(0,-1,0);
            Block gameBlock = checkAt.getBlock();

            if(gameBlock.getType().equals(Material.TNT))
            {
                //Game Ends
                //replace every Block with tnt or RedstoneBlock

                Location playerLoc = player.getLocation();
                for(int i = 1; i < 5; i++)
                {
                    playerLoc.add(i*1 ,0,i*1).getBlock().setType(Material.TNT);
                    playerLoc.add(-i*1,0,i*1).getBlock().setType(Material.TNT);
                    playerLoc.add(i*1 ,0,-i*1).getBlock().setType(Material.TNT);
                    playerLoc.add(-i*1,0,-i*1).getBlock().setType(Material.TNT);
                    playerLoc.add(i*1 ,-1,i*1).getBlock().setType(Material.REDSTONE_BLOCK);
                    playerLoc.add(-i*1,-1,i*1).getBlock().setType(Material.REDSTONE_BLOCK);
                    playerLoc.add(i*1 ,-1,-i*1).getBlock().setType(Material.REDSTONE_BLOCK);
                    playerLoc.add(-i*1,-1,-i*1).getBlock().setType(Material.REDSTONE_BLOCK);
                }

                Bukkit.getScheduler().scheduleSyncDelayedTask(mainClass, () -> player.kickPlayer(
                        ChatColor.AQUA + "Danke das du Minesweeper gespielt hast."), 200L);
            }
            else
            {
                //checkSurroundings
            }
        }
    }

    /*
        Finished Block Section
     */

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event)
    {
        Entity entity = event.getEntity();
        if(entity instanceof Player)
        {
            Player player = (Player) entity;
            //if(player.hasMetadata("tnt_ingame"))
            event.setCancelled(true);
        }
    }

    /*
        Finished Damage Section
     */

    public tnt_Listeners getInstance()
    {
        return instance;
    }
}
