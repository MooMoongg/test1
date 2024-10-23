<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
<meta charset="EUC-KR">

<title>
	<c:if test="${menu.equals('manage')}">
		��ǰ����
	</c:if>
	<c:if test="${!menu.equals('manage')}">
		��ǰ �����ȸ
	</c:if>
</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
var menu = "${menu}";

function fncGetProductList(currentPage) {
    $("#currentPage").val(currentPage);
    $("form").attr("method", "POST").attr("action", "/product/listProduct?menu=" + menu).submit();
}

$(function() {
    $("td.ct_btn01:contains('�˻�')").on("click", function() {
        fncGetProductList(1);
    });

    $(".ct_list_pop td:nth-child(3)").on("click", function() {
        if(menu === 'manage') {
            self.location = "/product/updateProductView?prodNo=" + $(this).text().trim();
        } else {
            var prodNo = $(this).text().trim();
            $.ajax({
                url: "/product/json/getProduct/" + prodNo,
                method: "GET",
                dataType: "json",
                headers: {
                    "Accept": "application/json",
                    "Content-Type": "application/json"
                },
                success: function(JSONData, status) {
                    var displayValue = "<h3>"
                        + "��ǰ��ȣ : " + JSONData.prodNo + "<br/>"
                        + "��ǰ�� : " + JSONData.prodName + "<br/>"
                        + "���� : " + JSONData.price + "<br/>"
                        + "��ǰ������ : " + JSONData.prodDetail + "<br/>"
                        + "����� : " + JSONData.regDateString + "<br/>"
                        + "</h3>";

                    $("h3").remove();
                    $("#" + prodNo).html(displayValue);
                }
            });
        }
    });

    $(".ct_list_pop td:nth-child(3)").css("color", "red");
    $("h7").css("color", "red");
    $(".ct_list_pop:nth-child(4n+6)").css("background-color", "whitesmoke");
});
</script>

</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/product/listProduct?menu=${menu}" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
						<c:if test="${menu.equals('manage')}">
							��ǰ����
						</c:if>
						<c:if test="${!menu.equals('manage')}">
							��ǰ �����ȸ
						</c:if>
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37">
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value = "0" ${!empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>��ǰ��ȣ</option>
				<option value = "1" ${!empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>��ǰ��</option>
				<option value = "2" ${!empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>��ǰ����</option>
			</select>
			<input 	type="text" name="searchKeyword"  value="${!empty search.searchKeyword ? search.searchKeyword : "" }" 
							class="ct_input_g" style="width:200px; height:19px" >
		</td>

		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncProductList(1);">�˻�</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >
		��ü  ${resultPage.totalCount} �Ǽ�, ���� ${resultPage.currentPage} ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">�������</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
			
			<c:set var="i" value="0" />
	
	
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${ i+1 }" />
		<tr class="ct_list_pop">
			<td align="center">${ i }</td>
			<td></td>
			<td align="left">			
			<c:if test="${menu eq 'manage'}">
				<a href="/product/updateProduct?prodNo=${product.prodNo}">${product.prodName}</a>
			</c:if>
			<c:if test="${!(menu eq 'manage')}">
				<a  id="abc" href="/product/getProduct?prodNo=${product.prodNo}">${product.prodName}</a>
			</c:if></td>
			<td></td>
			<td align="left">${product.price}</td>
			<td></td>
			<td align="left">${product.regDate}</td>
			<td></td>
			<td align="left">�Ǹ���</td>
		</tr>
		<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</c:forEach>
			
<%-- 		<c:forEach var="product" items="${list }"  varStatus="status"> --%>
<!-- 			<tr class="ct_list_pop"> -->
<%-- 				<td align="center">${status.count}</td> --%>
<!-- 				<td></td> -->
<!-- 				<td align="left"> -->
<%-- 					<c:if test = "${product.proTranCode eq '0' || empty product.proTranCode}"> --%>
<%-- 						<a href="/product/getProduct?prodNo=${product.prodNo }&menu=${menu}">${product.prodName }</a> --%>
<%-- 					</c:if> --%>
<%-- 					<c:if test = "${!empty product.proTranCode && product.proTranCode ne '0' }"> --%>
<%-- 						${product.prodName } --%>
<%-- 					</c:if> --%>
<!-- 				</td> -->
<!-- 				<td></td> -->
<%-- 				<td align="left">${product.price }</td> --%>
<!-- 				<td></td> -->
<%-- 				<td align="left">${product.regDate }</td> --%>
<!-- 				<td></td> -->
<!-- 				<td align="left"> -->
<%-- 					<c:if test="${empty user || user.role eq 'user'}"> --%>
<%-- 						<c:choose> --%>
<%-- 							<c:when test="${empty product.proTranCode || product.proTranCode eq '0'}"> --%>
<!-- 								�Ǹ��� -->
<%-- 							</c:when> --%>
<%-- 							<c:when test="${product.proTranCode ne '0' }"> --%>
<!-- 								������� -->
<%-- 							</c:when> --%>
<%-- 						</c:choose> --%>
<%-- 					</c:if> --%>
<%-- 					<c:if test="${!empty user && user.role ne 'user' }"> --%>
<%-- 						<c:choose> --%>
<%-- 							<c:when test="${product.proTranCode eq '1'}"> --%>
<!-- 								���ſϷ� -->
<%-- 								<c:if test="${menu eq 'manage' }"> --%>
<%-- 									<a href="/product/updateTranCodeByProd?prodNo=${product.prodNo }&tranCode=2&currentPage=${ resultPage.currentPage}">����ϱ�</a> --%>
<%-- 								</c:if> --%>
<%-- 							</c:when> --%>
<%-- 							<c:when test="${product.proTranCode eq '2'}"> --%>
<!-- 								����� -->
<%-- 							</c:when> --%>
<%-- 							<c:when test="${product.proTranCode eq '3'}"> --%>
<!-- 								��ۿϷ� -->
<%-- 							</c:when> --%>
<%-- 							<c:otherwise> --%>
<!-- 								�Ǹ��� -->
<%-- 							</c:otherwise> --%>
<%-- 						</c:choose> --%>
<%-- 					</c:if>							 --%>

<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 			<td colspan="11" bgcolor="D6D7D6" height="1"></td> -->
<!-- 			</tr> -->
<%-- 		</c:forEach> --%>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
			<input type="hidden" id="currentPage" name="currentPage" value=""/>
			<jsp:include page="../common/pageNavigator.jsp"/>	
    	</td>
	</tr>
</table>
<!--  ������ Navigator �� -->
</form>
</div>

</body>
</html>