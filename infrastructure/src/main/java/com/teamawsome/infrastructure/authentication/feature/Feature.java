package com.teamawsome.infrastructure.authentication.feature;

import edu.emory.mathcs.backport.java.util.Arrays;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public enum Feature {
    GET_ALL_BOOKS(BookstoreRole.ADMIN, BookstoreRole.MEMBER, BookstoreRole.LIBRARIAN);


    private BookstoreRole[] roles;

    Feature(BookstoreRole... roles){
        this.roles = roles;
    }

    public List<BookstoreRole> getRoles(){
        return Arrays.asList(roles);
    }

    public static List<Feature> getFeaturesForRoles(List<String> rolesOfUserAsString){
        List<BookstoreRole> rolesOfUser = rolesOfUserAsString.stream()
                .map(BookstoreRole::valueOf)
                .collect(Collectors.toList());

        List<Feature> listOfAllFeatures = Arrays.asList(Feature.values());
        List<Feature> allowedFeatures = new ArrayList<>();
        for(Feature feature : listOfAllFeatures){
            if(!Collections.disjoint(feature.getRoles(), rolesOfUser)){
                allowedFeatures.add(feature);
            }
        }
        return allowedFeatures;

//        return  Arrays.asList(Feature.values()).stream()
//                .filter(feature -> !Collections.disjoint(rolesOfUser, rolesOfUser))
//                .collect(Collectors.toList());
    }


}
