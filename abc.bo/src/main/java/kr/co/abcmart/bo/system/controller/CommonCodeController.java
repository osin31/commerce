package kr.co.abcmart.bo.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.system.model.master.SyCode;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;

@Controller
@RequestMapping("system/commoncode")
public class CommonCodeController extends BaseController {

	@Autowired
	private CommonCodeService commonCodeService;

	/**
	 * @Desc : 코드 전체 화면 호출
	 * @Method Name : commoncodeMain
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("")
	public ModelAndView commoncodeMain(Parameter<?> parameter) throws Exception {
		return forward("system/commoncode/commoncode-main");
	}

	/**
	 * @Desc : 상위코드 등록 화면 호출
	 * @Method Name : createUpCodeForm
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/create-upcode-form")
	public ModelAndView createUpCodeForm(Parameter<?> parameter) throws Exception {
		return forward("system/commoncode/create-upcode-form-pop");

	}

	/**
	 * @Desc : 하위코드 등록 화면 호출
	 * @Method Name : createDownCodeForm
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/create-downcode-form")
	public ModelAndView createDownCodeForm(Parameter<SyCodeDetail> parameter) throws Exception {
		SyCodeDetail syCodeDetail = parameter.create(SyCodeDetail.class);
		List<SyCodeDetail> downCodeList = commonCodeService.getUpCodeList(syCodeDetail); // 하위코드 등록화면에 뿌려줄 상위코드목록

		parameter.addAttribute("downCodeList", downCodeList);

		return forward("system/commoncode/create-downcode-form-pop");
	}

	/**
	 * @Desc : 상위, 하위 코드 수정화면
	 * @Method Name : updateUpDownCodeForm
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/update-updowncode-form")
	public ModelAndView updateUpDownCodeForm(Parameter<?> parameter) throws Exception {
		parameter.validate();
		SyCodeDetail syCodeDetailVO = parameter.create(SyCodeDetail.class);

		Map<String, Object> codeMap = commonCodeService.upDownCheck(syCodeDetailVO);
		String path = (String) codeMap.get("path");

		SyCodeDetail syCodeDetail = (SyCodeDetail) codeMap.get("syCodeDetail");
		if (UtilsText.isNotBlank(syCodeDetail.getUpCodeDtlNo())) {
			parameter.addAttribute("downCodeList", (List<SyCodeDetail>) codeMap.get("downCodeList"));
		}
		parameter.addAttribute("syCodeDetail", syCodeDetail);
		if (UtilsText.equals(parameter.getString("systemCodeYn"), Const.BOOLEAN_TRUE)) {
			parameter.addAttribute("systemCodeYn", parameter.getString("systemCodeYn"));
		}

		return forward(path);
	}

	/**
	 * @Desc : 코드 그룹 리스트 호출
	 * @Method Name : readCodeGroup
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-codegroup")
	public void readCodeGroup(Parameter<SyCode> parameter) throws Exception {
		Pageable<SyCode, SyCode> syCodePageable = new Pageable<>(parameter);
		Page<SyCode> page = commonCodeService.getCodeGroup(syCodePageable);

		writeJson(parameter, page.getGrid());

	}

	/**
	 * @Desc : 상위,하위코드 목록을 조회한다.
	 * @Method Name : readUpDownCode
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-updowncode")
	public void readUpDownCode(Parameter<SyCodeDetail> parameter) throws Exception {
		Pageable<SyCodeDetail, SyCodeDetail> syCodeDetailPageable = new Pageable<>(parameter);
		Page<SyCodeDetail> page = commonCodeService.getUpDownCode(syCodeDetailPageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 코드그룹을 상세 조회한다.
	 * @Method Name : readGroupDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-codegroup-detail")
	public void readGroupDetail(Parameter<?> parameter) throws Exception {
		parameter.validate();
		SyCode params = parameter.create(SyCode.class);
		List<SyCode> syCode = commonCodeService.getGroupDetail(params);

		writeJson(parameter, syCode);
	}

	/**
	 * @Desc : 코드그룹을 수정한다.
	 * @Method Name : updateCodeGroup
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/update-codegroup")
	public void updateCodeGroup(Parameter<SyCode> parameter) throws Exception {
		parameter.validate();
		if (UtilsText.isBlank(parameter.get().getSystemCodeYn())) {
			parameter.get().setSystemCodeYn(Const.BOOLEAN_TRUE);
		}
		commonCodeService.updateCodeGroup(parameter.get());

		writeJson(parameter, true);
	}

	/**
	 * @Desc : 코드그룹 등록시 중복되는 코드피륻 확인
	 * @Method Name : checkOverlapCodefield
	 * @Date : 2019. 11. 7.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/check-overlap-codefield")
	public void checkOverlapCodefield(Parameter<SyCode> parameter) throws Exception {
		Map<String, Object> rsMap = new HashMap<>();
		try {
			int count = commonCodeService.checkOverlapCodefield(parameter.get());
			rsMap.put("count", count);
		} catch (Exception e) {
			rsMap.put("Message", e.toString());
		}

		writeJson(parameter, rsMap);
	}

	/**
	 * @Desc : 상위, 하위코드 상세를 수정한다.
	 * @Method Name : updateDownCodeDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/update-updowncode-detail")
	public void updateDownCodeDetail(Parameter<?> parameter) throws Exception {
		// Parameter 클래스의 createArray 메소드를 이용하여 그리드에서 배열로 넘어온 파라메터 값을 VO에 맵핑
		SyCodeDetail syCodeDetail = parameter.create(SyCodeDetail.class);

		commonCodeService.updateUpDownCodeDetail(syCodeDetail);

	}

	/**
	 * @Desc : 코드그룹 삭제시에 상위,하위코드들을 가지고 있는지 체크.
	 * @Method Name : removeCheck
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/remove-check")
	public void removeCheck(Parameter<?> parameter) throws Exception {
		SyCodeDetail params = parameter.create(SyCodeDetail.class);
		String count = Integer.toString(commonCodeService.getRemoveCheck(params));

		writeJson(parameter, count);
	}

	/**
	 * @Desc : 상위코드 삭제시 하위코드 존재하는지 체크
	 * @Method Name : removeUpCodeCheck
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/remove-upcodecheck")
	public void removeUpCodeCheck(Parameter<?> parameter) throws Exception {
		SyCodeDetail params = parameter.create(SyCodeDetail.class);
		String count = Integer.toString(commonCodeService.getRemoveUpCodeCheck(params));

		writeJson(parameter, count);
	}

	/**
	 * @Desc : 상위,하위코드 삭제
	 * @Method Name : removeUpCode
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/remove-updowncode")
	public void removeUpCode(Parameter<?> parameter) throws Exception {
		SyCodeDetail params = parameter.create(SyCodeDetail.class);
		commonCodeService.deleteUpDownCode(params);

	}

	/**
	 * @Desc : 코드 그룹을 삭제한다.
	 * @Method Name : removeCodeGroup
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/remove-codegroup")
	public void removeCodeGroup(Parameter<?> parameter) throws Exception {
		parameter.validate();
		SyCode params = parameter.create(SyCode.class);
		if (UtilsText.isBlank(params.getSystemCodeYn())) {
			params.setSystemCodeYn(Const.BOOLEAN_TRUE);
		}
		if (UtilsText.equals(Const.BOOLEAN_TRUE, params.getSystemCodeYn())) {
			throw new Exception("시스템 코드는 삭제가 불가능합니다.");
		}
		commonCodeService.deleteCodeGroup(params);
	}

	/**
	 * @Desc : 코드 그룹을 저장한다.
	 * @Method Name : createCodeGroup
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/create-codegroup")
	public void createCodeGroup(Parameter<?> parameter) throws Exception {
		parameter.validate();
		SyCode params = parameter.create(SyCode.class);

		commonCodeService.insertCodeGroup(params);
		writeJson(parameter, true);
	}

	/**
	 * @Desc : 상위코드를 저장한다.
	 * @Method Name : createUpCodeDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/create-upcode-detail")
	public void createUpCodeDetail(Parameter<?> parameter) throws Exception {
		SyCodeDetail params = parameter.create(SyCodeDetail.class);

		commonCodeService.insertUpCodeDetail(params);

		writeJson(parameter, true);
	}

	/**
	 * @Desc : 하위코드를 저장한다.
	 * @Method Name : createDownCodeDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/create-downcode-detail")
	public void createDownCodeDetail(Parameter<?> parameter) throws Exception {
		SyCodeDetail params = parameter.create(SyCodeDetail.class);

		commonCodeService.insertDownCodeDetail(params);
		writeJson(parameter, true);
	}

	/**
	 * @Desc : 상위, 하위 코드 그리드에서 수정
	 * @Method Name : updateUpDownCodeGrid
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/update-updown-codegrid")
	public void updateUpDownCodeGrid(Parameter<?> parameter) throws Exception {
		SyCodeDetail[] entity = parameter.createArray(SyCodeDetail.class, "codeField");

		commonCodeService.updateUpDownCodeGrid(entity);

		writeJson(parameter, true);
	}

	/**
	 * @Desc : 정렬순서 현재 최대값 +1
	 * @Method Name : readMaxSortseq
	 * @Date : 2019. 6. 7.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-max-sortseq")
	public void readMaxSortseq(Parameter<?> parameter) throws Exception {
		Map<String, Object> rsMap = commonCodeService.selectMaxSortSeq();
		writeJson(parameter, rsMap);
	}

}
