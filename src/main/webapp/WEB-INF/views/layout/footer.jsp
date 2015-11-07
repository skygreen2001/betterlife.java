<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
         
	<!--map-->
	<div class="g-map" id="allmap">
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=44dc0c91a39640a1216f4e23913854b6"></script>
		<script type="text/javascript">
			// 百度地图API功能
			var map = new BMap.Map("allmap");    // 创建Map实例
			map.centerAndZoom(new BMap.Point(121.428286,31.258289), 16);  // 初始化地图,设置中心点坐标和地图级别
			map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
			map.setCurrentCity("上海");          // 设置地图显示的城市 此项是必须设置的
			map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
		        // 设置地点指示图 
		        var pt = new BMap.Point(121.428286,31.258289);
			var myIcon = new BMap.Icon("/images/bd_icon_map.png", new BMap.Size(65,109));
			var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
			map.addOverlay(marker2); 
		</script>
		<div class="map-overlay"></div>
	</div>
	<div class="container"> 
		<footer>
        	<p>&copy; 周月璞写于上海 2015</p>
      	</footer>
	</div>
	
	<!-- 可选的Bootstrap主题文件（一般不使用） -->
	<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap-theme.min.css"></script>
	<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
	<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>