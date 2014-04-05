package louis.server.dewardt;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class WorldData {
	World world;
	int id;
	boolean canSpawHostile = true;
	public WorldData(World world, int Id)
	{
		this.id = Id;
		this.world = world;
	}
	public WorldData(String world, int Id)
	{
		if(Bukkit.getServer().getWorld(world) == null)
		{
			Bukkit.getServer().createWorld(new WorldCreator(world));
		}
	}
	public void setSpawnHostileAble(boolean can)
	{
		this.canSpawHostile = can;
	}
	public boolean canSpawnHostile()
	{
		return this.canSpawHostile;
	}
	public int getID()
	{
		return this.id;
	}
	public World getWorld()
	{
		return this.world;
	}
}
