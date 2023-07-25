package com.zno.heed.MysqlEntites;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity @Table
public class AgreementFile {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="native")	
	private Long id;
	
	@Version
	private Long version;	
	private String fileName;	
	private String filePath;	

	@OneToOne
	@JoinColumn(name="membershipType")
	private ZnoReference membershipType;
	
	@OneToOne
	@JoinColumn(name="contractType")
	private ZnoReference contractType;

	@JsonFormat(pattern="MM/dd/yyyy")
	private Date dateCreated;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the version
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the membershipType
	 */
	public ZnoReference getMembershipType() {
		return membershipType;
	}

	/**
	 * @param membershipType the membershipType to set
	 */
	public void setMembershipType(ZnoReference membershipType) {
		this.membershipType = membershipType;
	}

	/**
	 * @return the contractType
	 */
	public ZnoReference getContractType() {
		return contractType;
	}

	/**
	 * @param contractType the contractType to set
	 */
	public void setContractType(ZnoReference contractType) {
		this.contractType = contractType;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AgreementFile [id=" + id + ", version=" + version
				+ ", fileName=" + fileName + ", filePath=" + filePath
				+ ", membershipType=" + membershipType + ", contractType="
				+ contractType + ", dateCreated=" + dateCreated + "]";
	}

}
