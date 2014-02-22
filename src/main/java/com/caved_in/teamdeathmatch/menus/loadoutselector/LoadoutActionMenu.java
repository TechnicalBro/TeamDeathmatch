package com.caved_in.teamdeathmatch.menus.loadoutselector;

import com.caved_in.teamdeathmatch.menus.closebehaviours.LoadoutMenuCloseBehaviour;
import me.xhawk87.PopupMenuAPI.PopupMenu;
import org.bukkit.entity.Player;

public class LoadoutActionMenu extends PopupMenu {

	public LoadoutActionMenu(Player player) {
		super("Select & Edit Loadouts",1);
		setMenuCloseBehaviour(LoadoutMenuCloseBehaviour.getInstance());
		addMenuItem(new LoadoutItem(LoadoutItem.LoadoutAction.SELECT),0);
		addMenuItem(new LoadoutItem(LoadoutItem.LoadoutAction.EDIT),1);
		openMenu(player);
	}
}
