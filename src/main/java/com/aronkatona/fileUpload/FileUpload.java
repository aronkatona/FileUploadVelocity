package com.aronkatona.fileUpload;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {

	private List<MultipartFile> uploadFiles;
	 
    public List<MultipartFile> getFiles() {
        return uploadFiles;
    }
 
    public void setFiles(List<MultipartFile> files) {
        this.uploadFiles = files;
    }
}