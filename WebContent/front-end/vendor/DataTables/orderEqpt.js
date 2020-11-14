$(document).ready( function () {
		
		let table = $('#example').DataTable({
		"pageLength": 5,
        "lengthMenu": [ 5, 10, 25, 30],	
	     "sAjaxSource": urls,//這個是請求的地址
	     "fnServerData": retrieveData,
	     "columns": [
	    	 {"data": "number",
	          className:'details-control',
              orderable:false,
              data:null,
              defaultContent: ''},
	         {"data": "mem_name",
	       	  className:'details-control',	  
	         },
	         {"data": "oc_start",
	          className:'details-control',	 
	         },
	         {"data": "oc_end",
	          className:'details-control',	 
	         }
	     ],
	     "columnDefs": [ {
	            "searchable": false,
	            "orderable": false,
	            "targets": 0
	        } ],
	     "order": [[1 , 'asc' ]],
		 language: {
		         lengthMenu : "每頁顯示 _MENU_紀錄", 
		         zeroRecords : "今日沒有裝備出租", 
		         info : "第_PAGE_頁/共 _PAGES_頁", 
		         infoEmpty : "沒有符合的資料", 
		         search : "查詢", 
		         infoFiltered : "(從 _MAX_條資料中過濾)", 
		         paginate : { "first" : "首頁", "last" : "末頁", "next" : "下一頁", "previous" : "上一頁"}
		     },
	    });
		
		$('#example tbody').on('click', 'td.details-control', function () {
// 		    let tr = $(this).closest('tr');
		    let row = table.row( this );
		 
		    if ( row.child.isShown() ) {
		        row.child.hide();
// 		        tr.removeClass('shown');
		    }
		    else {
		        row.child( format(row.data())).show();
// 		        tr.addClass('shown');
		    }
		} ); 
	                
		table.on( 'draw.dt', function () {
			    let PageInfo = $('#example').DataTable().page.info();
			    table.column(0, { page: 'current' }).nodes().each( function (cell, i) {
			            cell.innerHTML = i + 1 + PageInfo.start;
			        } );
		 } );
		
		$('#example tbody').on("click", ".get",function(){
		    let div = $(this).parents(".detailordar");
	        
			let om_no = $(this).parent().find("input[name=om_no]").val();
			let oe_no = $(this).parent().find("input[name=oe_no]").val();
			$.ajax( {
		        url: 'urls',
		        dataType: 'json',
		        data: {
		        	action:"updataGetTime",
		        	oe_no : oe_no,
		        	om_no : om_no
		        },
		       	success: function ( json ) {
		       		detailorder(div, json);
		       	}
			})
		}) 
		
		$('#example tbody').on("click", ".back",function(){
		    let div = $(this).parents(".detailordar");
	        
			let om_no = $(this).parent().find("input[name=om_no]").val();
			let oe_no = $(this).parent().find("input[name=oe_no]").val();
			let oe_reqty = $(this).parent().find("input[name=oe_reqty]").val();
			$.ajax( {
		        url: urls,
		        dataType: 'json',
		        data: {
		        	action:"updataBackTime",
		        	om_no : om_no,
		        	oe_no : oe_no,
		        	oe_qty : oe_qty,
		        	oe_reqty : oe_reqty
		        },
		       	success: function ( json ) {
		       		detailorder(div, json);
		       	}
			})
		}) 
		
	});   

	
	function format ( rowData ) {
	    let div = $('<div/>')
	        .addClass( 'loading' )
	        .text( 'Loading...' );
	 
	    $.ajax( {
	        url: urls,
	        dataType: 'json',
	        data: {
	        	action:"getOmEqptByomno",
	            om_no: rowData.om_no
	        },
	        success: function ( json ) {
	        	detailorder(div, json);
			}
	    } );
	 
	    return div;
	}

	function retrieveData(url, aoData, fnCallback){
		$.ajax({
			url: url,
            type: "POST",
			dataType:"json",
			data:{
				action:"getAllByToday",
				om_vdno:vd_no
			},
			success: function(data) {
				fnCallback(data);
			}
		})
	}
	
	function detailorder(div, json){
		 
   		if(json.length>0){
			div
			.attr("class", 'detailordar')
			.html("")
			div.append(
				$("<thead/>")
					.append(
						$("<tr/>")
							.append(
								$("<th/>")
								.html("裝備名稱"))
							.append(
								$("<th/>")
								.html("租借數量"))
							.append(
								$("<th/>")
								.html("租借日期"))
							.append(
								$("<th/>")
								.html("歸還日期"))
							.append(
								$("<th/>")
								.html("歸還數量"))
							.append(
								$("<th/>")
								.html("租借狀態"))
							.append(
								$("<th/>")
								.html(""))
							.append(
								$("<th/>")
								.html(""))
							))
       		$.each(json,function(index, oe){
       			if(oe.oe_stat==1){
       			let tr = $("<tr/>")
       			
       			div
       			.append(tr);
       				 
       			
       			tr.attr("id",oe.om_no + "-" + index);
       				tr.append(
						$("<input/>")
						.attr({"type":"hidden", "name":"oe_no" , "value":oe.oe_no}))
					tr.append(
						$("<input/>")
						.attr({"type":"hidden", "name":"om_no" , "value":oe.om_no}))
   				 	tr.append(
   						$("<th/>")	
   						.html(oe.eqpt_name))
   					tr.append(
   						$("<th/>")	
   						.html(oe.oe_qty))
					tr.append(
						$("<input/>")
						.attr({"type":"hidden", "name":"oe_qty" , "value":oe.oe_qty}))
       				tr.append(
   						$("<th/>")	
   						.html(oe.oe_expget)
   						)
   					tr.append(
   						$("<th/>")	
   						.html(oe.oe_expback)
   						);
       				if(oe.oe_get == undefined || oe.oe_back != undefined){
   						tr.append(
   							$("<th/>")	
   							.html(oe.oe_qty)
       					)
   					}else{
       					tr.append(
   							$("<input/>")	
   							.attr({"type":"text","name":"oe_reqty","value": oe.oe_qty})
							)
   					}
   					if(oe.oe_get==undefined){
   						tr.append(
   	       					$("<th/>")	
   	       					.html("未領取")
	       					)
   					}else if(oe.oe_back!=undefined){
   						tr.css("text")
   						tr.append(
	       						$("<th/>")	
   	       					.html("已歸還")
	       					)
   					}else{ 
   						tr.append(
	       						$("<th/>")	
   	       					.html("已領取")
	       					)
       					}
   						if(oe.oe_back != undefined ||oe.oe_get != undefined){
	       					tr.append(
								$("<button/>")
	    						.addClass("btn get")
	    						.attr("disabled", "disabled")
	    						.css("background","#BBBBBB")
								.html("領取裝備")
	       					)
						}else{
							tr.append(
								$("<button/>")
	    						.addClass("btn get")
								.html("領取裝備")
		       				)
						}
   						
						if(oe.oe_get == undefined || oe.oe_back != undefined){
	       					tr.append(
								$("<button/>")
								.addClass("btn back")
								.attr("disabled", "disabled")
								.css("background","#BBBBBB")
								.html("歸還裝備")
       						)	
						}else{
	       					tr.append(
								$("<button/>")
								.addClass("btn back")
   								.html("歸還裝備")
	       					)	
						}
       			}
       			
       		})
			}else{
   				div
				.html("沒有訂單喔")
   			}
	}