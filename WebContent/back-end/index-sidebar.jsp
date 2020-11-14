<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adminis.model.*"%>




<div id="wrapper">
    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
        <!-- index-sideber.jsp - Brand -->
        <a class="sidebar-brand d-flex align-items-center justify-content-center" href="<%=request.getContextPath()%>/back-end/index.jsp">
            <div class="sidebar-brand-icon rotate-n-15">
                <i class="fas fa-laugh-wink"></i>
            </div>
            <div class="sidebar-brand-text mx-3">Planing Admin </div>
        </a>
        <!-- Divider -->
        <hr class="sidebar-divider my-0">
        <!-- Nav Item - Dashboard -->
        
        <hr class="sidebar-divider">
        <!-- Heading -->
        <div class="sidebar-heading">
           	 功能
        </div>
        <!-- Nav Item - Pages Collapse Menu -->
        <li class="nav-item active">
            <a class="nav-link" href="#" data-toggle="collapse" data-target="#collapsePages1" aria-expanded="true" aria-controls="collapsePages1">
                <i class="fas fa-fw fa-folder"></i>
                <span>管理員管理</span>
            </a>
            		<div id="collapsePages1" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
            	    <div class="bg-white py-2 collapse-inner rounded">
                    <h6 class="collapse-header">管理員資料管理:</h6>
                    <a class="collapse-item" href="<%= request.getContextPath()%>/back-end/adminis/listAllAdminis.jsp">管理員查詢</a>
                    <a class="collapse-item" href="<%= request.getContextPath()%>/back-end/adminis/addAdminis.jsp">新增管理員</a>
                    <hr class="sidebar-divider d-none d-md-block">
                    </div>
		            </div>
		            
 			<a class="nav-link" href="#" data-toggle="collapse" data-target="#collapsePages2" aria-expanded="true" aria-controls="collapsePages2">
                <i class="fas fa-fw fa-folder"></i>
                <span>帳號管理</span>
            </a>
            		<div id="collapsePages2" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
            	    <div class="bg-white py-2 collapse-inner rounded">                 
                    <h6 class="collapse-header">帳號管理:</h6>
                    <a class="collapse-item" href="<%= request.getContextPath()%>/back-end/adminis/listAllAdminis.jsp">廠商帳號審核</a>
                    <a class="collapse-item" href="<%= request.getContextPath()%>/back-end/adminis/addAdminis.jsp">會員資料管理</a>
                    <hr class="sidebar-divider d-none d-md-block">
                    </div>
 		            </div>
 		            
 			<a class="nav-link" href="#" data-toggle="collapse" data-target="#collapsePages3" aria-expanded="true" aria-controls="collapsePages3">
                <i class="fas fa-fw fa-folder"></i>
                <span>資訊管理</span>
            </a>
            		<div id="collapsePages3" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
            	    <div class="bg-white py-2 collapse-inner rounded">                 
 		            <h6 class="collapse-header">資訊管理:</h6>
                    <a class="collapse-item" href="<%= request.getContextPath()%>/back-end/adminis/listAllAdminis.jsp">露營指南管理</a>
                    <a class="collapse-item" href="<%= request.getContextPath()%>/back-end/adminis/addAdminis.jsp">裝備介紹管理</a>
					<a class="collapse-item" href="<%= request.getContextPath()%>/back-end/adminis/listAllAdminis.jsp">露營指南管理</a>
                    <a class="collapse-item" href="<%= request.getContextPath()%>/back-end/adminis/addAdminis.jsp">裝備介紹管理</a>
                    <hr class="sidebar-divider d-none d-md-block">
                    </div>
 		            </div>
 		            
 			<a class="nav-link" href="#" data-toggle="collapse" data-target="#collapsePages4" aria-expanded="true" aria-controls="collapsePages4">
                <i class="fas fa-fw fa-folder"></i>
                <span>訂單管理</span>
            </a>
            		<div id="collapsePages4" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
            	    <div class="bg-white py-2 collapse-inner rounded">                 
 		            <h6 class="collapse-header">訂單管理:</h6>
                    <a class="collapse-item" href="<%= request.getContextPath()%>/back-end/adminis/listAllAdminis.jsp">訂單查詢</a>
                    <hr class="sidebar-divider d-none d-md-block">
                    </div>
 		            </div>
 		            
 			<a class="nav-link" href="#" data-toggle="collapse" data-target="#collapsePages5" aria-expanded="true" aria-controls="collapsePages5">
                <i class="fas fa-fw fa-folder"></i>
                <span>瀑布牆管理</span>
            </a>
            		<div id="collapsePages5" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
            	    <div class="bg-white py-2 collapse-inner rounded">                 
 		            <h6 class="collapse-header">瀑布牆管理:</h6>
                    <a class="collapse-item" href="<%= request.getContextPath()%>/back-end/wfreport/listAllWfreport.jsp">文章檢舉管理</a>
                    <hr class="sidebar-divider d-none d-md-block">
                    </div>
 		            </div>
 	
 	 			<a class="nav-link" href="#" data-toggle="collapse" data-target="#collapsePages6" aria-expanded="true" aria-controls="collapsePages6">
                <i class="fas fa-fw fa-folder"></i>
                <span>帳務管理</span>
            </a>
            		<div id="collapsePages6" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
            	    <div class="bg-white py-2 collapse-inner rounded">                 
 		            <h6 class="collapse-header">帳務管理:</h6>
                    <a class="collapse-item" href="<%= request.getContextPath()%>/back-end/adminis/listAllAdminis.jsp">收支管理</a>
                    <hr class="sidebar-divider d-none d-md-block">
                    </div>
 		            </div>	            
        </li>
        <!-- Divider -->
        <hr class="sidebar-divider d-none d-md-block">
        <!-- Sidebar Toggler (Sidebar) -->
        <div class="text-center d-none d-md-inline">
            <button class="rounded-circle border-0" id="sidebarToggle"></button>
        </div>  
     </ul>
        <!-- End of Sidebar --> 
</div>
