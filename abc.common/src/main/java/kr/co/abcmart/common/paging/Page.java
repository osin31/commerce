package kr.co.abcmart.common.paging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Page<U> implements Serializable {

	/***
	 * 조회된 데이터 목록 - 부분 범위 조회
	 */
	private List<U> content = new ArrayList<>();

	/***
	 * 페이지 block
	 */
	private int pageBlock = 10;

	/***
	 * 부분 범위로 조회된 데이터 출력 갯수
	 */
	private int rowsPerPage = 10;

	/***
	 * 조회할 페이지 번호. 가공 되지 않는 parameter pageNum 값.
	 */
	private int orgPageNum = 1;

	/***
	 * 조회할 페이지 번호. 1보다 작은 페이지 번호가 들어오면 1로 초기화 되고 전체 페이지수보다 크면 마지막 페이지 설정.
	 */
	private int pageNum = 1;

	/***
	 * 전체 데이터 갯수
	 */
	private int totalCount;

	/**
	 * 조회된 데이터가 있는지 없는지 확인.
	 * 
	 * @return boolean
	 */
	public boolean hasContent() {
		return !content.isEmpty();
	}

	/***
	 * 조회된 데이터 목록 주입.
	 * 
	 * @param content
	 */
	public void setContent(List<U> content) {
		this.content = content;
	}

	/***
	 * 조회된 데이터 목록 반환.
	 * 
	 * @param <U>
	 * @return List
	 */
	public List<U> getContent() {
		return content;
	}

	public int getOrgPageNum() {
		return orgPageNum;
	}

	public void setOrgPageNum(int orgPageNum) {
		this.orgPageNum = orgPageNum;
	}

	/***
	 * 조회할 페이지 번호 반환.
	 * 
	 * @return List
	 */
	public int getPageNum() {

		if (pageNum > getTotalPages()) {
			return getTotalPages();
		} else if (pageNum < 1) {
			return 1;
		}

		return pageNum;
	}

	/***
	 * 조회할 페이지 번호 등록.
	 * 
	 * @return List
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/***
	 * 전체 데이터 갯수 반환
	 * 
	 * @return int
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/***
	 * 전체 데이터 갯수 지정
	 * 
	 * @param totalCount 전체 데이터 갯수.
	 * @return int
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/***
	 * 목록 출력 갯수 반환.
	 * 
	 * @return
	 */
	public int getRowsPerPage() {
		return rowsPerPage;
	}

	/***
	 * 목록 출력 갯수 설정.
	 * 
	 * rowsPerPage = 3
	 * 
	 * 번호 제목 글쓴이 등록일 조회수 ------------------------------------------------------ 3
	 * 안녕하세요. 장진철 2018-12-12 0
	 * ------------------------------------------------------ 2 안녕하세요. 장진철
	 * 2018-12-12 0 ------------------------------------------------------ 1 안녕하세요.
	 * 장진철 2018-12-12 0 ------------------------------------------------------
	 * 
	 * @param rowsPerPage 목록 출력 갯수.
	 * @return
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	/**
	 * 전체 페이지 갯수. ex) Math.ceil(totalCount/rowsPerpage) = totalPages
	 * Math.ceil(51/10) = 6, 5.1 값을 올림 하면 전체 페이지는 6페이지가 된다.
	 * 
	 * @return
	 */
	public int getTotalPages() {
		return getTotalCount() == 0 ? 1 : (int) Math.ceil((double) getTotalCount() / (double) getRowsPerPage());
	}

	/***
	 * 이전 페이지 이동 가능 한지 확인. 첫페이지일 경우 이전으로 이동할 수 없으므로 false,아닐 경우 true
	 * 
	 * @return
	 */
	public boolean hasPrevious() {
		return getPageNum() > 1;
	}

	/**
	 * 첫 페이지 이면 true, 아니면 false
	 * 
	 * @return boolean
	 */
	public boolean isFirst() {
		return !hasPrevious();
	}

	/***
	 * 다음 페이지 이동 가능 여부. 마지막 페이지일 경우 다음 페이지로 이동할 수 없으으로 false 아닐 경우 true
	 * 
	 * @return boolean
	 */
	public boolean hasNext() {
		return getPageNum() < getTotalPages();
	}

	/**
	 * 마지막 페이지 이면 true, 아니면 false
	 * 
	 * @return boolean
	 */
	public boolean isLast() {
		return !hasNext();
	}

	/***
	 * 페이지 목록.
	 * 
	 * @return
	 */
	public List<Integer> getPages() {

		// 현재 페이지에 해당 하는 block 을 구한다.
		int currPageBlock = (int) ((getPageNum() - 1) / pageBlock) + 1;

		// 시작 및 종료 페이지 구한다.
		int beginPageNum = (currPageBlock - 1) * pageBlock + 1;
		int endPageNum = beginPageNum + pageBlock - 1;

		if (endPageNum > getTotalPages()) {
			endPageNum = getTotalPages();
		}

		List<Integer> pages = new ArrayList<>();

		for (int i = beginPageNum; i <= endPageNum; i++) {
			pages.add(i);
		}

		return pages;
	}

	public Map<String, Object> getGrid() {

		Map<String, Object> gridPage = new HashMap<>();

//		gridPage.put("ibpage", getPageNum());
//		gridPage.put("onepagerow", getRowsPerPage());
		gridPage.put("Data", getContent());
		gridPage.put("Total", getTotalCount());

		return gridPage;
	}

}