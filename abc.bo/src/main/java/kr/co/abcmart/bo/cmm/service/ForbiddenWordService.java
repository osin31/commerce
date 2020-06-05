package kr.co.abcmart.bo.cmm.service;

import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.cmm.model.master.CmForbiddenWord;
import kr.co.abcmart.bo.cmm.repository.master.CmForbiddenWordDao;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ForbiddenWordService {

	public static final String FORBIDDEN_WORD = "W";
	public static final String FORBIDDEN_PWD = "P";

	@Autowired
	CmForbiddenWordDao dao;

	/**
	 * 가장 최근 수정된 금지어를 반환한다.
	 *
	 * @param cmForbiddenWord
	 * @return
	 * @throws Exception
	 */
	public CmForbiddenWord getTopWithSyAdminByForbidWordTypeOrderbyModDtmDesc(CmForbiddenWord cmForbiddenWord)
			throws Exception {
		CmForbiddenWord server = dao.selectTopWithSyAdminByForbidWordTypeOrderbyModDtmDesc(cmForbiddenWord);

		return server;
	}

	/**
	 * 가장 최근에 수정된 금지어 정보가 있으면 해당 데이터를 update, 등록된 금지어 정보가 없으면 insert한다.
	 * 
	 * @param cmForbiddenWord
	 * @throws Exception
	 */
	public void regist(CmForbiddenWord cmForbiddenWord) throws Exception {
		CmForbiddenWord server = getTopWithSyAdminByForbidWordTypeOrderbyModDtmDesc(cmForbiddenWord);
		if (server == null) {
			insert(cmForbiddenWord);
		} else {
			cmForbiddenWord.setForbidWordSeq(server.getForbidWordSeq());
			setMod(cmForbiddenWord);
			dao.updateIdentity(cmForbiddenWord);
		}
	}

	/**
	 * 금지어 정보를 insert한다.
	 * 
	 * @param cmForbiddenWord
	 * @throws Exception
	 */
	public void insert(CmForbiddenWord cmForbiddenWord) throws Exception {
		setMod(cmForbiddenWord);
		cmForbiddenWord.setRgsterNo(LoginManager.getUserDetails().getAdminNo());

		dao.insertIdentity(cmForbiddenWord);
	}

	/**
	 * 전달받은 pwd에 관리자 비밀번호 금지어가 포함되어 있는지 체크하고 결과를 돌려준다.
	 * 
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public boolean hasForbiddenPwd(String pwd) throws Exception {
		return hasForbiddenPwdWithData(pwd).getFirst();
	}

	/**
	 * 전달받은 pwd에 관리자 비밀번호 금지어가 포함되어 있는지 체크하고 결과와 금지어를 돌려준다.
	 * 
	 * 
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public Pair<Boolean, String> hasForbiddenPwdWithData(String pwd) throws Exception {
		boolean result = false;
		String resultStr = "";
		CmForbiddenWord cmForbiddenWord = new CmForbiddenWord();
		cmForbiddenWord.setForbidWordType(FORBIDDEN_PWD);

		cmForbiddenWord = dao.selectTopByForbidWordTypeOrderbyModDtmDesc(cmForbiddenWord);
		if (cmForbiddenWord != null) {
			String forbiddenPwds = cmForbiddenWord.getForbidWordText();

			StringTokenizer st = new StringTokenizer(forbiddenPwds, ",");
			while (st.hasMoreElements()) {
				String str = (String) st.nextElement();
				str = str.trim();
				if (pwd.indexOf(str) > -1 && UtilsText.isNotBlank(str)) {
					result = true;
					resultStr = str;
					break;
				}
			}
		}

		return Pair.of(result, resultStr);
	}

	/**
	 * 현재 로그인한 사용자 정보를 수정일시, 수정자이름, 수정자고유번호에 set한다.
	 * 
	 * @param cmForbiddenWord
	 * @throws Exception
	 */
	private void setMod(CmForbiddenWord cmForbiddenWord) throws Exception {
		cmForbiddenWord.setModerNo(LoginManager.getUserDetails().getAdminNo());
	}
}
