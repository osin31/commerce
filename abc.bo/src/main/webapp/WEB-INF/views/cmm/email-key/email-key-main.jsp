<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/header.jsp"%>
<%@include file="/WEB-INF/views/common/menu.jsp"%>

		<script type="text/javascript">
			$(document).ready(function(){
				abc.biz.cmm.msgtemplate.initEmailKeySheet();

				$("a[name^=perToday]").click(function(){
					abc.date.days(this);
				});


				$("a[name^=perWeek]").click(function(){
					abc.date.week(this);
				});


				$("a[name^=per1Month]").click(function(){
					abc.date.month(this);
				});


				$("a[name^=perYear]").click(function(){
					abc.date.year(this);
				});

				$("a[name^=per1Month]").trigger("click");

				$("#searchDateKey").change(function(){
					if(abc.text.isBlank($("#searchDateKey option:selected").val())) {
						$("#fromDate").val("");
						$("#toDate").val("");
					}
				});

				// 목록 조회
				$("#searchBtn").click(function(f) {
					abc.biz.cmm.msgtemplate.doActionEmailKey("search");
				});

				$("#pageCount").change(function(f) {
					abc.biz.cmm.msgtemplate.doActionEmailKey("search");
				});

				// 초기화
				$("#resetBtn").click(function(f) {
					$.form("#frmSearch").reset();
					$("a[name^=per1Month]").trigger("click");
				});

				// 문자템플릿 등록 폼 호출
				$("#rgstBtn").click(function(f) {
					abc.open.popup({
						url 	:	"/cmm/msg/email-key/read-detail-pop",
						winname :	"emailKeypop",
						method	: 	"get",
						title 	:	"emailKeyInfo",
						width 	:	1200,
						height	:	900,
						params	: 	{}
					});
				});
				
				// enter 검색
				$(".search-wrap")
				.find(".ui-input, .js-ui-cal")
				.filter("input[type=text]:not([readonly]):not([disabled])").on("keypress", function(e) {
					if(e.keyCode === 13) {
						$("#searchBtn").trigger("click");
				    }
				});
				
				$("#searchBtn").trigger("click");

			});

			// 상담스크립트 상세 페이지 이동
			function emailKeySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
				if ( Row != 0) {
					if ( emailKeySheet.ColSaveName(Col) == "emailTitleText" && Value != "" ) {
						abc.open.popup({
							url 	:	"/cmm/msg/email-key/read-detail-pop",
							winname :	"emailKeypop",
							method	: 	"get",
							title 	:	"emailKeyInfo",
							width 	:	1200,
							height	:	900,
							params	: 	{emailTmplSeq : emailKeySheet.GetCellValue(Row, "emailTmplSeq")}
						});
					} 
				}
			}
		</script>

		<!-- S : container -->
		<div class="container">
			<div class="content-box">
				<!-- S : page-header -->
				<div class="page-header">
					<div class="fl">
						<h2 class="page-title">광고 이메일 관리</h2>
						<!-- DESC : 즐겨찾기 등록시 btn-favorites영역 active 클래스 추가 -->
						<button type="button" class="btn-favorites"><span class="ico ico-star"><span class="offscreen">즐겨찾기 등록</span></span></button>
					</div>
					<div class="fr">
						<div class="navi-wrap">
							<ul class="navi">
								<li class="home"><a href="#"><i class="ico ico-home"><span class="offscreen">홈</span></i></a></li>
								<li>프로모션</li>
								<li>메시징 관리</li>
								<li>광고 이메일 관리</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- E : page-header -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">광고 이메일 검색</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : search-wrap -->
				<form id="frmSearch" name="frmSearch">
				<div class="search-wrap">
					<div class="search-inner">
						<table class="tbl-search">
							<caption>광고 이메일 검색</caption>
							<colgroup>
								<col style="width: 120px;">
								<col>
								<col style="width: 79px">
								<col style="width: 120px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">사이트</th>
									<td class="input">
										<ul class="ip-box-list">
											<li>
												<span class="ui-rdo">
													<input id="chkSite00000" name="siteNo" class="ip_chk" type="radio" value="" checked>
													<label for="chkSite00000" class="ip_chk_lb">전체</label>
												</span>
											</li>
									<c:forEach var="site" items="${siteList}">
											<li>
												<span class="ui-rdo">
													<input id="chkSite${site.siteNo}" name="siteNo" class="ip_chk" type="radio" value="${site.siteNo}">
													<label for="chkSite${site.siteNo}" class="ip_chk_lb">${site.siteName}</label>
												</span>
											</li>
									</c:forEach>
										</ul>
									</td>
									<td></td>
									<th scope="row">수정자 ID</th>
									<td class="input">
										<input type="text" id="adminLoginId" name="adminLoginId" class="ui-input" title="최종수정자아이디 입력">
									</td>
								</tr>
								<tr>
									<th scope="row">메일제목</th>
									<td class="input">
										<input type="text" id="emailTitleText" name="emailTitleText" class="ui-input" title="메일제목 입력">
									</td>
									<td></td>
									<!-- <th scope="row">메일키</th>
									<td class="input">
										<input type="text" id="emailKeyText" name="emailKeyText" class="ui-input" title="메일키 입력">
									</td> -->
								</tr>
								<tr>
									<th scope="row">기간검색</th>
									<td class="input" colspan="4">
										<!-- S : term-date-wrap -->
										<span class="term-date-wrap">
											<select id="searchDateKey" name="searchDateKey" class="ui-sel" title="기간 선택">
												<option value="sendDtm">발송일</option>
												<option value="modDtm">수정일</option>
											</select>
											<span class="date-box">
												<input type="text" id="fromDate" name="fromDate" data-role="datepicker" class="ui-cal js-ui-cal" title="시작일 선택">
											</span>
											<span class="text">~</span>
											<span class="date-box">
												<input type="text" id="toDate" name="toDate" data-role="datepicker" class="ui-cal js-ui-cal" title="종료일 선택">
											</span>
											<span class="btn-group">
												<a href="javascript:void(0)" name="perToday" class="btn-sm btn-func">오늘</a>
												<a href="javascript:void(0)" name="perWeek" class="btn-sm btn-func">일주일</a>
												<a href="javascript:void(0)" name="per1Month" class="btn-sm btn-func">한달</a>
												<a href="javascript:void(0)" name="perYear" class="btn-sm btn-func">1년</a>
											</span>
										</span>
										<!-- E : term-date-wrap -->
									</td>
								</tr>
							</tbody>
						</table>
						<div class="confirm-box">
							<div class="fl">
								<a href="javascript:void(0)" id="resetBtn" class="btn-sm btn-func"><i class="ico ico-refresh"></i>초기화</a>
							</div>
							<div class="fr">
								<a href="javascript:void(0)" id="searchBtn" class="btn-normal btn-func">검색</a>
							</div>
						</div>
					</div>
					<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
				</div>
				</form>
				<!-- E : search-wrap -->

				<!-- S : content-header -->
				<div class="content-header">
					<div class="fl">
						<h3 class="content-title">광고 이메일 목록</h3>
					</div>
				</div>
				<!-- E : content-header -->

				<!-- S : tbl-controller -->
				<div class="tbl-controller">
					<div class="fl">
						<span class="opt-group">
							<label class="title" for="selTermsModule">목록개수</label>
							<select name="pageCount" id="pageCount" class="ui-sel" id="selTermsModule">
								<%@include file="/WEB-INF/views/common/pageSelect.jsp"%>
							</select>
						</span>
					</div>
					<div class="fr">
						<a href="javascript:void(0)" id="rgstBtn" class="btn-sm btn-link">등록</a>
					</div>
				</div>
				<!-- E : tbl-controller -->

				<!-- S : ibsheet-wrap -->
				<div class="ibsheet-wrap">
					<div id="emailKeyGrid" style="width:100%; height:429px;"></div>
				</div>
				<!-- E : ibsheet-wrap -->
			</div>
		</div>
		<!-- E : container -->

<script src="/static/common/js/biz/cmm/abcmart.cmm.msgtemplate.js<%=_DP_REV%>">
</script>

<%@include file="/WEB-INF/views/common/footer.jsp"%>