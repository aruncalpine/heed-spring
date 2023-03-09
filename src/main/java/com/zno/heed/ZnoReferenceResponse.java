package com.zno.heed;

public class ZnoReferenceResponse {

	private String referenceKey;
	private String referenceValue;
	private String description;
	private String type;
	
	public ZnoReferenceResponse(String referenceKey2, String referenceValue2) {
		setReferenceKey(referenceKey2);
		setReferenceValue(referenceValue2);
	}
	public String getReferenceKey() {
		return referenceKey;
	}
	public void setReferenceKey(String referenceKey) {
		this.referenceKey = referenceKey;
	}
	public String getReferenceValue() {
		return referenceValue;
	}
	public void setReferenceValue(String referenceValue) {
		this.referenceValue = referenceValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
