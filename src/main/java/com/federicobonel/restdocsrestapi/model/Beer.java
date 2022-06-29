package com.federicobonel.restdocsrestapi.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Beer {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private Timestamp creationDate;

    @UpdateTimestamp
    @Column(name = "last_modified_date")
    private Timestamp lastModifiedDate;

    @Column(name = "name")
    private String name;

    @Column(name = "style")
    private String style;

    @Column(name = "upc")
    private Long upc;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "min_hand")
    private Integer minOnHand;

    @Column(name = "quantity_to_brew")
    private Integer quantityToBrew;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Beer beer = (Beer) o;
        return id != null && Objects.equals(id, beer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
