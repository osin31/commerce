
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10004','인증번호확인','[ABC마트] 이메일 인증 인증번호 안내 드립니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- e : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">이메일 인증 인증번호 안내 드립니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				고객님의 소중한 개인정보보호를 위하여 이메일 인증을 진행 합니다. <br />
				아래 인증번호를 <span style="font-weight: bold;">인증번호란에 입력하고 ‘확인’ 버튼을 클릭하시면 인증이 완료</span>됩니다.
			</p>
			<div style="margin-top: 42px; font-size: 0;">
				<span style="display: inline-block; margin-top: 2px; font-size: 20px; font-weight: bold; vertical-align: top; letter-spacing: -2px">인증번호</span>
				<span style="display: inline-block; margin-left: 6px; font-size: 22px; font-weight: bold; vertical-align: top; letter-spacing: -1.1px; color: #ee1c25">${certificationNumber}</span>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-05003',null,'A',0,'SY00000013','2019-04-11 17:57:47','SY00000001','2019-09-09 16:22:51')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10011','장기미사용자 탈퇴유예','[ABC마트] 장기미사용 고객님, 개인정보 파기 전 로그인을 해주세요.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		 <tbody>
			<tr style="background-color: #000;">
			   <td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
				  <a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
					 <img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
				  </a>
			   </td>
			   <td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
				  <table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
					 <tbody>
						<tr>
						   <td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
						</tr>
					 </tbody>
				  </table>
			   </td>
			</tr>
		 </tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">장기미사용 고객님, 개인정보 파기 전 로그인을 해주세요</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님! <br />
				ABC마트 코리아에서 회원님께 개인정보 파기에 대한 안내 말씀 드립니다.<br /><br />

				정보통신 이용촉진 및 정보보호 등에 관한 법률에 의하여<br />
				ABC마트 코리아 온라인 쇼핑몰에 1년 이상 로그인 하지 않은 회원님의 개인정보를 파기해야 합니다.<br /><br />

				이를 원하지 않으실 경우 <span style="color: #ee1c25">${leaveSafeDateFormat}</span> 까지 A-RT 온라인 쇼핑물<span style="color: #1177df">(www.abcmart.co.kr)</span>에<br />
				1회 이상 로그인 해주시기 바랍니다.<br />
				시행 날짜 이후 서비스 이용을 위해선 새로 회원가입을 하셔야 합니다.
			</p>

			<!-- s : 개인정보 파기 개요 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">개인정보 파기 개요</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 151px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">자동 탈퇴 대상 회원</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">A-RT 온라인 쇼핑몰에 1년 이상 로그인하지 않은 회원</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">자동 탈퇴 시행 일자</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;"><span style="color: #ee1c25">${leaveDateFormat}</span></td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">개인 정보 파기 항목</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">회원가입 시 수집된 모든 정보</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">자동 소멸예정 포인트</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;"><span style="color: #ee1c25">${point}</span>P</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<ul style="margin: 11px 0 0; padding: 0;">
				<li style="overflow: hidden; margin: 0; padding: 0;">
					<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 10px 5px 0 0; background-color: #999; vertical-align: top;"></span>
					<p style="float: right; width: 671px; margin: 0; font-size: 12px; color: #666; letter-spacing: -1.2px; line-height: 22px; word-break: keep-all;">
						<span style="color: #ee1c25">적립된 포인트는 자동 소멸되며, 재가입시 포인트는 재 적립 되지 않습니다.</span> <br />
						관련법령 : [정보통신망 이용촉진 및 정보보호 등에 관한 법률] 제 29조 및 동법 시행령 제 16조
					</p>
				</li>
			</ul>
			<!-- e : 개인정보 파기 개요 -->

			<div style="margin-top: 45px; font-size: 0;">
				<a href="https://www.a-rt.com/login" target="_blank" style="display: inline-block; padding: 21px 69px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">로그인</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
				   <p style="margin:0;">
					  <span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
					  사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
					  대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
				   </p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-05007',null,'A',0,'SY00000011','2019-05-10 16:42:28','SY00000001','2019-09-09 16:35:46')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10011','입점업체 신규등록 관리자 비밀번호','입점업체 신규등록 관리자 비밀번호 안내','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- e : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">입점사로 등록 완료되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">안녕하세요, ${vndrName} 담당자님! <br />A-RT 입점사로 등록 완료되었습니다. <br />아래 계정 정보로 로그인 후 비밀번호 변경 부탁드립니다.</p>

			<!-- s : 입점사 계정 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">입점사 계정 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">입점사</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${vndrName}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">아이디</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${loginId}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">비밀번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pswdText}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 입점사 계정 정보 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${backOfficeUrl}" target="_blank" style="display: inline-block; padding: 21px 45px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">관리자 시스템</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EA-01002',null,'A',0,'SY00000013','2019-05-17 12:10:27','SY00000001','2019-09-09 15:56:02')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10011','비밀번호 초기화','비밀번호 초기화 안내','비밀번호가 초기화 되어 안내드립니다.&lt;br /&gt;
비밀번호 : ${pswdText}',null,'sy_002',null,'A',0,'SY20000013','2019-05-22 15:23:04','SY20000010','2019-06-11 18:07:36')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10011','회원 비밀번호 초기화','회원 비밀번호 초기화 안내','고객님의 비밀번호가 초기화되어 안내 드립니다.&lt;br /&gt;
비밀번호 : ${pswdText}',null,'mem_001',null,'A',0,'SY20000010','2019-06-11 18:02:07','SY20000010','2019-06-11 18:02:44')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10011','문의글 답변 (1:1 상담)','[A-RT] 고객님의 문의에 답변 드립니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- e : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">고객님의 문의에 답변 드립니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님!<br />
				고객님께서 문의주신 1:1 상담에 대한 답변을 드립니다.<br />
				A-RT는 언제나 고객님의 만족을 위해 최선을 다하겠습니다. 감사합니다.
			</p>

			<!-- s : 1:1 상담에 대한 답변 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">1:1 상담에 대한 답변</h3>
			<div style="margin-top: 15px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<div style="margin: 0; padding: 22px 50px 24px; background: url(${abcUrl}/static/images/email/icon_faq_q.png) 14px 22px no-repeat;">
					<p style="margin: 0; padding: 0; font-size: 15px; line-height: 24px; letter-spacing: -0.75px;">${counsel.inqryContText}</p>
				</div>
				<div style="margin: 0; padding: 30px 30px 39px 66px; background: #f8f8f8 url(${abcUrl}/static/images/email/icon_faq_a.png) 30px 30px no-repeat;">
					<p style="margin: 0; padding: 0; font-size: 15px; line-height: 24px; letter-spacing: -0.75px;">
						${counsel.aswrContText}
					</p>
					<span style="display: block; margin-top: 13px; padding: 0; font-size: 13px; color: #666;">${counsel.aswrDtm}</span>
				</div>
			</div>
			<ul style="margin: 11px 0 0; padding: 0;">
				<li style="overflow: hidden; margin: 0; padding: 0;">
					<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 10px 5px 0 0; background-color: #999; vertical-align: top;"></span>
					<p style="float: right; width: 671px; margin: 0; font-size: 12px; color: #666; letter-spacing: -0.6px; line-height: 22px; word-break: keep-all;">
						문의하신 사항에 대한 답변이 충분하신가요? 만일 충분한 답변이 되지 못했거나 추가로 질문할 내용이 있으시다면 <br /> 1:1상담 문의를 해주세요. 신속하게 처리해 드리겠습니다.
					</p>
				</li>
			</ul>
			<!-- e : 1:1 상담에 대한 답변 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${abcUrl}/board/member-counsel/read-inquiry-create-form" target="_blank" style="display: inline-block; padding: 21px 50px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">1:1 상담하기</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-04001',null,'A',0,'SY00000004','2019-07-01 16:19:39','SY00000001','2019-09-09 16:07:47')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10011','문의글 답변 (고객의 소리)','[A-RT] 고객님의 문의에 답변 드립니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- e : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">고객님의 문의에 답변 드립니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님! <br />
				고객님께서 문의주신 고객의 소리에 대한 답변을 드립니다.<br />
				A-RT는 언제나 고객님의 만족을 위해 최선을 다하겠습니다. 감사합니다.
			</p>

			<!-- s : 고객의 소리에 대한 답변 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">고객의 소리에 대한 답변</h3>
			<div style="margin-top: 15px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<div style="margin: 0; padding: 22px 50px 24px; background: url(${abcUrl}/static/images/email/icon_faq_q.png) 14px 22px no-repeat;">
					<p style="margin: 0; padding: 0; font-size: 15px; line-height: 24px; letter-spacing: -0.75px;">${counsel.inqryContText}</p>
				</div>
				<div style="margin: 0; padding: 30px 30px 39px 66px; background: #f8f8f8 url(${abcUrl}/static/images/email/icon_faq_a.png) 30px 30px no-repeat;">
					<p style="margin: 0; padding: 0; font-size: 15px; line-height: 24px; letter-spacing: -0.75px;">
						${counsel.aswrContText}
					</p>
					<span style="display: block; margin-top: 13px; padding: 0; font-size: 13px; color: #666;">${counsel.aswrDtm}</span>
				</div>
			</div>
			<ul style="margin: 11px 0 0; padding: 0;">
				<li style="overflow: hidden; margin: 0; padding: 0;">
					<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 10px 5px 0 0; background-color: #999; vertical-align: top;"></span>
					<p style="float: right; width: 671px; margin: 0; font-size: 12px; color: #666; letter-spacing: -0.6px; line-height: 22px; word-break: keep-all;">
						문의하신 사항에 대한 답변이 충분하신가요? 만일 충분한 답변이 되지 못했거나 추가로 질문할 내용이 있으시다면 <br /> 1:1상담 문의를 해주세요. 신속하게 처리해 드리겠습니다.
					</p>
				</li>
			</ul>
			<!-- e : 고객의 소리에 대한 답변 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${abcUrl}/board/member-counsel/read-inquiry-create-form" target="_blank" style="display: inline-block; padding: 21px 50px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">1:1 상담하기</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-04002',null,'A',0,'SY00000004','2019-07-01 17:49:28','SY00000001','2019-09-09 16:12:30')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10004','온라인 회원가입 축하','[ABC마트] 회원가입을 축하합니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- e : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">A-RT 회원이 되신 것을 환영합니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberInfo.memberName}님!<br />
				<span style="color: #ee1c25">A-RT 온라인회원 가입</span>을 진심으로 축하합니다.<br />
				앞으로 다양한 상품정보와 함께 만족스러운 쇼핑을 즐길 수 있도록 최선을 다하며,<br />
				항상 새로운 뉴스와 이벤트로 고객님의 알찬 쇼핑을 위해 열심히 노력하겠습니다. <br />
				감사합니다.
			</p>

			<!-- s : 회원가입 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">회원가입 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberInfo.memberName}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">아이디</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberInfo.loginId}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">가입일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${joinDtm}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 회원가입 정보 -->
 
			<div style="margin-top: 45px; font-size: 0;">
				<a href="${abcUrl}/mypage" target="_blank" style="display: inline-block; padding: 20px 53px 19px 54px; border: 1px solid #000; font-weight: bold; font-size: 15px; color: #000; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">마이페이지</a>
				<a href="${abcUrl}/member/member-change-login-page" target="_blank" style="display: inline-block; padding: 21px 61px 20px 62px; margin-left: 10px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">회원전환</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-05001',null,'A',0,'SY00000004','2019-07-03 11:09:58','SY00000001','2019-09-09 16:21:49')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10004','통합멤버쉽 회원가입 축하','[ABC마트] 회원가입을 축하합니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- e : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">A-RT 회원이 되신 것을 환영합니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberInfo.memberName}님!<br />
				<span style="color: #ee1c25">A-RT 통합 멤버십회원 가입</span>을 진심으로 축하합니다.<br />
				앞으로 다양한 상품정보와 함께 만족스러운 쇼핑을 즐길 수 있도록 최선을 다하며,<br />
				항상 새로운 뉴스와 이벤트로 고객님의 알찬 쇼핑을 위해 열심히 노력하겠습니다. <br />
				감사합니다.
			</p>

			<!-- s : 회원가입 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">회원가입 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberInfo.memberName}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">아이디</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberInfo.loginId}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">가입일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${joinDtm}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 회원가입 정보 -->

			<!-- s : 화원가입 환영 혜택 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">화원가입 환영 혜택</h3>
			<div style="margin-top: 15px; padding: 14px 158px 30px 0; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; font-size: 0;">
				<div style="display: inline-block; width: 131px; height: 63px; padding: 21px 27px 0 0; margin: 16px 16px 0 0; background: url(${abcUrl}/static/images/email/coupon_bg.png) 0 0 no-repeat; text-align: center; vertical-align: top;">
					<span style="font-size: 12px; letter-spacing: -1.2px">가입축하 할인쿠폰</span>
					<div style="font-size: 0; margin-top: 6px; padding: 0;">
						<span style="display: inline-block; font-size: 24px; font-weight: bold; color: #ee1c25; letter-spacing: -0.6px;">5,000</span><span style="display: inline-block; padding-left: 2px; font-size: 15px;">원</span>
					</div>
				</div>
				<div style="display: inline-block; width: 131px; height: 63px; padding: 21px 27px 0 0; margin: 16px 16px 0 0; background: url(${abcUrl}/static/images/email/coupon_bg.png) 0 0 no-repeat; text-align: center; vertical-align: top;">
					<span style="font-size: 12px; letter-spacing: -1.2px">현금처럼 사용가능</span>
					<div style="font-size: 0; margin-top: 6px; padding: 0;">
						<span style="display: inline-block; font-size: 24px; font-weight: bold; color: #ee1c25; letter-spacing: -0.6px;">5,000</span><span style="display: inline-block; padding-left: 2px; font-size: 15px;">P</span>
					</div>
				</div>
				<div style="display: inline-block; width: 131px; height: 63px; padding: 21px 27px 0 0; margin: 16px 16px 0 0; background: url(${abcUrl}/static/images/email/coupon_bg.png) 0 0 no-repeat; text-align: center; vertical-align: top;">
					<span style="font-size: 12px; letter-spacing: -1.2px">첫 구매 시 추가 적립</span>
					<div style="font-size: 0; margin-top: 6px; padding: 0;">
						<span style="display: inline-block; font-size: 24px; font-weight: bold; color: #ee1c25; letter-spacing: -0.6px;">5,000</span><span style="display: inline-block; padding-left: 2px; font-size: 15px;">P</span>
					</div>
				</div>
				<div style="display: inline-block; width: 131px; height: 63px; padding: 21px 27px 0 0; margin: 16px 16px 0 0; background: url(${abcUrl}/static/images/email/coupon_bg.png) 0 0 no-repeat; text-align: center; vertical-align: top;">
					<span style="font-size: 12px; letter-spacing: -1.2px">구매금액별 적립</span>
					<div style="font-size: 0; margin-top: 6px; padding: 0;">
						<span style="display: inline-block; font-size: 24px; font-weight: bold; color: #ee1c25; letter-spacing: -0.6px;">3%</span><span style="display: inline-block; padding-left: 2px; font-size: 15px;">P</span>
					</div>
				</div>
				<div style="display: inline-block; width: 131px; height: 63px; padding: 21px 27px 0 0; margin: 16px 16px 0 0; background: url(${abcUrl}/static/images/email/coupon_bg.png) 0 0 no-repeat; text-align: center; vertical-align: top;">
					<span style="font-size: 12px; letter-spacing: -1.2px">기념일 해당 월 첫 구매</span>
					<div style="font-size: 0; margin-top: 6px; padding: 0;">
						<span style="display: inline-block; font-size: 24px; font-weight: bold; color: #ee1c25; letter-spacing: -0.6px;">6%</span><span style="display: inline-block; padding-left: 2px; font-size: 15px;">P</span>
					</div>
				</div>
				<div style="display: inline-block; width: 131px; height: 63px; padding: 21px 27px 0 0; margin: 16px 16px 0 0; background: url(${abcUrl}/static/images/email/coupon_bg.png) 0 0 no-repeat; text-align: center; vertical-align: top;">
					<span style="font-size: 12px; letter-spacing: -1.2px">생일 해당 월 할인쿠폰</span>
					<div style="font-size: 0; margin-top: 6px; padding: 0;">
						<span style="display: inline-block; font-size: 24px; font-weight: bold; color: #ee1c25; letter-spacing: -0.6px;">10,000</span><span style="display: inline-block; padding-left: 2px; font-size: 15px;">원</span>
					</div>
				</div>
			</div>
			<ul style="margin: 11px 0 0; padding: 0;">
				<li style="overflow: hidden; margin: 0; padding: 0;">
					<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 10px 5px 0 0; background-color: #999; vertical-align: top;"></span>
					<p style="float: right; width: 671px; margin: 0; font-size: 12px; color: #666; letter-spacing: -1.2px; line-height: 22px; word-break: keep-all;">
						회원정보는 <span style="color: #000;">마이페이지 &gt; 개인정보 &gt; 개인정보 수정</span>에서 변경할 수 있습니다.
					</p>
				</li>
				<li style="overflow: hidden; margin: 0; padding: 0;">
					<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 10px 5px 0 0; background-color: #999; vertical-align: top;"></span>
					<p style="float: right; width: 671px; margin: 0; font-size: 12px; color: #666; letter-spacing: -1.2px; line-height: 22px; word-break: keep-all;">
						쿠폰은 <span style="color: #000;">마이페이지 &gt; 쇼핑혜택 &gt; 쿠폰 리스트</span>에서 확인할 수 있습니다.
					</p>
				</li>
				<li style="overflow: hidden; margin: 0; padding: 0;">
					<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 10px 5px 0 0; background-color: #999; vertical-align: top;"></span>
					<p style="float: right; width: 671px; margin: 0; font-size: 12px; color: #666; letter-spacing: -1.2px; line-height: 22px; word-break: keep-all;">
						포인트는 <span style="color: #000;">마이페이지 &gt; 쇼핑혜택 &gt; 포인트</span>에서 확인할 수 있습니다.
					</p>
				</li>
				<li style="overflow: hidden; margin: 0; padding: 0;">
					<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 10px 5px 0 0; background-color: #999; vertical-align: top;"></span>
					<p style="float: right; width: 671px; margin: 0; font-size: 12px; color: #666; letter-spacing: -1.2px; line-height: 22px; word-break: keep-all;">회원 혜택은 정책에 따라 변경될 수 있습니다.</p>
				</li>
			</ul>
			<!-- e : 화원가입 환영 혜택 -->

			<div style="margin-top: 45px; font-size: 0;">
				<a href="${abcUrl}/mypage" target="_blank" style="display: inline-block; padding: 20px 53px 19px 54px; border: 1px solid #000; font-weight: bold; font-size: 15px; color: #000; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">마이페이지</a>
				<a href="${abcUrl}" target="_blank" style="display: inline-block; padding: 21px 45px 20px; margin-left: 10px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">메인 바로가기</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-05009',null,'A',0,'SY00000004','2019-07-03 11:31:25','SY00000001','2019-09-09 16:23:46')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10011','장기미사용자 탈퇴처리','[ABC마트] 이용해 주셔서 감사합니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		 <tbody>
			<tr style="background-color: #000;">
			   <td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
				  <a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
					 <img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
				  </a>
			   </td>
			   <td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
				  <table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
					 <tbody>
						<tr>
						   <td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
						</tr>
					 </tbody>
				  </table>
			   </td>
			</tr>
		 </tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">이용해 주셔서 감사합니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요! <br />
				ABC마트 코리아입니다.<br /><br />

				장기간(1년 이상) A-RT의 서비스를 이용하지 않아<br />
				회원님의 개인정보 삭제 후 <span style="color: #ee1c25;">자동 탈퇴처리</span> 되었습니다.<br /><br />

				적립된 포인트는 소멸되었으며, 재가입시 포인트는 재 적립되지 않습니다.<br /><br />

				본 회원 정책은 새롭게 실시되는 정보통신망법 개정안의 인터넷상 개인정보보호 강화방안에 따라<br />
				개인정보 보관을 최소화하기 위한 방안입니다.<br /><br />

				그동안 A-RT 쇼핑몰을 이용해 주셔서 대단히 감사합니다.<br /><br />

				더 나은 A-RT가 되기 위해 노력하겠습니다.<br />
				감사합니다.

			</p>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
				   <p style="margin:0;">
					  <span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
					  사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
					  대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
				   </p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-05008',null,'A',0,'SY00000011','2019-07-03 15:23:27','SY00000001','2019-09-09 16:36:10')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10004','회원 탈퇴 메일','[ABC마트] 회원 탈퇴 요청이 처리되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- e : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">회원 탈퇴 요청이 처리되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}! <br />
				그동안 A-RT 쇼핑몰을 이용해 주셔서 대단히 감사합니다.<br />
				요청하신 회원 탈퇴가 정상적으로 처리되었습니다.<br />
				더 나은 A-RT가 되기 위해 노력하겠습니다.
				감사합니다
			</p>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-05006',null,'A',0,'SY00000004','2019-07-05 10:07:22','SY00000001','2019-09-09 16:23:17')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10000','주문취소','[A-RT] 주문이 취소되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">주문이 취소되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님! <br />
				<span style="color: #ee1c25;">${clmDtm}</span>에 고객님께서 신청하신 주문 취소가 정상적으로 처리되었습니다.<br />
				주문 시 고객님께서 <span style="color: #ee1c25;">결제하신 결제수단으로 결제금액이 환불</span> 됩니다.
			</p>

			<!-- s : 취소상품 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">취소상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdtList?exists>
						<#list prdtList as prdt>
							<tr>
								<td style="padding: 20px 15px; font-size: 0;">
									<img style="width: 100%" src="${prdt.imageUrl}?shrink=100:100" alt="${prdt.altrnText}">
								</td>
								<td>
									<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName}</span>
									<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName}</p>
									<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName} / 1개</span>
								</td>
								<td style="text-align: center;">
									<#if prdt.prdtNormalAmt &gt; prdt.orderAmt>
										<div style="margin-bottom: 5px; color: #999; text-decoration: line-through">
											<span style="font-size: 13px;">${prdt.prdtNormalAmt}</span><span style="font-size: 12px;">원</span>
										</div>
									</#if>
									<span style="font-size: 18px; font-weight: bold;">${prdt.orderAmt}</span><span style="font-size: 15px;">원</span>
								</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 취소상품 정보 -->

			<!-- s : 환불 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">환불 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: auto;" />
					<col style="width: auto;" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">취소금액</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold;">${cancelAmt}</span><span style="font-size: 15px;">원</span>
						</td>
					</tr>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">추가비용</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold;">${totalRedempAmt}</span><span style="font-size: 15px;">원</span>
						</td>
					</tr>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">총 환불금액</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold; color:#ee1c25;">${totalRfndAmt}</span><span style="font-size: 15px; color:#ee1c25;">원</span>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="padding: 7px 14px 9px; background: #fbfbfb;">
							<!-- s: table -->
							<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
								<colgroup>
									<col style="width: auto;" />
									<col style="width: auto;" />
								</colgroup>
								<tbody>
									<#if refundPymntAmtList?exists>
										<#list refundPymntAmtList as refund>
											<tr>
												<th scope="row" style="padding: 6px 0 5px; font-weight: normal; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left;">${refund.pymntMeansCodeName} 취소</th>
												<td style="padding: 3px 0 8px; color: #666; text-align: right;">
													<#if refund.pymntMeansCode != "10007" && refund.pymntMeansCode != "10008">
														<span style="font-size: 14px; letter-spacing: -0.7px;">${refund.pymntAmt}</span><span style="font-size: 13px; letter-spacing: -0.65px;">원</span>
													</#if>
													<#if refund.pymntMeansCode == "10007" || refund.pymntMeansCode == "10008">
														<span style="font-size: 14px; letter-spacing: -0.7px;">${refund.pymntAmt}</span><span style="font-size: 13px; letter-spacing: -0.65px;">P</span>
													</#if>
												</td>
											</tr>
										</#list>
									</#if>
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 환불 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 20px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">신용카드 승인취소 여부는 카드사를 통해 3~4일 후 확인 가능합니다. <br />단, 아래의 지불수단의 경우 최초 결제하신 수단과 다른 결제수단으로 환불 될 수 있습니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">무통장입금은 별도 연락을 통한 본인계좌로 환불 처리됩니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">휴대폰으로 결제하신 경우 당월 결제에 대해서만 휴대폰 결제취소가 되며,  전월 결제에 대해서는 별도 연락을 통한 본인계좌로 환불됩니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">계좌이체의 경우 당일은 즉시 환불, 주문일을 경과한 경우는 3일 정도 소요됩니다.</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl}" target="_blank" style="display: inline-block; padding: 21px 26px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">취소 내역 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-01002-1',null,'A',0,'SY00000057','2019-07-05 10:56:52','SY00000057','2019-09-09 16:22:46')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10000','주문취소','[ON THE SPOT] 주문이 취소되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
									<td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">주문이 취소되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName?if_exists}님! <br />
				<span style="color: #ee1c25;">${clmDtm?if_exists}</span>에 고객님께서 신청하신 주문 취소가 정상적으로 처리되었습니다.<br />
				주문 시 고객님께서 <span style="color: #ee1c25;">결제하신 결제수단으로 결제금액이 환불</span> 됩니다.
			</p>

			<!-- s : 취소상품 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">취소상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdtList?exists>
						<#list prdtList as prdt>
							<tr>
								<td style="padding: 20px 15px; font-size: 0;">
									<img style="width: 100%" src="${prdt.imageUrl?if_exists}?shrink=100:100" alt="${prdt.altrnText?if_exists}">
								</td>
								<td>
									<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName?if_exists}</span>
									<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName?if_exists}</p>
									<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName?if_exists} / 1개</span>
								</td>
								<td style="text-align: center;">
									<#if prdt.prdtNormalAmt &gt; prdt.orderAmt>
										<div style="margin-bottom: 5px; color: #999; text-decoration: line-through">
											<span style="font-size: 13px;">${prdt.prdtNormalAmt?if_exists}</span><span style="font-size: 12px;">원</span>
										</div>
									</#if>
									<span style="font-size: 18px; font-weight: bold;">${prdt.orderAmt?if_exists}</span><span style="font-size: 15px;">원</span>
								</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 취소상품 정보 -->

			<!-- s : 환불 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">환불 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: auto;" />
					<col style="width: auto;" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">취소금액</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold;">${cancelAmt?if_exists}</span><span style="font-size: 15px;">원</span>
						</td>
					</tr>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">추가비용</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold;">${totalRedempAmt?if_exists}</span><span style="font-size: 15px;">원</span>
						</td>
					</tr>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">총 환불금액</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold; color:#ee1c25;">${totalRfndAmt?if_exists}</span><span style="font-size: 15px; color:#ee1c25;">원</span>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="padding: 7px 14px 9px; background: #fbfbfb;">
							<!-- s: table -->
							<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
								<colgroup>
									<col style="width: auto;" />
									<col style="width: auto;" />
								</colgroup>
								<tbody>
									<#if refundPymntAmtList?exists>
										<#list refundPymntAmtList as refund>
											<tr>
												<th scope="row" style="padding: 6px 0 5px; font-weight: normal; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left;">${refund.pymntMeansCodeName?if_exists} 취소</th>
												<td style="padding: 3px 0 8px; color: #666; text-align: right;">
													<#if refund.pymntMeansCode != "10007" && refund.pymntMeansCode != "10008">
														<span style="font-size: 14px; letter-spacing: -0.7px;">${refund.pymntAmt?if_exists}</span><span style="font-size: 13px; letter-spacing: -0.65px;">원</span>
													</#if>
													<#if refund.pymntMeansCode == "10007" || refund.pymntMeansCode == "10008">
														<span style="font-size: 14px; letter-spacing: -0.7px;">${refund.pymntAmt?if_exists}</span><span style="font-size: 13px; letter-spacing: -0.65px;">P</span>
													</#if>
												</td>
											</tr>
										</#list>
									</#if>
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 환불 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 20px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">신용카드 승인취소 여부는 카드사를 통해 3~4일 후 확인 가능합니다. <br />단, 아래의 지불수단의 경우 최초 결제하신 수단과 다른 결제수단으로 환불 될 수 있습니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">무통장입금은 별도 연락을 통한 본인계좌로 환불 처리됩니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">휴대폰으로 결제하신 경우 당월 결제에 대해서만 휴대폰 결제취소가 되며,  전월 결제에 대해서는 별도 연락을 통한 본인계좌로 환불됩니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">계좌이체의 경우 당일은 즉시 환불, 주문일을 경과한 경우는 3일 정도 소요됩니다.</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl?if_exists}" target="_blank" style="display: inline-block; padding: 21px 26px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">취소 내역 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
						통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-01002-2',null,'A',0,'SY00000057','2019-07-05 13:26:28','SY00000057','2019-09-16 16:24:54')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10007','교환접수','[A-RT] 교환이 접수되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">교환이 접수되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님!<br />
				<span style="color: #ee1c25">${clmDtm}</span>에 고객님께서 요청하신 교환신청이 접수되었습니다.<br />
				접수 확인 후 1~2일 이내 교환상품 회수지로 택배기사님이 방문합니다. (주말, 공휴일제외)<br />
				교환신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 취소/반품/교환 내역</span>에서 확인하실 수 있습니다.
			</p>
			
			<!-- s : 교환 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">교환 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmDtm}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmNo}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환상품 회수지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${buyerPostCodeText}] ${buyerPostAddrText}, ${buyerDtlAddrText}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환상품 수령지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${rcvrPostCodeText}] ${rcvrPostAddrText}, ${rcvrDtlAddrText}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 교환 신청 정보 -->

			<!-- s : 교환상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">교환상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdtList?exists>
						<#list prdtList as prdt>
							<tr>
								<td style="padding: 20px 15px; font-size: 0;">
									<img style="width: 100%" src="${prdt.imageUrl}?shrink=100:100" alt="${prdt.altrnText}">
								</td>
								<td>
									<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName}</span>
									<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName}</p>
									<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName} / 1개</span>
								</td>
								<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
									${prdt.clmRsnCodeName} <#if prdt.clmRsnCode == "10010"><span style="display: block; margin-top: 9px; font-size: 13px; letter-spacing: -0.65px; text-align: center; color: #999;">${prdt.changeOptnName}</span></#if>
								</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 교환상품 정보 -->

			<!-- s : 교환 배송비 결제 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">교환 배송비 결제 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송비</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyPymntAmt}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">결제방식</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">
								<#if addDlvyAmtPymntType == "P">
									<#if dlvyAmtPymnt.pymntMeansCode == "10000">
										${dlvyPymntMeansName} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName} (${dlvyAmtPymnt.cardMask}) <#if dlvyAmtPymnt.instmtTermCount == 0>일시불</#if><#if dlvyAmtPymnt.instmtTermCount != 0>${dlvyAmtPymnt.instmtTermCount}개월</#if> ${dlvyAmtPymnt.strPymntDtm}</p>
									</#if>
									<#if dlvyAmtPymnt.pymntMeansCode == "10001">
										${dlvyPymntMeansName} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName} (${dlvyAmtPymnt.pymntOrganNoText})
									</#if>
									<#if dlvyAmtPymnt.pymntMeansCode == "10002">
										${dlvyPymntMeansName} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName}
									</#if>
								</#if>
								<#if addDlvyAmtPymntType == "C">
									${dlvyPymntMeansName}
								</#if>
								<#if addDlvyAmtPymntType == "F">
									${dlvyPymntMeansName}
								</#if>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 교환 배송비 결제 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">교환 접수완료 후 14일 이내에 상품이 교환지로 도착하지 않을 경우 접수가 취소됩니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">신발/의류/용품 외부에서 착용한 경우 교환 불가합니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">제품에 붙어있는 택(Tag)을 훼손/분실 및 정품 브랜드 박스에 테이핑 및 훼손한 경우 교환 불가합니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">지정택배(대한통운) 외 타 택배 이용 시 추가로 발생되는 비용은 고객 부담입니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">상품 불량 또는 오배송 확인 시 택배비는 환불됩니다. (선택하신 결제수단으로 택배비 환불처리)</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl}" target="_blank" style="display: inline-block; padding: 21px 43px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">교환 내역 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-03001-1',null,'A',0,'SY00000057','2019-07-05 15:22:25','SY00000057','2019-09-09 16:23:04')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10004','온라인회원 전환  ','[ABC마트] 회원전환이 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- e : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">회원전환이 완료되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberInfo.memberName}님!<br />
				<span style="color: #ee1c25">A-RT 온라인회원 전환</span>을 진심으로 축하합니다.<br />
				앞으로 다양한 상품정보와 함께 만족스러운 쇼핑을 즐길 수 있도록 최선을 다하며,<br />
				항상 새로운 뉴스와 이벤트로 고객님의 알찬 쇼핑을 위해 열심히 노력하겠습니다. <br />
				감사합니다.
			</p>

			<!-- s : 회원전환 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">회원전환 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberInfo.memberName}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">아이디</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberInfo.loginId}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">전환일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${joinDtm}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 회원전환 정보 -->
 

			<div style="margin-top: 45px; font-size: 0;">
				<a href="${abcUrl}/mypage" target="_blank" style="display: inline-block; padding: 20px 53px 19px 54px; border: 1px solid #000; font-weight: bold; font-size: 15px; color: #000; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">마이페이지</a>
				<a href="${abcUrl}/member/member-change-login-page" target="_blank" style="display: inline-block; padding: 21px 31px 20px 32px; margin-left: 10px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">통합멤버십 회원전환</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-05002',null,'A',0,'SY00000004','2019-07-05 15:34:27','SY00000001','2020-01-22 12:07:38')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10004','멤버쉽회원 전환','[ABC마트] 회원전환이 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- e : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">회원전환이 완료되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberInfo.memberName}님!<br />
				<span style="color: #ee1c25">A-RT 통합멤버십회원 전환</span>을 진심으로 축하합니다.<br />
				앞으로 다양한 상품정보와 함께 만족스러운 쇼핑을 즐길 수 있도록 최선을 다하며,<br />
				항상 새로운 뉴스와 이벤트로 고객님의 알찬 쇼핑을 위해 열심히 노력하겠습니다. <br />
				감사합니다.
			</p>

			<!-- s : 회원전환 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">회원전환 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberInfo.memberName}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">아이디</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberInfo.loginId}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">전환일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${joinDtm}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 회원전환 정보 -->
 

			<div style="margin-top: 45px; font-size: 0;">
				<a href="${abcUrl}/mypage" target="_blank" style="display: inline-block; padding: 20px 53px 19px 54px; border: 1px solid #000; font-weight: bold; font-size: 15px; color: #000; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">마이페이지</a>
				<a href="${abcUrl}" target="_blank" style="display: inline-block; padding: 21px 61px 20px 62px; margin-left: 10px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">메인 바로가기</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="https://www.a-rt.com/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-05010',null,'A',0,'SY00000004','2019-07-05 15:42:34','SY00000001','2019-09-09 16:24:08')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10007','교환접수','[ON THE SPOT] 교환이 접수되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
									<td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">교환이 접수되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName?if_exists}님!<br />
				<span style="color: #ee1c25">${clmDtm?if_exists}</span>에 고객님께서 요청하신 교환신청이 접수되었습니다.<br />
				접수 확인 후 1~2일 이내 교환상품 회수지로 택배기사님이 방문합니다. (주말, 공휴일제외)<br />
				교환신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 취소/반품/교환 내역</span>에서 확인하실 수 있습니다.
			</p>
			
			<!-- s : 교환 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">교환 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmDtm?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmNo?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환상품 회수지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${buyerPostCodeText?if_exists}] ${buyerPostAddrText?if_exists}, ${buyerDtlAddrText?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환상품 수령지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${rcvrPostCodeText?if_exists}] ${rcvrPostAddrText?if_exists}, ${rcvrDtlAddrText?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 교환 신청 정보 -->

			<!-- s : 교환상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">교환상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdtList?exists>
						<#list prdtList as prdt>
							<tr>
								<td style="padding: 20px 15px; font-size: 0;">
									<img style="width: 100%" src="${prdt.imageUrl?if_exists}?shrink=100:100" alt="${prdt.altrnText?if_exists}">
								</td>
								<td>
									<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName?if_exists}</span>
									<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName?if_exists}</p>
									<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName?if_exists} / 1개</span>
								</td>
								<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
									${prdt.clmRsnCodeName?if_exists} <#if prdt.clmRsnCode == "10010"><span style="display: block; margin-top: 9px; font-size: 13px; letter-spacing: -0.65px; text-align: center; color: #999;">${prdt.changeOptnName?if_exists}</span></#if>
								</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 교환상품 정보 -->

			<!-- s : 교환 배송비 결제 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">교환 배송비 결제 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송비</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyPymntAmt?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">결제방식</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">
								<#if addDlvyAmtPymntType == "P">
									<#if dlvyAmtPymnt.pymntMeansCode == "10000">
										${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists} (${dlvyAmtPymnt.cardMask?if_exists}) <#if dlvyAmtPymnt.instmtTermCount == 0>일시불</#if><#if dlvyAmtPymnt.instmtTermCount != 0>${dlvyAmtPymnt.instmtTermCount?if_exists}개월</#if> ${dlvyAmtPymnt.strPymntDtm?if_exists}</p>
									</#if>
									<#if dlvyAmtPymnt.pymntMeansCode == "10001">
										${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists} (${dlvyAmtPymnt.pymntOrganNoText?if_exists})
									</#if>
									<#if dlvyAmtPymnt.pymntMeansCode == "10002">
										${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists}
									</#if>
								</#if>
								<#if addDlvyAmtPymntType == "C">
									${dlvyPymntMeansName?if_exists}
								</#if>
								<#if addDlvyAmtPymntType == "F">
									${dlvyPymntMeansName?if_exists}
								</#if>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 교환 배송비 결제 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">교환 접수완료 후 14일 이내에 상품이 교환지로 도착하지 않을 경우 접수가 취소됩니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">신발/의류/용품 외부에서 착용한 경우 교환 불가합니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">제품에 붙어있는 택(Tag)을 훼손/분실 및 정품 브랜드 박스에 테이핑 및 훼손한 경우 교환 불가합니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">지정택배(대한통운) 외 타 택배 이용 시 추가로 발생되는 비용은 고객 부담입니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">상품 불량 또는 오배송 확인 시 택배비는 환불됩니다. (선택하신 결제수단으로 택배비 환불처리)</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl?if_exists}" target="_blank" style="display: inline-block; padding: 21px 43px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">교환 내역 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
						통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-03001-2',null,'A',0,'SY00000057','2019-07-05 17:47:35','SY00000057','2019-09-16 16:25:10')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10007','교환접수 완료','[A-RT] 교환 접수 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">교환접수가 완료되어 택배기사님이 방문할 예정입니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님!<br />
				<span style="color: #ee1c25">${modDtm}</span>에 교환접수가 완료되었습니다.<br />
				1~2일 이내 교환상품 회수지로 택배기사님이 방문합니다. (주말, 공휴일제외)<br />
				교환신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 취소/반품/교환 내역</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : 교환 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">교환 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmDtm}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmNo}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환상품 회수지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${buyerPostCodeText}] ${buyerPostAddrText}, ${buyerDtlAddrText}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환상품 수령지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${rcvrPostCodeText}] ${rcvrPostAddrText}, ${rcvrDtlAddrText}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 교환 신청 정보 -->

			<!-- s : 교환상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">교환상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdtList?exists>
						<#list prdtList as prdt>
							<tr>
								<td style="padding: 20px 15px; font-size: 0;">
									<img style="width: 100%" src="${prdt.imageUrl}?shrink=100:100" alt="${prdt.altrnText}">
								</td>
								<td>
									<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName}</span>
									<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName}</p>
									<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName} / 1개</span>
								</td>
								<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
									${prdt.clmRsnCodeName} <#if prdt.clmRsnCode == "10010"><span style="display: block; margin-top: 9px; font-size: 13px; letter-spacing: -0.65px; text-align: center; color: #999;">${prdt.changeOptnName}</span></#if>
								</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 교환상품 정보 -->

			<!-- s : 교환 배송비 결제 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">교환 배송비 결제 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송비</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyPymntAmt}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">결제방식</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">
								<#if addDlvyAmtPymntType == "P">
									<#if dlvyAmtPymnt.pymntMeansCode == "10000">
										${dlvyPymntMeansName} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName} (${dlvyAmtPymnt.cardMask}) <#if dlvyAmtPymnt.instmtTermCount == 0>일시불</#if><#if dlvyAmtPymnt.instmtTermCount != 0>${dlvyAmtPymnt.instmtTermCount}개월</#if> ${dlvyAmtPymnt.strPymntDtm}</p>
									</#if>
									<#if dlvyAmtPymnt.pymntMeansCode == "10001">
										${dlvyPymntMeansName} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName} (${dlvyAmtPymnt.pymntOrganNoText})
									</#if>
									<#if dlvyAmtPymnt.pymntMeansCode == "10002">
										${dlvyPymntMeansName} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName}
									</#if>
								</#if>
								<#if addDlvyAmtPymntType == "C">
									${dlvyPymntMeansName}
								</#if>
								<#if addDlvyAmtPymntType == "F">
									${dlvyPymntMeansName}
								</#if>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 교환 배송비 결제 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">교환 접수완료 후 14일 이내에 상품이 교환지로 도착하지 않을 경우 접수가 취소됩니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">신발/의류/용품 외부에서 착용한 경우 교환 불가합니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">제품에 붙어있는 택(Tag)을 훼손/분실 및 정품 브랜드 박스에 테이핑 및 훼손한 경우 교환 불가합니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">지정택배(대한통운) 외 타 택배 이용 시 추가로 발생되는 비용은 고객 부담입니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">상품 불량 또는 오배송 확인 시 택배비는 환불됩니다. (선택하신 결제수단으로 택배비 환불처리)</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl}" target="_blank" style="display: inline-block; padding: 21px 43px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">교환 내역 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-03007-1',null,'A',0,'SY00000057','2019-07-08 14:51:49','SY00000057','2019-09-09 16:23:35')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10007','교환접수 완료','[ON THE SPOT] 교환 접수 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
									<td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">교환접수가 완료되어 택배기사님이 방문할 예정입니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName?if_exists}님!<br />
				<span style="color: #ee1c25">${modDtm?if_exists}</span>에 교환접수가 완료되었습니다.<br />
				1~2일 이내 교환상품 회수지로 택배기사님이 방문합니다. (주말, 공휴일제외)<br />
				교환신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 취소/반품/교환 내역</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : 교환 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">교환 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmDtm?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmNo?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환상품 회수지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${buyerPostCodeText?if_exists}] ${buyerPostAddrText?if_exists}, ${buyerDtlAddrText?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환상품 수령지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${rcvrPostCodeText?if_exists}] ${rcvrPostAddrText?if_exists}, ${rcvrDtlAddrText?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 교환 신청 정보 -->

			<!-- s : 교환상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">교환상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdtList?exists>
						<#list prdtList as prdt>
							<tr>
								<td style="padding: 20px 15px; font-size: 0;">
									<img style="width: 100%" src="${prdt.imageUrl?if_exists}?shrink=100:100" alt="${prdt.altrnText?if_exists}">
								</td>
								<td>
									<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName?if_exists}</span>
									<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName?if_exists}</p>
									<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName?if_exists} / 1개</span>
								</td>
								<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
									${prdt.clmRsnCodeName?if_exists} <#if prdt.clmRsnCode == "10010"><span style="display: block; margin-top: 9px; font-size: 13px; letter-spacing: -0.65px; text-align: center; color: #999;">${prdt.changeOptnName?if_exists}</span></#if>
								</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 교환상품 정보 -->

			<!-- s : 교환 배송비 결제 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">교환 배송비 결제 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송비</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyPymntAmt?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">결제방식</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">
								<#if addDlvyAmtPymntType == "P">
									<#if dlvyAmtPymnt.pymntMeansCode == "10000">
										${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists} (${dlvyAmtPymnt.cardMask?if_exists}) <#if dlvyAmtPymnt.instmtTermCount == 0>일시불</#if><#if dlvyAmtPymnt.instmtTermCount != 0>${dlvyAmtPymnt.instmtTermCount?if_exists}개월</#if> ${dlvyAmtPymnt.strPymntDtm?if_exists}</p>
									</#if>
									<#if dlvyAmtPymnt.pymntMeansCode == "10001">
										${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists} (${dlvyAmtPymnt.pymntOrganNoText?if_exists})
									</#if>
									<#if dlvyAmtPymnt.pymntMeansCode == "10002">
										${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists}
									</#if>
								</#if>
								<#if addDlvyAmtPymntType == "C">
									${dlvyPymntMeansName?if_exists}
								</#if>
								<#if addDlvyAmtPymntType == "F">
									${dlvyPymntMeansName?if_exists}
								</#if>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 교환 배송비 결제 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">교환 접수완료 후 14일 이내에 상품이 교환지로 도착하지 않을 경우 접수가 취소됩니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">신발/의류/용품 외부에서 착용한 경우 교환 불가합니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">제품에 붙어있는 택(Tag)을 훼손/분실 및 정품 브랜드 박스에 테이핑 및 훼손한 경우 교환 불가합니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">지정택배(대한통운) 외 타 택배 이용 시 추가로 발생되는 비용은 고객 부담입니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">상품 불량 또는 오배송 확인 시 택배비는 환불됩니다. (선택하신 결제수단으로 택배비 환불처리)</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl?if_exists}" target="_blank" style="display: inline-block; padding: 21px 43px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">교환 내역 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
						통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-03007-2',null,'A',0,'SY00000057','2019-07-08 14:54:37','SY00000057','2019-09-16 16:25:52')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10007','교환완료','[A-RT] 교환 처리가 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">교환 상품이 발송되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님!<br />
				<span style="color: #ee1c25">${modDtm}</span>에 교환처리가 완료되었습니다.<br />
				교환신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 취소/반품/교환 내역</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : 교환 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">교환 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmDtm}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmNo}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 교환 신청 정보 -->

			<!-- s : 배송 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">배송 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송방법</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">일반택배</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">택배사</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${logisVndrName}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">운송장번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${waybilNoText}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 배송 정보 -->

			<!-- s : 교환상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">교환상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdtList?exists>
						<#list prdtList as prdt>
							<tr>
								<td style="padding: 20px 15px; font-size: 0;">
									<img style="width: 100%" src="${prdt.imageUrl}?shrink=100:100" alt="${prdt.altrnText}">
								</td>
								<td>
									<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName}</span>
									<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName}</p>
									<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName} / 1개</span>
								</td>
								<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">${prdt.clmPrdtStatCodeName}
								</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 교환상품 정보 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl}" target="_blank" style="display: inline-block; padding: 21px 43px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">교환 내역 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-03004-1',null,'A',0,'SY00000057','2019-07-08 15:31:29','SY00000057','2019-09-09 16:23:19')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10007','교환완료','[ON THE SPOT] 교환 처리가 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
									<td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">교환 상품이 발송되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName?if_exists}님!<br />
				<span style="color: #ee1c25">${modDtm?if_exists}</span>에 교환처리가 완료되었습니다.<br />
				교환신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 취소/반품/교환 내역</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : 교환 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">교환 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmDtm?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">교환 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmNo?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 교환 신청 정보 -->

			<!-- s : 배송 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">배송 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송방법</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">일반택배</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">택배사</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${logisVndrName?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">운송장번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${waybilNoText?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 배송 정보 -->

			<!-- s : 교환상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">교환상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdtList?exists>
						<#list prdtList as prdt>
							<tr>
								<td style="padding: 20px 15px; font-size: 0;">
									<img style="width: 100%" src="${prdt.imageUrl?if_exists}?shrink=100:100" alt="${prdt.altrnText?if_exists}">
								</td>
								<td>
									<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName?if_exists}</span>
									<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName?if_exists}</p>
									<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName?if_exists} / 1개</span>
								</td>
								<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">${prdt.clmPrdtStatCodeName?if_exists}
								</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 교환상품 정보 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl?if_exists}" target="_blank" style="display: inline-block; padding: 21px 43px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">교환 내역 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
						통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-03004-2',null,'A',0,'SY00000057','2019-07-08 15:32:51','SY00000057','2019-09-16 16:25:36')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10007','반품접수','[A-RT] 반품이 접수되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">반품이 접수되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님!<br />
				<span style="color: #ee1c25">${clmDtm}</span>에 고객님께서 요청하신 반품신청이 접수되었습니다.<br />
				접수 확인 후 1~2일 이내 반품상품 회수지로 택배기사님이 방문합니다. (주말, 공휴일제외)<br />
				반품신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 취소/반품/교환 내역</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : 반품 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">반품 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">반품신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmDtm}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">반품 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmNo}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">반품상품 회수지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${buyerPostCodeText}] ${buyerPostAddrText}, ${buyerDtlAddrText}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 반품 신청 정보 -->

			<!-- s : 반품 상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">반품 상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdtList?exists>
						<#list prdtList as prdt>
							<tr>
								<td style="padding: 20px 15px; font-size: 0;">
									<img style="width: 100%" src="${prdt.imageUrl}?shrink=100:100" alt="${prdt.altrnText}">
								</td>
								<td>
									<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName}</span>
									<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName}</p>
									<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName} / 1개</span>
								</td>
								<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
									${prdt.clmRsnCodeName}
								</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 반품 상품 정보 -->

			<!-- s : 반품 배송비 결제 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">반품 배송비 결제 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송비</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyPymntAmt}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">결제방식</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">
								<#if addDlvyAmtPymntType == "P">
									<#if dlvyAmtPymnt.pymntMeansCode == "10000">
										${dlvyPymntMeansName} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName} (${dlvyAmtPymnt.cardMask}) <#if dlvyAmtPymnt.instmtTermCount == 0>일시불</#if><#if dlvyAmtPymnt.instmtTermCount != 0>${dlvyAmtPymnt.instmtTermCount}개월</#if> ${dlvyAmtPymnt.strPymntDtm}</p>
									</#if>
									<#if dlvyAmtPymnt.pymntMeansCode == "10001">
										${dlvyPymntMeansName} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName} (${dlvyAmtPymnt.pymntOrganNoText})
									</#if>
									<#if dlvyAmtPymnt.pymntMeansCode == "10002">
										${dlvyPymntMeansName} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName}
									</#if>
								</#if>
								<#if addDlvyAmtPymntType == "S">
									${dlvyPymntMeansName}
								</#if>
								<#if addDlvyAmtPymntType == "C">
									${dlvyPymntMeansName}
								</#if>
								<#if addDlvyAmtPymntType == "F">
									${dlvyPymntMeansName}
								</#if>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 반품 배송비 결제 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">반품 신청 후 14일 이내에 상품이 반품지로 도착하지 않을 경우 접수가 취소됩니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">신발/의류/용품 외부에서 착용한 경우 반품 불가합니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">제품에 붙어있는 택(Tag)을 훼손/분실 및 정품 브랜드 박스에 테이핑 및 훼손한 경우 반품 불가합니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">지정택배 외 타 택배 이용 시 추가로 발생되는 비용은 고객 부담입니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">상품 불량 또는 오배송 확인 시 택배비 환불됩니다. (선택하신 결제수단으로 택배비 환불처리)</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl}" target="_blank" style="display: inline-block; padding: 21px 43px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">반품 내역 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-03002-1',null,'A',0,'SY00000057','2019-07-08 16:20:11','SY00000057','2019-09-09 16:23:12')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10007','반품접수','[ON THE SPOT] 반품이 접수되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
									<td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">반품이 접수되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName?if_exists}님!<br />
				<span style="color: #ee1c25">${clmDtm?if_exists}</span>에 고객님께서 요청하신 반품신청이 접수되었습니다.<br />
				접수 확인 후 1~2일 이내 반품상품 회수지로 택배기사님이 방문합니다. (주말, 공휴일제외)<br />
				반품신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 취소/반품/교환 내역</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : 반품 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">반품 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">반품신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmDtm?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">반품 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmNo?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">반품상품 회수지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${buyerPostCodeText?if_exists}] ${buyerPostAddrText?if_exists}, ${buyerDtlAddrText?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 반품 신청 정보 -->

			<!-- s : 반품 상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">반품 상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdtList?exists>
						<#list prdtList as prdt>
							<tr>
								<td style="padding: 20px 15px; font-size: 0;">
									<img style="width: 100%" src="${prdt.imageUrl?if_exists}?shrink=100:100" alt="${prdt.altrnText?if_exists}">
								</td>
								<td>
									<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName?if_exists}</span>
									<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName?if_exists}</p>
									<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName?if_exists} / 1개</span>
								</td>
								<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
									${prdt.clmRsnCodeName?if_exists}
								</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 반품 상품 정보 -->

			<!-- s : 반품 배송비 결제 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">반품 배송비 결제 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송비</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyPymntAmt?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">결제방식</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">
								<#if addDlvyAmtPymntType == "P">
									<#if dlvyAmtPymnt.pymntMeansCode == "10000">
										${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists} (${dlvyAmtPymnt.cardMask?if_exists}) <#if dlvyAmtPymnt.instmtTermCount == 0>일시불</#if><#if dlvyAmtPymnt.instmtTermCount != 0>${dlvyAmtPymnt.instmtTermCount?if_exists}개월</#if> ${dlvyAmtPymnt.strPymntDtm?if_exists}</p>
									</#if>
									<#if dlvyAmtPymnt.pymntMeansCode == "10001">
										${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists} (${dlvyAmtPymnt.pymntOrganNoText?if_exists})
									</#if>
									<#if dlvyAmtPymnt.pymntMeansCode == "10002">
										${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists}
									</#if>
								</#if>
								<#if addDlvyAmtPymntType == "S">
									${dlvyPymntMeansName?if_exists}
								</#if>
								<#if addDlvyAmtPymntType == "C">
									${dlvyPymntMeansName?if_exists}
								</#if>
								<#if addDlvyAmtPymntType == "F">
									${dlvyPymntMeansName?if_exists}
								</#if>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 반품 배송비 결제 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">반품 신청 후 14일 이내에 상품이 반품지로 도착하지 않을 경우 접수가 취소됩니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">신발/의류/용품 외부에서 착용한 경우 반품 불가합니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">제품에 붙어있는 택(Tag)을 훼손/분실 및 정품 브랜드 박스에 테이핑 및 훼손한 경우 반품 불가합니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">지정택배 외 타 택배 이용 시 추가로 발생되는 비용은 고객 부담입니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">상품 불량 또는 오배송 확인 시 택배비 환불됩니다. (선택하신 결제수단으로 택배비 환불처리)</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl?if_exists}" target="_blank" style="display: inline-block; padding: 21px 43px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">반품 내역 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
						통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-03002-2',null,'A',0,'SY00000057','2019-07-08 16:21:28','SY00000057','2019-09-16 16:25:28')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10007','반품접수 완료','[A-RT] 반품 접수 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">반품접수가 완료되어 택배기사님이 방문할 예정입니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님!<br />
				<span style="color: #ee1c25">${modDtm}</span>에 반품접수가 완료되었습니다.<br />
				1~2일 이내 반품상품 회수지로 택배기사님이 방문합니다. (주말, 공휴일제외)<br />
				반품신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 취소/반품/교환 내역</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : 반품 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">반품 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">반품신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmDtm}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">반품 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmNo}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">반품상품 회수지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${buyerPostCodeText}] ${buyerPostAddrText}, ${buyerDtlAddrText}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 반품 신청 정보 -->

			<!-- s : 반품 상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">반품 상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdtList?exists>
						<#list prdtList as prdt>
							<tr>
								<td style="padding: 20px 15px; font-size: 0;">
									<img style="width: 100%" src="${prdt.imageUrl}?shrink=100:100" alt="${prdt.altrnText}">
								</td>
								<td>
									<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName}</span>
									<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName}</p>
									<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName} / 1개</span>
								</td>
								<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
									${prdt.clmRsnCodeName}
								</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 반품 상품 정보 -->

			<!-- s : 반품 배송비 결제 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">반품 배송비 결제 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송비</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyPymntAmt}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">결제방식</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">
								<#if addDlvyAmtPymntType == "P">
									<#if dlvyAmtPymnt.pymntMeansCode == "10000">
										${dlvyPymntMeansName} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName} (${dlvyAmtPymnt.cardMask}) <#if dlvyAmtPymnt.instmtTermCount == 0>일시불</#if><#if dlvyAmtPymnt.instmtTermCount != 0>${dlvyAmtPymnt.instmtTermCount}개월</#if> ${dlvyAmtPymnt.strPymntDtm}</p>
									</#if>
									<#if dlvyAmtPymnt.pymntMeansCode == "10001">
										${dlvyPymntMeansName} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName} (${dlvyAmtPymnt.pymntOrganNoText})
									</#if>
									<#if dlvyAmtPymnt.pymntMeansCode == "10002">
										${dlvyPymntMeansName} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName}
									</#if>
								</#if>
								<#if addDlvyAmtPymntType == "S">
									${dlvyPymntMeansName}
								</#if>
								<#if addDlvyAmtPymntType == "C">
									${dlvyPymntMeansName}
								</#if>
								<#if addDlvyAmtPymntType == "F">
									${dlvyPymntMeansName}
								</#if>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 반품 배송비 결제 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">반품 신청 후 14일 이내에 상품이 반품지로 도착하지 않을 경우 접수가 취소됩니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">신발/의류/용품 외부에서 착용한 경우 반품 불가합니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">제품에 붙어있는 택(Tag)을 훼손/분실 및 정품 브랜드 박스에 테이핑 및 훼손한 경우 반품 불가합니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">지정택배 외 타 택배 이용 시 추가로 발생되는 비용은 고객 부담입니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">상품 불량 또는 오배송 확인 시 택배비는 환불됩니다. (선택하신 결제수단으로 택배비 환불처리)</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl}" target="_blank" style="display: inline-block; padding: 21px 43px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">반품 내역 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-03008-1',null,'A',0,'SY00000057','2019-07-09 09:41:28','SY00000057','2019-09-09 16:23:42')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10007','반품접수 완료','[ON THE SPOT] 반품 접수 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
									<td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">반품접수가 완료되어 택배기사님이 방문할 예정입니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName?if_exists}님!<br />
				<span style="color: #ee1c25">${modDtm?if_exists}</span>에 반품접수가 완료되었습니다.<br />
				1~2일 이내 반품상품 회수지로 택배기사님이 방문합니다. (주말, 공휴일제외)<br />
				반품신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 취소/반품/교환 내역</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : 반품 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">반품 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">반품신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmDtm?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">반품 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmNo?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">반품상품 회수지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${buyerPostCodeText?if_exists}] ${buyerPostAddrText?if_exists}, ${buyerDtlAddrText?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 반품 신청 정보 -->

			<!-- s : 반품 상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">반품 상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdtList?exists>
						<#list prdtList as prdt>
							<tr>
								<td style="padding: 20px 15px; font-size: 0;">
									<img style="width: 100%" src="${prdt.imageUrl?if_exists}?shrink=100:100" alt="${prdt.altrnText?if_exists}">
								</td>
								<td>
									<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName?if_exists}</span>
									<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName?if_exists}</p>
									<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName?if_exists} / 1개</span>
								</td>
								<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
									${prdt.clmRsnCodeName?if_exists}
								</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 반품 상품 정보 -->

			<!-- s : 반품 배송비 결제 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">반품 배송비 결제 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송비</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyPymntAmt?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">결제방식</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">
								<#if addDlvyAmtPymntType == "P">
									<#if dlvyAmtPymnt.pymntMeansCode == "10000">
										${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists} (${dlvyAmtPymnt.cardMask?if_exists}) <#if dlvyAmtPymnt.instmtTermCount == 0>일시불</#if><#if dlvyAmtPymnt.instmtTermCount != 0>${dlvyAmtPymnt.instmtTermCount?if_exists}개월</#if> ${dlvyAmtPymnt.strPymntDtm?if_exists}</p>
									</#if>
									<#if dlvyAmtPymnt.pymntMeansCode == "10001">
										${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists} (${dlvyAmtPymnt.pymntOrganNoText?if_exists})
									</#if>
									<#if dlvyAmtPymnt.pymntMeansCode == "10002">
										${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists}
									</#if>
								</#if>
								<#if addDlvyAmtPymntType == "S">
									${dlvyPymntMeansName?if_exists}
								</#if>
								<#if addDlvyAmtPymntType == "C">
									${dlvyPymntMeansName?if_exists}
								</#if>
								<#if addDlvyAmtPymntType == "F">
									${dlvyPymntMeansName?if_exists}
								</#if>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 반품 배송비 결제 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">반품 신청 후 14일 이내에 상품이 반품지로 도착하지 않을 경우 접수가 취소됩니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">신발/의류/용품 외부에서 착용한 경우 반품 불가합니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">제품에 붙어있는 택(Tag)을 훼손/분실 및 정품 브랜드 박스에 테이핑 및 훼손한 경우 반품 불가합니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">지정택배 외 타 택배 이용 시 추가로 발생되는 비용은 고객 부담입니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">상품 불량 또는 오배송 확인 시 택배비는 환불됩니다. (선택하신 결제수단으로 택배비 환불처리)</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl?if_exists}" target="_blank" style="display: inline-block; padding: 21px 43px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">반품 내역 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
						통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-03008-2',null,'A',0,'SY00000057','2019-07-09 09:42:27','SY00000057','2019-09-16 16:26:01')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10007','반품완료','[A-RT] 반품 처리가 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">반품 처리가 완료되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님!<br />
				<span style="color: #ee1c25">${modDtm}</span>에 반품 처리가 완료되었습니다.<br />
				반품신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 취소/반품/교환 내역</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : 반품 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">반품 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">반품신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmDtm}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">반품 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmNo}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 반품 신청 정보 -->

			<!-- s : 환불금액 안내 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">환불금액 안내</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">결제금액</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${totalPymntAmt}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">반품 배송비</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyPymntAmt}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">환불금액</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${refundAmt}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 환불금액 안내 -->

			<!-- s : 반품 상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">반품 상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdtList?exists>
						<#list prdtList as prdt>
							<tr>
								<td style="padding: 20px 15px; font-size: 0;">
									<img style="width: 100%" src="${prdt.imageUrl}?shrink=100:100" alt="${prdt.altrnText}">
								</td>
								<td>
									<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName}</span>
									<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName}</p>
									<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName} / 1개</span>
								</td>
								<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
									${prdt.clmPrdtStatCodeName}
								</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 반품 상품 정보 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl}" target="_blank" style="display: inline-block; padding: 21px 43px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">반품 내역 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-03005-1',null,'A',0,'SY00000057','2019-07-09 10:49:42','SY00000057','2019-09-09 16:23:27')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10007','반품완료','[ON THE SPOT] 반품 처리가 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
									<td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">반품 처리가 완료되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName?if_exists}님!<br />
				<span style="color: #ee1c25">${modDtm?if_exists}</span>에 반품 처리가 완료되었습니다.<br />
				반품신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 취소/반품/교환 내역</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : 반품 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">반품 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">반품신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmDtm?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">반품 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${clmNo?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 반품 신청 정보 -->

			<!-- s : 환불금액 안내 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">환불금액 안내</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">결제금액</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${totalPymntAmt?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">반품 배송비</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyPymntAmt?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">환불금액</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${refundAmt?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 환불금액 안내 -->

			<!-- s : 반품 상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">반품 상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdtList?exists>
						<#list prdtList as prdt>
							<tr>
								<td style="padding: 20px 15px; font-size: 0;">
									<img style="width: 100%" src="${prdt.imageUrl?if_exists}?shrink=100:100" alt="${prdt.altrnText?if_exists}">
								</td>
								<td>
									<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName?if_exists}</span>
									<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName?if_exists}</p>
									<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName?if_exists} / 1개</span>
								</td>
								<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
									${prdt.clmPrdtStatCodeName?if_exists}
								</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 반품 상품 정보 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl?if_exists}" target="_blank" style="display: inline-block; padding: 21px 43px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">반품 내역 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
						통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-03005-2',null,'A',0,'SY00000057','2019-07-09 10:51:15','SY00000057','2019-09-16 16:25:44')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10000','입금지연 (무통장입금)','[A-RT] 입금여부를 확인해주세요','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		 <tbody>
			<tr style="background-color: #000;">
			   <td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
				  <a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
					 <img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
				  </a>
			   </td>
			   <td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
				  <table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
					 <tbody>
						<tr>
						   <td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
						</tr>
					 </tbody>
				  </table>
			   </td>
			</tr>
		 </tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">입금여부를 확인해주세요</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님! <br />
				입금이 지연되고있어 상품을 출고하지 못하고 있습니다.<br />
				입금을 했으나 확인되지 않고있다면 고객센터로 연락 부탁드립니다.<br />
				주문상태는 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 주문/배송 현황 조회</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : 주문 및 입금 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문 및 입금 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderDtm}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderNo}</td>
						</tr>
						<#if nmbrCrtfcNoText?exists>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">비밀번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${nmbrCrtfcNoText}</td>
							</tr>
						</#if>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">입금금액</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pymntTodoAmt}원</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">입금계좌</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pymntOrganName} : ${pymntOrganNoText}&nbsp;(예금주 : ${accountName})</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">입금기한</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${vrtlAcntExprDtm}까지</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 주문 및 입금 정보 -->

			<!-- s : 주문상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 140px;" />
					<col style="width: 130px;" />
				</colgroup>
				<tbody>
					<#list productList as item>
						<tr>
							<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
								<img style="width: 100%" src="${item.imageUrl}" alt="${item.altrnText}">
							</td>
							<td style="border-bottom: 1px solid #e5e5e5;">
								<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${item.brandName}</span>
								<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${item.prdtName}</p>
								<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${item.optnName} / ${item.orderQty}개</span>
							</td>
							<#if item.prdtNormalAmt &gt; item.orderAmt>
								<td style="text-align: center;">
									<div style="margin-bottom: 5px; color: #999; text-decoration: line-through">
										<span style="font-size: 13px;">${item.prdtNormalAmt}</span><span style="font-size: 12px;">원</span>
									</div>
									<span style="font-size: 18px; font-weight: bold;">${item.orderAmt}</span><span style="font-size: 15px;">원</span>
								</td>
							<#else>
								<td style="border-bottom: 1px solid #e5e5e5; text-align: center;">
									<span style="font-size: 18px; font-weight: bold;">${item.orderAmt}</span><span style="font-size: 15px;">원</span>
								</td>
							</#if>
							<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">${item.dlvyTypeCodeName}</td>
						</tr>
					</#list>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 주문상품 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">2개 이상의 상품 주문 시 매장 재고여부에 따라 분리발송 될 수 있습니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">오프라인 매장 발송 상품은 온라인 발송 상품보다 평균 배송기간이 2~3일 정도 더 소요될 수 있습니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">매장 발송 상품의 경우 발송되는 매장정보는 주문 완료 후 <span style="color: #000; font-weight: bold;">마이페이지 &gt; 주문배송 현황조회</span>에서 확인 가능합니다.</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrlPc}" target="_blank" style="display: inline-block; padding: 21px 26px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">주문/배송 현황 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
				   <p style="margin:0;">
					  <span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
					  사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
					  대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
				   </p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-01003-1',null,'A',0,'SY00000011','2019-07-09 17:07:17','SY00000001','2019-10-15 17:18:03')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10000','입금지연(무통장입금)','[ON THE SPOT] 입금여부를 확인해주세요.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
				   <td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
					  <a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
						 <img src="${otsUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
					  </a>
				   </td>
				   <td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
					  <table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
						 <tbody>
							<tr>
							   <td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
							   <td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${otsUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
							   <td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
							</tr>
						 </tbody>
					  </table>
				   </td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->

		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">입금여부를 확인해주세요</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님! <br />
				입금이 지연되고있어 상품을 출고하지 못하고 있습니다.<br />
				입금을 했으나 확인되지 않고있다면 고객센터로 연락 부탁드립니다.<br />
				주문상태는 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 주문/배송 현황 조회</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : 주문 및 입금 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문 및 입금 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderDtm}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderNo}</td>
						</tr>
						<#if nmbrCrtfcNoText?exists>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">비밀번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${nmbrCrtfcNoText}</td>
							</tr>
						</#if>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">입금금액</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pymntTodoAmt}원</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">입금계좌</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pymntOrganName} : ${pymntOrganNoText}&nbsp;(예금주 : ${accountName})</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">입금기한</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${vrtlAcntExprDtm}까지</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 주문 및 입금 정보 -->

			<!-- s : 주문상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 140px;" />
					<col style="width: 130px;" />
				</colgroup>
				<tbody>
					<#list productList as item>
						<tr>
							<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
								<img style="width: 100%" src="${item.imageUrl}" alt="${item.altrnText}">
							</td>
							<td style="border-bottom: 1px solid #e5e5e5;">
								<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${item.brandName}</span>
								<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${item.prdtName}</p>
								<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${item.optnName} / ${item.orderQty}개</span>
							</td>
							<#if item.prdtNormalAmt &gt; item.orderAmt>
								<td style="text-align: center;">
									<div style="margin-bottom: 5px; color: #999; text-decoration: line-through">
										<span style="font-size: 13px;">${item.prdtNormalAmt}</span><span style="font-size: 12px;">원</span>
									</div>
									<span style="font-size: 18px; font-weight: bold;">${item.orderAmt}</span><span style="font-size: 15px;">원</span>
								</td>
							<#else>
								<td style="border-bottom: 1px solid #e5e5e5; text-align: center;">
									<span style="font-size: 18px; font-weight: bold;">${item.orderAmt}</span><span style="font-size: 15px;">원</span>
								</td>
							</#if>
							<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">${item.dlvyTypeCodeName}</td>
						</tr>
					</#list>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 주문상품 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">2개 이상의 상품 주문 시 매장 재고여부에 따라 분리발송 될 수 있습니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">오프라인 매장 발송 상품은 온라인 발송 상품보다 평균 배송기간이 2~3일 정도 더 소요될 수 있습니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">매장 발송 상품의 경우 발송되는 매장정보는 주문 완료 후 <span style="color: #000; font-weight: bold;">마이페이지 &gt; 주문배송 현황조회</span>에서 확인 가능합니다.</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrlPc}" target="_blank" style="display: inline-block; padding: 21px 26px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">주문/배송 현황 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
				   <p style="margin:0;">
					  <span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
					  통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
				   </p>
				</div>
			</div>
		</div>
		<!-- e: footer -->

	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-01003-2',null,'A',0,'SY00000011','2019-07-09 17:43:05','SY00000001','2019-10-15 17:18:55')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10000','입금지연 주문취소 (무통장입금)','[A-RT] 주문이 취소되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		 <tbody>
			<tr style="background-color: #000;">
			   <td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
				  <a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
					 <img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
				  </a>
			   </td>
			   <td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
				  <table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
					 <tbody>
						<tr>
						   <td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
						</tr>
					 </tbody>
				  </table>
			   </td>
			</tr>
		 </tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">주문이 취소되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님! <br /> <span style="color: #ee1c25">${today}</span>에  입금지연으로 주문이 자동 취소되었습니다.
			</p>

			<!-- s : 취소상품 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">취소상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#list productList as item>
						<tr>
							<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
								<img style="width: 100%" src="${item.imageUrl}" alt="${item.altrnText}">
							</td>
							<td style="border-bottom: 1px solid #e5e5e5;">
								<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${item.brandName}</span>
								<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${item.prdtName}</p>
								<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${item.optnName} / ${item.orderQty}개</span>
							</td>
							<#if item.prdtNormalAmt &gt; item.orderAmt>
								<td style="text-align: center;">
									<div style="margin-bottom: 5px; color: #999; text-decoration: line-through">
										<span style="font-size: 13px;">${item.prdtNormalAmt}</span><span style="font-size: 12px;">원</span>
									</div>
									<span style="font-size: 18px; font-weight: bold;">${item.orderAmt}</span><span style="font-size: 15px;">원</span>
								</td>
							<#else>
								<td style="border-bottom: 1px solid #e5e5e5; text-align: center;">
									<span style="font-size: 18px; font-weight: bold;">${item.orderAmt}</span><span style="font-size: 15px;">원</span>
								</td>
							</#if>
							<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">${item.dlvyTypeCodeName}</td>
						</tr>
					</#list>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 취소상품 정보 -->

			<!-- s : 190704 복구 | 환불정보 포인트사용 취소 케이스만 복구 -->
			<!-- s : 환불 정보 -->
			<#if pymntPoint &gt; 0>
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">환불 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: auto;" />
					<col style="width: auto;" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">포인트사용 취소</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold;">${pymntPoint}</span><span style="font-size: 15px;">P</span>
						</td>
					</tr>
				</tbody>
			</table>
			</#if>
			<!-- e: table -->
			<!-- e : 환불 정보 -->
			<!-- e : 190704 복구 | 환불정보 포인트사용 취소 케이스만 복구 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrlPc}" target="_blank" style="display: inline-block; padding: 21px 55px 20px 54px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">마이페이지</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
				   <p style="margin:0;">
					  <span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
					  사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
					  대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
				   </p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-01004-1',null,'A',0,'SY00000011','2019-07-10 18:22:35','SY00000001','2019-09-09 16:26:54')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10000','입금지연 주문취소 (무통장입금)','[ON THE SPOT] 주문이 취소되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
				   <td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
					  <a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
						 <img src="${otsUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
					  </a>
				   </td>
				   <td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
					  <table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
						 <tbody>
							<tr>
							   <td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
							   <td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${otsUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
							   <td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
							</tr>
						 </tbody>
					  </table>
				   </td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">주문이 취소되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님! <br /> <span style="color: #ee1c25">${today}</span>에  입금지연으로 주문이 자동 취소되었습니다.
			</p>

			<!-- s : 취소상품 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">취소상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#list productList as item>
						<tr>
							<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
								<img style="width: 100%" src="${item.imageUrl}" alt="${item.altrnText}">
							</td>
							<td style="border-bottom: 1px solid #e5e5e5;">
								<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${item.brandName}</span>
								<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${item.prdtName}</p>
								<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${item.optnName} / ${item.orderQty}개</span>
							</td>
							<#if item.prdtNormalAmt &gt; item.orderAmt>
								<td style="text-align: center;">
									<div style="margin-bottom: 5px; color: #999; text-decoration: line-through">
										<span style="font-size: 13px;">${item.prdtNormalAmt}</span><span style="font-size: 12px;">원</span>
									</div>
									<span style="font-size: 18px; font-weight: bold;">${item.orderAmt}</span><span style="font-size: 15px;">원</span>
								</td>
							<#else>
								<td style="border-bottom: 1px solid #e5e5e5; text-align: center;">
									<span style="font-size: 18px; font-weight: bold;">${item.orderAmt}</span><span style="font-size: 15px;">원</span>
								</td>
							</#if>
							<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">${item.dlvyTypeCodeName}</td>
						</tr>
					</#list>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 취소상품 정보 -->

			<!-- s : 190704 복구 | 환불정보 포인트사용 취소 케이스만 복구 -->
			<!-- s : 환불 정보 -->
			<#if pymntPoint &gt; 0>
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">환불 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: auto;" />
					<col style="width: auto;" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">포인트사용 취소</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold;">${pymntPoint}</span><span style="font-size: 15px;">P</span>
						</td>
					</tr>
				</tbody>
			</table>
			</#if>
			<!-- e: table -->
			<!-- e : 환불 정보 -->
			<!-- e : 190704 복구 | 환불정보 포인트사용 취소 케이스만 복구 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrlPc}" target="_blank" style="display: inline-block; padding: 21px 55px 20px 54px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">마이페이지</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
				   <p style="margin:0;">
					  <span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
					  통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
				   </p>
				</div>
			</div>
		</div>
		<!-- e: footer -->

	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-01004-2',null,'A',0,'SY00000011','2019-07-10 18:23:50','SY00000001','2019-09-09 16:27:58')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10011','AS접수','[A-RT] AS가 접수되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">AS가 접수되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName?if_exists}님!<br />
				<span style="color: #ee1c25">${asAcceptDtm?if_exists}</span>에 고객님께서 요청하신 AS신청이 접수되었습니다.<br />
				접수 확인 후 1~2일 이내 교환상품 회수지로 택배기사님이 방문합니다. (주말, 공휴일제외)<br />
				AS신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; AS신청</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : AS 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${asAcceptDtm?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${asAcceptNo?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS상품 회수지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${buyerPostCodeText?if_exists}] ${buyerPostAddrText?if_exists}, ${buyerDtlAddrText?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS상품 수령지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${rcvrPostCodeText?if_exists}] ${rcvrPostAddrText?if_exists}, ${rcvrDtlAddrText?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : AS 신청 정보 -->

			<!-- s : AS 상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdt?exists>
						<tr>
							<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
								<img style="width: 100%" src="${prdt.imageUrl?if_exists}?shrink=100:100" alt="${prdt.altrnText?if_exists}">
							</td>
							<td style="border-bottom: 1px solid #e5e5e5;">
								<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName?if_exists}</span>
								<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName?if_exists}</p>
								<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName?if_exists}</span>
							</td>
							<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
								${prdt.asGbnCodeName?if_exists} <span style="display: block; margin-top: 9px; font-size: 13px; letter-spacing: -0.65px; text-align: center; color: #999;">${prdt.asRsnCodeName?if_exists}</span>
							</td>
						</tr>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : AS 상품 정보 -->

			<!-- s : AS 배송비 결제 정보 -->
			<#if prdt?exists>
				<#if prdt.asGbnCode == "10001">
					<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 배송비 결제 정보</h3>
					<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
						<!-- s: table -->
						<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
							<colgroup>
								<col style="width: 140px;" />
								<col style="width: auto;" />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송비</th>
									<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyPymntAmt?if_exists}</td>
								</tr>
								<tr>
									<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">결제방식</th>
									<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">
										<#if dlvyAmtPymnt.pymntMeansCode == "10000">
											${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists} (${dlvyAmtPymnt.cardMask?if_exists}) <#if dlvyAmtPymnt.instmtTermCount == 0>일시불</#if><#if dlvyAmtPymnt.instmtTermCount != 0>${dlvyAmtPymnt.instmtTermCount?if_exists}개월</#if> ${dlvyAmtPymnt.strPymntDtm?if_exists}</p>
										</#if>
										<#if dlvyAmtPymnt.pymntMeansCode == "10002">
											${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists}
										</#if>
									</td>
								</tr>
							</tbody>
						</table>
						<!-- e: table -->
					</div>
				</#if>
			</#if>
			<!-- e : AS 배송비 결제 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">수선 상태에 따라 비용이 발생할 경우 대표계좌로 안내 드립니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">수선 기간은 최소 1~2주(공휴일 제외) 정도 소요되며, 수선 절차에 따라 기간이 다소 차이가 있을 수 있습니다.</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl?if_exists}" target="_blank" style="display: inline-block; padding: 21px 48px 20px 49px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">AS 신청 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-03003-1',null,'A',0,'SY00000057','2019-07-11 16:13:10','SY00000057','2019-09-16 16:30:22')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10011','AS접수','[ON THE SPOT] AS가 접수되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
									<td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">AS가 접수되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName?if_exists}님!<br />
				<span style="color: #ee1c25">${asAcceptDtm?if_exists}</span>에 고객님께서 요청하신 AS신청이 접수되었습니다.<br />
				접수 확인 후 1~2일 이내 교환상품 회수지로 택배기사님이 방문합니다. (주말, 공휴일제외)<br />
				AS신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; AS신청</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : AS 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${asAcceptDtm?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${asAcceptNo?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS상품 회수지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${buyerPostCodeText?if_exists}] ${buyerPostAddrText?if_exists}, ${buyerDtlAddrText?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS상품 수령지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${rcvrPostCodeText?if_exists}] ${rcvrPostAddrText?if_exists}, ${rcvrDtlAddrText?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : AS 신청 정보 -->

			<!-- s : AS 상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdt?exists>
						<tr>
							<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
								<img style="width: 100%" src="${prdt.imageUrl?if_exists}?shrink=100:100" alt="${prdt.altrnText?if_exists}">
							</td>
							<td style="border-bottom: 1px solid #e5e5e5;">
								<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName?if_exists}</span>
								<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName?if_exists}</p>
								<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName?if_exists}</span>
							</td>
							<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
								${prdt.asGbnCodeName?if_exists} <span style="display: block; margin-top: 9px; font-size: 13px; letter-spacing: -0.65px; text-align: center; color: #999;">${prdt.asRsnCodeName?if_exists}</span>
							</td>
						</tr>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : AS 상품 정보 -->

			<!-- s : AS 배송비 결제 정보 -->
			<#if prdt?exists>
				<#if prdt.asGbnCode == "10001">
					<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 배송비 결제 정보</h3>
					<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
						<!-- s: table -->
						<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
							<colgroup>
								<col style="width: 140px;" />
								<col style="width: auto;" />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송비</th>
									<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyPymntAmt?if_exists}</td>
								</tr>
								<tr>
									<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">결제방식</th>
									<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">
										<#if dlvyAmtPymnt.pymntMeansCode == "10000">
											${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists} (${dlvyAmtPymnt.cardMask?if_exists}) <#if dlvyAmtPymnt.instmtTermCount == 0>일시불</#if><#if dlvyAmtPymnt.instmtTermCount != 0>${dlvyAmtPymnt.instmtTermCount?if_exists}개월</#if> ${dlvyAmtPymnt.strPymntDtm?if_exists}</p>
										</#if>
										<#if dlvyAmtPymnt.pymntMeansCode == "10002">
											${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists}
										</#if>
									</td>
								</tr>
							</tbody>
						</table>
						<!-- e: table -->
					</div>
				</#if>
			</#if>
			<!-- e : AS 배송비 결제 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">수선 상태에 따라 비용이 발생할 경우 대표계좌로 안내 드립니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">수선 기간은 최소 1~2주(공휴일 제외) 정도 소요되며, 수선 절차에 따라 기간이 다소 차이가 있을 수 있습니다.</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl?if_exists}" target="_blank" style="display: inline-block; padding: 21px 48px 20px 49px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">AS 신청 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
						통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-03003-2',null,'A',0,'SY00000057','2019-07-11 16:14:12','SY00000057','2019-09-16 16:35:31')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10011','AS접수 완료','[A-RT] AS 접수 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">AS 접수가 완료되어 택배기사님이 방문할 예정입니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName?if_exists}님!<br />
				<span style="color: #ee1c25">${modDtm?if_exists}</span>에 AS 접수가 완료되었습니다.<br />
				1~2일 이내 AS상품 회수지로 택배기사님이 방문합니다. (주말, 공휴일제외)<br />
				AS신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; AS신청</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : AS 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${asAcceptDtm?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${asAcceptNo?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS상품 회수지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${buyerPostCodeText?if_exists}] ${buyerPostAddrText?if_exists}, ${buyerDtlAddrText?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS상품 수령지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${rcvrPostCodeText?if_exists}] ${rcvrPostAddrText?if_exists}, ${rcvrDtlAddrText?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : AS 신청 정보 -->

			<!-- s : AS 상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdt?exists>
						<tr>
							<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
								<#if prdt.imageUrl?exists && prdt.altrnText?exists>
									<img style="width: 100%" src="${prdt.imageUrl?if_exists}?shrink=100:100" alt="${prdt.altrnText?if_exists}">
								</#if>
							</td>
							<td style="border-bottom: 1px solid #e5e5e5;">
								<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName?if_exists}</span>
								<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName?if_exists}</p>
								<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName?if_exists}</span>
							</td>
							<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
								${prdt.asGbnCodeName?if_exists} <span style="display: block; margin-top: 9px; font-size: 13px; letter-spacing: -0.65px; text-align: center; color: #999;">${prdt.asRsnCodeName?if_exists}</span>
							</td>
						</tr>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : AS 상품 정보 -->

			<!-- s : AS 배송비 결제 정보 -->
			<#if prdt?exists>
				<#if prdt.asGbnCode == "10001">
					<#if dlvyPymntMeansName == "">
						<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 배송비 결제 정보</h3>
						<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
							<!-- s: table -->
							<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
								<colgroup>
									<col style="width: 140px;" />
									<col style="width: auto;" />
								</colgroup>
								<tbody>
									<tr>
										<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송비</th>
										<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyPymntAmt?if_exists}</td>
									</tr>
									<tr>
										<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">결제방식</th>
										<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">
											<#if dlvyAmtPymnt.pymntMeansCode == "10000">
												${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists} (${dlvyAmtPymnt.cardMask?if_exists}) <#if dlvyAmtPymnt.instmtTermCount == 0>일시불</#if><#if dlvyAmtPymnt.instmtTermCount != 0>${dlvyAmtPymnt.instmtTermCount?if_exists}개월</#if> ${dlvyAmtPymnt.strPymntDtm?if_exists}</p>
											</#if>
											<#if dlvyAmtPymnt.pymntMeansCode == "10002">
												${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists}
											</#if>
										</td>
									</tr>
								</tbody>
							</table>
							<!-- e: table -->
						</div>
					</#if>
				</#if>
			</#if>
			<!-- e : AS 배송비 결제 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">수선 상태에 따라 비용이 발생할 경우 대표계좌로 안내 드립니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">수선 기간은 최소 1~2주(공휴일 제외) 정도 소요되며, 수선 절차에 따라 기간이 다소 차이가 있을 수 있습니다.</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl?if_exists}" target="_blank" style="display: inline-block; padding: 21px 48px 20px 49px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">AS 신청 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-03009-1',null,'A',0,'SY00000057','2019-07-11 16:21:11','SY00000057','2019-09-17 19:03:21')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10011','AS접수 완료','[ON THE SPOT] AS 접수 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
									<td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">AS 접수가 완료되어 택배기사님이 방문할 예정입니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName?if_exists}님!<br />
				<span style="color: #ee1c25">${modDtm?if_exists}</span>에 AS 접수가 완료되었습니다.<br />
				1~2일 이내 AS상품 회수지로 택배기사님이 방문합니다. (주말, 공휴일제외)<br />
				AS신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; AS신청</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : AS 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${asAcceptDtm?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${asAcceptNo?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS상품 회수지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${buyerPostCodeText?if_exists}] ${buyerPostAddrText?if_exists}, ${buyerDtlAddrText?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS상품 수령지</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${rcvrPostCodeText?if_exists}] ${rcvrPostAddrText?if_exists}, ${rcvrDtlAddrText?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : AS 신청 정보 -->

			<!-- s : AS 상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdt?exists>
						<tr>
							<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
								<#if prdt.imageUrl?shrink=100:100?exists && prdt.altrnText?exists>
									<img style="width: 100%" src="${prdt.imageUrl?if_exists}?shrink=100:100" alt="${prdt.altrnText?if_exists}">
								</#if>
							</td>
							<td style="border-bottom: 1px solid #e5e5e5;">
								<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName?if_exists}</span>
								<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName?if_exists}</p>
								<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName?if_exists}</span>
							</td>
							<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
								${prdt.asGbnCodeName?if_exists} <span style="display: block; margin-top: 9px; font-size: 13px; letter-spacing: -0.65px; text-align: center; color: #999;">${prdt.asRsnCodeName?if_exists}</span>
							</td>
						</tr>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : AS 상품 정보 -->

			<!-- s : AS 배송비 결제 정보 -->
			<#if prdt?exists>
				<#if prdt.asGbnCode == "10001">
					<#if dlvyPymntMeansName == "">
						<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 배송비 결제 정보</h3>
						<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
							<!-- s: table -->
							<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
								<colgroup>
									<col style="width: 140px;" />
									<col style="width: auto;" />
								</colgroup>
								<tbody>
									<tr>
										<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송비</th>
										<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyPymntAmt?if_exists}</td>
									</tr>
									<tr>
										<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">결제방식</th>
										<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">
											<#if dlvyAmtPymnt.pymntMeansCode == "10000">
												${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists} (${dlvyAmtPymnt.cardMask?if_exists}) <#if dlvyAmtPymnt.instmtTermCount == 0>일시불</#if><#if dlvyAmtPymnt.instmtTermCount != 0>${dlvyAmtPymnt.instmtTermCount?if_exists}개월</#if> ${dlvyAmtPymnt.strPymntDtm?if_exists}</p>
											</#if>
											<#if dlvyAmtPymnt.pymntMeansCode == "10002">
												${dlvyPymntMeansName?if_exists} <p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${dlvyAmtPymnt.pymntOrganName?if_exists}
											</#if>
										</td>
									</tr>
								</tbody>
							</table>
							<!-- e: table -->
						</div>
					</#if>
				</#if>
			</#if>
			<!-- e : AS 배송비 결제 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">수선 상태에 따라 비용이 발생할 경우 대표계좌로 안내 드립니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">수선 기간은 최소 1~2주(공휴일 제외) 정도 소요되며, 수선 절차에 따라 기간이 다소 차이가 있을 수 있습니다.</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl?if_exists}" target="_blank" style="display: inline-block; padding: 21px 48px 20px 49px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">AS 신청 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
						통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-03009-2',null,'A',0,'SY00000057','2019-07-11 16:22:21','SY00000057','2019-09-17 19:03:35')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10011','AS완료(심의 결과-수선불가-반송 안내)','[A-RT] AS 수선 불가하여 반송되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">수선 불가하여 반송되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName?if_exists}님!<br />
				<span style="color: #ee1c25">${modDtm?if_exists}</span>에 AS 처리결과 수선이 불가하여 반송되었습니다.<br />
				1~2일 이내 AS상품 회수지로 택배기사님이 방문합니다. (주말, 공휴일제외)<br />
				AS신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; AS신청</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : AS 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${asAcceptDtm?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${asAcceptNo?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : AS 신청 정보 -->

			<!-- s : 배송 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">배송 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송방법</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyTypeCodeName?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">택배사</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${logisVndrName?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">운송장번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${waybilNoText?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 배송 정보 -->

			<!-- s : AS 상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdt?exists>
						<tr>
							<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
								<img style="width: 100%" src="${prdt.imageUrl?if_exists}?shrink=100:100" alt="${prdt.altrnText?if_exists}">
							</td>
							<td style="border-bottom: 1px solid #e5e5e5;">
								<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName?if_exists}</span>
								<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName?if_exists}</p>
								<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName?if_exists}</span>
							</td>
							<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
								${prdt.asStatCodeName?if_exists}
							</td>
						</tr>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : AS 상품 정보 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl?if_exists}" target="_blank" style="display: inline-block; padding: 21px 48px 20px 49px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">AS 신청 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-03006-1',null,'A',0,'SY00000057','2019-07-11 16:30:41','SY00000057','2019-09-16 16:30:55')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10011','AS완료(심의 결과-수선불가-반송 안내)','[ON THE SPOT] AS 수선 불가하여 반송되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
									<td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">수선 불가하여 반송되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName?if_exists}님!<br />
				<span style="color: #ee1c25">${modDtm?if_exists}</span>에 AS 처리결과 수선이 불가하여 반송되었습니다.<br />
				1~2일 이내 AS상품 회수지로 택배기사님이 방문합니다. (주말, 공휴일제외)<br />
				AS신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; AS신청</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : AS 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">

					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${asAcceptDtm?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${asAcceptNo?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : AS 신청 정보 -->

			<!-- s : 배송 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">배송 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송방법</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyTypeCodeName?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">택배사</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${logisVndrName?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">운송장번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${waybilNoText?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 배송 정보 -->

			<!-- s : AS 상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdt?exists>
						<tr>
							<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
								<img style="width: 100%" src="${prdt.imageUrl?if_exists}?shrink=100:100" alt="${prdt.altrnText?if_exists}">
							</td>
							<td style="border-bottom: 1px solid #e5e5e5;">
								<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName?if_exists}</span>
								<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName?if_exists}</p>
								<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName?if_exists}</span>
							</td>
							<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
								${prdt.asStatCodeName?if_exists}
							</td>
						</tr>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : AS 상품 정보 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl?if_exists}" target="_blank" style="display: inline-block; padding: 21px 48px 20px 49px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">AS 신청 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
						통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-03006-2',null,'A',0,'SY00000057','2019-07-11 16:33:31','SY00000057','2019-09-16 16:36:00')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10011','AS완료(수선 완료 배송)','[A-RT] AS 처리가 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">수선이 완료되어 재발송되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName?if_exists}님!<br />
				<span style="color: #ee1c25">${modDtm?if_exists}</span>에 AS 처리 결과 수선이 완료되었습니다.<br />
				AS신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; AS신청</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : AS 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${asAcceptDtm?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${asAcceptNo?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : AS 신청 정보 -->

			<!-- s : 배송 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">배송 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송방법</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyTypeCodeName?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">택배사</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${logisVndrName?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">운송장번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${waybilNoText?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 배송 정보 -->

			<!-- s : AS 상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdt?exists>
						<tr>
							<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
								<#if prdt.imageUrl?exists && prdt.altrnText?exists>
									<img style="width: 100%" src="${prdt.imageUrl?if_exists}?shrink=100:100" alt="${prdt.altrnText?if_exists}">
								</#if>
							</td>
							<td style="border-bottom: 1px solid #e5e5e5;">
								<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName?if_exists}</span>
								<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName?if_exists}</p>
								<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName?if_exists}</span>
							</td>
							<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
								${prdt.asStatCodeName?if_exists}
							</td>
						</tr>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : AS 상품 정보 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl?if_exists}" target="_blank" style="display: inline-block; padding: 21px 48px 20px 49px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">AS 신청  조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-03010-1',null,'A',0,'SY00000057','2019-07-11 16:45:40','SY00000057','2019-09-16 16:30:41')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10011','AS완료(수선 완료 배송)','[ON THE SPOT] AS 처리가 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
									<td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">수선이 완료되어 재발송되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName?if_exists}님!<br />
				<span style="color: #ee1c25">${modDtm?if_exists}</span>에 AS 처리 결과 수선이 완료되었습니다.<br />
				AS신청 내역은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; AS신청</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : AS 신청 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 신청 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS신청일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${asAcceptDtm?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">AS 접수번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${asAcceptNo?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : AS 신청 정보 -->

			<!-- s : 배송 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">배송 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송방법</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyTypeCodeName?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">택배사</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${logisVndrName?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">운송장번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${waybilNoText?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 배송 정보 -->

			<!-- s : AS 상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">AS 상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<#if prdt?exists>
						<tr>
							<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
								<img style="width: 100%" src="${prdt.imageUrl?if_exists}?shrink=100:100" alt="${prdt.altrnText?if_exists}">
							</td>
							<td style="border-bottom: 1px solid #e5e5e5;">
								<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName?if_exists}</span>
								<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName?if_exists}</p>
								<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName?if_exists}</span>
							</td>
							<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
								${prdt.asStatCodeName?if_exists}
							</td>
						</tr>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : AS 상품 정보 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl?if_exists}" target="_blank" style="display: inline-block; padding: 21px 48px 20px 49px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">AS 신청  조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
						통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-03010-2',null,'A',0,'SY00000057','2019-07-11 16:46:53','SY00000057','2019-09-16 16:35:51')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10002','배송	발송안내','[A-RT] 주문 상품이 발송되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		 <tbody>
			<tr style="background-color: #000;">
			   <td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
				  <a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
					 <img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
				  </a>
			   </td>
			   <td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
				  <table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
					 <tbody>
						<tr>
						   <td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
						</tr>
					 </tbody>
				  </table>
			   </td>
			</tr>
		 </tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">주문 상품이 발송되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님! <br />
				주문하신 상품을 발송했습니다. (1~2일 후 조회 가능합니다.) <br />
				배송기간은 2~3일 정도 소요되며 택배사 사정에 의해 지연될 수 있습니다. <br />
				배송상태 확인 및 배송정보 수정은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 주문/배송 현황 조회</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : 주문 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderDtm}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderNo}</td>
						</tr>
						<#if nmbrCrtfcNoText?exists>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">비밀번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${nmbrCrtfcNoText}</td>
							</tr>
						</#if>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 주문 정보 -->

			<!-- s : 배송 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">배송 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송방법</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyTypeCodeName?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">택배사</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${logisVndrCodeName?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">운송장번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${waybilNoText?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 배송 정보 -->

			<!-- s : 발송상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">발송상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 140px;" />
					<col style="width: 130px;" />
				</colgroup>
				<tbody>
					<tr>
						<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
							<img style="width: 100%" src="${imageUrl?if_exists}" alt="${altrnText?if_exists}">
						</td>
						<td style="border-bottom: 1px solid #e5e5e5;">
							<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${brandName}</span>
							<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdtName}</p>
							<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${optnName?if_exists} / ${orderQty}개</span>
						</td>
						<#if prdtNormalAmt &gt; orderAmt>
							<td style="text-align: center;">
								<div style="margin-bottom: 5px; color: #999; text-decoration: line-through">
									<span style="font-size: 13px;">${prdtNormalAmt}</span><span style="font-size: 12px;">원</span>
								</div>
								<span style="font-size: 18px; font-weight: bold;">${orderAmt}</span><span style="font-size: 15px;">원</span>
							</td>
						<#else>
							<td style="border-bottom: 1px solid #e5e5e5; text-align: center;">
								<span style="font-size: 18px; font-weight: bold;">${orderAmt}</span><span style="font-size: 15px;">원</span>
							</td>
						</#if>
						<#if storeNo?exists>
							<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
								매장 <span style="display: block; margin-top: 9px; font-size: 13px; letter-spacing: -0.65px; text-align: center; color: #999;">${storeName?if_exists}</span>
							</td>
						<#else>
							<td style="font-size: 14px; text-align: center">온라인</td>
						</#if>
					</tr>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 발송상품 정보 -->

			<!-- s : 배송지 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">배송지 정보</h3>
			<#if dlvyTypeCode == ''10000''>
				<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
					<!-- s: table -->
					<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
						<colgroup>
							<col style="width: 140px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberName}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">휴대폰 번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${buyerHdphnNoText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송주소</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${rcvrPostCodeText?if_exists}] ${rcvrPostAddrText?if_exists} ${rcvrDtlAddrText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송메시지</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyMemoText?if_exists}</td>
							</tr>
						</tbody>
					</table>
					<!-- e: table -->
				</div>

			<!-- s : 배송지 정보 > 편의점픽업인 경우 -->
			<#elseif dlvyTypeCode == ''10001''>
				<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
					<!-- s: table -->
					<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
						<colgroup>
							<col style="width: 140px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberName}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">휴대폰 번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${buyerHdphnNoText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">상품수령 편의점</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">
									${cvnstrName} (${cvnstrTelNoText})<br />${cvnstrPostCodeText?if_exists} ${cvnstrPostAddrText?if_exists} ${cvnstrDtlAddrText?if_exists}<br />
									<p style="margin: 0; color: #ee1c25;">편의점 픽업 상품은 픽업준비완료 시점부터 7일 이내 수령해야 하며,<br />기간 내 미 수령 시 배송비 고객 부담으로 반품처리 됩니다.</p>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- e: table -->
				</div>
			<!-- s : 배송지 정보 > 편의점픽업인 경우 -->

			<!-- s : 배송지 정보 > 매장픽업인 경우 -->
			<#elseif dlvyTypeCode == ''10002''>
				<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
					<!-- s: table -->
					<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
						<colgroup>
							<col style="width: 140px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberName}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">휴대폰 번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${buyerHdphnNoText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">픽업코드</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pickUpCode?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">수령지점</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${storeName?if_exists} <br />${storePostAddrText?if_exists} ${storeDtlAddrText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">영업정보</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">영업시간 : ${businessStartTime?if_exists} ~ ${businessEndTime?if_exists} <br />전화번호 : ${storeTelNoText?if_exists}</td>
							</tr>
						</tbody>
					</table>
					<!-- e: table -->
				</div>
			<!-- e : 배송지 정보 > 매장픽업인 경우 -->
			</#if>
			
			<!-- e : 배송지 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">2개 이상의 상품 주문 시 매장 재고여부에 따라 분리발송 될 수 있습니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">오프라인 매장 발송 상품은 온라인 발송 상품보다 평균 배송기간이 2~3일 정도 더 소요될 수 있습니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">매장 발송 상품의 경우 발송되는 매장정보는 주문 완료 후 <span style="color: #000; font-weight: bold;">마이페이지 &gt; 주문배송 현황조회</span>에서 확인 가능합니다.</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrlPc}" target="_blank" style="display: inline-block; padding: 21px 26px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">주문/배송 현황 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
				   <p style="margin:0;">
					  <span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
					  사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
					  대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
				   </p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-02001-1',null,'A',0,'SY00000011','2019-07-23 15:13:08','SY00000001','2019-09-09 16:28:36')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10002','배송	발송안내','[ON THE SPOT] 주문 상품이 발송되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
				   <td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
					  <a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
						 <img src="${otsUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
					  </a>
				   </td>
				   <td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
					  <table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
						 <tbody>
							<tr>
							   <td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
							   <td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${otsUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
							   <td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
							</tr>
						 </tbody>
					  </table>
				   </td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">주문 상품이 발송되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님! <br />
				주문하신 상품을 발송했습니다. (1~2일 후 조회 가능합니다.) <br />
				배송기간은 2~3일 정도 소요되며 택배사 사정에 의해 지연될 수 있습니다. <br />
				배송상태 확인 및 배송정보 수정은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 주문/배송 현황 조회</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : 주문 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderDtm}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderNo}</td>
						</tr>
						<#if nmbrCrtfcNoText?exists>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">비밀번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${nmbrCrtfcNoText}</td>
							</tr>
						</#if>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 주문 정보 -->

			<!-- s : 배송 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">배송 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송방법</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyTypeCodeName?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">택배사</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${logisVndrCodeName?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">운송장번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${waybilNoText?if_exists}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 배송 정보 -->

			<!-- s : 발송상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">발송상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 140px;" />
					<col style="width: 130px;" />
				</colgroup>
				<tbody>
					<tr>
						<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
							<img style="width: 100%" src="${imageUrl?if_exists}" alt="${altrnText?if_exists}">
						</td>
						<td style="border-bottom: 1px solid #e5e5e5;">
							<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${brandName}</span>
							<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdtName}</p>
							<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${optnName?if_exists} / ${orderQty}개</span>
						</td>
						<#if prdtNormalAmt &gt; orderAmt>
							<td style="text-align: center;">
								<div style="margin-bottom: 5px; color: #999; text-decoration: line-through">
									<span style="font-size: 13px;">${prdtNormalAmt}</span><span style="font-size: 12px;">원</span>
								</div>
								<span style="font-size: 18px; font-weight: bold;">${orderAmt}</span><span style="font-size: 15px;">원</span>
							</td>
						<#else>
							<td style="border-bottom: 1px solid #e5e5e5; text-align: center;">
								<span style="font-size: 18px; font-weight: bold;">${orderAmt}</span><span style="font-size: 15px;">원</span>
							</td>
						</#if>
						<#if storeNo?exists>
							<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">
								매장 <span style="display: block; margin-top: 9px; font-size: 13px; letter-spacing: -0.65px; text-align: center; color: #999;">${storeName?if_exists}</span>
							</td>
						<#else>
							<td style="font-size: 14px; text-align: center">온라인</td>
						</#if>
					</tr>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 발송상품 정보 -->

			<!-- s : 배송지 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">배송지 정보</h3>
			<#if dlvyTypeCode == ''10000''>	
				<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
					<!-- s: table -->
					<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
						<colgroup>
							<col style="width: 140px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberName}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">휴대폰 번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${buyerHdphnNoText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송주소</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${rcvrPostCodeText?if_exists}] ${rcvrPostAddrText?if_exists} ${rcvrDtlAddrText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송메시지</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyMemoText?if_exists}</td>
							</tr>
						</tbody>
					</table>
					<!-- e: table -->
				</div>

			<!-- s : 배송지 정보 > 편의점픽업인 경우 -->
			<#elseif dlvyTypeCode == ''10001''>
				<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
					<!-- s: table -->
					<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
						<colgroup>
							<col style="width: 140px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberName}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">휴대폰 번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${buyerHdphnNoText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">상품수령 편의점</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">
									${cvnstrName} (${cvnstrTelNoText})<br />${cvnstrPostCodeText?if_exists} ${cvnstrPostAddrText?if_exists} ${cvnstrDtlAddrText?if_exists}<br />
									<p style="margin: 0; color: #ee1c25;">편의점 픽업 상품은 픽업준비완료 시점부터 7일 이내 수령해야 하며,<br />기간 내 미 수령 시 배송비 고객 부담으로 반품처리 됩니다.</p>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- e: table -->
				</div>
			<!-- s : 배송지 정보 > 편의점픽업인 경우 -->

			<!-- s : 배송지 정보 > 매장픽업인 경우 -->
			<#elseif dlvyTypeCode == ''10002''>
				<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
					<!-- s: table -->
					<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
						<colgroup>
							<col style="width: 140px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberName}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">휴대폰 번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${buyerHdphnNoText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">픽업코드</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pickUpCode?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">수령지점</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${storeName?if_exists} <br />${storePostAddrText?if_exists} ${storeDtlAddrText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">영업정보</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">영업시간 : ${businessStartTime?if_exists} ~ ${businessEndTime?if_exists} <br />전화번호 : ${storeTelNoText?if_exists}</td>
							</tr>
						</tbody>
					</table>
					<!-- e: table -->
				</div>
			<!-- e : 배송지 정보 > 매장픽업인 경우 -->
			</#if>
			
			<!-- e : 배송지 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">2개 이상의 상품 주문 시 매장 재고여부에 따라 분리발송 될 수 있습니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">오프라인 매장 발송 상품은 온라인 발송 상품보다 평균 배송기간이 2~3일 정도 더 소요될 수 있습니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">매장 발송 상품의 경우 발송되는 매장정보는 주문 완료 후 <span style="color: #000; font-weight: bold;">마이페이지 &gt; 주문배송 현황조회</span>에서 확인 가능합니다.</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrlPc}" target="_blank" style="display: inline-block; padding: 21px 26px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">주문/배송 현황 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
				   <p style="margin:0;">
					  <span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
					  통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
				   </p>
				</div>
			</div>
		</div>
		<!-- e: footer -->

	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-02001-2',null,'A',0,'SY00000011','2019-07-23 15:14:10','SY00000001','2019-09-09 16:29:44')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10002','배송완료(구매확정 안내)','[A-RT] 주문하신 상품 잘 받으셨나요?','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		 <tbody>
			<tr style="background-color: #000;">
			   <td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
				  <a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
					 <img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
				  </a>
			   </td>
			   <td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
				  <table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
					 <tbody>
						<tr>
						   <td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
						</tr>
					 </tbody>
				  </table>
			   </td>
			</tr>
		 </tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">주문하신 상품 잘 받으셨나요?</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님! <br />
				<span style="color: #ee1c25">${orderDtm}</span>에 주문하신 상품은 잘 받으셨나요? <br />
				주문하신 상품을 확인하신 후 마음에 드신다면 <span style="color: #ee1c25">구매확정</span>을 해주시기 바랍니다.<br />
				(발송완료 후 14일 이내에 구매확정을 하지 않으시면 자동 구매확정 처리됩니다.)
			</p>

			<!-- s : 주문상품 정보 -->
			<h3 style="margin: 45px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<tbody>
					<tr>
						<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
							<img style="width: 100%" src="${imageUrl?if_exists}" alt="${altrnText?if_exists}">
						</td>
						<td style="border-bottom: 1px solid #e5e5e5;">
							<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${brandName}</span>
							<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdtName}</p>
							<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${optnName?if_exists} / ${orderQty}개</span>
						</td>
						<#if prdtNormalAmt &gt; orderAmt>
							<td style="text-align: center;">
								<div style="margin-bottom: 5px; color: #999; text-decoration: line-through">
									<span style="font-size: 13px;">${prdtNormalAmt}</span><span style="font-size: 12px;">원</span>
								</div>
								<span style="font-size: 18px; font-weight: bold;">${orderAmt}</span><span style="font-size: 15px;">원</span>
							</td>
						<#else>
							<td style="border-bottom: 1px solid #e5e5e5; text-align: center;">
								<span style="font-size: 18px; font-weight: bold;">${orderAmt}</span><span style="font-size: 15px;">원</span>
							</td>
						</#if>
					</tr>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 주문상품 정보 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrlPc}" target="_blank" style="display: inline-block; padding: 21px 26px 20px; margin-left: 10px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">주문/배송 현황 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
				   <p style="margin:0;">
					  <span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
					  사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
					  대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
				   </p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-02002-1',null,'A',0,'SY00000011','2019-07-23 19:11:02','SY00000001','2019-09-09 16:30:05')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10002','배송완료(구매확정 안내)',' [On the spot] 주문하신 상품 잘 받으셨나요?','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
				   <td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
					  <a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
						 <img src="${otsUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
					  </a>
				   </td>
				   <td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
					  <table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
						 <tbody>
							<tr>
							   <td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
							   <td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${otsUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
							   <td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
							</tr>
						 </tbody>
					  </table>
				   </td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">주문하신 상품 잘 받으셨나요?</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님! <br />
				<span style="color: #ee1c25">${orderDtm}</span>에 주문하신 상품은 잘 받으셨나요? <br />
				주문하신 상품을 확인하신 후 마음에 드신다면 <span style="color: #ee1c25">구매확정</span>을 해주시기 바랍니다.<br />
				(발송완료 후 14일 이내에 구매확정을 하지 않으시면 자동 구매확정 처리됩니다.)
			</p>

			<!-- s : 주문상품 정보 -->
			<h3 style="margin: 45px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<tbody>
					<tr>
						<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
							<img style="width: 100%" src="${imageUrl?if_exists}" alt="${altrnText?if_exists}">
						</td>
						<td style="border-bottom: 1px solid #e5e5e5;">
							<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${brandName}</span>
							<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdtName}</p>
							<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${optnName?if_exists} / ${orderQty}개</span>
						</td>
						<#if prdtNormalAmt &gt; orderAmt>
							<td style="text-align: center;">
								<div style="margin-bottom: 5px; color: #999; text-decoration: line-through">
									<span style="font-size: 13px;">${prdtNormalAmt}</span><span style="font-size: 12px;">원</span>
								</div>
								<span style="font-size: 18px; font-weight: bold;">${orderAmt}</span><span style="font-size: 15px;">원</span>
							</td>
						<#else>
							<td style="border-bottom: 1px solid #e5e5e5; text-align: center;">
								<span style="font-size: 18px; font-weight: bold;">${orderAmt}</span><span style="font-size: 15px;">원</span>
							</td>
						</#if>
					</tr>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 주문상품 정보 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrlPc}" target="_blank" style="display: inline-block; padding: 21px 26px 20px; margin-left: 10px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">주문/배송 현황 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
				   <p style="margin:0;">
					  <span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
					  통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
				   </p>
				</div>
			</div>
		</div>
		<!-- e: footer -->

	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-02002-2',null,'A',0,'SY00000011','2019-07-23 19:12:04','SY00000001','2019-09-09 16:30:32')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10002','매장픽업(편의점픽업)상품 수령가능일 안내','[A-RT] 상품이 픽업준비 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		 <tbody>
			<tr style="background-color: #000;">
			   <td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
				  <a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
					 <img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
				  </a>
			   </td>
			   <td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
				  <table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
					 <tbody>
						<tr>
						   <td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
						</tr>
					 </tbody>
				  </table>
			   </td>
			</tr>
		 </tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">상품이 픽업준비 완료되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님! <br />
				주문하신 상품이 <span style="color: #ee1c25;"><#if dlvyTypeCode == ''10001''>${cvnstrName?if_exists}<#else>${storeName?if_exists}</#if></span>에 준비 완료되었습니다.<br />
				<span style="color: #ee1c25;">${pickupPsbltYmd?if_exists}</span> 까지 해당 매장으로 방문해주시기 바랍니다.<br />
				미방문 시 주문이 취소됩니다.
			</p>

			<!-- s : 주문 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderDtm}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderNo}</td>
						</tr>
						<#if nmbrCrtfcNoText?exists>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">비밀번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${nmbrCrtfcNoText}</td>
							</tr>
						</#if>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 주문 정보 -->

			<!-- s : 주문상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<tr>
						<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
							<img style="width: 100%" src="${imageUrl?if_exists}" alt="${altrnText?if_exists}">
						</td>
						<td style="border-bottom: 1px solid #e5e5e5;">
							<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${brandName}</span>
							<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdtName}</p>
							<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${optnName?if_exists} / ${orderQty}개</span>
						</td>
						<#if prdtNormalAmt &gt; orderAmt>
							<td style="text-align: center;">
								<div style="margin-bottom: 5px; color: #999; text-decoration: line-through">
									<span style="font-size: 13px;">${prdtNormalAmt}</span><span style="font-size: 12px;">원</span>
								</div>
								<span style="font-size: 18px; font-weight: bold;">${orderAmt}</span><span style="font-size: 15px;">원</span>
							</td>
						<#else>
							<td style="border-bottom: 1px solid #e5e5e5; text-align: center;">
								<span style="font-size: 18px; font-weight: bold;">${orderAmt}</span><span style="font-size: 15px;">원</span>
							</td>
						</#if>
					</tr>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 주문상품 정보 -->

			<!-- s : 픽업매장 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">픽업매장 정보</h3>
			
			
			<!-- s : 배송지 정보 > 편의점픽업인 경우 -->
			<#if dlvyTypeCode == ''10001''>
				<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
					<!-- s: table -->
					<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
						<colgroup>
							<col style="width: 140px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberName}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">휴대폰 번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${buyerHdphnNoText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">상품수령 편의점</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">
									${cvnstrName} (${cvnstrTelNoText})<br />${cvnstrPostCodeText?if_exists} ${cvnstrPostAddrText?if_exists} ${cvnstrDtlAddrText?if_exists}<br />
									<p style="margin: 0; color: #ee1c25;">편의점 픽업 상품은 픽업준비완료 시점부터 7일 이내 수령해야 하며,<br />기간 내 미 수령 시 배송비 고객 부담으로 반품처리 됩니다.</p>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- e: table -->
				</div>
			<!-- s : 배송지 정보 > 편의점픽업인 경우 -->

			<!-- s : 배송지 정보 > 매장픽업인 경우 -->
			<#elseif dlvyTypeCode == ''10002''>
				<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
					<!-- s: table -->
					<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
						<colgroup>
							<col style="width: 140px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberName}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">휴대폰 번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${buyerHdphnNoText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">픽업코드</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pickUpCode?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">수령지점</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${storeName?if_exists} <br />${storePostAddrText?if_exists} ${storeDtlAddrText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">영업정보</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">영업시간 : ${businessStartTime?if_exists} ~ ${businessEndTime?if_exists} <br />전화번호 : ${storeTelNoText?if_exists}</td>
							</tr>
						</tbody>
					</table>
					<!-- e: table -->
				</div>
			<!-- e : 배송지 정보 > 매장픽업인 경우 -->
			</#if>		
			<!-- e : 픽업매장 정보 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrlPc}" target="_blank" style="display: inline-block; padding: 21px 26px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">주문/배송 현황 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
				   <p style="margin:0;">
					  <span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
					  사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
					  대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
				   </p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-02004-1',null,'A',0,'SY00000011','2019-07-24 10:46:08','SY00000001','2019-09-09 16:31:00')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10002','매장픽업(편의점픽업)상품 수령가능일 안내','[ON THE SPOT] 상품이 픽업준비 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
				   <td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
					  <a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
						 <img src="${otsUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
					  </a>
				   </td>
				   <td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
					  <table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
						 <tbody>
							<tr>
							   <td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
							   <td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${otsUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
							   <td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
							</tr>
						 </tbody>
					  </table>
				   </td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">상품이 픽업준비 완료되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님! <br />
				주문하신 상품이 <span style="color: #ee1c25;"><#if dlvyTypeCode == ''10001''>${cvnstrName?if_exists}<#else>${storeName?if_exists}</#if></span>에 준비 완료되었습니다.<br />
				<span style="color: #ee1c25;">${pickupPsbltYmd?if_exists}</span> 까지 해당 매장으로 방문해주시기 바랍니다.<br />
				미방문 시 주문이 취소됩니다.
			</p>

			<!-- s : 주문 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderDtm}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderNo}</td>
						</tr>
						<#if nmbrCrtfcNoText?exists>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">비밀번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${nmbrCrtfcNoText}</td>
							</tr>
						</#if>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 주문 정보 -->

			<!-- s : 주문상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<tr>
						<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
							<img style="width: 100%" src="${imageUrl?if_exists}" alt="${altrnText?if_exists}">
						</td>
						<td style="border-bottom: 1px solid #e5e5e5;">
							<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${brandName}</span>
							<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdtName}</p>
							<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${optnName?if_exists} / ${orderQty}개</span>
						</td>
						<#if prdtNormalAmt &gt; orderAmt>
							<td style="text-align: center;">
								<div style="margin-bottom: 5px; color: #999; text-decoration: line-through">
									<span style="font-size: 13px;">${prdtNormalAmt}</span><span style="font-size: 12px;">원</span>
								</div>
								<span style="font-size: 18px; font-weight: bold;">${orderAmt}</span><span style="font-size: 15px;">원</span>
							</td>
						<#else>
							<td style="border-bottom: 1px solid #e5e5e5; text-align: center;">
								<span style="font-size: 18px; font-weight: bold;">${orderAmt}</span><span style="font-size: 15px;">원</span>
							</td>
						</#if>
					</tr>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 주문상품 정보 -->

			<!-- s : 픽업매장 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">픽업매장 정보</h3>
			
			
			<!-- s : 배송지 정보 > 편의점픽업인 경우 -->
			<#if dlvyTypeCode == ''10001''>
				<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
					<!-- s: table -->
					<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
						<colgroup>
							<col style="width: 140px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberName}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">휴대폰 번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${buyerHdphnNoText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">상품수령 편의점</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">
									${cvnstrName} (${cvnstrTelNoText})<br />${cvnstrPostCodeText?if_exists} ${cvnstrPostAddrText?if_exists} ${cvnstrDtlAddrText?if_exists}<br />
									<p style="margin: 0; color: #ee1c25;">편의점 픽업 상품은 픽업준비완료 시점부터 7일 이내 수령해야 하며,<br />기간 내 미 수령 시 배송비 고객 부담으로 반품처리 됩니다.</p>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- e: table -->
				</div>
			<!-- s : 배송지 정보 > 편의점픽업인 경우 -->

			<!-- s : 배송지 정보 > 매장픽업인 경우 -->
			<#elseif dlvyTypeCode == ''10002''>
				<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
					<!-- s: table -->
					<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
						<colgroup>
							<col style="width: 140px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberName}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">휴대폰 번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${buyerHdphnNoText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">픽업코드</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pickUpCode?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">수령지점</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${storeName?if_exists} <br />${storePostAddrText?if_exists} ${storeDtlAddrText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">영업정보</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">영업시간 : ${businessStartTime?if_exists} ~ ${businessEndTime?if_exists} <br />전화번호 : ${storeTelNoText?if_exists}</td>
							</tr>
						</tbody>
					</table>
					<!-- e: table -->
				</div>
			<!-- e : 배송지 정보 > 매장픽업인 경우 -->
			</#if>		
			<!-- e : 픽업매장 정보 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrlPc}" target="_blank" style="display: inline-block; padding: 21px 26px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">주문/배송 현황 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
				   <p style="margin:0;">
					  <span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
					  통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
				   </p>
				</div>
			</div>
		</div>
		<!-- e: footer -->

	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-02004-2',null,'A',0,'SY00000011','2019-07-24 10:47:28','SY00000001','2019-09-09 16:31:28')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10002','매장픽업(편의점픽업)상품 수령완료 안내','[A-RT] 주문하신 상품의 픽업이 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		 <tbody>
			<tr style="background-color: #000;">
			   <td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
				  <a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
					 <img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
				  </a>
			   </td>
			   <td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
				  <table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
					 <tbody>
						<tr>
						   <td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
						   <td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
						   <td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
						</tr>
					 </tbody>
				  </table>
			   </td>
			</tr>
		 </tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">주문하신 상품 잘 받으셨나요?</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님! <br />
				<span style="color: #ee1c25">${orderDtm}</span>에 주문하신 상품은 잘 받으셨나요? <br />
				주문하신 상품을 확인하신 후 마음에 드신다면 <span style="color: #ee1c25">구매확정</span>을 해주시기 바랍니다.<br />
				(픽업완료 후 14일 이내에 구매확정을 하지 않으시면 자동 구매확정 처리됩니다.)
			</p>

			<!-- s : 주문상품 정보 -->
			<h3 style="margin: 45px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<tbody>
					<tr>
						<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
							<img style="width: 100%" src="${imageUrl?if_exists}" alt="${altrnText?if_exists}">
						</td>
						<td style="border-bottom: 1px solid #e5e5e5;">
							<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${brandName}</span>
							<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdtName}</p>
							<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${optnName?if_exists} / ${orderQty}개</span>
						</td>
						<#if prdtNormalAmt &gt; orderAmt>
							<td style="text-align: center;">
								<div style="margin-bottom: 5px; color: #999; text-decoration: line-through">
									<span style="font-size: 13px;">${prdtNormalAmt}</span><span style="font-size: 12px;">원</span>
								</div>
								<span style="font-size: 18px; font-weight: bold;">${orderAmt}</span><span style="font-size: 15px;">원</span>
							</td>
						<#else>
							<td style="border-bottom: 1px solid #e5e5e5; text-align: center;">
								<span style="font-size: 18px; font-weight: bold;">${orderAmt}</span><span style="font-size: 15px;">원</span>
							</td>
						</#if>
					</tr>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 주문상품 정보 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrlPc}" target="_blank" style="display: inline-block; padding: 21px 26px 20px; margin-left: 10px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">주문/배송 현황 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
				   <p style="margin:0;">
					  <span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
					  사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
					  대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
				   </p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-02005-1',null,'A',0,'SY00000011','2019-07-24 11:06:41','SY00000001','2019-09-09 16:34:13')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10002','매장픽업(편의점픽업)상품 수령완료 안내','[ON THE SPOT] 주문하신 상품의 픽업이 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
				   <td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
					  <a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
						 <img src="${otsUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
					  </a>
				   </td>
				   <td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
					  <table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
						 <tbody>
							<tr>
							   <td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
							   <td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${otsUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
							   <td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
							</tr>
						 </tbody>
					  </table>
				   </td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">주문하신 상품 잘 받으셨나요?</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님! <br />
				<span style="color: #ee1c25">${orderDtm}</span>에 주문하신 상품은 잘 받으셨나요? <br />
				주문하신 상품을 확인하신 후 마음에 드신다면 <span style="color: #ee1c25">구매확정</span>을 해주시기 바랍니다.<br />
				(픽업완료 후 14일 이내에 구매확정을 하지 않으시면 자동 구매확정 처리됩니다.)
			</p>

			<!-- s : 주문상품 정보 -->
			<h3 style="margin: 45px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 160px;" />
				</colgroup>
				<tbody>
					<tbody>
					<tr>
						<td style="padding: 20px 15px; border-bottom: 1px solid #e5e5e5; font-size: 0;">
							<img style="width: 100%" src="${imageUrl?if_exists}" alt="${altrnText?if_exists}">
						</td>
						<td style="border-bottom: 1px solid #e5e5e5;">
							<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${brandName}</span>
							<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdtName}</p>
							<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${optnName?if_exists} / ${orderQty}개</span>
						</td>
						<#if prdtNormalAmt &gt; orderAmt>
							<td style="text-align: center;">
								<div style="margin-bottom: 5px; color: #999; text-decoration: line-through">
									<span style="font-size: 13px;">${prdtNormalAmt}</span><span style="font-size: 12px;">원</span>
								</div>
								<span style="font-size: 18px; font-weight: bold;">${orderAmt}</span><span style="font-size: 15px;">원</span>
							</td>
						<#else>
							<td style="border-bottom: 1px solid #e5e5e5; text-align: center;">
								<span style="font-size: 18px; font-weight: bold;">${orderAmt}</span><span style="font-size: 15px;">원</span>
							</td>
						</#if>
					</tr>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 주문상품 정보 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrlPc}" target="_blank" style="display: inline-block; padding: 21px 26px 20px; margin-left: 10px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">주문/배송 현황 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
				   <p style="margin:0;">
					  <span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
					  통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
				   </p>
				</div>
			</div>
		</div>
		<!-- e: footer -->

	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-02005-2',null,'A',0,'SY00000011','2019-07-24 11:07:45','SY00000001','2019-09-09 16:34:46')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10000','기프트카드 주문완료','기프트카드 결제가 완료되었습니다','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
<!-- s: wrap -->
<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
	<!-- s : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
	<!-- s: header -->
	<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<tbody>
			<tr style="background-color: #000;">
				<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
					<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
						<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
					</a>
				</td>
				<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
					<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
						<tbody>
							<tr>
								<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
								<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
								<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
								<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
								<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="#" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
								<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
								<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- e: header -->
	<!-- e : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: content -->

<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">${title}</h2>

<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">안녕하세요, ${memberName}님!<br />
A-RT를 이용해 주셔서 감사합니다.<br />
기프트카드의 구매/선물내역은 <span style="font-weight: bold;">마이페이지 &gt; My 기프트카드 &gt; 구매/선물내역</span> 에서 확인하실 수 있습니다.</p>
<!-- s : 기프트카드 주문 정보 -->

<h3 style="margin: 70px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">기프트카드 주문 정보</h3>

<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;"><!-- s: table -->
<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
	<colgroup>
		<col style="width: 140px;" />
		<col style="width: auto;" />
	</colgroup>
	<tbody>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문일</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${payDtm}</td>
		</tr>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문번호</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderNum}</td>
		</tr>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">기프트카드 번호</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${cardNum}</td>
		</tr>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">기프트카드명</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${cardName}</td>
		</tr>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">유효종료일</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${endDtm}</td>
		</tr>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">받는사람</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${rcvrName} (${telNum})</td>
		</tr>
	</tbody>
</table>
<!-- e: table --></div>
<!-- e : 기프트카드 주문 정보 --><!-- s : 결제 정보 -->

<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">결제 정보</h3>

<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;"><!-- s: table -->
<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
	<colgroup>
		<col style="width: 140px;" />
		<col style="width: auto;" />
	</colgroup>
	<tbody>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">결제일시</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${paymentDtm}</td>
		</tr>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">결제수단</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pymntMeans}</td>
		</tr>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">결제금액</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${payAmt}원</td>
		</tr>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">상태</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">결제완료</td>
		</tr>
	</tbody>
</table>
<!-- e: table --></div>
<!-- e : 결제 정보 -->

<div style="margin-top: 50px; font-size: 0;"><a href="${abcUrl}/mypage/get-giftcard-history" style="display: inline-block; padding: 21px 16px 20px 17px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" target="_blank" title="새창 열림">기프트카드 구매/선물내역</a></div>
</div>
<!-- e: content --><!-- s: footer -->

<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
<div style="padding: 20px 0 21px;">
<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" style="color: #666;" target="_blank">고객센터</a>로 문의해 주시기 바랍니다.</p>
</div>

<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
<p style="margin:0;"><span style="color: #000">(주)에이비씨마트 코리아</span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<br />
사업자등록번호 201-81-76174통신판매업신고 제 2015-서울중고-1036호대표이사 이기호개인정보보호 책임자 박영수<br />
대표번호 080-701-7770, 1588-9667전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span></p>
</div>
</div>
</div>
<!-- e: footer --></div>
<!-- e: wrap -->
</body>
</html>',null,'EC-07001',null,'A',0,'SY00000104','2019-07-25 09:36:07','SY00000004','2019-11-07 12:05:03')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10000','기프트카드 결제 취소 안내','기프트카드 결제가 취소 되었습니다','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
	<!-- s : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
	<!-- s: header -->
	<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<tbody>
			<tr style="background-color: #000;">
				<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
					<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
						<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
					</a>
				</td>
				<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
					<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
						<tbody>
							<tr>
								<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
								<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
								<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
								<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
								<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="#" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
								<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
								<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- e: header -->
	<!-- e : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">기프트카드 결제가 취소되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님!<br />
				<span style="color: #ee1c25">${payDtm}</span>에 고객님께서 신청하신 결제 취소가 정상적으로 처리되었습니다.<br />
				주문 시 고객님께서 <span style="color: #ee1c25">결제하신 결제수단으로 결제금액이 환불</span>됩니다.
			</p>

			<!-- s : 취소 정보 -->
			<h3 style="margin: 70px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">취소 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${payDtm}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderNum}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">기프트카드 번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${cardNum}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 취소 정보 -->

			<!-- s : 환불 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">환불 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: auto;" />
					<col style="width: auto;" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">${pymntMeans} 취소</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold;">${payAmt}</span><span style="font-size: 15px;">원</span>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 환불 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">신용카드 승인취소 여부는 카드사를 통해 3~4일 후 확인 가능합니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">실시간 계좌이체의 경우 당일은 즉시 환불, 주문일을 경과한 경우는 3일 정도 소요됩니다.</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${abcUrl}/mypage/get-giftcard-history" target="_blank" style="display: inline-block; padding: 21px 16px 20px 17px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">기프트카드 구매/선물내역</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-07002',null,'A',0,'SY00000104','2019-07-25 09:37:17','SY00000004','2019-11-07 13:26:35')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10000','결제수당 변경 안내','[A-RT] 결제수단이 변경되었습니다.','<meta charset="UTF-8"><meta http-equiv="X-UA-Compatible" content="ie=edge">
<title></title>
<style type="text/css">html, body { margin: 0; padding: 0; }
</style>
<!-- s: wrap -->
<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;"><!-- s: header -->
<table align="center" border="0" cellpadding="0" cellspacing="0" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
	<tbody>
		<tr style="background-color: #000;">
			<td style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;" valign="top"><a href="${abcUrl}" style="margin: 0; padding: 0; vertical-align: top; " target="_blank" title="새창 열림"><img alt="A-RT" src="${imgPath}/images/art_logo.png" style="border: 0; vertical-align: top;" /> </a></td>
			<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
			<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
				<tbody>
					<tr>
						<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
						<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img alt="" src="${imgPath}/images/util_line.gif" valign="top" /></td>
						<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트&middot;쿠폰</a></td>
						<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img alt="" src="${imgPath}/images/util_line.gif" valign="top" /></td>
						<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
						<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img alt="" src="${imgPath}/images/util_line.gif" valign="top" /></td>
						<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
	</tbody>
</table>
<!-- e: header --><!-- s: content -->

<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">결제수단이 변경되었습니다.</h2>

<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">안녕하세요, ${memberName}님!<br />
결제수단 변경 시, 원 결제 건은 승인 취소 처리 됩니다.<br />
결제수단에 따라서 환불 처리 기간이 다소 차이가 있을 수 있습니다.<br />
변경된 결제정보는 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 주문/배송 현황 조회</span>에서 확인하실 수 있습니다.</p>
<!-- s : 주문 정보 -->

<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문 정보</h3>

<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;"><!-- s: table -->
<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
	<colgroup>
		<col style="width: 140px;" />
		<col style="width: auto;" />
	</colgroup>
	<tbody>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문일</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderDtm}</td>
		</tr>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문번호</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderNo}</td>
		</tr>
	</tbody>
</table>
<!-- e: table --></div>
<!-- e : 주문 정보 --><!-- s : 기존 결제 정보 -->

<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">기존 결제 정보</h3>

<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;"><!-- s: table -->
<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
	<colgroup>
		<col style="width: 140px;" />
		<col style="width: auto;" />
	</colgroup>
	<tbody>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">${pymntMeansCodeNameOld}</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pymntAmt}
			<p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${pymntMeansCodeNameOld}${cardMaskOld} ${instmtTermCountOld?if_exists} ${pymntDtmOld}</p>
			</td>
		</tr>
	</tbody>
</table>
<!-- e: table --></div>
<!-- e : 기존 결제 정보 -->
<!-- s : 변경 결제 정보 -->

<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">변경 결제 정보</h3>

<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;"><!-- s: table --><ch> </ch>

<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
	<colgroup>
		<col style="width: 140px;" />
		<col style="width: auto;" />
	</colgroup>
	<tbody>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">${pymntMeansCodeNameNew}</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pymntAmt}
			<p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${pymntMeansCodeNameNew} ${cardMaskNew} ${instmtTermCountNew?if_exists} ${pymntDtmNew}</p>
			</td>
		</tr>
	</tbody>
</table>
<!-- e: table --></div>
<!-- e : 변경 결제 정보 -->

<div style="margin-top: 50px; font-size: 0;"><a href="${landingUrl}" style="display: inline-block; padding: 21px 48px 20px 49px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" target="_blank" title="새창 열림">주문/배송 현황 조회</a></div>
</div>
<!-- e: content --><!-- s: footer -->

<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
<div style="padding: 20px 0 21px;">
<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" style="color: #666;" target="_blank">고객센터</a>로 문의해 주시기 바랍니다.</p>
</div>

<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
<p style="margin:0;"><span style="color: #000">(주)에이비씨마트 코리아</span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<br />
사업자등록번호 201-81-76174통신판매업신고 제 2015-서울중고-1036호대표이사 이기호개인정보보호 책임자 박영수<br />
대표번호 080-701-7770, 1588-9667전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span></p>
</div>
</div>
</div>
<!-- e: footer --></div>
<!-- e: wrap -->',null,'EC-01005-1',null,'A',0,'SY00000013','2019-07-26 13:17:44','SY00000013','2019-11-01 12:18:53')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10000','결제수단 변경 안내','[ON THE SPOT] 결제수단이 변경되었습니다.','<meta charset="UTF-8"><meta http-equiv="X-UA-Compatible" content="ie=edge">
<title></title>
<style type="text/css">html, body { margin: 0; padding: 0; }
</style>
<!-- s: wrap -->
<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;"><!-- s: header -->
<table align="center" border="0" cellpadding="0" cellspacing="0" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
	<tbody>
		<tr style="background-color: #262b44;">
			<td style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;" valign="top"><a href="${otsUrl}" style="margin: 0; padding: 0; vertical-align: top; " target="_blank" title="새창 열림"><img alt="On the spot" src="${imgPath}/images/ots_logo.png" style="border: 0; vertical-align: top;" /> </a></td>
			<td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
			<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
				<tbody>
					<tr>
						<td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
						<td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img alt="" src="${imgPath}/images/util_line.gif" valign="top" /></td>
						<td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
	</tbody>
</table>
<!-- e: header --><!-- s: content -->

<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">결제수단이 변경되었습니다.</h2>

<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">안녕하세요, ${memberName}님!<br />
결제수단 변경 시, 원 결제 건은 승인 취소 처리 됩니다.<br />
결제수단에 따라서 환불 처리 기간이 다소 차이가 있을 수 있습니다.<br />
변경된 결제정보는 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 주문/배송 현황 조회</span>에서 확인하실 수 있습니다.</p>
<!-- s : 주문 정보 -->

<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문 정보</h3>

<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;"><!-- s: table -->
<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
	<colgroup>
		<col style="width: 140px;" />
		<col style="width: auto;" />
	</colgroup>
	<tbody>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문일</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderDtm}</td>
		</tr>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문번호</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderNo}</td>
		</tr>
	</tbody>
</table>
<!-- e: table --></div>
<!-- e : 주문 정보 --><!-- s : 기존 결제 정보 -->

<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">기존 결제 정보</h3>

<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;"><!-- s: table -->
<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
	<colgroup>
		<col style="width: 140px;" />
		<col style="width: auto;" />
	</colgroup>
	<tbody>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">${pymntMeansCodeNameOld}</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pymntAmt}
			<p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${pymntMeansCodeNameOld} ${cardMaskOld} ${instmtTermCountOld?if_exists} ${pymntDtmOld}</p>
			</td>
		</tr>
	</tbody>
</table>
<!-- e: table --></div>
<!-- e : 기존 결제 정보 --><!-- s : 변경 결제 정보 -->

<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">변경 결제 정보</h3>

<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;"><!-- s: table --><ch> </ch>

<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
	<colgroup>
		<col style="width: 140px;" />
		<col style="width: auto;" />
	</colgroup>
	<tbody>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">${pymntMeansCodeNameNew}</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pymntAmt}
			<p style="margin: 3px 0 0 0; padding: 0; font-size: 13px; color: #999; letter-spacing: -0.65px;">${pymntMeansCodeNameNew}${cardMaskNew}${instmtTermCountNew?if_exists} ${pymntDtmNew}</p>
			</td>
		</tr>
	</tbody>
</table>
<!-- e: table --></div>
<!-- e : 변경 결제 정보 -->

<div style="margin-top: 50px; font-size: 0;"><a href="${landingUrl}" style="display: inline-block; padding: 21px 48px 20px 49px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" target="_blank" title="새창 열림">주문/배송 현황 조회</a></div>
</div>
<!-- e: content --><!-- s: footer -->

<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
<div style="padding: 20px 0 21px;">
<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" style="color: #666;" target="_blank">고객센터</a>로 문의해 주시기 바랍니다.</p>
</div>

<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
<p style="margin:0;"><span style="color: #000">(주)에이비씨마트 코리아</span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층사업자등록번호 201-81-76174<br />
통신판매업신고 제 2015-서울중고-1036호대표이사 이기호개인정보보호 책임자 박영수고객센터 1644-0136</p>
</div>
</div>
</div>
<!-- e: footer --></div>
<!-- e: wrap -->',null,'EC-01005-2',null,'A',0,'SY00000013','2019-07-26 13:19:08','SY20000212','2019-11-01 12:20:01')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','N',null,'메일키생성 테스트','메일키생성 테스트','에디터 영역 넘 작은거 아닙니까 흑흑bb','AC13dc1hccp',null,'2024-07-25 14:40:00','A',0,'SY00000115','2019-07-29 17:51:02','SY00000115','2019-07-29 18:55:32')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','N',null,'testtest','testtest','rttest','AC13dd0o161',null,'2019-07-31 11:30:00','A',0,'SY00000115','2019-07-29 17:52:01','SY00000115','2019-07-29 17:52:01')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10000','테스트','테스트','&lt;div class=&quot;board-view-wrap border-line-box webzine-view&quot;&gt;
&lt;div class=&quot;flex-box board-view-head&quot;&gt;
&lt;div class=&quot;view-tit-wrap&quot;&gt;&lt;span class=&quot;title&quot;&gt;ADIDAS YUNG-1 HIGH RES ORANGE WITH BLACKPINK ROSE&lt;/span&gt;&lt;/div&gt;

&lt;div class=&quot;view-btn-wrap&quot;&gt;&lt;span class=&quot;text-date&quot;&gt;SEP 22, 2018&lt;/span&gt;

&lt;div class=&quot;sns-share-box&quot;&gt;
&lt;div class=&quot;sns-list-box&quot;&gt;&amp;nbsp;&lt;/div&gt;
&lt;/div&gt;
&lt;/div&gt;
&lt;/div&gt;

&lt;div class=&quot;board-view-cont&quot;&gt;&lt;img alt=&quot;ADIDAS YUNG-1 HIGH RES ORANGE BLACK WITH BLACKPINK ROSE&quot; src=&quot;/static/images/temp/temp_webzine_thumb_01.jpg&quot; /&gt;&lt;br /&gt;
&lt;br /&gt;
&lt;img alt=&quot;ADIDAS YUNG-1 HIGH RES ORANGE BLACK WITH BLACKPINK ROSE&quot; src=&quot;/static/images/temp/temp_webzine_thumb_01_02.jpg&quot; /&gt;&lt;br /&gt;
&lt;br /&gt;
&amp;nbsp;
&lt;p&gt;ADIDAS ORIGINALS가 공식적으로 최대 규모의 YEEZY BOOST 350V2 출시를 발표했습니다.&lt;br /&gt;
&lt;br /&gt;
칸예 웨스트의 가장 인기있는 실루엣 중 하나인 &amp;#39;트리플 화이트&amp;#39;가 1월 25일(금) 온더스팟에서 발매합니다.&lt;br /&gt;
이번 YEEZY 350 V2 TRIPLE WHITE 제품은 &amp;#39;온더스팟 부산광복&amp;#39; 스토어를 모두 포함한 총 4군데의 온더스팟 스토어를 통해 발매됩니다.&lt;br /&gt;
&lt;br /&gt;
흰색 측면 스트라이프가 있는 하얀색 PRIMEKNIT 어퍼, 반투명 아웃솔이 외형을 완성합니다.&lt;br /&gt;
&lt;br /&gt;
RELEASE 1/25(FRI) AM 11:00&lt;br /&gt;
YEEZY BOOST 350 V2 TRIPLE WHITE&lt;/p&gt;
&lt;/div&gt;
&lt;/div&gt;
',null,null,'2019-07-29 19:00:24','M',0,'SY00000115','2019-07-29 19:00:24','SY00000115','2019-07-29 19:00:24')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10002','test','제목','내용1',null,'test-id',null,'A',0,'SY00000115','2019-07-29 20:28:15','SY00000093','2019-08-05 17:01:56')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10000','배송지 정보 변경','고객의 배송정보가 변경되었습니다.','<meta charset="UTF-8"><meta http-equiv="X-UA-Compatible" content="ie=edge">
<title></title>
<style type="text/css">html, body { margin: 0; padding: 0; }
</style>
<!-- s: wrap -->
<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;"><!-- s : 190813 수정 | mail header 영역 table 코딩으로 수정 --><!-- s: header -->
<table align="center" border="0" cellpadding="0" cellspacing="0" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
	<tbody>
		<tr style="background-color: #000;">
			<td style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;" valign="top"><a href="${landingUrl}" style="margin: 0; padding: 0; vertical-align: top; " target="_blank" title="새창 열림"><img alt="A-RT" src="${abcUrl}/static/images/email/art_logo.png" style="border: 0; vertical-align: top;" /> </a></td>
		</tr>
	</tbody>
</table>
<!-- e: header --><!-- e : 190813 수정 | mail header 영역 table 코딩으로 수정 --><!-- s: content -->

<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">고객의 배송정보가 변경되었습니다.</h2>

<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">안녕하세요, ${vndrName} 담당자님!<br />
상품 주문 고객의 배송정보가 변경되었습니다.<br />
배송에 차질이 생기지 않도록 확인 부탁드립니다.</p>
<!-- s : 배송정보 변경 주문건 -->

<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">배송정보 변경 주문건</h3>

<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;"><!-- s: table -->
<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
	<colgroup>
		<col style="width: 140px;" />
		<col style="width: auto;" />
	</colgroup>
	<tbody>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문번호</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderNo}</td>
		</tr>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문일시</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderDtm}</td>
		</tr>
		<tr>
			<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문상태</th>
			<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">결제완료</td>
		</tr>
	</tbody>
</table>
<!-- e: table --></div>
<!-- e : 배송정보 변경 주문건 -->

<div style="margin-top: 50px; font-size: 0;"><a href="${landingUrl}" style="display: inline-block; padding: 21px 45px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" target="_blank" title="새창 열림">관리자 시스템</a></div>
</div>
<!-- e: content --><!-- s: footer -->

<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
<div style="overflow: hidden; padding: 17px 0 0;">
<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
<p style="margin:0;"><span style="color: #000">(주)에이비씨마트 코리아</span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<br />
사업자등록번호 201-81-76174통신판매업신고 제 2015-서울중고-1036호대표이사 이기호개인정보보호 책임자 박영수<br />
대표번호 080-701-7770, 1588-9667전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span></p>
</div>
</div>
</div>
<!-- e: footer --></div>
<!-- e: wrap -->',null,'EA-05001',null,'A',0,'SY00000013','2019-07-30 12:32:20','SY00000001','2019-09-09 15:36:55')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10000','주문완료','[A-RT] 주문해 주셔서 감사합니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">주문해 주셔서 감사합니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName?if_exists}님! <br />
				A-RT를 이용해 주셔서 감사합니다.<br />
				배송상태 확인 및 배송정보 수정은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 주문/배송 현황 조회</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : 주문 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderDtm?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderNo?if_exists}</td>
						</tr>
						<#if orderPw?exists>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">비밀번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderPw?if_exists}</td>
							</tr>
						</#if>
						<#if rsvPrdtYn == ''Y''>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">발송예정일</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${rsvDlvyDtm?if_exists}</td>
							</tr>
						</#if>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 주문 정보 -->

			<!-- s : 주문상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 140px;" />
					<col style="width: 130px;" />
				</colgroup>
				<tbody>
					<#if prdtList?exists>
						<#list prdtList as prdt>
							<tr>
								<td style="padding: 20px 15px; font-size: 0;">
									<img style="width: 100%" src="${prdt.imageUrl}?shrink=100:100" alt="${prdt.altrnText}">
								</td>
								<td>
									<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName?if_exists}</span>
									<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName?if_exists}</p>
									<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName?if_exists} / 1개</span>
								</td>
								<td style="text-align: center;">
									<#if prdt.prdtNormalAmt &gt; prdt.orderAmt>
										<div style="margin-bottom: 5px; color: #999; text-decoration: line-through">
											<span style="font-size: 13px;">${prdt.prdtNormalAmt?if_exists}</span><span style="font-size: 12px;">원</span>
										</div>
									</#if>
									<span style="font-size: 18px; font-weight: bold;">${prdt.orderAmt?if_exists}</span><span style="font-size: 15px;">원</span>
								</td>
								<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">${prdt.prtdTypeCodeName?if_exists}</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 주문상품 정보 -->

			<!-- s : 배송지 정보 -->
			
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">배송지 정보</h3>
			
			<#if dlvyTypeCode == "10000">
				<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
					<!-- s: table -->
					<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
						<colgroup>
							<col style="width: 140px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${rcvrName?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">휴대폰 번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${rcvrHdphnNoText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송주소</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${rcvrPostCodeText?if_exists}] ${rcvrPostAddrText?if_exists}, ${rcvrDtlAddrText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송메시지</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyMemoText?if_exists}</td>
							</tr>
						</tbody>
					</table>
					<!-- e: table -->
				</div>
			</#if>
			
			<#if dlvyTypeCode == "10001">
				<!-- s : 배송지 정보 > 편의점픽업인 경우 -->
				<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
					<!-- s: table -->
					<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
						<colgroup>
							<col style="width: 140px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${rcvrName?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">휴대폰 번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${rcvrHdphnNoText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">상품수령 편의점</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">
									${storeName?if_exists} (${rcvrTellNoText?if_exists})<br />${rcvrPostCodeText?if_exists} ${rcvrPostAddrText?if_exists} ${rcvrDtlAddrText?if_exists}<br />
									<p style="margin: 0; color: #ee1c25;">편의점 픽업 상품은 픽업준비완료 시점부터 7일 이내 수령해야 하며,<br />기간 내 미 수령 시 배송비 고객 부담으로 반품처리 됩니다.</p>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- e: table -->
				</div>
				<!-- s : 배송지 정보 > 편의점픽업인 경우 -->
			</#if>
			
			<#if dlvyTypeCode == "10002">
				<!-- s : 배송지 정보 > 매장픽업인 경우 -->
				<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
					<!-- s: table -->
					<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
						<colgroup>
							<col style="width: 140px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${rcvrName?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">휴대폰 번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${rcvrHdphnNoText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">픽업코드</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${crtfcNoText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">수령지점</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${storeName?if_exists} <br />${rcvrPostAddrText?if_exists} ${rcvrDtlAddrText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">영업정보</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">영업시간 : ${storeAddInfo?if_exists} <br />전화번호 : ${rcvrTellNoText?if_exists}</td>
							</tr>
						</tbody>
					</table>
					<!-- e: table -->
				</div>
				<!-- e : 배송지 정보 > 매장픽업인 경우 -->
			</#if>
			
			<!-- e : 배송지 정보 -->

			<!-- s : 결제 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">결제 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: auto;" />
					<col style="width: auto;" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">주문금액</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold;">${sumOrderAmt?if_exists}</span><span style="font-size: 15px;">원</span>
						</td>
					</tr>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">총 할인금액</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold;">${totalDscntAmt?if_exists}</span><span style="font-size: 15px;">원</span>
						</td>
					</tr>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">총 결제금액</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold; color:#ee1c25;">${pymntAmt?if_exists}</span><span style="font-size: 15px; color:#ee1c25;">원</span>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="padding: 7px 14px 9px; background: #fbfbfb;">
							<!-- s: table -->
							<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
								<colgroup>
									<col style="width: auto;" />
									<col style="width: auto;" />
								</colgroup>
								<tbody>
									<#if ocOrderPaymentList?exists>
										<#list ocOrderPaymentList as pymnt>
											<tr>
												<th scope="row" style="padding: 6px 0 5px; font-weight: normal; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left;">${pymnt.pymntMeansCodeName?if_exists}</th>
												<td style="padding: 3px 0 8px; color: #666; text-align: right;">
													<#if pymnt.pymntMeansCode != "10007" && pymnt.pymntMeansCode != "10008">
														<span style="font-size: 14px; letter-spacing: -0.7px;">${pymnt.pymntAmt?if_exists}</span><span style="font-size: 13px; letter-spacing: -0.65px;">원</span>
													</#if>
													<#if pymnt.pymntMeansCode == "10007" || pymnt.pymntMeansCode == "10008">
														<span style="font-size: 14px; letter-spacing: -0.7px;">${pymnt.pymntAmt?if_exists}</span><span style="font-size: 13px; letter-spacing: -0.65px;">P</span>
													</#if>
												</td>
											</tr>
											<#if pymnt.pymntMeansCode == "10001">
												<tr>
													<td colspan="2" style="padding: 1px 0 3px 0;">
														<p style="margin: 0; font-size: 13px; letter-spacing: -0.65px; color: #999; line-height: 20px;">${pymnt.pymntOrganName?if_exists} : ${pymnt.pymntOrganNoText?if_exists}?(예금주 : 에이비씨마트코리아)<br />입금기한 : ${pymnt.vrtlAcntExprDtm?if_exists}까지</p>
													</td>
												</tr>
											</#if>
										</#list>
									</#if>
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 결제 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">2개 이상의 상품 주문 시 매장 재고여부에 따라 분리발송 될 수 있습니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">오프라인 매장 발송 상품은 온라인 발송 상품보다 평균 배송기간이 2~3일 정도 더 소요될 수 있습니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">매장 발송 상품의 경우 발송되는 매장정보는 주문 완료 후 <span style="color: #000; font-weight: bold;">마이페이지 &gt; 주문배송 현황조회</span>에서 확인 가능합니다.</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl?if_exists}" target="_blank" style="display: inline-block; padding: 21px 26px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">주문/배송 현황 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-01001-1',null,'A',0,'SY00000057','2019-08-01 16:50:10','SY00000057','2019-09-09 15:51:29')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10000','주문완료','[ON THE SPOT] 주문해 주셔서 감사합니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
									<td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">주문해 주셔서 감사합니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName?if_exists}님! <br />
				ON THE SPOT을 이용해 주셔서 감사합니다.<br />
				배송상태 확인 및 배송정보 수정은 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 주문/배송 현황 조회</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : 주문 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderDtm?if_exists}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderNo?if_exists}</td>
						</tr>
						<#if orderPw?exists>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">비밀번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderPw?if_exists}</td>
							</tr>
						</#if>
						<#if rsvPrdtYn == ''Y''>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">발송예정일</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${rsvDlvyDtm?if_exists}</td>
							</tr>
						</#if>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 주문 정보 -->

			<!-- s : 주문상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 140px;" />
					<col style="width: 130px;" />
				</colgroup>
				<tbody>
					<#if prdtList?exists>
						<#list prdtList as prdt>
							<tr>
								<td style="padding: 20px 15px; font-size: 0;">
									<img style="width: 100%" src="${prdt.imageUrl}?shrink=100:100" alt="${prdt.altrnText}">
								</td>
								<td>
									<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName?if_exists}</span>
									<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName?if_exists}</p>
									<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">${prdt.optnName?if_exists} / 1개</span>
								</td>
								<td style="text-align: center;">
									<#if prdt.prdtNormalAmt &gt; prdt.orderAmt>
										<div style="margin-bottom: 5px; color: #999; text-decoration: line-through">
											<span style="font-size: 13px;">${prdt.prdtNormalAmt?if_exists}</span><span style="font-size: 12px;">원</span>
										</div>
									</#if>
									<span style="font-size: 18px; font-weight: bold;">${prdt.orderAmt?if_exists}</span><span style="font-size: 15px;">원</span>
								</td>
								<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">${prdt.prtdTypeCodeName?if_exists}</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 주문상품 정보 -->

			<!-- s : 배송지 정보 -->
			
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">배송지 정보</h3>
			
			<#if dlvyTypeCode == "10000">
				<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
					<!-- s: table -->
					<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
						<colgroup>
							<col style="width: 140px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${rcvrName?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">휴대폰 번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${rcvrHdphnNoText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송주소</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">[${rcvrPostCodeText?if_exists}] ${rcvrPostAddrText?if_exists}, ${rcvrDtlAddrText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">배송메시지</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${dlvyMemoText?if_exists}</td>
							</tr>
						</tbody>
					</table>
					<!-- e: table -->
				</div>
			</#if>
			
			<#if dlvyTypeCode == "10001">
				<!-- s : 배송지 정보 > 편의점픽업인 경우 -->
				<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
					<!-- s: table -->
					<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
						<colgroup>
							<col style="width: 140px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${rcvrName?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">휴대폰 번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${rcvrHdphnNoText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">상품수령 편의점</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">
									${storeName?if_exists} (${rcvrTellNoText?if_exists})<br />${rcvrPostCodeText?if_exists} ${rcvrPostAddrText?if_exists} ${rcvrDtlAddrText?if_exists}<br />
									<p style="margin: 0; color: #ee1c25;">편의점 픽업 상품은 픽업준비완료 시점부터 7일 이내 수령해야 하며,<br />기간 내 미 수령 시 배송비 고객 부담으로 반품처리 됩니다.</p>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- e: table -->
				</div>
				<!-- s : 배송지 정보 > 편의점픽업인 경우 -->
			</#if>
			
			<#if dlvyTypeCode == "10002">
				<!-- s : 배송지 정보 > 매장픽업인 경우 -->
				<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
					<!-- s: table -->
					<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
						<colgroup>
							<col style="width: 140px;" />
							<col style="width: auto;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${rcvrName?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">휴대폰 번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${rcvrHdphnNoText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">픽업코드</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${crtfcNoText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">수령지점</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${storeName?if_exists} <br />${rcvrPostAddrText?if_exists} ${rcvrDtlAddrText?if_exists}</td>
							</tr>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">영업정보</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">영업시간 : ${storeAddInfo?if_exists} <br />전화번호 : ${rcvrTellNoText?if_exists}</td>
							</tr>
						</tbody>
					</table>
					<!-- e: table -->
				</div>
				<!-- e : 배송지 정보 > 매장픽업인 경우 -->
			</#if>
			
			<!-- e : 배송지 정보 -->

			<!-- s : 결제 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">결제 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: auto;" />
					<col style="width: auto;" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">주문금액</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold;">${sumOrderAmt?if_exists}</span><span style="font-size: 15px;">원</span>
						</td>
					</tr>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">총 할인금액</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold;">${totalDscntAmt?if_exists}</span><span style="font-size: 15px;">원</span>
						</td>
					</tr>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">총 결제금액</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold; color:#ee1c25;">${pymntAmt?if_exists}</span><span style="font-size: 15px; color:#ee1c25;">원</span>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="padding: 7px 14px 9px; background: #fbfbfb;">
							<!-- s: table -->
							<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
								<colgroup>
									<col style="width: auto;" />
									<col style="width: auto;" />
								</colgroup>
								<tbody>
									<#if ocOrderPaymentList?exists>
										<#list ocOrderPaymentList as pymnt>
											<tr>
												<th scope="row" style="padding: 6px 0 5px; font-weight: normal; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left;">${pymnt.pymntMeansCodeName?if_exists}</th>
												<td style="padding: 3px 0 8px; color: #666; text-align: right;">
													<#if pymnt.pymntMeansCode != "10007" && pymnt.pymntMeansCode != "10008">
														<span style="font-size: 14px; letter-spacing: -0.7px;">${pymnt.pymntAmt?if_exists}</span><span style="font-size: 13px; letter-spacing: -0.65px;">원</span>
													</#if>
													<#if pymnt.pymntMeansCode == "10007" || pymnt.pymntMeansCode == "10008">
														<span style="font-size: 14px; letter-spacing: -0.7px;">${pymnt.pymntAmt?if_exists}</span><span style="font-size: 13px; letter-spacing: -0.65px;">P</span>
													</#if>
												</td>
											</tr>
											<#if pymnt.pymntMeansCode == "10001">
												<tr>
													<td colspan="2" style="padding: 1px 0 3px 0;">
														<p style="margin: 0; font-size: 13px; letter-spacing: -0.65px; color: #999; line-height: 20px;">${pymnt.pymntOrganName?if_exists} : ${pymnt.pymntOrganNoText?if_exists}?(예금주 : 에이비씨마트코리아)<br />입금기한 : ${pymnt.vrtlAcntExprDtm?if_exists}까지</p>
													</td>
												</tr>
											</#if>
										</#list>
									</#if>
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 결제 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">2개 이상의 상품 주문 시 매장 재고여부에 따라 분리발송 될 수 있습니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">오프라인 매장 발송 상품은 온라인 발송 상품보다 평균 배송기간이 2~3일 정도 더 소요될 수 있습니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">매장 발송 상품의 경우 발송되는 매장정보는 주문 완료 후 <span style="color: #000; font-weight: bold;">마이페이지 &gt; 주문배송 현황조회</span>에서 확인 가능합니다.</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl?if_exists}" target="_blank" style="display: inline-block; padding: 21px 26px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">주문/배송 현황 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
						통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-01001-2',null,'A',0,'SY00000057','2019-08-01 17:00:21','SY00000057','2019-09-09 15:51:43')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10000','주문접수 (무통장결제)','[A-RT] 주문해 주셔서 감사합니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">주문이 접수되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님!<br />
				무통장입금 주문이 접수되었으며, 기한내에 입금해 주셔야 주문이 완료됩니다.<br />
				주문상태는 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 주문/배송 현황 조회</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : 주문 및 입금 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문 및 입금 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderDtm}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderNo}</td>
						</tr>
						<#if orderPw?exists>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">비밀번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderPw}</td>
							</tr>
						</#if>
						<#if ocOrderPaymentList?exists>
							<#list ocOrderPaymentList as pymnt>
								<#if pymnt.mainPymntMeansYn == "Y" && pymnt.pymntMeansCode == "10001">
									<tr>
										<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">입금금액</th>
										<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pymntTodoAmt}원</td>
									</tr>
									<tr>
										<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">입금계좌</th>
										<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pymnt.pymntOrganName} : ${pymnt.pymntOrganNoText}?(예금주 : 에이비씨마트코리아)</td>
									</tr>
									<tr>
										<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">입금기한</th>
										<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pymnt.vrtlAcntExprDtm}까지</td>
									</tr>
								</#if>
							</#list>
						</#if>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 주문 및 입금 정보 -->

			<!-- s : 주문상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 140px;" />
					<col style="width: 130px;" />
				</colgroup>
				<tbody>
					<#if prdtList?exists>
						<#list prdtList as prdt>
							<tr>
								<td style="padding: 20px 15px; font-size: 0;">
									<img style="width: 100%" src="${prdt.imageUrl}?shrink=100:100" alt="${prdt.altrnText}">
								</td>
								<td>
									<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName}</span>
									<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName}</p>
									<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">{prdt.optnName} / 1개</span>
								</td>
								<td style="text-align: center;">
									<#if prdt.prdtNormalAmt &gt; prdt.orderAmt>
										<div style="margin-bottom: 5px; color: #999; text-decoration: line-through">
											<span style="font-size: 13px;">${prdt.prdtNormalAmt}</span><span style="font-size: 12px;">원</span>
										</div>
									</#if>
									<span style="font-size: 18px; font-weight: bold;">${prdt.orderAmt}</span><span style="font-size: 15px;">원</span>
								</td>
								<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">${prdt.prtdTypeCodeName}</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 주문상품 정보 -->

			<!-- s : 결제 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">결제 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: auto;" />
					<col style="width: auto;" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">주문금액</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold;">${sumOrderAmt}</span><span style="font-size: 15px;">원</span>
						</td>
					</tr>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">총 할인금액</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold;">${totalDscntAmt}</span><span style="font-size: 15px;">원</span>
						</td>
					</tr>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">총 결제금액</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold; color:#ee1c25;">${pymntAmt}</span><span style="font-size: 15px; color:#ee1c25;">원</span>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="padding: 7px 14px 9px; background: #fbfbfb;">
							<!-- s: table -->
							<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
								<colgroup>
									<col style="width: auto;" />
									<col style="width: auto;" />
								</colgroup>
								<tbody>
									<#if ocOrderPaymentList?exists>
										<#list ocOrderPaymentList as pymnt>
											<tr>
												<th scope="row" style="padding: 6px 0 5px; font-weight: normal; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left;">${pymnt.pymntMeansCodeName}</th>
												<td style="padding: 3px 0 8px; color: #666; text-align: right;">
													<#if pymnt.pymntMeansCode != "10007" && pymnt.pymntMeansCode != "10008">
														<span style="font-size: 14px; letter-spacing: -0.7px;">${pymnt.pymntAmt}</span><span style="font-size: 13px; letter-spacing: -0.65px;">원</span>
													</#if>
													<#if pymnt.pymntMeansCode == "10007" || pymnt.pymntMeansCode == "10008">
														<span style="font-size: 14px; letter-spacing: -0.7px;">${pymnt.pymntAmt}</span><span style="font-size: 13px; letter-spacing: -0.65px;">P</span>
													</#if>
												</td>
											</tr>
											<#if pymnt.pymntMeansCode == "10001">
												<tr>
													<td colspan="2" style="padding: 1px 0 3px 0;">
														<p style="margin: 0; font-size: 13px; letter-spacing: -0.65px; color: #999; line-height: 20px;">${pymnt.pymntOrganName} : ${pymnt.pymntOrganNoText}?(예금주 : 에이비씨마트코리아)<br />입금기한 : ${pymnt.vrtlAcntExprDtm}까지</p>
													</td>
												</tr>
											</#if>
										</#list>
									</#if>
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 결제 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">2개 이상의 상품 주문 시 매장 재고여부에 따라 분리발송 될 수 있습니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">오프라인 매장 발송 상품은 온라인 발송 상품보다 평균 배송기간이 2~3일 정도 더 소요될 수 있습니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">매장 발송 상품의 경우 발송되는 매장정보는 주문 완료 후 <span style="color: #000; font-weight: bold;">마이페이지 &gt; 주문배송 현황조회</span>에서 확인 가능합니다.</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl}" target="_blank" style="display: inline-block; padding: 21px 26px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">주문/배송 현황 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-01006-1',null,'A',0,'SY00000057','2019-08-01 17:40:47','SY00000057','2019-09-09 15:52:05')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10001','Y','10000','주문접수 (무통장결제)','[ON THE SPOT] 주문해 주셔서 감사합니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #262b44; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #262b44;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${otsUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/ots_logo.png" alt="On the spot" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 200px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 92px; padding: 0; vertical-align: top;"><a href="${otsUrl}/display/magazine" style="font-size: 15px; color: #fff; text-decoration: none;">MAGAZINE</a></td>
									<td style="width: 1px; padding: 7px 0 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 87px; padding: 0 0 0 20px; vertical-align: top;"><a href="${otsUrl}/display/styling" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">STYLING</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">주문이 접수되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님!<br />
				무통장입금 주문이 접수되었으며, 기한내에 입금해 주셔야 주문이 완료됩니다.<br />
				주문상태는 <span style="font-weight: bold">마이페이지 &gt; 쇼핑내역 &gt; 주문/배송 현황 조회</span>에서 확인하실 수 있습니다.
			</p>

			<!-- s : 주문 및 입금 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문 및 입금 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderDtm}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">주문번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderNo}</td>
						</tr>
						<#if orderPw?exists>
							<tr>
								<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">비밀번호</th>
								<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${orderPw}</td>
							</tr>
						</#if>
						<#if ocOrderPaymentList?exists>
							<#list ocOrderPaymentList as pymnt>
								<#if pymnt.mainPymntMeansYn == "Y" && pymnt.pymntMeansCode == "10001">
									<tr>
										<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">입금금액</th>
										<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pymntTodoAmt}원</td>
									</tr>
									<tr>
										<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">입금계좌</th>
										<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pymnt.pymntOrganName} : ${pymnt.pymntOrganNoText}?(예금주 : 에이비씨마트코리아)</td>
									</tr>
									<tr>
										<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">입금기한</th>
										<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pymnt.vrtlAcntExprDtm}까지</td>
									</tr>
								</#if>
							</#list>
						</#if>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 주문 및 입금 정보 -->

			<!-- s : 주문상품 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">주문상품 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: 130px;" />
					<col style="width: auto;" />
					<col style="width: 140px;" />
					<col style="width: 130px;" />
				</colgroup>
				<tbody>
					<#if prdtList?exists>
						<#list prdtList as prdt>
							<tr>
								<td style="padding: 20px 15px; font-size: 0;">
									<img style="width: 100%" src="${prdt.imageUrl}?shrink=100:100" alt="${prdt.altrnText}">
								</td>
								<td>
									<span style="display: block; font-size: 14px; font-weight: bold; color: #000;">${prdt.brandName}</span>
									<p style="display: block; margin: 10px 0 0; font-size: 14px; color: #666;">${prdt.prdtName}</p>
									<span style="display: block; margin: 14px 0 0; font-size: 14px; color: #666;">{prdt.optnName} / 1개</span>
								</td>
								<td style="text-align: center;">
									<#if prdt.prdtNormalAmt &gt; prdt.orderAmt>
										<div style="margin-bottom: 5px; color: #999; text-decoration: line-through">
											<span style="font-size: 13px;">${prdt.prdtNormalAmt}</span><span style="font-size: 12px;">원</span>
										</div>
									</#if>
									<span style="font-size: 18px; font-weight: bold;">${prdt.orderAmt}</span><span style="font-size: 15px;">원</span>
								</td>
								<td style="border-bottom: 1px solid #e5e5e5; font-size: 14px; text-align: center">${prdt.prtdTypeCodeName}</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 주문상품 정보 -->

			<!-- s : 결제 정보 -->
			<h3 style="margin: 50px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">결제 정보</h3>
			<!-- s: table -->
			<table style="width: 100%; margin-top: 14px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5; border-collapse: collapse; table-layout: fixed;">
				<colgroup>
					<col style="width: auto;" />
					<col style="width: auto;" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">주문금액</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold;">${sumOrderAmt}</span><span style="font-size: 15px;">원</span>
						</td>
					</tr>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">총 할인금액</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold;">${totalDscntAmt}</span><span style="font-size: 15px;">원</span>
						</td>
					</tr>
					<tr>
						<th scope="row" style="padding: 21px 14px 19px; border-bottom: 1px solid #e5e5e5; font-size: 15px; font-weight: normal; letter-spacing: -1.5px; color: #000; text-align:left;">총 결제금액</th>
						<td style="padding: 18px 14px 19px; border-bottom: 1px solid #e5e5e5; text-align: right;">
							<span style="font-size: 18px; font-weight: bold; color:#ee1c25;">${pymntAmt}</span><span style="font-size: 15px; color:#ee1c25;">원</span>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="padding: 7px 14px 9px; background: #fbfbfb;">
							<!-- s: table -->
							<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
								<colgroup>
									<col style="width: auto;" />
									<col style="width: auto;" />
								</colgroup>
								<tbody>
									<#if ocOrderPaymentList?exists>
										<#list ocOrderPaymentList as pymnt>
											<tr>
												<th scope="row" style="padding: 6px 0 5px; font-weight: normal; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left;">${pymnt.pymntMeansCodeName}</th>
												<td style="padding: 3px 0 8px; color: #666; text-align: right;">
													<#if pymnt.pymntMeansCode != "10007" && pymnt.pymntMeansCode != "10008">
														<span style="font-size: 14px; letter-spacing: -0.7px;">${pymnt.pymntAmt}</span><span style="font-size: 13px; letter-spacing: -0.65px;">원</span>
													</#if>
													<#if pymnt.pymntMeansCode == "10007" || pymnt.pymntMeansCode == "10008">
														<span style="font-size: 14px; letter-spacing: -0.7px;">${pymnt.pymntAmt}</span><span style="font-size: 13px; letter-spacing: -0.65px;">P</span>
													</#if>
												</td>
											</tr>
											<#if pymnt.pymntMeansCode == "10001">
												<tr>
													<td colspan="2" style="padding: 1px 0 3px 0;">
														<p style="margin: 0; font-size: 13px; letter-spacing: -0.65px; color: #999; line-height: 20px;">${pymnt.pymntOrganName} : ${pymnt.pymntOrganNoText}?(예금주 : 에이비씨마트코리아)<br />입금기한 : ${pymnt.vrtlAcntExprDtm}까지</p>
													</td>
												</tr>
											</#if>
										</#list>
									</#if>
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- e: table -->
			<!-- e : 결제 정보 -->

			<div style="margin-top: 50px; padding: 24px 0 25px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<ul style="margin: 0; padding: 0;">
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">2개 이상의 상품 주문 시 매장 재고여부에 따라 분리발송 될 수 있습니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">오프라인 매장 발송 상품은 온라인 발송 상품보다 평균 배송기간이 2~3일 정도 더 소요될 수 있습니다.</p>
					</li>
					<li style="overflow: hidden; margin: 0; padding: 0 8px; list-style: none;">
						<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 11px 5px 0 0; background-color: #999; vertical-align: top;"></span>
						<p style="float: right; width: 653px; margin: 0; font-size: 14px; color: #666; letter-spacing: -1.4px; line-height: 24px; word-break: keep-all;">매장 발송 상품의 경우 발송되는 매장정보는 주문 완료 후 <span style="color: #000; font-weight: bold;">마이페이지 &gt; 주문배송 현황조회</span>에서 확인 가능합니다.</p>
					</li>
				</ul>
			</div>

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${landingUrl}" target="_blank" style="display: inline-block; padding: 21px 26px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">주문/배송 현황 조회</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${otsUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>사업자등록번호 201-81-76174 <br />
						통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>고객센터 1644-0136
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>',null,'EC-01006-2',null,'A',0,'SY00000057','2019-08-01 17:42:58','SY00000057','2019-09-09 15:52:17')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10000','테스트2','테스트2','테스트2',null,null,'2019-08-05 11:18:10','M',0,'SY00000093','2019-08-05 11:18:10','SY00000093','2019-08-05 11:18:10')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','N',null,'등록 테스트 ','등록 테스트 ','등록 테ㅡ트','AC5pp2rzy',null,'2019-08-21 10:00:00','A',0,'SY00000004','2019-08-05 14:04:04','SY00000004','2019-08-05 14:04:04')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10011','기타 테스트','기타 테스트','기타 테스트',null,null,'2019-08-05 15:34:14','M',0,'SY00000093','2019-08-05 15:34:14','SY00000093','2019-08-05 15:34:14')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10011','관리자 계정 생성','[A-RT] 관리자로 등록 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- e : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">관리자로 등록 완료되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">안녕하세요, <span>${adminName}</span>님! <br />A-RT 관리자 계정이 등록 완료되었습니다. <br />아래 계정 정보로 로그인 후 비밀번호 변경 부탁드립니다.</p>

			<!-- s : 관리자 계정 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">관리자 계정 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">아이디</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${loginId}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">비밀번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pswdText}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 관리자 계정 정보 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${backOfficeUrl}" target="_blank" style="display: inline-block; padding: 21px 45px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">관리자 시스템</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EA-01004',null,'A',0,'SY00000004','2019-08-05 18:50:45','SY00000001','2019-09-09 16:04:39')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10004','임시비밀번호발급안내','[ABC마트] 임시비밀번호가 발급되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- e : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">임시비밀번호가 발급되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberName}님!<br />
				<span style="color: #ee1c25">고객님의 요청으로 임시비밀번호가 발급되었으니, <br />
					임시비밀번호를 사용하여 로그인 후 새로운 비밀번호로 변경하시기 바랍니다.</span>
			</p>

			<!-- s : 임시비밀번호 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">임시비밀번호 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">임시비밀번호</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${pswdText}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">발급일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${issueDate}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<ul style="margin: 11px 0 0; padding: 0;">
				<li style="overflow: hidden; margin: 0; padding: 0;">
					<span style="float: left; display: inline-block; width: 2px; height: 2px; margin: 10px 5px 0 0; background-color: #999; vertical-align: top;"></span>
					<p style="float: right; width: 671px; margin: 0; font-size: 12px; color: #666; letter-spacing: -1.2px; line-height: 22px; word-break: keep-all;">
						회원정보는 <span style="color: #000;">마이페이지 &gt; 개인정보 &gt; 비밀번호 변경</span>에서 변경할 수 있습니다.
					</p>
				</li>
			</ul>
			<!-- e : 임시비밀번호 정보 -->

			<div style="margin-top: 50px; font-size: 0;">
				<a href="${abcUrl}/mypage" target="_blank" style="display: inline-block; padding: 21px 48px 20px 49px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" title="새창 열림">마이페이지</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-05012',null,'A',0,'SY00000004','2019-08-07 14:39:04','SY00000001','2019-09-09 16:24:30')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','N',null,'0820','0820','123456','AC1lz707clg',null,'2019-08-26 06:00:00','A',0,'SY10000032','2019-08-20 14:46:51','SY10000032','2019-08-20 14:46:51')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','N',null,'ewerwerwerww','ewerwerwerww','&lt;img alt=&quot;&quot; src=&quot;https://devimg.a-rt.com/art/editor/201908/1566892757466.jpg&quot; style=&quot;width: 294px; height: 294px;&quot; /&gt;','AC1s0ee6ebj',null,'2019-08-28 01:20:00','A',0,'SY10000019','2019-08-27 16:59:36','SY10000019','2019-08-27 16:59:36')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','N',null,'정기이메일','정기이메일','정기정기쓰','AC1ssmw7ch9',null,'2019-09-02 07:20:00','A',0,'SY10000032','2019-08-28 15:07:09','SY00000093','2019-09-04 18:35:43')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','N',null,'광고 이메일','광고 이메일 수정','광고 이메일 수정',null,null,'2019-09-03 10:30:00','A',0,'SY00000093','2019-09-04 18:36:30','SY00000093','2019-09-04 18:39:30')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','N',null,'광고 이메일1','광고 이메일1','&lt;meta charset=&quot;utf-8&quot;&gt;
&lt;title&gt;&lt;/title&gt;
&lt;style type=&quot;text/css&quot;&gt;body {margin:0 auto;padding:0; width:830px;}
img {border:0;}
&lt;/style&gt;
&lt;div style=&quot;text-align: center; line-height: 0;&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1516/20190827165101567.jpg&quot; usemap=&quot;#Map1&quot; /&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/promotion/startEvent?eventId=000507&amp;amp;eDM20190828&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1606/20190827165107619.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005095&amp;amp;eDM20190828&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1726/20190827165114361.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005092&amp;amp;eDM20190828&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1986/20190827165121486.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005111&amp;amp;eDM20190828&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1090/20190827165128867.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005112&amp;amp;eDM20190828&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1184/20190827165138732.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005113&amp;amp;eDM20190828&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1024/20190827165147983.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005114&amp;amp;eDM20190828&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1588/20190827172219208.jpg&quot; style=&quot;width: 830px; height: 230px;&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1685/20190827165234484.jpg&quot; usemap=&quot;#Map6&quot; /&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1944/20190827165244406.jpg&quot; usemap=&quot;#Map2&quot; /&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1188/20190827165301514.jpg&quot; usemap=&quot;#Map3&quot; /&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1692/20190827165313559.jpg&quot; usemap=&quot;#Map4&quot; /&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/mail/confirm?key=AC53WKMD72&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1029/20190827165324514.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/storePickup/storePickup&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1095/20190827165331022.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1213/20190827165338410.jpg&quot; usemap=&quot;#Map5&quot; /&gt;
&lt;div style=&quot;background: rgb(249, 249, 249); margin: 0px auto; padding: 27px 36px; width: 758px; text-align: left;&quot;&gt;
&lt;ul style=&quot;margin: 0px; padding: 0px; color: rgb(156, 156, 156); line-height: 1.6em; font-family: Dotum, &amp;quot;돋움&amp;quot;; font-size: 11px; list-style-type: none;&quot;&gt;
	&lt;li&gt;- 메일 내 상품의 가격 및 정보는 발송일 기준이며 개봉시점에 따라 가격 및 정보는 변경될 수 있습니다.&lt;/li&gt;
	&lt;li&gt;- 본 메일은 정보통신망 이용촉진 및 정보보호 등에 관한 법률 시행규칙에 의거 2019년 08월 27일 기준으로 광고메일 수신동의 여부를&lt;br /&gt;
	&amp;nbsp;&amp;nbsp;확인한 결과 회원님께서 수신 동의를 하셨기에 발송되었습니다.&lt;/li&gt;
	&lt;li&gt;- 메일수신을 원치 않으시면, &lt;a href=&quot;http://www.abcmart.co.kr/abc/etc/refuseEmailDmForm?userDmMail=[$_EMAIL_$]&quot; style=&quot;color: rgb(0, 0, 0);&quot; target=&quot;_blank&quot;&gt;수신거부&lt;/a&gt;를 클릭해주시기 바랍니다. (If you don&amp;#39;t want to receive this mail, &lt;a href=&quot;http://www.abcmart.co.kr/abc/etc/refuseEmailDmForm?userDmMail=[$_EMAIL_$]&quot; style=&quot;color: rgb(0, 0, 0);&quot; target=&quot;_blank&quot;&gt;please click here&lt;/a&gt;)&lt;/li&gt;
	&lt;li&gt;- 본 메일은 발신전용으로 회신이 되지 않으며 자세한 문의사항은 &lt;a href=&quot;http://www.abcmart.co.kr/abc/customer/main/?eDM&quot; style=&quot;color: rgb(0, 0, 0);&quot; target=&quot;_blank&quot;&gt;고객센터&lt;/a&gt;를 이용해주시기 바랍니다.&lt;/li&gt;
&lt;/ul&gt;
&lt;/div&gt;
&lt;br /&gt;
&lt;img alt=&quot;ABC-MART 회사정보&quot; border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1500/20190827165344766.jpg&quot; /&gt;&lt;/div&gt;

&lt;p&gt;&lt;map id=&quot;Map1&quot; name=&quot;Map1&quot;&gt;&lt;area coords=&quot;291,6,545,102&quot; href=&quot;http://www.abcmart.co.kr/?eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;623,123,829,179&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/promotion/startEvent?eventId=000214&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;417,122,624,177&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/user/joinUserIntro/?eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;208,122,418,177&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/event/main/?eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;2,122,208,177&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/theme/topList/?eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area alt=&quot;모바일로 이메일 보기&quot; coords=&quot;1,31,133,91&quot; href=&quot;http://www.abcmart.co.kr/abc/mail/mailView?mailKey=AC53WKMD72&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map2&quot; name=&quot;Map2&quot;&gt;&lt;area coords=&quot;192,1,627,76&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/theme/newPrdtZone? &amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;66,142,245,437&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073087&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;329,136,503,435&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072657&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;588,142,769,432&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073122&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;65,456,261,738&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073126&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;328,454,503,742&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073123&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;589,449,761,744&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072963&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;68,777,240,1072&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072962&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;330,774,494,1082&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072961&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;590,785,762,1080&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072964&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;66,1105,238,1400&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072960&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;330,1107,502,1402&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072576&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;591,1106,763,1401&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072575&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map3&quot; name=&quot;Map3&quot;&gt;&lt;area coords=&quot;5,78,409,336&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005115&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;414,76,816,333&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005116&amp;amp;eDM2019828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;13,330,416,586&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005117&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;418,335,817,589&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005118&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map4&quot; name=&quot;Map4&quot;&gt;&lt;area coords=&quot;3,101,416,517&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005089&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;418,102,826,515&quot; href=&quot;http://www.abcmart.co.kr/abc/event/main#couponFocus&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;-1,521,412,933&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004047&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;419,519,826,933&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=000019&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map5&quot; name=&quot;Map5&quot;&gt;&lt;area coords=&quot;-2,1,275,104&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0000&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;277,4,552,104&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0001&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;555,5,825,103&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0002&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;4,107,274,204&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0003&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;278,107,555,204&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0004&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;560,106,826,202&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0005&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map6&quot; name=&quot;Map6&quot;&gt;&lt;area coords=&quot;19,134,412,389&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0068639&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;417,136,807,392&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0046019&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;21,449,411,704&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0066077&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;419,449,811,707&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0064461&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;19,764,410,1020&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0068719&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;422,762,810,1018&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0064939&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;/p&gt;
',null,null,'2019-09-03 13:10:00','A',0,'SY00000093','2019-09-04 18:40:08','SY00000093','2019-09-04 18:40:08')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','N',null,'정기 이메일','정기 이메일','&lt;meta charset=&quot;utf-8&quot;&gt;
&lt;title&gt;&lt;/title&gt;
&lt;style type=&quot;text/css&quot;&gt;body {margin:0 auto;padding:0; width:830px;}
img {border:0;}
&lt;/style&gt;
&lt;div style=&quot;text-align: center; line-height: 0;&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1319/20190709164722172.jpg&quot; usemap=&quot;#Map1&quot; /&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004988&amp;amp;eDM20190710&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;https://devimg.a-rt.com/art/editor/201909/1568008482761.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004986&amp;amp;eDM20190710&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1946/20190709165039689.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004992&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1000/20190709165711211.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004993&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1024/20190709165718559.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004994&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1078/20190709165724813.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004995&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1581/20190709165730826.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1233/20190709165747381.jpg&quot; usemap=&quot;#Map6&quot; /&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1857/20190709165754275.jpg&quot; usemap=&quot;#Map2&quot; /&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1104/20190709165801378.jpg&quot; usemap=&quot;#Map3&quot; /&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1933/20190709165808273.jpg&quot; usemap=&quot;#Map4&quot; /&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/mail/confirm?key=AC53WKM9EK&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1058/20190709165814422.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/storePickup/storePickup&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1935/20190709165821124.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1675/20190709165827171.jpg&quot; usemap=&quot;#Map5&quot; /&gt;
&lt;div style=&quot;background: rgb(249, 249, 249); margin: 0px auto; padding: 27px 36px; width: 758px; text-align: left;&quot;&gt;
&lt;ul style=&quot;margin: 0px; padding: 0px; color: rgb(156, 156, 156); line-height: 1.6em; font-family: Dotum, &amp;quot;돋움&amp;quot;; font-size: 11px; list-style-type: none;&quot;&gt;
	&lt;li&gt;- 메일 내 상품의 가격 및 정보는 발송일 기준이며 개봉시점에 따라 가격 및 정보는 변경될 수 있습니다.&lt;/li&gt;
	&lt;li&gt;- 본 메일은 정보통신망 이용촉진 및 정보보호 등에 관한 법률 시행규칙에 의거 2019년 07월 09일 기준으로 광고메일 수신동의 여부를&lt;br /&gt;
	&amp;nbsp;&amp;nbsp;확인한 결과 회원님께서 수신 동의를 하셨기에 발송되었습니다.&lt;/li&gt;
	&lt;li&gt;- 메일수신을 원치 않으시면, &lt;a href=&quot;http://www.abcmart.co.kr/abc/etc/refuseEmailDmForm?userDmMail=[$_EMAIL_$]&quot; style=&quot;color: rgb(0, 0, 0);&quot; target=&quot;_blank&quot;&gt;수신거부&lt;/a&gt;를 클릭해주시기 바랍니다. (If you don&amp;#39;t want to receive this mail, &lt;a href=&quot;http://www.abcmart.co.kr/abc/etc/refuseEmailDmForm?userDmMail=[$_EMAIL_$]&quot; style=&quot;color: rgb(0, 0, 0);&quot; target=&quot;_blank&quot;&gt;please click here&lt;/a&gt;)&lt;/li&gt;
	&lt;li&gt;- 본 메일은 발신전용으로 회신이 되지 않으며 자세한 문의사항은 &lt;a href=&quot;http://www.abcmart.co.kr/abc/customer/main/?eDM&quot; style=&quot;color: rgb(0, 0, 0);&quot; target=&quot;_blank&quot;&gt;고객센터&lt;/a&gt;를 이용해주시기 바랍니다.&lt;/li&gt;
&lt;/ul&gt;
&lt;/div&gt;
&lt;br /&gt;
&lt;img alt=&quot;ABC-MART 회사정보&quot; border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1520/20190709165834804.jpg&quot; /&gt;&lt;/div&gt;

&lt;p&gt;&lt;map id=&quot;Map1&quot; name=&quot;Map1&quot;&gt;&lt;area coords=&quot;291,6,545,102&quot; href=&quot;http://www.abcmart.co.kr/?eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;623,123,829,179&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/promotion/startEvent?eventId=000214&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;417,122,624,177&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/user/joinUserIntro/?eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;208,122,418,177&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/event/main/?eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;2,122,208,177&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/theme/topList/?eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area alt=&quot;모바일로 이메일 보기&quot; coords=&quot;1,31,133,91&quot; href=&quot;http://www.abcmart.co.kr/abc/mail/mailView?mailKey=AC53WKM9EK&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map2&quot; name=&quot;Map2&quot;&gt;&lt;area coords=&quot;192,1,627,76&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/theme/newPrdtZone?&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;65,141,244,436&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072275&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;329,136,503,435&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072277&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;588,142,769,432&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072276&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;65,456,261,738&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0068651&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;328,454,503,742&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072122&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;589,449,761,744&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072270&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;68,777,240,1072&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072271&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;330,774,494,1082&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072272&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;590,785,762,1080&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072129&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;66,1105,238,1400&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072124&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;330,1107,502,1402&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072123&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;591,1106,763,1401&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072047&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map3&quot; name=&quot;Map3&quot;&gt;&lt;area coords=&quot;5,77,409,335&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004996&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;414,76,816,333&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004997&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;11,330,414,586&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004998&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;418,335,817,589&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004999&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map4&quot; name=&quot;Map4&quot;&gt;&lt;area coords=&quot;1,101,414,517&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004989&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;418,103,826,516&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004968&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;-1,521,412,933&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004984&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;419,519,826,933&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=000341&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map5&quot; name=&quot;Map5&quot;&gt;&lt;area coords=&quot;-2,1,275,104&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0000&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;277,4,552,104&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0001&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;555,5,825,103&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0002&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;4,107,274,204&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0003&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;278,107,555,204&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0004&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;560,106,826,202&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0005&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map6&quot; name=&quot;Map6&quot;&gt;&lt;area coords=&quot;18,134,411,389&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0071061&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;417,136,807,392&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0066407&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;21,449,411,704&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0070285&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;419,449,811,707&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0055433&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;19,764,410,1020&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0068873&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;421,762,809,1018&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0070220&amp;amp;eDM20190710&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;/p&gt;
',null,null,'2019-09-09 00:00:00','A',0,'SY10000019','2019-09-09 14:55:58','SY10000019','2019-09-09 14:56:25')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','N',null,'정기 이메일 TEST','정기 이메일 TEST','&lt;meta charset=&quot;utf-8&quot;&gt;
&lt;title&gt;&lt;/title&gt;
&lt;style type=&quot;text/css&quot;&gt;body {margin:0 auto;padding:0; width:830px;}
img {border:0;}
&lt;/style&gt;
&lt;div style=&quot;line-height:0; text-align:center;&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1345/20190924162003135.jpg&quot; usemap=&quot;#Map1&quot; /&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005173&amp;amp;eDM20190925&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1644/20190924162010774.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005169&amp;amp;eDM20190925&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1041/20190924162016544.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005174&amp;amp;eDM20190925&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1038/20190924162138896.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005175&amp;amp;eDM20190925&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1616/20190924162220397.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005176&amp;amp;eDM20190925&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1106/20190924162230718.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005177&amp;amp;eDM20190925&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1243/20190924162236290.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1953/20190924162404747.jpg&quot; usemap=&quot;#Map6&quot; /&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1506/20190924162412411.jpg&quot; usemap=&quot;#Map2&quot; /&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1610/20190924162417496.jpg&quot; usemap=&quot;#Map3&quot; /&gt;&lt;br /&gt;
&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1580/20190924170912398.jpg&quot; style=&quot;border-width: 0px; border-style: solid; width: 830px; height: 934px;&quot; usemap=&quot;#Map4&quot; /&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/mail/confirm?key=AC53WKMZ2K&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1252/20190924162432206.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/storePickup/storePickup&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1342/20190924162438873.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1705/20190924162445140.jpg&quot; usemap=&quot;#Map5&quot; /&gt;
&lt;div style=&quot;margin:0 auto;padding:0;background:#f9f9f9;width:758px;padding:27px 36px; text-align:left;&quot;&gt;
&lt;ul style=&quot;list-style-type:none;margin:0;color:#9c9c9c;padding:0;font-size:11px;font-family:Dotum, &#39;돋움&#39;;line-height:1.6em&quot;&gt;
	&lt;li&gt;- 메일 내 상품의 가격 및 정보는 발송일 기준이며 개봉시점에 따라 가격 및 정보는 변경될 수 있습니다.&lt;/li&gt;
	&lt;li&gt;- 본 메일은 정보통신망 이용촉진 및 정보보호 등에 관한 법률 시행규칙에 의거 2019년 09월 24일 기준으로 광고메일 수신동의 여부를&lt;br /&gt;
	&amp;nbsp;&amp;nbsp;확인한 결과 회원님께서 수신 동의를 하셨기에 발송되었습니다.&lt;/li&gt;
	&lt;li&gt;- 메일수신을 원치 않으시면, &lt;a href=&quot;http://www.abcmart.co.kr/abc/etc/refuseEmailDmForm?userDmMail=[$_EMAIL_$]&quot; style=&quot;color: rgb(0, 0, 0);&quot; target=&quot;_blank&quot;&gt;수신거부&lt;/a&gt;를 클릭해주시기 바랍니다. (If you don&amp;#39;t want to receive this mail, &lt;a href=&quot;http://www.abcmart.co.kr/abc/etc/refuseEmailDmForm?userDmMail=[$_EMAIL_$]&quot; style=&quot;color: rgb(0, 0, 0);&quot; target=&quot;_blank&quot;&gt;please click here&lt;/a&gt;)&lt;/li&gt;
	&lt;li&gt;- 본 메일은 발신전용으로 회신이 되지 않으며 자세한 문의사항은 &lt;a href=&quot;http://www.abcmart.co.kr/abc/customer/main/?eDM&quot; style=&quot;color: rgb(0, 0, 0);&quot; target=&quot;_blank&quot;&gt;고객센터&lt;/a&gt;를 이용해주시기 바랍니다.&lt;/li&gt;
&lt;/ul&gt;
&lt;/div&gt;
&lt;br /&gt;
&lt;img alt=&quot;ABC-MART 회사정보&quot; border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1092/20190924162452608.jpg&quot; /&gt;&lt;/div&gt;

&lt;p&gt;&lt;map id=&quot;Map1&quot; name=&quot;Map1&quot;&gt;&lt;area coords=&quot;291,6,545,102&quot; href=&quot;http://www.abcmart.co.kr/?eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;623,123,829,179&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/promotion/startEvent?eventId=000214&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;417,122,624,177&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/user/joinUserIntro/?eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;208,122,418,177&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/event/main/?eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;2,122,208,177&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/theme/topList/?eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area alt=&quot;모바일로 이메일 보기&quot; coords=&quot;1,31,133,91&quot; href=&quot;http://www.abcmart.co.kr/abc/mail/mailView?mailKey=AC53WKMZ2K&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map2&quot; name=&quot;Map2&quot;&gt;&lt;area coords=&quot;192,1,627,76&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/theme/newPrdtZone?&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;66,142,245,437&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072325&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;329,136,503,435&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072326&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;588,142,769,432&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072333&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;65,456,261,738&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072426&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;328,454,503,742&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072425&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;589,449,761,744&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0071807&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;68,777,240,1072&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072334&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;330,774,494,1082&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073623&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;590,785,762,1080&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073622&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;66,1105,238,1400&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073621&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;330,1107,502,1402&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072658&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;591,1106,763,1401&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072659&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map3&quot; name=&quot;Map3&quot;&gt;&lt;area coords=&quot;5,79,409,337&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005178&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;415,76,817,333&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005179&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;13,330,416,586&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005180&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;418,335,817,589&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005181&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map4&quot; name=&quot;Map4&quot;&gt;&lt;area coords=&quot;6,102,419,518&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005145&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;418,102,826,515&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=000341&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;4,522,417,934&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005182&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;419,519,826,933&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004047&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map5&quot; name=&quot;Map5&quot;&gt;&lt;area coords=&quot;-2,1,275,104&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0000&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;277,4,552,104&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0001&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;555,5,825,103&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0002&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;4,107,274,204&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0003&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;278,107,555,204&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0004&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;560,106,826,202&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0005&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map6&quot; name=&quot;Map6&quot;&gt;&lt;area coords=&quot;16,136,409,391&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0068300&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;418,136,808,392&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0066105&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;21,449,411,704&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0071761&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;419,449,811,707&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0071895&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;19,764,410,1020&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0065173&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;422,762,810,1018&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0067150&amp;amp;eDM20190925&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;/p&gt;
',null,null,'2019-09-25 05:00:00','A',0,'SY10000032','2019-09-25 18:27:31','SY10000032','2019-09-25 18:27:31')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','N',null,'테스트테스트 ','테스트테스트 ','&lt;meta charset=&quot;utf-8&quot;&gt;
&lt;title&gt;&lt;/title&gt;
&lt;style type=&quot;text/css&quot;&gt;body {margin:0 auto;padding:0; width:830px;}
img {border:0;}
&lt;/style&gt;
&lt;div style=&quot;text-align: center; line-height: 0;&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1516/20190827165101567.jpg&quot; usemap=&quot;#Map1&quot; /&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/promotion/startEvent?eventId=000507&amp;amp;eDM20190828&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1606/20190827165107619.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005095&amp;amp;eDM20190828&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1726/20190827165114361.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005092&amp;amp;eDM20190828&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1986/20190827165121486.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005111&amp;amp;eDM20190828&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1090/20190827165128867.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005112&amp;amp;eDM20190828&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1184/20190827165138732.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005113&amp;amp;eDM20190828&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1024/20190827165147983.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005114&amp;amp;eDM20190828&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1588/20190827172219208.jpg&quot; style=&quot;width: 830px; height: 230px;&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1685/20190827165234484.jpg&quot; usemap=&quot;#Map6&quot; /&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1944/20190827165244406.jpg&quot; usemap=&quot;#Map2&quot; /&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1188/20190827165301514.jpg&quot; usemap=&quot;#Map3&quot; /&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1692/20190827165313559.jpg&quot; usemap=&quot;#Map4&quot; /&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/mail/confirm?key=AC53WKMD72&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1029/20190827165324514.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/storePickup/storePickup&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1095/20190827165331022.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1213/20190827165338410.jpg&quot; usemap=&quot;#Map5&quot; /&gt;
&lt;div style=&quot;background: rgb(249, 249, 249); margin: 0px auto; padding: 27px 36px; width: 758px; text-align: left;&quot;&gt;
&lt;ul style=&quot;margin: 0px; padding: 0px; color: rgb(156, 156, 156); line-height: 1.6em; font-family: Dotum, &amp;quot;돋움&amp;quot;; font-size: 11px; list-style-type: none;&quot;&gt;
	&lt;li&gt;- 메일 내 상품의 가격 및 정보는 발송일 기준이며 개봉시점에 따라 가격 및 정보는 변경될 수 있습니다.&lt;/li&gt;
	&lt;li&gt;- 본 메일은 정보통신망 이용촉진 및 정보보호 등에 관한 법률 시행규칙에 의거 2019년 08월 27일 기준으로 광고메일 수신동의 여부를&lt;br /&gt;
	&amp;nbsp;&amp;nbsp;확인한 결과 회원님께서 수신 동의를 하셨기에 발송되었습니다.&lt;/li&gt;
	&lt;li&gt;- 메일수신을 원치 않으시면, &lt;a href=&quot;http://www.abcmart.co.kr/abc/etc/refuseEmailDmForm?userDmMail=[$_EMAIL_$]&quot; style=&quot;color: rgb(0, 0, 0);&quot; target=&quot;_blank&quot;&gt;수신거부&lt;/a&gt;를 클릭해주시기 바랍니다. (If you don&amp;#39;t want to receive this mail, &lt;a href=&quot;http://www.abcmart.co.kr/abc/etc/refuseEmailDmForm?userDmMail=[$_EMAIL_$]&quot; style=&quot;color: rgb(0, 0, 0);&quot; target=&quot;_blank&quot;&gt;please click here&lt;/a&gt;)&lt;/li&gt;
	&lt;li&gt;- 본 메일은 발신전용으로 회신이 되지 않으며 자세한 문의사항은 &lt;a href=&quot;http://www.abcmart.co.kr/abc/customer/main/?eDM&quot; style=&quot;color: rgb(0, 0, 0);&quot; target=&quot;_blank&quot;&gt;고객센터&lt;/a&gt;를 이용해주시기 바랍니다.&lt;/li&gt;
&lt;/ul&gt;
&lt;/div&gt;
&lt;br /&gt;
&lt;img alt=&quot;ABC-MART 회사정보&quot; border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1500/20190827165344766.jpg&quot; /&gt;&lt;/div&gt;

&lt;p&gt;&lt;map id=&quot;Map1&quot; name=&quot;Map1&quot;&gt;&lt;area coords=&quot;291,6,545,102&quot; href=&quot;http://www.abcmart.co.kr/?eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;623,123,829,179&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/promotion/startEvent?eventId=000214&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;417,122,624,177&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/user/joinUserIntro/?eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;208,122,418,177&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/event/main/?eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;2,122,208,177&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/theme/topList/?eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area alt=&quot;모바일로 이메일 보기&quot; coords=&quot;1,31,133,91&quot; href=&quot;http://www.abcmart.co.kr/abc/mail/mailView?mailKey=AC53WKMD72&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map2&quot; name=&quot;Map2&quot;&gt;&lt;area coords=&quot;192,1,627,76&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/theme/newPrdtZone? &amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;66,142,245,437&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073087&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;329,136,503,435&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072657&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;588,142,769,432&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073122&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;65,456,261,738&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073126&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;328,454,503,742&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073123&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;589,449,761,744&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072963&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;68,777,240,1072&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072962&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;330,774,494,1082&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072961&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;590,785,762,1080&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072964&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;66,1105,238,1400&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072960&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;330,1107,502,1402&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072576&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;591,1106,763,1401&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072575&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map3&quot; name=&quot;Map3&quot;&gt;&lt;area coords=&quot;5,78,409,336&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005115&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;414,76,816,333&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005116&amp;amp;eDM2019828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;13,330,416,586&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005117&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;418,335,817,589&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005118&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map4&quot; name=&quot;Map4&quot;&gt;&lt;area coords=&quot;3,101,416,517&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005089&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;418,102,826,515&quot; href=&quot;http://www.abcmart.co.kr/abc/event/main#couponFocus&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;-1,521,412,933&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004047&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;419,519,826,933&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=000019&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map5&quot; name=&quot;Map5&quot;&gt;&lt;area coords=&quot;-2,1,275,104&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0000&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;277,4,552,104&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0001&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;555,5,825,103&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0002&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;4,107,274,204&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0003&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;278,107,555,204&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0004&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;560,106,826,202&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0005&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map6&quot; name=&quot;Map6&quot;&gt;&lt;area coords=&quot;19,134,412,389&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0068639&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;417,136,807,392&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0046019&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;21,449,411,704&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0066077&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;419,449,811,707&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0064461&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;19,764,410,1020&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0068719&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;422,762,810,1018&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0064939&amp;amp;eDM20190828&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;/p&gt;
',null,null,'2019-09-26 14:00:00','A',0,'SY10000012','2019-09-26 11:59:10','SY10000012','2019-09-26 11:59:10')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10000','TEST 템플릿','TEST 템플릿','TEST 템플릿',null,null,'2019-09-30 10:05:21','M',0,'SY20000047','2019-09-30 10:05:21','SY20000047','2019-09-30 10:05:21')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10011','광고수신동의확인','[ABC마트] 광고성 정보의 수신동의 여부 안내드립니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- e : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">광고성 정보 수신동의 여부 안내 드립니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요!<br />
				ABC마트 코리아입니다.<br /><br />

				${memberName}님은 <span style="color: #ee1c25">${requestDate}</span> A-RT.COM의 <span style="color: #ee1c25">광고성 정보(${messageType})</span> 수신을 동의하셨습니다. <br /><br />

				이메일과 SMS의 수신동의를 철회하시려면, <span style="font-weight: bold;">''마이페이지 &gt; 개인정보수정 &gt; 마케팅 활용 동의 여부''</span> 에서<br />
				수신동의 여부를 수정해주시기 바라며,<br />
				PUSH의 수신동의를 철회하시려면, A-RT.COM APP의 <span style="font-weight: bold;">''설정 &gt; 광고성 알림(PUSH) 설정''</span> 에서 <br />
				수신동의 여부를 수정해주시기 바랍니다.<br /><br />

				수신 여부의 수정이 없을 경우 기존에 선택하신 수신 여부가 유지되며, <br />
				2년 마다 수신동의 여부를 안내 드립니다.
			</p>
			<div style="margin-top: 44px; font-size: 0;">
				<a href="${landingUrl}" target="_blank" style="display: inline-block; padding: 20px 53px 19px 54px; border: 1px solid #000; font-weight: bold; font-size: 15px; color: #000; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">마이페이지</a>
				<a href="${abcUrl}" target="_blank" style="display: inline-block; padding: 21px 45px 20px; margin-left: 10px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">메인 바로가기</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 17px 0;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px;">
					정보통신망 이용촉진 및 정보보호 등에 관한 법률 제30조의2에 근거하여 회원님의 이메일 수신 동의 여부와 관계없이 법적 의무 사항으로 발송된 메일입니다. <br />
					본 메일은 발신전용으로 회신이 되지 않습니다. 보다 자세한 문의사항은 <a href="${abcUrl}/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.
				</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-05011',null,'A',0,'SY00000001','2019-11-12 14:21:40','SY00000001','2019-11-12 17:24:06')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10011','문의글 답변 (상품 Q&amp;A)','[A-RT] 고객님의 문의에 답변 드립니다.','<meta charset="UTF-8"><meta http-equiv="X-UA-Compatible" content="ie=edge">
<title></title>
<style type="text/css">html, body { margin: 0; padding: 0; }
</style>
<!-- s: wrap -->
<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;"><!-- s : 190813 수정 | mail header 영역 table 코딩으로 수정 --><!-- s: header -->
<table align="center" border="0" cellpadding="0" cellspacing="0" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
	<tbody>
		<tr style="background-color: #000;">
			<td style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;" valign="top"><a href="${abcUrl}" style="margin: 0; padding: 0; vertical-align: top; " target="_blank" title="새창 열림"><img alt="A-RT" src="${abcUrl}/static/images/email/art_logo.png" style="border: 0; vertical-align: top;" /> </a></td>
			<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
			<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
				<tbody>
					<tr>
						<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
						<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img alt="" src="${abcUrl}/static/images/email/util_line.gif" valign="top" /></td>
						<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트&middot;쿠폰</a></td>
						<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img alt="" src="${abcUrl}/static/images/email/util_line.gif" valign="top" /></td>
						<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
						<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img alt="" src="${abcUrl}/static/images/email/util_line.gif" valign="top" /></td>
						<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
	</tbody>
</table>

<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">고객님의 문의에 답변 드립니다.</h2>

<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">안녕하세요, ${memberName}님!<br />
고객님께서 문의주신 상품 Q&amp;A에 대한 답변을 드립니다.<br />
A-RT는 언제나 고객님의 만족을 위해 최선을 다하겠습니다. 감사합니다.</p>
<!-- s : 상품 Q&A에에 대한 답변 -->

<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">상품 Q&amp;A에 대한 답변</h3>

<div style="margin-top: 15px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
<div style="margin: 0; padding: 22px 50px 24px; background: url(images/icon_faq_q.png) 14px 22px no-repeat;">
<p style="margin: 0; padding: 0; font-size: 15px; line-height: 24px; letter-spacing: -0.75px;">${inqryContText}</p>
</div>

<div style="margin: 0; padding: 30px 30px 39px 66px; background: #f8f8f8 url(images/icon_faq_a.png) 30px 30px no-repeat;">
<p style="margin: 0; padding: 0; font-size: 15px; line-height: 24px; letter-spacing: -0.75px;">${aswrContText}</p>
<span style="display: block; margin-top: 13px; padding: 0; font-size: 13px; color: #666;">${aswrDtm}</span></div>
</div>

<ul style="margin: 11px 0 0; padding: 0;">
	<li style="overflow: hidden; margin: 0; padding: 0;">
	<p style="float: right; width: 671px; margin: 0; font-size: 12px; color: #666; letter-spacing: -0.6px; line-height: 22px; word-break: keep-all;">문의하신 사항에 대한 답변이 충분하신가요? 만일 충분한 답변이 되지 못했거나 추가로 질문할 내용이 있으시다면<br />
	1:1상담 문의를 해주세요. 신속하게 처리해 드리겠습니다.</p>
	</li>
</ul>
<!-- s : 상품 Q&A에에 대한 답변 -->

<div style="margin-top: 50px; font-size: 0;"><a href="${abcUrl}/board/member-counsel/read-inquiry-create-form" style="display: inline-block; padding: 21px 50px 20px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;" target="_blank" title="새창 열림">1:1 상담하기</a></div>
</div>
<!-- e: content --><!-- s: footer -->

<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
<div style="padding: 20px 0 21px;">
<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="${abcUrl}/board" style="color: #666;" target="_blank">고객센터</a>로 문의해 주시기 바랍니다.</p>
</div>

<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
<p style="margin:0;"><span style="color: #000">(주)에이비씨마트 코리아</span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층<br />
사업자등록번호 201-81-76174통신판매업신고 제 2015-서울중고-1036호대표이사 이기호개인정보보호 책임자 박영수<br />
대표번호 080-701-7770, 1588-9667전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span></p>
</div>
</div>
</div>
<!-- e: footer --></div>
<!-- e: wrap -->',null,'EC-04003',null,'A',0,'SY00000094','2019-11-13 18:36:41','SY00000094','2019-11-14 10:05:54')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10004','기존 멤버십 회원 멤버십 전환','[ABC마트] 회원전환이 완료되었습니다.','<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">

	<title>A-RT</title>
	<style>
		html, body { margin: 0; padding: 0; }
	</style>
</head>
<body>
	<!-- s: wrap -->
	<div style="width: 750px; margin: 0 auto; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
		<!-- s : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: header -->
		<table border="0" cellpadding="0" cellspacing="0" align="center" style="width: 100%; margin: 0 auto; border-collapse: collapse; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
			<tbody>
				<tr style="background-color: #000;">
					<td valign="top" style="height: 80px; padding: 0 0 0 36px; vertical-align: middle;">
						<a href="${abcUrl}" target="_blank" style="margin: 0; padding: 0; vertical-align: top; " title="새창 열림">
							<img src="${abcUrl}/static/images/email/art_logo.png" alt="A-RT" style="border: 0; vertical-align: top;" />
						</a>
					</td>
					<td style="width: 343px; height: 41px; padding: 39px 0 0 0; vertical-align: top;">
						<table align="right" border="0" cellpadding="0" cellspacing="0" style="width: 100%; padding: 0; margin: 0; border-collapse: collapse; mso-table-lspace:0; mso-table-rspace:0; table-layout: fixed; mso-table-lspace:0; mso-table-rspace:0; line-height: 1; line-height: 1; color: #000; font-family:''맑은고딕'', ''MalgunGothic'', ''Malgun Gothic'', Dotum;">
							<tbody>
								<tr>
									<td style="width: 57px; padding: 0; vertical-align: top;"><a href="${abcUrl}/display/best" style="font-size: 15px; color: #fff; text-decoration: none;">BEST</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 93px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/event/main" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">이벤트·쿠폰</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 58px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/promotion/planning-display" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">기획전</a></td>
									<td style="width: 1px; padding: 7px 0 0; vertical-align: top;"><img src="${abcUrl}/static/images/email/util_line.gif" alt="" valign="top"></td>
									<td style="width: 56px; padding: 0 0 0 20px; vertical-align: top;"><a href="${abcUrl}/display/webzine" style="font-size: 15px; color: #fff; text-decoration: none; letter-spacing: -0.75px">웹진</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- e: header -->
		<!-- e : 190813 수정 | mail header 영역 table 코딩으로 수정 -->
		<!-- s: content -->
		<div style="min-height: 422px; padding: 68px 34px 79px; border: 1px solid #999; border-top: none;">
			<h2 style="margin: 0; padding: 0; font-size: 28px; color: #000; font-weight: bold; line-height:1; letter-spacing: -2.8px;">회원전환이 완료되었습니다.</h2>
			<p style="padding:0; margin: 31px 0 0; font-size: 15px; line-height:25px; letter-spacing: -1.5px;">
				안녕하세요, ${memberInfo.memberName}님!<br />
				<span style="color: #ee1c25">A-RT 통합멤버십회원 전환</span>을 진심으로 축하합니다.<br />
				앞으로 다양한 상품정보와 함께 만족스러운 쇼핑을 즐길 수 있도록 최선을 다하며,<br />
				항상 새로운 뉴스와 이벤트로 고객님의 알찬 쇼핑을 위해 열심히 노력하겠습니다. <br />
				감사합니다.
			</p>

			<!-- s : 회원전환 정보 -->
			<h3 style="margin: 44px 0 0; padding: 0; font-size: 15px; color: #000; font-weight: bold; line-height:1; letter-spacing: -1.5px;">회원전환 정보</h3>
			<div style="margin-top: 15px; padding: 17px 0 18px; border-top: 2px solid #000; border-bottom: 1px solid #d5d5d5;">
				<!-- s: table -->
				<table style="width: 100%; border-collapse: collapse; table-layout: fixed;">
					<colgroup>
						<col style="width: 140px;" />
						<col style="width: auto;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">이름</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberInfo.memberName}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">아이디</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${memberInfo.loginId}</td>
						</tr>
						<tr>
							<th scope="row" style="background-color: #fff; font-weight: 400; padding: 12px 15px; font-size: 14px; color: #666; letter-spacing: -1.4px; text-align: left; line-height:1; vertical-align: top;">전환일</th>
							<td style="background-color: #fff; font-size: 15px; color: #000; padding: 8px 15px; line-height: 22px; letter-spacing: -1.5px;">${joinDtm}</td>
						</tr>
					</tbody>
				</table>
				<!-- e: table -->
			</div>
			<!-- e : 회원전환 정보 -->

			<div style="margin-top: 45px; font-size: 0;">
				<a href="${abcUrl}/mypage" target="_blank" style="display: inline-block; padding: 20px 53px 19px 54px; border: 1px solid #000; font-weight: bold; font-size: 15px; color: #000; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">마이페이지</a>
				<a href="${abcUrl}" target="_blank" style="display: inline-block; padding: 21px 61px 20px 62px; margin-left: 10px; background-color: #000; font-weight: bold; font-size: 15px; color: #fff; text-decoration: none; line-height: 1; letter-spacing: -0.75px; text-align: center;"  title="새창 열림">메인 바로가기</a>
			</div>
		</div>
		<!-- e: content -->
		<!-- s: footer -->
		<div style="position:relative; font-family: ''Gulim'', ''굴림'';">
			<div style="padding: 20px 0 21px;">
				<p style="padding: 0; margin: 0; font-size: 11px; color: #999; line-height: 1; letter-spacing: -0.55px;">본 메일은 발신전용으로 회신이 되지 않습니다. 문의사항은 <a href="https://www.a-rt.com/board" target="_blank" style="color: #666;">고객센터</a>로 문의해 주시기 바랍니다.</p>
			</div>
			<div style="overflow: hidden; padding: 17px 0 0; border-top: 1px solid #e5e5e5;">
				<div style="font-size: 11px; color: #999; line-height: 18px; letter-spacing: -0.55px">
					<p style="margin:0;">
						<span style="color: #000">(주)에이비씨마트 코리아</span><span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>주소 서울특별시 중구 을지로 2가 203 파인애비뉴 B동 21층 <br />
						사업자등록번호 201-81-76174<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>통신판매업신고 제 2015-서울중고-1036호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>대표이사 이기호<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>개인정보보호 책임자 박영수<br />
						대표번호 080-701-7770, 1588-9667<span style="display: inline-block; width:1px; height: 10px; margin: 3px 8px 0 9px; background-color: #d5d5d5; vertical-align: top;"></span>전자우편주소 <span style="color: #999; text-decoration: none; cursor: default;">abcmartcs@abcmartkorea.co.kr</span>
					</p>
				</div>
			</div>
		</div>
		<!-- e: footer -->
	</div>
	<!-- e: wrap -->
</body>
</html>
',null,'EC-05010-1','2019-11-19 15:43:18','A',0,'SY00000001','2019-11-19 15:43:18','SY00000001','2019-11-19 15:43:18')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','Y','10008','EDM','정기 EDM TEST ','&lt;meta charset=&quot;utf-8&quot;&gt;
&lt;title&gt;&lt;/title&gt;
&lt;style type=&quot;text/css&quot;&gt;body {margin:0 auto;padding:0; width:830px;}
img {border:0;}
&lt;/style&gt;
&lt;div style=&quot;line-height:0; text-align:center;&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1472/20191231160334531.jpg&quot; usemap=&quot;#Map1&quot; /&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/mobile/planDisp/detail?plndpId=005426&amp;amp;eDM2020102&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1536/20191231160341351.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005425&amp;amp;eDM20200102&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1009/20191231160348580.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005428&amp;amp;eDM20200102&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1274/20191231160401471.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005429&amp;amp;eDM20200102&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1168/20191231160410431.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005430&amp;amp;eDM20200102&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1287/20191231160417880.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005431&amp;amp;eDM20200102&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1236/20191231160424713.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1838/20191231171112513.jpg&quot; style=&quot;border-width: 0px; border-style: solid; width: 830px; height: 1058px;&quot; usemap=&quot;#Map6&quot; /&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1844/20191231160545746.jpg&quot; usemap=&quot;#Map2&quot; /&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1345/20191231160553674.jpg&quot; usemap=&quot;#Map3&quot; /&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1019/20191231160600527.jpg&quot; usemap=&quot;#Map4&quot; /&gt;&lt;br /&gt;
&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/storePickup/storePickup&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1807/20191231161844128.jpg&quot; style=&quot;width: 830px; height: 97px;&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1584/20191231160620388.jpg&quot; usemap=&quot;#Map5&quot; /&gt;
&lt;div style=&quot;margin:0 auto;padding:0;background:#f9f9f9;width:758px;padding:27px 36px; text-align:left;&quot;&gt;
&lt;ul style=&quot;list-style-type:none;margin:0;color:#9c9c9c;padding:0;font-size:11px;font-family:Dotum, &#39;돋움&#39;;line-height:1.6em&quot;&gt;
	&lt;li&gt;- 메일 내 상품의 가격 및 정보는 발송일 기준이며 개봉시점에 따라 가격 및 정보는 변경될 수 있습니다.&lt;/li&gt;
	&lt;li&gt;- 본 메일은 정보통신망 이용촉진 및 정보보호 등에 관한 법률 시행규칙에 의거 2019년 12월 31일 기준으로 광고메일 수신동의 여부를&lt;br /&gt;
	&amp;nbsp;&amp;nbsp;확인한 결과 회원님께서 수신 동의를 하셨기에 발송되었습니다.&lt;/li&gt;
	&lt;li&gt;- 메일수신을 원치 않으시면, &lt;a href=&quot;http://www.abcmart.co.kr/abc/etc/refuseEmailDmForm?userDmMail=[$_EMAIL_$]&quot; style=&quot;color: rgb(0, 0, 0);&quot; target=&quot;_blank&quot;&gt;수신거부&lt;/a&gt;를 클릭해주시기 바랍니다. (If you don&amp;#39;t want to receive this mail, &lt;a href=&quot;http://www.abcmart.co.kr/abc/etc/refuseEmailDmForm?userDmMail=[$_EMAIL_$]&quot; style=&quot;color: rgb(0, 0, 0);&quot; target=&quot;_blank&quot;&gt;please click here&lt;/a&gt;)&lt;/li&gt;
	&lt;li&gt;- 본 메일은 발신전용으로 회신이 되지 않으며 자세한 문의사항은 &lt;a href=&quot;http://www.abcmart.co.kr/abc/customer/main/?eDM20200102&quot; style=&quot;color: rgb(0, 0, 0);&quot; target=&quot;_blank&quot;&gt;고객센터&lt;/a&gt;를 이용해주시기 바랍니다.&lt;/li&gt;
&lt;/ul&gt;
&lt;/div&gt;
&lt;br /&gt;
&lt;img alt=&quot;ABC-MART 회사정보&quot; border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1816/20191231160627277.jpg&quot; /&gt;&lt;/div&gt;

&lt;p&gt;&lt;map id=&quot;Map1&quot; name=&quot;Map1&quot;&gt;&lt;area coords=&quot;291,6,545,102&quot; href=&quot;http://www.abcmart.co.kr/?eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;623,123,829,179&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/promotion/startEvent?eventId=000214&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;417,122,624,177&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/user/joinUserIntro/?eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;208,122,418,177&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/event/main/?eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;2,122,208,177&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/theme/topList/?eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area alt=&quot;모바일로 이메일 보기&quot; coords=&quot;1,31,133,91&quot; href=&quot;http://www.abcmart.co.kr/abc/mail/mailView?mailKey=AC53WKM9Y0&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map2&quot; name=&quot;Map2&quot;&gt;&lt;area coords=&quot;192,1,627,76&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/theme/newPrdtZone? &amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;69,140,248,435&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073063&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;329,136,503,435&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073060&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;588,142,769,432&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0075039&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;65,456,261,738&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0075038&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;328,454,503,742&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0075037&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;589,449,761,744&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0075036&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;68,777,240,1072&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0075035&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;330,774,494,1082&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0074890&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;590,785,762,1080&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0074891&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;66,1105,238,1400&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0074924&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;330,1107,502,1402&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0074930&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;591,1106,763,1401&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0075070&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map3&quot; name=&quot;Map3&quot;&gt;&lt;area coords=&quot;12,75,416,333&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005432&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;417,76,819,333&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005433&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;13,334,416,590&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005434&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;418,335,817,589&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005435&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map4&quot; name=&quot;Map4&quot;&gt;&lt;area coords=&quot;6,102,419,518&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005408&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;418,102,826,515&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005402&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;5,522,418,934&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005369&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;419,519,826,933&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=004047&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map5&quot; name=&quot;Map5&quot;&gt;&lt;area coords=&quot;-2,1,275,104&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0000&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;277,4,552,104&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0001&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;555,5,825,103&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0002&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;4,107,274,204&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0003&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;278,107,555,204&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0004&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;560,106,826,202&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0005&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map6&quot; name=&quot;Map6&quot;&gt;&lt;area coords=&quot;16,136,409,391&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073055&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;418,136,808,392&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0071528&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;21,450,411,705&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0050449&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;419,449,811,707&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0074204&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;19,764,410,1020&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073770&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;422,762,810,1018&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073179&amp;amp;eDM20200102&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;/p&gt;
',null,null,'2020-01-10 13:44:54','M',0,'SY10000053','2020-01-10 13:44:54','SY10000053','2020-01-10 13:44:54')
insert into [CM_EMAIL_TEMPLATE]([SITE_NO],[EMAIL_TMPL_YN],[EMAIL_TYPE_CODE],[EMAIL_TMPL_NAME],[EMAIL_TITLE_TEXT],[EMAIL_CONT_TEXT],[EMAIL_KEY_TEXT],[EMAIL_ID],[SEND_DTM],[SEND_PROC_GBN_TYPE],[EMAIL_CNFRM_COUNT],[RGSTER_NO],[RGST_DTM],[MODER_NO],[MOD_DTM]) values ('10000','N',null,'20200105 정기 EDM','20200110 정기 EDM','&lt;meta charset=&quot;utf-8&quot;&gt;
&lt;title&gt;&lt;/title&gt;
&lt;style type=&quot;text/css&quot;&gt;body {margin:0 auto;padding:0; width:830px;}
img {border:0;}
&lt;/style&gt;
&lt;div style=&quot;line-height:0; text-align:center;&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1730/20190910172341933.jpg&quot; usemap=&quot;#Map1&quot; /&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/mail/mailView?mailKey=AC53WKMW92&amp;amp;eDM20190911&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1743/20190910172347884.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005148&amp;amp;eDM20190911&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1666/20190910172354630.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005145&amp;amp;eDM20190911&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1935/20190910172400590.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005151&amp;amp;eDM20190911&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1574/20190910172411151.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005152&amp;amp;eDM20190911&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1914/20190910172417975.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005153&amp;amp;eDM20190911&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1259/20190910172423845.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005154&amp;amp;eDM20190911&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1888/20190910172431397.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1872/20190910172438602.jpg&quot; usemap=&quot;#Map6&quot; /&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1811/20190910172447113.jpg&quot; usemap=&quot;#Map2&quot; /&gt;&lt;br /&gt;
&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1975/20190910181027030.jpg&quot; style=&quot;border-width: 0px; border-style: solid; width: 830px; height: 600px;&quot; usemap=&quot;#Map3&quot; /&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1253/20190910172500249.jpg&quot; usemap=&quot;#Map4&quot; /&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/mail/confirm?key=AC53WKMW92&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1441/20190910172506438.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;a href=&quot;http://www.abcmart.co.kr/abc/storePickup/storePickup&quot; target=&quot;_blank&quot;&gt;&lt;img src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1021/20190910172511678.jpg&quot; /&gt;&lt;/a&gt;&lt;br /&gt;
&lt;img border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1913/20190910172517416.jpg&quot; usemap=&quot;#Map5&quot; /&gt;
&lt;div style=&quot;margin:0 auto;padding:0;background:#f9f9f9;width:758px;padding:27px 36px; text-align:left;&quot;&gt;
&lt;ul style=&quot;list-style-type:none;margin:0;color:#9c9c9c;padding:0;font-size:11px;font-family:Dotum, &#39;돋움&#39;;line-height:1.6em&quot;&gt;
	&lt;li&gt;- 메일 내 상품의 가격 및 정보는 발송일 기준이며 개봉시점에 따라 가격 및 정보는 변경될 수 있습니다.&lt;/li&gt;
	&lt;li&gt;- 본 메일은 정보통신망 이용촉진 및 정보보호 등에 관한 법률 시행규칙에 의거 2019년 09월 10일 기준으로 광고메일 수신동의 여부를&lt;br /&gt;
	&amp;nbsp;&amp;nbsp;확인한 결과 회원님께서 수신 동의를 하셨기에 발송되었습니다.&lt;/li&gt;
	&lt;li&gt;- 메일수신을 원치 않으시면, &lt;a href=&quot;http://www.abcmart.co.kr/abc/etc/refuseEmailDmForm?userDmMail=[$_EMAIL_$]&quot; style=&quot;color: rgb(0, 0, 0);&quot; target=&quot;_blank&quot;&gt;수신거부&lt;/a&gt;를 클릭해주시기 바랍니다. (If you don&amp;#39;t want to receive this mail, &lt;a href=&quot;http://www.abcmart.co.kr/abc/etc/refuseEmailDmForm?userDmMail=[$_EMAIL_$]&quot; style=&quot;color: rgb(0, 0, 0);&quot; target=&quot;_blank&quot;&gt;please click here&lt;/a&gt;)&lt;/li&gt;
	&lt;li&gt;- 본 메일은 발신전용으로 회신이 되지 않으며 자세한 문의사항은 &lt;a href=&quot;http://www.abcmart.co.kr/abc/customer/main/?eDM&quot; style=&quot;color: rgb(0, 0, 0);&quot; target=&quot;_blank&quot;&gt;고객센터&lt;/a&gt;를 이용해주시기 바랍니다.&lt;/li&gt;
&lt;/ul&gt;
&lt;/div&gt;
&lt;br /&gt;
&lt;img alt=&quot;ABC-MART 회사정보&quot; border=&quot;0&quot; src=&quot;http://image.abcmart.co.kr/nexti/images/abcmart/client/1196/20190910172522880.jpg&quot; /&gt;&lt;/div&gt;

&lt;p&gt;&lt;map id=&quot;Map1&quot; name=&quot;Map1&quot;&gt;&lt;area coords=&quot;291,6,545,102&quot; href=&quot;http://www.abcmart.co.kr/?eDM&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;623,123,829,179&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/promotion/startEvent?eventId=000214&amp;amp;eDM&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;417,122,624,177&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/user/joinUserIntro/?eDM&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;208,122,418,177&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/event/main/?eDM&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;2,122,208,177&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/theme/topList/?eDM&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area alt=&quot;모바일로 이메일 보기&quot; coords=&quot;1,31,133,91&quot; href=&quot;http://www.abcmart.co.kr/abc/mail/mailView?mailKey=AC53WKMW92&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map2&quot; name=&quot;Map2&quot;&gt;&lt;area coords=&quot;192,1,627,76&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/theme/newPrdtZone? &amp;amp;eDM20190220&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;66,142,245,437&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072737&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;329,136,503,435&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072736&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;588,142,769,432&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072735&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;65,456,261,738&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072734&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;328,454,503,742&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0071764&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;589,449,761,744&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073430&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;68,777,240,1072&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073429&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;330,774,494,1082&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073428&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;590,785,762,1080&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0071482&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;66,1105,238,1400&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0073404&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;330,1107,502,1402&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072946&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;591,1106,763,1401&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0072947&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map3&quot; name=&quot;Map3&quot;&gt;&lt;area coords=&quot;4,78,408,336&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005155&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;414,76,816,333&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005156&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;13,330,416,586&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005157&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;418,335,817,589&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005158&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map4&quot; name=&quot;Map4&quot;&gt;&lt;area coords=&quot;5,101,418,517&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005146&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;418,102,826,515&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005144&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;2,522,415,934&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005130&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;419,519,826,933&quot; href=&quot;http://www.abcmart.co.kr/abc/planDisp/detail?plndpId=005110&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map5&quot; name=&quot;Map5&quot;&gt;&lt;area coords=&quot;-2,1,275,104&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0000&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;277,4,552,104&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0001&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;555,5,825,103&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0002&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;4,107,274,204&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0003&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;278,107,555,204&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0004&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;560,106,826,202&quot; href=&quot;http://www.abcmart.co.kr/common/deviceRedirect?url=/abc/category/category1?ctgrId=0005&amp;amp;eDM20190911&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;map id=&quot;Map6&quot; name=&quot;Map6&quot;&gt;&lt;area coords=&quot;16,134,409,389&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0067329&amp;amp;eDM20190904&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;418,136,808,392&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0070279&amp;amp;eDM20190904&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;21,449,411,704&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0069753&amp;amp;eDM20190904&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;419,449,811,707&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0070301&amp;amp;eDM20190904&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;19,764,410,1020&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0071431&amp;amp;eDM20190904&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt; &lt;area coords=&quot;422,762,810,1018&quot; href=&quot;http://www.abcmart.co.kr/abc/product/detail?prdtCode=0071117&amp;amp;eDM20190904&quot; onfocus=&quot;this.blur()&quot; shape=&quot;rect&quot; target=&quot;_blank&quot; /&gt;&lt;/map&gt;&lt;/p&gt;
',null,null,'2020-01-10 17:30:00','A',0,'SY10000053','2020-01-10 14:10:25','SY10000053','2020-01-10 17:18:12')

GO

update  CM_EMAIL_TEMPLATE  set RGSTER_NO  ='SY00000001'