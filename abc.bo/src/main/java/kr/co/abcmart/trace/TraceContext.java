
package kr.co.abcmart.trace;

import java.util.ArrayList;
import java.util.List;

/***
 *
 * @Desc : SQL을 추적 한 값을 공유 하기 위한 Threadlocal
 * @FileName : TraceContext.java
 * @Project : abc.bo
 * @Date : 2019. 3. 13.
 * @Author : zerocooldog@zen9.co.kr
 */
public class TraceContext implements Cloneable {

	private static ThreadLocal<SQLTrace> local = new ThreadLocal<SQLTrace>();
	private static List<SQLTrace> arrSqlTrace = new ArrayList<SQLTrace>();
	static SQLTrace sqlTrace = new SQLTrace();

	public static SQLTrace getContext() {
		return local.get();
	}

	public static void set(SQLTrace sqlTrace) {
		local.set(sqlTrace);
	}

	public static void clear() {
		local.remove();
	}

	public static SQLTrace get() {
		return local.get();
	}

	public static SQLTrace getNewContext(SQLTrace sqlTrace1) {
		return sqlTrace = sqlTrace1;
	}

	public static void setSqlTrace(SQLTrace sqlTrace) {
		arrSqlTrace.add(sqlTrace);
	}

	public static List<SQLTrace> getSqlTrace() {
		return arrSqlTrace;
	}

	public static void sqlTraceClean() {
		arrSqlTrace.clear();
	}
}