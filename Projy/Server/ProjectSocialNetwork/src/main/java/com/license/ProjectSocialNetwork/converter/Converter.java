package com.license.ProjectSocialNetwork.converter;

import com.license.ProjectSocialNetwork.controller.dto.BaseDTO;
import com.license.ProjectSocialNetwork.model.BaseEntity;

import java.io.Serializable;

public interface Converter <ID extends Serializable, Model extends BaseEntity<ID>, DTO extends BaseDTO<ID>>{
    Model convertDtoToModel(DTO dto);

    DTO convertModelToDto(Model model);
}
