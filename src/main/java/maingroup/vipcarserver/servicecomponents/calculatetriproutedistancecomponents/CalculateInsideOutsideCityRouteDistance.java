package maingroup.vipcarserver.servicecomponents.calculatetriproutedistancecomponents;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import maingroup.vipcarserver.dtos.calculatetriproutedistancedtos.CityInsideOutsideTripRouteDistanceDto;
import maingroup.vipcarserver.dtos.calculatetriproutedistancedtos.RoutePointLocationListDto;
import maingroup.vipcarserver.dtos.calculatetriproutedistancedtos.CitySectorDto;
import maingroup.vipcarserver.services.calculatetriproutedistanceservices.CitySectorService;
import maingroup.vipcarserver.services.calculatetriproutedistanceservices.RoutePointLocationListService;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CalculateInsideOutsideCityRouteDistance {
    private final CitySectorService citySectorService;
    private final RoutePointLocationListService routePointLocationListService;
    private final GeometryFactory geometryFactory = new GeometryFactory();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CityInsideOutsideTripRouteDistanceDto calculateRouteDistances(Long riderId) {
        CitySectorDto citySector = citySectorService.getFirstCitySector();
        List<RoutePointLocationListDto> routePoints = routePointLocationListService.getAllPointLocationRouteListByRiderId(riderId);

        Polygon polygon = createPolygonFromWKT(extractWKT(citySector.getPolygonCoordinates()));

        double totalInsideDistance = 0.0;
        double totalOutsideDistance = 0.0;

        for (int i = 0; i < routePoints.size() - 1; i++) {
            RoutePointLocationListDto startPoint = routePoints.get(i);
            RoutePointLocationListDto endPoint = routePoints.get(i + 1);

            Coordinate startCoordinate = new Coordinate(startPoint.getPointLocationLongitude(), startPoint.getPointLocationLatitude());
            Coordinate endCoordinate = new Coordinate(endPoint.getPointLocationLongitude(), endPoint.getPointLocationLatitude());
            LineString lineString = geometryFactory.createLineString(new Coordinate[]{startCoordinate, endCoordinate});

            Geometry intersection = polygon.intersection(lineString);
            double distanceInside = 0.0;
            double distanceOutside;

            // Calculate distance inside polygon using Haversine formula
            for (int j = 0; j < intersection.getCoordinates().length - 1; j++) {
                Coordinate c1 = intersection.getCoordinates()[j];
                Coordinate c2 = intersection.getCoordinates()[j + 1];
                distanceInside += haversineDistance(c1.y, c1.x, c2.y, c2.x);
            }

            // Calculate total distance of the segment using Haversine formula
            double totalDistance = haversineDistance(startCoordinate.y, startCoordinate.x, endCoordinate.y, endCoordinate.x);

            // Calculate distance outside polygon
            distanceOutside = totalDistance - distanceInside;

            totalInsideDistance += distanceInside;
            totalOutsideDistance += distanceOutside;
        }

        // Round distances to one decimal place
        totalInsideDistance = Math.ceil(totalInsideDistance * 10) / 10.0;
        totalOutsideDistance = Math.ceil(totalOutsideDistance * 10) / 10.0;

        return new CityInsideOutsideTripRouteDistanceDto(totalInsideDistance, totalOutsideDistance);
    }

    private Polygon createPolygonFromWKT(String wkt) {
        try {
            WKTReader reader = new WKTReader(geometryFactory);
            return (Polygon) reader.read(wkt);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create polygon from WKT", e);
        }
    }

    private String extractWKT(String polygonCoordinatesJson) {
        try {
            JsonNode jsonNode = objectMapper.readTree(polygonCoordinatesJson);
            return jsonNode.get("wkt").asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract WKT from JSON", e);
        }
    }

    private double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
