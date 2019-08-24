package com.kk.evemarket.common.trade;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CharacterInfo {
	private String characterId;
	private String characterName;
	private LocalDateTime expiresOn;
	private String scopes;
	private String tokenType;
	private String characterOwnerHash;
	private String intellectualProperty;

	public CharacterInfo() {
		// TODO Auto-generated constructor stub
	}

	@JsonProperty("CharacterID")
	public String getCharacterID() {
		return characterId;
	}

	public void setCharacterID(String characterId) {
		this.characterId = characterId;
	}

	@JsonProperty("CharacterName")
	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	@JsonProperty("ExpiresOn")
	public LocalDateTime getExpiresOn() {
		return expiresOn;
	}

	public void setExpiresOn(LocalDateTime expiresOn) {
		this.expiresOn = expiresOn;
	}

	@JsonProperty("Scopes")
	public String getScopes() {
		return scopes;
	}

	public void setScopes(String scopes) {
		this.scopes = scopes;
	}

	@JsonProperty("TokenType")
	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	@JsonProperty("CharacterOwnerHash")
	public String getCharacterOwnerHash() {
		return characterOwnerHash;
	}

	public void setCharacterOwnerHash(String characterOwnerHash) {
		this.characterOwnerHash = characterOwnerHash;
	}

	@JsonProperty("IntellectualProperty")
	public String getIntellectualProperty() {
		return intellectualProperty;
	}

	public void setIntellectualProperty(String intellectualProperty) {
		this.intellectualProperty = intellectualProperty;
	}
}
