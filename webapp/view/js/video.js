function submitForm(path, fileName){
	$('#inputPath').attr('value',path+'/'+fileName);
	$('#form').attr('action','/player/home');
	$('#form').submit();
}
