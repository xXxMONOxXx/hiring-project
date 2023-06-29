package by.mishastoma.customerservice.service;

import by.mishastoma.customerservice.dto.DecryptResponse;
import by.mishastoma.customerservice.dto.OrderRequest;
import by.mishastoma.customerservice.dto.OrderResponse;
import by.mishastoma.customerservice.dto.StatusResponse;
import by.mishastoma.customerservice.exception.ForbiddenException;
import by.mishastoma.customerservice.exception.OrderNotFoundException;
import by.mishastoma.customerservice.mapper.EntityMapper;
import by.mishastoma.customerservice.model.Order;
import by.mishastoma.customerservice.model.OrderStatus;
import by.mishastoma.customerservice.repository.CustomerRepository;
import by.mishastoma.customerservice.repository.OrderRepository;
import by.mishastoma.customerservice.repository.OrderStatusRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Value("${order.default.status.id}")
    private Long defaultStatusId;
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final EntityMapper entityMapper;
    private final CustomerRepository customerRepository;
    private final OrderStatusRepository orderStatusRepository;

    public OrderResponse update(String authHeader, Long id, OrderRequest request) {
        Order order = customerIsAllowedToChangeOrder(authHeader, id);
        Order updatedOrder = entityMapper.updateOrder(order, request);
        return modelMapper.map(orderRepository.save(updatedOrder), OrderResponse.class);
    }

    public OrderResponse createOrder(OrderRequest request, String authHeader) {
        DecryptResponse decryptResponse = customerService.decryptAuthHeader(authHeader);
        Order order = modelMapper.map(request, Order.class);
        order.setCustomer(customerRepository.findById(decryptResponse.getId()).get());
        order.setStatusId(defaultStatusId);
        return modelMapper.map(orderRepository.save(order), OrderResponse.class);
    }

    public void deleteOrder(String authHeader, Long id) {
        customerIsAllowedToChangeOrder(authHeader, id);
        orderRepository.deleteById(id);
    }

    public List<StatusResponse> getAllStatuses() {
        List<OrderStatus> statuses = orderStatusRepository.findAll();
        return statuses
                .stream()
                .map(orderStatus -> modelMapper.map(orderStatus, StatusResponse.class))
                .toList();
    }

    public Order customerIsAllowedToChangeOrder(String authHeader, Long orderId) {
        DecryptResponse decryptResponse = customerService.decryptAuthHeader(authHeader);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(String.format("Order %d wasn't found", orderId)));
        if (!decryptResponse.getId().equals(order.getCustomer().getId())) {
            throw new ForbiddenException("Users ids are different, access denied");
        } else {
            return order;
        }
    }
}
