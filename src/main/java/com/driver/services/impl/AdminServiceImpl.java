package com.driver.services.impl;

import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;

    @Override
    public Admin register(String username, String password) {
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);

        return adminRepository1.save(admin);

    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) {

        Admin admin = adminRepository1.findById(adminId).get();
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setName(providerName);
        Admin admin1 = adminRepository1.save(admin);
        admin1.getServiceProviders().add(serviceProvider);
        return admin1;


    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception{


        String name = countryName.toLowerCase();
        if(!(name.equals("ind") || name.equals("aus") || name.equals("jpn") || name.equals("usa") || name.equals("chi"))){
            throw new Exception("Country not found");
        }
        CountryName countryName1 = CountryName.valueOf(name);



        ServiceProvider serviceProvider = serviceProviderRepository1.findById(serviceProviderId).get();
        Country country = new Country();
        country.setCountryName(countryName1);
        country.setCode(countryName1.toCode());

        ServiceProvider serviceProvider1 = serviceProviderRepository1.save(serviceProvider);
        serviceProvider1.getCountryList().add(country);

        return serviceProvider1;




    }
}
