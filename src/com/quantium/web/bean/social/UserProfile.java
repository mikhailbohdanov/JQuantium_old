package com.quantium.web.bean.social;

import com.quantium.web.bean.core.Route;
import com.quantium.web.bean.location.City;
import com.quantium.web.bean.location.Country;
import com.quantium.web.bean.view.clientObjects.Image;
import com.quantium.web.dao.annotation.Index;
import com.quantium.web.dao.annotation.Row;
import com.quantium.web.dao.annotation.Table;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by FREEMAN on 30.09.14.
 */
@Table("social_profiles")
public class UserProfile {
    public enum Sex {
        NONE,
        MALE,
        FEMALE
    }
    public enum Relation {
        NONE,
        NOT_MARRIED,
        DATING,
        ENGAGED,
        MARRIED,
        IN_LOVE,
        DIFFICULT,
        ACTIVE_SEARCH
    }
    public enum Owner {
        MY,
        NOT_FRIEND,
        MY_REQUEST_FRIEND,
        ME_REQUEST_FRIEND,
        FRIEND,
        IN_MY_BLACKLIST,
        I_IN_BLACKLIST
    }

    @Index
    @Row(name = "userId", unsigned = true, notNull = true)
    private int userId;

    @Row(name = "firstName", length = 127)
    private String firstName;

    @Row(name = "lastName", length = 127)
    private String lastName;

    @Row(name = "sex", defaultValue = "NONE")
    private Sex sex;

    @Row(name = "relation", defaultValue = "NONE")
    private Relation relation;

    @Row(name = "relationUserId", unsigned = true, defaultValue = "0")
    private int relationUserId;

    @Row(name = "BDate")
    private Date BDate;

    @Row(name = "BDateVisibility", length = 1)
    private int BDateVisibility;

    @Row(name = "countryId", unsigned = true, defaultValue = "0")
    private int countryId;

    @Row(name = "cityId", unsigned = true, defaultValue = "0")
    private int cityId;

    @Row(name = "photoId", unsigned = true, defaultValue = "0")
    private int photoId;

    private UserProfile relationUser;
    private Country country;
    private City city;
    private Image photo;

    private Route route;

    private Owner owner;

    public int getUserId() {
        return userId;
    }
    public UserProfile setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }
    public UserProfile setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }
    public UserProfile setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Sex getSex() {
        return sex;
    }
    public UserProfile setSex(Sex sex) {
        this.sex = sex;
        return this;
    }
    public UserProfile setSex(String sex) {
        this.sex = Sex.valueOf(sex);
        return this;
    }

    public Relation getRelation() {
        return relation;
    }
    public UserProfile setRelation(Relation relation) {
        this.relation = relation;
        return this;
    }
    public UserProfile setRelation(String relation) {
        this.relation = Relation.valueOf(relation);
        return this;
    }

    public int getRelationUserId() {
        return relationUserId;
    }
    public UserProfile setRelationUserId(int relationUserId) {
        this.relationUserId = relationUserId;
        return this;
    }

    public Date getBDate() {
        return BDate;
    }
    public UserProfile setBDate(Date BDate) {
        this.BDate = BDate;
        return this;
    }

    public int getBDateVisibility() {
        return BDateVisibility;
    }
    public UserProfile setBDateVisibility(int BDateVisibility) {
        this.BDateVisibility = BDateVisibility;
        return this;
    }

    public int getCountryId() {
        return countryId;
    }
    public UserProfile setCountryId(int countryId) {
        this.countryId = countryId;
        return this;
    }

    public int getCityId() {
        return cityId;
    }
    public UserProfile setCityId(int cityId) {
        this.cityId = cityId;
        return this;
    }

    public int getPhotoId() {
        return photoId;
    }
    public UserProfile setPhotoId(int photoId) {
        this.photoId = photoId;
        return this;
    }

    public UserProfile getRelationUser() {
        return relationUser;
    }
    public UserProfile setRelationUser(UserProfile relationUser) {
        this.relationUser = relationUser;
        return this;
    }

    public Country getCountry() {
        return country;
    }
    public UserProfile setCountry(Country country) {
        this.country = country;
        return this;
    }

    public City getCity() {
        return city;
    }
    public UserProfile setCity(City city) {
        this.city = city;
        return this;
    }

    public Image getPhoto() {
        return photo;
    }
    public UserProfile setPhoto(Image photo) {
        this.photo = photo;
        return this;
    }

    public Route getRoute() {
        return route;
    }
    public UserProfile setRoute(Route route) {
        this.route = route;
        return this;
    }

    public String getUrl() {
        if (route != null)
            return route.getUrl();

        return "id" + userId;
    }

    public Owner getOwner() {
        return owner;
    }
    public UserProfile setOwner(Owner owner) {
        this.owner = owner;
        return this;
    }
}
