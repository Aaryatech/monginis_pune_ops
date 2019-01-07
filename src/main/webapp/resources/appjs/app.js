jQuery(document).ready(function($){

	 var
	  form = $('.form'),
	  cache_width = form.width(),
	  a4 = [595.28, 841.89]; // for a4 size paper width and height

	 $('#create_pdf').on('click', function() {
			 
	  $('body').scrollTop(0);
	  createPDF2();
	 });
	 //create pdf
//	 function createPDF() {
//	  getCanvas().then(function(canvas) {
//	   var
//	    img = canvas.toDataURL("image/png"),
//	    doc = new jsPDF({
//	     unit: 'px',
//	     format: 'a4'
//	    });
//	   doc.addImage(img, 'JPEG', 2, 2);
//	 //  window.open(doc.output('bloburl'), '_blank');
//	   
////	doc.save();
////	   form.width(cache_width);
//	  });
//	 }

	 // create canvas object
	 function getCanvas() {
	  form.width((a4[0] * 1.33333) - 80).css('max-width', 'none');
	  return html2canvas(form, {
	   imageTimeout: 2000,
	   removeContainer: true
	  });
	 }
	 
	 
	 function createPDF2() {
		  getCanvas().then(function(canvas) {
			  var doc = new jsPDF("landscape", "mm", "a4");
			  
			  var
			    img = canvas.toDataURL("image/png");
			  
		
			  doc.addImage(img,'JPEG',5,5);
//			  doc.text(20, 20, 'Example 2');
			//  doc.autoPrint();
			  window.open(doc.output('bloburl'));
		  });
		 }
	 
	 
});