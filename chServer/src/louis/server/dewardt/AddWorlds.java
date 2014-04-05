package louis.server.dewardt;


public class AddWorlds{
	
	public static void onEnable()
	{
		PlayerWorld.addWorldData(new WorldData("world_"+PlayerWorld.LOBBY,PlayerWorld.LOBBY));
		PlayerWorld.addWorldData(new WorldData("world_"+PlayerWorld.LAYERSPLEEF,PlayerWorld.LAYERSPLEEF));
		PlayerWorld.addWorldData(new WorldData("world_"+PlayerWorld.PVP,PlayerWorld.PVP));

	}
}
