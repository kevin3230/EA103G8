<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.vendor.model.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/fontawesome-5.15.1/css/all.min.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/css/bootstrap.min.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/header.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/footer.css">
<title>mapsite</title>
    <style>
body {
            background-color: #f2f2f2;
            color: #444;
        }
        .map_panel {
            top: 1px;
            margin: 1em 1em;
            width: 60%;
            height: 90vh;
            display: inline-block;
        }
        .side_panel {
            width: 33%;
            height: 90vh;
            display: inline-block;
            overflow-y: scroll;
        }
        #map {
            width: 100%;
            height: 90vh;
            margin: 0;
            border: 2px solid #a6a6a6;
            border-radius: 7px;
        }
        @media screen and (max-width: 575px){
            .map_panel {
                width: 95%;
                height: 50vh;
            }
            .side_panel{
                width: 95%;
                margin: 1em 1em;
            }
            #map{
                height: 100%;
            }
        }
        .site_list {
            margin: 0;
            width: 100%;
            overflow: hidden;
        }
        .site_list .site {
            width: 93%;
            display: inline-block;
            overflow: hidden;
            background-color: #FFF;
            border: 1px solid #c8cccf;
            border-radius: 5px;
            padding-left: 3%;
            padding-right: 2%;
        }
        a{
            color: #444;
            font-weight: 600;
        }
    </style>
</head>
<body>

<%@ include file="/front-end/index/header.jsp" %>

    <div class="map_panel">
        <div id="map"></div>
    </div>
    <div class="side_panel">
        <div class="site_list"></div>
    </div>

<%@ include file="/front-end/index/footer.jsp" %>

<script src="<%= request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/popper.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
<script src="<%= request.getContextPath()%>/front-end/index/assets/js/header.js"></script>

<script>
	       var map, geocoder;

        function initMap() {
            //建立map物件
            map = new google.maps.Map(document.getElementById('map'), {
                zoom: 11,
                styles: [{ //地圖styles
                        "featureType": "water",
                        "elementType": "geometry",
                        "stylers": [{
                                "visibility": "on"
                            },
                            {
                                "color": "#aee2e0"
                            }
                        ]
                    },
                    {
                        "featureType": "landscape",
                        "elementType": "geometry.fill",
                        "stylers": [{
                            "color": "#abce83"
                        }]
                    },
                    {
                        "featureType": "poi",
                        "elementType": "geometry.fill",
                        "stylers": [{
                            "color": "#769E72"
                        }]
                    },
                    {
                        "featureType": "poi",
                        "elementType": "labels.text.fill",
                        "stylers": [{
                            "color": "#7B8758"
                        }]
                    },
                    {
                        "featureType": "poi",
                        "elementType": "labels.text.stroke",
                        "stylers": [{
                            "color": "#EBF4A4"
                        }]
                    },
                    {
                        "featureType": "poi.park",
                        "elementType": "geometry",
                        "stylers": [{
                                "visibility": "simplified"
                            },
                            {
                                "color": "#8dab68"
                            }
                        ]
                    },
                    {
                        "featureType": "road",
                        "elementType": "geometry.fill",
                        "stylers": [{
                            "visibility": "simplified"
                        }]
                    },
                    {
                        "featureType": "road",
                        "elementType": "labels.text.fill",
                        "stylers": [{
                            "color": "#5B5B3F"
                        }]
                    },
                    {
                        "featureType": "road",
                        "elementType": "labels.text.stroke",
                        "stylers": [{
                            "color": "#ABCE83"
                        }]
                    },
                    {
                        "featureType": "road",
                        "elementType": "labels.icon",
                        "stylers": [{
                            "visibility": "off"
                        }]
                    },
                    {
                        "featureType": "road.local",
                        "elementType": "geometry",
                        "stylers": [{
                            "color": "#A4C67D"
                        }]
                    },
                    {
                        "featureType": "road.arterial",
                        "elementType": "geometry",
                        "stylers": [{
                            "color": "#9BBF72"
                        }]
                    },
                    {
                        "featureType": "road.highway",
                        "elementType": "geometry",
                        "stylers": [{
                            "color": "#EBF4A4"
                        }]
                    },
                    {
                        "featureType": "transit",
                        "stylers": [{
                            "visibility": "off"
                        }]
                    },
                    {
                        "featureType": "administrative",
                        "elementType": "geometry.stroke",
                        "stylers": [{
                                "visibility": "on"
                            },
                            {
                                "color": "#87ae79"
                            }
                        ]
                    },
                    {
                        "featureType": "administrative",
                        "elementType": "geometry.fill",
                        "stylers": [{
                                "color": "#7f2200"
                            },
                            {
                                "visibility": "off"
                            }
                        ]
                    },
                    {
                        "featureType": "administrative",
                        "elementType": "labels.text.stroke",
                        "stylers": [{
                                "color": "#ffffff"
                            },
                            {
                                "visibility": "on"
                            },
                            {
                                "weight": 4.1
                            }
                        ]
                    },
                    {
                        "featureType": "administrative",
                        "elementType": "labels.text.fill",
                        "stylers": [{
                            "color": "#495421"
                        }]
                    },
                    {
                        "featureType": "administrative.neighborhood",
                        "elementType": "labels",
                        "stylers": [{
                            "visibility": "off"
                        }]
                    }
                ]
            });

            //取得超連結各county的編號
            var search = (location.search).toString().split("=");
            var countynum = search[1];
            if (!(countynum >= 0 && countynum < 20)) {
                countynum = 3
            }
            console.log(countynum);

            //以countynum為index取得geojson的縣市名稱(countyname)
            var county;
            $.getJSON("taiwan_map.geojson", function(json) {
                var county = json.features[countynum].properties.COUNTYNAME;
                console.log(county);
                //建立geocoder物件
                geocoder = new google.maps.Geocoder();
                geocoder.geocode({ 'address': county }, function(results, status) {
                    if (status == 'OK') {
                        // 若轉換成功...
                        map.setCenter(results[0].geometry.location);
                    } else {
                        // 若轉換失敗...
                        map.setCenter(new google.maps.LatLng(23.973974, 120.979681));
                        console.log(status);
                    }
                });
            })

            var markers = [];
            $.getJSON("campSite_v2.json", function(json) {

                //建立marker物件&執行addMarker(e)
                function addMarker(e) {
                    markers[e] = new google.maps.Marker({
                        position: {
                            lat: parseFloat(json[e].lat),
                            lng: parseFloat(json[e].lon)
                        },
                        map: map,
                        icon: { //更改地圖標記樣式
                            url: 'https://www.flaticon.com/svg/static/icons/svg/2489/2489241.svg',
                            scaledSize: new google.maps.Size(35, 35)
                        }
                    });
                }

                for (var i = 0; i < json.length; i++) {
                    addMarker(i);

                    //建立infoWindow物件資訊視窗
                    var infowindow = new google.maps.InfoWindow({
                        position: {
                            lat: parseFloat(json[i].lat),
                            lng: parseFloat(json[i].lon)
                        },
                        maxWidth: 200
                    });
                    addInfoWindow(i);
                }

                //透過addListener監聽事件顯示&關閉infowindow
                function addInfoWindow(j) { //額外寫一個函式，將變數i傳入，利用區域變數的特性，將變數i鎖在區域變數作用域上
                    var a = -1;
                    markers[j].addListener('click', function() {
                        //設定infowindow開關
                        a = a * -1;
                        if (a > 0) {
                            //匯入infowindow內容
                            infowindow.setContent(`<a href="#">
                                                    <div class="cgname">` + json[j].name + `</div>
                                                    <div><span class="cgregion">地區: </span>` + json[j].region + `</div>
                                                    <div><span class="cgregion">地址: </span>` + json[j].address + `</div>
                                                    <div><span class="cglatlon">經緯度: </span>(` + json[j].lat + `, ` + json[j].lon + `)</div>
                                                    </a>`);
                            //map必填marker可選填(marker表示放在哪個地圖標記)
                            infowindow.open(map, markers[j]);
                            //設定地圖標記動畫效果
                            markers[j].setAnimation(google.maps.Animation.BOUNCE);
                        } else {
                            infowindow.close();
                            //關閉地圖標記動畫效果
                            markers[j].setAnimation(null);

                        }
                    })
                }

                //取得視窗bounds(西南&東北座標)
                var boundsNElat, boundsNElng, boundsSWlat, boundsSWlng;
                google.maps.event.addListener(map, 'idle', function() {
                    var bounds = map.getBounds();
                    if (bounds !== null) {
                        boundsNElat = bounds.getNorthEast().lat();
                        boundsNElng = bounds.getNorthEast().lng();
                        boundsSWlat = bounds.getSouthWest().lat();
                        boundsSWlng = bounds.getSouthWest().lng();
                    }

                    //移除所有site後再新增符合在bounds座標內的site
                    $(".site").remove()
                    for (var j = 0; j < json.length; j++) {
                        var latSite = json[j].lat.substring(0, 7),
                            lonSite = json[j].lon.substring(0, 8);                      
                        if (boundsSWlat <= latSite && boundsNElat >= latSite &&
                            boundsSWlng <= lonSite && boundsNElng >= lonSite) {
                            $(".site_list").append(`
                                                    <a href="#">
                                                    <div class="site">
                                                    <div class="cgname">` + json[j].name + `</div>
                                                    <div><span class="cgregion">地區: </span>` + json[j].region + `</div>
                                                    <div><span class="cgregion">地址: </span>` + json[j].address + `</div>
                                                    <div><span class="cglatlon">經緯度: </span>(` + latSite + `, ` + lonSite + `)</div>
                                                    </div></a>`);
                        }
                    }
                });
            });
        }
    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA0yuHIUOGU1dx1CO_o1gOYuN6AkNlGSbQ&libraries=places&callback=initMap" async defer></script>
</body>
</html>