package org.revo.pere.model;

import lombok.*;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Search {
    private int page;
    @Min(1)
    private int size;
    private Sort.Direction direction;
    private PropertyDirection property;
    private String value;
}
