function submitForm(path){
	$('#inputPath').attr('value',path);
	$('#form').attr('action','/player/home');
	$('#form').submit();
}