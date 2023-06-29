package by.mishastoma.customerservice.service;

import by.mishastoma.customerservice.dto.DecryptResponse;
import by.mishastoma.customerservice.dto.OrderPositionRequest;
import by.mishastoma.customerservice.dto.OrderPositionResponse;
import by.mishastoma.customerservice.exception.ForbiddenException;
import by.mishastoma.customerservice.exception.OrderNotFoundException;
import by.mishastoma.customerservice.exception.OrderPositionNotFoundException;
import by.mishastoma.customerservice.mapper.EntityMapper;
import by.mishastoma.customerservice.model.Order;
import by.mishastoma.customerservice.model.OrderPosition;
import by.mishastoma.customerservice.repository.OrderPositionRepository;
import by.mishastoma.customerservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderPositionService {

    private final OrderPositionRepository orderPositionRepository;
    private final CustomerService customerService;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final EntityMapper entityMapper;

    public OrderPositionResponse createPosition(String authHeader, OrderPositionRequest request) {
        orderService.customerIsAllowedToChangeOrder(authHeader, request.getOrderId());
        OrderPosition orderPosition = modelMapper.map(request, OrderPosition.class);
        return modelMapper.map(orderPositionRepository.save(orderPosition), OrderPositionResponse.class);
    }

    public OrderPositionResponse updatePosition(String authHeader, OrderPositionRequest request, Long id) {
        OrderPosition orderPosition = customerIsAllowedToChangeOrderPosition(authHeader, id);
        OrderPosition updateOrder = entityMapper.updateOrderPosition(orderPosition, request);
        return modelMapper.map(orderPositionRepository.save(updateOrder), OrderPositionResponse.class);
    }

    public void deleteOrderPosition(String authHeader, Long id) {
        OrderPosition orderPosition = customerIsAllowedToChangeOrderPosition(authHeader, id);
        orderPositionRepository.delete(orderPosition);
    }

    public OrderPosition customerIsAllowedToChangeOrderPosition(String authHeader, Long id) {
        OrderPosition orderPosition = orderPositionRepository.findById(id)
                .orElseThrow(() -> new OrderPositionNotFoundException(id));
        DecryptResponse decryptResponse = customerService.decryptAuthHeader(authHeader);
        Long orderId = orderPosition.getOrderId();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(String.format("Order %d wasn't found", orderId)));
        if (!decryptResponse.getId().equals(order.getCustomer().getId())) {
            throw new ForbiddenException("Users ids are different, access denied");
        } else {
            return orderPosition;
        }
    }
}
