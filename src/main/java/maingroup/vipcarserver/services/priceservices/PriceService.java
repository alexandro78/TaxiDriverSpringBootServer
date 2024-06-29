package maingroup.vipcarserver.services.priceservices;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.pricedtos.PriceDto;
import maingroup.vipcarserver.repositories.price.PriceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceService {
    private final PriceRepository priceRepository;

    public PriceDto getLatestComfortPrices() {
        return priceRepository.findTopComfortPrices();
    }

    public PriceDto getLatestLuxPrices() {
        return priceRepository.findTopLuxPrices();
    }

    public PriceDto getLatestPremiumPrices() {
        return priceRepository.findTopPremiumPrices();
    }

    public PriceDto getLatestElitePrices() {
        return priceRepository.findTopElitePrices();
    }
}
