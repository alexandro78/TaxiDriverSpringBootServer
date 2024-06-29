package maingroup.vipcarserver.services.priceservices;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.pricedtos.DriverPriceFilterDto;
import maingroup.vipcarserver.repositories.pricefilter.DriverPriceFilterRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverPriceFilterService {
    private final DriverPriceFilterRepository driverPriceFilterRepository;

    public DriverPriceFilterDto getDriverPriceFilterByDriverId(Long driverId) {
        return driverPriceFilterRepository.findFirstByDriverId(driverId)
                .map(driverFilter -> new DriverPriceFilterDto(driverFilter.getDriver().getId(), driverFilter.getPrice()))
                .orElse(null); // Повертаємо null, якщо запис не знайдено, можна також кидати виняток або використовувати інший спосіб
    }
}
