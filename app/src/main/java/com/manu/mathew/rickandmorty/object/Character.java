package com.manu.mathew.rickandmorty.object;

import java.util.Date;

public class Character {

    public int id;
    public String name;
    public String status;
    public String species;
    public String type;
    public String gender;
    //        public Origin origin;
//        public Location location;
    public String image;
    //public List<String> episode;
    public String url;
    public String created;

    public Character(int id, String name, String status, String species, String type, String gender, String image, String url, String created) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.image = image;
        this.url = url;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }

    public String getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public String getCreated() {
        return created;
    }
}
