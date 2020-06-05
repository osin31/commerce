package kr.co.abcmart.common.paging;

import java.util.ArrayList;
import java.util.List;

import kr.co.abcmart.common.bean.Bean;
import kr.co.abcmart.common.constant.BaseConst;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Pageable<V, M> {

	/***
	 * 조회된 데이터 목록 - 부분 범위 조회
	 */
	private List<M> content = new ArrayList<>();
	
	/***
	 * 부분 범위로 조회된 데이터 출력 갯수
	 */
	private int rowsPerPage = 10;
	
	/***
	 * 조회할 페이지 번호. 가공 되지 않는 parameter pageNum 값.
	 */
	private int orgPageNum = 1;
	
	/***
	 * 조회할 페이지 번호. 1보다 작은 페이지 번호가 들어오면 1로 초기화 된다.
	 */
	private int pageNum = 1;
	
	/***
	 * 전체 데이터 갯수 
	 */
	private int totalCount;
	
	/***
	 * 정렬 ibsheet 일 경우 column^accending 형식으로 넘오온다. upMenuNo^ASC
	 */
	private String sortColumn;
	
	/***
	 * 정렬 ibsheet 일 경우 column^accending 형식으로 넘오온다. upMenuNo^ASC
	 */
	private String sortType;
	
	/***
	 * 조회된 데이터 목록 - 부분 범위 조회
	 */
	private V bean;
	
	
	private boolean usePage = true;
	
	public boolean isUsePage() {
		return usePage;
	}

	public void setUsePage(boolean usePage) {
		this.usePage = usePage;
	}

	public Pageable(Parameter<V> parameter) {
		initialize(parameter, null, null);
	}
	
	public Pageable(Parameter<?> parameter, Bean bean) {
		initialize(parameter, null, bean);
	}
	
	public Pageable(Parameter<?> parameter, Class<?> clazz) {
		initialize(parameter, clazz, null);
	}
	
	private void initialize(Parameter<?> parameter, Class<?> clazz, Bean bean) {
		
		if(parameter != null) {
			
			if(!UtilsText.isBlank(parameter.getString(BaseConst.PAGING_PARAM_PAGE_NUM))) {
				this.orgPageNum = parameter.getInt(BaseConst.PAGING_PARAM_PAGE_NUM);
				this.pageNum = parameter.getInt(BaseConst.PAGING_PARAM_PAGE_NUM);
			}else if(!UtilsText.isBlank(parameter.getString(BaseConst.IBSHEET_PAGING_PARAM_IBPAGE))) {
				this.orgPageNum = parameter.getInt(BaseConst.IBSHEET_PAGING_PARAM_IBPAGE);
				this.pageNum = parameter.getInt(BaseConst.IBSHEET_PAGING_PARAM_IBPAGE);
			}
			
			if(!UtilsText.isBlank(parameter.getString(BaseConst.PAGING_PARAM_ROWSPERPAGE))) {
				this.rowsPerPage = parameter.getInt(BaseConst.PAGING_PARAM_ROWSPERPAGE);
			}else if(!UtilsText.isBlank(parameter.getString(BaseConst.IBSHEET_PAGING_PARAM_ONEPAGEROW))) {
				this.rowsPerPage = parameter.getInt(BaseConst.IBSHEET_PAGING_PARAM_ONEPAGEROW);
			}
			
			if(!UtilsText.isBlank(parameter.getString(BaseConst.PAGING_PARAM_SORT))) {
				setSort(parameter.getString(BaseConst.PAGING_PARAM_SORT));
			}else if(!UtilsText.isBlank(parameter.getString(BaseConst.IBSHEET_PAGING_PARAM_IBORDERBY))) {
				setSort(parameter.getString(BaseConst.IBSHEET_PAGING_PARAM_IBORDERBY));
			}
			
			if(clazz != null) {
				try {
					setBean(parameter.create(clazz));
				} catch (Exception e) {
					e.printStackTrace();
				}				
			} else if(bean != null) {
				setBean(extracted(bean));
			} else {
				setBean((V)parameter.get());
			}
		}		
	}

	@SuppressWarnings("unchecked")
	private V extracted(Bean bean) {
		return (V)bean;
	}
	
	private void setSort(String sort) {
		
		if(!UtilsText.isBlank(sort)) {
			
			String[] sortInfo = sort.split("\\^");
			this.sortColumn = sortInfo[0];
			this.sortType = sortInfo[1];
			
		}
	}
	
	
	public void setContent(List<M> content) {
		this.content = content;
	}

	public List<M> getContent() {
		return (List<M>) content;
	}
	
	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public int getOrgPageNum() {
		return orgPageNum;
	}

	public int getPageNum() {
		if(pageNum < 1) {
			return 1;
		}
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public String getSortType() {
		return sortType;
	}

	public V getBean() {
		return (V) bean;
	}

	public void setBean(V bean) {
		this.bean = (V) bean;
	}
	
	public Page<M> getPage() {
		
		Page<M> page = new Page<>();
		
		page.setOrgPageNum(getOrgPageNum());
		page.setPageNum(getPageNum());
		page.setRowsPerPage(getRowsPerPage());
		page.setTotalCount(getTotalCount());
		page.setContent(getContent());
		
		return page;
	}

	public static void main(String[] args) {
		
		log.debug("{}","a^b".split("\\^")[0]);
	}
}