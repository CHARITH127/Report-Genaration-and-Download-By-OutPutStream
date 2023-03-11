package lk.epic.repo;

import lk.epic.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepo extends JpaRepository<Customer, String> {

    @Query("FROM Customer C WHERE C.userId=:value OR C.userName=:value OR C.email=:value OR C.telephoneNumber=:value")
    Customer searchCustomer(@Param("value") String value);

    @Query("FROM Customer C WHERE C.userName=:name")
    Customer checkLogin(@Param("name") String name);

}
