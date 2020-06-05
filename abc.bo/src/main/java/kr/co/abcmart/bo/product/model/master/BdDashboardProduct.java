package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.product.model.master.base.BaseBdDashboardProduct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BdDashboardProduct extends BaseBdDashboardProduct {

   /** 탭 구분 (전체/자사/입점) */
   private String tabCode;

   /** 서브탭 구분 */
   private String siteName;

   /** 판매/전시중 건수 */
   private java.lang.Integer deps1 = 0;

   /** 일시품절 건수 */
   private java.lang.Integer deps2 = 0;

   /** 승인요청 건수 */
   private java.lang.Integer deps3 = 0;

   /** 승인반려 건수 */
   private java.lang.Integer deps4 = 0;
   
   /** 입점업체번호(PO용) */
   private String vndrNo;
   
}