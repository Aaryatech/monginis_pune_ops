<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">

<title>Monginis</title>


<link
	href="${pageContext.request.contextPath}/resources/css/monginis.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet" type="text/css"/>	
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/images/feviconicon.png"
	type="image/x-icon" />
	
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>	
	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.min.js"></script>

<!--rightNav-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/menuzord.js"></script>
	
<script type="text/javascript">
jQuery(document).ready(function(){
	jQuery("#menuzord").menuzord({
		align:"left"
	});
});
</script>
<!--rightNav-->
<!--datepicker-->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script>
  $( function() {
    $( "#todatepicker" ).datepicker({ dateFormat: 'dd-mm-yy' });
  } );
  $( function() {
    $( "#fromdatepicker" ).datepicker({ dateFormat: 'dd-mm-yy' });
  } );
 
  </script>
<!--datepicker--> 


</head>
<body>

<c:url var="getItemwiselReport" value="/getItemwiselReport" />
<c:url var="getMenuwiselReport" value="/getMenuwiselReport" />
	
	<div class="sidebarOuter"></div>
	
	<div class="wrapper">

		<!--topHeader-->

		<jsp:include page="/WEB-INF/views/include/header.jsp">
			<jsp:param name="frDetails" value="${frDetails}" />

		</jsp:include>

		<!--topHeader-->

		<!--rightContainer-->
		<div class="fullGrid center">
			<!--fullGrid-->
			<div class="wrapperIn2">

				<!--leftNav-->

				<jsp:include page="/WEB-INF/views/include/left.jsp">
					<jsp:param name="myMenu" value="${menuList}" />

				</jsp:include>


				<!--leftNav-->
				<!--rightSidebar-->
				
				<!-- Place Actual content of page inside this div -->
				<div class="sidebarright">
				

<div class="row">
	    <div class="col-md-12"><h2 class="pageTitle">View Item wise Report</h2></div>
	</div>
	
	<div class="colOuter">
		<div align="center" >
		<div class="col1"><div class="col1title"><b>From&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
		<input id="fromdatepicker"  placeholder="Delivery Date"  name="from_Date" type="text" size="35" >
		</div></div>
		<div class="col2"><div class="col1title"><b>TO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
		<input id="todatepicker"  placeholder="Delivery Date"  name="to_Date" type="text" size="35" >
		</div></div>
					<input type="hidden" name="frId" id="frId" value="${frId}">
		
	</div>
 
	
 	<div align="center">
		     <button class="btn search_btn" onclick="searchSellBill()" >HTML View </button>
		    <button class="btn search_btn" onclick="showChart()" >Graph</button>
<%-- 		    	 <a href='${pageContext.request.contextPath}/pdf?reportURL=showSellItemwiseReportpPdf' class="btn search_btn" id="btn_pdf" style="display: none" >PDF</a>
 --%>		    		<button class="btn btn-primary" value="PDF" id="PDFButton" onclick="genPdf()">PDF</button>
 
 	</div>
	</div>
		 
	
	<div class="row" id="table" style="display: none">
		<div class="col-md-12">
		<!--table-->
			<div class="table-responsive">
				<div class="shInnerwidth">
					
								<table width="100%" border="0" cellspacing="0"
														cellpadding="0" id="table_grid" class="table table-bordered">
								<thead >
									<tr class="bgpink">
									<th align="right" style="width:100px">Sr no.</th>
									<!-- <th align="center">Bill No</th> -->
									<th align="center">Item Name</th>
									<th align="center">Item Id</th>
									<th align="center">Group Name</th>
								 	<th align="center">Quantity</th>
									<th align="center">Amount</th> 
								  </tr>
								</thead>
								
								 <tbody >
								 </tbody>
								  
								</table>
						 
				</div>
			</div>
		<!--table end-->
		 
		</div>	
    </div>
    
    <div class="row" id="menuTable">
		<div class="col-md-12">
		<!--table-->
			<div class="table-responsive">
				<div class="shInnerwidth">
					
								<table width="100%" border="0" cellspacing="0"
														cellpadding="0" id="table_menu" class="table table-bordered">
								<thead >
									<tr class="bgpink">
									<th align="right" style="width:100px">Sr no.</th>
									<!-- <th align="center">Bill No</th> -->
									<th align="center">Category Name</th>
								
								 	<th align="center">Quantity</th>
									<th align="center">Amount</th> 
								  </tr>
								</thead>
								
								 <tbody >
								 </tbody>
								  
								</table>
						 
				</div>
			</div>
		<!--table end-->
		 
		</div>	
    </div>

	<div id="chart" style="display: none"><br><br><br>
		 <hr><div  >
	 
			<div  id="chart_div" style="width:60%; height:300; float:left;" style="overflow-y: scroll;"></div> 
		 
			<div   id="Piechart" style="width:40%%; height:300; float: right;" ></div> 
			<div   id="PieAmtchart" style="width:40%%; height:300; float: right;" ></div> 
			</div>
			 
			<div class="colOuter" >
			 
				<div   id="PieChart_div" style="width:100%; height:300;" align="center" ></div>
				</div>
				 
				</div>
				
				
				</div>
				<!--rightSidebar-->

			</div>
			<!--fullGrid-->
		</div>
		<!--rightContainer-->

	</div>
	<!--wrapper-end-->

	<!--easyTabs-->
	<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<!--easyTabs-->

	
	<script type="text/javascript">
	function searchSellBill()
	{ 
		//document.getElementById('btn_pdf').style="display:none";
		document.getElementById('menuTable').style.display = "block";
		document.getElementById('table').style="display:none";
		   document.getElementById('chart').style="display:none";
		  // document.getElementById('showchart').style.display = "block";
		$('#table_menu td').remove();
		
		
		var isValid = validate();
		
		if (isValid) {
			
			var fromDate = document.getElementById("fromdatepicker").value;
			var toDate = document.getElementById("todatepicker").value;
			 
			
			
			$.getJSON('${getMenuwiselReport}',{
				
								fromDate : fromDate,
								toDate : toDate,
							 
								ajax : 'true',

							},
							function(data) {

								//$('#table_grid td').remove();
								
								

								if (data == "") {
									alert("No records found !!");

								}
							 

							 
								var amtTotal=0;
								
								var totalQty=0;
								
								$.each(data,function(key, sellBillData) {


									var tr = $('<tr></tr>');

								  	tr.append($('<td></td>').html(key+1));

								   
								  	
								   
								  	
								  	tr.append($('<td></td>').html('<p style="color:blue;"  onclick=itemSellBill('+sellBillData.catId+');>'+sellBillData.catName+'</p>'));
								  	
									tr.append($('<td></td>').html(sellBillData.qty));
									totalQty=totalQty + sellBillData.qty;
								  	
								  	tr.append($('<td></td>').html(sellBillData.amount));
								  	
								  	amtTotal=amtTotal + sellBillData.amount;
								  	
								  	
								  	

									$('#table_menu tbody').append(tr);

									
													

												})
												
							var tr = "<tr>";
								 var total = "<td colspan='2'>&nbsp;&nbsp;&nbsp;<b> Total</b></td>";
								 
								var totalAmt = "<td>&nbsp;&nbsp;&nbsp;<b>"
									+ amtTotal
									+ "</b></td>";
								 var totalQty = "<td><b>&nbsp;&nbsp;&nbsp;"
									+  totalQty
									+ "</b></td>";
							
									
								
								var trclosed = "</tr>";
								
								
								$('#table_menu tbody')
								.append(tr);
								$('#table_menu tbody')
								.append(total);
								$('#table_menu tbody')
								.append(totalQty);
								 $('#table_menu tbody')
									.append(totalAmt);
								 $('#table_menu tbody')
								 
								$('#table_menu tbody')
								.append(trclosed); 
								 
							});

		}
	}
	</script>
	
	<script type="text/javascript">
	function itemSellBill(id)
	{ 
		
		document.getElementById('table').style.display = "block";
		   document.getElementById('chart').style="display:none";
		   document.getElementById('menuTable').style="display:none";
		  // document.getElementById('showchart').style.display = "block";
		$('#table_grid td').remove();
		
		
		var isValid = validate();
		
		if (isValid) {
		//	document.getElementById('btn_pdf').style.display = "block";
			var fromDate = document.getElementById("fromdatepicker").value;
			var toDate = document.getElementById("todatepicker").value;
			var category=$("#category").val();
			
			
			$.getJSON('${getItemwiselReport}',{
				
								fromDate : fromDate,
								toDate : toDate,
								category : id,
								ajax : 'true',

							},
							function(data) {

								//$('#table_grid td').remove();
								
								

								if (data == "") {
									alert("No records found !!");

								}
							 

							 
								var amtTotal=0;
								
								var totalQty=0;
								
								$.each(data,function(key, sellBillData) {


									var tr = $('<tr></tr>');

								  	tr.append($('<td></td>').html(key+1));

								  	tr.append($('<td></td>').html(sellBillData.itemName));
								  	
								  	tr.append($('<td></td>').html(sellBillData.itemId));
								  	
								  	tr.append($('<td></td>').html(sellBillData.catName));
								  	
									tr.append($('<td></td>').html(sellBillData.qty));
									totalQty=totalQty + sellBillData.qty;
								  	
								  	tr.append($('<td></td>').html(sellBillData.amount));
								  	
								  	amtTotal=amtTotal + sellBillData.amount;
								  	
								  	
								  	

									$('#table_grid tbody').append(tr);

									
													

												})
												
							var tr = "<tr>";
								 var total = "<td colspan='4'>&nbsp;&nbsp;&nbsp;<b> Total</b></td>";
								 
								var totalAmt = "<td>&nbsp;&nbsp;&nbsp;<b>"
									+ amtTotal
									+ "</b></td>";
								 var totalQty = "<td><b>&nbsp;&nbsp;&nbsp;"
									+  totalQty
									+ "</b></td>";
							
									
								
								var trclosed = "</tr>";
								
								
								$('#table_grid tbody')
								.append(tr);
								$('#table_grid tbody')
								.append(total);
								$('#table_grid tbody')
								.append(totalQty);
								 $('#table_grid tbody')
									.append(totalAmt);
								 $('#table_grid tbody')
								 
								$('#table_grid tbody')
								.append(trclosed); 
								 
							});

		}
	}
	</script>
	
	<script type="text/javascript">
	function validate() {
	
	
		var fromDate =$("#fromdatepicker").val();
		var toDate =$("#todatepicker").val();
		

		var isValid = true;

	 if (fromDate == "" || fromDate == null) {

			isValid = false;
			alert("Please select From Date");
		}
	 else if (toDate == "" || toDate == null) {

			isValid = false;
			alert("Please select To Date");
		}
		return isValid;

	}
 
</script>
	<script type="text/javascript">
	 
function showChart(){
	$("#PieChart_div").empty();
	$("#chart_div").empty();
	
	
		document.getElementById('chart').style.display = "block";
		   document.getElementById("table").style="display:none";
		   document.getElementById("menuTable").style="display:none";
		   
		   var fromDate = document.getElementById("fromdatepicker").value;
			var toDate = document.getElementById("todatepicker").value;
			var isValid = validate();
			
			if (isValid) {
			//	document.getElementById("btn_pdf").style="display:none";
			$.getJSON('${getMenuwiselReport}',{
				
								fromDate : fromDate,
								toDate : toDate,
								ajax : 'true',

							},
							function(data) {
								 //alert(data);
							 if (data == "") {
									alert("No records found !!");

							 }
							 var i=0;

							 google.charts.load('current', {'packages':['corechart', 'bar']});
							 google.charts.setOnLoadCallback(drawStuff);

							 function drawStuff() {
 
							   var chartDiv = document.getElementById('chart_div');
							   document.getElementById("chart_div").style.border = "thin dotted red";
						       var dataTable = new google.visualization.DataTable();
						       
						       dataTable.addColumn('string', 'Category'); // Implicit domain column.
						       dataTable.addColumn('number', 'Amount'); // Implicit data column.
						      // dataTable.addColumn({type:'string', role:'interval'});
						     //  dataTable.addColumn({type:'string', role:'interval'});
						       dataTable.addColumn('number', 'Qauntity');
						       $.each(data,function(key, item) {

									//var tax=item.cgst + item.sgst;
									//var date= item.billDate+'\nTax : ' + item.tax_per + '%';
									
								   dataTable.addRows([
									 
								             [item.catName, item.amount, item.qty, ]
								           
								           ]);
								     }) 
						    
 var materialOptions = {
          width: 600,
          height:450,
          chart: {
            title: ' Qauntity & Amount',
            subtitle: 'Menu wise Qauntity & Amount Graph'
          },
          series: {
            0: { axis: 'distance' }, // Bind series 0 to an axis named 'distance'.
            1: { axis: 'brightness' } // Bind series 1 to an axis named 'brightness'.
          },
          axes: {
            y: {
              distance: {label: 'Amount'}, // Left y-axis.
              brightness: {side: 'right', label: 'Quantity'} // Right y-axis.
            }
          }
        };
						       var materialChart = new google.charts.Bar(chartDiv);
						       
						       function selectHandler() {
							          var selectedItem = materialChart.getSelection()[0];
							          if (selectedItem) {
							            var topping = dataTable.getValue(selectedItem.row, 0);
							           // alert('The user selected ' + selectedItem.row,0);
							            i=selectedItem.row,0;
							            itemSellBill(data[i].catId);
							           // google.charts.setOnLoadCallback(drawBarChart);
							          }
							        }
						       
						       function drawMaterialChart() {
						          // var materialChart = new google.charts.Bar(chartDiv);
						           google.visualization.events.addListener(materialChart, 'select', selectHandler);    
						           materialChart.draw(dataTable, google.charts.Bar.convertOptions(materialOptions));
						          // button.innerText = 'Change to Classic';
						          // button.onclick = drawClassicChart;
						         }
						       
						       function drawQtyChart() {
									 var dataTable = new google.visualization.DataTable();
									 dataTable.addColumn('string', 'Topping');
									 dataTable.addColumn('number', 'Qauntity');
							
									   $.each(data,function(key, item) {

										//	var amt=item.cash + item.card + item.other;

										   dataTable.addRows([

										             [item.catName, item.qty]

										           ]);
										   

										   }) 
								 var options = {'title':'Sell Qauntity per Category',
					                       'width':400,
					                       'height':250};
									   document.getElementById("Piechart").style.border = "thin dotted red";
								 var chart = new google.visualization.PieChart(document.getElementById('Piechart'));
							        function selectQtyHandler() {
							          var selectedItem = chart.getSelection()[0];
							          if (selectedItem) {
							            var topping = dataTable.getValue(selectedItem.row, 0);
							           // alert('The user selected ' + selectedItem.row,0);
							            i=selectedItem.row,0;
							          itemSellBill(data[i].catId);
							           // google.charts.setOnLoadCallback(drawBarChart);
							          }
							        }

							        google.visualization.events.addListener(chart, 'select', selectQtyHandler);    
							        chart.draw(dataTable, options);
							      }
								 
						       function drawAmtChart() {
									 var dataTable = new google.visualization.DataTable();
									 dataTable.addColumn('string', 'Topping');
									 dataTable.addColumn('number', 'Amount');
							
									   $.each(data,function(key, item) {

										//	var amt=item.cash + item.card + item.other;

										   dataTable.addRows([

										             [item.catName, item.amount]

										           ]);
										   

										   }) 
								 var options = {'title':'Sell Amount per Category',
					                       'width':400,
					                       'height':250};
									   document.getElementById("PieAmtchart").style.border = "thin dotted red";
								 var chart = new google.visualization.PieChart(document.getElementById('PieAmtchart'));
							        function selectAmtHandler() {
							          var selectedItem = chart.getSelection()[0];
							          if (selectedItem) {
							            var topping = dataTable.getValue(selectedItem.row, 0);
							           // alert('The user selected ' + selectedItem.row,0);
							            i=selectedItem.row,0;
							          itemSellBill(data[i].catId);
							            //google.charts.setOnLoadCallback(drawBarChart);
							          }
							        }

							        google.visualization.events.addListener(chart, 'select', selectAmtHandler);    
							        chart.draw(dataTable, options);
							      }
								 
						      /*  var chart = new google.visualization.ColumnChart(
						                document.getElementById('chart_div'));
						       chart.draw(dataTable,
						          {width: 800, height: 600, title: 'Tax Summary Chart'}); */
						       drawMaterialChart();
						       google.charts.setOnLoadCallback(drawQtyChart);
						       google.charts.setOnLoadCallback(drawAmtChart);
							 };
							 
										
							  	});
			}
}
</script>
<script type="text/javascript">
function genPdf()
{
	var fromDate = document.getElementById("fromdatepicker").value;
	var toDate = document.getElementById("todatepicker").value;
	var frId=document.getElementById("frId").value;
	window.open('${pageContext.request.contextPath}/pdf?reportURL=pdf/showSellItemwiseReportpPdf/'+fromDate+'/'+toDate+'/'+frId);
	
	}

</script>	
</body>
</html>
