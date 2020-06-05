package kr.co.abcmart.bo.system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.model.master.SySiteDeliveryType;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class SiteServiceTest {

	@Autowired
	SiteService service;

	@Test
	void testGetSite() throws Exception {
		String siteNo = "10000";
		SySite site = service.getSite(siteNo);
		log.debug("site : {}", site);
	}

	@Test
	void testGetSiteList() throws Exception {
		List<SySite> list = service.getSiteList();
		for (SySite sySite : list) {
			log.debug("siteNo : {}, siteName : {}", sySite.getSiteNo(), sySite.getSiteName());
		}

	}

	@Test
	void testGetUseChannelList() throws Exception {
		List<SySiteChnnl> list = service.getUseChannelList();
		for (SySiteChnnl SySiteChnnl : list) {
			log.debug("siteNo : {}, chnnlNo : {}, chnnlName : {}", SySiteChnnl.getSiteNo(), SySiteChnnl.getChnnlNo(),
					SySiteChnnl.getChnnlName());
		}

	}

	@Test
	void testGetSiteListByCombo() throws Exception {
		Pair<JSONObject, List<SySite>> pair = service.getSiteListByCombo();
		log.debug("site combo : {}", pair.getFirst());
		for (SySite sySite : pair.getSecond()) {
			log.debug("siteNo : {}, siteName : {}", sySite.getSiteNo(), sySite.getSiteName());
		}

	}

	@Test
	void testGetUseChannelListBySiteNo() throws Exception {
		List<SySiteChnnl> list = service.getUseChannelListBySiteNo("10000");

		for (SySiteChnnl sySiteChnnl : list) {
			log.debug("getChnnlNo : {}, getChnnlName : {}", sySiteChnnl.getChnnlNo(), sySiteChnnl.getChnnlName());
		}

	}

	@Test
	void testGetChannelListBySiteNo() throws Exception {
		List<SySiteChnnl> list = service.getChannelListBySiteNo("10000", false);

		for (SySiteChnnl sySiteChnnl : list) {
			log.debug("getChnnlNo : {}, getChnnlName : {}", sySiteChnnl.getChnnlNo(), sySiteChnnl.getChnnlName());
		}

	}

	@Test
	void testGetDeliveryType() throws Exception {
		List<SySiteDeliveryType> list = service.getDeliveryType("10000", true);

		for (SySiteDeliveryType sySiteDeliveryType : list) {
			log.debug("getSiteNo : {}, getDlvyTypeCode : {}, getUseYn : {}", sySiteDeliveryType.getSiteNo(),
					sySiteDeliveryType.getDlvyTypeCode(), sySiteDeliveryType.getUseYn());
		}

	}

	@Test
	void testGetVendorUseChannelList() throws Exception {
		List<SySiteChnnl> list = service.getVendorUseChannelList();
		for (SySiteChnnl SySiteChnnl : list) {
			log.debug("siteNo : {}, chnnlNo : {}, chnnlName : {}", SySiteChnnl.getSiteNo(), SySiteChnnl.getChnnlNo(),
					SySiteChnnl.getChnnlName());
		}
	}

	@Test
	void testGetVendorNo() throws Exception {
		String channelNo = "10001";
		String actual = service.getVendorNo(channelNo);
		log.debug("testGetVendorNo.actual : {}", actual);
		assertEquals("VD10000001", actual);
	}

}
