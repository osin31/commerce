<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp" %>
<%@include file="/WEB-INF/views/common/menu.jsp" %>
<script type="text/javascript">
	$(function() {
		abc.mainPopup([${fn:join(adminNoticeMainPop, ",")}]);
	});
</script>
<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<div class="dashboard-box">
					<div class="flex-box">
						<div class="card-box has-tab">
							<div class="card-header">
								<div class="fl">
									<span class="card-title">상품현황</span>
									<span class="date-range">
										<span id="productSearchStartDtm"><fmt:formatDate value="${oneMonthAgoDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}"/></span>
										 	~ 
									 	<span id="productSearchEndDtm"><fmt:formatDate value="${nowDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}" /></span>
									</span>
								</div>
								<div class="fr">
									<button type="button"  id = "productrefreshbtn" class="btn-sm btn-func"><i class="ico ico-refresh"></i>새로고침</button>
								</div>
							</div>
							<div class="card-body">
								<div class="tab-wrap">
									<ul class="tabs">
										<li class="tab-item"><a href="#tabContent1" class="tab-link">전체</a></li>
										<li class="tab-item"><a href="#tabContent2" class="tab-link">자사</a></li>
										<li class="tab-item"><a href="#tabContent3" class="tab-link">입점</a></li>
									</ul>
									<!-- S:tab_content -->
									<div id="tabContent1" class="tab-content">
										<div class="flex-box">
											<ul class="channel-list">
												<li>
													<span class="item">
														<input type="radio" id="rdoChannel1" name="rgChannelAll"value="all" checked><label for="rdoChannel1">전체</label>
													</span>
												</li>
												<li>
													<span class="item">
														<input type="radio" id="rdoChannel2" name="rgChannelAll" value="a-rt"><label for="rdoChannel2">A-RT</label>
													</span>
												</li>
												<li>
													<span class="item">
														<input type="radio" id="rdoChannel3" name="rgChannelAll" value="abc"><label for="rdoChannel3">ABC</label>
													</span>
												</li>
												<li>
													<span class="item">
														<input type="radio" id="rdoChannel4" name="rgChannelAll" value="gs"><label for="rdoChannel4">GS</label>
													</span>
												</li>
												<li>
													<span class="item">
														<input type="radio" id="rdoChannel5" name="rgChannelAll" value="ots"><label for="rdoChannel5">OTS</label>
													</span>
												</li>
											</ul>
											<ul class="status-list">
												<li class="status-item">
													<span class="item-label">판매/전시중</span>
													<span class="item-value"><a href="#" id="all01" class="cnt product-status" name="onSale">0</a>건</span>
												</li>
												<li class="status-item">
													<span class="item-label">일시품절</span>
													<span class="item-value"><a href="#" id="all02" class="cnt product-status" name="outOfStock">0</a>건</span>
												</li>
												<li class="status-item aprv-page">
													<span class="item-label">승인요청</span>
													<span class="item-value"><a href="#" id="all03" class="cnt product-status" name="request">0</a>건</span>
												</li>
												<li class="status-item aprv-page">
													<span class="item-label">승인반려</span>
													<span class="item-value"><a href="#" id="all04" class="cnt product-status" name="refuse">0</a>건</span>
												</li>
											</ul>
										</div>
									</div>
									<!-- E:tab_content -->
									<!-- S:tab_content -->
									<div id="tabContent2" class="tab-content">
										<div class="flex-box">
											<ul class="channel-list">
												<li>
													<span class="item">
														<input type="radio" id="rdoChannel11" name="rgChannelMy" value="all" checked><label for="rdoChannel11">전체</label>
													</span>
												</li>
												<li>
													<span class="item">
														<input type="radio" id="rdoChannel12" name="rgChannelMy"value="a-rt"><label for="rdoChannel12">A-RT</label>
													</span>
												</li>
												<li>
													<span class="item">
														<input type="radio" id="rdoChannel13" name="rgChannelMy"value="abc"><label for="rdoChannel13">ABC</label>
													</span>
												</li>
												<li>
													<span class="item">
														<input type="radio" id="rdoChannel14" name="rgChannelMy"value="gs"><label for="rdoChannel14">GS</label>
													</span>
												</li>
												<li>
													<span class="item">
														<input type="radio" id="rdoChannel15" name="rgChannelMy"value="ots"><label for="rdoChannel15">OTS</label>
													</span>
												</li>
											</ul>
											<ul class="status-list">
												<li class="status-item">
													<span class="item-label">판매/전시중</span>
													<span class="item-value"><a href="#" id="my01" class="cnt product-status" name="onSale">0</a>건</span>
												</li>
												<li class="status-item">
													<span class="item-label">일시품절</span>
													<span class="item-value"><a href="#" id="my02" class="cnt product-status" name="outOfStock">9,999</a>건</span>
												</li>
											</ul>
										</div>
									</div>
				
									<!-- E:tab_content -->
									<!-- S:tab_content -->
									<div id="tabContent3" class="tab-content">
										<div class="flex-box">
											<ul class="channel-list">
												<li>
													<span class="item">
														<input type="radio" id="rdoChannel21" name="rgChannelCompany" value="all" checked><label for="rdoChannel21">전체</label>
													</span>
												</li>
												<li>
													<span class="item">
														<input type="radio" id="rdoChannel22" name="rgChannelCompany" value="a-rt"><label for="rdoChannel22">A-RT</label>
													</span>
												</li>
												<li>
													<span class="item">
														<input type="radio" id="rdoChannel25" name="rgChannelCompany" value="ots"><label for="rdoChannel25">OTS</label>
													</span>
												</li>
											</ul>
											<ul class="status-list">
												<li class="status-item">
													<span class="item-label">판매/전시중</span>
													<span class="item-value"><a href="#" id="cpy01" class="cnt product-status" name="onSale">0</a>건</span>
												</li>
												<li class="status-item">
													<span class="item-label">일시품절</span>
													<span class="item-value"><a href="#" id="cpy02" class="cnt product-status" name="outOfStock">2,999</a>건</span>
												</li>
												<li class="status-item">
													<span class="item-label">승인요청</span>
													<span class="item-value"><a href="#" id="cpy03" class="cnt product-status" name="request">0</a>건</span>
												</li>
												<li class="status-item">
													<span class="item-label">승인반려</span>
													<span class="item-value"><a href="#" id="cpy04" class="cnt product-status" name="refuse">0</a>건</span>
												</li>
											</ul>
										</div>
									</div>
									<!-- E:tab_content -->
								<script>
			                     $(function () {
			                        var arr = [{
			                              'all' : {
			                                 'val1' : 0, 
			                                 'val2' : 1,
			                                 'val3' : 0,
			                                 'val4' : 0,
			                              },
			                              'a-rt' : {
			                                 'val1' : 0,
			                                 'val2' : 1,
			                                 'val3' : 1,
			                                 'val4' : 1,
			                              },
			                              'abc' : {
			                                 'val1' : 2,
			                                 'val2' : 2,
			                                 'val3' : 2,
			                                 'val4' : 2,
			                              },
			                              'gs' : {
			                                 'val1' : 3,
			                                 'val2' : 3,
			                                 'val3' : 3,
			                                 'val4' : 3,
			                              },
			                              'ots' : {
			                                 'val1' : 4,
			                                 'val2' : 4,
			                                 'val3' : 4,
			                                 'val4' : 4,
			                              },
			                           }, {
			                              'all' : {
			                                 'val1' : 0, 
			                                 'val2' : 2,
			                                 'val3' : 0,
			                                 'val4' : 0,
			                              },
			                              'a-rt' : {
			                                 'val1' : 0,
			                                 'val2' : 1,
			                                 'val3' : 1,
			                                 'val4' : 1,
			                              },
			                              'abc' : {
			                                 'val1' : 2,
			                                 'val2' : 2,
			                                 'val3' : 2,
			                                 'val4' : 2,
			                              },
			                              'gs' : {
			                                 'val1' : 3,
			                                 'val2' : 3,
			                                 'val3' : 3,
			                                 'val4' : 3,
			                              },
			                              'ots' : {
			                                 'val1' : 4,
			                                 'val2' : 4,
			                                 'val3' : 4,
			                                 'val4' : 4,
			                              },
			                           }, {
			                              'all' : {
			                                 'val1' : 0, 
			                                 'val2' : 3,
			                                 'val3' : 0,
			                                 'val4' : 0,
			                              },
			                              'a-rt' : {
			                                 'val1' : 0,
			                                 'val2' : 1,
			                                 'val3' : 1,
			                                 'val4' : 1,
			                              },
			                              'ots' : {
			                                 'val1' : 4,
			                                 'val2' : 4,
			                                 'val3' : 4,
			                                 'val4' : 4,
			                              },
			                           }];
			                        <c:choose>
			                           <c:when test="${!empty productStateList }">
			                              <c:forEach var="prdtStateAll" items="${productStateList.all}" varStatus="status">
			                                 arr[0]['${prdtStateAll.siteName}']['val1'] = ${prdtStateAll.deps1};
			                                 arr[0]['${prdtStateAll.siteName}']['val2'] = ${prdtStateAll.deps2};
			                                 arr[0]['${prdtStateAll.siteName}']['val3'] = ${prdtStateAll.deps3};
			                                 arr[0]['${prdtStateAll.siteName}']['val4'] = ${prdtStateAll.deps4};
			                              </c:forEach>
			  
			                              <c:forEach var="prdtStateMy" items="${productStateList.my}" varStatus="status">
			                                 arr[1]['${prdtStateMy.siteName}']['val1'] = ${prdtStateMy.deps1};
			                                 arr[1]['${prdtStateMy.siteName}']['val2'] = ${prdtStateMy.deps2};
			                                 arr[1]['${prdtStateMy.siteName}']['val3'] = ${prdtStateMy.deps3};
			                                 arr[1]['${prdtStateMy.siteName}']['val4'] = ${prdtStateMy.deps4};
			                              </c:forEach>
			
			                              <c:forEach var="prdtStateCompany" items="${productStateList.company}" varStatus="status">
			                                 arr[2]['${prdtStateCompany.siteName}']['val1'] = ${prdtStateCompany.deps1};
			                                 arr[2]['${prdtStateCompany.siteName}']['val2'] = ${prdtStateCompany.deps2};
			                                 arr[2]['${prdtStateCompany.siteName}']['val3'] = ${prdtStateCompany.deps3};
			                                 arr[2]['${prdtStateCompany.siteName}']['val4'] = ${prdtStateCompany.deps4};
			                              </c:forEach>
			                           </c:when>
			                        </c:choose>
			                        
			                        $('#all01').text(arr[0]['all']['val1']);
			                        $('#all02').text(arr[0]['all']['val2']);
			                        $('#all03').text(arr[0]['all']['val3']);
			                        $('#all04').text(arr[0]['all']['val4']);
			                        
			                        $('#my01').text(arr[1]['all']['val1']);
			                        $('#my02').text(arr[1]['all']['val2']);
			                        $('#my03').text(arr[1]['all']['val3']);
			                        $('#my04').text(arr[1]['all']['val4']);
			                        
			                        $('#cpy01').text(arr[2]['all']['val1']);
			                        $('#cpy02').text(arr[2]['all']['val2']);
			                        $('#cpy03').text(arr[2]['all']['val3']);
			                        $('#cpy04').text(arr[2]['all']['val4']);
			                        
			                        
			                        $('input[name=rgChannelAll]').on('change', function(event) {
			                           var data;
			                           data = arr[0][$(this).val()];
			                           $('#all01').text(data.val1);
			                           $('#all02').text(data.val2);
			                           $('#all03').text(data.val3);
			                           $('#all04').text(data.val4);
			                           // switch($(this).val()) {
			                           //    case "all":
			                           // }
			                           
			                           if ($(this).val() == "abc" || $(this).val() == "gs") {
			                        	   $('.aprv-page').hide();
			                           } else {
			                        	   $('.aprv-page').show();
			                           }
			                        });
			                        
			                        $('input[name=rgChannelMy]').on('change', function(event) {
			                           var data;
			                           data = arr[1][$(this).val()];
			                           $('#my01').text(data.val1);
			                           $('#my02').text(data.val2);
			                           $('#my03').text(data.val3);
			                           $('#my04').text(data.val4);
			                           // switch($(this).val()) {
			                           //    case "all":
			                           // }
			                        });
			                        
			                        $('input[name=rgChannelCompany]').on('change', function(event) {
			                           var data;
			                           data = arr[2][$(this).val()];
			                           $('#cpy01').text(data.val1);
			                           $('#cpy02').text(data.val2);
			                           $('#cpy03').text(data.val3);
			                           $('#cpy04').text(data.val4);
			                           // switch($(this).val()) {
			                           //    case "all":
			                           // }
			                        });
			                        
				                    // 상품 현황 새로고침 클릭시
			                    	$('#productrefreshbtn').off().on('click', function() {
			                    		abc.biz.main.bodashboard.refreshAjax("productRefresh", function(data) {
			                    			
			                    			if (data.productList != null) {
			                    							                    				
				                              $.each(data.productList.all, function(i,v) {
				                            	  arr[0][v.siteName]['val1'] = v.deps1;
				                            	  arr[0][v.siteName]['val2'] = v.deps2;
				                            	  arr[0][v.siteName]['val3'] = v.deps3;
				                            	  arr[0][v.siteName]['val4'] = v.deps4;
				                              });
				                              
				                              $.each(data.productList.my, function(i,v) {
				                            	  arr[1][v.siteName]['val1'] = v.deps1;
					                              arr[1][v.siteName]['val2'] = v.deps2;
					                              arr[1][v.siteName]['val3'] = v.deps3;
					                              arr[1][v.siteName]['val4'] = v.deps4;
				                              });
				                              
				                              $.each(data.productList.company, function(i,v) {
				                            	  arr[2][v.siteName]['val1'] = v.deps1;
					                              arr[2][v.siteName]['val2'] = v.deps2;
					                              arr[2][v.siteName]['val3'] = v.deps3;
					                              arr[2][v.siteName]['val4'] = v.deps4;
				                              });
				                              
				                              $('#all01').text(arr[0]['all']['val1']);
						                      $('#all02').text(arr[0]['all']['val2']);
						                      $('#all03').text(arr[0]['all']['val3']);
						                      $('#all04').text(arr[0]['all']['val4']);
						                        
						                      $('#my01').text(arr[1]['all']['val1']);
						                      $('#my02').text(arr[1]['all']['val2']);
						                      $('#my03').text(arr[1]['all']['val3']);
						                      $('#my04').text(arr[1]['all']['val4']);
						                        
						                      $('#cpy01').text(arr[2]['all']['val1']);
						                      $('#cpy02').text(arr[2]['all']['val2']);
						                      $('#cpy03').text(arr[2]['all']['val3']);
						                      $('#cpy04').text(arr[2]['all']['val4']);
			                    			}
			                    		});
			                    	});
			                     });
			                     </script>
								</div>
							</div>
						</div>
						<div class="card-box has-tab">
							<div class="card-header">
								<div class="fl">
									<span class="card-title">주문/배송 현황</span>
									<span class="date-range">
										<span><fmt:formatDate value="${oneMonthAgoDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}"/></span>
										 	~ 
									 	<span><fmt:formatDate value="${nowDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}" /></span>
									</span>
								</div>
								<div class="fr">
									<button type="button" class="btn-sm btn-func" id="btnOrderRefresh"><i class="ico ico-refresh"></i>새로고침</button>
								</div>
							</div>
							<div class="card-body">
								<div class="tab-wrap">
									<ul class="tabs">
										<li class="tab-item"><a href="#tabContent11" class="tab-link">전체</a></li>
										<li class="tab-item"><a href="#tabContent12" class="tab-link">자사</a></li>
										<li class="tab-item"><a href="#tabContent13" class="tab-link">입점</a></li>
									</ul>
									<!-- S:tab_content -->
									<div id="tabContent11" class="tab-content">
										<ul class="status-list">
											<li class="status-item">
												<span class="item-label">결제완료</span>
												<span class="item-value"><a href="#" class="cnt" name="paymentCompleteAll"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"><c:out value="${orderDlvyCountInfo.COMPLETE_CNT + orderDlvyCountInfo.VNDR_COMPLETE_CNT}" default="0"/></fmt:formatNumber></a>건</span>
											</li>
											<li class="status-item">
												<span class="item-label">상품준비중</span>
												<span class="item-value"><a href="#" class="cnt" name="productIngAll"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"><c:out value="${orderDlvyCountInfo.PRODUCT_PREPARATION_CNT + orderDlvyCountInfo.VNDR_PRODUCT_PREPARATION_CNT}" default="0"/></fmt:formatNumber></a>건</span>
											</li>
											<li class="status-item">
												<span class="item-label">배송중</span>
												<span class="item-value"><a href="#" class="cnt" name="deveryIngAll"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"><c:out value="${orderDlvyCountInfo.DLVYING_PICKUP_READY_CNT + orderDlvyCountInfo.VNDR_DLVYING_PICKUP_READY_CNT}" default="0"/></fmt:formatNumber></a>건</span>
											</li>
											<li class="status-item">
												<span class="item-label">배송지연</span>
												<span class="item-value"><a href="#" class="cnt" name="deveryNotAll"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"><c:out value="${orderDlvyCountInfo.PRODUCT_PREPARATION_CNT + orderDlvyCountInfo.VNDR_PRODUCT_PREPARATION_CNT}" default="0"/></fmt:formatNumber></a>건</span>
											</li>
										</ul>
									</div>
									<!-- E:tab_content -->
									<!-- S:tab_content -->
									<div id="tabContent12" class="tab-content">
										<ul class="status-list">
											<li class="status-item">
												<span class="item-label">결제완료</span>
												<span class="item-value"><a href="#" class="cnt" name="paymentComplete"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"><c:out value="${orderDlvyCountInfo.COMPLETE_CNT}" default="0"/></fmt:formatNumber></a>건</span>
											</li>
											<li class="status-item">
												<span class="item-label">상품준비중</span>
												<span class="item-value"><a href="#" class="cnt" name="productIng"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"><c:out value="${orderDlvyCountInfo.PRODUCT_PREPARATION_CNT}" default="0"/></fmt:formatNumber></a>건</span>
											</li>
											<li class="status-item">
												<span class="item-label">배송중</span>
												<span class="item-value"><a href="#" class="cnt" name="deveryIng"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"><c:out value="${orderDlvyCountInfo.DLVYING_PICKUP_READY_CNT}" default="0"/></fmt:formatNumber></a>건</span>
											</li>
											<li class="status-item">
											<%--// 배송지연은 자사가 없기 때문에 삭제함[20191008]
												<span class="item-label">배송지연</span>
												<span class="item-value"><a href="#" class="cnt" name="deveryNot">8</a>건</span>
											--%>
											</li>
										</ul>
									</div>
									<!-- E:tab_content -->
									<!-- S:tab_content -->
									<div id="tabContent13" class="tab-content">
										<ul class="status-list">
											<li class="status-item">
												<span class="item-label">결제완료</span>
												<span class="item-value"><a href="#" class="cnt" name="paymentCompleteVndr"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"><c:out value="${orderDlvyCountInfo.VNDR_COMPLETE_CNT}" default="0"/></fmt:formatNumber></a>건</span>
											</li>
											<li class="status-item">
												<span class="item-label">상품준비중</span>
												<span class="item-value"><a href="#" class="cnt" name="productIngVndr"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"><c:out value="${orderDlvyCountInfo.VNDR_PRODUCT_PREPARATION_CNT}" default="0"/></fmt:formatNumber></a>건</span>
											</li>
											<li class="status-item">
												<span class="item-label">배송중</span>
												<span class="item-value"><a href="#" class="cnt" name="deveryIngVndr"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}"><c:out value="${orderDlvyCountInfo.VNDR_DLVYING_PICKUP_READY_CNT}" default="0"/></fmt:formatNumber></a>건</span>
											</li>
											<li class="status-item">
											<%--// 배송지연은 입점이 없기 때문에 삭제함[20191008]
												<span class="item-label">배송지연</span>
												<span class="item-value"><a href="#" class="cnt" name="deveryNotVndr">12</a>건</span>
											--%>
											</li>
										</ul>
									</div>
									<!-- E:tab_content -->
								</div>
							</div>
						</div>
						<div class="card-box has-tab">
							<div class="card-header">
								<div class="fl">
									<span class="card-title">클레임 현황</span>
									<span class="date-range">
										<span><fmt:formatDate value="${oneMonthAgoDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}"/></span>
										 	~ 
									 	<span><fmt:formatDate value="${nowDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}" /></span>
									</span>
								</div>
								<div class="fr">
									<button type="button" class="btn-sm btn-func" id="btnClaimRefresh"><i class="ico ico-refresh"></i>새로고침</button>
								</div>
							</div>
							<div class="card-body">
								<div class="tab-wrap">
									<ul class="tabs">
										<li class="tab-item"><a href="#tabContent21" class="tab-link">전체</a></li>
										<li class="tab-item"><a href="#tabContent22" class="tab-link">자사</a></li>
										<li class="tab-item"><a href="#tabContent23" class="tab-link">입점</a></li>
									</ul>
									<!-- S:tab_content -->
									<div id="tabContent21" class="tab-content">
										<ul class="status-list">
											<li class="status-item">
												<span class="item-label">반품접수/진행중</span>
												<span class="item-value"><a href="#" class="cnt" name="clmReturnCnt"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}">${claimCountInfo.clmReturnCnt + claimCountInfo.vndrClmReturnCnt}</fmt:formatNumber></a>건</span>
											</li>
											<li class="status-item">
												<span class="item-label">교환접수/진행중</span>
												<span class="item-value"><a href="#" class="cnt" name="clmExchangeCnt"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}">${claimCountInfo.clmExchangeCnt + claimCountInfo.vndrClmExchangeCnt}</fmt:formatNumber></a>건</span>
											</li>
											<li class="status-item">
												<span class="item-label">AS 접수/진행중</span>
												<span class="item-value"><a href="#" class="cnt" name="asCnt"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}">${claimCountInfo.asCnt}</fmt:formatNumber></a>건</span>
											</li>
											<li class="status-item">
												<span class="item-label">판매취소요청</span>
												<span class="item-value"><a href="#" class="cnt" name="cancelSellRequestCnt"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}">${claimCountInfo.cancelSellRequestCnt}</fmt:formatNumber></a>건</span>
											</li>
										</ul>
									</div>
									<!-- E:tab_content -->
									<!-- S:tab_content -->
									<div id="tabContent22" class="tab-content">
										<ul class="status-list">
											<li class="status-item">
												<span class="item-label">반품접수/진행중</span>
												<span class="item-value"><a href="#" class="cnt" name="mmnyYclmReturnCnt"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}">${claimCountInfo.clmReturnCnt}</fmt:formatNumber></a>건</span>
											</li>
											<li class="status-item">
												<span class="item-label">교환접수/진행중</span>
												<span class="item-value"><a href="#" class="cnt" name="mmnyYclmExchangeCnt"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}">${claimCountInfo.clmExchangeCnt}</fmt:formatNumber></a>건</span>
											</li>
											<li class="status-item">
												<span class="item-label">AS 접수/진행중</span>
												<span class="item-value"><a href="#" class="cnt" name="asCnt"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}">${claimCountInfo.asCnt}</fmt:formatNumber></a>건</span>
											</li>
										</ul>
									</div>
									<!-- E:tab_content -->
									<!-- S:tab_content -->
									<div id="tabContent23" class="tab-content">
										<ul class="status-list">
											<li class="status-item">
												<span class="item-label">반품접수/진행중</span>
												<span class="item-value"><a href="#" class="cnt" name="mmnyNclmReturnCnt"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}">${claimCountInfo.vndrClmReturnCnt}</fmt:formatNumber></a>건</span>
											</li>
											<li class="status-item">
												<span class="item-label">교환접수/진행중</span>
												<span class="item-value"><a href="#" class="cnt" name="mmnyNclmExchangeCnt"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}">${claimCountInfo.vndrClmExchangeCnt}</fmt:formatNumber></a>건</span>
											</li>
											<li class="status-item">
												<span class="item-label">판매취소요청</span>
												<span class="item-value"><a href="#" class="cnt" name="cancelSellRequestCnt"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}">${claimCountInfo.cancelSellRequestCnt}</fmt:formatNumber></a>건</span>
											</li>
										</ul>
									</div>
									<!-- E:tab_content -->
								</div>
							</div>
						</div>
						<div class="card-box has-tab">
							<div class="card-header">
								<div class="fl">
									<span class="card-title">판매 현황</span>
									<span class="date-range">
										<span><fmt:formatDate value="${oneMonthAgoDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}"/></span>
										 	~ 
									 	<span><fmt:formatDate value="${nowDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}" /></span>
									</span>
								</div>
							</div>
							<div class="card-body">
								<div class="tab-wrap">
									<ul class="tabs">
										<li class="tab-item"><a href="#tabContent51" class="tab-link">전체</a></li>
										<li class="tab-item"><a href="#tabContent52" class="tab-link">자사</a></li>
										<li class="tab-item"><a href="#tabContent53" class="tab-link">입점</a></li>
									</ul>
									<!-- S:tab_content -->
									<div id="tabContent51" class="tab-content">
										<ul class="status-list">
											<li class="status-item">
												<span class="item-label">주문건수</span>
												<span class="item-value"><a href="#" class="cnt" name="cntDashBoardOrder"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}" value="${localOrderCntAmt.orderCnt + vendorOrderCntAmt.orderCnt}"/></a>건</span>
											</li>
											<li class="status-item">
												<span class="item-label">주문금액</span>
												<span class="item-value"><a href="#" class="cnt tc-blue" name="cntDashBoardOrder"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}" value="${localOrderCntAmt.orderAmt + vendorOrderCntAmt.orderAmt}"/></a>원</span>
											</li>
										</ul>
									</div>
									<!-- E:tab_content -->
									<!-- S:tab_content -->
									<div id="tabContent52" class="tab-content">
										<ul class="status-list">
											<li class="status-item">
												<span class="item-label">주문건수</span>
												<span class="item-value"><a href="#" class="cnt" name="cntDashBoardOrder"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}" value="${localOrderCntAmt.orderCnt}"/></a>건</span>
											</li>
											<li class="status-item">
												<span class="item-label">주문금액</span>
												<span class="item-value"><a href="#" class="cnt tc-blue" name="cntDashBoardOrder"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}" value="${localOrderCntAmt.orderAmt}"/></a>원</span>
											</li>
										</ul>
									</div>
									<!-- E:tab_content -->
									<!-- S:tab_content -->
									<div id="tabContent53" class="tab-content">
										<ul class="status-list">
											<li class="status-item">
												<span class="item-label">주문건수</span>
												<span class="item-value"><a href="#" class="cnt" name="cntDashBoardOrder"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}" value="${vendorOrderCntAmt.orderCnt}"/></a>건</span>
											</li>
											<li class="status-item">
												<span class="item-label">주문금액</span>
												<span class="item-value"><a href="#" class="cnt tc-blue" name="cntDashBoardOrder"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}" value="${vendorOrderCntAmt.orderAmt}"/></a>원</span>
											</li>
										</ul>
									</div>
									<!-- E:tab_content -->
								</div>
							</div>
							
						</div>
					</div>
					<div class="flex-box">
						<div class="card-box has-tab">
							<div class="card-header">
								<div class="fl">
									<span class="card-title">미처리 문의현황</span>
									<span class="date-range">
										<span><fmt:formatDate value="${oneMonthAgoDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}"/></span>
										 	~ 
									 	<span><fmt:formatDate value="${nowDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}" /></span>
									</span>
								</div>
								<div class="fr">
									<button type="button" name="refresh" id="unAswrRefreshBtn" class="btn-sm btn-func"><i class="ico ico-refresh"></i>새로고침</button>
								</div>
							</div>
							<div class="card-body">
								<div class="tab-wrap">
									<ul class="tabs">
										<li class="tab-item"><a href="#tabContent2_1" class="tab-link">전체</a></li>
										<li class="tab-item"><a href="#tabContent2_2" class="tab-link">자사</a></li>
										<li class="tab-item"><a href="#tabContent2_3" class="tab-link">입점</a></li>
									</ul>
									<c:choose>
										<c:when test="${!empty unAswrList }">			
											<c:forEach var="unAswrList" items="${unAswrList}" varStatus="status">			
												<div id="tabContent2_<c:out value="${status.count}" />" class="tab-content">
													<ul class="status-list">
														<li class="status-item">
															<span class="item-label">1:1 문의</span>
															<span class="item-value"><a href="#" class="cnt" name="inquiryCnt">${unAswrList.inquiryCnt }</a>건</span>
														</li>
														<li class="status-item">
															<span class="item-label">협력 게시판</span>
															<span class="item-value"><a href="#" class="cnt" name="coworkCnt">${unAswrList.coworkCnt }</a>건</span>
														</li>
														<c:if test="${status.count ne 3 }">
															<li class="status-item">
															<span class="item-label">고객의 소리</span>
															<span class="item-value"><a href="#" class="cnt" name="voiceCnt">${unAswrList.voiceCnt }</a>건</span>
															</li>
														</c:if>
														<li class="status-item">
															<span class="item-label">상품 Q&A</span>
															<span class="item-value"><a href="#" class="cnt" name ="prdtQnaCnt">${unAswrList.prdtQnaCnt }</a>건</span>
														</li>
														<li class="status-item">
															<span class="item-label">상품 후기</span>
															<span class="item-value"><a href="#" class="cnt" name="reviewCnt">${unAswrList.reviewCnt }</a>건</span>
														</li>
													</ul>
												</div>
											</c:forEach>	
										</c:when>
										<c:otherwise>
											<c:forEach begin="1" end="3" varStatus="status">
												<div id="tabContent2_<c:out value="${status.count}" />" class="tab-content">
													<ul class="status-list">
														<li class="status-item">
															<span class="item-label">1:1 문의</span>
															<span class="item-value"><a href="#" class="cnt" name="inquiryCnt">0</a>건</span>
														</li>
														<li class="status-item">
															<span class="item-label">협력 게시판</span>
															<span class="item-value"><a href="#" class="cnt" name="coworkCnt">0</a>건</span>
														</li>
														<li class="status-item">
															<span class="item-label">고객의 소리</span>
															<span class="item-value"><a href="#" class="cnt">0</a>건</span>
														</li>
														<li class="status-item">
															<span class="item-label">상품 Q&amp;A</span>
															<span class="item-value"><a href="#" class="cnt">0</a>건</span>
														</li>
														<li class="status-item">
															<span class="item-label">상품 후기</span>
															<span class="item-value"><a href="#" class="cnt">0</a>건</span>
														</li>
													</ul>
												</div>
											</c:forEach>	
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
						<div class="card-box has-tab">
							<div class="card-header">
								<div class="fl">
									<span class="card-title">기타 문의현황</span>
									<span class="date-range">
										<span><fmt:formatDate value="${oneMonthAgoDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}"/></span>
										 	~ 
									 	<span><fmt:formatDate value="${nowDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}" /></span>
									</span>
								</div>
							</div>
							<div class="card-body">
								<div class="tab-wrap">
									<ul class="tabs">
										<li class="tab-item"><a href="#tabContent2_4" class="tab-link">전체</a></li>
										<li class="tab-item"><a href="#tabContent2_5" class="tab-link">통합몰</a></li>
										<li class="tab-item"><a href="#tabContent2_6" class="tab-link">OTS</a></li>
									</ul>
									<c:choose>
										<c:when test="${!empty etcList }">
								<c:forEach var="etcList" items="${etcList}" varStatus="status">
									<div id="tabContent2_<c:out value="${status.count + 3}" />" class="tab-content">
										<ul class="status-list">
											<li class="status-item">
												<span class="item-label">입점/제휴 문의</span>
												<span class="item-value"><a href="#" class="cnt" name="contactCnt">${etcList.contactCnt }</a>건</span>
											</li>
											<li class="status-item">
												<span class="item-label">단체주문 문의</span>
												<span class="item-value"><a href="#" class="cnt" name="bulkBuyCnt">${etcList.bulkBuyCnt }</a>건</span>
											</li>
										</ul>
									</div>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach begin="4" end="6" varStatus="status">
										<div id="tabContent2_<c:out value="${status.index}" />" class="tab-content">
											<ul class="status-list">
												<li class="status-item">
													<span class="item-label">입점/제휴 문의</span>
													<span class="item-value"><a href="#" class="cnt">0</a>건</span>
												</li>
												<li class="status-item">
													<span class="item-label">단체주문 문의</span>
													<span class="item-value"><a href="#" class="cnt">999</a>건</span>
												</li>
											</ul>
										</div>
									</c:forEach>
								</c:otherwise>
							</c:choose>		
								</div>
							</div>
						</div>
						<div class="card-box has-tab">
							<div class="card-header">
								<div class="fl">
									<span class="card-title">BEST 입점사 TOP 5</span>
									<span class="date-range">
										<span><fmt:formatDate value="${oneMonthAgoDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}"/></span>
										 	~ 
									 	<span><fmt:formatDate value="${nowDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}" /></span>
									</span>
								</div>
							</div>
							<div class="card-body">
								<div class="tab-wrap">
									<ul class="tabs">
										<li class="tab-item"><a href="#tabContent2_7" class="tab-link">주문건수</a></li>
										<li class="tab-item"><a href="#tabContent2_8" class="tab-link">주문금액</a></li>
									</ul>
									<div id="tabContent2_7" class="tab-content">
										<table class="tbl-col">
											<caption>BEST 입점사 TOP 5 주문건수</caption>
											<colgroup>
												<col style="width: 60px;">
												<col>
												<col style="width: 90px;">
											</colgroup>
											<tbody>
												<c:forEach var="cntList" items='${bestVndrOrderCnt}' varStatus="index">
													<tr>
														<td class="text-center">${index.count}</td>
														<td class="text-left">${cntList.vndrName}</td>
														<td class="text-right"><c:out value="${cntList.orderCnt}건"/></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div id="tabContent2_8" class="tab-content">
										<table class="tbl-col">
											<caption>BEST 입점사 TOP 5 주문금액</caption>
											<colgroup>
												<col style="width: 60px;">
												<col>
												<col style="width: 130px;">
											</colgroup>
											<tbody>
												<c:forEach var="amtList" items='${bestVndrOrderAmt}' varStatus="id">
													<tr>
														<td class="text-center">${id.count}</td>
														<td class="text-left">${amtList.vndrName}</td>
														<td class="text-right"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}" value="${amtList.orderCnt}"/>원</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
						<div class="card-box has-tab">
							<div class="card-header">
								<div class="fl">
									<span class="card-title">관리대상 입점사 TOP 5</span>
									<span class="date-range">
										<span><fmt:formatDate value="${oneMonthAgoDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}"/></span>
										 	~ 
									 	<span><fmt:formatDate value="${nowDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}" /></span>
									</span>
								</div>
							</div>
							<div class="card-body">
								<div class="tab-wrap">
									<ul class="tabs">
										<li class="tab-item"><a href="#tabContent2_9" class="tab-link">미처리건수</a></li>
										<li class="tab-item"><a href="#tabContent2_10" class="tab-link">배송지연건수</a></li>
									</ul>
									<div id="tabContent2_9" class="tab-content">
										<table class="tbl-col">
											<caption>관리대상 입점사 TOP 5 미처리건수</caption>
											<colgroup>
												<col style="width: 60px;">
												<col>
												<col style="width: 90px;">
											</colgroup>
											<tbody>
												<c:forEach var="vndrList" items="${unAswrVndrList}" varStatus="index">
													<tr>
														<td class="text-center">${index.count}</td>
														<td class="text-center">${vndrList.vndrName}</td>
														<td class="text-right"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}" value="${vndrList.totalCnt}"/>건</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div id="tabContent2_10" class="tab-content">
										<table class="tbl-col">
											<caption>관리대상 입점사 TOP 5 배송지연건수</caption>
											<colgroup>
												<col style="width: 60px;">
												<col>
												<col style="width: 90px;">
											</colgroup>
											<tbody>
												<c:forEach var="delayedVndrList" items="${delayedDeliveryVndrList}" varStatus="index">
													<tr>
														<td class="text-center">${index.count}</td>
														<td class="text-center">${delayedVndrList.vndrName}</td>
														<td class="text-right"><fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}" value="${delayedVndrList.totalCnt}"/>건</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="flex-box col-4by2">
						<div class="card-box">
							<div class="card-header">
								<div class="fl">
									<span class="card-title">회원현황</span>
									<span class="date-range"><span><fmt:formatDate value="${oneDayAgoDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}"/></span> 기준</span>
								</div>
							</div>
							<div class="card-body">
								<ul class="status-list">
									<li class="status-item">
										<span class="item-label">통합멤버십</span>
										<span class="item-value">
											<span class="cnt black">
												<fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}" value="${memberStatus.memberShip}"/>
											</span>건
										</span>
									</li>
									<li class="status-item">
										<span class="item-label">온라인</span>
										<span class="item-value">
											<span class="cnt black">
												<fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}" value="${memberStatus.online}"/>
											</span>건
										</span>
									</li>
									<li class="status-item">
										<span class="item-label">통합멤버십 전환</span>
										<span class="item-value">
											<span class="cnt black">
												<fmt:formatNumber type="number" pattern="${Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT}" value="${memberStatus.mbshpCnvrt}"/>
											</span>건
										</span>
									</li>
								</ul>
							</div>
						</div>
						<div class="card-box">
							<div class="card-header">
								<div class="fl">
									<span class="card-title">기획전 현황</span>
									<span class="date-range">
										<span><fmt:formatDate value="${oneMonthAgoDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}"/></span>
										 	~ 
									 	<span><fmt:formatDate value="${nowDtm}" pattern="${Const.DEFAULT_DATE_PATTERN}" /></span>
									</span>
								</div>
								<div class="fr">
									<button type="button" class="btn-sm btn-func" id="planningDisplaybtn"><i class="ico ico-refresh"></i>새로고침</button>
								</div>
							</div>
							<div class="card-body">
								<ul class="status-list">
									<li class="status-item">
										<span class="item-label">진행중인 기획전</span>
										<span class="item-value"><a href="javascript:void(0);" class="cnt planning-display" name="plndpOngoing">${useplanning }</a>건</span>
									</li>
									<li class="status-item">
										<span class="item-label">승인요청중인 기획전</span>
										<span class="item-value"><a href="javascript:void(0);" class="cnt planning-display" name="plndpRequest">${usereadyplanning }</a>건</span>
									</li>
									<li class="status-item">
										<span class="item-label">승인반려 기획전</span>
										<span class="item-value"><a href="javascript:void(0);" class="cnt planning-display" name="plndpRefuse">${notuseplanning }</a>건</span>
									</li>
								</ul>
							</div>
						</div>
						<div class="card-box table">
							<div class="card-header">
								<div class="fl">
									<span class="card-title">공지사항</span>
									<a href="#" class="btn-sm btn-func" id="noticeMoreBtn">더보기</a>
								</div>
								<!-- <div class="fr">
									<button type="button" class="btn-sm btn-func"><i class="ico ico-refresh"></i>새로고침</button>
								</div> -->
							</div>
							<div class="card-body">
								<table class="tbl-col">
									<caption>공지사항</caption>
									<colgroup>
										<col>
										<col style="width: 130px;">
									</colgroup>
									<thead>
										<tr>
											<th scope="col">제목</th>
											<th scope="col">등록일시</th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${not empty adminNoticeList}">
												<c:forEach var="adminNoticeList" items="${adminNoticeList}">
													<tr>
														<td>
															<a href="javascript:void(0)" class="adminNoticeTitle" title="관리자 공지사항 제목" name="${adminNoticeList.adminNotcSeq }">${adminNoticeList.notcTitleText }</a>
														</td>
														<td class="text-center">
															<fmt:formatDate value="${adminNoticeList.rgstDtm }" pattern="${Const.DEFAULT_DATETIME_PATTERN}"/>
														</td>
													</tr>
												</c:forEach>	
											</c:when>
											<c:otherwise>
												<tr>
													<td class="text-center" colspan="2">관리자 공지사항이 없습니다.</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- E : container -->
		
<!-- S : 비밀번호 변경 레이어 -->
<%@include file="/WEB-INF/views/system/login/password-change.jsp" %>
<!-- E : 비밀번호 변경 레이어 -->

<script src="/static/common/js/biz/main/abcmart.main.bo.dashboard.js<%=_DP_REV%>"></script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>