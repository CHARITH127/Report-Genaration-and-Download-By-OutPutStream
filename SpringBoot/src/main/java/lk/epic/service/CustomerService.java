package lk.epic.service;

import lk.epic.dto.CustomerDTO;
import lk.epic.entity.Customer;
import lk.epic.repo.CustomerRepo;
import lk.epic.util.EncriptionCode;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    EncriptionCode encriptionCode;

    @Autowired
    ModelMapper mapper;

    public boolean saveCustomer(CustomerDTO customerDTO) {

        if (customerRepo.existsById(customerDTO.getUserId())) {
            throw new RuntimeException("Customer All ready saved");
        } else {
            Customer customer = new Customer(customerDTO.getUserId(), customerDTO.getUserName(), customerDTO.getAddress(), customerDTO.getEmail(), customerDTO.getTelephoneNumber(), customerDTO.getPassword());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String createTime = dtf.format(now);
            String encryptedPassword = encriptionCode.encript(customer.getPassword());

            customer.setCreateDate(createTime);
            customer.setPassword(encryptedPassword);
            customer.setCreateUser(customer.getUserName());
            customer.setLastUpdateDate("");
            customer.setLastUpdateUser("");

            customerRepo.save(customer);
            return true;

        }
    }

    public CustomerDTO searchCustomer(String custID) {

        if (customerRepo.existsById(custID)) {
            Customer customer = customerRepo.searchCustomer(custID);
            return new CustomerDTO(customer.getUserId(), customer.getUserName(), customer.getAddress(), customer.getEmail(), customer.getTelephoneNumber(), customer.getPassword());
        } else {
            throw new RuntimeException("Not such a customer");
        }
    }

    public boolean UpdateCustomer(CustomerDTO customerDTO) {
        CustomerDTO checkCustomer = searchCustomer(customerDTO.getUserId());
        String decriptionPassword = encriptionCode.decription(checkCustomer.getPassword());

        if ((customerDTO.getUserId().equalsIgnoreCase(checkCustomer.getUserId())) && (customerDTO.getUserName().equalsIgnoreCase(checkCustomer.getUserName())) && (customerDTO.getAddress().equalsIgnoreCase(checkCustomer.getAddress())) && (customerDTO.getEmail().equalsIgnoreCase(checkCustomer.getEmail())) && (customerDTO.getTelephoneNumber().equalsIgnoreCase(checkCustomer.getTelephoneNumber())) & (customerDTO.getPassword().equalsIgnoreCase(decriptionPassword))) {
            throw new RuntimeException("Nothing to update this");
        } else {
            Customer customer = mapper.map(customerRepo.findById(customerDTO.getUserId()).get(), Customer.class);
            String createdDate = customer.getCreateDate();
            String createdUser = customer.getCreateUser();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String lastUpdateTime = dtf.format(now);

            String encryptedPassword = encriptionCode.encript(customerDTO.getPassword());
            customer.setUserName(customerDTO.getUserName());
            customer.setAddress(customerDTO.getAddress());
            customer.setEmail(customerDTO.getEmail());
            customer.setTelephoneNumber(customerDTO.getTelephoneNumber());
            customer.setPassword(encryptedPassword);
            customer.setCreateDate(createdDate);
            customer.setCreateUser(createdUser);
            customer.setLastUpdateDate(lastUpdateTime);
            customer.setLastUpdateUser(customerDTO.getUserName());

            customerRepo.save(customer);
            return true;
        }

    }

    public boolean deleteCustomer(String custId) {
        if (customerRepo.existsById(custId)) {
            customerRepo.deleteById(custId);
            return true;
        } else {
            throw new RuntimeException("Not such a customer");
        }
    }

    public List<CustomerDTO> getAll() {
        List<Customer> customers = customerRepo.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO(customer.getUserId(), customer.getUserName(), customer.getAddress(), customer.getEmail(), customer.getTelephoneNumber(), customer.getPassword());
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    public boolean checkLogin(String userName, String password) {
        Customer customer = customerRepo.checkLogin(userName);
        if (customer != null) {
            String encriptedPassword = customer.getPassword();
            String decript = encriptionCode.decription(encriptedPassword);

            if (password.equals(decript)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
