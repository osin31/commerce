package kr.co.abcmart.common.request;

import org.springframework.http.MediaType;

public class FileDownload {
	
	private MediaType contentType = MediaType.APPLICATION_OCTET_STREAM;
	
	private String filePath;	

	private String fileName;	
			
	private String OrgFileName;

		
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public MediaType getContentType() {
		return contentType;
	}

	public void setContentType(MediaType contentType) {
		this.contentType = contentType;
	}

	public String getOrgFileName() {
		return OrgFileName;
	}

	public void setOrgFileName(String orgFileName) {
		OrgFileName = orgFileName;
	}

}
