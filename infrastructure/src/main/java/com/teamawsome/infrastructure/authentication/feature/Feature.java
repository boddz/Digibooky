package com.teamawsome.infrastructure.authentication.feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public enum Feature {
    GET_ALL_BOOKS(BookstoreRole.ADMIN),
    MAKE_ADMIN(BookstoreRole.ADMIN),
    MAKE_LIBRARIAN(BookstoreRole.LIBRARIAN);



    private BookstoreRole[] roles;

    Feature(BookstoreRole... roles){
        this.roles = roles;
    }

    public List<BookstoreRole> getRoles(){
        return List.of(roles);
    }

    public static List<Feature> getFeaturesForRoles(List<BookstoreRole> rolesOfUser){

        List<Feature> listOfAllFeatures = List.of(Feature.values());
        List<Feature> allowedFeatures = new ArrayList<>();
        for(Feature feature : listOfAllFeatures){
            if(!Collections.disjoint(feature.getRoles(), rolesOfUser)){
                allowedFeatures.add(feature);
            }
        }
        return allowedFeatures;
    }


}
