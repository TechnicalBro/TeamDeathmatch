package com.caved_in.teamdeathmatch.runnables;

import com.caved_in.commons.player.Players;
import com.caved_in.teamdeathmatch.GameMessages;
import com.caved_in.teamdeathmatch.vote.Vote;
import com.caved_in.teamdeathmatch.vote.VoteFactory;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class ExecuteVoteRunnable implements Runnable {
	@Override
	public void run() {
		Vote activeVote = VoteFactory.getActiveVote();
		if (activeVote.canExecute()) {
			activeVote.execute();
		} else {
			Players.sendMessageToAllPlayers(GameMessages.VOTE_FAILED(activeVote));
		}
		VoteFactory.clearActiveVote();
	}
}
