package kr.co.abcmart.bo.cmm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.cmm.model.master.CmForbiddenWord;
import kr.co.abcmart.bo.cmm.service.ForbiddenWordService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.request.Parameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("cmm")
public class ForbiddenWordController extends BaseController {

	@Autowired
	ForbiddenWordService service;

	/**
	 * 금칙어를 조회한다.
	 * 
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("forbidden-word")
	public ModelAndView read(Parameter<CmForbiddenWord> parameter) throws Exception {
		CmForbiddenWord cmForbiddenWord = parameter.get();
		cmForbiddenWord.setForbidWordType(ForbiddenWordService.FORBIDDEN_WORD);

		cmForbiddenWord = service.getTopWithSyAdminByForbidWordTypeOrderbyModDtmDesc(cmForbiddenWord);
		if (cmForbiddenWord == null) {
			cmForbiddenWord = new CmForbiddenWord();
		}
		parameter.addAttribute(cmForbiddenWord);

		return forward("cmm/forbidden-word/forbidden-word-main");
	}

	/**
	 * 금칙어를 등록/수정한다.
	 * 
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("forbidden-word/update")
	public void update(Parameter<CmForbiddenWord> parameter) throws Exception {
		CmForbiddenWord cmForbiddenWord = parameter.get();
		cmForbiddenWord.setForbidWordType(ForbiddenWordService.FORBIDDEN_WORD);

		cmForbiddenWord.validate();

		service.regist(cmForbiddenWord);
	}

	/**
	 * 비밀번호 금칙어를 조회한다.
	 * 
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("forbidden-pwd")
	public ModelAndView pwRead(Parameter<CmForbiddenWord> parameter) throws Exception {
		CmForbiddenWord cmForbiddenWord = parameter.get();
		cmForbiddenWord.setForbidWordType(ForbiddenWordService.FORBIDDEN_PWD);

		cmForbiddenWord = service.getTopWithSyAdminByForbidWordTypeOrderbyModDtmDesc(cmForbiddenWord);
		if (cmForbiddenWord == null) {
			cmForbiddenWord = new CmForbiddenWord();
		}
		parameter.addAttribute(cmForbiddenWord);

		return forward("cmm/forbidden-pwd/forbidden-pwd-main");
	}

	/**
	 * 비밀번호 금칙어를 등록/수정한다.
	 * 
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("forbidden-pwd/update")
	public void pwUpdate(Parameter<CmForbiddenWord> parameter) throws Exception {
		CmForbiddenWord cmForbiddenWord = parameter.get();
		cmForbiddenWord.setForbidWordType(ForbiddenWordService.FORBIDDEN_PWD);

		cmForbiddenWord.validate();

		service.regist(cmForbiddenWord);
	}

}
