package com.zno.heed.files;

import lombok.Data;

@Data
public class FileUploadResponse {
	private String fileName;
    private String downloadUri;
    private long size;
}
