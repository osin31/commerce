package kr.co.abcmart.bo.display.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.diquest.ir.client.command.CommandSearchRequest;
import com.diquest.ir.common.msg.protocol.Protocol;
import com.diquest.ir.common.msg.protocol.query.OrderBySet;
import com.diquest.ir.common.msg.protocol.query.Query;
import com.diquest.ir.common.msg.protocol.query.QuerySet;
import com.diquest.ir.common.msg.protocol.query.SelectSet;
import com.diquest.ir.common.msg.protocol.query.WhereSet;
import com.diquest.ir.common.msg.protocol.result.Result;
import com.diquest.ir.common.msg.protocol.result.ResultSet;

import kr.co.abcmart.bo.display.model.master.DpSearchWord;
import kr.co.abcmart.bo.display.model.master.DpSearchWordHistory;
import kr.co.abcmart.bo.display.repository.master.DpSearchWordDao;
import kr.co.abcmart.bo.display.repository.master.DpSearchWordHistoryDao;
import kr.co.abcmart.common.constant.BaseConst;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SearchWordService {

	@Autowired
	private DpSearchWordDao dpSearchWordDao;

	@Autowired
	private DpSearchWordHistoryDao dpSearchWordHistoryDao;

	@Value("${search.ip}")
	private String SEARCH_IP;

	@Value("${search.port}")
	private String SEARCH_PORT;

	/**
	 * @Desc : 검색어 목록 조회
	 * @Method Name : selectDpSearchWordList
	 * @Date : 2019. 4. 17.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public Page<DpSearchWord> selectDpSearchWordList(Pageable<DpSearchWord, DpSearchWord> pageable) throws Exception {

		int count = dpSearchWordDao.selectDpSearchWordListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpSearchWordDao.selectDpSearchWordList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 검색어 저장
	 * @Method Name : setDpSearchWord
	 * @Date : 2019. 4. 17.
	 * @Author : 이가영
	 * @param dpSearchWords
	 * @return
	 */
	public Map<String, Object> setDpSearchWord(DpSearchWord[] dpSearchWords) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		// validate
		String result = this.validateSearchWord(dpSearchWords);

		if (UtilsText.equals(result, Const.BOOLEAN_FALSE)) {
			resultMap.put("result", Const.BOOLEAN_FALSE);
			throw new Exception("검색어는 중복될 수 없습니다.");
		}

		UserDetails user = LoginManager.getUserDetails();

		int len = dpSearchWords.length;
		int idx = 0;
		for (DpSearchWord dpSearchWord : dpSearchWords) {

			idx++;

			dpSearchWord.setModerNo(user.getAdminNo());

			if (dpSearchWord.getDelYn() != 1) {
				if (UtilsText.equals(BaseConst.CRUD_I, dpSearchWord.getStatus())) { // 추가

					dpSearchWord.setRgsterNo(user.getAdminNo());
					this.insertDpSearchWord(dpSearchWord);
				} else if (UtilsText.equals(BaseConst.CRUD_U, dpSearchWord.getStatus())) { // 수정
					this.updateDpSearchWord(dpSearchWord);
				}
			} else { // 삭제
				this.deleteDpSearchWord(dpSearchWord);
			}

			// 검색어 이력 추가
			if (idx == len) {
				dpSearchWordHistoryDao.insertDpSearchWordHistory(dpSearchWord);
			}
		}

		resultMap.put("result", Const.BOOLEAN_TRUE);
		return resultMap;
	}

	/**
	 * @Desc : 검색어 중복 체크
	 * @Method Name : validateSearchWord
	 * @Date : 2019. 12. 19.
	 * @Author : 이강수
	 * @param DpSearchWord[]
	 * @return
	 */
	public String validateSearchWord(DpSearchWord[] dpSearchWords) throws Exception {

		String result = Const.BOOLEAN_TRUE;

		for (DpSearchWord dpSearchWord : dpSearchWords) {
			if (dpSearchWord.getDelYn() != 1 && UtilsText.equals(BaseConst.CRUD_I, dpSearchWord.getStatus())) {
				int validateInteger = dpSearchWordDao.selectDpSearchWordForValidate(dpSearchWord);
				if (validateInteger > 0) {
					result = Const.BOOLEAN_FALSE;
					return result;
				}
			}
		}

		return result;
	}

	/**
	 * @Desc : 검색어 추가
	 * @Method Name : insertDpSearchWord
	 * @Date : 2019. 4. 17.
	 * @Author : 이가영
	 * @param dpSearchWord
	 */
	public void insertDpSearchWord(DpSearchWord dpSearchWord) throws Exception {

		dpSearchWordDao.insertDpSearchWord(dpSearchWord);
	}

	/**
	 * @Desc : 검색어 수정
	 * @Method Name : updateDpSearchWord
	 * @Date : 2019. 4. 17.
	 * @Author : 이가영
	 * @param dpSearchWord
	 */
	public void updateDpSearchWord(DpSearchWord dpSearchWord) throws Exception {

		dpSearchWordDao.updateDpSearchWord(dpSearchWord);
	}

	/**
	 * @Desc : 검색어 삭제
	 * @Method Name : deleteDpSearchWord
	 * @Date : 2019. 4. 17.
	 * @Author : 이가영
	 * @param dpSearchWord
	 */
	public void deleteDpSearchWord(DpSearchWord dpSearchWord) throws Exception {

		dpSearchWordDao.deleteDpSearchWord(dpSearchWord);
	}

	/**
	 * @Desc : 검색어 이력 목록 조회
	 * @Method Name : selectDpSearchWordHistoryList
	 * @Date : 2019. 4. 18.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public Page<DpSearchWordHistory> selectDpSearchWordHistoryList(
			Pageable<DpSearchWordHistory, DpSearchWordHistory> pageable) throws Exception {

		int count = dpSearchWordHistoryDao.selectDpSearchWordHistoryListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpSearchWordHistoryDao.selectDpSearchWordHistoryList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 인기 검색어 목록 조회
	 * @Method Name : getPopularSearchWordList
	 * @Date : 2019. 7. 9.
	 * @Author : 이가영
	 * @return
	 */
	public Page<JSONObject> getPopularSearchWordList(Pageable<DpSearchWord, JSONObject> pageable) throws Exception {

		/** 검색 ip **/
		String searchIP = SEARCH_IP;

		/** 검색 port **/
		int searchPORT = Integer.parseInt(SEARCH_PORT);

		/** 검색엔진 쿼리 및 결과 초기화 **/
		QuerySet querySet = null;
		SelectSet[] selectSet = null;
		WhereSet[] whereSet = null;
		OrderBySet[] orderBySet = null;
		Result result = null;
		Result[] resultList = null;
		CommandSearchRequest command = null;

		/** 반환 데이터 **/
		JSONObject totalResultJson = new JSONObject();

		/** 컬렉션 명 **/
		String collection = "HOTKEYWORDS";

		/** 인기검색어 아이디 **/
		String[] hotKeywordId = new String[] { "LOG_ART" };

		/** ART | OTS 인기검색어 구분 **/
		String[] hotKeywordPart = new String[] { "ART" };

		/** 쿼리 셋 **/
		querySet = new QuerySet(hotKeywordId.length);

		for (int i = 0; i < hotKeywordId.length; i++) {

			/** SelectSet **/
			selectSet = new SelectSet[] { new SelectSet("KEYWORD", Protocol.SelectSet.NONE), /** 검색어 **/
					new SelectSet("RANKING", Protocol.SelectSet.NONE), /** 현재랭크 **/
					new SelectSet("PREV_RANK", Protocol.SelectSet.NONE), /** 이전랭크 **/
					new SelectSet("COUNT", Protocol.SelectSet.NONE) /** 검색횟수 **/
			};

			/** WhereSet **/
			whereSet = new WhereSet[] {
					new WhereSet("IDX_TRENDS_ID", Protocol.WhereSet.OP_HASALL, hotKeywordId[i], 1) /** 컬렉션 아이디 어절 **/
			};

			/** OrderBySet **/
			orderBySet = new OrderBySet[] {
					new OrderBySet(true, "SORT_RANKING", Protocol.OrderBySet.OP_POSTWEIGHT) /** 랭크 오름차순 **/
			};

			/** 검색엔진 쿼리 및 결과 초기화 **/
			Query query = new Query(); /** 쿼리 생성 **/
			query.setSelect(selectSet); /** Select 생성 **/
			query.setFrom(collection); /** From 생성 **/
			query.setWhere(whereSet); /** Where 생성 **/
			query.setOrderby(orderBySet); /** OrderBy 생성 **/
			query.setDebug(true); /** Debug 로그 생성 여부 **/
			query.setPrintQuery(false); /** PrintQuery 로그 생성 여부 **/
			query.setLoggable(true); /** 로그에 남길 검색어 명시 여부 **/
			query.setResult(0, 29); /** 페이지 당 결과 갯수 설정 **/
			querySet.addQuery(query); /** 쿼리셋에 추가 **/
		}

		/** 검색 서버로 검색 정보 전송 **/
		int returnCode = 0;
		command = new CommandSearchRequest(searchIP, searchPORT);
		returnCode = command.request(querySet);
		log.debug("인기검색어 전송결과코드 : " + returnCode + " | 전송결과 : " + (returnCode == 1 ? "성공" : "실패"));

//		JSONArray resultArray = new JSONArray();
		List<JSONObject> resultArray = new LinkedList<>();
		if (returnCode > 0) {
			ResultSet results = command.getResultSet();
			resultList = results.getResultList();

			if (resultList.length > 0) {
//				for (int i = 0; i < resultList.length; i++) {
//				result = resultList[i];
				for (int r = 0; r < resultList[0].getRealSize(); r++) {
					JSONObject resultJson = new JSONObject();
					for (int c = 0; c < resultList[0].getNumField(); c++) {
						resultJson.put(new String(selectSet[c].getField()), new String(resultList[0].getResult(r, c)));
					}
					resultArray.add(resultJson);
				}
//					totalResultJson.put(hotKeywordPart[i], resultArray);
//				}
			}
		} else {
			resultList = new Result[1];
			resultList[0] = new Result();
		}

		pageable.setTotalCount(30);
		pageable.setContent(resultArray);

		return pageable.getPage();
	}

}
