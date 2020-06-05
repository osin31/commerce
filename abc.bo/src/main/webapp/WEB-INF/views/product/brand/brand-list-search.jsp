<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- S : search-wrap -->
<form id="search-form">
	<div class="search-wrap">
		<div class="search-inner">
			<!-- S : 20190212 수정 // 기획 변경으로 브랜드 검색 영역 수정 -->
			<table class="tbl-search">
				<caption>브랜드 검색 테이블이며 브랜드 구분, 브랜드명 (국문 / 영문), 온라인 코드, 브랜드 ID (ERP), 사용여부에 대해 검색합니다.</caption>
				<colgroup>
					<col style="width:15%;"/>
					<col/>
					<col style="width:79px;"/>
					<col style="width:15%;"/>
					<col/>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">브랜드명 (국문 / 영문)</th>
						<td class="input">
							<input type="text" class="ui-input" title="브랜드명 입력" name="brandName"/>
						</td>
						<td></td>
						<th scope="row">브랜드 ID (ERP)</th>
						<td class="input">
							<input type="text" class="ui-input" title="브랜드 id 입력" name="brandNo"/>
						</td>
					</tr>
					<tr>
						<th scope="row">사이트 구분</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<input id="site-all" name="siteNo" type="radio" value="" checked/>
										<label for="site-all">전체</label>
									</span>
								</li>
								<c:forEach items="${searchConditionSiteList }" var="item">
									<li>
										<span class="ui-rdo">
											<input id="site-no-${item.siteNo }" name="siteNo" type="radio" value="${item.siteNo }"/>
											<label for="site-no-${item.siteNo }">${item.siteName }</label>
										</span>
									</li>
								</c:forEach>
								<%--
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="site-mall" name="siteGubun" type="radio" value="MALL"/>
										<label for="site-mall">통합몰</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="site-ots" name="siteGubun" type="radio" value="OTS"/>
										<label for="site-ots">OTS</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<!-- DESC : input id / label for 동일하게 맞춰주세요 -->
										<input id="site-mall-ots" name="siteGubun" type="radio" value="MALL-OTS"/>
										<label for="site-mall-ots">통합몰+OTS</label>
									</span>
								</li>
								--%>
							</ul>
							<!-- E : ip-box-list -->
						</td>
						<td></td>
						<th scope="row">사용여부</th>
						<td class="input">
							<!-- S : ip-box-list -->
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<input id="use-yn-all" name="useYn" type="radio" value="" checked/>
										<label for="use-yn-all">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<input id="use-yn-y" name="useYn" type="radio" value="Y"/>
										<label for="use-yn-y">예</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<input id="use-yn-n" name="useYn" type="radio" value="N"/>
										<label for="use-yn-n">아니오</label>
									</span>
								</li>
							</ul>
							<!-- E : ip-box-list -->
						</td>
					</tr>
					<!--
					<tr>
						<th scope="row">온라인 코드</th>
						<td class="input">
							<input type="text" class="ui-input" title="온라인 코드 입력" name="brandGroupNoText"/>
						</td>
						<td></td>
						<th scope="row">승인여부</th>
						<td class="input">
							!-- S : ip-box-list --
							<ul class="ip-box-list">
								<li>
									<span class="ui-rdo">
										<input id="complateAll" name="complateYn" type="radio" value="" checked/>
										<label for="complateAll">전체</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<input id="complateY" name="complateYn" type="radio" value="Y"/>
										<label for="complateY">예</label>
									</span>
								</li>
								<li>
									<span class="ui-rdo">
										<input id="complateN" name="complateYn" type="radio" value="N"/>
										<label for="complateN">아니오</label>
									</span>
								</li>
							</ul>
							!-- E : ip-box-list --
						</td>
						!-- DESC : 20190314 삭제 // 베스트여부 삭제 --
					</tr>
					-->
				</tbody>
			</table>
			<!-- E : 20190212 수정 // 기획 변경으로 브랜드 검색 영역 수정 -->

			<div class="confirm-box">
				<div class="fl">
					<a href="#" class="btn-sm btn-func" id="clearBtn"><i class="ico ico-refresh"></i>초기화</a>
				</div>
				<div class="fr">
					<a href="#" class="btn-normal btn-func" id="search">검색</a>
				</div>
			</div>
		</div>
		<button type="button" class="btn-search-toggle"><span class="offscreen">닫기</span></button>
	</div>
</form>
<!-- E : search-wrap -->
