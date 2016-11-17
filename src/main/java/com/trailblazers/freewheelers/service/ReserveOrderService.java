package com.trailblazers.freewheelers.service;

import com.trailblazers.freewheelers.mappers.MyBatisUtil;
import com.trailblazers.freewheelers.mappers.ReserveOrderMapper;
import com.trailblazers.freewheelers.model.OrderStatus;
import com.trailblazers.freewheelers.model.ReserveOrder;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReserveOrderService {

    private final SqlSession sqlSession;
    private final ReserveOrderMapper orderMapper;

    public ReserveOrderService() {
        sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        orderMapper = sqlSession.getMapper(ReserveOrderMapper.class);
    }

    public void save(ReserveOrder reserveOrder) {
        if(reserveOrder.getOrder_id() == null) {
            orderMapper.insert(reserveOrder);
        } else {
            orderMapper.save(reserveOrder);
        }
        sqlSession.commit();
    }

    public List<ReserveOrder> findAllOrdersByAccountId(Long account_id) {
        sqlSession.clearCache();
        return orderMapper.findAllFor(account_id);
    }

    public List<ReserveOrder> getAllOrdersByAccount() {
        sqlSession.clearCache();
        return orderMapper.findAll();
    }

    public void updateOrderDetails(Long order_id, OrderStatus status, String note) {
        ReserveOrder order = orderMapper.get(order_id);

        order.setStatus(status);
        order.setNote(note);

        orderMapper.save(order);
        sqlSession.commit();
    }

}
