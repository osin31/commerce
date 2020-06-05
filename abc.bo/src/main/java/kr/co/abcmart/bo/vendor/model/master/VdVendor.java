package kr.co.abcmart.bo.vendor.model.master;

import java.util.List;

import kr.co.abcmart.bo.vendor.model.master.base.BaseVdVendor;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.message.Message;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class VdVendor extends BaseVdVendor implements Validator {

	@ParameterOption(target = "chnnlNo")
	private VdVendorDisplayChnnl[] chnnlNoList;

	@ParameterOption(target = "chargeMdNo")
	private VdVendorChargeMd[] chargeMdList;

	@ParameterOption(target = "vndrMngrId")
	private VdVendorManager[] managerList;

	@ParameterOption(target = "vendorBrandNo")
	private VdVendorBrand[] vendorbrandList;

	@ParameterOption(target = "vendorStdCtgrNo")
	private VdVendorStandardCategory[] vendorStdCtgList;

	@ParameterOption(target = "dlvyGuideBgnCode")
	private VdVendorDeliveryGuide[] deliveryGuideList;

	@ParameterOption(target = "cpnTypeCode")
	private VdVendorApplyCoupon[] applyCouponList;

	@ParameterOption(target = "commissionRowIndex")
	private VdVendorExceptionCommission[] exceptionCommissionList;

	@ParameterOption(target = "discountBrandIndex")
	private VdVendorBrandEmployeeDiscount[] employeeDiscountList;

	@ParameterOption(target = "wrhsDlvyGbnType")
	private VdVendorWrhsDlvyAddress[] dlvyAddressList;

	@ParameterOption(target = "vndrDfltCmsnSeq")
	private VdVendorDefaultCommission[] defaultCommissionList;

	private String chnnlNames;
	private String vndrMngrName;
	private String exceptionCommissionApplyYn;
	private String employeeDiscountApplyYn;
	private String vendorCouponApplyYn;
	private String chargeMdNo;
	private String toDate;
	private String fromDate;
	private String searchKey;
	private String searchValue;
	private String searchDateKey;
	private String vndrStatCodeName;
	private String dfltCmsnRateText;

	private String bizNo1;
	private String bizNo2;
	private String bizNo3;
	private String crprtNo1;
	private String crprtNo2;
	private String mailBizNo1;
	private String mailBizNo2;
	private String mailBizNo3;
	private String vndrStdCtgrNo;
	private String vndrBrandNo;

	private String moderId;
	private String moderName;
	private String searchModerName;

	private String status;

	private String tmsGubun;

	private List<String> vndrNoList;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * kr.co.abcmart.bo.vendor.model.master.base.BaseVdVendor#setBizNoText(java.lang
	 * .String)
	 */
	@Override
	public void setBizNoText(String bizNoText) {

		super.setBizNoText(bizNoText);

		if (bizNoText != null) {
			String[] bizNoTextArry = UtilsText.split(bizNoText, "-");
			for (int i = 0; i < bizNoTextArry.length; i++) {
				if (i == 0) {
					setBizNo1(bizNoTextArry[i]);
				}
				if (i == 1) {
					setBizNo2(bizNoTextArry[i]);
				}
				if (i == 2) {
					setBizNo3(bizNoTextArry[i]);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * kr.co.abcmart.bo.vendor.model.master.base.BaseVdVendor#setCrprtNoText(java.
	 * lang .String)
	 */
	@Override
	public void setCrprtNoText(String crprtNoText) {

		super.setCrprtNoText(crprtNoText);

		if (crprtNoText != null) {
			String[] crprtNoTextArry = UtilsText.split(crprtNoText, "-");
			for (int i = 0; i < crprtNoTextArry.length; i++) {
				if (i == 0) {
					setCrprtNo1(crprtNoTextArry[i]);
				}
				if (i == 1) {
					setCrprtNo2(crprtNoTextArry[i]);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * kr.co.abcmart.bo.vendor.model.master.base.BaseVdVendor#setMailBizNoText(java.
	 * lang .String)
	 */
	@Override
	public void setMailBizNoText(String mailBizNoText) {

		super.setMailBizNoText(mailBizNoText);

		if (mailBizNoText != null) {
			String[] mailBizNoTextArry = UtilsText.split(mailBizNoText, "-");
			for (int i = 0; i < mailBizNoTextArry.length; i++) {
				if (i == 0) {
					setMailBizNo1(mailBizNoTextArry[i]);
				}
				if (i == 1) {
					setMailBizNo2(mailBizNoTextArry[i]);
				}
				if (i == 2) {
					setMailBizNo3(mailBizNoTextArry[i]);
				}
			}
		}
	}

	public String getModerName() {
		return UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
				UtilsMasking.userName(this.moderName), Const.R_PAREN);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see kr.co.abcmart.common.validation.Validator#validate()
	 */
	@Override
	public void validate() throws ValidatorException {
		if (UtilsText.isBlank(getVndrNo())) {
			validationMessage("vendor.valid.variableisnull", "",
					new String[] { Message.getMessage("vendor.msg.vendor") });
		}
	}

}
