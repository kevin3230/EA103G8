package com.ordercamp.model;

import java.sql.Connection;
import java.util.List;

public interface OrderCampDAO_interface {
    public void insert(OrderCampVO orderCampVO);
    public void update(OrderCampVO orderCampVO);
    public OrderCampVO findByPrimaryKey(String oc_no);
    public List<OrderCampVO> getAll();
    // 用營位編號查詢
    public List<OrderCampVO> getOrderCampsByCampno(String camp_no);
    // 用主訂單編號查詢
    public List<OrderCampVO> getOrderCampsByOmno(String om_no);
    // Author Jeff
    public void insert(OrderCampVO orderCampVO, Connection con);
    public void update(OrderCampVO orderCampVO, Connection con);
    public void delete(OrderCampVO orderCampVO, Connection con);
    public List<OrderCampVO> getActiveOrderCampsByOmno(String om_no);
}