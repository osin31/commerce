package kr.co.abcmart.bo.display.model.master;

import kr.co.abcmart.bo.display.model.master.base.BaseCmCatalogEventStore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CmCatalogEventStore extends BaseCmCatalogEventStore {

	private String storeName;

}
