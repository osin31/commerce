<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>

<script type="text/javascript">
	$(function(){
		// 사이트 grid init
		abc.biz.system.site.initSiteSheet();
		
		new abc.biz.system.site.processImage({
			file: '#pcLogoImg',
			name: '#pcLogoName'
		});
		
		new abc.biz.system.site.processImage({
			file: '#moLogoImg',
			name: '#moLogoName'
		});
		
		new abc.biz.system.site.processImage({
			file: '#gnbLogoImg',
			name: '#gnbLogoName'
		});
		
		$("#sortSeq").on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
		
		$("#chnnlPrdtGbnNo").on('input', function(event){
			abc.text.validateOnlyNumber(this);
		});
		
		// tabsactivate event
		$("#tabSiteDetail").on("tabsactivate", function(event, ui) {
			if (ui.newTab.prevObject.attr('id') === "tabDelivery") {
				location.hash = "tabDelivery";
			} else if (ui.newTab.prevObject.attr('id') === "tabChannel") {
				location.hash = "tabChannel";
			} else if (ui.newTab.prevObject.attr('id') === "tabPayment") {
				location.hash = "tabPayment";
			} else if (ui.newTab.prevObject.attr('id') === "tabGuide") {
				location.hash = "tabGuide";
			}
			abc.biz.system.site.changeSiteData();
		});
	});	

	// 사이트 : selectCell 이벤트 			
	function siteSheet_OnSelectCell(oldRow, oldCol, newRow, newCol, isDelete) {
	}
	
	// 사이트 : click 이벤트 			
	function siteSheet_OnClick(row, col, value, cellX, cellY, cellW, cellH) {
		abc.biz.system.site.selectedRow = row;
		abc.biz.system.site.changeSiteData();
	}
	
	// 사이트 : 검색 종료
	function siteSheet_OnSearchEnd(code, msg, stCode, stMsg, responseText) {
		if (code != 0) {
			alert("오류가 발생했습니다.");
		} else {
			siteSheet.SelectCell(abc.biz.system.site.selectedRow, 0);
			abc.biz.system.site.changeSiteData();
		}
	}
	
	function channelSheet_OnValidation(row, col, value) {
		if (channelSheet.ColSaveName(col) == "sortSeq") {
	        if (abc.text.isBlank(value)) {
	        	alert("정렬순서를 입력해 주십시요.");
	        	channelSheet.ValidateFail(1);
	        	channelSheet.SelectCell(row, "sortSeq");
	        }
	    }
		
		if (channelSheet.ColSaveName(col) == "sellPsbltYn"
				|| channelSheet.ColSaveName(col) == "insdMgmtInfoText") {
			if (channelSheet.GetCellValue(row, "sellPsbltYn") == "Y") {
				var insdMgmtInfoText = channelSheet.GetCellValue(row, "insdMgmtInfoText");
				if (abc.text.isBlank(insdMgmtInfoText)) {
					alert("매장코드를 입력해 주십시요.");
					channelSheet.ValidateFail(1);
					channelSheet.SelectCell(row, "insdMgmtInfoText");
				}
				
				if (abc.text.isLimitLength(insdMgmtInfoText, 20)) {
					alert("매장코드가 최대값을 초과하였습니다.");
					channelSheet.ValidateFail(1);
					channelSheet.SelectCell(row, "insdMgmtInfoText");
				}
			}
				
		}
	}
	
	function channelSheet_OnEditValidation(row, col, value) {
		if (channelSheet.ColSaveName(col) == "sortSeq") {
	        if (abc.text.isBlank(value)) {
	        	alert("정렬순서를 입력해 주십시요.");
	        	channelSheet.ValidateFail(1);
	        }
	        if (value > abc.consts.INT_MAX_VALUE) {
	        	alert("정렬순서가 최대값을 초과하였습니다.");
	        	channelSheet.ValidateFail(1);
	        }
	    }
		
		if (channelSheet.ColSaveName(col) == "chnnlName") {
	        if (abc.text.isBlank(value)) {
	        	alert("채널명을 입력해 주십시요.");
	        	channelSheet.ValidateFail(1);
	        }
	        if (abc.text.isLimitLength(value, 100)) {
	        	alert("채널명이 최대값을 초과하였습니다.");
	        	channelSheet.ValidateFail(1);
	        }
	    }
	}
	
	// 채널 : 검색 종료	
	function channelSheet_OnSearchEnd(code, msg, stCode, stMsg, responseText) {
		if (code != 0) {
			alert("오류가 발생했습니다.");
		} else {
			
		}
	}
	
	// 채널 : click 이벤트 			
	function channelSheet_OnClick(row, col, value, cellX, cellY, cellW, cellH) {
		
		if (row == 0) return false;
		
		var params = {};
		params.siteNo = channelSheet.GetCellValue(row, "siteNo");
		params.chnnlNo = channelSheet.GetCellValue(row, "chnnlNo");
		
		//그리드 클릭 시 상세조회
		abc.biz.system.site.channelDetailInfo(params);
	}
</script>

<!-- S : container -->
<div class="container">
	<div class="content-box">
		<!-- S : page-header -->
		<div class="page-header">
			<div class="fl">
				<h2 class="page-title">사이트 관리</h2>
				<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
				<button type="button" class="btn-favorites">
					<span class="ico ico-star">
						<span class="offscreen">즐겨찾기 등록</span>
					</span>
				</button>
			</div>
			<div class="fr">
				<div class="navi-wrap">
					<ul class="navi">
						<li class="home">
							<a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a>
						</li>
						<li>기본설정</li>
						<li>사이트 관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- E : page-header -->

		<div class="col-wrap col3-2by1 full-content">
			<div class="col">
				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">사이트 목록</h3>
					</div>
					<div class="fr">
						<div class="btn-group">
							<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
							<a href="#" class="btn-sm btn-func" id="newSiteBtn">등록</a>
							<!-- E : 20190114 수정 -->
						</div>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : selectable-list-wrap -->
				<div class="ibsheet-wrap">
					<div id="siteGrid"></div>
				</div>
				<!-- E : selectable-list-wrap -->
			</div>

			<div class="col">
				<!-- S : row-wrap -->
				<div class="row-wrap">
					<div class="row">
						<!-- S : content-header -->
						<div class="content-header">
							<div class="fl">
								<h3 class="content-title">사이트정보</h3>
							</div>
						</div>
						<!-- E : content-header -->

						<!-- S : tbl-row -->
						<form id="siteForm" name="siteForm">
							<input type="hidden" name="siteNo">
							<input type="hidden" name="useYn" value="Y">
							<input type="hidden" name="sortSeq" value="0">
							<table class="tbl-row">
								<caption>사이트정보</caption>
								<colgroup>
									<col style="width: 130px;">
									<col>
								</colgroup>
								<tbody>
									<tr>
										<th scope="row"><span class="th-required">사이트명</span></th>
										<td class="input"><input type="text" class="ui-input" title="사이트명 입력" name="siteName" id="siteName" maxlength="50"></td>
									</tr>
									<!-- DESC : 20190116 작성자, 수정자 영역 삭제 // 기획변경 -->
									<tr>
										<th scope="row">SNS</th>
										<td class="input">
											<ul class="ip-box-list">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkSNS01" type="checkbox">
														<label for="chkSNS01">전체</label>
													</span>
												</li>
												<c:forEach items="${snsChnnlCode}" var="code">
												<li>
													<span class="ui-chk">
														<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input type="hidden" name="applySnsArray.snsChnnlCode" value="${code.codeDtlNo}">
														<input id="snsChnnl${code.codeDtlNo}" type="hidden" name="applySnsArray.useYn" custom="snsChnnlHidden" value="N">
														<input id="snsChnnlCheckbox${code.codeDtlNo}" type="checkbox" custom="snsChnnlCheckbox" data="${code.codeDtlNo}">
														<label for="snsChnnlCheckbox${code.codeDtlNo}">${code.codeDtlName}</label>
													</span>
												</li>
												</c:forEach>
											</ul>
										</td>
									</tr>
								</tbody>
							</table>
							<!-- E : tbl-row -->
							
							<!-- DESC : 20190116 저장 버튼 삭제 // 기획변경 -->
							<!-- s : 190729 추가 | 사이트 정보 테이블 하단 안내문구 추가 -->
							<div class="tbl-desc-wrap">
								<ol class="tbl-desc-list">
								<li>* 사이트를 추가/수정할 경우 abc.backend PJT의 각 개발환경별 app-config.properties파일에 site.pc.url.list, site.mobile.url.list을 추가/수정하셔야 합니다.</li>
								<li>* 채널을 추가/수정할 경우 abc.backend PJT의 각 개발환경별 app-config.properties파일에 channel.pc.url.list, channel.mobile.url.list을 추가/수정하셔야 합니다.</li>
								</ol>
							</div>
							<!-- e : 190729 추가 | 사이트 정보 테이블 하단 안내문구 추가 -->
					</div>

					<div class="row">
						<!-- S : tab-wrap -->
						<div class="tab-wrap" id="tabSiteDetail">
							<ul class="tabs">
								<li class="tab-item">
									<a href="#tabContent1" class="tab-link" id="tabDelivery">배송정보</a>
								</li>
								<!-- S : 20180116 수정 // 등록화면인 경우, 배송정보 제외 나머지 탭 tab-link영역 tab-disabled 클래스 추가 -->
								<li class="tab-item" custom="tabOption">
									<a href="#tabContent2" class="tab-link tab-disabled" custom="tabOption" id="tabChannel">채널정보</a>
								</li>
								<li class="tab-item" custom="tabOption">
									<a href="#tabContent3" class="tab-link tab-disabled" custom="tabOption" id="tabPayment">결제수단</a>
								</li>
								<li class="tab-item" custom="tabOption">
									<a href="#tabContent4" class="tab-link tab-disabled" custom="tabOption" id="tabGuide">배송/클레임 정책</a>
								</li>
								<!-- E : 20180116 수정 -->
							</ul>
							<!-- S:tab_content -->
							<div id="tabContent1" class="tab-content">
								<!-- S : content-header -->
								<div class="content-header">
									<div class="fl">
										<h3 class="content-title">배송정보</h3>
									</div>
								</div>
								<!-- E : content-header -->

								<!-- S : tbl-row -->
								<table class="tbl-row">
									<caption>배송정보</caption>
									<colgroup>
										<col style="width: 13%;">
										<col style="width: 12%;">
										<col>
									</colgroup>
									<tbody>
										<tr>
											<th scope="row" colspan="2"><span class="th-required">배송유형</span></th>
											<td class="input">
												<ul class="ip-box-list">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="chkDelivery01" type="checkbox"> <label for="chkDelivery01">전체</label>
														</span>
													</li>
													<c:forEach items="${dlvyTypeCode}" var="code" varStatus="status">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input type="hidden" name="deliveryTypeList.dlvyTypeCode" value="${code.codeDtlNo}">
															<input id="deliveryType${code.codeDtlNo}" type="hidden" name="deliveryTypeList.useYn" custom="dlvyTypeHidden" value="N">
															<input id="checkbox${code.codeDtlNo}" type="checkbox" custom="dlvyTypeCheckbox" data="${code.codeDtlNo}"> <label for="checkbox${code.codeDtlNo}">${code.codeDtlName}</label>
														</span>
													</li>
													</c:forEach>
												</ul>
											</td>
										</tr>
										<tr>
											<th scope="rowgroup" rowspan="4">
												<span class="th-required">배송비용</span>
											</th>
											<th scope="row">주문</th>
											<td class="input">
												<!-- S : ip-text-box -->
												<span class="ip-text-box">
													<span class="text">구매금액</span>
													<input type="text" class="ui-input num-unit10000000" title="구매금액 입력" name="freeDlvyStdrAmt" id="freeDlvyStdrAmt" custom="number">
													<span class="text">원 미만일 때 배송비</span>
													<input type="text" class="ui-input num-unit10000000" title="배송비 입력" name="dlvyAmt" id="dlvyAmt" custom="number">
													<span class="text">원</span>
												</span>
												<!-- E : ip-text-box -->
											</td>
										</tr>
										<tr>
											<th scope="row">A/S</th>
											<td class="input">
												<!-- S : ip-text-box -->
												<span class="ip-text-box">
													<span class="text">수선AS 시 왕복</span>
													<input type="text" class="ui-input num-unit10000000" title="수선AS비용 입력" name="asDlvyAmt" id="asDlvyAmt" custom="number">
													<span class="text">원</span>
												</span>
												<!-- E : ip-text-box -->
											</td>
										</tr>
										<tr>
											<th scope="row">교환 배송</th>
											<td class="input">
												<!-- S : ip-text-box -->
												<span class="ip-text-box">
													<span class="text">일반 교환 시 왕복</span>
													<input type="text" class="ui-input num-unit10000000" title="교환 배송 비용 입력" name="exchngDlvyAmt" id="exchngDlvyAmt" custom="number">
													<span class="text">원, 상품불량, 오배송의 경우 배송비 환불</span>
												</span>
												<!-- E : ip-text-box -->
											</td>
										</tr>
										<tr>
											<th scope="row">반품비용</th>
											<td class="input">
												<!-- S : ip-text-box -->
												<span class="ip-text-box full-size">
													<span class="text">무료배송 상품의 반품 시</span>
													<input type="text" class="ui-input num-unit10000000" title="무료배송 반품비용 입력" name="freeDlvyRtnAmt" id="freeDlvyRtnAmt" custom="number">
													<span class="text">원, 상품불량, 오배송의 경우 배송비 환불</span>
												</span>
												<!-- E : ip-text-box -->
												<!-- S : ip-text-box -->
												<span class="ip-text-box full-size">
													<span class="text">유료배송 상품의 반품 시</span>
													<input type="text" class="ui-input num-unit10000000" title="유료배송 반품비용 입력" name="paidDlvyRtnAmt" id="paidDlvyRtnAmt" custom="number">
													<span class="text">원, 상품불량, 오배송의 경우 배송비 환불</span>
												</span>
												<!-- E : ip-text-box -->
											</td>
										</tr>
										<!-- DESC : 20190116 삭제 // 기획 수정으로 작성자/수정자 영역 테이블 분류되어 삭제 -->
									</tbody>
								</table>

								<!-- S : 20190116 수정 // 기획 수정으로 테이블 분류되어 추가. 등록화면인 경우 미노출 -->
								<!-- S : tbl-row -->
								<table class="tbl-row">
									<caption>작성 정보</caption>
									<colgroup>
										<col style="width: 130px;">
										<col>
										<col style="width: 130px;">
										<col>
									</colgroup>
									<tbody>
										<tr>
											<th scope="row">작성자</th>
											<td id="deliveryRgster"></td>
											<th scope="row">작성일시</th>
											<td id="deliveryRgstDtm"></td>
										</tr>
										<tr>
											<th scope="row">수정자</th>
											<td id="deliveryModer"></td>
											<th scope="row">수정일시</th>
											<td id="deliveryModDtm"></td>
										</tr>
									</tbody>
								</table>
								<!-- E : tbl-row -->
								<!-- E : 20190116 수정 // 기획 수정으로 테이블 분류되어 추가 -->
								<!-- E : tbl-row -->

								<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
								<!-- S : content-bottom -->
								<div class="content-bottom">
									<a href="#" class="btn-lg btn-save" id="siteSaveBtn">저장</a>
								</div>
								<!-- E : content-bottom -->
								<!-- E : 20190114 수정 -->
							</div>
							</form>
							<!-- E:tab_content -->
							<!-- S:tab_content -->
							
							<form id="channelForm" name="channelForm">
							<div id="tabContent2" class="tab-content">
								<!-- S : content-header -->
								<div class="content-header">
									<div class="fl">
										<h3 class="content-title">채널정보</h3>
									</div>
								</div>
								<!-- E : content-header -->
								
								<!-- S : tbl-controller -->
								<div class="tbl-controller">
									<div class="fl">
										<!-- S : opt-group -->
										<span class="opt-group"> <label class="title" for="channelPageCount">목록개수</label>
											<select class="ui-sel" id="channelPageCount">
												<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
											</select>
										</span>
										<!-- E : opt-group -->
									</div>
								</div>
								<!-- E : tbl-controller -->

								<!-- S : ibsheet-wrap -->
								<div class="ibsheet-wrap">
									<div id="channelGrid"></div>
								</div>
								<!-- E : ibsheet-wrap -->
								<div class="content-header">
									<div class="fl">
										<h3 class="content-title">채널 상세정보</h3>
									</div>
									<div class="fr">
										<div class="btn-group">
											<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
											<a href="#" class="btn-sm btn-func" id="newChnnelBtn">신규등록</a>
											<!-- E : 20190114 수정 -->
										</div>
									</div>
								</div>
								<!-- S : tbl-row -->
								<table class="tbl-row">
									<caption>채널정보</caption>
									<colgroup>
										<col style="width: 175px;">
										<col>
										<col style="width: 175px;">
										<col>
										<col style="width: 67px;">
									</colgroup>
									
									<input type="hidden" name="siteNo">
									<input type="hidden" id="chnnlNo" name="chnnlNo">
									<tbody>
										<tr>
											<th scope="row"><span class="th-required">채널명</span></th>
											<td class="input"><input type="text" class="ui-input" title="채널명 입력" id="chnnlName" name="chnnlName" maxlength="50"></td>
											<th scope="row"><span class="th-required">정렬순서</span></th>
											<td class="input" colspan="2"><input type="text" class="ui-input" title="채널명 입력" id="sortSeq" name="sortSeq" maxlength="3"></td>
										</tr>
										<tr>
											<th scope="row">입점사허용여부</th>
											<td class="input">
												<span class="ip-text-box">
													<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkUse03" type="checkbox" checked> <label for="chkUse03">허용</label>
														<input type="hidden" id="vndrUseYn" name="vndrUseYn">
													</span>
												</span>
											</td>
											<th scope="row">판매여부/매장코드</th>
											<td class="input" colspan="2">
												<span class="ip-text-box">
													<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
														<input id="chkUse02" type="checkbox" checked> <label for="chkUse02">가능</label>
														<input type="hidden" id="sellPsbltYn" name="sellPsbltYn">
													</span>
													<input type="text" class="ui-input" title="매장코드 입력" id="insdMgmtInfoText" name="insdMgmtInfoText" maxlength="10">
												</span>
											</td>
										</tr>
										<tr>
											<th scope="row">업체번호</th>
											<td class="input"><input type="text" class="ui-input" title="업체번호 입력" id="vndrNo" maxlength="10"></td>
											<th scope="row">사용여부</th>
											<td class="input" colspan="2">
												<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
													<input id="chkUse01" type="checkbox" checked="checked"> <label for="chkUse01">사용</label>
													<input type="hidden" id="useYn" name="useYn">
												</span>
											</td>
										</tr>
										<tr>
											<th scope="row"><span class="th-required">채널 상품 구분 번호(자사)</span></th>
											<td class="input"><input type="text" class="ui-input" title="채널 상품 구분 번호 입력" id="chnnlPrdtGbnNo" name="chnnlPrdtGbnNo" maxlength="3"></td>
											<th scope="row">검색 영역 채널 전시 여부</th>
											<td class="input" colspan="2">
												<ul class="ip-box-list">
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input type="radio" name="srchRelmExpsrYn" id="srchRelmExpsrY" checked value="${Const.BOOLEAN_TRUE}">
															<label for="srchRelmExpsrY">전시</label>
														</span>
													</li>
													<li>
														<span class="ui-rdo">
															<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input type="radio" name="srchRelmExpsrYn" id="srchRelmExpsrN" value="${Const.BOOLEAN_FALSE}">[]
															<label for="srchRelmExpsrN">미전시</label>
														</span>
													</li>
												</ul>
											</td>
										</tr>
										<tr>
											<th scope="row"><span class="th-required">채널 로고 이미지 PC</span></th>
											<td class="input" colspan="4">
												<div class="file-wrap">
													<ul class="file-list">
														<li>
															<span class="btn-box">
																<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
																<input type="hidden" id="pcLogoName" title="첨부파일 추가" name="pcLogoName" value="">
																<input type="file" id="pcLogoImg" title="첨부파일 추가" name="pcLogoImg" value="">
																<label for="pcChnnLogoImg">찾아보기</label>
															</span>
														</li>
														<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
														<li>
															<a href="javascript:void(0);" class="subject"></a>
															<button type="button" class="btn-file-del">
																<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
															</button>
														</li>
													</ul>
													<div class="img-wrap">
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row"><span class="th-required">채널 로고 이미지 MO</span></th>
											<td class="input" colspan="4">
												<div class="file-wrap">
													<ul class="file-list">
														<li>
															<span class="btn-box">
																<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
																<input type="hidden" id="moLogoName" title="첨부파일 추가" name="moLogoName" value="">
																<input type="file" id="moLogoImg" title="첨부파일 추가" name="moLogoImg" value="">
																<label for="moChnnLogoImg">찾아보기</label>
															</span>
														</li>
														<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
														<li>
															<a href="javascript:void(0);" class="subject"></a>
															<button type="button" class="btn-file-del">
																<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
															</button>
														</li>
													</ul>
													<div class="img-wrap">
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row"><span class="th-required">GNB 로고 이미지 PC</span></th>
											<td class="input" colspan="4">
												<div class="file-wrap">
													<ul class="file-list">
														<li>
															<span class="btn-box">
																<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
																<input type="hidden" id="gnbLogoName" title="첨부파일 추가" name="gnbLogoName" value="">
																<input type="file" id="gnbLogoImg" title="첨부파일 추가" name="gnbLogoImg" value="">
																<label for="gnbLogoImg">찾아보기</label>
															</span>
														</li>
														<!-- DESC : 첨부파일 링크인 경우 subject a태그 변환 -->
														<li>
															<a href="javascript:void(0);" class="subject"></a>
															<button type="button" class="btn-file-del">
																<span class="ico ico-fdel"><span class="offscreen">첨부파일 삭제</span></span>
															</button>
														</li>
													</ul>
													<div class="img-wrap">
													</div>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
								<!-- E : tbl-row -->
								
								<!-- S : content-bottom -->
								<div class="content-bottom">
									<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
									<a href="#tabChannel" class="btn-lg btn-save" id="channelSaveBtn">저장</a>
									<!-- E : 20190114 수정 -->
								</div>
								<!-- E : content-bottom -->
							</div>
							</form>
							<!-- E:tab_content -->
							<!-- S:tab_content -->
							<form id="paymentForm" name="paymentForm">
							<div id="tabContent3" class="tab-content">
								<!-- S : content-header -->
								<div class="content-header">
									<div class="fl">
										<h3 class="content-title">임직원</h3>
									</div>
									<div class="fr"></div>
								</div>
								<!-- E : content-header -->

								<!-- S : tbl-row -->
								<table class="tbl-row">
									<caption>임직원</caption>
									<colgroup>
										<col style="width: 12%;">
										<col style="width: 16%;">
										<col>
									</colgroup>
									<tbody>
										<tr>
											<th scope="rowgroup" rowspan="2">일반주문</th>
											<th scope="row">주결제수단</th>
											<td class="input">
												<ul class="ip-box-list">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="chkMainPayment01" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_MEMBERSHIP}YGY" class="paymentCheckboxAll">
															<label for="chkMainPayment01">전체</label>
														</span>
													</li>
													<c:forEach items="${pymntMeansCode}" var="code" varStatus="status">
													<c:if test="${code.addInfo1 eq 'M'}">
													<input type="hidden" name="siteNo">
													<input type="hidden" name="memberTypeCode" value="${CommonCode.MEMBER_TYPE_MEMBERSHIP}">
													<input type="hidden" name="empYn" value="Y">
													<input type="hidden" name="orderType" value="G">
													<input type="hidden" name="mainPymntMeansYn" value="Y">
													<input type="hidden" name="pymntMeansCode" value="${code.codeDtlNo}">
													<!-- id 조합 : memberTypeCode + empYn + orderType + mainPymntMeansYn + pymntMeansCode -->
													<input id="${CommonCode.MEMBER_TYPE_MEMBERSHIP}YGY${code.codeDtlNo}" type="hidden" name="useYn" value="N" data-group="${CommonCode.MEMBER_TYPE_MEMBERSHIP}YGYHidden" class="paymentUseHidden">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="${CommonCode.MEMBER_TYPE_MEMBERSHIP}YGY${code.codeDtlNo}Checkbox" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_MEMBERSHIP}YGYCheckbox" data-id="${CommonCode.MEMBER_TYPE_MEMBERSHIP}YGY${code.codeDtlNo}" class="paymentCheckbox">
															<label for="${CommonCode.MEMBER_TYPE_MEMBERSHIP}YGY${code.codeDtlNo}Checkbox">${code.codeDtlName}</label>
														</span>
													</li>
													</c:if>
													</c:forEach>
												</ul>
											</td>
										</tr>
										<tr>
											<th scope="row">보조결제수단</th>
											<td class="input">
												<ul class="ip-box-list">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="chkMainPayment02" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_MEMBERSHIP}YGN" class="paymentCheckboxAll">
															<label for="chkMainPayment02">전체</label>
														</span>
													</li>
													<c:forEach items="${pymntMeansCode}" var="code" varStatus="status">
													<c:if test="${code.addInfo1 eq 'S' && code.codeDtlNo ne '10007' && code.codeDtlNo ne '10008'}">
													<input type="hidden" name="siteNo">
													<input type="hidden" name="memberTypeCode" value="${CommonCode.MEMBER_TYPE_MEMBERSHIP}">
													<input type="hidden" name="empYn" value="Y">
													<input type="hidden" name="orderType" value="G">
													<input type="hidden" name="mainPymntMeansYn" value="N">
													<input type="hidden" name="pymntMeansCode" value="${code.codeDtlNo}">
													<input id="${CommonCode.MEMBER_TYPE_MEMBERSHIP}YGN${code.codeDtlNo}" type="hidden" name="useYn" value="N" data-group="${CommonCode.MEMBER_TYPE_MEMBERSHIP}YGNHidden" class="paymentUseHidden">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="${CommonCode.MEMBER_TYPE_MEMBERSHIP}YGN${code.codeDtlNo}Checkbox" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_MEMBERSHIP}YGNCheckbox" data-id="${CommonCode.MEMBER_TYPE_MEMBERSHIP}YGN${code.codeDtlNo}" class="paymentCheckbox">
															<label for="${CommonCode.MEMBER_TYPE_MEMBERSHIP}YGN${code.codeDtlNo}Checkbox">${code.codeDtlName}</label>
														</span>
													</li>
													</c:if>
													</c:forEach>
												</ul>
											</td>
										</tr>
									</tbody>
								</table>
								<!-- E : tbl-row -->

								<!-- S : content-header -->
								<div class="content-header">
									<div class="fl">
										<h3 class="content-title">온라인간편회원</h3>
									</div>
								</div>
								<!-- E : content-header -->
								
								<!-- S : tbl-row -->
								<table class="tbl-row">
									<caption>온라인간편회원</caption>
									<colgroup>
										<col style="width: 12%;">
										<col style="width: 16%;">
										<col>
									</colgroup>
									<tbody>
										<tr>
											<th scope="rowgroup" rowspan="2">일반주문</th>
											<th scope="row">주결제수단</th>
											<td class="input">
												<ul class="ip-box-list">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="chkMainPayment03" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_ONLINE}NGY" class="paymentCheckboxAll">
															<label for="chkMainPayment03">전체</label>
														</span>
													</li>
													<c:forEach items="${pymntMeansCode}" var="code" varStatus="status">
													<c:if test="${code.addInfo1 eq 'M'}">
													<input type="hidden" name="siteNo">
													<input type="hidden" name="memberTypeCode" value="${CommonCode.MEMBER_TYPE_ONLINE}">
													<input type="hidden" name="empYn" value="N">
													<input type="hidden" name="orderType" value="G">
													<input type="hidden" name="mainPymntMeansYn" value="Y">
													<input type="hidden" name="pymntMeansCode" value="${code.codeDtlNo}">
													<input id="${CommonCode.MEMBER_TYPE_ONLINE}NGY${code.codeDtlNo}" type="hidden" name="useYn" value="N" data-group="${CommonCode.MEMBER_TYPE_ONLINE}NGYHidden" class="paymentUseHidden">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="${CommonCode.MEMBER_TYPE_ONLINE}NGY${code.codeDtlNo}Checkbox" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_ONLINE}NGYCheckbox" data-id="${CommonCode.MEMBER_TYPE_ONLINE}NGY${code.codeDtlNo}" class="paymentCheckbox">
															<label for="${CommonCode.MEMBER_TYPE_ONLINE}NGY${code.codeDtlNo}Checkbox">${code.codeDtlName}</label>
														</span>
													</li>
													</c:if>
													</c:forEach>
												</ul>
											</td>
										</tr>
										<tr>
											<th scope="row">보조결제수단</th>
											<td class="input">
												<ul class="ip-box-list">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="chkMainPayment04" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_ONLINE}NGN" class="paymentCheckboxAll"> <label for="chkMainPayment04">전체</label>
														</span>
													</li>
													<c:forEach items="${pymntMeansCode}" var="code" varStatus="status">
													<c:if test="${code.addInfo1 eq 'S'}">
													<input type="hidden" name="siteNo">
													<input type="hidden" name="memberTypeCode" value="${CommonCode.MEMBER_TYPE_ONLINE}">
													<input type="hidden" name="empYn" value="N">
													<input type="hidden" name="orderType" value="G">
													<input type="hidden" name="mainPymntMeansYn" value="N">
													<input type="hidden" name="pymntMeansCode" value="${code.codeDtlNo}">
													<input id="${CommonCode.MEMBER_TYPE_ONLINE}NGN${code.codeDtlNo}" type="hidden" name="useYn" value="N" data-group="${CommonCode.MEMBER_TYPE_ONLINE}NGNHidden" class="paymentUseHidden">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="${CommonCode.MEMBER_TYPE_ONLINE}NGN${code.codeDtlNo}Checkbox" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_ONLINE}NGNCheckbox" data-id="${CommonCode.MEMBER_TYPE_ONLINE}NGN${code.codeDtlNo}" class="paymentCheckbox">
															<label for="${CommonCode.MEMBER_TYPE_ONLINE}NGN${code.codeDtlNo}Checkbox">${code.codeDtlName}</label>
														</span>
													</li>
													</c:if>
													</c:forEach>
												</ul>
											</td>
										</tr>
									</tbody>
								</table>
								<!-- E : tbl-row -->
								
								<!-- S : content-header -->
								<div class="content-header">
									<div class="fl">
										<h3 class="content-title">통합멤버십회원</h3>
									</div>
								</div>
								<!-- E : content-header -->

								<!-- S : tbl-row -->
								<table class="tbl-row">
									<caption>통합멤버십회원</caption>
									<colgroup>
										<col style="width: 12%;">
										<col style="width: 16%;">
										<col>
									</colgroup>
									<tbody>
										<tr>
											<th scope="rowgroup" rowspan="2">일반주문</th>
											<th scope="row">주결제수단</th>
											<td class="input">
												<ul class="ip-box-list">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="chkMainPayment05" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NGY" class="paymentCheckboxAll">
															<label for="chkMainPayment05">전체</label>
														</span>
													</li>
													<c:forEach items="${pymntMeansCode}" var="code" varStatus="status">
													<c:if test="${code.addInfo1 eq 'M'}">
													<input type="hidden" name="siteNo">
													<input type="hidden" name="memberTypeCode" value="${CommonCode.MEMBER_TYPE_MEMBERSHIP}">
													<input type="hidden" name="empYn" value="N">
													<input type="hidden" name="orderType" value="G">
													<input type="hidden" name="mainPymntMeansYn" value="Y">
													<input type="hidden" name="pymntMeansCode" value="${code.codeDtlNo}">
													<input id="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NGY${code.codeDtlNo}" type="hidden" name="useYn" value="N" data-group="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NGYHidden" class="paymentUseHidden">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NGY${code.codeDtlNo}Checkbox" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NGYCheckbox" data-id="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NGY${code.codeDtlNo}" class="paymentCheckbox">
															<label for="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NGY${code.codeDtlNo}Checkbox">${code.codeDtlName}</label>
														</span>
													</li>
													</c:if>
													</c:forEach>
												</ul>
											</td>
										</tr>
										<tr>
											<th scope="row">보조결제수단</th>
											<td class="input">
												<ul class="ip-box-list">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="chkMainPayment06" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NGN" class="paymentCheckboxAll">
															<label for="chkMainPayment06">전체</label>
														</span>
													</li>
													<c:forEach items="${pymntMeansCode}" var="code" varStatus="status">
													<c:if test="${code.addInfo1 eq 'S'}">
													<input type="hidden" name="siteNo">
													<input type="hidden" name="memberTypeCode" value="${CommonCode.MEMBER_TYPE_MEMBERSHIP}">
													<input type="hidden" name="empYn" value="N">
													<input type="hidden" name="orderType" value="G">
													<input type="hidden" name="mainPymntMeansYn" value="N">
													<input type="hidden" name="pymntMeansCode" value="${code.codeDtlNo}">
													<input id="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NGN${code.codeDtlNo}" type="hidden" name="useYn" value="N" data-group="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NGNHidden" class="paymentUseHidden">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NGN${code.codeDtlNo}Checkbox" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NGNCheckbox" data-id="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NGN${code.codeDtlNo}" class="paymentCheckbox">
															<label for="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NGN${code.codeDtlNo}Checkbox">${code.codeDtlName}</label>
														</span>
													</li>
													</c:if>
													</c:forEach>
												</ul>
											</td>
										</tr>
										<tr>
											<th scope="rowgroup" rowspan="2">예약주문</th>
											<th scope="row">주결제수단</th>
											<td class="input">
												<ul class="ip-box-list">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="chkMainPayment07" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NRY" class="paymentCheckboxAll">
															<label for="chkMainPayment07">전체</label>
														</span>
													</li>
													<c:forEach items="${pymntMeansCode}" var="code" varStatus="status">
													<c:if test="${code.addInfo1 eq 'M'}">
													<input type="hidden" name="siteNo">
													<input type="hidden" name="memberTypeCode" value="${CommonCode.MEMBER_TYPE_MEMBERSHIP}">
													<input type="hidden" name="empYn" value="N">
													<input type="hidden" name="orderType" value="R">
													<input type="hidden" name="mainPymntMeansYn" value="Y">
													<input type="hidden" name="pymntMeansCode" value="${code.codeDtlNo}">
													<input id="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NRY${code.codeDtlNo}" type="hidden" name="useYn" value="N" data-group="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NRYHidden" class="paymentUseHidden">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NRY${code.codeDtlNo}Checkbox" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NRYCheckbox" data-id="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NRY${code.codeDtlNo}" class="paymentCheckbox">
															<label for="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NRY${code.codeDtlNo}Checkbox">${code.codeDtlName}</label>
														</span>
													</li>
													</c:if>
													</c:forEach>
												</ul>
											</td>
										</tr>
										<tr>
											<th scope="row">보조결제수단</th>
											<td class="input">
												<ul class="ip-box-list">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="chkMainPayment08" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NRN" class="paymentCheckboxAll">
															<label for="chkMainPayment08">전체</label>
														</span>
													</li>
													<c:forEach items="${pymntMeansCode}" var="code" varStatus="status">
													<c:if test="${code.addInfo1 eq 'S'}">
													<input type="hidden" name="siteNo">
													<input type="hidden" name="memberTypeCode" value="${CommonCode.MEMBER_TYPE_MEMBERSHIP}">
													<input type="hidden" name="empYn" value="N">
													<input type="hidden" name="orderType" value="R">
													<input type="hidden" name="mainPymntMeansYn" value="N">
													<input type="hidden" name="pymntMeansCode" value="${code.codeDtlNo}">
													<input id="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NRN${code.codeDtlNo}" type="hidden" name="useYn" value="N" data-group="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NRNHidden" class="paymentUseHidden">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NRN${code.codeDtlNo}Checkbox" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NRNCheckbox" data-id="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NRN${code.codeDtlNo}" class="paymentCheckbox">
															<label for="${CommonCode.MEMBER_TYPE_MEMBERSHIP}NRN${code.codeDtlNo}Checkbox">${code.codeDtlName}</label>
														</span>
													</li>
													</c:if>
													</c:forEach>
												</ul>
											</td>
										</tr>
									</tbody>
								</table>
								<!-- E : tbl-row -->

								<!-- S : tbl-desc-wrap -->
								<div class="tbl-desc-wrap">
									<ol class="tbl-desc-list">
										<li>* 예약주문의 경우 통함멤버십 회원만 가능, 임직원 회원 및 온라인간편회원, 비회원은 불가</li>
									</ol>
								</div>
								<!-- E : tbl-desc-wrap -->

								<!-- S : content-header -->
								<div class="content-header">
									<div class="fl">
										<h3 class="content-title">비회원</h3>
									</div>
								</div>
								<!-- E : content-header -->

								<!-- S : tbl-row -->
								<table class="tbl-row">
									<caption>비회원</caption>
									<colgroup>
										<col style="width: 12%;">
										<col style="width: 16%;">
										<col>
									</colgroup>
									<tbody>
										<tr>
											<th scope="rowgroup" rowspan="2">일반주문</th>
											<th scope="row">주결제수단</th>
											<td class="input">
												<ul class="ip-box-list">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="chkMainPayment09" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_NONMEMBER}NGY" class="paymentCheckboxAll">
															<label for="chkMainPayment09">전체</label>
														</span>
													</li>
													<c:forEach items="${pymntMeansCode}" var="code" varStatus="status">
													<c:if test="${code.addInfo1 eq 'M'}">
													<input type="hidden" name="siteNo">
													<input type="hidden" name="memberTypeCode" value="${CommonCode.MEMBER_TYPE_NONMEMBER}">
													<input type="hidden" name="empYn" value="N">
													<input type="hidden" name="orderType" value="G">
													<input type="hidden" name="mainPymntMeansYn" value="Y">
													<input type="hidden" name="pymntMeansCode" value="${code.codeDtlNo}">
													<input id="${CommonCode.MEMBER_TYPE_NONMEMBER}NGY${code.codeDtlNo}" type="hidden" name="useYn" value="N" data-group="${CommonCode.MEMBER_TYPE_NONMEMBER}NGYHidden" class="paymentUseHidden">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="${CommonCode.MEMBER_TYPE_NONMEMBER}NGY${code.codeDtlNo}Checkbox" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_NONMEMBER}NGYCheckbox" data-id="${CommonCode.MEMBER_TYPE_NONMEMBER}NGY${code.codeDtlNo}" class="paymentCheckbox">
															<label for="${CommonCode.MEMBER_TYPE_NONMEMBER}NGY${code.codeDtlNo}Checkbox">${code.codeDtlName}</label>
														</span>
													</li>
													</c:if>
													</c:forEach>
												</ul>
											</td>
										</tr>
										<tr>
											<th scope="row">보조결제수단</th>
											<td class="input">
												<ul class="ip-box-list">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="chkMainPayment10" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_NONMEMBER}NGN" class="paymentCheckboxAll">
															<label for="chkMainPayment10">전체</label>
														</span>
													</li>
													<c:forEach items="${pymntMeansCode}" var="code" varStatus="status">
													<c:if test="${code.addInfo1 eq 'S' && code.codeDtlNo ne '10007' && code.codeDtlNo ne '10008'}">
													<input type="hidden" name="siteNo">
													<input type="hidden" name="memberTypeCode" value="${CommonCode.MEMBER_TYPE_NONMEMBER}">
													<input type="hidden" name="empYn" value="N">
													<input type="hidden" name="orderType" value="G">
													<input type="hidden" name="mainPymntMeansYn" value="N">
													<input type="hidden" name="pymntMeansCode" value="${code.codeDtlNo}">
													<input id="${CommonCode.MEMBER_TYPE_NONMEMBER}NGN${code.codeDtlNo}" type="hidden" name="useYn" value="N" data-group="${CommonCode.MEMBER_TYPE_NONMEMBER}NGNHidden" class="paymentUseHidden">
													<li>
														<span class="ui-chk"> <!-- DESC : input id / label for 동일하게 맞춰주세요 -->
															<input id="${CommonCode.MEMBER_TYPE_NONMEMBER}NGN${code.codeDtlNo}Checkbox" type="checkbox" data-group="${CommonCode.MEMBER_TYPE_NONMEMBER}NGNCheckbox" data-id="${CommonCode.MEMBER_TYPE_NONMEMBER}NGN${code.codeDtlNo}" class="paymentCheckbox">
															<label for="${CommonCode.MEMBER_TYPE_NONMEMBER}NGN${code.codeDtlNo}Checkbox">${code.codeDtlName}</label>
														</span>
													</li>
													</c:if>
													</c:forEach>
												</ul>
											</td>
										</tr>
									</tbody>
								</table>
								<!-- E : tbl-row -->

								<!-- S : tbl-row -->
								<table class="tbl-row">
									<caption>결제수단 작성 정보</caption>
									<colgroup>
										<col style="width: 130px;">
										<col>
										<col style="width: 130px;">
										<col>
									</colgroup>
									<tbody>
										<tr>
											<th scope="row">작성자</th>
											<td id="paymentRgster"></td>
											<th scope="row">작성일시</th>
											<td id="paymentRgstDtm"></td>
										</tr>
										<tr>
											<th scope="row">수정자</th>
											<td id="pyamentModer"></td>
											<th scope="row">수정일시</th>
											<td id="paymentModDtm"></td>
										</tr>
									</tbody>
								</table>
								<!-- E : tbl-row -->

								<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
								<!-- S : content-bottom -->
								<div class="content-bottom">
									<a href="#" class="btn-lg btn-save" id="paymentSaveBtn">저장</a>
								</div>
								</form>
								<!-- E : content-bottom -->
								<!-- E : 20190114 수정 -->
							</div>
							<!-- E:tab_content -->
							<!-- S:tab_content -->
							<div id="tabContent4" class="tab-content">
								<!-- S : content-header -->
								<form id="guideForm" name="guideForm">
								<input type="hidden" name="siteNo">
								<div class="content-header">
									<div class="fl">
										<h3 class="content-title">정책</h3>
										<!-- S : opt-group -->
										<span class="opt-group">
											<select class="ui-sel" title="정책 선택" name="dlvyGuideBgnCode" id="dlvyGuideBgnCode">
												<c:forEach items="${dlvyGuideBgnCode}" var="code" varStatus="status">
												<option value="${code.codeDtlNo}">${code.codeDtlName}</option>
												</c:forEach>
											</select>
										</span>
										<!-- E : opt-group -->
									</div>
									<div class="fr"></div>
								</div>
								<!-- E : content-header -->

								<!-- S : ibsheet-wrap -->
								<div class="ibsheet-wrap">
									<textarea name="dlvyGuideInfo" id="dlvyGuideInfo" rows="10" cols="80"></textarea>
								</div>
								<!-- E : ibsheet-wrap -->

								<!-- S : tbl-row -->
								<table class="tbl-row">
									<caption>배송/클레임 정책 작성 정보</caption>
									<colgroup>
										<col style="width: 130px;">
										<col>
										<col style="width: 130px;">
										<col>
									</colgroup>
									<tbody>
										<tr>
											<th scope="row">작성자</th>
											<td id="guideRgster"></td>
											<th scope="row">작성일시</th>
											<td id="guideRgstDtm"></td>
										</tr>
										<tr>
											<th scope="row">수정자</th>
											<td id="guideModer"></td>
											<th scope="row">수정일시</th>
											<td id="guideModDtm"></td>
										</tr>
									</tbody>
								</table>
								</form>
								<!-- E : tbl-row -->

								<!-- S : content-bottom -->
								<div class="content-bottom">
									<!-- S : 20190114 수정 // 버튼 가이드 변경으로 마크업 수정 -->
									<a href="#tabGuide" class="btn-lg btn-save" id="guideSaveBtn">저장</a>
									<!-- E : 20190114 수정 -->
								</div>
								<!-- E : content-bottom -->
							</div>
							<!-- E:tab_content -->
						</div>
						<!-- E : tab-wrap -->
					</div>
				</div>
				<!-- E : row-wrap -->
			</div>
		</div>
	</div>
</div>
<!-- E : container -->

<script src="/static/common/js/biz/system/abcmart.system.site.js<%=_DP_REV%>">
	
</script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>