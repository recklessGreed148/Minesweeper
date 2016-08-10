package de.recklessGreed;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by recklessGreed on 08.08.16.
 */
public class tnt_Main extends JavaPlugin
{
    Listener tnt_events;
    tnt_Main instance;


    @Override
    public void onDisable()
    {
        super.onDisable();
    }

    @Override
    public void onEnable()
    {
        instance = this;
        tnt_events = new tnt_Listeners(instance);
        Bukkit.getServer().getPluginManager().registerEvents(tnt_events,this);

        super.onEnable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        return super.onCommand(sender, command, label, args);
    }





    public tnt_Main getInstance()
    {
        return instance;
    }
}
