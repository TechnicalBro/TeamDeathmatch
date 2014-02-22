package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.GamePlayer;
import com.caved_in.teamdeathmatch.gamehandler.GameSetupHandler;
import com.caved_in.teamdeathmatch.perks.Perk;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class RestoreInventory implements Runnable {
	private ItemStack[] inventory = null;
	private String playerName = "";

	public RestoreInventory(String playerName) {
		this.playerName = playerName;
		this.inventory = FakeboardHandler.getPlayer(playerName).getDeathInventory();
	}

	@Override
	public void run() {
		Player rPlayer = Bukkit.getPlayer(playerName);
		if (rPlayer != null) {
			GamePlayer gamePlayer = FakeboardHandler.getPlayer(rPlayer);


			rPlayer.getInventory().setArmorContents(gamePlayer.getTeam().equalsIgnoreCase("T") ? GameSetupHandler.getBlueTeamArmor() : GameSetupHandler.getRedTeamArmor());

			Perk playerPerk = gamePlayer.getActivePerk();
			if (playerPerk != null) {
				if (!playerPerk.getPerkName().equalsIgnoreCase("Nothing")) {
					for (PotionEffect Effect : playerPerk.getEffects()) {
						rPlayer.addPotionEffect(Effect);
					}
				}
			}
			rPlayer.getInventory().setContents(this.inventory);
			rPlayer.updateInventory();
		}

	}

}
