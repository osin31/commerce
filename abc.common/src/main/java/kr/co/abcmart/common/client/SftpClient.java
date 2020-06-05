package kr.co.abcmart.common.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SftpClient {

	private static final String SEPARATOR = "/";

	private String id;
	private String pw;
	private String host;
	private int port;

	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;

	public SftpClient(String id, String pw, String host, int port) {
		this.id = id;
		this.pw = pw;
		this.host = host;
		this.port = port;

		init();
	}

	public SftpClient(String id, String pw, String host) {
		this(id, pw, host, 22);
	}

	// SFTP 서버연결
	public void init() {

		// JSch 객체 생성
		JSch jsch = new JSch();
		try {
			// 세션객체 생성 ( user , host, port )
			session = jsch.getSession(id, host, port);

			// password 설정
			session.setPassword(pw);

			// 세션관련 설정정보 설정
			java.util.Properties config = new java.util.Properties();

			// 호스트 정보 검사하지 않는다.
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			// 접속
			session.connect();

			// sftp 채널 접속
			channel = session.openChannel("sftp");
			channel.connect();

		} catch (JSchException e) {
			e.printStackTrace();
		}
		channelSftp = (ChannelSftp) channel;

	}

	// 단일 파일 업로드
	public void upload(String path, File file) throws Exception {
		FileInputStream in = null;

		try { // 파일을 가져와서 inputStream에 넣고 저장경로를 찾아 put
			in = new FileInputStream(file);
			path = mkdir(path);
			channelSftp.cd(path);
			channelSftp.put(in, file.getName());
		} catch (SftpException se) {
			se.printStackTrace();
			throw se;
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
			throw fe;
		} finally {
			try {
				in.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
				throw ioe;
			}
		}
	}

	// 단일 파일 업로드
	public void upload(String path, String fileName, InputStream inputStream) throws Exception {

		try { // 파일을 가져와서 inputStream에 넣고 저장경로를 찾아 put
			path = mkdir(path);
			channelSftp.cd(path);
			channelSftp.put(inputStream, fileName);
		} catch (SftpException se) {
			se.printStackTrace();
			throw se;
		} finally {
			try {
				inputStream.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
				throw ioe;
			}
		}
	}

	// 단일 파일 업로드
	public void uploadSabangNet(String path, File file) throws Exception {
		FileInputStream in = null;

		try { // 파일을 가져와서 inputStream에 넣고 저장경로를 찾아 put
			in = new FileInputStream(file);
			channelSftp.cd(path);
			channelSftp.put(in, file.getName());
		} catch (SftpException se) {
			se.printStackTrace();
			throw se;
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
			throw fe;
		} finally {
			try {
				in.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
				throw ioe;
			}
		}
	}

	// 단일 파일 다운로드
	public InputStream download(String path, String fileName) {
		InputStream in = null;
		try { // 경로탐색후 inputStream에 데이터를 넣음
			channelSftp.cd(path);
			in = channelSftp.get(fileName);

		} catch (SftpException se) {
			se.printStackTrace();
		}

		return in;
	}

	// 파일서버와 세션 종료
	public void disconnect() {
		channelSftp.quit();
		session.disconnect();
	}

	private String mkdir(String path) throws SftpException {
		String[] pathArray = path.split(SEPARATOR);
		String currentDirectory = channelSftp.pwd();
		String totPathArray = "";
		for (int i = 0; i < pathArray.length; i++) {
			totPathArray += pathArray[i] + SEPARATOR;
			String currentPath = currentDirectory + SEPARATOR + totPathArray;

			try {
				channelSftp.mkdir(currentPath);
				channelSftp.cd(currentPath);
			} catch (Exception e) {
				channelSftp.cd(currentPath);
			}
		}

		return currentDirectory + "/" + totPathArray;

	}
}
