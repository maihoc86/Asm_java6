


var randomScalingFactor = function() { return Math.floor(Math.random() * 100000) };

var lineChartData = {
	labels: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7","Tháng 8","Tháng 9","Tháng 10","Tháng 11","Tháng 12"],
	datasets: [
		{
			label: "Doanh thu trước",
			fillColor: "rgba(220,220,220,0.2)",
			strokeColor: "rgba(220,220,220,1)",
			pointColor: "rgba(220,220,220,1)",
			pointStrokeColor: "#fff",
			pointHighlightFill: "#fff",
			pointHighlightStroke: "rgba(220,220,220,1)",
			data: [randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor()]
		},
		{
			label: "Doanh thu sau",
			fillColor: "rgba(48, 164, 255, 0.2)",
			strokeColor: "rgba(48, 164, 255, 1)",
			pointColor: "rgba(48, 164, 255, 1)",
			pointStrokeColor: "#fff",
			pointHighlightFill: "#fff",
			pointHighlightStroke: "rgba(48, 164, 255, 1)",
			data: [randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor()]
		}
	]

}

var barChartData = {
	labels: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7","Tháng 8","Tháng 9","Tháng 10","Tháng 11","Tháng 12"],
	datasets: [
		{
			fillColor: "rgba(220,220,220,0.5)",
			strokeColor: "rgba(220,220,220,0.8)",
			highlightFill: "rgba(220,220,220,0.75)",
			highlightStroke: "rgba(220,220,220,1)",
			data: [randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor()]
		},
		{
			fillColor: "rgba(48, 164, 255, 0.2)",
			strokeColor: "rgba(48, 164, 255, 0.8)",
			highlightFill: "rgba(48, 164, 255, 0.75)",
			highlightStroke: "rgba(48, 164, 255, 1)",
			data: [randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor()]
		}
	]

}

var pieData = [
	{
		value: 300,
		color: "#30a5ff",
		highlight: "#62b9fb",
		label: "Quần/Áo"
	},
	{
		value: 50,
		color: "#ffb53e",
		highlight: "#fac878",
		label: "Đồng hồ"
	},
		{
		value: 100,
		color: "#3f7f00",
		highlight: "#346603",
		label: "Dây chuyền"
	},
	{
		value: 400,
		color: "#7f00ff",
		highlight: "#360070",
		label: "Dây nịt"
	},
	{
		value: 100,
		color: "#1ebfae",
		highlight: "#3cdfce",
		label: "Nón"
	},
	{
		value: 120,
		color: "#f9243f",
		highlight: "#f6495f",
		label: "Giày"
	}

];

var doughnutData = [
	{
		value: 300,
		color: "#30a5ff",
		highlight: "#62b9fb",
		label: "Quần/Áo"
	},
	{
		value: 50,
		color: "#ffb53e",
		highlight: "#fac878",
		label: "Đồng hồ"
	},
		{
		value: 100,
		color: "#3f7f00",
		highlight: "#346603",
		label: "Dây chuyền"
	},
	{
		value: 400,
		color: "#7f00ff",
		highlight: "#360070",
		label: "Dây nịt"
	},
	{
		value: 100,
		color: "#1ebfae",
		highlight: "#3cdfce",
		label: "Nón"
	},
	{
		value: 120,
		color: "#f9243f",
		highlight: "#f6495f",
		label: "Giày"
	}

];

var radarData = {
	labels: ["Quần/Áo", "Đồng hồ", "Phụ kiện", "Giày", "Dép", "Nón", "Dây chuyền","Dây nịt"],
	datasets: [
		{
			label: "My First dataset",
			fillColor: "rgba(220,220,220,0.2)",
			strokeColor: "rgba(220,220,220,1)",
			pointColor: "rgba(220,220,220,1)",
			pointStrokeColor: "#fff",
			pointHighlightFill: "#fff",
			pointHighlightStroke: "rgba(220,220,220,1)",
			data: [65, 59, 90, 81, 56, 55, 40,90]
		},
		{
			label: "My Second dataset",
			fillColor: "rgba(48, 164, 255, 0.2)",
			strokeColor: "rgba(48, 164, 255, 0.8)",
			pointColor: "rgba(48, 164, 255, 1)",
			pointStrokeColor: "#fff",
			pointHighlightFill: "#fff",
			pointHighlightStroke: "rgba(48, 164, 255, 1)",
			data: [28, 48, 40, 19, 96, 27, 100,30]
		}
	]
};

var polarData = [
	{
		value: 300,
		color: "#30a5ff",
		highlight: "#62b9fb",
		label: "Quần/Áo"
	},
	{
		value: 50,
		color: "#ffb53e",
		highlight: "#fac878",
		label: "Đồng hồ"
	},
		{
		value: 100,
		color: "#3f7f00",
		highlight: "#346603",
		label: "Dây chuyền"
	},
	{
		value: 400,
		color: "#7f00ff",
		highlight: "#360070",
		label: "Dây nịt"
	},
	{
		value: 100,
		color: "#1ebfae",
		highlight: "#3cdfce",
		label: "Nón"
	},
	{
		value: 120,
		color: "#f9243f",
		highlight: "#f6495f",
		label: "Giày"
	}

];
