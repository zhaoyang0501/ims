jQuery.ims.dept = {
	hasUpdate : false,
	hasDelete : false,
	
	beforeClick: function(treeId, treeNode) {
			var newTreeNode=$.fn.zTree.getZTreeObj("treeDept").getNodeByTId(""+treeNode.tId);
			$("#dept_pid").val(newTreeNode.id);
			//判断是否是根节点
			parentName="";
			if(newTreeNode.level!=0){
				parentName=newTreeNode.getParentNode().name;
			}
			$.ims.dept.refreshRigthTable(newTreeNode.id,newTreeNode.name,parentName,newTreeNode.permission);
			return true;
		},
	dept_form:null,
	toBeDeleteId:"",
	setting_tree : {
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					beforeClick: function(treeId, treeNode) {
						var newTreeNode=$.fn.zTree.getZTreeObj("treeDept").getNodeByTId(""+treeNode.tId);
						$("#dept_pid").val(newTreeNode.id);
						//判断是否是根节点
						parentName="";
						if(newTreeNode.level!=0){
							parentName=newTreeNode.getParentNode().name;
						}
						$.ims.dept.refreshRigthTable(newTreeNode.id,newTreeNode.name,parentName,newTreeNode.permission);
						return true;
					}
				}
	},
	
	/**点击创建子节点按钮*/
	showCreateModal: function(){
		if($("#dept_pid").val()==''){
			noty({"text":"请选择父部门!","layout":"top","type":"error","timeout":"2000"});
			return ;
			}
		$("#dept_id").val("");
		$("#dept_name").val("");
		$("#dept_permission").val("");
		$("#dept_operation").val("save");
		$.ims.common.setchosenvalue("manager","");
		$('#createModal').modal("show");
	},
	/**点击编辑按钮*/
	showUpdateModal :function(id){
		$("#dept_operation").val("update");
		$.ajax({
			url: $.ims.getContextPath()+"/sysconfig/dept/querybyid/"+id,
			dataType: "json",
			success: function(json){		
					$("#dept_name").val(json.name);
					$("#dept_id").val(json.id);
					$("#dept_permission").val(json.permission);
					$.ims.common.setchosenvalue("manager",json.manager);
					$('#createModal').modal("show");
				}
		});	
	},
	/**保存或者修改部门信息*/
	saveOrUpdateDept: function(){
		if(!$.ims.dept.dept_form.form()) return ;
		var treeObj=$.fn.zTree.getZTreeObj("treeDept");
		var dept_name=$("#dept_name").val();
		var managerId=$("#manager").val();
		if($("#dept_operation").val()=='save'){
			 $.ajax({
				 	type: "post",
					url: $.ims.getContextPath()+"/sysconfig/dept/save",
					dataType: "json",
					data: {
						name:$("#dept_name").val(),
						pId:$("#dept_pid").val(),
						permission:$("#dept_permission").val(),
						manager:managerId
					},
					success: function(json){
						$('#createModal').modal("hide");
						if (json == true){
							noty({"text":"新增部门成功!","layout":"top","type":"success","timeout":"2000"});
							var selectNodeId=treeObj.getSelectedNodes()[0].id;
							var node=treeObj.getSelectedNodes()[0];
							$.ims.dept.loadTree();
							treeObj.selectNode(node);
							$.ims.dept.beforeClick(selectNodeId, node);
						}
						else{
							noty({"text":"该部门名称已经存在!","layout":"top","type":"error","timeout":"2000"});
						}
					}
				});	
		}else{
			 $.ajax({
					type: "post",
					url: $.ims.getContextPath()+"/sysconfig/dept/update",
					dataType: "json",
					data: {
						id:$("#dept_id").val(),
						name:$("#dept_name").val(),
						permission:$("#dept_permission").val(),
						manager:managerId
					},
					success: function(json){
						$('#createModal').modal("hide");
						if (json == true){
							noty({"text":"部门修改成功!","layout":"top","type":"success","timeout":"2000"});
							var selectNodeId=treeObj.getSelectedNodes()[0].id;
							var node=treeObj.getSelectedNodes()[0];
							$.ims.dept.loadTree();
							treeObj.selectNode(node);
							$.ims.dept.beforeClick(selectNodeId, node);
						}
						else{
							noty({"text":"该部门名称已经存在!","layout":"top","type":"error","timeout":"2000"});
						}
					}
				});	
		}
	},
	/**刷新右侧表格*/
	refreshRigthTable :function(id,name,parentName,permission){
		var hasUpdate = this.hasUpdate;
		var hasDelete = this.hasDelete;
		$.ajax({
			url :$.ims.getContextPath()+"/sysconfig/dept/querybypid/"+id,
			success : function(json) {
				$("#dept_table").html("");
				var html;
				//如果是根节点则不添加；否则添加节点信息至右侧列表
				if(parentName==""){
					html="";
				}else{
					var uname = "";
					$.ajax({
						url: $.ims.getContextPath()+"/sysconfig/dept/querybyid/"+id,
						dataType: "json",
						async:false,
						success: function(json){		
								uname=json.managerName;
							}
					});	
					html="<tr>"+
//						"<td>"+id+"</td>"+
						"<td>"+name+"</td>"+
						"<td>"+uname+"</td>"+
						"<td>"+parentName+"</td>"+
						"<td>"+(permission==null?"":permission)+"</td>"+
						"<td class=\"center\">"+
						'<div class="btn-group">'+
						'<a class="btn2 btn-info" onclick=""><i class="icon-zoom-in"></i> 操 作</a>'+
						'<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>'+
						'<ul class="dropdown-menu">'+
					  	"	<li><a onclick=\"$.ims.dept.showUpdateModal('"+id+"')\"> <i class=\"icon-edit icon-white\"> </i> 编辑 </a></li> "+
					  	"	<li><a onclick=\"$.ims.dept.deleteDept("+id+")\" > <i class=\"icon-trash icon-white\"> </i> 删除 </a></li> "+
						'</ul></div>'+
					  	"</td>  "+
						"</tr>";
				}
				$("#dept_table").append(html);
				for (var i = 0; i < json.length; i++) {
					column="<tr>"+
//						"<td>"+json[i].id+"</td>"+
						"<td>"+json[i].name+"</td>"+
						"<td>"+json[i].managerName+"</td>"+
						"<td>"+json[i].parentName+"</td>"+
						"<td>"+(json[i].permission==null?"":json[i].permission)+"</td>"+
						"<td class=\"center\">"+
						'<div class="btn-group">'+
						'<a class="btn2 btn-info" onclick=""><i class="icon-zoom-in"></i> 操 作</a>'+
						'<a class="btn2 dropdown-toggle" data-toggle="dropdown" onclick=""><span class="icon-caret-down"></span></a>'+
						'<ul class="dropdown-menu">'+
					  	"	<li><a onclick=\"$.ims.dept.showUpdateModal('"+json[i].id+"')\"> <i class=\"icon-edit icon-white\"> </i> 编辑 </a></li> "+
					  	"	<li><a onclick=\"$.ims.dept.deleteDept("+json[i].id+")\" > <i class=\"icon-trash icon-white\"> </i> 删除 </a></li> "+
					  	'</ul></div>'+
					  	"</td>  "+
						"</tr>";
					$("#dept_table").append(column);
				}
			}
		});
	},
	/**加载树*/
	loadTree:function (){
		$.ajax({
			async:false,
		    url: $.ims.getContextPath()+"/sysconfig/dept/queryall",
		    dataType: "json",
		    success: function(data){
		    	$.fn.zTree.init($("#treeDept"), $.ims.dept.setting_tree, data).expandAll(true);
		    }
		});
	},
	/**点击删除按钮*/
	showDeleteModal: function(id){
		toBeDeleteId=id;
		$('#deleteModal').modal("show");
	},
	/**删除部门*/
	deleteDept: function(id){
		toBeDeleteId=id;
		bootbox.confirm( "是否确认删除？", function (result) {
            if(result){
            			var treeObj=$.fn.zTree.getZTreeObj("treeDept");
		  			  $.ajax({
		  				type: "post",
		  				url: $.ims.getContextPath()+"/sysconfig/dept/delete/"+toBeDeleteId,
		  				dataType: "json",
		  				success: function(json){		
		  					if (json == true){
		  						var notiJsonStr = "{\"text\":\"删除部门成功!\",\"layout\":\"top\",\"type\":\"success\",\"timeout\":\"2000\"}";
		  						noty(JSON.parse(notiJsonStr));
		  						$('#deleteModal').modal("hide");
		  						var node=treeObj.getSelectedNodes()[0];
		  						var selectNodeId=node.id;
		  						if(toBeDeleteId != selectNodeId){
		  							treeObj.selectNode(node);
		  							$.ims.dept.beforeClick(selectNodeId, node);
		  						}else{
		  							$("#dept_table").empty();
		  						}
		  						$.ims.dept.loadTree();
		  					}else{
		  						noty({"text":"该部门下有子部门不允许删除!","layout":"top","type":"error","timeout":"2000"});
		  					}
		  				}
		  			});	  
            }
        });
	}
};
