package org.fasttrackit.hotel.management.config;

import lombok.RequiredArgsConstructor;
import org.fasttrackit.hotel.management.loader.DataLoader;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "hotel.info")
@ConstructorBinding
public class AppConfiguration {
    private final String hotelName;
    private final Integer noOfFloors;

    public AppConfiguration(String name, Integer floors){
        this.hotelName = name;
        this.noOfFloors = floors;
    }

    public String getHotelName(){
        return hotelName;
    }

    public Integer getNoOfFloors(){
        return noOfFloors;
    }
}
