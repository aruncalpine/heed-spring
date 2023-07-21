package com.zno.heed.files;

import lombok.Data;

@Data
public class ImageUploadResponse {
	private String fileName;
    private String downloadUri;
    private long size;
}
