package com.quantium.web.service;

import com.quantium.web.bean.location.City;
import com.quantium.web.bean.location.Country;
import com.quantium.web.bean.location.Region;
import com.quantium.web.dao.LocationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by FREEMAN on 14.10.14.
 */
@Service
public class LocationService {

    @Autowired
    private LocationDAO locationDAO;

    public Country getCountry(int countryId) {
        return null;
    }
    public Region getRegion(int regionId) {
        return null;
    }
    public City getCity(int cityId) {
        return null;
    }

}
