package kr.co.abcmart.bo.system.model.master;

import kr.co.abcmart.bo.system.model.master.base.BaseSySiteChnnlImage;
import kr.co.abcmart.common.request.FileUpload;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SySiteChnnlImage extends BaseSySiteChnnlImage {
	private FileUpload[] files;
}
