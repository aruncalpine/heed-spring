package com.zno.heed.files;

import lombok.Data;

@Data
public class ProfileImageUploadResponse {
	private String fileName;
    private String downloadUri;
    private long size;
}
