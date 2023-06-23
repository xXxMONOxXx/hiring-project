package by.mishastoma.customerservice.mapper;

import by.mishastoma.customerservice.dto.ApplicationRequest;
import by.mishastoma.customerservice.dto.CustomerRequest;
import by.mishastoma.customerservice.dto.MessageRequest;
import by.mishastoma.customerservice.dto.OrderPositionRequest;
import by.mishastoma.customerservice.dto.OrderRequest;
import by.mishastoma.customerservice.dto.ReviewRequest;
import by.mishastoma.customerservice.model.Application;
import by.mishastoma.customerservice.model.Customer;
import by.mishastoma.customerservice.model.Message;
import by.mishastoma.customerservice.model.Order;
import by.mishastoma.customerservice.model.OrderPosition;
import by.mishastoma.customerservice.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EntityMapper {

    Order updateOrder(@MappingTarget Order order, OrderRequest dto);

    Customer updateCustomer(@MappingTarget Customer customer, CustomerRequest dto);

    Review updateReview(@MappingTarget Review review, ReviewRequest dto);

    Message updateMessage(@MappingTarget Message message, MessageRequest dto);

    OrderPosition updateOrderPosition(@MappingTarget OrderPosition position, OrderPositionRequest dto);

    Application updateApplication(@MappingTarget Application application, ApplicationRequest dto);
}
