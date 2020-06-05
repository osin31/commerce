<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/subHeader.jsp" %>

<body class="window-body">
	<div class="window-wrap">
		<div class="window-title">
			<h1>전시코너 관리</h1>
		</div>
		<form name="frm">
		<input type="hidden" name="dispTmplCornerSeq" value="${corner.dispTmplCornerSeq }" />
		<input type="hidden" name="dispTmplNo" value="${corner.dispTmplNo }" />
		
		<div class="window-content">
			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">전시코너 기본정보</h3>
				</div>
			</div>
			<!-- E : content-header -->
			
			<!-- S : tbl-row -->
			<table class="tbl-row">
				<caption>전시코너 기본정보</caption>
				<colgroup>
					<col style="width: 150px;">
					<col>
					<col style="width: 150px;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">코너번호</th>
						<td>${corner.dispTmplCornerSeq }</td>
						<th scope="row">
							<span class="th-required">코너명</span>
						</th>
						<td class="input">
							<!-- TODO : 입력 글자수 변경시 placeholder 수정 필요 -->
							<input type="text" class="ui-input" title="코너명 입력" placeholder="20자 이내로 입력" name="cornerName" id="cornerName"  value="${corner.cornerName}" maxlength="20">
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">사용여부</span>
						</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="useYn" id="radioUse01" value="Y" ${corner.useYn eq 'Y' or empty corner.useYn ? 'checked' : ''} />
										<label for="radioUse01">사용</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="useYn" id="radioUse02" value="N" ${corner.useYn eq 'N' ? 'checked' : ''}>
										<label for="radioUse02">사용안함</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
						<th scope="row">
							<span class="th-required">전시코너명 노출 유형</span>
						</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="cornerNameDispType" id="radioDisplayType01" value="T" ${corner.cornerNameDispType eq 'T' or empty corner.cornerNameDispType ? 'checked' : ''} default>
										<label for="radioDisplayType01">텍스트</label>
									</span>
								</li>
								<%-- <li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="cornerNameDispType" id="radioDisplayType02" value="I" ${corner.cornerNameDispType eq 'I' ? 'checked' : ''}>
										<label for="radioDisplayType02">이미지</label>
									</span>
								</li> --%>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input type="radio" name="cornerNameDispType" id="radioDisplayType03" value="N" ${corner.cornerNameDispType eq 'N' ? 'checked' : ''}>
										<label for="radioDisplayType03">전시안함</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<tr>
						<th scope="row">
							<span class="th-required">코너설명</span>
						</th>
						<td class="input" colspan="3">
							<!-- TODO : 입력 글자수 변경시 placeholder 수정 필요 -->
							<input type="text" class="ui-input" title="코너 설명 입력" placeholder="30자 이내로 입력" name="noteText"  maxlength="30" value="${corner.noteText}"/>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- E : tbl-row -->

			<!-- S : content-header -->
			<div class="content-header">
				<div class="fl">
					<h3 class="content-title">전시코너 구성정보</h3>
				</div>
				<!-- S : 전시코너 기본정보 > 세트 사용여부 > 사용 선택시 노출되는 버튼영역 -->
				<div class="fr">
					<button type="button" class="btn-sm btn-del" id="del-corner-set">선택삭제</button>
					<button type="button" class="btn-sm btn-func" id="add-corner-set">세트추가</button>
				</div>
				<!-- E : 전시코너 기본정보 > 세트 사용여부 > 사용 선택시 노출되는 버튼영역 -->
			</div>
			<!-- E : content-header -->

			<!-- S : 20190222 수정 // ibsheet > table 변경 -->
			<!-- S : 전시코너 기본정보 > 세트 사용시 -->
			<!-- S : tbl-col -->
			<table class="tbl-col">
				<caption>전시코너 구성정보</caption>
				<colgroup>
					<col style="width: 7%">
					<col style="width: 15%">
					<col style="width: auto">
					<col style="width: 15%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col" class="only-chk">
							<span class="ui-chk">
								<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
								<input id="check-all-item" type="checkbox" name="chkConfigModule">
								<label for="check-all-item"></label>
							</span>
						</th>
						<th scope="col">노출순서</th>
						<th scope="col">콘텐츠 유형</th>
						<th scope="col">노출 콘텐츠 수</th>
					</tr>
				</thead>
				<tbody id="append-corner-set">
				<c:set var="setSize" value="${list.size() }" />
				<c:set var="firstSetIndex" value="" />
				<c:if test="${list ne null and setSize > 0 }">
					<c:forEach var="set" items="${list }" varStatus="status">
					<c:choose>
						<c:when test="${firstSetIndex ne set.sortSeq}">
							<c:set var="firstSetIndex" value="${set.sortSeq }" />	
							<tr>
								<td class="only-chk" rowspan="${setSize/corner.setCount}">
									<span class="ui-chk">
										<input id="chkConfig${status.index }" type="checkbox" name="chkConfigModule" class="check-item">
										<label for="chkConfig${status.index }"></label>
									</span>
								</td>
								<td class="input" rowspan="${setSize/corner.setCount}">
									<input type="number" name="sortSeq" class="ui-input text-center" title="노출 순서 입력" value="${set.sortSeq}" min="0">
								</td>
								<td class="text-center">
									<input type="hidden" name="contTypeCode" value="${set.contTypeCode}" />
									<input type="hidden" name="contTypeCodeName" value="${set.contTypeCodeName}" />
									${set.contTypeCodeName}
								</td>
								<!-- DESC : 비활성화시 input disabled 속성 추가 -->
								<td class="input"><input type="number" name="dispContCount" class="ui-input text-center set-name-count" title="노출 콘텐츠 수 입력" value="${set.dispContCount}" min="0" max="1"></td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td class="text-center">
									<input type="hidden" name="contTypeCode" value="${set.contTypeCode}" />
									<input type="hidden" name="contTypeCodeName" value="${set.contTypeCodeName}" />
									${set.contTypeCodeName}
									</td>
								<td class="input"><input type="number" name="dispContCount" class="ui-input text-center" title="노출 콘텐츠 수 입력" value="${set.dispContCount}" min="0"></td>
							</tr>
						</c:otherwise>
					</c:choose>
					</c:forEach>
				</c:if>
				</tbody>
				<script type="text/x-template" id="set-corner-tmpl">
				<tr>
					<!-- DESC : *** 묶어지는 행의 첫번째 행(tr) td.only-chk, 두번째 행(tr) td.input 영역에 rowspan="세트행개수" 속성추가
									예시)
									1. 묶어지는 세트 행 개수가 3일경우, 1행(tr) td.only-chk영역, td.input영역에 rowspan="3"을 추가
									2. 이어지는 2,3행(tr)에서는 td.only-chk, td.input영역 삭제
					-->
					<td class="only-chk" rowspan="${codeList.CONT_TYPE_CODE.size() }">
						<span class="ui-chk">
							<input id="chkConfig" type="checkbox" name="chkConfigModule" class="check-item">
							<label for="chkConfig"></label>
						</span>
					</td>
					<td class="input" rowspan="${codeList.CONT_TYPE_CODE.size() }">
						<input type="number" name="sortSeq" class="ui-input text-center" title="노출 순서 입력" value="1">
					</td>
					<td class="text-center contTypeCodeName">
						${codeList.CONT_TYPE_CODE.get(0).codeDtlName}
					</td>
					<!-- DESC : 비활성화시 input disabled 속성 추가 -->
					<td class="input">
						<input type="hidden" name="contTypeCode" value="${codeList.CONT_TYPE_CODE.get(0).codeDtlNo}" />
						<input type="hidden" name="contTypeCodeName" value="${codeList.CONT_TYPE_CODE.get(0).codeDtlName}" />
						<input type="number" name="dispContCount" class="ui-input text-center set-name-count" title="노출 콘텐츠 수 입력" value="0">
					</td>
				</tr>
				<c:forEach var="code" items="${codeList.CONT_TYPE_CODE}" varStatus="status">
				<c:if test="${status.index > 0 }">
				<tr>
					<td class="text-center contTypeCodeName">
						${code.codeDtlName }
					</td>
					<td class="input">
						<input type="hidden" name="contTypeCode" value="${code.codeDtlNo }" />
						<input type="hidden" name="contTypeCodeName" value="${code.codeDtlName}" />
						<input type="number" name="dispContCount" class="ui-input text-center" title="노출 콘텐츠 수 입력" value="0">
					</td>
				</tr>
				</c:if>
				</c:forEach>
				</script>
			</table>
			<!-- S : window-btn-group -->
			<div class="window-btn-group">
				<a href="javascript:void(0);" class="btn-normal btn-save" id="save-corner">저장</a>
			</div>
			<!-- E : window-btn-group -->
		</div>
		</form>
	</div>
	
	<script type="text/javascript" src="/static/common/js/biz/display/common/abcmart.display.common.js<%=_DP_REV%>"></script>
	<script type="text/javascript" src="/static/common/js/biz/display/abcmart.display.template.corner.pop.js<%=_DP_REV%>"></script>
</body>
</html>