package maingroup.vipcarserver.services.calculatetriproutedistanceservices;


import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.calculatetriproutedistancedtos.CitySectorDto;
import maingroup.vipcarserver.repositories.calculatetriproutedistance.CitySectorRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CitySectorService {

    private final CitySectorRepository citySectorRepository;

    public CitySectorDto getFirstCitySector() {
        return citySectorRepository.findFirstByOrderById()
                .map(sector -> new CitySectorDto(
                        sector.getId(),
                        sector.getSityName(),
                        sector.getSityPolygonCoordinates()
                ))
                .orElseThrow(() -> new RuntimeException("CitySector not found"));
    }
}
