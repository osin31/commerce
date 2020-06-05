package kr.co.abcmart.bo.member.vo;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum MemberShipErrorCode {
	defalut("defalut", "입력하신 내용으로 조회된 내역이 없습니다."), noPurchaseHistory("04063801", "입력하신 내용으로 조회된 내역이 없습니다."),
	PurchaseHistoryError("04063802", "입력하신 내용의 금액이 잘못되었습니다."), purchaseConfirmedError("04063803", "이미 구매확정된 거래 입니다."),
	returnReceiptError("04063804", "반품 영수증 정보입니다."), etcError("04063805", "기타 오류가 발생하였습니다."),
	ifError("04063806", "인터페이스 연동에 실패하였습니다.");

	private String code;
	private String desc;

	private MemberShipErrorCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

	public static MemberShipErrorCode getByCode(String code) {
		for (MemberShipErrorCode type : values()) {
			if (type.code.equals(code)) {
				return type;
			}
		}
		return MemberShipErrorCode.defalut;
		// throw new IllegalArgumentException( "MemberShipErrorCode No Data Found.." );
	}
}
