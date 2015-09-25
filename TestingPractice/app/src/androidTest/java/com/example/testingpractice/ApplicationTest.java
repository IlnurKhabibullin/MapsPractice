package com.example.testingpractice;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

    private static final List<String> INPUT_FILES = new ArrayList<String>() {
        {
            add("input1.json");
            add("input2.json");
            add("input3.json");
            add("input4.json");
            add("input5.json");
            add("input_empty.json");
            add("input_error.json");
        }
    };

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void firstMethodTest() {
        MainActivity activity = mActivityRule.getActivity();
        String[][] expectedResults = {
                {},
                {},
                {"Belarus", "Great Britain"},
                {"Russia", "Germany", "USA", "Canada", "Great Britain", "Bangladesh"},
                {"Country 15", "Country 17", "Country 41", "Country 71", "Country 72", "Country 80", "Country 93"},
                {},
                {}
        };
        for (int i = 0; i < INPUT_FILES.size(); i++) {
            String inputFile = INPUT_FILES.get(i);
            try {
                List<GeoObjects.Country> countries = CountriesHelper.countriesByCitiesCount(
                        CountriesHelper.deserialize(activity.getJsonFromAssets(inputFile)));
                assertEquals(expectedResults[i].length, countries.size());
                for (int j = 0; j < expectedResults[i].length; j++) {
                    assertEquals(expectedResults[i][j], countries.get(j).name);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                if (i != 5) fail("Unexpected IAE in " + inputFile);
            } catch (Exception e) {
                e.printStackTrace();
                if (i != 6) fail("Unexpected Exception in " + inputFile);
            }
        }
    }

    @Test
    public void secondMethodTest() {
        MainActivity activity = mActivityRule.getActivity();
        String[][] expectedResults = {
                {},
                {"ru City 7", "fr City 9", "de City 3", "de City 12"},
                {"ru City 6", "ru City 8", "it City 5", "fr City 5", "de City 7", "us City 11"
                        , "es City 3", "es City 5", "by City 7", "by City 9", "by City 11"
                        , "by City 12", "by City 19", "by City 23", "by City 30", "gb City 5"
                        , "gb City 10", "gb City 23", "gb City 28", "az City 3"},
                {"ru City 10", "ru City 11", "it City 2", "it City 19", "fr City 1", "fr City 2"
                        , "fr City 5", "fr City 11", "de City 5", "de City 9", "de City 12"
                        , "us City 1", "us City 2", "us City 5", "us City 10", "cn City 5"
                        , "es City 2", "by City 7", "cn City 2", "cn City 29", "gb City 3"
                        , "gb City 21", "gb City 26", "gb City 30", "ar City 2", "az City 7"
                        , "al City 2", "al City 4", "al City 6", "al City 21", "dz City 12"
                        , "am City 18", "af City 16", "bd City 13", "bd City 14", "bd City 21"
                        , "bd City 22", "br City 3", "br City 8", "va City 5", "va City 7"
                        , "hn City 10", "gr City 4"},
                {"10 City 4", "10 City 5", "10 City 9", "13 City 12", "15 City 7", "15 City 9"
                        , "15 City 10", "15 City 17", "15 City 19", "15 City 27", "16 City 1"
                        , "17 City 8", "17 City 9", "20 City 3", "20 City 5", "21 City 5"
                        , "21 City 10", "22 City 3", "22 City 5", "22 City 10", "22 City 15"
                        , "24 City 12", "24 City 22", "25 City 11", "25 City 17", "25 City 24"
                        , "27 City 18", "28 City 8", "28 City 21", "28 City 23", "30 City 2"
                        , "31 City 1", "31 City 19", "32 City 13", "32 City 14", "32 City 15"
                        , "32 City 17", "35 City 3", "35 City 4", "35 City 15", "36 City 6"
                        , "37 City 1", "37 City 2", "37 City 3", "38 City 2", "38 City 4"
                        , "38 City 5", "38 City 8", "39 City 21", "40 City 3", "41 City 2"
                        , "42 City 8", "42 City 11", "44 City 6", "44 City 23", "45 City 7"
                        , "46 City 2", "46 City 11", "46 City 19", "47 City 5", "47 City 9"
                        , "47 City 10", "51 City 7", "53 City 2", "53 City 3", "53 City 9"
                        , "54 City 3", "55 City 10", "55 City 16", "56 City 1", "56 City 10"
                        , "56 City 11", "56 City 13", "57 City 1", "57 City 4", "58 City 4"
                        , "58 City 12", "58 City 16", "59 City 14", "59 City 15", "60 City 16"
                        , "61 City 6", "62 City 10", "63 City 9", "64 City 3", "64 City 11"
                        , "65 City 4", "65 City 7", "65 City 9", "65 City 19", "65 City 20"
                        , "66 City 8", "66 City 9", "67 City 1", "67 City 11", "67 City 12"
                        , "67 City 13", "70 City 2", "70 City 9", "70 City 11", "70 City 18"
                        , "71 City 22", "71 City 25", "72 City 23", "75 City 4", "75 City 7"
                        , "77 City 7", "78 City 2", "79 City 1", "79 City 4", "79 City 14"
                        , "80 City 11", "80 City 17", "81 City 1", "81 City 2", "81 City 3"
                        , "81 City 7", "81 City 12", "82 City 10", "82 City 11", "83 City 5"
                        , "83 City 12", "83 City 13", "84 City 17", "85 City 3", "86 City 2"
                        , "86 City 6", "87 City 3", "87 City 9", "89 City 7", "91 City 7"
                        , "93 City 8", "93 City 11", "93 City 17", "93 City 25", "95 City 6"
                        , "96 City 25", "98 City 12", "98 City 13", "99 City 10", "99 City 14"},
                {},
                {}
        };

        for (int i = 0; i < INPUT_FILES.size(); i++) {
            String inputFile = INPUT_FILES.get(i);
            try {
                List<GeoObjects.City> cities = CountriesHelper.citiesByPopulation(
                        CountriesHelper.deserialize(activity.getJsonFromAssets(inputFile)));
                assertEquals(expectedResults[i].length, cities.size());
                for (int j = 0; j < expectedResults[i].length; j++) {
                    assertEquals(expectedResults[i][j], cities.get(j).name);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                if (i != 5) fail("Unexpected IAE in " + inputFile);
            } catch (Exception e) {
                e.printStackTrace();
                if (i != 6) fail("Unexpected Exception in " + inputFile);
            }
        }
    }

    @Test
    public void thirdMethodTest() {
        MainActivity activity = mActivityRule.getActivity();
        String[] expectedResults = {
                "Russia", "Germany", "Belarus", "USA", "Country 15", "", ""
        };
        for (int i = 0; i < INPUT_FILES.size(); i++) {
            String inputFile = INPUT_FILES.get(i);
            try {
                GeoObjects.Country country = CountriesHelper.cityWithBiggestPopulation(
                        CountriesHelper.deserialize(activity.getJsonFromAssets(inputFile)));
                assertEquals(expectedResults[i], country.name);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                if (i != 5) fail("Unexpected IAE in " + inputFile);
            } catch (Exception e) {
                e.printStackTrace();
                if (i != 6) fail("Unexpected Exception in " + inputFile);
            }
        }
    }

    @Test
    public void fourthMethodTest() {
        MainActivity activity = mActivityRule.getActivity();
        String[] expectedResults = {
                "Sample country 1", "Germany", "France", "Italy", "Country 72", "", ""
        };
        for (int i = 0; i < INPUT_FILES.size(); i++) {
            String inputFile = INPUT_FILES.get(i);
            try {
                GeoObjects.Country country = CountriesHelper.countryWithBiggestInfrastructire(
                        CountriesHelper.deserialize(activity.getJsonFromAssets(inputFile)));
                assertEquals(expectedResults[i], country.name);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                if (i != 5) fail("Unexpected IAE in " + inputFile);
            } catch (Exception e) {
                e.printStackTrace();
                if (i != 6) fail("Unexpected Exception in " + inputFile);
            }
        }
    }

    @Test
    public void fifthMethodTest() {
        MainActivity activity = mActivityRule.getActivity();

        String[] expectedResults = {
                null, null, null, null, "Country 43", "", ""
        };
        for (int i = 0; i < INPUT_FILES.size(); i++) {
            String inputFile = INPUT_FILES.get(i);
            try {
                GeoObjects.Country country = CountriesHelper.countriesByLatitude(
                        CountriesHelper.deserialize(activity.getJsonFromAssets(inputFile)));
                assertEquals(expectedResults[i] == null ? null :
                                new GeoObjects.Country(expectedResults[i]), country);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                if (i != 5) fail("Unexpected IAE in " + inputFile);
            } catch (Exception e) {
                e.printStackTrace();
                if (i != 6) fail("Unexpected Exception in " + inputFile);
            }
        }
    }

    @Test
    public void sixthMethodTest() {
        MainActivity activity = mActivityRule.getActivity();

        boolean[] expectedResults = {
                false, true, true, true, true, false, false
        };
        for (int i = 0; i < INPUT_FILES.size(); i++) {
            String inputFile = INPUT_FILES.get(i);
            try {
                boolean haveCountry = CountriesHelper.hasCountriesWithBigCities(
                        CountriesHelper.deserialize(activity.getJsonFromAssets(inputFile)));
                assertEquals(expectedResults[i], haveCountry);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                if (i != 5) fail("Unexpected IAE in " + inputFile);
            } catch (Exception e) {
                e.printStackTrace();
                if (i != 6) fail("Unexpected Exception in " + inputFile);
            }
        }
    }
}