<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {		
		abc.biz.order.vendor.memo.init('${orderNo}');
	});
	
</script>
<!-- S:tab_content -->
<div id="tabContent3" class="tab-content">
	<!-- S : msg-full-content -->
	<div class="msg-full-content">
		<!-- S : content-header -->
		<div class="content-header">
			<div class="fl">
				<h3 class="content-title">관리자 메모</h3>
				
			</div>
		</div>
		
		<!-- S : manager-msg-wrap -->
		<form id="memoForm" name="memoForm" method="post" onsubmit="return false;">
			<input type="hidden" name="orderNo" value="${orderNo}">
			<input type="hidden" name="orderMemoSeq" value="0">
			<input type="hidden" name="orderMemoGbnCode" value="10000">
			<div class="manager-msg-wrap">
				<div class="msg-box">
					<textarea title="관리자 메모 입력" name="memoText"></textarea>
				</div>
				<button type="button" id="btnMemoRegist" class="btn-normal btn-save">저장</button>
			</div>
		</form>
		<!-- E : manager-msg-wrap -->
		<!-- S : sort-radio-wrap -->
		<div class="sort-radio-wrap">
			<ul class="sort-radio-list">
				<li>
					<input type="radio" id="viewAll" name="sortRadio" checked>
					<label for="viewAll">
						<span class="opt-name">전체</span><span class="cnt" id="totalMemoCnt"></span>
					</label>
				</li>
				<li>
					<input type="radio" id="viewOrder" name="sortRadio">
					<label for="viewOrder">
						<span class="opt-name">주문정보</span><span class="cnt" id="orderMemoCnt"></span>
					</label>
				</li>
				<li>
					<input type="radio" id="viewClaim" name="sortRadio">
					<label for="viewClaim">
						<span class="opt-name">클레임</span><span class="cnt" id="claimMemoCnt"></span>
					</label>
				</li>
			</ul>
		</div>
		<!-- E : sort-radio-wrap -->
		<!-- S : msg-list-wrap -->
		<div class="msg-list-wrap">
			<ul class="msg-list" id="memoList">
			</ul>
		</div>
		<!-- E : msg-list-wrap -->
	</div>
	<!-- E : msg-full-content -->
</div>
<!-- E:tab_content -->
