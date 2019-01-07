<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>Dashboard - MONGINIS Admin</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">



<!--base css styles-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/data-tables/bootstrap3/dataTables.bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/bootstrap-fileupload/bootstrap-fileupload.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/chosen-bootstrap/chosen.min.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/bootstrap-timepicker/compiled/timepicker.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/clockface/css/clockface.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/bootstrap-datepicker/css/datepicker.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/assets/bootstrap-daterangepicker/daterangepicker.css" />



<!--page specific css styles-->

<!--flaty css styles-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/flaty.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/flaty-responsive.css">

<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon.png">

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common.js"></script>

</head>
<body>


	<div class="container" id="main-container">

		<!-- BEGIN Sidebar -->
		<div id="sidebar" class="navbar-collapse collapse">


			<div id="sidebar-collapse" class="visible-lg">
				<i class="fa fa-angle-double-left"></i>
			</div>
			<!-- END Sidebar Collapse Button -->
		</div>
		<!-- END Sidebar -->


		<!-- BEGIN Content -->
		<div id="main-content">
			<!-- BEGIN Page Title -->
			<div class="page-title">
				<div>
					<h1>
						<i class="fa fa-file-o"></i> Franchisees
					</h1>
				</div>
			</div>
			<!-- END Page Title -->


			<!-- BEGIN Main Content -->
			<div class="row">
				<div class="col-md-12">
					<div class="row">
						<div class="col-md-12">
							<div class="box">
								<div class="box-title">
									<h3>
										<i class="fa fa-bars"></i> Add Franchisee
									</h3>
									<div class="box-tool">
										<a href="${pageContext.request.contextPath}/resources/index.php/franchisee/list_all">Back to
											List</a> <a data-action="collapse" href="#"><i
											class="fa fa-chevron-up"></i></a>
									</div>
								</div>

								<div class="box-content">
									<!-- <form action="addNewFrProcess" class="form-horizontal" enctype="multipart/form-data
										id="validation-form" method="post"> -->
<form method="POST" action="addNewFrProcess" class="form-horizontal" enctype="multipart/form-data">
										<div class="form-group">
											<label class="col-sm-3 col-lg-2 control-label" for="fr_code">Code</label>
											<div class="col-sm-6 col-lg-4 controls">
												<input type="text" name="fr_code" id="fr_code"
													class="form-control" data-rule-required="true" />
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-3 col-lg-2 control-label">Opening
												Date</label>
											<div class="col-sm-5 col-lg-3 controls">
												<input class="form-control date-picker" id="dp1" size="16"
													type="text" name="fr_opening_date" value="10-08-2017"
													required />
											</div>
										</div>


										<div class="form-group">
											<label class="col-sm-3 col-lg-2 control-label" for="fr_name">Franchisee
												Name</label>
											<div class="col-sm-6 col-lg-4 controls">
												<input type="text" name="fr_name" id="fr_name"
													class="form-control" data-rule-required="true" />
											</div>
										</div>



										<div class="form-group">
											<label class="col-sm-3 col-lg-2 control-label">Image</label>
											<div class="col-sm-9 col-lg-10 controls">
												<div class="fileupload fileupload-new"
													data-provides="fileupload">
													<div class="fileupload-new img-thumbnail"
														style="width: 200px; height: 150px;">
														<img
															src="http://www.placehold.it/200x150/EFEFEF/AAAAAA&amp;text=no+image"
															alt="" />
													</div>
													<div
														class="fileupload-preview fileupload-exists img-thumbnail"
														style="max-width: 200px; max-height: 150px; line-height: 20px;"></div>
													<div>
														<span class="btn btn-default btn-file"><span
															class="fileupload-new">Select image</span> <span
															class="fileupload-exists">Change</span> <input
															type="file" class="file-input" name="fr_image"
															id="fr_image" data-rule-required="true" /></span> <a href="#"
															class="btn btn-default fileupload-exists"
															data-dismiss="fileupload">Remove</a>
													</div>
												</div>

											</div>
										</div>

										


										<div class="form-group">
											<div
												class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2">
												<input type="submit" class="btn btn-primary" value="Submit">
												<button type="button" class="btn">Cancel</button>
											</div>
										</div>
										
										
										
									</form>
								</div>
							</div>
						</div>
					</div></div></div>
					<!-- END Main Content -->

					<footer>
						<p>2017 © MONGINIS.</p>
					</footer>

					<a id="btn-scrollup" class="btn btn-circle btn-lg" href="#"><i
						class="fa fa-chevron-up"></i></a>
				</div>
				<!-- END Content -->
			</div>
			<!-- END Container -->

			<!--basic scripts-->
			
			<script
				src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
			<script>
				window.jQuery
						|| document
								.write('<script src="${pageContext.request.contextPath}/resources/assets/jquery/jquery-2.0.3.min.js"><\/script>')
			</script>
			<script src="${pageContext.request.contextPath}/resources/assets/bootstrap/js/bootstrap.min.js"></script>
			<script
				src="${pageContext.request.contextPath}/resources/assets/jquery-slimscroll/jquery.slimscroll.min.js"></script>
			<script src="${pageContext.request.contextPath}/resources/assets/jquery-cookie/jquery.cookie.js"></script>

			<!--page specific plugin scripts-->
			<script src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.js"></script>
			<script src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.resize.js"></script>
			<script src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.pie.js"></script>
			<script src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.stack.js"></script>
			<script src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.crosshair.js"></script>
			<script src="${pageContext.request.contextPath}/resources/assets/flot/jquery.flot.tooltip.min.js"></script>
			<script src="${pageContext.request.contextPath}/resources/assets/sparkline/jquery.sparkline.min.js"></script>

			<!--flaty scripts-->
			<script src="${pageContext.request.contextPath}/resources/js/flaty.js"></script>
			<script src="${pageContext.request.contextPath}/resources/js/flaty-demo-codes.js"></script>
			<!--page specific plugin scripts-->
			<script type="text/javascript"
				src="${pageContext.request.contextPath}/resources/assets/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
			<script type="text/javascript"
				src="${pageContext.request.contextPath}/resources/assets/chosen-bootstrap/chosen.jquery.min.js"></script>
			<script type="text/javascript"
				src="${pageContext.request.contextPath}/resources/assets/clockface/js/clockface.js"></script>
			<script type="text/javascript"
				src="${pageContext.request.contextPath}/resources/assets/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
			<script type="text/javascript"
				src="${pageContext.request.contextPath}/resources/assets/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
			<script type="text/javascript"
				src="${pageContext.request.contextPath}/resources/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
			<script type="text/javascript"
				src="${pageContext.request.contextPath}/resources/assets/bootstrap-daterangepicker/date.js"></script>
			<script type="text/javascript"
				src="${pageContext.request.contextPath}/resources/assets/bootstrap-daterangepicker/daterangepicker.js"></script>

			<script type="text/javascript"
				src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/jquery.validate.min.js"></script>
			<script type="text/javascript"
				src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/additional-methods.min.js"></script>
				
				
				
</body>
</html>