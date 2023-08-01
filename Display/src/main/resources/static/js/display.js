var chart_options = {
    scales: {
        xAxes: [{
            type: 'time', time: {
                unit: 'minute'
            }
        }], yAxes: [{
            scaleLabel: {
                display: true, labelString: 'Quantity of BTC'
            }, ticks: {
                beginAtZero: true
            }
        }]
    }
};

var sell_chart_ctx = document.getElementById("sell_chart").getContext("2d");
var buy_chart_ctx = document.getElementById("buy_chart").getContext("2d");

var sell_chart = new Chart(sell_chart_ctx, {
    type: "bar", data: {
        datasets: [{
            label: 'Sell Quantity', // 이름을 설정합니다.
            backgroundColor: "#ad3d3c", borderColor: "#ad3d3c", borderWidth: 0, data: []
        }]
    }, options: chart_options
});

var buy_chart = new Chart(buy_chart_ctx, {
    type: "bar", data: {
        datasets: [{
            label: 'Buy Quantity', // 이름을 설정합니다.
            backgroundColor: "#2e8b57", borderColor: "#2e8b57", borderWidth: 0, data: []
        }],
    }, options: chart_options
});

var api = document.getElementById("data_api_url").value;

var refreshDataset = function () {
    fetch(api).then(response => {
        if (response.status == 200) {
            response.json().then(dataset => {
                buy_chart.data.datasets[0].data = dataset.buy;
                sell_chart.data.datasets[0].data = dataset.sell;
                sell_chart.update();
                buy_chart.update();
            });
        } else {
            console.warn("STATUS CODE: " + response.status);
        }
    }).catch(console.error);
}

refreshDataset();
setInterval(refreshDataset, 5000);