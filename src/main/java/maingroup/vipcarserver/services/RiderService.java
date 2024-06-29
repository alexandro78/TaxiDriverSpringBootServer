package maingroup.vipcarserver.services;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.founddriverdto.SettingOptionsDto;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchRiderDataDto;
import maingroup.vipcarserver.repositories.RiderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiderService {

    private final RiderRepository riderRepository;

    public SearchRiderDataDto getSearchRiderDataByUserId(Long userId) {
        return riderRepository.findUserDataByUserId(userId);
    }

    public Long getSavedRouteByRiderId(Long riderId) {
        return riderRepository.findSavedRouteByRiderId(riderId);
    }

    public SettingOptionsDto getOptionsSettingByUserId(Long userId) {
        return riderRepository.findSettingOptionsByUserId(userId);
    }
}
