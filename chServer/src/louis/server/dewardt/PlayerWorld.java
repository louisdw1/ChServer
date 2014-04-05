package louis.server.dewardt;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class PlayerWorld {
	public static int LOBBY = 0;
	public static int LAYERSPLEEF = 1;
	public static int PVP = 2;

	public static ArrayList<WorldData> worldD = new ArrayList<WorldData>();
	public static void addWorldData(WorldData wd)
	{
		worldD.add(wd);
	}
	public WorldData getWorldDataFromID(int id)
	{
		int i = 0;
		while(i<worldD.size())
		{
			if(worldD.get(i).id==id)
			{
				return worldD.get(i);
			}
			i++;
		}
		return new WorldData(Bukkit.getWorld("world"),-1);
	}
	public static WorldData getWorldDataFromWorld(World world)
	{
		int i = 0;
		while(i<worldD.size())
		{
			if(worldD.get(i).world==world)
			{
				return worldD.get(i);
			}
			i++;
		}
		return new WorldData(Bukkit.getWorld("world"),-1);
	}
}
