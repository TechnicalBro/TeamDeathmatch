package com.caved_in.teamdeathmatch.commands;

import com.caved_in.commons.commands.CommandController;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import com.caved_in.teamdeathmatch.menus.loadoutselector.LoadoutSelectionMenu;
import org.bukkit.entity.Player;

import static com.caved_in.teamdeathmatch.GameMessages.*;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class KitCommand {

	@CommandController.CommandHandler(name = "kit", description = "Select & Set your active loadout", usage = "/kit")
	public void onKitCommand(Player player, String[] args) {
		if (GameSetupHandler.isGameInProgress()) {
			try {
				PlayerHandler.sendMessage(player, LOADOUT_EDIT_INSTRUCTION);
				new LoadoutSelectionMenu(player);
			} catch (Exception ex) {
				ex.printStackTrace();
				PlayerHandler.kickPlayer(player, PLAYER_DATA_LOAD_ERROR);
			}
		} else {
			PlayerHandler.sendMessage(player, GAME_MUST_BEGIN_LOADOUT_SELECTION);
		}
	}
}