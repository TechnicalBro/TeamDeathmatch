package com.caved_in.teamdeathmatch.guns;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public enum GunType {
	SHOTGUN("shotgun"),
	SNIPER("sniper"),
	ASSAULT("assault"),
	PISTOL("pistol"),
	SPECIAL("special");

	private String category;

	GunType(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return this.category;
	}
}
