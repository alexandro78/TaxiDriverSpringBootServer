package maingroup.vipcarserver.services.priceservices;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.pricedtos.RiderPriceFilterDto;
import maingroup.vipcarserver.repositories.pricefilter.RiderPriceFilterRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiderPriceFilterService {
    private final RiderPriceFilterRepository riderPriceFilterRepository;

    public RiderPriceFilterDto getRiderPriceFilterByRiderId(Long riderId) {
        return riderPriceFilterRepository.findFirstByRiderId(riderId)
                .map(riderFilter -> new RiderPriceFilterDto(riderFilter.getRider().getId(), riderFilter.getPrice()))
                .orElse(null); // Повертаємо null, якщо запис не знайдено, можна також кидати виняток або використовувати інший спосіб
    }
}
