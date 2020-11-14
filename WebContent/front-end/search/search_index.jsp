<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="container-fluid" id="frame">
	<div class="row justify-content-center h-100">
		<div class="col-10 d-flex flex-column h-100">
			<div id="areaSelector">
				<h2 class="font-weight-bold">地區</h2>
				<div class="mb-3">
					<label><input type="checkbox" name="areaBlock">&nbsp;&nbsp;北部</label>
					<label><input type="checkbox" name="areaBlock">&nbsp;&nbsp;中部</label>
					<label><input type="checkbox" name="areaBlock">&nbsp;&nbsp;南部</label>
					<label><input type="checkbox" name="areaBlock">&nbsp;&nbsp;東部</label>
				</div>
				<label><input type="checkbox" name="campArea">&nbsp;&nbsp;台北</label>
				<label><input type="checkbox" name="campArea">&nbsp;&nbsp;新北</label>
				<label><input type="checkbox" name="campArea">&nbsp;&nbsp;桃園</label>
				<label><input type="checkbox" name="campArea">&nbsp;&nbsp;新竹</label>
				<label><input type="checkbox" name="campArea">&nbsp;&nbsp;苗栗</label>
				<label><input type="checkbox" name="campArea">&nbsp;&nbsp;台中</label>
				<label><input type="checkbox" name="campArea">&nbsp;&nbsp;南投</label>
				<label><input type="checkbox" name="campArea">&nbsp;&nbsp;雲林</label>
				<label><input type="checkbox" name="campArea">&nbsp;&nbsp;嘉義</label>
				<label><input type="checkbox" name="campArea">&nbsp;&nbsp;台南</label>
				<label><input type="checkbox" name="campArea">&nbsp;&nbsp;高雄</label>
				<label><input type="checkbox" name="campArea">&nbsp;&nbsp;屏東</label>
				<label><input type="checkbox" name="campArea">&nbsp;&nbsp;宜蘭</label>
				<label><input type="checkbox" name="campArea">&nbsp;&nbsp;花蓮</label>
				<label><input type="checkbox" name="campArea">&nbsp;&nbsp;台東</label>
				<hr>
			</div>
			<div id="typeSelector">
				<h2 class="font-weight-bold">類型</h2>
				<label><input type="checkbox" name="campType">&nbsp;&nbsp;室外</label>
				<label><input type="checkbox" name="campType">&nbsp;&nbsp;雨棚</label>
				<label><input type="checkbox" name="campType">&nbsp;&nbsp;小木屋</label>
				<label><input type="checkbox" name="campType">&nbsp;&nbsp;露營車</label>
				<label><input type="checkbox" name="campType">&nbsp;&nbsp;室內</label>
				<hr>
			</div>
			<div class="flex-fill">
				<h2 class="font-weight-bold">促銷活動</h2>
				<label><input type="radio" name="campPromo">&nbsp;&nbsp;有</label>
				<label><input type="radio" name="campPromo">&nbsp;&nbsp;無</label>
			</div>
			<div>
				<button type="button" class="btn btn-secondary w-100" id="goToSearch">查詢</button>
			</div>
		</div>
	</div>
</div>