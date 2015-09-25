package com.example.testingpractice;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ильнур on 25.09.2015.
 */
public class CountriesHelper {

    public static GeoObjects deserialize(String json) throws Exception {
        GeoObjects geoObjects =  new Gson().fromJson(json, GeoObjects.class);
        if (geoObjects.countries == null) {
            throw new IllegalArgumentException("json is empty");
        }
        return geoObjects;
    }

    public static List<GeoObjects.Country> countriesByCitiesCount(GeoObjects geoObjects){
        List<GeoObjects.Country> resultCountries = new ArrayList<>();

        for (GeoObjects.Country country : geoObjects.countries) {
            if (country.cities.size() > 27) {
                resultCountries.add(country);
            }
        }

        return resultCountries;
    }

    public static List<GeoObjects.City> citiesByPopulation(GeoObjects geoObjects) {
        List<GeoObjects.City> resultCities = new ArrayList<>();

        for (GeoObjects.Country country : geoObjects.countries) {
            for (GeoObjects.City city : country.cities) {
                if (city.population > 45_000_000) {
                    resultCities.add(city);
                }
            }
        }

        return resultCities;
    }

    public static GeoObjects.Country cityWithBiggestPopulation(GeoObjects geoObjects) {

        long resultPopulation = -1;
        GeoObjects.Country resultCountry = null;

        for (GeoObjects.Country country : geoObjects.countries) {

            long population = 0;
            for (GeoObjects.City city : country.cities) {
                population += city.population;
            }
            if (population > resultPopulation) {
                resultPopulation = population;
                resultCountry = country;
            }
        }

        return resultCountry;
    }

    public static GeoObjects.Country countryWithBiggestInfrastructire(GeoObjects geoObjects) {
        GeoObjects.Country resultCountry = null;
        int resultCitiesCount = -1;

        for (GeoObjects.Country country : geoObjects.countries) {
            int citiesCount = 0;

            for (GeoObjects.City city : country.cities) {
                if (city.markers.contains(GeoObjects.Marker.RESORT)
                        && city.markers.contains(GeoObjects.Marker.COUNTRY_CAPITAL)
                        && city.markers.contains(GeoObjects.Marker.WITH_AIRPORT)) {
                    citiesCount++;
                }
            }

            if (citiesCount > resultCitiesCount) {
                resultCitiesCount = citiesCount;
                resultCountry = country;
            }
        }

        return resultCountry;
    }

    public static GeoObjects.Country countriesByLatitude(GeoObjects geoObjects) {
        for (GeoObjects.Country country : geoObjects.countries) {
            int citiesCount = 0;
            for (GeoObjects.City city : country.cities) {
                if (city.location.latitude > 60) citiesCount++;
            }
            if (citiesCount == country.cities.size()) return country;
        }

        return null;
    }

    public static boolean hasCountriesWithBigCities(GeoObjects geoObjects) {
        for (GeoObjects.Country country : geoObjects.countries) {
            int citiesCount = 0;
            for (GeoObjects.City city : country.cities) {
                int bigDistrictsCount = 0;
                for (GeoObjects.District district : city.districts) {
                    if (district.size == GeoObjects.Size.LARGE) bigDistrictsCount++;
                }
                if (bigDistrictsCount >= 7) citiesCount++;
            }
            if (citiesCount >= 2) return true;
        }
        return false;
    }
}
