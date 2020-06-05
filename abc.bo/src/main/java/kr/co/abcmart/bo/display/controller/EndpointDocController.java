package kr.co.abcmart.bo.display.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;

@Controller
@RequestMapping("noacl")
public class EndpointDocController extends BaseController {

	@Autowired

	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@RequestMapping(value = "/endPoints", method = RequestMethod.GET)
	public ModelAndView getEndPointsInView(Parameter<?> parameter) {

		List<Map<String, String>> pages = new ArrayList<Map<String, String>>();
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();

		for (Entry<RequestMappingInfo, HandlerMethod> elem : map.entrySet()) {

			RequestMappingInfo key = elem.getKey();

			HandlerMethod method = elem.getValue();

			Map<String, String> item = new HashMap<String, String>();

			item.put("path", (String) key.getPatternsCondition().getPatterns().toArray()[0]);

			item.put("cls", method.getMethod().getDeclaringClass().getSimpleName());

			item.put("method", method.getMethod().getName());
			item.put("pkg", method.getBeanType().getName());
			item.put("mc", key.getMethodsCondition().toString());

			if (!item.get("pkg").contains("kr.co.abcmart.bo.display")
					&& !item.get("pkg").contains("kr.co.abcmart.bo.product")
					&& !item.get("pkg").contains("kr.co.abcmart.bo.promotion")
					&& !item.get("pkg").contains("kr.co.abcmart.bo.event")) {
				continue;
			}

//			auditMenu auditMenu = method.getMethod().getDeclaringClass().getAnnotation(AuditMenu.class);
//
//			if (auditMenu != null) {
//
//				item.put("menuId", auditMenu.value().getMenuId());
//
//			}
//
//			AuditOperation operation = method.getMethodAnnotation(AuditOperation.class);
//
//			if (operation != null) {
//
//				item.put("operation", operation.value().getOperation());
//
//			}

			Description desc = method.getMethodAnnotation(Description.class);
			if (desc != null) {

				item.put("desc", desc.value());

			}

			StringBuffer sb = new StringBuffer();

			for (MethodParameter param : method.getMethodParameters()) {

				sb.append(param.getParameterType().getSimpleName()).append(", ");

			}

			if (sb.toString().length() > 0) {

				item.put("param", sb.toString().substring(0, sb.toString().length() - 2));

			} else {

				item.put("param", "");

			}

			item.put("rtn", method.getMethod().getReturnType().getSimpleName());

			if (UtilsText.equals(item.get("rtn"), "XcnResponseVO")) {
				result.add(item);
			} else {
				pages.add(item);

			}

		}
		parameter.addAttribute("endPoints", result);
		parameter.addAttribute("pages", pages);

		return forward("/display/contents/popup/endPoints");
	}

}