<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>



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


<c:url var="getBillwiseTaxSellReport" value="/getBillwiseTaxSellReport" />
	
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
	    <div class="col-md-12"><h2 class="pageTitle">View Sell Tax Bill wise Report</h2></div>
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
		<%--   &nbsp;&nbsp;&nbsp;
		    	     <a href='${pageContext.request.contextPath}/pdf?reportURL=showSellTaxBillwiseReportpPdf' id="btn_pdf" class="btn search_btn" style="display: none">PDF</a> --%>
		  <button class="btn btn-primary" value="PDF" id="PDFButton" onclick="genPdf()">PDF</button>
		<br>
    </div>
	</div>
	<div class="row">
		<div class="col-md-12">
		<!--table-->
			<div class="clearfix"></div>


				<div id="table-scroll" class="table-scroll">
					<div id="faux-table" class="faux-table" aria="hidden"></div>
					<div class="table-wrap">
						<table id="table_grid" class="main-table">
							<thead>
								<tr class="bgpink">

															
								
									<th class="col-md-1">Sr.No.</th>
									<!-- <th class="md=col-1">Bill No</th> -->
									<!-- <th class="md=col-1">Party name</th> -->
									<th class="col-md-1">GST No.</th>
									<th class="col-md-1">Bill no.</th>
									<th class="md=col-1">Date</th>
									<th class="md=col-1">Bill Amt</th>
									<th class="md=col-1">Taxable Amt</th>
									<th class="md=col-1">Tax Rate</th>
									<th class="md=col-1">IGST</th>
								 	<th class="md=col-1">CGST</th>
									<th class="md=col-1">SGST</th> 
									<th class="md=col-1">CESS</th> 
								  </tr>
								</thead>
								
								 <tbody >
								
								</tbody>
								  
								</table>
								 
						
				</div>
					<div class="form-group" style="display: none;" id="range">
								 
											 
											 
											<div class="col-sm-3  controls">
											 <input type="button" id="expExcel" class="btn btn-primary" value="EXPORT TO Excel" onclick="exportToExcel();" disabled="disabled">
											</div>
											</div>
			</div>
		<!--table end-->
		 
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
	<!--easyTabs-->

	
	<script type="text/javascript">
	function searchSellBill()
	{ 
		
		$('#table_grid td').remove();
		
		
		var isValid = validate();
		
		if (isValid) {
			//document.getElementById('btn_pdf').style.display = "block";
			var fromDate = document.getElementById("fromdatepicker").value;
			var toDate = document.getElementById("todatepicker").value;
			   
			
			$.getJSON('${getBillwiseTaxSellReport}',{
				
								fromDate : fromDate,
								toDate : toDate,
								ajax : 'true',

							},
							function(data) {

								 
								
								

								if (data == "") {
									alert("No records found !!");
									  document.getElementById("expExcel").disabled=true;
								}
								 

							 	var taxTotal=0;
								var igstTotal=0;
								var cgstTotal=0;
								var sgstTotal=0;
								var cessTotal=0;
								var billTotal=0;
								$.each(data,function(key, sellTaxData) {

									
									  document.getElementById("expExcel").disabled=false;
										document.getElementById('range').style.display = 'block';
									
									var tr = $('<tr></tr>');

								  	tr.append($('<td class="col-md-1"></td>').html(key+1));
								  	
								  	tr.append($('<td class="col-md-1"></td>').html(sellTaxData.tax_gstn));
								  	
								  	tr.append($('<td class="col-md-1"></td>').html(sellTaxData.sellBillNo));

								  	tr.append($('<td class="col-md-1"></td>').html(sellTaxData.billDate));
								  	
								  	tr.append($('<td class="col-md-1"></td>').html(sellTaxData.bill_amount));
								  	billTotal=billTotal + sellTaxData.bill_amount;
								  	
								   	tr.append($('<td class="col-md-1"></td>').html(sellTaxData.tax_amount));
								  	taxTotal=taxTotal + sellTaxData.tax_amount;
								  	
									tr.append($('<td class="col-md-1"></td>').html(sellTaxData.tax_per));
								  	
									tr.append($('<td class="col-md-1"></td>').html(sellTaxData.igst));
									igstTotal=igstTotal + sellTaxData.igst;
									
								  	tr.append($('<td class="col-md-1"></td>').html(sellTaxData.cgst));
								  	cgstTotal=cgstTotal + sellTaxData.cgst;

								  	tr.append($('<td class="col-md-1"></td>').html(sellTaxData.sgst));
								  	sgstTotal=sgstTotal + sellTaxData.sgst;
								  	
								  	tr.append($('<td class="col-md-1"></td>').html(sellTaxData.sess));
								  	cessTotal=cessTotal + sellTaxData.sess;

								  	

									$('#table_grid tbody').append(tr);

									
									
													

												})
												
							var tr = "<tr>";
								 var total = "<td colspan='4'>&nbsp;&nbsp;&nbsp;<b> Total</b></td>";
								 
								 
								 var totalAmt="<td>&nbsp;&nbsp;&nbsp;<b>"
										+ billTotal.toFixed(2);
										+ "</b></td>";
								var totalTax = "<td>&nbsp;&nbsp;&nbsp;<b>"
									+ taxTotal.toFixed(2);
									+ "</b></td>";
									var blank = "<td>&nbsp;&nbsp;&nbsp;<b>"
										
										+ "</b></td>";
								var igst = "<td><b>&nbsp;&nbsp;&nbsp;"
										+  igstTotal.toFixed(2);
										+ "</b></td>";
								 var cgst = "<td><b>&nbsp;&nbsp;&nbsp;"
									+  cgstTotal.toFixed(2);
									+ "</b></td>";
								var sgst = "<td><b>&nbsp;&nbsp;&nbsp;"
									+ sgstTotal.toFixed(2);
									+ "</b></td>";
								var cess = "<td><b>&nbsp;&nbsp;&nbsp;"
									+ cessTotal.toFixed(2);
									+ "</b></td>"; 
									
								
								var trclosed = "</tr>";
								
								
								$('#table_grid tbody')
								.append(tr);
								$('#table_grid tbody')
								.append(total);
								
								$('#table_grid tbody')
								.append(totalAmt);
								$('#table_grid tbody')
								.append(totalTax)
								 $('#table_grid tbody')
									.append(blank);
								 $('#table_grid tbody')
									.append(igst);
								 $('#table_grid tbody')
								.append(cgst);
								$('#table_grid tbody')
								.append(sgst); 
								$('#table_grid tbody')
								.append(cess); 
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
	window.open('${pageContext.request.contextPath}/pdf?reportURL=pdf/showSellTaxBillwiseReportpPdf/'+fromDate+'/'+toDate+'/'+frId+'/');
		}
}

</script>	
	
</body>
</html>
