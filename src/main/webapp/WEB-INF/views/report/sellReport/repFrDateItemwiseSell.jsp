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

<%-- <!DOCTYPE html>
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



</head>
<body>
 --%>


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
<c:url var="getDateItemwiselReport" value="/getDateItemwiselReport" />
<c:url var="getDateCatwisellReport" value="/getDateCatwisellReport" />
	
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
	    <div class="col-md-12"><h2 class="pageTitle">Datewise-Itemwise Sale Report</h2></div>
	</div>
	<br>
<div class="row">
	
	<div class="col-sm-1"><div class="pull-left"><b>Group</b></div></div>
		<div class="col-md-3"><select id="category" class="form-control chosen"  style="width: 230px" placeholder="Select Category"  name="category" tabindex="4"    >
		<option value="-1">Select Option</option>
								
											
											<c:forEach items="${unSelectedCatList}" var="unSelectedCat"
													varStatus="count">
												<option value="${unSelectedCat.catId}"><c:out value="${unSelectedCat.catName}"/></option>
												</c:forEach>

							</select>
							</div>
		<div class="col-sm-1"><div class="pull-left"><b>From</b></div></div>
	    <div class="col-md-3">
		<input id="fromdatepicker"  placeholder="From Date"  name="from_Date" type="text" style="width: 230px; text-align:left;" size="30"  class="form-control" autocomplete="off">
		</div>
			<div class="col-sm-1"><div class="pull-left"><b>TO</b></div></div>
			  <div class="col-md-3">
		<input id="todatepicker"  placeholder="To Date"  name="to_Date" type="text" size="30" style="width: 230px;  text-align:left;"  class="form-control" autocomplete="off">
		</div></div>
					<input type="hidden" name="frId" id="frId" value="${frId}">
		
	<!-- </div>
 </div> -->
	<br>
 <!-- 	<div align="center"> 
		    <button class="btn search_btn" onclick="searchSellBill()" >HTML View </button>
		    <button class="btn search_btn" onclick="showChart()" >Graph</button>
		    	    <button class="btn search_btn" onclick="showPdf()" >PDF </button>
		 
		<br>
    </div> -->
	<!-- </div> -->
	<div class="row">
		<div align="center"> 
		    <button class="btn search_btn" onclick="searchSellBill()" >HTML View </button>
		    <button class="btn search_btn" onclick="showChart()" >Graph</button>
<%-- 		    	     <a href='${pageContext.request.contextPath}/pdf?reportURL=showSellDateItemwisewiseReportpPdf' class="btn search_btn" id="btn_pdf" style="display: none">PDF</a>
 --%>		 	<button class="btn btn-primary" value="PDF" id="PDFButton" onclick="genPdf()">PDF</button>
		
    </div></div> 
		<br> 
	<%-- <div class="row">
	
	<div class="colOuter">
		<div class="col1"><div class="col1title">Group</div></div>
		<div class="col2"><select id="category" class="form-control chosen" placeholder="Select Category"  name="category" tabindex="6"    >
		<option value="-1">Select Option</option>
								
											
											<c:forEach items="${unSelectedCatList}" var="unSelectedCat"
													varStatus="count">
												<option value="${unSelectedCat.catId}"><c:out value="${unSelectedCat.catName}"/></option>
												</c:forEach>

							</select>
		</div>
			
								
	</div>
	
		<div class="colOuter">
		<div class="col1"><div class="col1title">From</div></div>
		<div class="col2"><input id="fromdatepicker" class="texboxitemcode texboxcal" placeholder="Delivery Date"  name="from_Date" type="text"  >
		</div>
	</div>

	<div class="colOuter">
		<div class="col1"><div class="col1title">TO</div></div>
		<div class="col2"><input id="todatepicker" class="texboxitemcode texboxcal" placeholder="Delivery Date"  name="to_Date" type="text"  >
		</div>
	</div>
	
		<div align="center">
		     <button class="btn search_btn" onclick="searchSellBill()" >HTML View </button>
		    <button class="btn search_btn" onclick="showChart()" >Graph</button>
		    	   <br><br> <button id="btn_pdf" class="btn search_btn" onclick="showPdf()"  >PDF </button>
		</div>
		<br>
		
    </div>
	 --%>
	<div class="row" id="table">
		<div class="col-md-12">
		<!--table-->
			<div class="clearfix"></div>


				<div id="table-scroll" class="table-scroll">
					<div id="faux-table" class="faux-table" aria="hidden"></div>
					<div class="table-wrap">
						<table id="table_grid" class="main-table">
							<thead>
								<tr class="bgpink">

									<th class="col-md-1" style="text-align:center;">Sr.No.</th>
									<!-- <th class="col-md-1">Bill No</th> -->
									<th class="col-md-1" style="text-align:center;">Bill Date</th>
									<th class="col-md-1" style="text-align:center;">Item Name</th>
									<th class="col-md-1" style="text-align:center;">Group Name</th>
								 	<th class="col-md-1" style="text-align:center;">Quantity</th>
									<th class="col-md-1" style="text-align:center;">Amount</th> 
								  </tr>
								</thead>
								
								 <tbody >
								 </tbody>
								  
								</table>
						 
				</div>
				</div>
		<!--table end-->
		<br>
				 <div class="form-group" style="display: none;" id="range">
								 
											 
											 
											<div class="col-sm-3  controls">
											 <input type="button" id="expExcel" class="btn btn-primary" value="EXPORT TO Excel" onclick="exportToExcel();" disabled="disabled">
											</div>
											</div>
			
		 
		</div>	
    </div>

	<div id="chart" style="display: none"><br> <hr>
		<div id="chart_div" style="width:400; height:300" align="center"></div>
				 
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
		
		   document.getElementById('chart').style="display:none";
		 //  document.getElementById('btn_pdf').style.display = "block";
		$('#table_grid td').remove();
		
		
		var isValid = validate();
		
		if (isValid) {
			document.getElementById('table').style.display = "block";
			var fromDate = document.getElementById("fromdatepicker").value;
			var toDate = document.getElementById("todatepicker").value;
			var category=$("#category").val();
			
			
			$.getJSON('${getDateItemwiselReport}',{
				
								fromDate : fromDate,
								toDate : toDate,
								category : category,
								ajax : 'true',

							},
							function(data) {

								//$('#table_grid td').remove();
								
								

								if (data == "") {
									alert("No records found !!");
									  document.getElementById("expExcel").disabled=true;
								}
							 

							 
								var amtTotal=0;
								
								var totalQty=0;
								
								$.each(data,function(key, sellBillData) {
									  document.getElementById("expExcel").disabled=false;
										document.getElementById('range').style.display = 'block';

									var tr = $('<tr></tr>');

								  	tr.append($('<td class ="col-md-1"></td>').html(key+1));
								  	
								  	tr.append($('<td class ="col-md-1"></td>').html(sellBillData.billDate));

								  	tr.append($('<td class ="col-md-1"></td>').html(sellBillData.itemName));
								  									  	
								  	tr.append($('<td class ="col-md-1" style="text-align:center;"></td>').html(sellBillData.catName));
								  	
									tr.append($('<td class ="col-md-1" style="text-align:right;"></td>').html((sellBillData.qty).toFixed(2)));
									totalQty=totalQty + sellBillData.qty;
								  	
								  	tr.append($('<td class ="col-md-1" style="text-align:right;"></td>').html((sellBillData.amount).toFixed(2)));
								  	
								  	amtTotal=amtTotal + sellBillData.amount;
								  	
								  	
								  	

									$('#table_grid tbody').append(tr);

									
													

												})
												
							var tr = "<tr>";
								 var total = "<td colspan='4'>&nbsp;&nbsp;&nbsp;<b> Total</b></td>";
								 
								var totalAmt = "<td style=text-align:right;>&nbsp;&nbsp;&nbsp;<b>"
									+ (amtTotal).toFixed(2)
									+ "</b></td>";
								 var totalQty = "<td style=text-align:right;><b>&nbsp;&nbsp;&nbsp;"
									+  (totalQty).toFixed(2)
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
		
		document.getElementById('chart').style.display = "block";
		   document.getElementById("table").style="display:none";
		
		   var isValid = validate();
			
			if (isValid) {
				  // document.getElementById('btn_pdf').style.display = "block";
				var fromDate = document.getElementById("fromdatepicker").value;
				var toDate = document.getElementById("todatepicker").value;
				var category=$("#category").val();
				
				
				$.getJSON('${getDateCatwisellReport}',{
					
									fromDate : fromDate,
									toDate : toDate,
									category : category,
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

							 function drawStuff() {
 
							   var chartDiv = document.getElementById('chart_div');
							   document.getElementById("chart_div").style.border = "thin dotted red";
						       var dataTable = new google.visualization.DataTable();
						       
						       dataTable.addColumn('string', 'Bill Date'); // Implicit domain column.
						       dataTable.addColumn('number', 'Amount'); // Implicit data column.
						      // dataTable.addColumn({type:'string', role:'interval'});
						     //  dataTable.addColumn({type:'string', role:'interval'});
						       dataTable.addColumn('number', 'Qauntity');
						       $.each(data,function(key, item) {

									//var tax=item.cgst + item.sgst;
									//var date= item.billDate+'\nTax : ' + item.tax_per + '%';
									
								   dataTable.addRows([
									 
								             [item.billDate, item.amount, item.qty, ]
								           
								           ]);
								     }) 
						    
 var materialOptions = {
          width: 600,
          height:450,
          chart: {
            title: ' Qauntity & Amount',
            subtitle: 'Date wise Qauntity & Amount '
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
							            itemSellBill(data[i].billDate);
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
						         
						       drawMaterialChart();
						 
							 };
							 
										
							  	});
			}
}
</script>
<script type="text/javascript">
	function itemSellBill(date)
	{ 
		document.getElementById('table').style.display = "block";
		   document.getElementById('chart').style="display:none";

	
		$('#table_grid td').remove();
		
		
		var isValid = validate();
		
		if (isValid) {
			   document.getElementById('btn_pdf').style.display = "block";
			//var fromDate = date;
			//var toDate = date;
			var category=$("#category").val();
			//alert(date);
			var newdate = date.split("-").reverse().join("-");
			
			$.getJSON('${getDateItemwiselReport}',{
				
								fromDate : newdate,
								toDate : newdate,
								category : category,
								ajax : 'true',

							},
							function(data) {

								//$('#table_grid td').remove();
								
								

								if (data == "") {
									alert("No records found !!");
									 document.getElementById("expExcel").disabled=true;
								}
							 

							 
								var amtTotal=0;
								
								var totalQty=0;
								
								$.each(data,function(key, sellBillData) {

									  document.getElementById("expExcel").disabled=false;
										document.getElementById('range').style.display = 'block';
									var tr = $('<tr></tr>');

								  	tr.append($('<td class ="col-md-1"></td>').html(key+1));
								  	
								  	tr.append($('<td class ="col-md-1"></td>').html(sellBillData.billDate));

								  	tr.append($('<td class ="col-md-1"></td>').html(sellBillData.itemName));
								  									  	
								  	tr.append($('<td class ="col-md-1"></td>').html(sellBillData.catName));
								  	
									tr.append($('<td class ="col-md-1"></td>').html(sellBillData.qty));
									totalQty=totalQty + sellBillData.qty;
								  	
								  	tr.append($('<td class ="col-md-1"></td>').html(sellBillData.amount));
								  	
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
	var category=$("#category").val();

	var fromDate = document.getElementById("fromdatepicker").value;
	var toDate = document.getElementById("todatepicker").value;
	var frId=document.getElementById("frId").value;
	window.open('${pageContext.request.contextPath}/pdf?reportURL=pdf/showSellDateItemwisewiseReportpPdf/'+fromDate+'/'+toDate+'/'+frId+'/'+category);
		}
	}

</script>	
</body>
</html>
