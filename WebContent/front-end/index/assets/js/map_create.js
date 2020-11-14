        //設定寬高
        var width = 600,
            height = 600;

        // 座標變換函式,設定path 
        var projection = d3.geo.mercator() //設定投影函式 
            .center([121.0, 24.0]) // 函式是用於設定地圖的中心位置，[107,31] 指的是經度和緯度。
            .scale(3000) //函式用於設定放大的比例。
            .translate([width / 2, height / 2]); //函式用於設定平移。

        var path = d3.geo.path().projection(projection);

        // 讓d3抓svg，並寫入寬高
        var svg = d3.select("#svg").attr("width", width).attr("height", height);

        //填充顏色用
        // var color = d3.scale.category20b();      

        // 讓d3抓GeoJSON檔，並寫入path的路徑
        d3.json("taiwan_map.geojson", function(error, json) {

            //GeoJSON與SVG纏繞順序(winding order)相反,需reverse !!!
            var features = json.features;
            features.forEach(function(feature) {
                if (feature.geometry.type == "MultiPolygon") {
                    feature.geometry.coordinates.forEach(function(polygon) {

                        polygon.forEach(function(ring) {
                            ring.reverse();
                        })
                    })
                } else if (feature.geometry.type == "Polygon") {
                    feature.geometry.coordinates.forEach(function(ring) {
                        ring.reverse();
                    })
                }
            })

            var j = 0; //用來區別各county超連結
            //開始寫入path的路徑
            if (error)
                return console.error(error);
            var taiwan = svg.selectAll("path")
                .data(features)
                .enter()
                .append("a")
                .attr({ // 設定id，為了click時加class用
                    href: (d) => 'mapsite.jsp?county=' +
                        j++
                })
                .append("path")
                .attr("stroke", "#FFF") //線的顏色
                .attr("stroke-width", 0.2)
                // .attr("fill", function(d, i) {
                //     return color(i);
                // })
                .attr("d", path)
                .attr({ // 設定id，為了click時加class用
                    id: (d) => 'city' + d.properties.COUNTYCODE
                })


            for (var i = 0; i < 22; i++) {
                var counties = document.getElementsByTagName("path");
                var id = counties[i].getAttribute("id") //取得縣市id名稱

                if (id === "city10017" || id === "city63000" || //北北基
                    id === "city65000") {
                    var district = svg.selectAll("#" + id)
                        .attr("fill", "#000079")
                } else if (id === "city68000" || id === "city10004" || //桃竹苗
                    id === "city10018" || id === "city10005") {
                    var district = svg.selectAll("#" + id)
                        .attr("fill", "#A23400")
                } else if (id === "city66000" || id === "city10007" || //中彰投
                    id === "city10008") {
                    var district = svg.selectAll("#" + id)
                        .attr("fill", "#737300")
                } else if (id === "city10009" || id === "city10020" || //雲嘉南澎
                    id === "city10010" || id === "city67000" ||
                    id === "city10016") {
                    var district = svg.selectAll("#" + id)
                        .attr("fill", "#467500")
                } else if (id === "city64000" || id === "city10013" //高屏
                ) {
                    var district = svg.selectAll("#" + id)
                        .attr("fill", "#336666")
                } else if (id === "city10002" || id === "city10015" || //宜花東
                    id === "city10014") {
                    var district = svg.selectAll("#" + id)
                        .attr("fill", "#6C3365")
                }
            }
        })
        //09007 連江縣 //09020 金門縣 //10002 宜蘭縣 //10004 新竹縣
        //10005 苗栗縣 //10007 彰化縣 //10008 南投縣 //10009 雲林縣
        //10010 嘉義縣 //10013 屏東縣 //10014 台東縣 //10015 花蓮縣
        //10016 澎湖縣 //10017 基隆市 //10018 新竹市 //10020 嘉義市  
        //63000 台北市 //65000 新北市 //66000 台中市 //67000 台南市
        //68000 桃園市 //64000 高雄市