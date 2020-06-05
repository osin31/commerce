<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


	<!-- S : content-header -->
	<div class="content-header">
		<div class="fl">
			<h3 class="content-title">입점사 상품 정보제공고시 설정</h3>
		</div>
	</div>
	<!-- E : content-header -->

	<!-- S : tbl-row -->
		<table class="tbl-row">
			<caption>기본정산 수수료 정보</caption>
			<colgroup>
				<col style="width: 150px">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">
						<span>A/S 책임자와 전화번호</span>
					</th>
					<td class="input" colspan="3">
						<!-- S : ip-text-box -->
						<input type="text" id="asMngrText" name="asMngrText" class="ui-input" maxlength="100" title="AS 담당자와 전화번호 입력"  value='<c:out value="${vendorInfo.asMngrText}"/>'>
						<!-- E : ip-text-box -->
					</td>
				</tr>
			</tbody>
		</table>


	<!-- E : tbl-row -->
