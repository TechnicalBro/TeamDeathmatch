package com.caved_in.teamdeathmatch.vote;

import com.caved_in.commons.Commons;
import com.caved_in.commons.player.Players;
import com.caved_in.teamdeathmatch.Game;
import com.caved_in.teamdeathmatch.GameMessages;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class VoteMap extends Vote {
	private String map = "";

	public VoteMap(String voteCaster, String[] args) {
		super(voteCaster, args);
		addYes();
		setVoted(voteCaster);
		this.map = args[0];
	}

	@Override
	public void execute() {
		Commons.threadManager.runTaskOneTickLater(new Runnable() {

			@Override
			public void run() {
				Game.gameMap = map;
				Players.sendMessageToAllPlayers(GameMessages.MAP_CHANGED(map));
			}
		});
	}

	@Override
	public void announce() {
		String mapChangeAnnoucnement = GameMessages.ANNOUNCE_VOTE_MAP_CHANGE(getCaster(), map);
		Players.sendMessageToAllPlayers(mapChangeAnnoucnement);
	}
}
