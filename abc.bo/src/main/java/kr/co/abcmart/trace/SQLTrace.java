package kr.co.abcmart.trace;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SQLTrace {
	private String adminNo;
	private String authNo;
	private String menuNo;
	private String menuYn;
	private String accessUrl;
	private String accessParameterText;
	private String accessIpText;
	private String accessType;
	private String sucessYn;
	private String failRsnText;
	private String sucessRsnText;
	private String queryId;
	private String queryText;
	private int rsltCount;
	private String rsltDescText;

}
