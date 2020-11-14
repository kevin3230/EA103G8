<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.cgintro.model.*"%>
<%@ page import="com.promotion.model.*"%>
<%@ page import="com.camp.model.*"%>
<%@ page import="com.promocamp.model.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="utilities.CommonMethods"%>

<%
	String vd_no = request.getParameter("vd_no");

	// 取得上線的露營區介紹
	CGIntroService cgIntroSvc = new CGIntroService();
	CGIntroVO cgIntroVO = cgIntroSvc.getCGIntroByVdno(vd_no);
	pageContext.setAttribute("cgIntroVO", cgIntroVO);

	// 取得有效的促銷專案
	PromotionService promoSvc = new PromotionService();
	Map<String, PromotionVO> promoVOMap = new HashMap<>();
	promoSvc.getAlivePromosByVdno(vd_no, promoVOMap);
	pageContext.setAttribute("promoVOMap", promoVOMap);
%>
<jsp:useBean id="vendorSvc" scope="page" class="com.vendor.model.VendorService"/>
<jsp:useBean id="cgiPicSvc" scope="page" class="com.cgintro.model.CGIPicService"/>
<jsp:useBean id="campSvc" scope="page" class="com.camp.model.CampService"/>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>${vendorSvc.getOneVendor(param.vd_no).vd_cgname}</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/utility/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/header.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/footer.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/cgintro/css/campGroundIntro.css">
</head>
<body>
	
	<!-- Header開始 -->
	<%@ include file="/front-end/index/header.jsp" %> 
	<!-- Header結束 -->

	<div class="container">
		<div class="row justify-content-center my-3">
			<div class="col-8">
				<div id="mainPic" class="carousel slide" data-ride="carousel">
					<div class="carousel-inner">

						<c:if test="${empty cgiPicSvc.getCGIPicsByCgino(cgIntroVO.cgi_no)}">
							<div class="carousel-item active">
								<img class="d-block" src="<%=request.getContextPath()%>/front-end/cgintro/images/cgiPic_default.jpg">
							</div>
						</c:if>

						<c:forEach var="cgiPicVO" items="${cgiPicSvc.getCGIPicsByCgino(cgIntroVO.cgi_no)}" varStatus="loc">
							<div class="carousel-item <c:if test="${loc.first}">active</c:if>">
								<img class="d-block" src="<%=request.getContextPath()%>/cgintro/cgintro.do?action=getOneCGIPPic&cgip_no=${cgiPicVO.cgip_no}">
							</div>
						</c:forEach>

					</div>
				</div>
				<div id="thumbnail">
					<ul class="d-flex">
					</ul>
				</div>
			</div>
			<div id="simpleInfo" class="col-4 d-flex flex-column">
				<div class="row justify-content-center flex-fill">
					<div>
						<h1 class="pt-3">${vendorSvc.getOneVendor(param.vd_no).vd_cgname}</h1>
						<table class="text-dark">
							<tbody>
								<tr>
									<th class="p-2"><i class="fas fa-map"></i></th>
									<td class="text-left py-2"><h6>${vendorSvc.getOneVendor(param.vd_no).vd_cgaddr}</h6></td>
								</tr>
								<tr>
									<th class="p-2"><i class="fas fa-mountain"></i></th>
									<td class="text-left py-2"><h6>海拔1000公尺</h6></td>
								</tr>
								<tr>
									<th class="p-2"><i class="fas fa-calendar-check"></i></th>
									<td class="text-left py-2"><h6>
										開放訂位日期：<span id="lastOrderLong"></span>日內
									</h6></td>
								</tr>
								<tr>
									<th class="p-2"><i class="fas fa-calendar-times"></i></th>
									<td class="text-left py-2"><h6>
										最後可訂位日：<span id="lastOrderDay"></span>
									</h6></td>
								</tr>
								<tr>
									<th class="p-2"><i class="fas fa-times"></i></th>
									<td class="text-left py-2"><h6>沒有公休日</h6></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="row justify-content-center">
					<button type="button" id="talk" class="btn text-light userBtn" value="${param.vd_no}">詢問業者</button>
					<button type="button" id="reserve" class="btn text-light bg-danger">立即預約</button>
				</div>
			</div>
		</div>

		<div id="moreInfo" class="row justify-content-center my-4">
			<div class="col-3">
				<button type="button" class="btn text-light" data-toggle="modal" data-target="#cgIntro">露營區介紹</button>
			</div>
			<div class="col-3">
				<button type="button" class="btn text-light" data-toggle="modal" data-target="#cgFacility">露營區設施</button>
			</div>
			<div class="col-3">
				<button type="button" class="btn text-light" data-toggle="modal" data-target="#cgNotice">露營區須知</button>
			</div>
		</div>

		<div class="row">
			<div class="col-12">
					<%
						int i = 0;
						pageContext.setAttribute("i", i);
					%>
					<c:forEach var="campVO" items="${campSvc.getCampsByVdno(param.vd_no)}" varStatus="loc">
						<c:if test="${campVO.camp_stat > 0}">
							<%
								CampVO campVO = (CampVO) pageContext.getAttribute("campVO");
								PromoCampService promoCampSvc = new PromoCampService();
								List<PromoCampVO> promoCampVOList = promoCampSvc.getActiveByPc_campno(campVO.getCamp_no());
								pageContext.setAttribute("promoCampVOList", promoCampVOList);
							%>
							<c:if test="${i % 3 == 0}"><div class="card-group"></c:if>
							<div class="card">
								<div class="card-img-top img-container">
									<img src="<%=request.getContextPath()%>/cgintro/cgintro.do?action=getOneCampPic&camp_no=${campVO.camp_no}">
								</div>
								<div class="card-body">
									<span class="card-title d-flex justify-content-between align-items-center fs20">
										<span>${campVO.camp_name}</span>
										<span class="d-flex align-items-center">
											每帳 NT$
											<div class="d-inline-block text-center px-2 price">
												<c:if test="${not empty promoCampVOList}">
													<div class="remove">${campVO.camp_price}</div>
													<div class="text-danger"><%=CommonMethods.minPrice(promoCampVOList)%></div>
												</c:if>
												<c:if test="${empty promoCampVOList}">
													<div>${campVO.camp_price}</div>
												</c:if>
											</div>
											元
										</span>
									</span>
									<ul class="card-text">
										<c:forEach var="promoCampVO" items="${promoCampVOList}">
											<li>${promoVOMap.get(promoCampVO.pc_prono).pro_name}</li>
										</c:forEach>
									</ul>
								</div>
							</div>
							<c:if test="${i % 3 == 2}"></div><br></c:if>
							<%
								i++;
								pageContext.setAttribute("i", i);
							%>
						</c:if>
					</c:forEach>

					<c:if test="${i % 3 != 0}">
						<c:forEach var="j" begin="1" end="${(3 - i % 3) % 3}">
							<div class="card invisible">
								<div class="card-img-top img-container">
									<img src="">
								</div>
								<div class="card-body">
									<span class="card-title d-flex justify-content-between align-items-center fs20">
										<span></span>
										<span class="d-flex align-items-center">
											每帳 NT$
											<div class="d-inline-block text-center px-2 price">
												<div class=""></div>
												<div class="text-danger d-none"></div>
											</div>
											元
										</span>
									</span>
									<ul class="card-text">
										<li></li>
									</ul>
								</div>
							</div>
						</c:forEach>
						</div>
						<br>
					</c:if>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="cgIntro" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLongTitle">露營區介紹</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					${cgIntroVO.cgi_content}
					<c:if test="${empty cgIntroVO.cgi_content}">
						<b>【交通指引】</b><br>
						◇路線一<br>
						北二高竹林交流道下→往竹東方向→五峰（122縣道）→122縣道→於29.5K處往五指山方向→走五指山登山口右側往(竹37-1)南庒之產業道路→往(竹67）五峰鄉方向進入桃山村白蘭部落後沿路上均有標示指。<br>
						◇路線二<br>
						北二高竹林交流道下→往竹東方向→五峰（122縣道）→122縣道→於終點50.4K右邊往白蘭部落方向→竹67線進入白蘭部落沿路上均有標示指引。<br>
						<br>
						<b>【營業時間】</b><br>
						進場時間：當日中午12：00以後/退場時間：次日中午12：00以前<br>
						★ 預訂(星期五)二天一夜露營，請於星期六上午11:00前離場，以提供星期六訂位者使用，造成不便敬請見諒。<br>
						★ 連續假日營地紮營時間為中午12點至隔日中午12點，以供下位紮營者使用。<br>
						★ 週五(或前一晚) 提前進場 (限隔日續住者) 18:00~22:00 可入營，酌收400元(現場收費)，18:00前到場，以整日收費計算，請於23:00前搭營完成，避免打擾已就寢露友。請於露營日2天前電話聯絡營主 0911-107640 確認是否有營位。<br>
						★★連續假日期間、星期六晚上不提供夜衝服務★★
					</c:if>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">關閉</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="cgFacility" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLongTitle">露營區設施</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<ol>
						<li>寵物同行</li>
						<li>吹風機</li>
						<li>雨棚</li>
						<li>果菜採收</li>
					 	<li>提供電源</li>
					 	<li>有冰箱</li>
					 	<li>沙坑</li>
					 	<li>戲水池</li>
					 	<li>遊樂器材</li>
					</ul>
					 
					 
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">關閉</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="cgNotice" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLongTitle">露營區須知</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<b>【營地收費】</b><br>
					收費項目：包含營地停車位、炊事帳位置、清潔費、水電、衛浴及服務費。<br>
					收費標準：每一個露營單位【以4人/ 1車 / 1帳篷 / 1炊事帳】為限。<br>
					（若需增加露營人數，每人加收 100 元，不分小孩及大人。 訪客，每位訪客酌收 100 元場地清潔費。）<br>
					★ 營帳為神殿、怪獸帳、變形金剛、5 X 8大天幕者請訂2個營位。<br>
					★ 睡車上者視同收一帳4人1000元/天計費。<br>
					<br>
					<b>【露營日當天訂位】</b><br>
					線上查詢當天是否有空位，如有空位請電話通知營地管理員 0911-107640當天進場露營 (現場收費)。<br>
					如遇颱風、地震等天災、交通因中斷，經政府或旅客所在地政府發佈停止上班上課，皆可延期或100%退費。(如選擇退費訂位者需負擔退款手續費每帳50元/天，退款手續費將從退款金額中扣除)。<br>
					<br>
					<b>【露營約定】</b><br>
					★營區內孩童玩耍皆需家長自行全程陪同。<br>
					★營地看植栽維護不易，請珍惜花草樹木。<br>
					★為維護園區安全，進出營區請減速慢行。<br>
					★為響應環保及不浪費資源，營區內將不提供個人盥洗用品等備品。<br>
					★晚上10:00~早上08:00為管制時段，請勿大聲喧嘩，以免影響其他露友安寧。<br>
					★露營區內嚴禁營火﹑點火把﹑放爆竹，烤肉請使用離地30公分以上高腳烤肉架。<br>
					★垃圾請放入垃圾袋內並確實分類，離開前請放置於回收場垃圾桶。<br>
					★化妝室內之馬桶及小便池請勿丟入雜物，以確保暢通。<br>
					★營區內的電源插座僅提供小電補光用，禁止使用高功率電器產品，如電磁爐、暖爐、電鍋、烤箱、冷氣等，如未依規定而使線路燒毀，請照價賠償。<br>
					★露營區內嚴禁營火﹑點火把﹑放爆竹。<br>
					★營地內嚴禁使用卡拉OK及施放任何型式種類的鞭炮煙火。<br>
					★營區屬開放空間，個人安全/用品/財務請自行負責。<br>
					★如攜帶寵(動)物入營，請將寵(動)物繫上繩圈或放進籠裡，勿讓寵(動)物在營地內自由活動，避免叫聲擾人，並請自行清理排泄物。請勿攜帶有列管、危險性、攻擊性、不受控制的寵(動)物，為維護露友安全，營主保留現場拒絕進入營區之權利。如有傷害他人或損壞他人裝備，請自行負責，與本營區無關。<br>
					★營區內禁止賭博、吸食毒品及聚眾滋事等一切違法行為，經管理員勸導仍未改善者，將請您主動離開，露營費用沒收不予退還。<br>
					★露營野炊，請特別留意食物衛生與保存，若食用自備餐飲而身體不適，請自行負責。<br>
					★戶外蚊蟲多，請做好防蚊準備，並穿著長袖衣物及長褲，避免蚊蟲叮咬。<br>
					★露宿大自然，請留意野生動物，夜間請避開黑暗處，也不要餵食。<br>
					★如您是下雨天不露營者，建議不要提前預訂營位，可於露營日當天確定天氣狀況，適合您露營再電話聯繫營區管理員訂位，以免造成營區與露營者之間的困擾。<br>
					★以上規範為維護露營者之權益，請您配合，違反屢勸不聽者取消露營資格。<br>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">關閉</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Footer開始 -->	
<%-- 	<%@ include file="/front-end/index/footer.jsp" %> --%>
	<!-- Footer結束 -->
	
	<%@ include file="/front-end/message/chatroom.jsp"%>

	<script src="<%=request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/cgintro/js/campGroundIntro.js"></script>
<%-- 	<%@ include file="/front-end/index/Notice.jsp"%> --%>
	<script>
		var vd_no = "${param.vd_no}";
		var contextPath = "<%=request.getContextPath()%>";
	</script>
</body>
</html>