package louis.server.dewardt;

import java.util.logging.Level;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class CharityServer extends JavaPlugin{
	@Override
	public void onEnable()
	{
		PVP.onEnable();
		LayerSpleef.onEnable();
		getLogger().log(Level.INFO, "Starting up!");
		AddWorlds.onEnable();
		getServer().getPluginManager().registerEvents(new Lobby(),this);
		getServer().getPluginManager().registerEvents(new Rank(),this);
		getServer().getPluginManager().registerEvents(new MinigameCls(),this);
		getCommand("join").setExecutor(new MinigameCls());
		getCommand("leave").setExecutor(new MinigameCls());


	}
	
	@Override
	public void onDisable()
	{
		getLogger().log(Level.INFO, "Shuting down!");
	}
}
