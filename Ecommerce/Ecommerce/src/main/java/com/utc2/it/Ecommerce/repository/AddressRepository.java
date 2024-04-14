package com.utc2.it.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.utc2.it.Ecommerce.entity.Address;
import com.utc2.it.Ecommerce.entity.User;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    @Query("select ad from Address ad where ad.user=:user")
    List<Address>getListAddressByUser(@Param("user")User user);
    @Query("select ad from Address ad where ad.user=:user and ad.isDefine=:active ")
    Address getAddressByIsDefine(@Param("user")User user,@Param("active") Boolean active);
    @Query("select ad from Address ad where ad.Id=:id")
    Address findAddressById(@Param("id") Long id);

}
