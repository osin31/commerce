package kr.co.abcmart.common.client;

import kr.co.abcmart.common.request.FileUpload;

public interface SftpProcess {

	void process(SftpClient sftpClient, FileUpload[] fileUploads) throws Exception;
}
