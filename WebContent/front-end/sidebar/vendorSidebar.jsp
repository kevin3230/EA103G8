<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

  <div class="d-flex flex-column col-2 text-center p-0" id="vdSidebar" style="background-color:rgba(180,180,180,0.25);">

    <h3 class="my-3">業者專區</h3>

    <div class="card">
       <div class="card-header aButton">
        <a class="btn btn-link" href="<%=request.getContextPath()%>/vendor/VendorServlet?action=vdGetInfo">業者資料</a>
      </div>
    </div>
    <div class="card">
       <div class="card-header aButton">
        <a class="btn btn-link" href="<%=request.getContextPath()%>/front-end/membersorder/vendorsOrder.jsp">訂單管理</a>
      </div>
    </div>
    <div class="card">
       <div class="card-header aButton">
        <a class="btn btn-link" href="<%=request.getContextPath()%>/cgintro/cgintro.do?action=goToCGIntro&vd_no=${vendorVO.vd_no}">營區資訊</a>
      </div>
    </div>

    <div class="accordion" id="accordionSidebar">
      <div class="card">
        <div class="card-header card-header-btn" id="headingOne">
          <h2 class="mb-0">
            <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
              	營位管理
            </button>
          </h2>
        </div>
        <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordionSidebar">
          <div class="card-body card-body-color">
            <jsp:useBean id="campSvc_nav" scope="page" class="com.camp.model.CampService" />
            <form method="post" action="<%=request.getContextPath()%>/camp/camp.do">
              <div class="input-group">
                <select class="custom-select" id="inputGroupSelect01" aria-label="Example select with button addon" name="camp_no">
                  <c:forEach var="campVO" items="${campSvc_nav.getCampsByVdnoWithoutDeleted(vd_no)}">
                    <option value="${campVO.camp_no}">${campVO.camp_name}
                  </c:forEach>
                </select>
                <div class="input-group-append">
                  <input type="hidden" name="action" value="getOne_For_Display">
                  <input type="submit" class="btn btn-outline-dark" value="查詢" ${campSvc_nav.getCampsByVdnoWithoutDeleted(vd_no).isEmpty() ? "disabled" : ""} >
                </div>
              </div>
            </form>
            <div class="row m-1">
              <a class="btn btn-outline-dark btn-block aButton2" href="<%=request.getContextPath()%>/front-end/camp/listAllCamp.jsp" role="button">營位一覽</a>
            </div>
            <div class="row m-1">
              <a class="btn btn-outline-dark btn-block aButton2" href="<%=request.getContextPath()%>/front-end/camp/addCamp.jsp" role="button">新增營位</a>
            </div>      
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-header card-header-btn" id="headingTwo">
          <h2 class="mb-0">
            <button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
            	  裝備管理
            </button>
          </h2>
        </div>
        <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
          <div class="card-body card-body-color">
            <jsp:useBean id="eqptSvc_nav" scope="page" class="com.equipment.model.EquipmentService" />
            <form method="post" action="<%=request.getContextPath()%>/equipment/equipment.do" >
              <div class="input-group">
                <select class="custom-select" id="inputGroupSelect02" aria-label="Example select with button addon" name="eqpt_no">
                  <c:forEach var="equipmentVO" items="${eqptSvc_nav.getEquipmentsByVdnoWithoutDeleted(vd_no)}" > 
                    <option value="${equipmentVO.eqpt_no}">${equipmentVO.eqpt_name}
                  </c:forEach>
                </select>
                <div class="input-group-append">
                  <input type="hidden" name="action" value="getOneEquipment_For_Display">
                  <input type="submit" class="btn btn-outline-dark" value="查詢" ${eqptSvc_nav.getEquipmentsByVdnoWithoutDeleted(vd_no).isEmpty() ? "disabled" : ""}>
                </div>
              </div>
            </form>
            <div class="row m-1">
              <a class="btn btn-outline-dark btn-block aButton2" href="<%=request.getContextPath()%>/front-end/equipment/listAllEquipment.jsp" role="button">裝備一覽</a>
            </div>
            <div class="row m-1">
              <a class="btn btn-outline-dark btn-block aButton2" href="<%=request.getContextPath()%>/front-end/equipment/addEquipment.jsp" role="button">新增裝備</a>
            </div>
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-header card-header-btn" id="headingThree">
          <h2 class="mb-0">
            <button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
              	食材管理
            </button>
          </h2>
        </div>
        <div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#accordionSidebar">
          <div class="card-body card-body-color">
            <jsp:useBean id="foodSvc_nav" scope="page" class="com.food.model.FoodService" />
            <form method="post" action="<%=request.getContextPath()%>/food/food.do" >
              <div class="input-group">
                <select class="custom-select" id="inputGroupSelect03" aria-label="Example select with button addon" name="food_no">
                  <c:forEach var="foodVO" items="${foodSvc_nav.getFoodsByVdnoWithoutDeleted(vd_no)}" > 
                    <option value="${foodVO.food_no}">${foodVO.food_name}
                  </c:forEach>
                </select>
                <div class="input-group-append">
                  <input type="hidden" name="action" value="getOneFood_For_Display">
                  <input type="submit" class="btn btn-outline-dark" value="查詢" ${foodSvc_nav.getFoodsByVdnoWithoutDeleted(vd_no).isEmpty() ? "disabled" : ""}>
                </div>
              </div>
            </form>
            <div class="row m-1">
              <a class="btn btn-outline-dark btn-block aButton2" href="<%=request.getContextPath()%>/front-end/food/listAllFood.jsp" role="button">食材一覽</a>
            </div>
            <div class="row m-1">
              <a class="btn btn-outline-dark btn-block aButton2" href="<%=request.getContextPath()%>/front-end/food/addFood.jsp" role="button">新增食材</a>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="card">
       <div class="card-header aButton">
        <a class="btn btn-link" href="<%=request.getContextPath()%>/front-end/vendor/VendorLeaseEqpt.jsp">裝備出租</a>
      </div>
    </div>
    <div class="card">
       <div class="card-header aButton">
        <a class="btn btn-link" href="<%=request.getContextPath()%>/front-end/vendorfoodstatic/foodStatic.jsp">食材統計</a>
      </div>
    </div>
    <div class="card">
       <div class="card-header aButton">
        <a class="btn btn-link" href="<%=request.getContextPath()%>/front-end/promotion/promoHome.jsp">促銷專案</a>
      </div>
    </div>

</div>