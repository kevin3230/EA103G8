<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.vendor.model.*"%>

<%
    MembersVO memVO2 = (MembersVO)session.getAttribute("memVO");
    VendorVO vendorVO2 = (VendorVO)session.getAttribute("vendorVO");
%>

<!DOCTYPE HTML>
<!--
    Hielo by TEMPLATED
    templated.co @templatedco
    Released for free under the Creative Commons Attribution 3.0 license (templated.co/license)
-->
<html>

<head>
    <title>Plamping</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/utility/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/main.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/map_create.css" />
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.14.0/css/all.css" integrity="sha384-HzLeBuhoNPvSl5KYnjx0BT+WB0QEEqLprO+NBkkk5gbc67FTaL7XIGa2w1L0Xbgc" crossorigin="anonymous">
    <script src="<%=request.getContextPath()%>/front-end/index/assets/js/d3js.org/d3.v3.min.js" charset="utf-8"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/main2.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/search/css/search_index.css">
</head>

<body>
    <div id="icons">
        <input type="text" placeholder="搜尋露營區" id="search">
        <!-- <a href="#"><i class="fas fa-envelope fa-1g"></i></a> -->

        <!-- icon -->
        <div id="navIcon">
            <c:if test="<%=vendorVO2 == null && memVO2 == null%>">
                <a class="text-header px-3" href="#" class="envelopeicon">
                    <i class="fas fa-envelope fa-lg"></i>
                </a>
            </c:if>

            <c:if test="<%=vendorVO2 != null || memVO2 != null%>">
                <div class="dropdown">
                    <a class="text-header px-3" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown">
                        <i class="fas fa-envelope fa-lg"></i></a>
                    <div class="dropdown-menu dropdown-menu-right mt-2" id="newnotice">
                    </div>
                </div>
            </c:if>

            <a class="dropdown" href="<%=request.getContextPath()%>/front-end/carorder/confirmOrder.jsp">
                <i class="fas fa-shopping-cart fa-lg"></i>
            </a>

            <c:if test="<%=memVO2 == null && vendorVO2 == null%>">
                <a class="dropdown" href="<%=request.getContextPath()%>/front-end/index/MemVdSignInSignUp.jsp"><i class="fas fa-user-circle fa-lg"></i></a>
            </c:if>
            
            <c:if test="<%=memVO2 != null || vendorVO2 != null%>">
                <div class="dropdown">
                    <a class="dropdown" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown"><i class="fas fa-user-circle fa-lg"></i></a>
    
                    <div class="dropdown-menu dropdown-menu-right mt-2">
                        <c:if test="<%=memVO2 != null%>">
                            <a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/members/MembersInfo.jsp">會員專區</a>
                            <a class="dropdown-item" href="<%=request.getContextPath()%>/members/MembersServlet?action=memUpdateSubmit&mem_no=M000000001">修改會員資料</a>
                            <a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/membersorder/membersOrder.jsp">訂單管理</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="<%=request.getContextPath()%>/members/MembersServlet?action=memSignOutSubmit">登出</a>
                        </c:if>
                        
                        <c:if test="<%=vendorVO2 != null && memVO2 == null%>">
                            <a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/vendor/VendorInfo.jsp">業者專區</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="<%=request.getContextPath()%>/vendor/VendorServlet?action=vdSignOutSubmit">登出</a>
                        </c:if>
                    </div>
                </div>
            </c:if>
        </div>  
    </div>
    <header id="header" class="alt">
        <div class="logo"><a href="<%=request.getContextPath()%>/front-end/index/index.jsp">
        <img id="logo" src="<%= request.getContextPath()%>/front-end/index/images/logo/logo3_trans.png" alt="PLAMPING"></a></div>
        <a href="<%=request.getContextPath()%>/front-end/search/search.jsp">尋找營區</a>
        <a href="<%=request.getContextPath()%>/front-end/waterfall/listAllWaterfall.jsp">瀑布牆</a>
        <a href="<%=request.getContextPath()%>/front-end/information/guide.jsp">露營指南</a>
        <a href="<%=request.getContextPath()%>/front-end/information/eqptIntro.jsp">裝備介紹</a>
        <a href="<%=request.getContextPath()%>/front-end/information/activity.jsp">活動快報</a>
        <a href="#menu">Menu</a>
    </header>
    <!-- Nav -->
    <nav id="menu">
        <ul class="links">
            <li><a href="<%=request.getContextPath()%>/front-end/search/search.jsp">尋找營區</a></li>
            <li><a href="<%=request.getContextPath()%>/front-end/waterfall/listAllWaterfall.jsp">瀑布牆</a></li>
            <li><a href="<%=request.getContextPath()%>/front-end/information/guide.jsp">露營指南</a></li>
            <li><a href="<%=request.getContextPath()%>/front-end/information/eqptIntro.jsp">裝備介紹</a></li>
            <li><a href="<%=request.getContextPath()%>/front-end/information/activity.jsp">活動快報</a></li>
        </ul>
    </nav>
    <!-- Banner -->
    <section class="banner full">
        <article>
            <img src="images/camp1_3x2.jpg" alt="" />
            <div class="inner">
                <header>
                    <p>Plamping Your Life</p>
                    <h2>露營</h2>
                </header>
            </div>
        </article>
        <article>
            <img src="images/camp2_3x2.jpg" alt="" />
            <div class="inner">
                <header>
                    <p>Plamping Your Life</p>
                    <h2>Camping</h2>
                </header>
            </div>
        </article>
        <article>
            <img src="images/camp3_3x2.jpg" alt="" />
            <div class="inner">
                <header>
                    <p>Plamping Your Life</p>
                    <h2>キャンプ</h2>
                </header>
            </div>
        </article>
        <article>
            <img src="images/camp4_3x2.jpg" alt="" />
            <div class="inner">
                <header>
                    <p>Plamping Your Life</p>
                    <h2>Cámping</h2>
                </header>
            </div>
        </article>
        <article>
            <img src="images/camp5_3x2.jpg" alt="" />
            <div class="inner">
                <header>
                    <p id="verticalwrite">春のとびら。始まり</p>
                    <h2>一緒に、&emsp;&emsp;&emsp;&emsp;&emsp;行こうぜ</h2>
                </header>
            </div>
        </article>
    </section>
    <!-- One -->
    <section id="one" class="wrapper style2">
        <div class="inner">
            <div class="grid-style">
                <div>
                    <div class="box">
                        <div class="image fit">
                            <img src="images/pic1_3x2.jpg" alt="" />
                        </div>
                        <div class="content">
                            <header class="align-center">
                                <p>maecenas sapien feugiat ex purus</p>
                                <h2>預約訂位</h2>
                            </header>
                            <p>想來一場與大自然為伍的旅行嗎？那就跟著趣吧一起入住大自然，來體驗最純粹的露營體驗之旅，Plamping提供全台多條特色露營體驗行程!</p>
                            <footer class="align-center">
                                <a href="<%=request.getContextPath()%>/front-end/search/search.jsp" class="button alt">Learn More</a>
                            </footer>
                        </div>
                    </div>
                </div>
                <div>                    
                    <div class="box">
                        <div class="image fit">
                            <img src="images/pic2_3x2.jpg" alt="" />
                        </div>
                        <div class="content">
                            <header class="align-center">
                                <p>mattis elementum sapien pretium tellus</p>
                                <h2>瀑布牆</h2>
                            </header>
                            <p> 觀自在菩薩, 行深般若波羅密多時, 照見五蘊皆空, 度一切苦厄.
                                舍利子, 色不異空, 空不異色, 色即是空, 空即是色; 受想行識, 亦復如是.
                                舍利子, 是諸法空相: 不生不滅, 不垢不淨, 不增不減.
                            </p>
                            <footer class="align-center">
                                <a href="<%=request.getContextPath()%>/front-end/waterfall/listAllWaterfall.jsp" class="button alt">Learn More</a>
                            </footer>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="box">
                        <div class="image fit">
                            <img src="images/pic3_3x2.jpg" alt="" />
                        </div>
                        <div class="content">
                            <header class="align-center">
                                <p>mattis elementum sapien pretium tellus</p>
                                <h2>露營指南</h2>
                            </header>
                            <p> 隨著旅遊風氣興盛，越來越多人選擇走向戶外，而露營就是不少現代人躍躍欲試的首選玩法之一，雖然露營聽起來很簡單，但對露營新手來說，從心態、行前準備，到帳篷、睡袋這些裝備都是一門新學問，到底露營新手要怎樣才能讓露營變得簡單、有趣，又玩出新花樣呢？</p>
                            <footer class="align-center">
                                <a href="<%=request.getContextPath()%>/front-end/information/guide.jsp" class="button alt">Learn More</a>
                            </footer>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Two -->
    <section>
        <div class="mapcontainer justify-content-center">
            <!-- <div id="mapbox"> -->
            <div id="map">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="190 221 200 200" preserveAspectRatio="xMidYMid meet" id="svg">
                    <!-- <text x="200" y="240">TAIWAN</text> -->
                </svg>
                <!-- </div> -->
            </div>

            <div id="searchBlock">
                <%@ include file="/front-end/search/search_index.jsp"%>
            </div>
        </div>

    </section>
    <!-- Three -->
    <section id="two" class="wrapper style3">
        <div class="inner">
            <header class="align-center">
                <p>Nam vel ante sit amet libero scelerisque facilisis eleifend vitae urna</p>
                <h2>Morbi maximus justo</h2>
            </header>
        </div>
    </section>
    <!-- Four -->
    <section id="three" class="wrapper style2">
        <div class="inner">
            <header class="align-center">
                <p class="special">Nam vel ante sit amet libero scelerisque facilisis eleifend vitae urna</p>
                <h2>Morbi maximus justo</h2>
            </header>
            <div class="gallery">
                <div>
                    <div class="image fit">
                        <img src="images/image1_3x2.jpg" alt="" />
                    </div>
                </div>
                <div>
                    <div class="image fit">
                        <img src="images/image2_3x2.jpg" alt="" />
                    </div>
                </div>
                <div>
                    <div class="image fit">
                        <img src="images/image3_3x2.jpg" alt="" />
                    </div>
                </div>
                <div>
                    <div class="image fit">
                        <img src="images/image4_3x2.jpg" alt="" />
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Footer -->
    <footer id="footer">
        <div class="container">
            <ul class="about">
                <li><a href="#" class=""><span class="label">聯絡我們</span></a></li>
                <li><a href="<%=request.getContextPath()%>/front-end/information/faq.jsp" class=""><span class="label">FAQ</span></a></li>
                <li><a href="#" class=""><span class="label">服務條款</span></a></li>
                <li><a href="#" class=""><span class="label">隱私權聲明</span></a></li>
            </ul>
            <ul class="icons">
                <li><a href="#" class="icon fa-twitter"><span class="label">Twitter</span></a></li>
                <li><a href="#" class="icon fa-facebook"><span class="label">Facebook</span></a></li>
                <li><a href="#" class="icon fa-instagram"><span class="label">Instagram</span></a></li>
                <li><a href="#" class="icon fa-envelope-o"><span class="label">Email</span></a></li>
            </ul>
        </div>
        <div class="copyright">
            &copy; Plamping / &copy; あfろ・芳文社／野外活動委員会 連携企画
        </div>
    </footer>
    
    <%@ include file="/front-end/message/chatroom.jsp"%>
    
    <!-- Scripts -->
    <script src="<%=request.getContextPath()%>/front-end/index/assets/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/front-end/index/assets/js/jquery.scrollex.min.js"></script>
    <script src="<%=request.getContextPath()%>/front-end/index/assets/js/skel.min.js"></script>
    <script src="<%=request.getContextPath()%>/front-end/index/assets/js/util.js"></script>
    <script src="<%=request.getContextPath()%>/front-end/index/assets/js/main.js"></script>
    <script src="<%=request.getContextPath()%>/front-end/index/assets/js/map_create.js"></script>
    <script src="<%= request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
    <script src="<%= request.getContextPath()%>/utility/js/popper.min.js"></script>
    <script src="<%= request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
</body>

</html>