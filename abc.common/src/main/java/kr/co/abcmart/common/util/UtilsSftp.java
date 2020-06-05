package kr.co.abcmart.common.util;

import java.io.InputStream;

import kr.co.abcmart.common.client.SftpClient;
import kr.co.abcmart.common.client.SftpProcess;
import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.request.FileUpload;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtilsSftp {

	public static void upload(String id, String pw, String host, int port, String path, String fileName,
			InputStream inputStream) throws Exception {

		SftpClient sftpClient = new SftpClient(id, pw, host, port);

		if (inputStream != null) {
			sftpClient.upload(path, fileName, inputStream);
		}

		sftpClient.disconnect();
	}

	public static void upload(String id, String pw, String host, String path, String fileName, InputStream inputStream)
			throws Exception {
		upload(id, pw, host, 22, path, fileName, inputStream);
	}

	public static void upload(String path, String fileName, InputStream inputStream) throws Exception {
		upload(Config.getString("file.sftp.id"), Config.getString("file.sftp.pw"), Config.getString("file.sftp.host"),
				Config.getInt("file.sftp.port"), path, fileName, inputStream);
	}

	public static void uploadArray(String id, String pw, String host, int port, String path, String fileName,
			FileUpload[] fileUploads, SftpProcess sftpProcess) throws Exception {

		SftpClient sftpClient = new SftpClient(id, pw, host, port);

		if (fileUploads != null) {
			sftpProcess.process(sftpClient, fileUploads);
		}

		sftpClient.disconnect();
	}

	public static void uploadArray(String id, String pw, String host, int port, String path, String fileName,
			FileUpload[] fileUploads) throws Exception {
		uploadArray(id, pw, host, port, path, fileName, fileUploads);
	}

	public static void uploadArray(String id, String pw, String host, String path, String fileName,
			FileUpload[] fileUploads) throws Exception {
		uploadArray(id, pw, host, 22, path, fileName, fileUploads);
	}

	private static void uploadArray(String id, String pw, String host, int port, FileUpload[] fileUploads,
			SftpProcess sftpProcess) throws Exception {
		uploadArray(id, pw, host, port, null, null, fileUploads, sftpProcess);
	}

	public static void uploadArray(String path, String fileName, FileUpload[] fileUploads, SftpProcess sftpProcess)
			throws Exception {
		uploadArray(Config.getString("file.sftp.id"), Config.getString("file.sftp.pw"),
				Config.getString("file.sftp.host"), Config.getInt("file.sftp.port"), path, fileName, fileUploads,
				sftpProcess);
	}

	public static void uploadArray(FileUpload[] fileUploads, SftpProcess sftpProcess) throws Exception {
		uploadArray(null, null, fileUploads, sftpProcess);
	}

	public static void main(String[] args) throws Exception {

		FileUpload[] fileUpload = new FileUpload[] { new FileUpload(null), new FileUpload(null) };
		UtilsSftp.uploadArray("ftpuser", "ftpuser1", "210.122.45.36", 22, fileUpload, (client, fileUploads) -> {
			// 파일 경로 및 파일명 정의 후 upload함수를 호출 하여 업로드 처리 한다.
			// client.upload(dir, file);

			log.debug("업로드 실행  : {}", fileUploads);
		});
	}

}
