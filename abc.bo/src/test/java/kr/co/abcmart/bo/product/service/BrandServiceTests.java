package kr.co.abcmart.bo.product.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.abcmart.bo.product.model.master.DpBrand;
import kr.co.abcmart.bo.product.model.master.DpBrandStyle;
import kr.co.abcmart.bo.product.vo.DpBrandSearchVO;
import kr.co.abcmart.bo.product.vo.DpBrandStyleSearchVO;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class BrandServiceTests {

	@Autowired
	BrandService BrandService;

	@Mock
	HttpServletRequest request;

	@Mock
	HttpServletResponse response;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

//	@Test
	public void testSearchBrandList() throws Exception {
		log.debug("브랜드 목록 조회 테스트");
		Parameter<DpBrandSearchVO> parameter = new Parameter<>(request, response);
		Pageable<DpBrandSearchVO, DpBrand> pageable = new Pageable<DpBrandSearchVO, DpBrand>(parameter);
		Page<DpBrand> page = this.BrandService.searchBrandList(pageable);
		log.debug("{}", page);
	}

	@Test
	public void testSearchBrandStyleList() throws Exception {
		log.debug("브랜드스타일 목록 조회 테스트");
		request.setAttribute("pageCount", "100");
		Parameter<DpBrandStyleSearchVO> parameter = new Parameter<DpBrandStyleSearchVO>(request, response);
		log.debug("param value is : {}", parameter.get().getPageCount());
		Pageable<DpBrandStyleSearchVO, DpBrandStyle> pageable = new Pageable<DpBrandStyleSearchVO, DpBrandStyle>(
				parameter);
//		Page<DpBrandStyle> result = this.BrandService.searchBrandStyleList(pageable);
//		log.debug("{}", result);
	}

//	@Test
	public void testSearchBrandApi() throws Exception {
		log.debug("브랜드 조회 API 테스트");
		DpBrandSearchVO criteria = new DpBrandSearchVO();
//		criteria.setBrandName("aa");
		List<DpBrand> result = this.BrandService.searchBrandApi(criteria);
		log.debug("{}", result);
	}

}
