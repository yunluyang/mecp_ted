$(document).ready(function(){
	$("tbody tr:odd").css("background-color","#EEEEEE");
    trEdit();//td的点击事件封装成一个函数
 	});


 
//我们需要找到所有的单元格
function trEdit(){
  var numTd = $("tbody td");
	//给这些单元格注册鼠标点击的事件
	numTd.click(function() {	
		//找到当前鼠标点击的td,this对应的就是响应了click的那个td
		var tdObj = $(this);
		var text = tdObj.html(); 
		if (tdObj.children("input").length > 0) {
			//当前td中input，不执行click处理
			return false;
		}
		tdObj.html("");
		//创建一个文本框
		//去掉文本框的边框
		//设置文本框中的文字字体大小是12px
		//使文本框的宽度和td的宽度相同
		//设置文本框的背景色
		//需要将当前td中的内容放到文本框中
		//将文本框插入到td中
		var inputObj = $("<input type='text' class='layui-input'>").css({"font-size":"25px","text-align":"center"})
			.width(tdObj.width())
			.height(tdObj.height()) 
			.val(text).appendTo(tdObj);
		
		//处理文本框上回车和esc按键的操作
		inputObj.keyup(function(event){
			//获取当前按下键盘的键值
			var keycode = event.which;
			//处理回车的情况
			if (keycode == 13) {
				//获取当当前文本框中的内容
				var inputtext = $(this).val();
				//将td的内容修改成文本框中的内容
				tdObj.html(inputtext);
			}
			//处理esc的情况
			if (keycode == 27) {
				//将td中的内容还原成text
				tdObj.html(text);
			}
		});
	});
}


