
package com.kk.evemarket.auth.provider;

public class Constants {
	public static final String CLIENT_ID = "931abd14a80b488ab77c21f69570c647";
	public static final String SECRET_KEY = "padI7WNHZHbDNg8cHXXtcCG6rtre2BrgWTBPe3mZ";
	public static final int SERVER_PORT = 55005;
	public static final String REDIRECT = "http://localhost:55005/";
	public static final String AUTH_URL = "https://login.eveonline.com/oauth/authorize";
	public static final String ACCESS_TOKEN_URL = "https://login.eveonline.com/oauth/token";
	public static final String AUTH_VERIFY_URL = "https://login.eveonline.com/oauth/verify";
	public static String[] SCOPES = new String[] { "esi-assets.read_assets.v1",
			"esi-bookmarks.read_character_bookmarks.v1", "esi-calendar.read_calendar_events.v1",
			"esi-calendar.respond_calendar_events.v1", "esi-characters.read_contacts.v1", "esi-clones.read_clones.v1",
			"esi-corporations.read_corporation_membership.v1", "esi-fleets.read_fleet.v1", "esi-fleets.write_fleet.v1",
			"esi-killmails.read_killmails.v1", "esi-location.read_location.v1", "esi-location.read_ship_type.v1",
			"esi-mail.organize_mail.v1", "esi-mail.read_mail.v1", "esi-mail.send_mail.v1",
			"esi-planets.manage_planets.v1", "esi-search.search_structures.v1", "esi-skills.read_skillqueue.v1",
			"esi-skills.read_skills.v1", "esi-universe.read_structures.v1", "esi-wallet.read_character_wallet.v1" };
	public static final long TOKEN_REFRESH_PERIOD = 19 * 60000;
}
