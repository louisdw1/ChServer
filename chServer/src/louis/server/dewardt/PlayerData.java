package louis.server.dewardt;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerData extends JavaPlugin{
	public static Map<String, String> playerWorld = new HashMap<String,String>();
	public void onEnable()
	{
		//Load Maps
		 try{
				playerWorld = SLAPI.load("playerWorld.bin");

		            }catch(Exception e){
		                //handle the exception
		                getLogger().warning("Couldn't load Save file: "+e.getStackTrace());
		            }
	}
	public void onDisable()
	{
		 try{
				SLAPI.save(playerWorld,"PlayerWorld.bin");

		            }catch(Exception e){
		                //handle the exception
		                getLogger().warning("Couldn't load Save file: "+e.getStackTrace());
		            }

	}
	public static String getPlayerWorld(Player player)
	{
		if(!playerWorld.containsKey(player.getName()))
		{
			return null;
		}
		return playerWorld.get(player.getName());
	}
	public static void setWorld(String world,Player player)
	{
		playerWorld.put(player.getName(), world);
	}
}
