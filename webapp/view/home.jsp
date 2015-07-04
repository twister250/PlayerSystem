<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href='<c:url value="/view/css/default.css"/>' type="text/css">
		<script type="text/javascript" src="view/js/jquery/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="view/js/video.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){				
				$('input:radio[name=movie]').change(function(){
					$('tr.selected').removeClass('selected');
					$('input:radio[name=movie]:checked').parents('tr').addClass("selected");
					var name = $('tr.selected td:nth-child(2)').html();
					var path = $('tr.selected td:nth-child(3)').html();
					var type = $('tr.selected td:nth-child(4)').html();
					$('input:hidden[name=name]').attr('value',name);
					$('input:hidden[name=path]').attr('value',path);
					$('input:hidden[name=type]').attr('value',type);
				});
			});
		</script>
		<title>Player</title>		
	</head>
	<body>		
		<form action="/player/video" method="POST" id="form">
			<table>
				<thead>
					<tr>
						<th>Selecione</th>
						<th>Arquivo</th>
						<th>Diretório</th>
						<th>Tipo</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty entries}">
							<c:forEach items="${entries}" var="entrie">						
								<tr>						
									<td><a href="#" onclick="submitForm('${entrie.parent}','${entrie.fileName}');"><c:out value="${entrie.fileName}"></c:out></a></td>
									<td><c:out value="${entrie.fileName}"></c:out></td>
									<td><c:out value="${entrie.parent}"></c:out></td>
									<td>Pasta</td>
								</tr>
							</c:forEach>
							<input type="hidden" name="path" value="" id="inputPath"/>
						</c:when>
						<c:otherwise>
							<c:forEach items="${movies}" var="movie" varStatus="i">
								<tr>
									<td><input type="radio" name="movie" value="${movie}"></td>
									<td><c:out value="${movie.fileName}"></c:out></td>
									<td><c:out value="${movie.path.parent}"></c:out></td>
									<td><c:out value="${movie.type}"></c:out></td>									
								</tr>								
							</c:forEach>
							<input type="hidden" name="name"/>
							<input type="hidden" name="path"/>
							<input type="hidden" name="type"/>
						</c:otherwise>						
					</c:choose>
				</tbody>
			</table>
			<c:if test="${not empty movies}">
				<button type="submit" name="submit">Assistir</button>
			</c:if>			
		</form>
	</body>
</html>