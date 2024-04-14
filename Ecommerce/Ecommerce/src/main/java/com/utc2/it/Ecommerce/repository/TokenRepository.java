package com.utc2.it.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.utc2.it.Ecommerce.entity.Token;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("""
select  T FROM Token T inner join  User u 
on T.user.Id=u.Id
WHERE T.user.Id=:userId and T.logout=false 
""")
    List<Token>findAllTokenByUser(Long userId);
    Optional<Token>findByToken(String token);
}
