package digital.metro.pricing.calculator.service;

import digital.metro.pricing.calculator.model.Basket;
import digital.metro.pricing.calculator.model.BasketEntry;
import digital.metro.pricing.calculator.web.response.BasketCalculationResult;

import java.math.BigDecimal;

public interface BasketCalculatorService {

    /**
     * Calculates the prices of articles of each type and the total amount of the items in the shopping basket.
     *
     * @param basket The shopping basket containing items and their quantity.
     * @return A {@link BasketCalculationResult} object representing the result of the calculation, which contains
     * information such as customer ID, priced basket entries, and the total amount.
     */
    BasketCalculationResult calculateBasket(Basket basket);

    /**
     * Calculates the price of an article for a specific customer.
     *
     * @param basketEntry The basket entry containing the article id and the quantity.
     * @param customerId The id of the customer.
     * @return A BigDecimal representing the calculated price of the article for the specified given customer.
     */
    BigDecimal calculateArticlePrice(BasketEntry basketEntry, String customerId);

    /**
     * Calculates the price of an article.
     *
     * @param basketEntry The basket entry containing the article id and the quantity.
     * @return A BigDecimal representing the calculated price of the article.
     */
    BigDecimal calculateArticlePrice(BasketEntry basketEntry);
}
