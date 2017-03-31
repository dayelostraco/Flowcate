package com.notiflowcate.service;

import com.notiflowcate.exception.ApplicationException;
import com.notiflowcate.model.dto.ApplicationDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Will Goss
 * 12/21/15
 */
@Component(value = "applicationService")
public interface ApplicationService {

    ApplicationDto save(ApplicationDto applicationDto);

    ApplicationDto update(ApplicationDto applicationDto) throws ApplicationException;

    void delete(Long applicationId) throws ApplicationException;

    List<ApplicationDto> findAll();

    ApplicationDto findByApplicationId(Long applicationId);

    ApplicationDto findByApiKey(String apiKey);

    ApplicationDto findByName(String name);

    ApplicationDto findActiveApplicationByApiKey(String apiKey);
}
