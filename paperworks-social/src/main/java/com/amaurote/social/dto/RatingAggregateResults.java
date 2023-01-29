package com.amaurote.social.dto;

import lombok.Getter;

@Getter
public class RatingAggregateResults {

    private final double rating;
    private final int totalRatings;

    public RatingAggregateResults(Double rating, Long totalRatings) {
        this.rating = (rating == null) ? 0 : rating;
        this.totalRatings = (totalRatings == null) ? 0 : totalRatings.intValue();
    }
}