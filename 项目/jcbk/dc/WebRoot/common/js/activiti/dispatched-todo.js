/**
 * 布控流程任务办理
 */
$(function() {
    // 跟踪
    $('.trace').click(graphTrace);
    
});

/**
 * 完成任务
 * @param {Object} taskId
 */
function complete(taskId,taskProInstId, key,value,advice,conPath,withdraw,id) {
	$.blockUI({
        message: '<h2><img src="' + 'common/images/ajax/loading.gif" align="absmiddle"/>正在提交请求……</h2>'
    });
	// 发送任务完成请求
	var url = '';
	if(withdraw){
		url = '/dc/withdraw/complete.do?taskId=' + taskId+'&key='+key+'&value='+value+'&taskProInstId='+taskProInstId+'&advice='+encodeURI(encodeURI(advice))+'&id='+id;
	} else{
		url = '/dc/dispatched/complete.do?taskId=' + taskId+'&key='+key+'&value='+value+'&taskProInstId='+taskProInstId+'&advice='+encodeURI(encodeURI(advice))+'&id='+id;
	}
	
    $.post(url , function(resp) {
		$.unblockUI();
        if (resp == 'success') {
            alert('审批完成');
            if(withdraw){
            	location.replace("withdraw/"+conPath+".do");
            } else{
            	location.replace("dispatched/"+conPath+".do");
            }
        } else {
            alert('操作失败!请联系管理员');
        }
    });
}