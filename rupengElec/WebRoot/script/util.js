function openWindowWithName(url,width,height,name) {
  var left=(screen.availWidth-width)/2;
  var top=(screen.availHeight-height)/2;
  var ref="";
  ref += "width="+width+"px,height="+height+"px,";
  ref += "left="+left+"px,top="+top+"px,";
  ref += "resizable=yes,scrollbars=yes,status=yes,toolbar=no,systemmenu=no,location=no,borderSize=thin";//channelmode,fullscreen
  var childWindow = window.open(url,name,ref,false);
  childWindow.focus();
}

function  openWindow(url,width,height){
  openWindowWithName(url,width,height,'newwindow');
}




//格式化日期函数
function formatDate(date ,pattern){
    if(!pattern){
	    pattern="yyyy-MM-dd";
    }
	var o = {
		'M+' : date.getMonth() + 1, //月份\n"
		'd+' : date.getDate(), //日 \n"
		'h+' : date.getHours(), //小时\n"
		'm+' : date.getMinutes(), //分 \n"
		's+' : date.getSeconds(), //秒 \n"
		'S' : date.getMilliseconds()
	};
	//替换填充年份
	if (/(y+)/.test(pattern)) {
		pattern = pattern.replace(RegExp.$1, (date.getFullYear() + '')
				.substr(4 - RegExp.$1.length));
	}
	//填充替换剩余的时间元素
	for ( var key in o){
		if (new RegExp('(' + key + ')').test(pattern)){
			pattern = pattern.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[key])
					: (('00' + o[key]).substr(('' + o[key]).length)));
		}
	}
	//返回格式化结果
    return pattern;	
}

