package kr.co.abcmart.bo.product.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.cmm.model.master.CmMessageTemplate;
import kr.co.abcmart.bo.cmm.service.MessageService;
import kr.co.abcmart.bo.cmm.service.MsgTemplateService;
import kr.co.abcmart.bo.cmm.vo.MessageVO;
import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.service.MemberPointService;
import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.bo.product.model.master.BdProductReview;
import kr.co.abcmart.bo.product.model.master.BdProductReviewEvlt;
import kr.co.abcmart.bo.product.model.master.BdProductReviewImage;
import kr.co.abcmart.bo.product.repository.master.BdProductReviewDao;
import kr.co.abcmart.bo.product.repository.master.BdProductReviewEvltDao;
import kr.co.abcmart.bo.product.repository.master.BdProductReviewImageDao;
import kr.co.abcmart.bo.product.vo.BdProductReviewSearchVO;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품후기 서비스
 * @FileName : ProductReviewService.java
 * @Project : abc.bo
 * @Date : 2019. 3. 29.
 * @Author : hsjhsj19
 */
@Slf4j
@Service
public class ProductReviewService {

	@Autowired
	private BdProductReviewDao productReviewDao;

	@Autowired
	private BdProductReviewImageDao productReviewImageDao;

	@Autowired
	private BdProductReviewEvltDao productReviewEvltDao;

	@Autowired
	private MemberPointService memberPointService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MsgTemplateService msgTemplateService;

	@Autowired
	private ProductReviewService reviewService;

	/**
	 * @Desc : 상품 후기 목록 조회
	 * @Method Name : selectProductReview
	 * @Date : 2019. 4. 4.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<BdProductReview> selectProductReview(Pageable<BdProductReviewSearchVO, BdProductReview> pageable)
			throws Exception {
		Integer count = this.productReviewDao.selectProductReviewCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(this.productReviewDao.selectProductReview(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 상품 후기 단건 조회
	 * @Method Name : searchProductReviewByPrimaryKey
	 * @Date : 2019. 4. 4.
	 * @Author : hsjhsj19
	 * @param prdtRvwSeq
	 * @return
	 * @throws Exception
	 */
	public BdProductReview searchProductReviewByPrimaryKey(int prdtRvwSeq) throws Exception {
		BdProductReview bdProductReview = new BdProductReview();
		bdProductReview.setPrdtRvwSeq(prdtRvwSeq);
		// BD_PRODUCT_REVIEW 조회
		BdProductReview review = this.productReviewDao.selectByPK(bdProductReview);

		return review;
	}

	/**
	 * @Desc : 후기 수정
	 * @Method Name : updateProductReview
	 * @Date : 2019. 4. 4.
	 * @Author : hsjhsj19
	 * @param bdPopup
	 * @throws Exception
	 */
	public int updateProductReview(BdProductReview[] bdProductReviews) throws Exception {
		return updateWithConditions("updateProductReview", bdProductReviews);
	}

	/**
	 * @Desc : 상품 후기 이미지 목록 조회
	 * @Method Name : searchProductReviewImage
	 * @Date : 2019. 4. 4.
	 * @Author : hsjhsj19
	 * @param review
	 * @return
	 * @throws Exception
	 */
	public List<BdProductReviewImage> searchProductReviewImage(BdProductReview review) throws Exception {
		BdProductReviewImage bdProductReviewImage = new BdProductReviewImage();
		bdProductReviewImage.setPrdtRvwSeq(review.getPrdtRvwSeq());
		return productReviewImageDao.select(bdProductReviewImage);
	}

	/**
	 * @Desc : 상품후기평가 목록 조회 (단건인지..?)
	 * @Method Name : searchProductReviewEvlt
	 * @Date : 2019. 4. 4.
	 * @Author : hsjhsj19
	 * @param review
	 * @return
	 * @throws Exception
	 */
	public List<BdProductReviewEvlt> searchProductReviewEvlt(BdProductReview review) throws Exception {
		BdProductReviewEvlt bdProductReviewEvlt = new BdProductReviewEvlt();
		bdProductReviewEvlt.setPrdtRvwSeq(review.getPrdtRvwSeq());
		return productReviewEvltDao.select(bdProductReviewEvlt);
	}

	/**
	 * @Desc : [서비스 요청] 상품 후기 답변/미답변 갯수
	 * @Method Name : getAswrYNCount
	 * @Date : 2019. 4. 5.
	 * @Author : hsjhsj19
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public String getAswrCount(String memberNo) throws Exception {
		BdProductReviewSearchVO bdProductReviewSearchVO = new BdProductReviewSearchVO();
		bdProductReviewSearchVO.setAswrStatCode(CommonCode.ASWR_STAT_CODE_UN);
		bdProductReviewSearchVO.setMemberNo(memberNo);

		return productReviewDao.getAswrCount(bdProductReviewSearchVO);
	}

	/**
	 * @Desc : 상품후기 삭제
	 * @Method Name : deleteProductReview
	 * @Date : 2019. 4. 8.
	 * @Author : hsjhsj19
	 * @param review
	 * @throws Exception
	 */
	@Deprecated
	public void deleteProductReview(BdProductReview review) throws Exception {
		BdProductReviewEvlt rvEvlt = new BdProductReviewEvlt();
		BdProductReviewImage rvImage = new BdProductReviewImage();
		rvEvlt.setPrdtRvwSeq(review.getPrdtRvwSeq());
		rvImage.setPrdtRvwSeq(review.getPrdtRvwSeq());

		productReviewImageDao.deleteImg(rvImage);
		productReviewEvltDao.deleteEvlt(rvEvlt);
		productReviewDao.delete(review);
	}

	/**
	 * @Desc :일괄 변경 저장
	 * @Method Name : batchSave
	 * @Date : 2019. 4. 9.
	 * @Author : hsjhsj19
	 * @param reviews
	 * @return
	 * @throws Exception
	 */
	public int batchSave(BdProductReview[] bdProductReviews) throws Exception {
		return updateWithConditions("batchSave", bdProductReviews);
	}

	/**
	 * @Desc : 상품후기 답변 등록/수정
	 * @Method Name : updateWithConditions
	 * @Date : 2019. 4. 17.
	 * @Author : hsjhsj19
	 * @param methodName
	 * @param bdProductReviews
	 * @return
	 * @throws Exception
	 */
	private int updateWithConditions(String methodName, BdProductReview[] bdProductReviews) throws Exception {
		Date now = new Date();
		// 세션 정보 호출
		UserDetails user = LoginManager.getUserDetails();
		String adminNo = user.getAdminNo();
		int cnt = 0;
		for (BdProductReview bdProductReview : bdProductReviews) {

			// 비교 조회용
			BdProductReview selectReview = searchProductReviewByPrimaryKey(bdProductReview.getPrdtRvwSeq());

			if ("updateProductReview".equals(methodName)) {
				// 단건저장 최초는 항상 답변완료와 확인으로 등록
				if (Const.BOOLEAN_FALSE.equals(selectReview.getCnfrmYn())) {
					// 미확인여부는 확인
					bdProductReview.setCnfrmYn("Y");
					bdProductReview.setCnfrmrNo(adminNo);
					bdProductReview.setCnfrmDtm(new Timestamp(now.getTime()));
				}

				if (CommonCode.ASWR_STAT_CODE_UN.equals(selectReview.getAswrStatCode())) {
					// 미답변상태만 답변완료 처리(답변내용이 없어도 답변처리 가능하게 요청 반영)
					bdProductReview.setAswrNo(adminNo);
					bdProductReview.setAswrDtm(new Timestamp(now.getTime()));
					bdProductReview.setAswrStatCode(CommonCode.ASWR_STAT_CODE_CM);
				}
			} else {
				// 일괄저장은 입력 받은 값으로만 변경되도록 등록
				if (Const.BOOLEAN_TRUE.equals(bdProductReview.getCnfrmYn())
						&& Const.BOOLEAN_FALSE.equals(selectReview.getCnfrmYn())) {
					// 미확인여부는 확인
					bdProductReview.setCnfrmYn("Y");
					bdProductReview.setCnfrmrNo(adminNo);
					bdProductReview.setCnfrmDtm(new Timestamp(now.getTime()));
				}
				if (CommonCode.ASWR_STAT_CODE_CM.equals(bdProductReview.getAswrStatCode())
						&& CommonCode.ASWR_STAT_CODE_UN.equals(selectReview.getAswrStatCode())) {
					// 미답변상태만 답변완료 처리(답변내용이 없어도 답변처리 가능하게 요청 반영)
					bdProductReview.setAswrNo(adminNo);
					bdProductReview.setAswrDtm(new Timestamp(now.getTime()));
				}
			}

			// 지급여부 판단
			switch (bdProductReview.getPointPayType()) {
			case "N": // 미지급
				bdProductReview.setPointPayYn("N");
				break;
			case "G": // 일반 포인트 지급
			case "P": // 포토 포인트 지급
				// 온라인구매인 경우 주문확정일이 30일 이전일때 포인트지급가능
				Integer buyDcsnDtm = selectReview.getBuyDcsnDtm();
				// 현재 미지급 Y이고 일반 또는 포토 포인트 지급으로 변경
//				if (buyDcsnDtm < 1) {
//					if ("N".equals(selectReview.getPointPayYn())) {
//						throw new Exception("구매확정인 상품에만 포인트지급이 가능합니다.");
//					}
//				} else
				if (30 < buyDcsnDtm) {
					throw new Exception("포인트 지급기한이 지났습니다.");
				}

				if ("N".equals(selectReview.getPointPayYn())) {
					// 지급불가는 항상 N으로 변경해줘야 일반회원이 통합멤버쉽회원 전환 시 작성한 후기에 포인트 지급 가능
					bdProductReview.setPointPayImpsbltYn("N");
					// 포인트지급여부
					bdProductReview.setPointPayYn("Y");

					String reviewType = bdProductReview.getPointPayType();
					Map<String, Object> serviceMap = memberPointService
							.updateMemberUserPointProvide(selectReview.getMemberNo(), reviewType);
					if ((boolean) serviceMap.get("result").equals("Success")) {
						bdProductReview.setPointPayCnclYn("N");
						bdProductReview.setPayPointAmt((Integer) serviceMap.get("point"));
						bdProductReview.setPointPayDtm(new Timestamp(now.getTime()));
						bdProductReview.setPlcySeq((Integer) serviceMap.get("plcySeq"));
					} else {
						throw new Exception((String) serviceMap.get("returnMessage"));
					}
				}
				break;
			case "C": // 지급취소

				if ("N".equals(selectReview.getPointPayCnclYn())) {
					// 포인트취소여부
					bdProductReview.setPointPayCnclYn("Y");

					Map<String, Object> serviceMap = memberPointService
							.updateMemberUserPointDeduct(selectReview.getMemberNo(), selectReview.getPayPointAmt());
					if ((boolean) serviceMap.get("result").equals("Success")) {
						bdProductReview.setPointPayYn("N");
						bdProductReview.setPayPointAmt(0);
						bdProductReview.setPointPayCnclDtm(new Timestamp(now.getTime()));
						bdProductReview.setPointPayCnclrNo(adminNo);
					} else {
						throw new Exception((String) serviceMap.get("returnMessage"));
					}
				}
				break;
			case "I": // 지급불가
				bdProductReview.setPointPayImpsbltYn("Y");
				break;
			default:
				// throw new Exception("지급상태를 선택하세요.");
			}

			bdProductReview.setModerNo(adminNo);
			bdProductReview.setModDtm(new Timestamp(now.getTime()));

			cnt += productReviewDao.updateWithoutPK(bdProductReview);

			// 최초 포인트 지급 시 고객에게 알림톡 전송
			if ("N".equals(selectReview.getPointPayType()) && ("G".equals(bdProductReview.getPointPayType())
					|| "P".equals(bdProductReview.getPointPayType()))) {
				MbMember member = this.memberService.getMember(selectReview.getMemberNo());

				if (UtilsText.isNotBlank(member.getHdphnNoText())) {
					reviewService.sendReviewMessageNoTrx(member, bdProductReview, selectReview);
				}

			}
		}

		return cnt;

	}

	public void sendReviewMessageNoTrx(MbMember member, BdProductReview bdProductReview, BdProductReview selectReview) {
		Map<String, String> map = new HashMap<>();
		MessageVO messageVO = new MessageVO();

		try {
			// 사이트
			String siteNo = selectReview.getSiteNo();
			messageVO.setSiteNo(siteNo);
			// 발신자
			messageVO.setSndrName(Const.SYS_SENDER_MESSAGE_NAME);
			// 발신번호
			messageVO.setSendTelNoText(Const.SYS_SENDER_MESSAGE_NUMBER);
			// 즉시발송여부
			messageVO.setReal(true);
			// 수신자명
			messageVO.setRcvrName(member.getMemberName());
			// 수신번호
			messageVO.setRecvTelNoText(member.getHdphnNoText());

			String linkUrl = "/product?prdtNo=" + selectReview.getPrdtNo() + "#productReview";
			// String linkUrl = "/mypage/shopping/notebook/product-review";
			// 통합몰
			linkUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, linkUrl);
			messageVO.setMesgId("ART1007");

			CmMessageTemplate cmMessageTemplate = msgTemplateService.getMessageTemplateByMesgId(messageVO.getMesgId());

			// ${수신자명}
			map.put("memberName", member.getMemberName());
			// ${랜딩URL}
			map.put("linkUrl", linkUrl);
			// ${포인트}
			map.put("payPointAmt", "" + bdProductReview.getPayPointAmt());
			// 알림톡/SMS 발송
			messageVO.setMesgContText(cmMessageTemplate.getMesgContText());
			messageVO.setMessageTemplateModel(map);
			messageService.setSendMessageProcess(messageVO);
		} catch (Exception e) {
			log.error("상품 후기 알림톡발송에 실패하였습니다. :{}", e);
		}

	}

	/**
	 * @Desc : 미답변 후기 갯수 조회
	 * @Method Name : getReviewGroupCount
	 * @Date : 2019. 11. 4.
	 * @Author : hsjhsj19
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getReviewGroupCount() throws Exception {
		return this.productReviewDao.selectReviewGroupCount();
	}

	/**
	 * @Desc : 후기 작성 포인트 지급 현황
	 * @Method Name : getMemberReviewPointStats
	 * @Date : 2019. 8. 7.
	 * @Author : hsjhsj19
	 * @param review
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMemberReviewPointStats(BdProductReview review) throws Exception {
		return this.productReviewDao.selectMemberReviewPointStats(review);

	}

}
