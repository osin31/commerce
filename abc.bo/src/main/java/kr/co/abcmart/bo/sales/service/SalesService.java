package kr.co.abcmart.bo.sales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.sales.model.master.IfMaechul;
import kr.co.abcmart.bo.sales.repository.master.IfMaechulDao;
import kr.co.abcmart.bo.sales.vo.IfMeaechulExcelVo;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SalesService {

	@Autowired
	private IfMaechulDao ifMaechulDao;

	public Page<IfMaechul> getIfMaechulList(Pageable<IfMaechul, IfMaechul> pageable) throws Exception {

		int count = ifMaechulDao.selectIfMaechulListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			List<IfMaechul> ifMaechulList = ifMaechulDao.selectIfMaechulList(pageable);

			pageable.setContent(ifMaechulList);
		}

		return pageable.getPage();
	}

	public List<IfMeaechulExcelVo> getIfMaechulForAllExcelList(IfMaechul ifMaechul) throws Exception {
		return ifMaechulDao.selectIfMaechulAllExcelList(ifMaechul);
	}

	public List<IfMaechul> getIfMaechulExcelList(IfMaechul ifMaechul) throws Exception {
		return ifMaechulDao.selectIfMaechulExcelList(ifMaechul);
	}

	public IfMaechul getIfMaechulSelector(IfMaechul ifMaechul) throws Exception {
		return ifMaechulDao.getIfMaechulSelector(ifMaechul);
	}
}
