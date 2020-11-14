        $("#step2").addClass("here");


        var date = new Date(dateEnd - dateStart);
        var equipmentVO = new Array();
        var eqptAvailVO = new Array();
        var eqptAvailAllVO = new Array();
        var PromotionVO = new Array();
        var dateStart;
        var dateEnd;
        var days;
        
        
        function DateDiff(sDate1, sDate2) { // sDate1 和 sDate2 是 2016-06-18 格式
            dateEnd
            var iDays = parseInt(Math.abs(sDate1 - sDate2) / 1000 / 60 / 60 / 24) + 1; // 把相差的毫秒數轉換為天數
            return iDays;
        };
        
        $(document).ready(function() {
        	
        	start();
        	
        		
        	  
        });
        
       	function start(){
          	 $.ajax({
                url: urls,
                type: "POST",
                data: {
                    action: "getAllBymem_no",
                    mem_no : mem_no,
                    om_no : om_no
                },
                success: function(list) {
                	if(list=="[]"){
                		$("#MainContent_divOption").html("")
                		$("#MainContent_divOption")
                		.append(
                			$("<div/>")
                			.addClass("nodata")
                			.html("業者目前沒有提供裝備喔!")	
                			)		
                			.append(
            	        		$("<button>")
            	        		.addClass("btn btn-lg ")
            	        		.attr({type:"button", id:"return", "onclick":"javascript:location.href='"+urlch+"/carcamp/carCamp.do?action=editOrder&om_no="+reback+"'"})
            	        		.html("返回上一頁"))
                	        .append(
            	        		$("<button>")
            	        		.addClass("btn btn-danger btn-lg")
            	        		.attr({type:"button", id:"submit"})
            	        		.html("下一步 (訂購食材)"))	

                			
                	}else{
                        equipmentVO = JSON.parse(list);
                        console.log(equipmentVO)
                        dateStart = new Date(equipmentVO[0].cc_start);
                        dateEnd = new Date(equipmentVO[0].cc_end);
                        days = DateDiff(dateStart, dateEnd);
                       	for (let i = 0; i < days; i++) {
                            eqptavail(i);	
                       	}
                    }
                   	html();
                },
                beforeSend: function () {
                    $('.loading').show();
                },
                complete: function () {
                    $('.loading').hide();
                }
            });	
          	}
       	
        function html(){
        	

            $(".row_header")
        	.append(
            	$("<th/>")
            	.addClass("col_name")
            	.html("裝備選擇"))
            .append(
            	$("<th/>")
            	.addClass("col_price")
            	.html("金額"))
            for (let i = 1; i <= days; i++) {
                $(".row_header").append("<th class='col_stay'>第" + i + "日</th>");
            }

   	
            	
            $.each(equipmentVO, function(index, ce) {
                var tr = $(".tbody")
                        .append(
                            $("<tr>")
                            .attr("id", index)
                            .addClass("row_detail")
                        )
                $("#" + index)
                    .append(
                        $("<th/>")
                        .addClass("col_name")
                        .append(
                            $("<a/>")
                            .addClass("option_view")
                            .attr({ href: "#", type: "button", "data-toggle": "modal", "data-target": '#exampleModal' + index })
                            .html(ce.eqpt_name)
                        )
                    )
                $("#" + index + " .col_name")
                    .append(
                        $("<div/>")
                        .addClass("modal fade")
                        .attr({ id: "exampleModal" + index, tabindex: "-1", role: "dialog", "aria-labelledby": "exampleModalLabel", "aria-hidden": "true" })
                        .append(
                            $("<div/>")
                            .addClass("modal-dialog modal-dialog-centered")
                            .attr("role", "document")
                            .append(
                                $("<div/>")
                                .addClass("modal-content")
                                .append(
                                    $("<div/>")
                                    .addClass("modal-header")
                                    .append(
                                        $("<h5/>")
                                        .addClass("modal-title")
                                        .attr("id", "exampleModalLabel")
                                        .html(ce.eqpt_name)
                                    )
                                    .append(
                                        $("<button/>")
                                        .addClass("close")
                                        .attr({ type: "button", "data-dismiss": "modal", "aria-label": "Close" })
                                        .append(
                                            $("<span/>")
                                            .attr("aria-hidden", "true")
                                            .html("\&times;")
                                        )
                                    )
                                )
                                .append(
                                    $("<div/>")
                                    .addClass("modal-body")
                                    .append(
                                        $("<img/>")
                                        .addClass("col-12")
                                        .attr("src", urlimg + ce.eqpt_no)
                                    )
                                )
                            )
                        )
                    )
                $("#" + index)
                	.append(
                	$("<td>")
                	.addClass("col_price price")
                	.html('NT$ :'+ce.eqpt_price ));
                minprice(ce,index);


                for (let i = 0; i < days; i++) {
                    var ind = index + (i * equipmentVO.length);
                    if (eqptAvailAllVO[ind].ea_qty == 0) {
                        $("#" + index)
                        	.append($("<td>")
                        	.addClass("col_value")
                        		.append('<select disabled="disabled" class="select' + i + '" name="careqpt_qty" id="' + ce.eqpt_no + "-" + eqptAvailAllVO[ind].ea_date  + '" style="width: 40px;">'));
                        $("#" + index + " .select" + i)
                        	.append('<option selected="selected"  value="0">'+'-'+'</option>');
                    } else {
                        $("#" + index)
                        	.append(
                        	$("<td>")
                        	.addClass("col_value")
                        		.append('<select class="select' + i + '" name="careqpt_qty" id="' + ce.eqpt_no + "-" + eqptAvailAllVO[ind].ea_date  + '" style="width: 40px;">'));
                        $("#" + index + " .select" + i)
                        .append('<option selected="selected" value="0,' + eqptAvailAllVO[ind].ea_date + ',' + ce.eqpt_no + '">0</option>');
                        var selectval = eqptAvailAllVO[ind].ea_qty <= 9 ? eqptAvailAllVO[ind].ea_qty : 9;
                        for (let s = 1; s <= selectval; s++) {
                            $("#" + index + " .select" + i)
                            .append('<option value="' + s + ',' + eqptAvailAllVO[ind].ea_date + ',' + ce.eqpt_no+'">' + s + '</option>')
                        }
                    }
                }
            });
            $("#btn")
        	.append(
        		$("<button>")
        		.addClass("btn btn-lg ")
        		.attr({type:"button", id:"return", "onclick":"javascript:location.href='"+urlch+"/carcamp/carCamp.do?action=editOrder&om_no="+reback+"'"})
        		.html("返回上一頁"))
        	.append(
        		$("<button>")
        		.addClass("btn btn-danger btn-lg")
        		.attr({type:"button", id:"submit"})
        		.html("下一步 (訂購食材)"))	
        		if(om_no === ""|| carEqptVOlist !== ""){
        			oldcareqpt();
        		}else{
        			oldordereqpt();
        		}
        }
        		
        	
        function eqptavail(i) {
            var dd = dateStart;
            var ds = fullDate(dd.getFullYear(), (dd.getMonth() + 1), (dd.getDate() + i));
            $.ajax({
                url: urls,
                type: "POST",
                async: false,
                data: {

                    action: "getEqptAvailsByEqptno",
                    start: ds,
                    om_no:om_no
                },
                success: function(data) {
                    eqptAvailVO = JSON.parse(data);
                    eqptAvailVOSoup(eqptAvailVO, ds);
                    $.merge(eqptAvailAllVO, eqptAvailVO);

                }
            });
        }

        function fullDate(yr, mon, date) {
            return regexpInt(yr, 4) + "-" + regexpInt(mon, 2) + "-" + regexpInt(date, 2);
        }

        function regexpInt(num, digit) {
            var str = num.toString();
            if (str.length < digit) {
                for (let i = str.length; i < digit; i++)
                    str = "0" + str;
            }
            return str;
        }


        function eqptAvailVOSoup(eqptAvailVO, ds) {
            for (let i = 0; i < equipmentVO.length; i++) {
                var exist = eqptAvailVO.some(function(x) { return x.ea_eqptno === equipmentVO[i].eqpt_no; });
                if (!exist) {
                    eqptAvailVO.push({
                        ea_eqptno: equipmentVO[i].eqpt_no,
                        ea_qty: equipmentVO[i].eqpt_qty,
                        ea_date: ds
                    });
                }
                eqptAvailVO.sort(function(a, b) {
                    var a_eqptno = parseInt(a.ea_eqptno.replaceAll("E", ""));
                    var b_eqptno = parseInt(b.ea_eqptno.replaceAll("E", ""));
                    return a_eqptno - b_eqptno;
                });
            }
        };
        
    	function minprice(ce,index){
			$.ajax({
	            url: urls,
	            type: "POST",
				dataType:"json",
				data:{
					action:"getPromotionByeqptno",
					eqptno:ce.eqpt_no
				},
	            success: function(data) {
	            	PromotionVO=data;
	            	if(PromotionVO.length !=0){
	            		 if(ce.eqpt_no==PromotionVO[0].pe_eqptno){
	                		$("#" + index + " .col_price")
	                		.html("")
	                			.append(
	                				$("<div/>")
	                				.addClass("remove")
	                				.html('NT$: ' +ce.eqpt_price ))
	                			.append(
	                				$("<div/>")
	                				.addClass("text-danger prom")
	                				.html('NT$: '+PromotionVO[0].pe_price ))	
	                	}
	            	}
	            }
			})
    	}
    	
    	function oldcareqpt(){
    		console.log("1");
    		$.ajax({
    		url: urls,
            type: "POST",
			dataType:"json",
			data:{
				action : "getoldcareqpt",
				mem_no : mem_no,
				om_no : om_no
			},
            success: function(data) {
            	$.each(data, function(index, ce) {
            		let str =parseInt($("#"+ce.ce_eqptno+"-"+ce.ce_expget + ' option:last').html());
            		if(ce.ce_qty <= str){
            			$("#"+ce.ce_eqptno+"-"+ce.ce_expget).get(0).selectedIndex=ce.ce_qty;
            		}else{
                		$("#"+ce.ce_eqptno+"-"+ce.ce_expget).parent().css("background-color", "#e0122d");
                		$("#"+ce.ce_eqptno+"-"+ce.ce_expget).get(0).selectedIndex=0;
            		}
           	 	})
            }
    	})
    	}
    	
       	function oldordereqpt(){
       		console.log("2");
    		$.ajax({
    		url: urls,
            type: "POST",
			dataType:"json",
			data:{
				action:"getoldcareqpt",
				om_no:om_no
			},
            success: function(data) {
            	$.each(data, function(index, oe) {
              		let str =parseInt($("#"+oe.oe_eqptno+"-"+oe.oe_expget + ' option:last').html());
            		if(oe.oe_qty <= str){
            			$("#"+oe.oe_eqptno+"-"+oe.oe_expget).get(0).selectedIndex=oe.oe_qty;
            		}else{
                		$("#"+oe.oe_eqptno+"-"+oe.oe_expget).parent().css("background-color", "#e0122d");
                		$("#"+oe.oe_eqptno+"-"+oe.oe_expget).get(0).selectedIndex=0;
            		}
            		
           	 	})
            }
    	})
    	}
       	

       
       	
    	
   /*=================================================================================================*/     
        
    	$.fn.serializeObject = function() {
    		var o = [];
    		var a = this.serializeArray();
    	    $.each(a, function(i,val) {
    	        if(this.value[0]!="0"){
    	        o.push(val.value);
    	        }
    	    });
    	    return o;
    	};
    	
		$(document).on("click", "#submit",function(){
    		var jsonArray =[];
    	    var formData = $("#formdata").serializeObject();
    	    console.log(formData)
    	    o = [];
    	    for(var i=0; i<formData.length;i++){
    	      var fd = {};
    	      fd['ce_eqptno'] = formData[i].split(",")[2];
    	      fd['ce_qty'] = formData[i].split(",")[0];
    	      fd['ce_expget'] = formData[i].split(",")[1];
    		  jsonArray.push(fd);
    	    }
         	var os=JSON.stringify(jsonArray);
         	fromAjax(os);
    	    });
    	
		function fromAjax(formdata){
			 $.ajax({
	                url: urls,
	                type: "POST",
//					dataType:"json",
					data:{
						action:"submitdata",
						jsonArray:formdata,
						mem_no:mem_no,
						om_no:om_no,
					},
	                success: function(data) {
	                	$("#submit").attr("disabled","disabled");
	                	if(data==="save"){
	                		if(om_no==""){
	                			window.location.href = urlch + "/front-end/carfood/reserveFood.jsp"
	                		}else{
	                			window.location.href = urlch + "/front-end/carfood/reserveFood.jsp?om_no="+om_no
	                		}
	                	}else{
	                		$("#submit").removeAttr("disabled");
	                		scrollTop();
	                		data=JSON.parse(data);
	                		$.each(data, function(index, ea) {
	                		if(ea.ea_qty==0){
	                			
	                			$("#"+ea.ea_eqptno+"-"+ea.ea_date)
	                				.html("")
	                				.attr("disabled","disabled")
	                				.append(
	                					$("<option/>")
	                					.attr({"selected":"selected", "value":"0"})
	                					.html("-"))
	                					
	                		$("#"+ea.ea_eqptno+"-"+ea.ea_date)
	                			.parent()
	                			.css("background-color", "#e0122d")
	                			
	                		}else{
	                       		$("#"+ea.ea_eqptno+"-"+ea.ea_date)
	            				.html("")
		                		for(var i=0;i<ea.ea_qty;i++){
		                			$("#"+ea.ea_eqptno+"-"+ea.ea_date)
			                				.append(
			                					$("<option/>")
			                					.attr("value", i)
			                					.html(i))
			                		$("#"+ea.ea_eqptno+"-"+ea.ea_date).parent().css("background-color", "#e0122d");
			                		$("#"+ea.ea_eqptno+"-"+ea.ea_date).get(0).selectedIndex=0;
		                		}	
	                		}
	         
	                	})
	                	}
	                }	                	
	   	        });
		}
		

	    function scrollTop() {
	        window.document.body.scrollTop = 0;
	        window.document.documentElement.scrollTop = 0;
	    }
		
