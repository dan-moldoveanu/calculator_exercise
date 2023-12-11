package digital.metro.pricing.calculator.controller;

import digital.metro.pricing.calculator.model.Basket;
import digital.metro.pricing.calculator.model.BasketEntry;
import digital.metro.pricing.calculator.service.BasketCalculatorService;
import digital.metro.pricing.calculator.web.response.BasketCalculationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/calculator")
public class CalculatorResource {

    private final BasketCalculatorService basketCalculatorService;

    @Autowired
    public CalculatorResource(BasketCalculatorService basketCalculatorService) {
        this.basketCalculatorService = basketCalculatorService;
    }

    @PostMapping("/calculate-basket")
    public BasketCalculationResult calculateBasket(@RequestBody final Basket basket) {
        return basketCalculatorService.calculateBasket(basket);
    }

    @GetMapping("/article/{articleId}")
    public BigDecimal getArticlePrice(@PathVariable final String articleId) {
        return basketCalculatorService.calculateArticlePrice(new BasketEntry(articleId, BigDecimal.ONE));
    }

    @GetMapping("/article/{articleId}/customer/{customerId}")
    public BigDecimal getArticlePriceForCustomer(@PathVariable final String articleId, @PathVariable final String customerId) {
        return basketCalculatorService.calculateArticlePrice(new BasketEntry(articleId, BigDecimal.ONE), customerId);
    }
}
