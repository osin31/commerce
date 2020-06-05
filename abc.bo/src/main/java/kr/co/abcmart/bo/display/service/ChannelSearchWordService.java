package kr.co.abcmart.bo.display.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.display.model.master.DpChnnlSearchWord;
import kr.co.abcmart.bo.display.repository.master.DpChnnlSearchWordDao;
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
public class ChannelSearchWordService {

	@Autowired
	private DpChnnlSearchWordDao dpChnnlSearchWordDao;

	/**
	 * @Desc : 채널 검색어 목록 조회
	 * @Method Name : selectChannelSearchWordList
	 * @Date : 2019. 12. 18.
	 * @Author : 이강수
	 * @param Pageable<DpChnnlSearchWord, DpChnnlSearchWord>
	 * @return
	 */
	public Page<DpChnnlSearchWord> selectChannelSearchWordList(Pageable<DpChnnlSearchWord, DpChnnlSearchWord> pageable)
			throws Exception {

		int count = dpChnnlSearchWordDao.selectDpChnnlSearchWordListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(dpChnnlSearchWordDao.selectDpChnnlSearchWordList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 채널 검색어 추가/수정/삭제
	 * @Method Name : setChannelSearchWord
	 * @Date : 2019. 12. 18.
	 * @Author : 이강수
	 * @param DpChnnlSearchWord[]
	 */
	public Map<String, Object> setChannelSearchWord(DpChnnlSearchWord[] dpChnnlSearchWords) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		// validate
		String result = this.validateChannelSearchWord(dpChnnlSearchWords);

		if (UtilsText.equals(result, Const.BOOLEAN_FALSE)) {
			resultMap.put("result", Const.BOOLEAN_FALSE);
			throw new Exception("검색어는 중복될 수 없습니다.");
		}

		UserDetails user = LoginManager.getUserDetails();

		for (DpChnnlSearchWord dpChnnlSearchWord : dpChnnlSearchWords) {

			dpChnnlSearchWord.setModerNo(user.getAdminNo());

			if (dpChnnlSearchWord.getDelYn() != 1) {
				if (UtilsText.equals(BaseConst.CRUD_I, dpChnnlSearchWord.getStatus())) { // 추가

					dpChnnlSearchWord.setRgsterNo(user.getAdminNo());
					this.insertDpChnnlSearchWord(dpChnnlSearchWord);

				} else if (UtilsText.equals(BaseConst.CRUD_U, dpChnnlSearchWord.getStatus())) { // 수정

					this.updateDpChnnlSearchWord(dpChnnlSearchWord);
				}
			} else { // 삭제

				this.deleteDpChnnlSearchWord(dpChnnlSearchWord);
			}
		}

		resultMap.put("result", Const.BOOLEAN_TRUE);
		return resultMap;
	}

	/**
	 * @Desc : 채널 검색어 추가
	 * @Method Name : insertDpChnnlSearchWord
	 * @Date : 2019. 12. 23.
	 * @Author : 이강수
	 * @param DpChnnlSearchWord
	 */
	@CacheEvict(value = "channelSearchWordService.getChannelSearchWordList", allEntries = true)
	public void insertDpChnnlSearchWord(DpChnnlSearchWord dpChnnlSearchWord) throws Exception {
		dpChnnlSearchWordDao.insertDpChnnlSearchWord(dpChnnlSearchWord);
	}

	/**
	 * @Desc : 채널 검색어 수정
	 * @Method Name : updateDpChnnlSearchWord
	 * @Date : 2019. 12. 23.
	 * @Author : 이강수
	 * @param DpChnnlSearchWord
	 */
	@CacheEvict(value = "channelSearchWordService.getChannelSearchWordList", allEntries = true)
	public void updateDpChnnlSearchWord(DpChnnlSearchWord dpChnnlSearchWord) throws Exception {
		dpChnnlSearchWordDao.updateDpChnnlSearchWord(dpChnnlSearchWord);
	}

	/**
	 * @Desc : 채널 검색어 삭제
	 * @Method Name : deleteDpChnnlSearchWord
	 * @Date : 2019. 12. 23.
	 * @Author : 이강수
	 * @param DpChnnlSearchWord
	 */
	@CacheEvict(value = "channelSearchWordService.getChannelSearchWordList", allEntries = true)
	public void deleteDpChnnlSearchWord(DpChnnlSearchWord dpChnnlSearchWord) throws Exception {
		dpChnnlSearchWordDao.deleteDpChnnlSearchWord(dpChnnlSearchWord);
	}

	/**
	 * @Desc : 채널 검색어 중복 체크
	 * @Method Name : validateChannelSearchWord
	 * @Date : 2019. 12. 19.
	 * @Author : 이강수
	 * @param DpChnnlSearchWord[]
	 * @return
	 */
	public String validateChannelSearchWord(DpChnnlSearchWord[] dpChnnlSearchWords) throws Exception {

		String result = Const.BOOLEAN_TRUE;

		for (DpChnnlSearchWord dpChnnlSearchWord : dpChnnlSearchWords) {
			if (dpChnnlSearchWord.getDelYn() != 1 && (UtilsText.equals(BaseConst.CRUD_I, dpChnnlSearchWord.getStatus())
					|| UtilsText.equals(BaseConst.CRUD_U, dpChnnlSearchWord.getStatus()))) {
				int validateInteger = dpChnnlSearchWordDao.selectDpChnnlSearchWordForValidate(dpChnnlSearchWord);
				if (validateInteger > 0) {
					result = Const.BOOLEAN_FALSE;
					return result;
				}
			}
		}

		return result;
	}
}
