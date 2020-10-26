package by.itechart.controller;

import by.itechart.exception.CustomException;
import by.itechart.model.response.ComparisonResponse;
import by.itechart.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static by.itechart.utils.ProjectProperties.COMPARISON_EXCEPTION;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OpenApiController {

    private final OpenApiService openApiService;

    @GetMapping(value = "/comparison")
    public ResponseEntity<ComparisonResponse> comparison(String firstTicker, String secondTicker) {
        ComparisonResponse comparison = openApiService.comparison(firstTicker, secondTicker)
                .orElseThrow(() -> new CustomException(COMPARISON_EXCEPTION));
        return new ResponseEntity<>(comparison, HttpStatus.OK);
    }

    @GetMapping(value = "/comparisonForTheYear")
    public ResponseEntity<ComparisonResponse> comparisonForTheYear(String ticker) {
        ComparisonResponse comparisonResponse = openApiService.comparisonForTheYear(ticker)
                .orElseThrow(() -> new CustomException(COMPARISON_EXCEPTION));
        return new ResponseEntity<>(comparisonResponse, HttpStatus.OK);
    }
}