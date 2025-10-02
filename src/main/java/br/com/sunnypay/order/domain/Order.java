package br.com.sunnypay.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders_tb")
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID referenceId;

    @Setter
    @Column(nullable = false)
    private Long amountInCents;

    @Enumerated(EnumType.STRING)
    @Setter
    private OrderStatus status = OrderStatus.PENDING;

    @Setter
    private String customerName;

    @Setter
    private String customerEmail;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Order(Long amountInCents, String customerName, String customerEmail) {
        this.amountInCents = amountInCents;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }

    public Order() {

    }

    @PrePersist
    private void generateReferenceId() {
        if (referenceId == null) {
            referenceId = UUID.randomUUID();
        }
    }
}
