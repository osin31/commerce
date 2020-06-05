package kr.co.abcmart.bo.display.model.master;

import kr.co.abcmart.bo.display.model.master.base.BaseBdPopupDevice;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BdPopupDevice extends BaseBdPopupDevice {

	private String pcCnnctnType;

	private String moCnnctnType;

	private String pcLinkInfo;

	private String moLinkInfo;

	private String pcImageName;

	private String moImageName;

	private String pcPopupType;

	private java.lang.Short pcPopupXPostn;

	private java.lang.Short pcPopupYPostn;

	private java.lang.Short pcPopupWidthNum;

	private java.lang.Short pcPopupHeightNum;

	private java.lang.Short moPopupWidthNum;

	private java.lang.Short moPopupHeightNum;

	private String pcTargetType;

	private String moTargetType;

	private String pcArtrnText;

	private String moArtrnText;

}
