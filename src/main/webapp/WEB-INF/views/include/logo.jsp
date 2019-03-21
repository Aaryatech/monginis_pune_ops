<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<!--topHeader-->
<div class="fullGrid center logoBarbg slideposi">
	<div class="wrapperIn positionR">
    	<div class="logoBarLeft"><a href="${pageContext.request.contextPath}/home"><img src="${pageContext.request.contextPath}/resources/images/minlogo.png" alt="monginis"></a></div>  
       
         <c:choose>
               <c:when test = "${isSpDayShow ==true}">
                    <a class="special-day" href="${pageContext.request.contextPath}/showSpDayCake"> Advance Order </a>
              </c:when>
               <c:otherwise>
              </c:otherwise>
              </c:choose>
 
 <input type="hidden" id="achievedTarget" value="${sessionScope.achievedTarget}">
       <input type="hidden" id="target" value="${sessionScope.fraTarget}">
        <div class="logoBarRight">
      
        <div id="menuzord" class="menuzord red menuzord-responsive">
<!--            <div id="chart_div" style="width: 120px;height: 70px;  float: left;margin-right: 20px;"></div>
 -->           <ul class="menuzord-menu menuzord-right menuzord-indented scrollable">
           
               <c:choose>
                  <c:when test="${frDetails.frRate=='0'}"> <li><img class="mngimg" src="${pageContext.request.contextPath}/resources/images/star0.5.png" alt="monginis" style="height: 30px;"></li> </c:when>
                   <c:when test="${frDetails.frRate=='1'}">  <li><img class="mngimg" src="${pageContext.request.contextPath}/resources/images/star1.png" alt="monginis" style="height: 30px;"></li> </c:when> 
                    <c:when test="${frDetails.frRate=='2'}">  <li><img class="mngimg" src="${pageContext.request.contextPath}/resources/images/star1.5.png" alt="monginis" style="height: 30px;"></li> </c:when>
                     <c:when test="${frDetails.frRate=='3'}">  <li><img class="mngimg" src="${pageContext.request.contextPath}/resources/images/star2.png" alt="monginis" style="height: 30px;"></li> </c:when>
                      <c:when test="${frDetails.frRate=='4'}">  <li><img class="mngimg" src="${pageContext.request.contextPath}/resources/images/star2.5.png" alt="monginis" style="height: 30px;"></li> </c:when>
                       <c:when test="${frDetails.frRate=='5'}">  <li><img class="mngimg" src="${pageContext.request.contextPath}/resources/images/star3.png" alt="monginis" style="height: 30px;"></li> </c:when>
                        <c:when test="${frDetails.frRate=='6'}">  <li><img class="mngimg" src="${pageContext.request.contextPath}/resources/images/star3.5.png" alt="monginis" style="height: 30px;"></li> </c:when>
                         <c:when test="${frDetails.frRate=='7'}">  <li><img class="mngimg" src="${pageContext.request.contextPath}/resources/images/star4.png" alt="monginis"style="height: 30px;"></li> </c:when>
                          <c:when test="${frDetails.frRate=='8'}">  <li><img class="mngimg" src="${pageContext.request.contextPath}/resources/images/star4.5.png" alt="monginis" style="height: 30px;"></li> </c:when>
                         <c:when test="${frDetails.frRate=='9'}">  <li><img class="mngimg" src="${pageContext.request.contextPath}/resources/images/star5.png" alt="monginis" style="height: 30px;"></li> </c:when>
                <c:otherwise>
                    <img src="http://www.placehold.it/200x150/EFEFEF/AAAAAA&amp;text=no+image" alt="" />
                    
                </c:otherwise>    
              </c:choose>
              
               
                <li><a href="#"><div class="usericon">${frDetails.frName} </div>
                 <div class="userimg">
                <img src="${frDetails.frImage}"  height="70" width="70" this.src='${pageContext.request.contextPath}/resources/images/user.png';">
                	</div> </a>
                	<ul class="dropdown">
                       
						<li><a href="${pageContext.request.contextPath}/showeditprofile">Edit Profile</a></li>
						<li><a href="${pageContext.request.contextPath}/logout">Log out</a></li>
					</ul>
                </li>
            </ul>
        </div>
        </div>
    </div>
</div>
<!--topHeader-->

  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
 <!--  <script type="text/javascript">
	var achievedTarget=document.getElementById("achievedTarget").value;
	var target=document.getElementById("target").value;
google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(drawMultSeries);

function drawMultSeries() {
	 
	achievedTarget=Math.ceil(achievedTarget);
	 
	if(achievedTarget>=target)
		{
		 
		target=0;
		}
	else{
		 
		 
		target=target-achievedTarget;
		
	}
	
      var data = google.visualization.arrayToDataTable([
        ['Target In Lakhs', 'Assigned Target'],
        ['Franchise Target',target],
        ['Franchise Target',achievedTarget],
     
      
      ]);

      var options = {
    	        title: 'Franchise Target',
    	        chartArea: {width: '790',height: '790'},
    	        hAxis: {
    	          title: 'Target Details',
    	          minValue: 0
    	        },
    	        vAxis: {
    	          title: 'Target In Lakhs'
    	        }
    	     
    	      };


            var chart = new google.visualization.PieChart(document.getElementById('chart_div'));

   
      chart.draw(data, options);
    }


</script> -->
