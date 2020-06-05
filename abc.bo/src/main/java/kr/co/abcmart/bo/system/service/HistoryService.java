package kr.co.abcmart.bo.system.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.system.model.master.SyMenu;
import kr.co.abcmart.bo.system.model.master.SyMenuAccessHistory;
import kr.co.abcmart.bo.system.repository.master.SyMenuAccessHistoryDao;
import kr.co.abcmart.bo.system.repository.master.SyMenuDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HistoryService{
	@Autowired
	private SyMenuAccessHistoryDao syMenuAccessHistoryDao;

	@Autowired
	private SyMenuDao syMenuDao;

	/**
	 *
	 * @Desc      	: 관리자 URL 접근 이력 리스트 데이터를 조회한다.
	 * @Method Name : getUrlAccessHistoryList
	 * @Date  	  	: 2019. 8. 5.
	 * @Author    	: 최경호
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SyMenuAccessHistory> getUrlAccessHistoryList(
			Pageable<SyMenuAccessHistory, SyMenuAccessHistory> pageable) throws Exception {

		int count = syMenuAccessHistoryDao.selectUrlAccessHistoryListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(syMenuAccessHistoryDao.selectUrlAccessHistoryList(pageable));
		}

		return pageable.getPage();
	}

	public List<SyMenu> getMenuData(SyMenu syMenu) throws Exception {
		List<SyMenu> data = new ArrayList<SyMenu>();
		String result = String.valueOf(this.getLeafMenuCount(syMenu));

		// 상세화면에서만 사용
		// 메뉴 선택 select box를 생성할 때 하위의 메뉴 개수에 따라서 select box 생성 여부의 결정을 위해 세팅하는 변수
		// 목록에서는 최하위 뎁스의 상위 뎁스까지만 조회가 가능하지만 상세의 select box는 최하위 메뉴까지 select box를 만들어 줘야 하는데
		// 그 때 하위의 셀렉트 박스의 개수를 세팅해서 쿼리의 where 조건을 변경해줘서 최하위 메뉴까지 조회하도록 하는 기준 변수
		if("0".equals(result)) {
			syMenu.setLevel("3");
		}

		data = syMenuAccessHistoryDao.selectMenu(syMenu);

		for(SyMenu symenu : data) {
			symenu.setLevel(result);
		}

		return data;
	}

	/**
	 *
	 * @Desc      	: 관리자 MENU 접근 이력 리스트 데이터를 조회한다.
	 * @Method Name : getMenuAccessHistoryList
	 * @Date  	  	: 2019. 8. 5.
	 * @Author    	: 최경호
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SyMenuAccessHistory> getMenuAccessHistoryList(
			Pageable<SyMenuAccessHistory, SyMenuAccessHistory> pageable) throws Exception {

		int count = syMenuAccessHistoryDao.selectMenuAccessHistoryListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(syMenuAccessHistoryDao.selectMenuAccessHistoryList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 *
	 * @Desc      	: 1뎁스 메뉴의 메뉴No로 3뎁스 메뉴의 개수를 조회
	 * @Method Name : getLeafMenuCount
	 * @Date  	  	: 2019. 8. 5.
	 * @Author    	: 최경호
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int getLeafMenuCount(SyMenu syMenu) throws Exception {
		return syMenuAccessHistoryDao.selectLeafMenuCount(syMenu);

	}

	/**
	 *
	 * @Desc      	: 선택한 메뉴의 최상위부터 최하위 메뉴와 메뉴 리스트 정보를 Map으로 리턴한다.
	 * @Method Name : getMenuDataList
	 * @Date  	  	: 2019. 8. 7.
	 * @Author    	: 최경호
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMenuDataList(SyMenuAccessHistory syMenuAccessHistory) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SyMenu> menuList = syMenuAccessHistoryDao.selectMenuDataList(syMenuAccessHistory);

		List<Object> list = new ArrayList<Object>();
		int size = menuList.size();
		for(int i=0; i<menuList.size(); i++) {
			SyMenu syMenu = menuList.get(i);
			if(size == syMenu.getMenuLevel()) {
				syMenu.setLevel("3");
			}
			list.add(syMenuAccessHistoryDao.selectMenu(syMenu));
		}
		map.put("menuList", menuList);
		map.put("selectList", list);

		return map;
	}

	/**
	 *
	 * @Desc      	: URL 접근이력 상세 데이터 조회
	 * @Method Name : getMenuDataList
	 * @Date  	  	: 2019. 8. 7.
	 * @Author    	: 최경호
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SyMenuAccessHistory> getMenuAccessHistoryDetailPop(Pageable<SyMenuAccessHistory, SyMenuAccessHistory> pageable) throws Exception {
		int count = syMenuAccessHistoryDao.selectMenuAccessHistoryDetailCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(syMenuAccessHistoryDao.selectMenuAccessHistoryDetailPop(pageable));
		}

		return pageable.getPage();
	}

	/**
	 *
	 * @Desc      	: 관리자 MENU 접근 이력 리스트 데이터를 조회한다.
	 * @Method Name : getMenuAccessHistoryList
	 * @Date  	  	: 2019. 8. 5.
	 * @Author    	: 최경호
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getAdminAccessHistoryList(SyMenuAccessHistory syMenuAccessHistory) throws Exception {
//		int count = 100; //syMenuAccessHistoryDao.selectAdminAccessHistoryListCount(pageable);
//		pageable.setTotalCount(count);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String accessUrl = "";

//		if (count > 0) {
			List<Map<String, Object>> list =syMenuAccessHistoryDao.selectAdminAccessHistoryList(syMenuAccessHistory);

			nextLoop : for(int i=0; i<list.size(); i++) {
				Map<String, Object> map = list.get(i);
				if(i != 0 && !accessUrl.equals(map.get("accessUrl"))) {
					resultList.add(resultMap);
					resultMap = new HashMap<String, Object>();
				}

				for(String key : map.keySet()) {
//					if(key.startsWith("SY")) {
//						int cnt = (int)map.get(key);
//						System.out.println("key >>>" + key);
//
//						if(cnt > 0) {
//		            		resultMap.put(key+"Cnt", map.get("accessUrlCnt"));
//			            	resultMap.put(key+"ResultCnt", map.get("resultCnt"));
//						}
//						resultMap.put("accessUrl", map.get("accessUrl"));
//						accessUrl = (String)map.get("accessUrl");
//						if(i ==  list.size()-1) {
//							resultList.add(resultMap);
//							resultMap = new HashMap<String, Object>();
//						}
//
//						continue nextLoop;
//					}

					if(key.startsWith("SY")) {
						int cnt = (int)map.get(key);

						if(syMenuAccessHistory.getAdminNo() == null) {
							if(cnt > 0) {
			            		resultMap.put(key+"Cnt", map.get("accessUrlCnt"));
				            	resultMap.put(key+"ResultCnt", map.get("resultCnt"));
				            	resultMap.put("accessUrl", map.get("accessUrl"));
				            	accessUrl = (String)map.get("accessUrl");
			            		if(i ==  list.size()-1) {
			            			resultList.add(resultMap);
			            			resultMap = new HashMap<String, Object>();
			            		}

				            	continue nextLoop;
							}
						}else {
							if(cnt > 0) {
			            		resultMap.put(key+"Cnt", map.get("accessUrlCnt"));
				            	resultMap.put(key+"ResultCnt", map.get("resultCnt"));
				            	resultMap.put("accessUrl", map.get("accessUrl"));
				            	accessUrl = (String)map.get("accessUrl");
			            		if(i ==  list.size()-1) {
			            			resultList.add(resultMap);
			            			resultMap = new HashMap<String, Object>();
			            		}

				            	continue nextLoop;
			            	}else {
			            		resultMap.put("accessUrl", map.get("accessUrl"));
			            		accessUrl = (String)map.get("accessUrl");
			            		if(i ==  list.size()-1) {
			            			resultList.add(resultMap);
			            			resultMap = new HashMap<String, Object>();
			            		}
			            		continue nextLoop;
			            	}
						}
					}
				}
			}

//			for(Map data : resultList) {
//				System.out.println("Map >>>" + data);
//			}
//		}

		return resultList;
	}

	/**
	 *
	 * @Desc      	: ADMIN 접근이력 상세 데이터 조회
	 * @Method Name : getMenuDataList
	 * @Date  	  	: 2019. 8. 28.
	 * @Author    	: 최경호
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SyMenuAccessHistory> getAdminAccessHistoryDetailPop(Pageable<SyMenuAccessHistory, SyMenuAccessHistory> pageable) throws Exception {
		int count = syMenuAccessHistoryDao.selectAdminAccessHistoryDetailCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(syMenuAccessHistoryDao.selectAdminAccessHistoryDetailPop(pageable));
		}

		return pageable.getPage();
	}
}
