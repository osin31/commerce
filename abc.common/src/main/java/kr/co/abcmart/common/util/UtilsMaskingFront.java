package kr.co.abcmart.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/*
   2018 홈페이지 개인정보 노출방지 안내서(행정안전부,한국인터넷징흥원) 주요 개인정보 8종 정규표현식
   ● 주민등록번호
      (?<=[0-9a-zA-Z])([0-9][0-9][01][0-9][0-3][0-9][\s-:\.]?)([1-4]\d{6})(?=[0-9a-zA-Z])
   ● 여권번호
      (?<=[^0-9a-zA-Z])([M|S|R|O|D|m|s|r|o|d][0-9]{8})(?=[^0-9a-zA-Z])
      (?<=[^0-9a-zA-Z])([a-zA-Z]{2}[0-9]{7})(?=[^0-9a-zA-Z])
   ● 운전면허번호
      (?<=[^0-9a-zA-Z])(\d{2}[\s-:\.])(\d{6}[\s-:\.])(\d{2})(?=[^0-9a-zA-Z])
   ● 핸드폰번호
      (?<=[^0-9a-zA-Z])(01[0|1|6|7|8|9][\s-:\.]?)(\d{3,4}[\s-:\.]?)(\d{4})(?=[^0-9a-zA-Z])
   ● 신용카드번호
      (?<=[^0-9a-zA-Z])(\d{4}[\s-:\.])(\d{4}[\s-:\.])(\d{4}[\s-:\.])(\d{4})(?=[^0-9a-zA-Z])
   ● 건강보험번호
      (?<=[^0-9a-zA-Z])([1-9]\d{10})(?=[^0-9a-zA-Z])
   ● 계좌번호
      (?<=[^0-9a-zA-Z])(\d{3}[\s-:\.])(\d{3}[\s-:\.])(\d{6})(?=[^0-9a-zA-Z])
      (?<=[^0-9a-zA-Z])(\d{4}[\s-:\.])(\d{3}[\s-:\.])(\d{6})(?=[^0-9a-zA-Z])
      (?<=[^0-9a-zA-Z])(\d{6}[\s-:\.])(\d{2}[\s-:\.])(\d{6})(?=[^0-9a-zA-Z])
      (?<=[^0-9a-zA-Z])(\d{6}[\s-:\.])(\d{2}[\s-:\.])(\d{6})(?=[^0-9a-zA-Z])
      (?<=[^0-9a-zA-Z])(\d{3}[\s-:\.])(\d{2}[\s-:\.])(\d{5}[\s-:\.])(\d{1})(?=[^0-9a-zA-Z])
      (?<=[^0-9a-zA-Z])(\d{3}[\s-:\.])(\d{6}[\s-:\.])(\d{5})(?=[^0-9a-zA-Z])
      (?<=[^0-9a-zA-Z])(\d{3}[\s-:\.])(\d{6}[\s-:\.])(\d{2}[\s-:\.])(\d{3})(?=[^0-9a-zA-Z])
   ● 외국인 등록번호
      (?<=[^0-9a-zA-Z])([0-9][0-9][01][0-9][0-3][0-9][\s-:\.]?)([5-8]\d{6})(?=[^0-9a-zA-Z])    
*/

@Slf4j
public class UtilsMaskingFront {

	public static final boolean DEFAULT_VERIFICATION = true;
	public static final String REGEX_REPLACE_CHARACTER = "[\\s-:\\.^,]";

	public static final String REGEX_RESIDENT_REGISTRATION_NUMBER = "([0-9][0-9][01][0-9][0-3][0-9][\\s-:\\.]?)([1-4]\\d{6})";
	public static final String REGEX_CELL_PHONE_NUMBER = "(01[0|1|6|7|8|9][\\s-:\\.]?)(\\d{3,4}[\\s-:\\.]?)(\\d{4})";
	public static final String REGEX_CARD_NUMBER = "(\\d{4}[\\s-:\\.]?)(\\d{4}[\\s-:\\.]?)(\\d{4}[\\s-:\\.]?)(\\d{3,4})";
	public static final List<String> REGEX_BANK_ACCOUNT_NUMBER = Arrays.asList(
			"(\\d{3,4}[\\s-:\\.])(\\d{3}[\\s-:\\.])(\\d{6})", "(\\d{6}[\\s-:\\.])(\\d{2}[\\s-:\\.])(\\d{6})",
			"(\\d{3}[\\s-:\\.])(\\d{6}[\\s-:\\.])(\\d{5})",
			"(\\d{3}[\\s-:\\.])(\\d{2}[\\s-:\\.])(\\d{5}[\\s-:\\.])(\\d{1})",
			"(\\d{3}[\\s-:\\.])(\\d{6}[\\s-:\\.])(\\d{2}[\\s-:\\.])(\\d{3})");

	private static boolean isValid(String regex, String value) {
		return Pattern.matches(regex, value);
	}

	/***
	 * 사용자 이름 마스킹.
	 * 
	 * @param value 사용자 이름
	 * @return String
	 */
	public static String userName(String value) {

		if (value == null) {
			return null;
		}

		String pattern = (value.length() <= 2) ? "(?<=.{1})." : "(?<=.{1}).(?=.{1})";

		return value.replaceAll(pattern, "*");
	}

	/***
	 * 로그인 아이디 마스킹.
	 * 
	 * @param value 로그인 아이디
	 * @return String
	 */
	public static String loginId(String value) {

		if (value == null) {
			return null;
		}

		return value.length() <= 3 ? value : value.replaceAll(".(?!.{3})", "*");
	}

	/***
	 * 사용자 이메일 마스킹.
	 * 
	 * @param value 사용자 이메일
	 * @return String
	 */
	public static String emailAddress(String value) {

		if (value == null) {
			return null;
		}

		int idLength = value.indexOf("@");

		if (idLength > 3) {
			String pattern = UtilsText.concat("(?<=.{", Integer.toString((int) (idLength - 3)), "}).(?=.*@)");
			return value.replaceAll(pattern, "*");
		} else {
			return value;
		}

	}

	/***
	 * 주민등록번호 마스킹.
	 * 
	 * @param value 주민등록번호
	 * @return String
	 */
	public static String residentRegistrationNumber(String value) {
		return residentRegistrationNumber(value, DEFAULT_VERIFICATION);
	}

	/***
	 * 주민등록번호 마스킹. verification 이 true 일 경우 주민등록번호 형식에 맞는지 검증 후 마스킹 처리 한다. 형식이 맞지
	 * 않으면 null 값 리턴.
	 * 
	 * @param value        주민등록번호
	 * @param verification 주민등록번호 형식에 맞는지 검증.
	 * @return String
	 */
	public static String residentRegistrationNumber(String value, boolean verification) {

		if (value == null) {
			return null;
		}

		if (verification) {
			boolean isVerification = isValid(REGEX_RESIDENT_REGISTRATION_NUMBER, value);

			if (!isVerification) {
				log.warn("The Resident Registration Number format does not match.");
				return null;
			}
		}

		return UtilsText.formatter(value, "######-#******");
	}

	/***
	 * 휴대전화번호 마스킹.
	 * 
	 * @param value 휴대전화번호
	 * @return String
	 */
	public static String cellPhoneNumber(String value) {
		return cellPhoneNumber(value, DEFAULT_VERIFICATION);
	}

	/***
	 * 휴대전화번호 마스킹. verification 이 true 일 경우 휴대전화번호 형식에 맞는지 검증 후 마스킹 처리 한다. 형식이 맞지
	 * 않으면 null 값 리턴.
	 * 
	 * @param value        휴대전화번호
	 * @param verification 휴대전화번호 형식에 맞는지 검증.
	 * @return String
	 */
	public static String cellPhoneNumber(String value, boolean verification) {

		if (value == null) {
			return null;
		}

		value = value.replaceAll(REGEX_REPLACE_CHARACTER, "");

		if (verification) {
			boolean isVerification = isValid(REGEX_CELL_PHONE_NUMBER, value);

			if (!isVerification) {
				log.warn("The CellPhone Number format does not match.");
				return null;
			}
		}

		String pattern = "###-***-####";
		if (value.length() > 10) {
			pattern = "###-****-####";
		}

		return UtilsText.formatter(value, pattern);
	}

	/***
	 * 신용카드번호 마스킹.
	 * 
	 * @param value 신용카드번호
	 * @return String
	 */
	public static String cardNumber(String value) {
		return cellPhoneNumber(value, DEFAULT_VERIFICATION);
	}

	/***
	 * 신용카드번호 마스킹. verification 이 true 일 경우 신용카드번호 형식에 맞는지 검증 후 마스킹 처리 한다. 형식이 맞지
	 * 않으면 null 값 리턴.
	 * 
	 * @param value        신용카드번호
	 * @param verification 신용카드번호 형식에 맞는지 검증.
	 * @return String
	 */
	public static String cardNumber(String value, boolean verification) {

		if (value == null) {
			return null;
		}

		value = value.replaceAll(REGEX_REPLACE_CHARACTER, "");

		if (verification) {
			boolean isVerification = isValid(REGEX_CARD_NUMBER, value);

			if (!isVerification) {
				log.warn("The Card Number format does not match.");
				return null;
			}
		}

		String pattern = "****-****-****-####";
		// 15자리 카드 번호... amex?
		if (value.length() < 16) {
			pattern = "****-****-****-###";
		}

		return UtilsText.formatter(value, pattern);
	}

	/***
	 * 계좌번호 마스킹.
	 * 
	 * @param value 신용카드번호
	 * @return String
	 */
	public static String bankAccoutNumber(String value) {
		return bankAccoutNumber(value, false);
	}

	/***
	 * 계좌번호 마스킹. verification 이 true 일 경우 계좌번호 형식에 맞는지 검증 후 마스킹 처리 한다. 형식이 맞지 않으면
	 * null 값 리턴.
	 * 
	 * @param value        계좌번호
	 * @param verification 계좌번호 형식에 맞는지 검증.
	 * @return String
	 */
	public static String bankAccoutNumber(String value, boolean verification) {

		if (value == null) {
			return null;
		}

		boolean isVerification = false;
		if (verification) {

			for (String bankAccountPattern : REGEX_BANK_ACCOUNT_NUMBER) {

				if (isValid(bankAccountPattern, value)) {
					isVerification = true;
					break;
				}
			}

			if (!isVerification) {
				log.warn("The Bank Account Number format does not match.");
				return null;
			}

		}

		value = value.replaceAll(REGEX_REPLACE_CHARACTER, "");

		return value.replaceAll("(?<=.{3}).", "*");
	}

	/***
	 * ip 주소 마스킹. 3번째 그룹을 마스킹 처리한다. ex) 123.123.***.123
	 * 
	 * @param value ip 주소
	 * @return String
	 */
	public static String ipAddress(String value) {

		if (value == null) {
			return null;
		}

		return value.replaceAll("(\\d{1,3}.\\d{1,3}).(\\d{1,3}).(\\d{1,3})", "$1.***.$3");
	}

	/***
	 * 주소 마스킹. (마스킹 패턴은 사용자 이름과 동일)
	 * 
	 * @param value 주소(or 상세주소)
	 * @return String
	 */
	public static String postAddress(String value) {

		if (value == null) {
			return null;
		}

		String pattern = (value.length() <= 2) ? "(?<=.{1})." : "(?<=.{1}).(?=.{1})";

		return value.replaceAll(pattern, "*");
	}

	/***
	 * 기프트카드 마스킹. (마스킹 패턴은 사용자 이름과 동일)
	 * 
	 * @param value 기프트 카드 번호
	 * @return String
	 */
	public static String giftCardNumber(String value) {

		if (value == null) {
			return null;
		}

		String pattern = (value.length() <= 2) ? "(?<=.{1})." : "(?<=.{4}).(?=.{4})";

		return value.replaceAll(pattern, "*");
	}
}