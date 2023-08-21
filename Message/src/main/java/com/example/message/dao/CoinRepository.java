package com.example.message.dao;

import com.example.message.domain.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * packageName    : com.example.message.dao
 * fileName       : CoinRepository
 * author         : Jay
 * date           : 2023-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-26        Jay       최초 생성
 */
@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {
    Coin findBySymbol(String symbol);
    @Query("SELECT c.status FROM Coin c WHERE c.symbol = :symbol")
    String findStatusBySymbol(@Param("symbol") String symbol);
}