package cn.itcast.order.service;

import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RestTemplate restTemplate;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        //2.利用restTemplate调用user-service查询用户
        String url = "http://cloud-user-service/user/"+order.getUserId();
        //发送请求，获取用户数据
        User user = restTemplate.getForObject(url, User.class);
        //封装数据回去
        order.setUser(user);
        // 4.返回
        return order;
    }
}
