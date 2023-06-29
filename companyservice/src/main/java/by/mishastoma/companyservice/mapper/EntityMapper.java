package by.mishastoma.companyservice.mapper;

import by.mishastoma.companyservice.dto.CompanyRequest;
import by.mishastoma.companyservice.dto.EmployeeRequest;
import by.mishastoma.companyservice.dto.MessageRequest;
import by.mishastoma.companyservice.model.Company;
import by.mishastoma.companyservice.model.Employee;
import by.mishastoma.companyservice.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EntityMapper {
    Message updateMessage(@MappingTarget Message message, MessageRequest dto);

    Company updateCompany(@MappingTarget Company company, CompanyRequest dto);

    Employee updateEmployee(@MappingTarget Employee employee, EmployeeRequest dto);
}
