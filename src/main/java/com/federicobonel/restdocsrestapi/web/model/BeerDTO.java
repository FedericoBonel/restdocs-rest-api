package com.federicobonel.restdocsrestapi.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerDTO {

    @Null
    private UUID id;

    @Null
    private Long version;

    @Null
    private OffsetDateTime creationDate;

    @Null
    private OffsetDateTime lastModifiedDate;

    @NotBlank
    private String name;

    @NotBlank
    private String style;

    @Positive
    @NotNull
    private Long upc;

    @Positive
    @NotNull
    private BigDecimal price;

    private Integer minOnHand;

}
