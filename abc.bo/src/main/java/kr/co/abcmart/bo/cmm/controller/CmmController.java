package kr.co.abcmart.bo.cmm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.abcmart.bo.cmm.service.CmmService;
import kr.co.abcmart.bo.cmm.vo.EditorImageVo;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.request.Parameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("cmm")
public class CmmController extends BaseController {

	@Autowired
	CmmService cmmService;

	@RequestMapping(value = "/editor/image/upload", method = RequestMethod.POST)
	public void imageUpload(Parameter<EditorImageVo> parameter) throws Exception {
		EditorImageVo editorImageVo = parameter.get();
		editorImageVo.setFromPromotion(false);
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			result = cmmService.imageUpload(editorImageVo);
		} catch (Exception e) {
			Map<String, Object> error = new HashMap<String, Object>();
			error.put("message", e.getMessage());

			result.put("uploaded", 0);
			result.put("error", error);
		}

		log.debug("result - {}", result);

		writeJson(parameter, result);
	}
	
	@RequestMapping(value = "/editor/image/promotion/upload", method = RequestMethod.POST)
	public void imageUploadTest(Parameter<EditorImageVo> parameter) throws Exception {
		EditorImageVo editorImageVo = parameter.get();
		editorImageVo.setFromPromotion(true);
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			result = cmmService.imageUpload(editorImageVo);
		} catch (Exception e) {
			Map<String, Object> error = new HashMap<String, Object>();
			error.put("message", e.getMessage());
			
			result.put("uploaded", 0);
			result.put("error", error);
		}
		
		log.debug("result - {}", result);
		
		writeJson(parameter, result);
	}

}