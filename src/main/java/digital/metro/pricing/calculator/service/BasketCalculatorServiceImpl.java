package digital.metro.pricing.calculator.service;

import digital.metro.pricing.calculator.model.Basket;
import digital.metro.pricing.calculator.model.BasketEntry;
import digital.metro.pricing.calculator.repository.PriceRepository;
import digital.metro.pricing.calculator.web.response.BasketCalculationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BasketCalculatorServiceImpl implements BasketCalculatorService {

    private final PriceRepository priceRepository;

    @Autowired
    public BasketCalculatorServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public BasketCalculationResult calculateBasket(Basket basket) {
        Map<String, BigDecimal> pricedArticles = basket.getEntries().stream()
                .collect(Collectors.toMap(
                        BasketEntry::getArticleId,
                        entry -> calculateArticlePrice(entry, basket.getCustomerId())
                                .multiply(entry.getQuantity())
                ));

        BigDecimal totalAmount = pricedArticles.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new BasketCalculationResult(basket.getCustomerId(), pricedArticles, totalAmount);
    }

    @Override
    public BigDecimal calculateArticlePrice(BasketEntry basketEntry, String customerId) {
        String articleId = basketEntry.getArticleId();

        if (customerId != null) {
            BigDecimal customerPrice = priceRepository.getPriceByArticleIdAndCustomerId(articleId, customerId);

            if (customerPrice != null) {
                return customerPrice;
            }
        }

        return priceRepository.getPriceByArticleId(articleId);
    }

    @Override
    public BigDecimal calculateArticlePrice(BasketEntry basketEntry) {
        return calculateArticlePrice(basketEntry, null);
    }
}
