package maingroup.vipcarserver.repositories.price;

import maingroup.vipcarserver.dtos.pricedtos.PriceDto;
import maingroup.vipcarserver.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query("SELECT new maingroup.vipcarserver.dtos.pricedtos.PriceDto(p.comfortBoardingFare, p.comfortCityKm, p.comfortOutOfCityKm) " +
            "FROM Price p ORDER BY p.updatedAt DESC, p.timestamp DESC")
    PriceDto findTopComfortPrices();

    @Query("SELECT new maingroup.vipcarserver.dtos.pricedtos.PriceDto(p.luxBoardingFare, p.luxCityKm, p.luxOutOfCityKm) " +
            "FROM Price p ORDER BY p.updatedAt DESC, p.timestamp DESC")
    PriceDto findTopLuxPrices();

    @Query("SELECT new maingroup.vipcarserver.dtos.pricedtos.PriceDto(p.premiumBoardingFare, p.premiumCityKm, p.premiumOutOfCityKm) " +
            "FROM Price p ORDER BY p.updatedAt DESC, p.timestamp DESC")
    PriceDto findTopPremiumPrices();

    @Query("SELECT new maingroup.vipcarserver.dtos.pricedtos.PriceDto(p.eliteBoardingFare, p.eliteCityKm, p.eliteOutOfCityKm) " +
            "FROM Price p ORDER BY p.updatedAt DESC, p.timestamp DESC")
    PriceDto findTopElitePrices();
}
