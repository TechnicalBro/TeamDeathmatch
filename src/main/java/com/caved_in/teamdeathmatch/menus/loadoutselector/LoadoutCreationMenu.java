package com.caved_in.teamdeathmatch.menus.loadoutselector;

import com.caved_in.commons.item.Items;
import com.caved_in.teamdeathmatch.Game;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.GamePlayer;
import me.xhawk87.PopupMenuAPI.PopupMenu;
import me.xhawk87.PopupMenuAPI.PopupMenuAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import java.util.Arrays;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class LoadoutCreationMenu {

	public LoadoutCreationMenu(Player player) {
		getMenu(player).openMenu(player);
	}

	public LoadoutCreationMenu() {

	}

	public PopupMenu getMenu(Player player) {
		PopupMenu loadoutCreationMenu = PopupMenuAPI.createMenu("Create a Loadout", 1);
		GamePlayer GamePlayer = FakeboardHandler.getPlayer(player);
		int loadoutLimit = GamePlayer.getLoadoutLimit();
		for (int I = 0; I < loadoutLimit; I++) {
			int loadoutNumber = I + 1;
			LoadoutCreationItem loadoutCreationItem = new LoadoutCreationItem("Loadout #" + loadoutNumber, new MaterialData(Material.CHEST), loadoutNumber);
			loadoutCreationItem.setDescriptions(Arrays.asList(Items.getItemName(Game.crackShotAPI.generateWeapon(GamePlayer.getPrimaryGunID
					(loadoutNumber))), Items.getItemName(Game.crackShotAPI.generateWeapon(GamePlayer.getSecondaryGunID(loadoutNumber)))));
			loadoutCreationMenu.addMenuItem(loadoutCreationItem, I);
		}

		loadoutCreationMenu.setExitOnClickOutside(false);
		return loadoutCreationMenu;
	}
}
