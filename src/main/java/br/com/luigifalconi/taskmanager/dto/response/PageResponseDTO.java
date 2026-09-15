package br.com.luigifalconi.taskmanager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponseDTO<T> {

    private List<T> content;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

    private boolean first;

    private boolean last;

    public static <E, D> PageResponseDTO<D> from(
            Page<E> page,
            Function<E, D> mapper
    ) {

        return PageResponseDTO.<D>builder()

                .content(
                        page.getContent()
                                .stream()
                                .map(mapper)
                                .toList()
                )

                .page(page.getNumber())
                .size(page.getSize())

                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())

                .first(page.isFirst())
                .last(page.isLast())

                .build();
    }

}