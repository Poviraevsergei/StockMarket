package by.itechart.service;

import by.itechart.model.response.ComparisonResponse;

import java.util.Optional;

public interface OpenApiService {

    Optional<ComparisonResponse> comparison(String firstTicker, String secondTicker);

    Optional<ComparisonResponse> comparisonForTheYear(String ticker);
}