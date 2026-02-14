package it.uniroma3.siw.the_something_awful.model;



import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Creature {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String codeName;
	private String name;
	private String description;
	@Enumerated(EnumType.STRING)
	private ThreatLevel threatLevel;
	@Enumerated(EnumType.STRING)
	private List<CreatureStatus> status;
	private String discoveredAt;
	@ManyToOne
	@JoinColumn(name = "agent_id")
	private User certifiedBy;
	private String imageFileName;
	
	public Creature() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ThreatLevel getThreatLevel() {
		return threatLevel;
	}

	public void setThreatLevel(ThreatLevel threatLevel) {
		this.threatLevel = threatLevel;
	}

	public String getDiscoveredAt() {
		return discoveredAt;
	}

	public void setDiscoveredAt(String discoveredAt) {
		this.discoveredAt = discoveredAt;
	}

	public User getCertifiedBy() {
		return certifiedBy;
	}

	public void setCertifiedBy(User certifiedBy) {
		this.certifiedBy = certifiedBy;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
}
