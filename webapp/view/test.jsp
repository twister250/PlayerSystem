<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0">
	<jsp:directive.page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" />
	<jsp:output
		doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
		omit-xml-declaration="true" />
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
			<link rel="stylesheet" href='<c:url value="/view/css/default.css"/>' type="text/css"/>
			<title>Player</title>
		</head>
		<body>		
			<table>
				<thead>
					<tr>
						<th>Pasta</th>		<!--	getFileName		-->
						<th>Diretório</th>	<!--	getParent		-->
						<th>Teste</th>	<!--	getParent		-->
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty entries}">
							<c:forEach items="${entries}" var="e">
								<tr>
									<td><a href="/player/home?path=${e.parent}/${e.fileName}"><c:out value="${e.fileName}"></c:out></a></td>
									<td><c:out value="${e.parent}"></c:out></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach items="${files}" var="f" >
								<c:set property="d" value="d"/>
								<tr>
									<td><a href="/player/video?media=${f.fileName}&path=${f.parent}"><c:out value="${f.fileName}"></c:out></a></td>								
									<td><c:out value="${f.parent}"></c:out></td>								
									<td><c:out value="${test}"></c:out></td>								
									<td><c:out value="${test.lastIndexOf(d)}"></c:out></td>								
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>								
				</tbody>
			</table>
		</body>
	</html>
</jsp:root>