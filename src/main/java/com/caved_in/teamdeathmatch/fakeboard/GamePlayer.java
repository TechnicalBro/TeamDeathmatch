package com.caved_in.teamdeathmatch.fakeboard;

import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.potions.PotionHandler;
import com.caved_in.teamdeathmatch.TDMGame;
import com.caved_in.teamdeathmatch.guns.GunWrapper;
import com.caved_in.teamdeathmatch.loadout.Loadout;
import com.caved_in.teamdeathmatch.perks.Perk;
import com.caved_in.teamdeathmatch.perks.Perks.Nothing;
import com.caved_in.teamdeathmatch.scoreboard.PlayerScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class GamePlayer {
	private static final int PREMIUM_LOADOUT_LIMIT = 9;
	private static final int NON_PREMIUM_LOADOUT_LIMIT = 4;

	private int loadoutSlots = NON_PREMIUM_LOADOUT_LIMIT;

	private String name = "";
	private String teamName = "";

	private int playerScore = 0;
	private int killStreak = 0;
	private int playerDeaths = 0;

	private static final int afkDuration = (20 * 60) * 10;

	private String primaryGunID = "AK-47";
	private String secondaryGunID = "USP45";

	private PlayerScoreboard playerScoreboard;

	private Perk activePerk;

	private boolean afk = false;

	private Set<Perk> playerPerks = new HashSet<>();

	private HashMap<Integer, Loadout> playerLoadouts = new HashMap<>();

	private Set<String> unlockedGuns = new HashSet<>();

	private ItemStack[] deathInventory = new ItemStack[]{};

	/**
	 * Initiates a new GamePlayer Instance
	 *
	 * @param playerName Players Name
	 */
	public GamePlayer(String playerName) {
		this.name = playerName;
		this.activePerk = new Nothing();
		defaultPlayerData();
		setupPlayerData();
		//Instance the players loadout slots
		loadoutSlots = getPlayer().isWhitelisted() ? PREMIUM_LOADOUT_LIMIT : NON_PREMIUM_LOADOUT_LIMIT;

	}

	public void setPlayerScoreboard(PlayerScoreboard playerScoreboard) {
		this.playerScoreboard = playerScoreboard;
	}

	/**
	 * Initiates a new GamePlayer Instance
	 *
	 * @param player Player to make new GamePlayer of
	 */
	public GamePlayer(Player player) {
		this(player.getName());
	}

	public void defaultPlayerData() {
		if (!TDMGame.loadoutSQL.hasData(name)) {
			List<Loadout> loadouts = new ArrayList<>();
			for (int i = 0; i < getLoadoutLimit(); i++) {
				loadouts.add(new Loadout(name, i + 1, primaryGunID, secondaryGunID, activePerk));
			}
			TDMGame.loadoutSQL.insertLoadouts(loadouts);
		}

		if (!TDMGame.perksSQL.hasData(name)) {
			TDMGame.perksSQL.insertPerk(new Nothing(), name);
		}

		TDMGame.gunsSQL.insertGuns(name, TDMGame.gunHandler.getDefaultGunMap().keySet());
	}

	public void setupPlayerData() {
		playerLoadouts.putAll(TDMGame.loadoutSQL.getLoadouts(name));
		playerPerks.addAll(TDMGame.perksSQL.getPerks(name));
		unlockedGuns.addAll(TDMGame.gunsSQL.getGuns(name));
	}


	public PlayerScoreboard getPlayerScoreboard() {
		return playerScoreboard;
	}

	public void updateScoreboard() {
		playerScoreboard.updateScoreboardData(this);
	}

	public void clearScoreboard() {
		if (playerScoreboard != null) {
			playerScoreboard.clearScoreboard();
		}
	}

	public boolean hasGun(String gunName) {
		return unlockedGuns.contains(gunName);
	}

	public boolean hasGun(GunWrapper gunWrapper) {
		return hasGun(gunWrapper.getGunName());
	}

	public void unlockGun(String gunId) {
		if (!unlockedGuns.contains(gunId)) {
			unlockedGuns.add(gunId);
			TDMGame.gunsSQL.insertGun(name, gunId);
		}
	}


	public int getLoadoutLimit() {
		return loadoutSlots;
	}

	public boolean hasPerk(Perk perk) {
		return playerPerks.contains(perk);
	}

	public void addPerk(Perk perk) {
		playerPerks.add(perk);
		TDMGame.perksSQL.insertPerk(perk, name);
	}

	public String getName() {
		return this.name;
	}

	public Player getPlayer() {
		return PlayerHandler.getPlayer(name);
	}

	public int getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(int score) {
		playerScore = score;
	}

	public void addScore(int amount) {
		playerScore += amount;
	}

	public void setTeam(String teamName) {
		this.teamName = teamName;
	}

	public String getTeam() {
		return teamName;
	}

	public int getKillStreak() {
		return killStreak;
	}

	public void resetKillstreak() {
		killStreak = 0;
	}

	public void addKillstreak(int amount) {
		killStreak += amount;
	}

	public String getPrimaryGunID() {
		return primaryGunID;
	}

	public String getPrimaryGunID(int loadoutNumber) {
		return getLoadout(loadoutNumber).getPrimary();
	}

	public String getSecondaryGunID() {
		return secondaryGunID;
	}

	public String getSecondaryGunID(int loadoutNumber) {
		return getLoadout(loadoutNumber).getSecondary();
	}

	public Perk getPerk(int loadoutNumber) {
		return getLoadout(loadoutNumber).getPerk();
	}

	public void setActiveLoadout(int loadoutNumber) {
		Loadout selectedLoad = getLoadout(loadoutNumber);
		if (selectedLoad != null) {
			primaryGunID = selectedLoad.getPrimary();
			secondaryGunID = selectedLoad.getSecondary();
			activePerk = selectedLoad.getPerk();
		}
	}

	public ItemStack[] getDeathInventory() {
		return this.deathInventory;
	}

	public void setDeathInventory(ItemStack[] deathInventory) {
		this.deathInventory = deathInventory;
	}

	public Loadout getLoadout(int loadoutNumber) {
		return playerLoadouts.get(loadoutNumber);
	}

	public void giveActiveLoadout() {
		Player player = this.getPlayer();
		TDMGame.crackShotAPI.giveWeapon(player, getPrimaryGunID(), 1);
		TDMGame.crackShotAPI.giveWeapon(player, getSecondaryGunID(), 1);

		for (PotionEffect potionEffect : getActivePerk().getEffects()) {
			player.addPotionEffect(potionEffect);
		}
	}

	public void setAfk(boolean isAfk) {
		setAfk(isAfk, true);
	}

	public void setAfk(boolean isAfk, boolean message) {
		this.afk = isAfk;
		Player player = getPlayer();
		if (isAfk) {
			PlayerHandler.addPotionEffect(player, PotionHandler.getPotionEffect(PotionEffectType.INVISIBILITY, 1, afkDuration));
		} else {
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
		}
		if (message) {
			PlayerHandler.sendMessage(player, "&7You are " + (isAfk ? "now" : "no longer") + " afk");

		}
	}

	public void toggleAfk() {
		setAfk(!afk);
	}

	public boolean isAfk() {
		return this.afk;
	}

	public Perk getActivePerk() {
		return this.activePerk;
	}

	public int getPlayerDeaths() {
		return this.playerDeaths;
	}

	public void resetDeaths() {
		this.playerDeaths = 0;
	}

	public void addDeath() {
		this.playerDeaths += 1;
	}

	@Override
	public boolean equals(Object o) {
		//If it's null, then it's not this object
		if (o == null) {
			return false;
		}
		//If it's this instance, then yes
		if (o == this) {
			return true;
		}
		//It's not a player, or a game player
		if (!(o instanceof GamePlayer) && !(o instanceof Player)) {
			return false;
		}

		if (o instanceof GamePlayer) {
			GamePlayer gamePlayer = (GamePlayer) o;
			return gamePlayer.getName().equals(name);
		} else {
			Player player = (Player) o;
			return player.getName().equals(name);
		}
	}
}
