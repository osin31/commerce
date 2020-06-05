package kr.co.abcmart.zconfiguration.cache.property;

import lombok.Data;

@Data
public class RedisProperty {

	private String host;

	private int port;

	private String password;

	private int database;
}