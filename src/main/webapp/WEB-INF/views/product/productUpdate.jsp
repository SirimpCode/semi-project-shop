<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"></jsp:include>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<!-- jQuery UI CSS -->
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">

<!-- jQuery UI JS -->
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
<style type="text/css">

.fileList {
    display: flex;
    align-items: center;
    justify-content: space-between;
    background-color: #fff;
    border: 1px solid #ddd;
    padding: 8px 12px;
    margin-top: 8px;
    border-radius: 6px;
}
.fileList span.fileName {
    font-size: 14px;
    color: #333;
}


#formContainer {
    width: 80%;
    margin: 80px auto;        
    padding: 40px 30px;
    background-color: #fff;
    box-shadow: 0 4px 16px rgba(0,0,0,0.1);
    border-radius: 12px;
    box-sizing: border-box;
}


#tblProdInput {
    width: 80%;
    border-collapse: collapse;
}


.prodInputName {
    font-weight: bold;
    padding: 15px 10px;
    vertical-align: top;
    background-color: #f5f5f5;
    border-bottom: 1px solid #ddd;
}


#tblProdInput td {
    padding: 15px 10px;
    border-bottom: 1px solid #eee;
}


input[type="text"],
input[type="file"],
select,
textarea {
    width: 100%;
    padding: 10px;
    font-size: 14px;
    border: 1px solid #ccc;
    border-radius: 6px;
    box-sizing: border-box;
}


textarea {
    resize: vertical;
    height: 120px;
}


#fileDrop {
    width: 100%;
    height: 100px;
    background-color: #f8f9fa;
    border: 2px dashed #ccc;
    border-radius: 8px;
    margin-top: 10px;
    padding: 20px;
    text-align: center;
    color: #777;
    font-size: 14px;
    transition: background-color 0.3s ease;
    overflow-y: auto;
}

#fileDrop:hover {
    background-color: #eef3f7;
    border-color: #999;
    color: #444;
}


.btn-wrapper {
    text-align: center;
    margin-top: 40px;
}
#fileDrop {
    width: 100%;
    height: 200px;
    background-color: #f8f9fa;
    border: 2px dashed #ccc;
    border-radius: 8px;
    margin-top: 10px;
    padding: 20px;
    text-align: center;
    color: #777;
    font-size: 14px;
    transition: background-color 0.3s ease;
}

#fileDrop:hover {
    background-color: #eef3f7;
    border-color: #999;
    color: #444;
}
.delete:hover{
	cursor: pointer;
}
</style>

<script type="text/javascript">


$(function() {

	
    $('span.error').hide();
	let file_arr = [];
	let file_arr_copy = [];
    let hasNewImage = $("input[name='product_contents_img']")[0].files.length > 0;
    let hasOldImage = $("input[name='originContents']").val()?.trim() !== "";
    let originImage = $("input[name='originContents']").val()?.trim();

    
    let hasNewThumbnail = $("input[name='pimage1']")[0].files.length > 0;
    let hasOldThumbnail = $("input[name='originThumbnail']").val()?.trim() !== "";
    let originThumbnail = $("input[name='originThumbnail']").val()?.trim();

    // 수량 스피너
    $("input#spinnerPqty").spinner({
        spin: function(event, ui) {
            if (ui.value > 100) {
                $(this).spinner("value", 100);
                return false;
            } else if (ui.value < 1) {
                $(this).spinner("value", 1);
                return false;
            }
        }
    });

    // 드래그앤드롭 이벤트 등록
    $('div#fileDrop')
        .on("dragenter", function(e) {
            e.preventDefault();
            e.stopPropagation();
        })
        .on("dragover", function(e) {
            e.preventDefault();
            e.stopPropagation();
            $(this).css("background-color", "#ffd8d8");
        })
        .on("dragleave", function(e) {
            e.preventDefault();
            e.stopPropagation();
            $(this).css("background-color", "#fff");
        })
        .on("drop", function(e) {
            e.preventDefault();
            e.stopPropagation();
            $(this).css("background-color", "#fff");

            const files = e.originalEvent.dataTransfer.files;


            
            if (files && files.length > 0) {
                for (let i = 0; i < files.length; i++) {
                    const f = files[i];
                    const fileType = f.type;
                    let fileSize = f.size / 1024 / 1024; // MB 단위

                    if (!(fileType === 'image/jpeg' || fileType === 'image/png')) {
                        alert("JPEG 또는 PNG 형식만 업로드 가능합니다.");
                        return;
                    } else if (fileSize >= 10) {
                        alert("10MB 미만 파일만 업로드 가능합니다.");
                        return;
                    } else {
                        file_arr.push(f);
                    }
                    
                    $('div#fileDrop').empty();
                    
                    for(let imgfile of file_arr){
                    	const fileName = imgfile.name;
                    	
	                  	const v_html = 
	                	   `<div class='fileList'>
	                	     <span class='fileName'>\${fileName}</span>
	                	     <span class='delete'>&times;</span>
	                	     <span class='clear'></span>
	                	   </div>`;
	                 	$(this).append(v_html); 
	                    
                    } // end of  for(let imgfile of file_arr){} --------------
                    
                }

            }
            file_arr_copy = file_arr;
            
            if (typeof file_arr_copy === "undefined") {
                file_arr_copy = [];
            }

        });
    
    	$(document).on('click','span.delete',function(e){
    	    const $target = $(e.target);
    	    const type = $target.data('type');
    	    const filename = $target.data('filename');
    		
    		if(type === 'existing'){
    			
    			$.ajax({
    				url:"${pageContext.request.contextPath}/prod/deleteimg",
    				type:"post",
    				data:{"filename":filename, "product_id":"${pvo.product_id}"},
    				dataType:"json",
    				success:function(json){
    	    			$(e.target).parent().remove();
    				},
                  	error: function(request, status, error){
               		   	alert("메롱");
                       	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
                  	}
    				
    			}); 
    			

    		}
    		else{
        	const idx = $('span.delete').index($(e.target));
        	      	
            file_arr_copy.splice(idx, 1);	        
	        $(e.target).parent().remove();
    		}
    	});
    	
    	$(document).on("click","input:button[id='btnUpdate']",function(e){
    	    console.log(hasOldThumbnail);
    		let is_infoData_OK = true;
	    	   
	    	  $('span.error').hide();
	    	  
	    	   $('.infoData').each(function(index, elmt){
	    		   const val = $(elmt).val().trim();
	    		   const inputType = $(elmt).attr("type");
	    		   
    		    
	    		    if (!hasNewImage && !hasOldImage) {
	    		        $("input[name='product_contents_img']").next().show(); // 에러 메시지 출력
	    		        is_infoData_OK = false;
	    		        return false;
	    		    }
	    		    
	    		    if (!hasNewThumbnail && !hasOldThumbnail) {
	    		        $("input[name='pimage1']").next().show(); // 에러 메시지 출력
	    		        is_infoData_OK = false;
	    		        return false;
	    		    }
	    		    
	    		    else{
	    		   		if(val == "") {
		    			   $(elmt).next().show();
		    			   is_infoData_OK = false;
		    			   return false; // forEach 는 break(중단) 기능이 없으나, each 는 있다.
		    			   
		    		   		}
	    		   		}
	    	   });    		
    		
	    	  	  if(is_infoData_OK) {
	    		   
	    	  		var formData = new FormData($("form[name='prodInputFrm']").get(0));
	    		   
	    	  		if (!hasNewImage && originImage != "") {
	    	  		    formData.append("product_contents_img", "${pvo.product_contents}");
	    	  		}
	    	  		
	    	  		if (!hasNewThumbnail && originThumbnail != "") {
	    	  		    formData.append("pimage1", "${thumbnail}");
	    	  		}
	    	  		
	    	  		for(let i = 0; i < file_arr_copy.length; i++) {
	    	  			formData.append("files", file_arr_copy[i]);
	    	  		}

	    	  		
	    	  		$.ajax({
				    	  url:"${pageContext.request.contextPath}/prod/updateProdInfoProdImg",
				    	  type:"post",
				    	  data:formData,
				    	  processData:false,  // 파일 전송시 설정 
		                  contentType:false,  // 파일 전송시 설정
		                  dataType:"json",
		                  success:function(json){
		                	  alert("수정완료");
		                	  location.href="${pageContext.request.contextPath}/product/detail/${pvo.product_id}"
		                	  
		                  },
		                  error: function(request, status, error){
		                	   //alert("메롱");
		                       alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		                  }
				       });
	    	   } 
	    	  
	    	   
    		
    	});
    	
    
    
});

</script>

<div align="center" style="margin-bottom: 20px;">

    <div style="border: solid green 2px; width: 250px; margin-top: 20px; padding: 10px 0; border-left: hidden; border-right: hidden;">
        <span style="font-size: 15pt; font-weight: bold;">제품수정&nbsp;[관리자전용]</span>
    </div>
    <br/>

 
    <form name="prodInputFrm" enctype="multipart/form-data">
		<input type="hidden" name="product_id" value="${pvo.product_id}"/>
        <table id="tblProdInput" style="width: 80%;">
            <tbody>
                <tr>
                    <td width="25%" class="prodInputName" style="padding-top: 10px;">카테고리</td>
                    <td width="75%" align="left" style="padding-top: 10px;">
						<select name="category_id" class="infoData">
						    <option value="" >:::선택하세요:::</option>
						    <c:forEach var="cvo" items="${cateList}">
						        <option  value="${cvo.category_id}" selected="${pvo.category_id}"> 
						            ${cvo.category_name}
						        </option>
						    </c:forEach>
						</select>
                        <span class="error">필수입력</span>
                    </td>
                </tr>

                <tr>
                    <td class="prodInputName">제품명 </td>
                    <td align="left">
                        <input type="text" style="width: 300px;" name="product_name" class="box infoData" value="${pvo.product_name}" />
                        <span class="error">필수입력</span>
                    </td>
                </tr>
                
				<tr>
				    <td class="prodInputName">소재 / 사이즈</td>
				    <td>
				        <div style="display: flex; gap: 10px;">
				            <div style="flex: 1;">
				                <input type="text" style="width: 100%;" name="metter" class="box infoData" placeholder="소재 입력" value="${pvo.metter }"/>
				                <span class="error">필수입력</span>
				            </div>
				            <div style="flex: 1;">
				                <input type="text" style="width: 100%;" name="product_size" class="box infoData" placeholder="사이즈 입력" value="${pvo.product_size}"/>
				                <span class="error">필수입력</span>
				            </div>
				        </div>
				    </td>
				</tr>               
                

                <tr>
                    <td class="prodInputName">제품이미지</td>
                    <td align="left">
                    	<c:if test="${not empty thumbnail}">
						    <div id="existingProductThumbnail">
						        <p>기존 파일:</p>
						        <img src="${pageContext.request.contextPath}${thumbnail}" 
						             style="width: 150px; height: auto;" />
						        <input type="hidden" name="originThumbnail" value="${thumbnail}" />
						    </div>
						</c:if>
                        <input type="file" name="pimage1" class="img_file" accept="image/*"/>
                        <span class="error">필수입력</span>
                    </td>
                </tr>

                <tr>
                    <td class="prodInputName" style="padding-bottom: 10px; text-align: left;">추가이미지파일(선택)</td>
                    <td>
                        <span style="font-size: 10pt;">파일을 1개씩 마우스로 끌어 오세요</span>
                        <div id="fileDrop" class="fileDrop border border-secondary">
							<c:if test="${not empty imageList}">
							    <c:forEach var="img" items="${imageList}">
							        <div class="fileList">
							            <img src="${pageContext.request.contextPath}${img}" alt="미리보기" style="max-height: 100px; margin-right: 10px;">
							            <span class="delete" data-type="existing" data-filename="${img}">&times;</span>
							        </div>
							    </c:forEach>
							</c:if>
                        </div>
                       
                    </td>
                </tr>

                <tr>
                    <td class="prodInputName">제품수량</td>
                    <td align="left">
                        <input id="spinnerPqty" name="stock" value="${pvo.stock }" style="width: 50px; height: 30px;" /> 개
                        <span class="error">필수입력</span>
                    </td>
                </tr>

                <tr>
                    <td class="prodInputName">제품정가</td>
                    <td align="left">
                        <input type="text" style="width: 100px;" name="price" class="box infoData" value="${pvo.price}"/> 원
                        <span class="error">필수입력</span>
                    </td>
                </tr>

                <tr>
                    <td class="prodInputName">제품설명</td>
                    <td align="left">
                        <input type="text" name="product_info" class="box infoData" value="${pvo.product_info }">
                        <span class="error">필수입력</span>
                    </td>
                </tr>



                <tr>
                    <td class="prodInputName">제품상세설명</td>
                    <td align="left">
						<c:if test="${not empty pvo.product_contents}">
						    <div id="existingProductContents">
						        <p>기존 파일:</p>
						        <img src="${pageContext.request.contextPath}${pvo.product_contents}" 
						             style="width: 150px; height: auto;" />
						        <input type="hidden" name="originContents" value="${pvo.product_contents}" />
						    </div>
						</c:if>
						
						<!-- 새로운 파일 업로드 input -->
						<p>새 파일 선택 (선택 시 기존 파일은 무시됩니다):</p>
						<input type="file" name="product_contents_img" class="product_contents_img" accept="image/*" />
                    </td>
                </tr>

                <tr style="height: 70px;">
                    <td colspan="2" align="center" style="border: none; padding: 50px 0;">
                        <input type="button" value="제품수정" id="btnUpdate" style="width: 120px;" class="btn btn-info btn-lg mr-5" />
                        <input type="reset" value="취소" style="width: 120px;" class="btn btn-danger btn-lg" />
                    </td>
                </tr>
            </tbody>
        </table>

    </form>
</div>

	<jsp:include page="../include/footer.jsp"></jsp:include>