package maingroup.vipcarserver.servicecomponents.matchingbycarprioritycomponents;

import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchFitDriversDataDto;
import maingroup.vipcarserver.dtos.searchtripdtos.SearchRiderDataDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

//Class за допомогою методу filterDriversByRiderClasses створює
//відфільтрований список drives з тими чиї авто = классу авто які стоять у rider
@Component
@RequiredArgsConstructor
public class DriversFilterByRiderCarClasses {

    private boolean matchesRiderToDriversClass(SearchRiderDataDto riderData, SearchFitDriversDataDto driver) {
        return (riderData.getComfort() && driver.getComfort()) ||
                (riderData.getLux() && driver.getLux()) ||
                (riderData.getParam2() && driver.getPremium()) ||
                (riderData.getParam3() && driver.getElite());
    }

    public List<SearchFitDriversDataDto> filterDriversByRiderClasses(SearchRiderDataDto riderData, List<SearchFitDriversDataDto> allDrivers) {
        return allDrivers.stream()
                .filter(driver -> matchesRiderToDriversClass(riderData, driver)) // Filtering by vehicle class matching
                .collect(Collectors.toList()); // Collect filtered drivers into a list
    }
}
