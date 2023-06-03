package com.driver.services.impl;

import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.model.User;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Override
    public User register(String username, String password, String countryName) throws Exception{
        CountryName countryName1 = CountryName.valueOf(countryName);

        List<Country> countryList  =  countryRepository3.findAll();
        Country country = null;

        for (Country country1 : countryList){
            if(country1.getCountryName().equals(countryName1)){
                country = country1;
                break;
            }
        }

        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setCountry(country);

        String code = String.valueOf(country.getCode());
        String id = String.valueOf(user.getId());
        String Ip = "code" + "." +  "id";
        user.setOriginalIp(Ip);
       User user1 = userRepository3.save(user);
        country.setUser(user1);
        return  user1;



    }

    @Override
    public User subscribe(Integer userId, Integer serviceProviderId) {

        User user = userRepository3.findById(userId).get();
        ServiceProvider serviceProvider = serviceProviderRepository3.findById(serviceProviderId).get();

        User user1 = userRepository3.save(user);
        user1.getServiceProviderList().add(serviceProvider);
        return user1;

    }
}
