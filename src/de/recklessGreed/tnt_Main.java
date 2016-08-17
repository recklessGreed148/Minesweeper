package de.recklessGreed;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;

/**
 * Created by recklessGreed on 08.08.16.
 */
public class tnt_Main extends JavaPlugin
{
    Listener tnt_events;
    tnt_Main instance;
    HashMap<Player, World> playerWorldMap = new HashMap<Player, World>();



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

    public static void copyFolder(File src, File dest) throws IOException
    {

        if (src.isDirectory())
        {

            //if directory not exists, create it
            if (!dest.exists())
            {
                dest.mkdir();
            }

            //list all the directory contents
            String files[] = src.list();

            for (String file : files)
            {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                copyFolder(srcFile, destFile);
            }

        }
        else
        {
            //if file, then copy it
            //Use bytes stream to support all file types
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes
            while ((length = in.read(buffer)) > 0)
            {
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();
        }
    }
}
