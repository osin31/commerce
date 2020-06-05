<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">기본정산 수수료 정보</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : tbl-row -->
		<table class="tbl-row">
			<caption>기본정산 수수료 정보</caption>
			<colgroup>
				<col style="width: 150px">
				<col>
				<col style="width: 150px">
				<col>
			</colgroup>
			<tbody>
			<c:if test="${vendorInfo.status  != 'U'}">
				<tr>
					<th scope="row">
						<span class="th-required">기본정산 수수료율</span>
					</th>
					<td class="input" colspan="3">
						<!-- S : ip-text-box -->
						<span class="ip-text-box">
							<input type="hidden" name="defaultCommissionList.vndrDfltCmsnSeq" value="1" />
							<input type="text" id="dfltCmsnRate" name="defaultCommissionList.dfltCmsnRate" class="ui-input num-unit100 text-right" maxlength="3" title="기본정산 수수료율 입력">
							<span class="text">%</span>
							<span class="text">* 1~100 까지의 숫자만 입력가능. 적용 시작일은 등록일의 익일 반영됩니다.</span>
						</span>
						<!-- E : ip-text-box -->
					</td>
				</tr>
			</c:if>
			<c:if test="${vendorInfo.status  == 'U'}">
				<tr>
					<th scope="row">
						<span>적용중인 수수료율</span>
					</th>
					<td class="input">
						<!-- S : ip-text-box -->
						<span class="ip-text-box">
							<input type="hidden" name="defaultCommissionList.vndrDfltCmsnSeq" value="${defaultCommissionList[0].vndrDfltCmsnSeq}" />
							<input type="hidden" name="defaultCommissionList.applyStartYmd" value='<fmt:formatDate value="${defaultCommissionList[0].applyStartYmd}" pattern="${Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS}"/>'/>
							<input type="hidden" name="defaultCommissionList.dfltCmsnRate" value="${defaultCommissionList[0].dfltCmsnRate}" />
							<span class="text">${defaultCommissionList[0].dfltCmsnRate}</span>
							<span class="text">%</span>
						</span>
						<!-- E : ip-text-box -->
					</td>
					<th scope="row">
						<span>적용 시작일</span>
					</th>
					<td class="input">
						<!-- S : ip-text-box -->
						<span class="ip-text-box">
							<span class="text"><fmt:formatDate value="${defaultCommissionList[0].applyStartYmd}" pattern="${Const.DEFAULT_DATE_PATTERN}"/></span>
						</span>
						<!-- E : ip-text-box -->
					</td>
				</tr>
				<tr>
					<th scope="row">
						<span>변경할 수수료율</span>
					</th>
					<td class="input">
						<!-- S : ip-text-box -->
						<span class="ip-text-box">
							<input type="hidden" name="defaultCommissionList.vndrDfltCmsnSeq" value="${defaultCommissionList[1].vndrDfltCmsnSeq}"/>
							<input type="hidden" name="defaultCommissionList.applyStartYmd" value='<fmt:formatDate value="${defaultCommissionList[1].applyStartYmd}" pattern="${Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS}"/>'/>
							<input type="text" id="dfltCmsnRate" name="defaultCommissionList.dfltCmsnRate" value="${defaultCommissionList[1].dfltCmsnRate}" class="ui-input" title="기본정산 수수료율 입력" placeholder="1~100 까지의 숫자만 입력">
							<span class="text">%</span>
						</span>
						<!-- E : ip-text-box -->
					</td>
					<th scope="row">
						<span>적용 시작일</span>
					</th>
					<td class="input">
						<!-- S : ip-text-box -->
						<span class="ip-text-box">
							<c:if test="${empty defaultCommissionList[1]}">
								<span class="text">*적용 시작일은 등록일의 익일 반영됩니다.</span>
							</c:if>
							<c:if test="${not empty defaultCommissionList[1]}">
								<span class="text"><fmt:formatDate value="${defaultCommissionList[1].applyStartYmd}" pattern="${Const.DEFAULT_DATE_PATTERN}"/></span>
							</c:if>
						</span>
						<!-- E : ip-text-box -->
					</td>
				</tr>
			</c:if>
			</tbody>
		</table>


	<!-- E : tbl-row -->
