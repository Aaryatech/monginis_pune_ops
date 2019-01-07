var code=["#abc","#abd","#bdq","#cdq"];
var item_name=["pestries","savouries", "cake","burger"];
$(document).ready(
    function(){
		$("#barcode").autocomplete({
			source: code,
			autoFocus:true,
		});
	}
);
$(document).ready(
    function(){
		$("#item_name").autocomplete({
			source: item_name,
			autoFocus:true,
		});
	}
);