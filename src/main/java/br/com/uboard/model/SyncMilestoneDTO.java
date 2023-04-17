package br.com.uboard.model;

import java.io.Serializable;
import java.util.List;

public class SyncMilestoneDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long userUUID;
	private Long contextID;
	private List<MilestoneDTO> milestones;

	public Long getUserUUID() {
		return userUUID;
	}

	public void setUserUUID(Long userUUID) {
		this.userUUID = userUUID;
	}

	public Long getContextID() {
		return contextID;
	}

	public void setContextID(Long contextID) {
		this.contextID = contextID;
	}

	public List<MilestoneDTO> getMilestones() {
		return milestones;
	}

	public void setMilestones(List<MilestoneDTO> milestones) {
		this.milestones = milestones;
	}

}
