package kr.co.abcmart.bo.display.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.display.model.master.DpChnnlSearchWord;
import kr.co.abcmart.bo.display.model.master.DpSearchWord;
import kr.co.abcmart.bo.display.model.master.DpSearchWordHistory;
import kr.co.abcmart.bo.display.service.ChannelSearchWordService;
import kr.co.abcmart.bo.display.service.SearchWordService;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/display/search-word")
public class SearchWordController extends BaseController {

	@Autowired
	private SearchWordService searchWordService;

	@Autowired
	private ChannelSearchWordService channelSearchWordService;

	@Autowired
	private SiteService siteService;

	/**
	 * @Desc : 검색어 관리
	 * @Method Name : searchWord
	 * @Date : 2019. 4. 16.
	 * @Author : lee
	 * @param parameter
	 * @return
	 */
	@GetMapping("")
	public ModelAndView searchWord(Parameter<?> parameter) throws Exception {

		parameter.addAttribute("siteList", siteService.getSiteList());
		parameter.addAttribute("ots", Const.SITE_NO_OTS);

		SySiteChnnl sySiteChnnl = new SySiteChnnl();
		sySiteChnnl.setSiteNo(Const.SITE_NO_ART);
		parameter.addAttribute("channelList", siteService.getSearchDisplayChannel(sySiteChnnl));

		return forward("/display/search-word/search-word");
	}

	/**
	 * @Desc : 검색어 목록 조회
	 * @Method Name : list
	 * @Date : 2019. 4. 16.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/list")
	public void list(Parameter<DpSearchWord> parameter) throws Exception {

		Pageable<DpSearchWord, DpSearchWord> pageable = new Pageable<>(parameter);

		Page<DpSearchWord> page = searchWordService.selectDpSearchWordList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 채널 검색어 목록 조회
	 * @Method Name : readChannelWordList
	 * @Date : 2019. 12. 18.
	 * @Author : 이강수
	 * @param Parameter<DpChnnlSearchWord>
	 */
	@PostMapping("/read-channel-word-list")
	public void readChannelWordList(Parameter<DpChnnlSearchWord> parameter) throws Exception {

		Pageable<DpChnnlSearchWord, DpChnnlSearchWord> pageable = new Pageable<>(parameter);

		Page<DpChnnlSearchWord> page = channelSearchWordService.selectChannelSearchWordList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 검색어 저장
	 * @Method Name : save
	 * @Date : 2019. 4. 17.
	 * @Author : 이가영, 이강수
	 * @param parameter
	 */
	@PostMapping("/save")
	public void save(Parameter<DpSearchWord[]> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		DpSearchWord[] dpSearchWords = parameter.get();

		resultMap = searchWordService.setDpSearchWord(dpSearchWords);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 중복체크를 위한 검색어 목록 전체 조회
	 * @Method Name : readAllSearchWordList
	 * @Date : 2019. 12. 18.
	 * @Author : 이강수
	 * @param Parameter<DpSearchWord>
	 */
	@PostMapping("/read-all-search-word-list")
	public void readAllSearchWordList(Parameter<DpSearchWord> parameter) throws Exception {
		Pageable<DpSearchWord, DpSearchWord> pageable = new Pageable<>(parameter);

		// 전체 가져오기 인자 set
		pageable.getBean().setIsPageable(Const.BOOLEAN_FALSE);

		Page<DpSearchWord> page = searchWordService.selectDpSearchWordList(pageable);

		writeJson(parameter, page);
	}

	/**
	 * @Desc : 채널 검색어 저장
	 * @Method Name : saveChannelWord
	 * @Date : 2019. 12. 18.
	 * @Author : 이강수
	 * @param Parameter<DpChnnlSearchWord[]>
	 */
	@PostMapping("/save-channel-word")
	public void saveChannelWord(Parameter<DpChnnlSearchWord[]> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		DpChnnlSearchWord[] dpChnnlSearchWords = parameter.get();

		resultMap = channelSearchWordService.setChannelSearchWord(dpChnnlSearchWords);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 중복체크를 위한 채널 검색어 목록 전체 조회
	 * @Method Name : readAllChannelSearchWordList
	 * @Date : 2019. 12. 19.
	 * @Author : 이강수
	 * @param Parameter<DpChnnlSearchWord>
	 */
	@PostMapping("/read-all-channel-search-word-list")
	public void readAllChannelSearchWordList(Parameter<DpChnnlSearchWord> parameter) throws Exception {
		Pageable<DpChnnlSearchWord, DpChnnlSearchWord> pageable = new Pageable<>(parameter);

		// 전체 가져오기 인자 set
		pageable.getBean().setIsPageable(Const.BOOLEAN_FALSE);
		pageable.getBean().setIsAllChnnl(Const.BOOLEAN_TRUE);

		Page<DpChnnlSearchWord> page = channelSearchWordService.selectChannelSearchWordList(pageable);

		writeJson(parameter, page);
	}

	/**
	 * @Desc : 히스토리 보기 팝업
	 * @Method Name : history
	 * @Date : 2019. 4. 17.
	 * @Author : 이가영
	 * @param parameter
	 * @return
	 */
	@GetMapping("/history")
	public ModelAndView history(Parameter<DpSearchWord> parameter) throws Exception {

		DpSearchWord dpSearchWord = parameter.get();

		parameter.addAttribute("siteNo", dpSearchWord.getSiteNo());
		parameter.addAttribute("siteName", dpSearchWord.getSiteName());
		parameter.addAttribute("srchWordGbnType", dpSearchWord.getSrchWordGbnType());

		return forward("/display/search-word/search-word-history");
	}

	/**
	 * @Desc : 검색어 이력 목록 조회
	 * @Method Name : historyList
	 * @Date : 2019. 4. 18.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/history/list")
	public void historyList(Parameter<DpSearchWordHistory> parameter) throws Exception {

		Pageable<DpSearchWordHistory, DpSearchWordHistory> pageable = new Pageable<>(parameter);

		Page<DpSearchWordHistory> page = searchWordService.selectDpSearchWordHistoryList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 인기 검색어 목록 조회
	 * @Method Name : popularList
	 * @Date : 2019. 7. 8.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/popular/list")
	public void popularList(Parameter<DpSearchWord> parameter) throws Exception {

		Pageable<DpSearchWord, JSONObject> pageable = new Pageable<>(parameter);

		Page<JSONObject> page = searchWordService.getPopularSearchWordList(pageable);

		writeJson(parameter, page.getGrid());
	}

}