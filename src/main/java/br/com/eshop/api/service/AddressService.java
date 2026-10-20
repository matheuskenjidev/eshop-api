package br.com.eshop.api.service;

import br.com.eshop.api.model.User;
import br.com.eshop.api.payload.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO, User user);

    List<AddressDTO> getAdresses();

    AddressDTO getAddressById(Long addressId);
}
