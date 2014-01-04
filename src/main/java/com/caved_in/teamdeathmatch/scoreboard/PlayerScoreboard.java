package com.caved_in.teamdeathmatch.scoreboard;

import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.teamdeathmatch.fakeboard.FakeboardHandler;
import com.caved_in.teamdeathmatch.fakeboard.fPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerScoreboard {
	private String dummyObjectiveName = ChatColor.RESET.toString();
	private Objective objective;
	private Scoreboard scoreboard;

	private String terroristScore = ChatColor.GOLD + "Terrorist:";
	private String counterTerroristScore = ChatColor.GOLD + "CTerrorist:";
	private String killsScore = ChatColor.AQUA + "Kills:";
	private String deathsScore = ChatColor.RED + "Deaths:";
	private String killsStreak = ChatColor.YELLOW + "Kill Streak:";
	private String XP = ChatColor.GREEN + "XP:";


	public PlayerScoreboard() {
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		objective = scoreboard.registerNewObjective(dummyObjectiveName, "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName("Lock & Load");
	}

	public void clearScoreboard() {
		scoreboard.resetScores(getName(terroristScore));
		scoreboard.resetScores(getName(counterTerroristScore));
		scoreboard.resetScores(getName(killsScore));
		scoreboard.resetScores(getName(deathsScore));
		scoreboard.resetScores(getName(killsStreak));
		scoreboard.resetScores(getName(XP));
		//this.Board.clearSlot(DisplaySlot.SIDEBAR);
	}

	public Scoreboard getScoreboard() {
		return this.scoreboard;
	}

	public void updateScoreboardData(fPlayer player) {
		Score terroristScore = objective.getScore(getName(this.terroristScore));
		Score counterTerroristScore = objective.getScore(getName(this.counterTerroristScore));
		Score playerKillScore = objective.getScore(getName(killsScore));
		Score playerDeathScore = objective.getScore(getName(deathsScore));
		Score playerKillStreak = objective.getScore(getName(killsStreak));
		Score playerXPScore = objective.getScore(getName(XP));
		terroristScore.setScore(FakeboardHandler.getTeam("T").getTeamScore());
		counterTerroristScore.setScore(FakeboardHandler.getTeam("CT").getTeamScore());
		playerKillScore.setScore(player.getPlayerScore());
		playerDeathScore.setScore(player.getPlayerDeaths());
		playerKillStreak.setScore(player.getKillStreak());
		playerXPScore.setScore((int) PlayerHandler.getData(player.getPlayerName()).getCurrency());
	}

	public enum ScoreType {
		Terrorist, CounterTerrorist, Kills, Deaths, Killstreak
	}

	public void updateData(ScoreType score, fPlayer playerWrapper) {
		switch (score) {
			case CounterTerrorist:
				Score ctScore = this.objective.getScore(getName(counterTerroristScore));
				ctScore.setScore(FakeboardHandler.getTeam("CT").getTeamScore());
				break;
			case Deaths:
				Score dScore = objective.getScore(getName(deathsScore));
				dScore.setScore(playerWrapper.getPlayerDeaths());
				break;
			case Kills:
				Score kScore = objective.getScore(getName(killsScore));
				kScore.setScore(playerWrapper.getPlayerScore());
				break;
			case Killstreak:
				Score ksScore = objective.getScore(getName(killsScore));
				ksScore.setScore(playerWrapper.getKillStreak());
				break;
			case Terrorist:
				Score tScore = objective.getScore(getName(terroristScore));
				tScore.setScore(FakeboardHandler.getTeam("T").getTeamScore());
				break;
			default:
				break;
		}
	}

	public OfflinePlayer getName(String Name) {
		return Bukkit.getOfflinePlayer(Name);
	}
}