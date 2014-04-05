package louis.server.dewardt;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface MiniGameListener {
	/**This is called from the instance of the class and is called when the player clicks on
	 * the appropriate item.
	 * @param 
	 * Player the player that clicked on the icon.
	 */
	public void onPlayerJoin(Player player);
	public void onJoinCommand(CommandSender sender, Command cmd, String label, String[] args);
	public void onLeaveCommand(CommandSender sender, Command cmd, String label, String[] args);
}
