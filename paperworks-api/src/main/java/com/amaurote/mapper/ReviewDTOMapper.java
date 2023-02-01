package com.amaurote.mapper;

import com.amaurote.domain.entity.UserBookReview;
import com.amaurote.dto.ReviewDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public record ReviewDTOMapper() implements Function<UserBookReview, ReviewDTO> {

    @Override
    public ReviewDTO apply(UserBookReview userBookReview) {
        if (userBookReview == null)
            return null;

        return ReviewDTO.builder()
                .text(userBookReview.getText())
                .reviewerUsername(userBookReview.getReviewer().getUsername())
                .dateCreated(userBookReview.getDateCreated())
                .dateUpdated(userBookReview.getDateUpdated())
                .build();
    }
}
