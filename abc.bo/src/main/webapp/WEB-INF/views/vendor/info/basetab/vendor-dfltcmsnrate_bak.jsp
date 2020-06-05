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
		</colgroup>
		<tbody>
			<tr>
				<th scope="row">
					<span class="th-required">기본정산 수수료율</span>
				</th>
				<td class="input">
					<!-- S : ip-text-box -->
					<span class="ip-text-box">
						<input type="text" id="dfltCmsnRate" name="dfltCmsnRate" class="ui-input" title="기본정산 수수료율 입력" placeholder="1~100 까지의 숫자만 입력" value='<c:out value="${vendorInfo.dfltCmsnRate}"/>'>
						<span class="text">%</span>
					</span>
					<!-- E : ip-text-box -->
				</td>
			</tr>
		</tbody>
	</table>
	<!-- E : tbl-row -->
