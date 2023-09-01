package com.example.message.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * packageName    : com.example.message.domain
 * fileName       : Coin
 * author         : Jay
 * date           : 2023-07-6
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-26       Jay       최초 생성
 */
@Entity
@Table(name = "coin")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coin_uid")
    private Long coinUid;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "status")
    private String status;

    @Column(name = "like_count")
    private Integer likeCount;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Coin(String symbol, String status) {
        this.symbol = symbol;
        this.status = status;
    }
    public static Coin createBTCEvent(String symbol, String status){
        return new Coin(symbol, status);
    }


}