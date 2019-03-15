<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<style>
table, th, td {
    border: 1px solid #9da88d;
}

</style>
</head>
<body>
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
<c:url var="getDatewiselReport" value="/getDatewiselReport" />
	
	<div class="sidebarOuter"></div>
	
	<div class="wrapper">

		<!--topHeader-->

		<jsp:include page="/WEB-INF/views/include/logo.jsp">
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
	    <div class="col-md-12"><h2 class="pageTitle">Datewise Sale Report</h2></div>
	</div>
	
	<div class="colOuter">
		<div align="center" >
		<div class="col1"><div class="col1title"><b>From&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
		<input id="fromdatepicker"  placeholder="Delivery Date"  name="from_Date" type="text" size="35"  autocomplete="off" >
		</div></div>
		<div class="col2"><div class="col1title"><b>TO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
		<input id="todatepicker"  placeholder="Delivery Date"  name="to_Date" type="text" size="35" autocomplete="off"  >
		</div></div>
				<input type="hidden" name="frId" id="frId" value="${frId}">
		
	</div>
 
	
 	<div align="center"> 
		    <button class="btn search_btn" onclick="searchSellBill()" >HTML View </button>
		    <button class="btn search_btn" onclick="showChart()" >Graph</button>
<%-- 		    	   <a href='${pageContext.request.contextPath}/pdf?reportURL=showSellDatewiseReportpPdf' id="btn_pdf" class="btn search_btn" style="display: none">PDF</a>
 --%>		 		 												<button class="btn btn-primary" value="PDF" id="PDFButton" onclick="genPdf()">PDF</button>
 
		<br>
    </div>
	</div>
	
	<div class="row" id="table">
		<div class="col-md-12">
		<!--table-->
		<div class="clearfix"></div>


				<div id="table-scroll" class="table-scroll">
					<div id="faux-table" class="faux-table" aria="hidden">
						<div class="table-wrap">
						<table id="table_grid" class="main-table" >
							<thead>
								<tr class="bgpink">

									<th class="col-md-1"style="text-align:center;">Sr.No.</th>
								<!--       <th class="col-md-1" style="text-align:center;">Bill No</th>  -->
									<th class="col-md-1"style="text-align:center;">Day</th>
									<th class="col-md-1"style="text-align:center;">Date</th>
									<th class="col-md-1"style="text-align:center;">Amount</th>
									<th class="col-md-1"style="text-align:center;">Cash</th>
								 	<th class="col-md-1"style="text-align:center;">Card</th>
								 	<th class="col-md-1"style="text-align:center;">Other</th> 
								  </tr>
								</thead>
								
								 <tbody >
								 </tbody>
								  
								</table></div>
				</div>
					<div class="table-wrap">
						<table id="table_grid" class="main-table" >
							<thead>
								<tr class="bgpink">

									<th class="col-md-1"style="text-align:center;">Sr.No.</th>
									<!--  <th class="col-md-1"style="text-align:center;">Bill No</th> -->
									<th class="col-md-1"style="text-align:center;">Day</th>
									<th class="col-md-1"style="text-align:center;">Date</th>
									<th class="col-md-1"style="text-align:center;">Amount</th>
									<th class="col-md-1"style="text-align:center;">Cash</th>
								 	<th class="col-md-1"style="text-align:center;">Card</th>
								 <th class="col-md-1"style="text-align:center;">Other</th>
								  </tr>
								</thead>
								
								 <tbody >
								 </tbody>
								  
								</table></div><br>
								<div class="form-group" style="display: none;" id="range">
								 
											 
											 
											<div class="col-sm-3  controls">
											 <input type="button" id="expExcel" class="btn btn-primary" value="EXPORT TO Excel" onclick="exportToExcel();" disabled="disabled">
											</div>
											</div>
						<div align="center" id="showchart" style="display: none">
		    
		    
		
				</div>
			</div>
		<!--table end-->
		 
		</div>	
    </div>

	<div id="chart" style="display: none"><br><br><br>
	<hr><div  >
	 
			<div  id="chart_div" style="width:60%; height:300; float:left;" ></div> 
		 
			<div   id="PieChart_div" style="width:40%%; height:300; float: right;" ></div> 
			</div>
			<!-- <hr style="height:1px; width:50%%;" color="black">
			<div class="colOuter" >
			 
				<div   id="PieChart_div" style="width:100%; height:300;" align="center" ></div>
				</div> -->
				 
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
		document.getElementById('table').style.display = "block";
		   document.getElementById('chart').style="display:none";
		   //document.getElementById('showchart').style.display = "block";
		$('#table_grid td').remove();
		
		
		var isValid = validate();
		
		if (isValid) {
			//document.getElementById('btn_pdf').style.display = "block";
			var fromDate = document.getElementById("fromdatepicker").value;
			var toDate = document.getElementById("todatepicker").value;
			   
			
			$.getJSON('${getDatewiselReport}',{
				
								fromDate : fromDate,
								toDate : toDate,
								ajax : 'true',

							},
							function(data) {

								//$('#table_grid td').remove();
								
								

								if (data == "") {
									alert("No records found !!");
									document.getElementById("expExcel").disabled=true;
								}
								 

							 	var cashTotal=0;
								var cardTotal=0;
								var amtTotal=0;
								var otherTotal=0;
								$.each(data,function(key, sellBillData) {

									  document.getElementById("expExcel").disabled=false;
										document.getElementById('range').style.display = 'block';
									var tr = $('<tr></tr>');

								  	tr.append($('<td class="col-md-1" style="text-align:left;"></td>').html(key+1));

									/* tr.append($('<td class="col-md-1"></td>').html(sellBillData.billNo)); */
									tr.append($('<td class="col-md-1"></td>').html(sellBillData.day));
								  	tr.append($('<td class="col-md-1"></td>').html(sellBillData.billDate));
								  	
								  	var amt=sellBillData.cash + sellBillData.card + sellBillData.other;
								  	tr.append($('<td class="col-md-1"style="text-align:right;"></td>').html((amt).toFixed(2)));
								  	
								  	amtTotal=amtTotal + sellBillData.cash + sellBillData.card + sellBillData.other;
									
								  	tr.append($('<td class="col-md-1"style="text-align:right;"></td>').html((sellBillData.cash).toFixed(2)));
								  	cashTotal=cashTotal + sellBillData.cash;

								  	tr.append($('<td class="col-md-1"style="text-align:right;"></td>').html((sellBillData.card).toFixed(2)));
								  	cardTotal=cardTotal + sellBillData.card;
								  	
								   	tr.append($('<td class="col-md-1"style="text-align:right;"></td>').html((sellBillData.other).toFixed(2)));
								  	otherTotal=otherTotal + sellBillData.other; 

								  	

									$('#table_grid tbody').append(tr);

									
													

												})
												
							var tr = "<tr>";
								 var total = "<td colspan='3'style=text-align:left;>&nbsp;&nbsp;&nbsp;<b> Total</b></td>";
								 
								var totalAmt = "<td style=text-align:right;>&nbsp;&nbsp;&nbsp;<b>"
									+ (amtTotal).toFixed(2);
									+ "</b></td>";
								 var cash = "<td style=text-align:right;><b>&nbsp;&nbsp;&nbsp;"
									+  (cashTotal).toFixed(2);
									+ "</b></td>";
								var card = "<td style=text-align:right;><b>&nbsp;&nbsp;&nbsp;"
									+ (cardTotal).toFixed(2);
									+ "</b></td>";
								var other = "<td style=text-align:right;><b>&nbsp;&nbsp;&nbsp;"
									+ (otherTotal).toFixed(2);
									+ "</b></td>"; 
									
								
								var trclosed = "</tr>";
								
								
								$('#table_grid tbody')
								.append(tr);
								$('#table_grid tbody')
								.append(total);
								$('#table_grid tbody')
								.append(totalAmt);
								 $('#table_grid tbody')
									.append(cash);
								 $('#table_grid tbody')
								.append(card);
								$('#table_grid tbody')
								.append(other); 
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
		 
		   var fromDate = document.getElementById("fromdatepicker").value;
			var toDate = document.getElementById("todatepicker").value;
			   
			var isValid = validate();
			
			if (isValid) {
				 // document.getElementById('btn_pdf').style.display = "block";
				  
			$.getJSON('${getDatewiselReport}',{
				
								fromDate : fromDate,
								toDate : toDate,
								ajax : 'true',

							},
							function(data) {
								// alert(data);
							 if (data == "") {
									alert("No records found !!");

								}
							 var i=0;
							 
							  google.charts.load('current', {'packages':['corechart', 'bar']});
							  google.charts.setOnLoadCallback(drawStuff);
							 
							 function drawChart() {
								 var dataTable = new google.visualization.DataTable();
								 dataTable.addColumn('string', 'Topping');
								 dataTable.addColumn('number', 'Slices');
						
								   $.each(data,function(key, item) {

										var amt=item.cash + item.card + item.other;

									   dataTable.addRows([

									             [item.billDate, amt]

									           ]);
									   

									   }) 
							 var options = {'title':'Sell Amount per Day',
				                       'width':300,
				                       'height':250};
							 var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));

							   document.getElementById("chart_div").style.border = "thin dotted red";
						        function selectHandler() {
						          var selectedItem = chart.getSelection()[0];
						          if (selectedItem) {
						            var topping = dataTable.getValue(selectedItem.row, 0);
						           // alert('The user selected ' + selectedItem.row,0);
						            i=selectedItem.row,0;
						            google.charts.setOnLoadCallback(drawBarChart);
						          }
						        }

						        google.visualization.events.addListener(chart, 'select', selectHandler);    
						        chart.draw(dataTable, options);
						      }
							 
							 function drawStuff() {
								 
								   var chartDiv = document.getElementById('chart_div');
								   document.getElementById("chart_div").style.border = "thin dotted red";
							       var dataTable = new google.visualization.DataTable();
							       
							       dataTable.addColumn('string', 'Date'); // Implicit domain column.
							       dataTable.addColumn('number', 'Amount'); // Implicit data column.
							      // dataTable.addColumn({type:'string', role:'interval'});
							     //  dataTable.addColumn({type:'string', role:'interval'});
							       $.each(data,function(key, item) {

										var amt=item.cash + item.card + item.other;

									   dataTable.addRows([

									             [item.billDate, amt]

									           ]);
									   

									   }) 
							    
	 var materialOptions = {
	          width: 600,
	          height:450,
	          chart: {
	            title: 'Sell Amount per Day',
	            subtitle: ' '
	          },
	          series: {
	            0: { axis: 'distance' }, // Bind series 0 to an axis named 'distance'.
	            1: { axis: 'brightness' } // Bind series 1 to an axis named 'brightness'.
	          },
	          axes: {
	            y: {
	              distance: {label: 'Paid Amount'}, // Left y-axis.
	              brightness: {side: 'right', label: 'Total Tax'} // Right y-axis.
	            }
	          }
	        };
							       var materialChart = new google.charts.Bar(chartDiv);
							       
							        function selectHandler() {
						          var selectedItem = materialChart.getSelection()[0];
						          if (selectedItem) {
						            var topping = dataTable.getValue(selectedItem.row, 0);
						            //alert('The user selected ' + selectedItem.row,0);
						            i=selectedItem.row,0;
						            google.charts.setOnLoadCallback(drawBarChart);
						          }
						        }
							       
							       function drawMaterialChart() {
							          // var materialChart = new google.charts.Bar(chartDiv);
							           google.visualization.events.addListener(materialChart, 'select', selectHandler);    
							           materialChart.draw(dataTable, google.charts.Bar.convertOptions(materialOptions));
							          // button.innerText = 'Change to Classic';
							          // button.onclick = drawClassicChart;
							         }
							         
							       drawMaterialChart();
							 
								 };
							 
							 
							 function drawBarChart() {
								 var dataTable = new google.visualization.DataTable();
								 dataTable.addColumn('string', 'Topping');
								 dataTable.addColumn('number', 'Slices');
							        
									   dataTable.addRows([

									             ['Cash', data[i].cash],
									             ['Card', data[i].card],
									             ['Other', data[i].other],

									           ]);

									//   }) 
							 var options = {'title':'Total Amount: Cash, Card and Other',
				                       'width':400,
				                       'height':250};
							 var chart = new google.visualization.PieChart(document.getElementById('PieChart_div'));

							   document.getElementById("PieChart_div").style.border = "thin dotted red";
						        function selectHandler() {
						          var selectedItem = chart.getSelection()[0];
						          if (selectedItem) {
						            var topping = dataTable.getValue(selectedItem.row, 0);
						          //  alert('The user selected ' + selectedItem.row,0);
						           
						          }
						        }

						        google.visualization.events.addListener(chart, 'select', selectHandler);    
						        chart.draw(dataTable, options);
				
							 }
										
							  	});
			}
			}
			
			
			
</script>

<script>
	
(function() {
  var fauxTable = document.getElementById("faux-table");
  var mainTable = document.getElementById("table_grid");
  var clonedElement = table_grid.cloneNode(true);
  var clonedElement2 = table_grid.cloneNode(true);
  clonedElement.id = "";
  clonedElement2.id = "";
  fauxTable.appendChild(clonedElement);
  fauxTable.appendChild(clonedElement2);
})();

function exportToExcel()
{
	 
	window.open("${pageContext.request.contextPath}/exportToExcel");
			document.getElementById("expExcel").disabled=true;
}
	</script>

<script type="text/javascript">
function genPdf()
{  
	var isValid=validate();
    if(isValid==true)
    {
	var fromDate = document.getElementById("fromdatepicker").value;
	var toDate = document.getElementById("todatepicker").value;
	var frId=document.getElementById("frId").value;
	window.open('${pageContext.request.contextPath}/pdf?reportURL=pdf/showSellDatewiseReportpPdf/'+fromDate+'/'+toDate+'/'+frId);
    }
}

</script>	

</body>
</html>
