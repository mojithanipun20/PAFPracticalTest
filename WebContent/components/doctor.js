
$(document).ready(function()

 {
	 $("#alertSuccess").hide();
	 $("#alertError").hide();
 });

$(document).on("click", "#btnSave", function(event) 
		{  
			$("#alertSuccess").text("");  
			$("#alertSuccess").hide();  
			$("#alertError").text("");  
			$("#alertError").hide(); 
			
			
			var status = validatedoctorForm();  
			if (status != true)  
			{   
				$("#alertError").text(status);   
				$("#alertError").show();   
				return;  
			} 
			
			var type = ($("#hiddoc_numSave").val() == "") ? "POST" : "PUT"; 
			
			$.ajax( 
			{  
				url : "DoctorAPI",  
				type : type,  
				data : $("#formdoctor").serialize(),  
				dataType : "text",  
				complete : function(response, status)  
				{   
					ondoctorSaveComplete(response.responseText, status);
				} 
			
		}); 
}); 
		
function ondoctorSaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(JSON.stringify(response)); 
		console.log(resultSet);
		
		if (status == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 
			
			$("#divdoctorsGrid").html(resultSet.data);
			location.reload();
			setTimeout(location.reload.bind(location), 1000)
			
		} else if (status.trim() == "error")   
		{    
			$("#alertError").text(status);    
			$("#alertError").show();   
		} 

		} else if (status == "error")  
		{   
			$("#alertError").text("Error while saving.");   
			$("#alertError").show();  
		} else  
		{   
			$("#alertError").text("Unknown error while saving..");   
			$("#alertError").show();  
		} 

		$("#hiddoc_numSave").val("");  
		$("#formdoctor")[0].reset(); 
		
}

$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "DoctorAPI",   
		type : "DELETE",   
		data : "doc_num=" + $(this).data("doct_num"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			ondoctorDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 


function ondoctorDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		// var resultSet = JSON.parse(response); 

		if (status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show();
			setTimeout(location.reload.bind(location), 1000)
			location.reload();
			//$("#divdoctorGrid").html(resultSet.data);   
		} else if (status.trim() == "error")   
		{    
			$("#alertError").text(status);    
			$("#alertError").show();   
		} 

			} else if (status == "error")  
			{   
				$("#alertError").text("Error while deleting.");   
				$("#alertError").show();  
			} else  
			{   
				$("#alertError").text("Unknown error while deleting.");   
				$("#alertError").show();  
			} 
	} 

$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hiddoc_numSave").val($(this).closest("tr").find('#hiddoctorIDUpdate').val());     
	$("#doc_ID").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#name").val($(this).closest("tr").find('td:eq(1)').text());     
	$("#gender").val($(this).closest("tr").find('td:eq(2)').text());     
	$("#specialist").val($(this).closest("tr").find('td:eq(3)').text()); 
}); 


function validatedoctorForm() 
{    
	if ($("#doc_ID").val().trim() == "")  
	{   
		return "Insert doctor ID.";   
	}
	  
	if ($("#name").val().trim() == "")  
	{   
		return "Insert doctor name.";  
	}
	
	if ($("#gender").val().trim() == "")  
	{   
		return "Insert doctor gender";  
	} 
  
	if ($("#specialist").val().trim() == "")  
	{   
		return "Insert doctor specialist.";
	} 
	 
	 return true;
	
}

