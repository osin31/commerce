package kr.co.abcmart.bo.display.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.abcmart.bo.display.model.master.DpWebzine;
import kr.co.abcmart.bo.display.vo.DpWebzineSearchVO;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class DisplayContentsServiceTests {

	@Autowired
	DisplayContentsService displayContentsService;

	@Mock
	HttpServletRequest request;

	@Mock
	HttpServletResponse response;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test() throws Exception {

		request.setAttribute("dispYn", Const.BOOLEAN_TRUE);

		Parameter<DpWebzineSearchVO> parameter = new Parameter<>(request, response);

//		DpWebzineSearchVO dpWebzineSearchVO = new DpWebzineSearchVO();

//		dpWebzineSearchVO.setDispYn("N");

//		parameter.setProperty(dpWebzineSearchVO, 0);

//		parameter.addAttribute("dispYn", Const.BOOLEAN_TRUE);
//		parameter.addAttribute("", "");
//		parameter.addAttribute("", "");
//		parameter.addAttribute("", "");
//		parameter.addAttribute("", "");
//		parameter.addAttribute("", "");
//		parameter.addAttribute("", "");

		Pageable<DpWebzineSearchVO, DpWebzine> pageable = new Pageable<>(parameter);

		Page<DpWebzine> page = displayContentsService.getDpWebzineList(pageable);

		log.debug("{}", page);

	}
}
