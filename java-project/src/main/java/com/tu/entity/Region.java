package com.tu.entity;
// Generated Oct 25, 2018 2:31:40 PM by Hibernate Tools 5.2.11.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Region generated by hbm2java
 */
@Entity
@Table(name = "region")
public class Region implements java.io.Serializable {

	private int regionId;
	private String name;
	private Set<User> users = new HashSet<User>(0);
	private Set<Team> teams = new HashSet<Team>(0);

	public Region() {
	}

	public Region(int regionId, String name) {
		this.regionId = regionId;
		this.name = name;
	}

	public Region(int regionId, String name, Set<User> users, Set<Team> teams) {
		this.regionId = regionId;
		this.name = name;
		this.users = users;
		this.teams = teams;
	}

	@Id

	@Column(name = "region_id", unique = true, nullable = false)
	public int getRegionId() {
		return this.regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "region")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "region")
	public Set<Team> getTeams() {
		return this.teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

}