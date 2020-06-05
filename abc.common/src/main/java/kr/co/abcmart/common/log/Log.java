package kr.co.abcmart.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class Log {

	public final Logger logger = LoggerFactory.getLogger(getClass());
}
