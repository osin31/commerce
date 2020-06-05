package kr.co.abcmart.bo.cmm.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.cmm.model.master.CmPushMessage;
import kr.co.abcmart.bo.cmm.model.master.CmPushSendHistory;
import kr.co.abcmart.bo.cmm.repository.master.base.BaseCmPushMessageDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface CmPushMessageDao extends BaseCmPushMessageDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseCmPushMessageDao 클래스에 구현 되어있습니다.
	 * BaseCmPushMessageDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public CmPushMessage selectByPrimaryKey(CmPushMessage cmPushMessage) throws Exception;

	public int appPushSeq(String todayDate) throws Exception;

	public int appPushTotalCount(Pageable<CmPushMessage, CmPushMessage> cmPushMessage) throws Exception;

	public List<CmPushMessage> appPushReadList(Pageable<CmPushMessage, CmPushMessage> cmPushMessage) throws Exception;

	public int insertAppPush(CmPushMessage cmPushMessage) throws Exception;

	public CmPushMessage selectReadDetailAppPush(String pushMesgNo) throws Exception;

	public int updateAppPush(CmPushMessage cmPushMessage) throws Exception;

	public int insertTargetMember(List<CmPushMessage> targetMember) throws Exception;

	public int deleteTargetMember(CmPushMessage cmPushMessage) throws Exception;

	public List<CmPushMessage> selectPushUploadTargetList(String messageNo) throws Exception;

	public int getPushTargetListCount(CmPushMessage cmPushMessage) throws Exception;

	public int getPushTargetMemberDeviceTokenTextListCount(CmPushMessage cmPushMessage) throws Exception;

	public List<CmPushMessage> getPushTargetMemberDeviceTokenTextList(CmPushMessage cmPushMessage) throws Exception;

	public int getPushAppDownloarMemberDeviceTokenTextListCount(CmPushMessage cmPushMessage) throws Exception;

	public List<CmPushMessage> getPushAppDownloarMemberDeviceTokenTextList(CmPushMessage cmPushMessage)
			throws Exception;

	public void insertPushSendHistoryNoTrx(CmPushSendHistory cmPushSendHistory) throws Exception;

	public int updateAppPushManage(CmPushMessage cmPushMessage) throws Exception;

	public List<CmPushSendHistory> getPushSendCountFromHistory(String pushMesgNo) throws Exception;

	public int updateCanselAppPush(CmPushMessage cmPushMessage) throws Exception;
}
