<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href='<c:url value="/view/css/default.css"/>' type="text/css">
		<title>Player</title>
	</head>
	<body>		
		<h1>${movie.fileName}</h1>				
		<video width="720" height="404" controls >
  			<source src="movie?media=${movie.fileName}&path=${movie.path}" type="video/${movie.type}">
  			<track label="Portuguese" kind="captions" srclang="pt" src="movie?path=${movie.path}/sub&track=${movie.movieName}.vtt" default>
		</video>		
	</body>
</html>