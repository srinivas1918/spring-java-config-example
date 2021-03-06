package com.hms.pro.domain;

// Generated Jun 19, 2016 12:53:53 PM by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Building generated by hbm2java
 */
@Entity
@Table(name = "building", catalog = "hms")
public class Building implements java.io.Serializable {

	private int buildingId;
	private Hostal hostal;
	private String buildingName;
	private String incharge;
	private String buildingContactNo;
	private String buildingAddress;
	private Set<Floor> floors = new HashSet<Floor>(0);
	private Set<Room> rooms=new HashSet<Room>(0);
	private Integer isActive;

	public Building() {
	}

	public Building(int buildingId) {
		this.buildingId = buildingId;
	}

	public Building(int buildingId, Hostal hostal, String buildingName,
			String incharge, String buildingContactNo, String buildingAddress,
			Set<Floor> floors) {
		this.buildingId = buildingId;
		this.hostal = hostal;
		this.buildingName = buildingName;
		this.incharge = incharge;
		this.buildingContactNo = buildingContactNo;
		this.buildingAddress = buildingAddress;
		this.floors = floors;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "building_id", unique = true, nullable = false)
	public int getBuildingId() {
		return this.buildingId;
	}

	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hostal")
	public Hostal getHostal() {
		return this.hostal;
	}

	public void setHostal(Hostal hostal) {
		this.hostal = hostal;
	}

	@Column(name = "building_name", length = 45)
	public String getBuildingName() {
		return this.buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	@Column(name = "incharge", length = 45)
	public String getIncharge() {
		return this.incharge;
	}

	public void setIncharge(String incharge) {
		this.incharge = incharge;
	}

	@Column(name = "building_contact_no", length = 45)
	public String getBuildingContactNo() {
		return this.buildingContactNo;
	}

	public void setBuildingContactNo(String buildingContactNo) {
		this.buildingContactNo = buildingContactNo;
	}

	@Column(name = "building_address", length = 150)
	public String getBuildingAddress() {
		return this.buildingAddress;
	}

	public void setBuildingAddress(String buildingAddress) {
		this.buildingAddress = buildingAddress;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "building")
	public Set<Floor> getFloors() {
		return this.floors;
	}

	public void setFloors(Set<Floor> floors) {
		this.floors = floors;
	}

	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "building")
	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	@Column(name = "isActive")
	public Integer getIsActive() {
		return isActive;
	}
	
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	
	

}
