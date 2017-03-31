package com.notiflowcate.service.impl;

import com.notiflowcate.exception.ApplicationException;
import com.notiflowcate.model.dto.ApplicationDto;
import com.notiflowcate.model.entity.Application;
import com.notiflowcate.repository.ApplicationRepository;
import com.notiflowcate.service.ApplicationService;
import com.notiflowcate.service.TransformService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @author Dayel Ostraco
 * 12/21/15.
 */
@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final TransformService orikaTransformService;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                  TransformService orikaTransformService) {

        this.applicationRepository = applicationRepository;
        this.orikaTransformService = orikaTransformService;
    }

    /**
     * Persists the passed in {@link ApplicationDto}.
     *
     * @param applicationDto {@link ApplicationDto}
     * @return Persisted {@link ApplicationDto}
     */
    @Override
    public ApplicationDto save(ApplicationDto applicationDto) {

        // Convert Application DTO to Application Entity
        Application application = orikaTransformService.map(applicationDto, Application.class);

        // Generate an API Key
        application.setApiKey(RandomStringUtils.randomAlphanumeric(36).toUpperCase());

        // TODO: Create Application Topic

        // Save new Application Entity
        application = applicationRepository.save(application);

        // Convert persisted Application Entity to a DTO
        return orikaTransformService.map(application, ApplicationDto.class);
    }

    /**
     * Updates the passed in {@link ApplicationDto}.
     *
     * @param applicationDto {@link ApplicationDto}
     * @return Persisted {@link ApplicationDto}
     */
    @Override
    public ApplicationDto update(ApplicationDto applicationDto) throws ApplicationException {

        // Verify that the Application exists
        Application applicationVerification = applicationRepository.findOne(applicationDto.getApplicationId());
        if (applicationVerification == null) {
            throw new ApplicationException("Application does not exist");
        }

        // Convert Application DTO to Application Entity
        Application application = orikaTransformService.map(applicationDto, Application.class);

        // Save new Application Entity
        application = applicationRepository.save(application);

        // Convert persisted Application Entity to a DTO
        return orikaTransformService.map(application, ApplicationDto.class);
    }

    /**
     * Soft deletes an Application.
     *
     * @param applicationId {@link Long}
     * @throws ApplicationException
     */
    @Override
    public void delete(Long applicationId) throws ApplicationException {

        // Verify that the Application exists
        Application applicationVerification =
                applicationRepository.findOne(applicationId);
        if (applicationVerification == null) {
            throw new ApplicationException("Application does not exist or you do not have access.");
        }

        // Soft Delete
        applicationVerification.setDeleted(LocalDateTime.now(ZoneId.of("UTC")));

        // Persist soft deleted Beacon
        applicationRepository.save(applicationVerification);
    }

    /**
     * Retrieves all registered {@link Application} as {@link ApplicationDto}.
     *
     * @return List of all {@link Application} as {@link ApplicationDto}
     */
    @Override
    public List<ApplicationDto> findAll() {

        // Convert retrieved Application Entities to DTOs
        return orikaTransformService.mapList(applicationRepository.findAll(), ApplicationDto.class);
    }

    /**
     * Retrieve {@link Application} by applicationId as an {@link ApplicationDto}.
     *
     * @param applicationId Long
     * @return ApplicationDto
     */
    @Override
    public ApplicationDto findByApplicationId(Long applicationId) {

        // Convert retrieved Application Entity to a DTO
        return orikaTransformService.map(applicationRepository.findOne(applicationId), ApplicationDto.class);
    }

    /**
     * Retrieves an {@link Application} by the passed in apiKey and returns it as an {@link ApplicationDto}.
     *
     * @param apiKey String
     * @return {@link ApplicationDto}
     */
    @Override
    public ApplicationDto findByApiKey(String apiKey) {

        // Find Application Entity by apiKey
        Application application = applicationRepository.findByApiKey(apiKey);

        // Convert Application Entity to Dto
        return orikaTransformService.map(application, ApplicationDto.class);
    }

    /**
     * Retrieves an active {@link Application} by the passed in apiKey and returns it as an {@link ApplicationDto}.
     *
     * @param apiKey String
     * @return {@link ApplicationDto}
     */
    @Override
    public ApplicationDto findActiveApplicationByApiKey(String apiKey) {

        // Find Application Entity by apiKey
        Application application = applicationRepository.findActiveApplicationByApiKey(apiKey);

        // Convert Application Entity to Dto
        return orikaTransformService.map(application, ApplicationDto.class);
    }

    /**
     * Retrieves an active {@link Application} by the passed in name and returns it as an {@link ApplicationDto}.
     *
     * @param name String
     * @return {@link ApplicationDto}
     */
    @Override
    public ApplicationDto findByName(String name) {
        // Find Application Entity by apiKey
        Application application = applicationRepository.findActiveApplicationByName(name);

        // Convert Application Entity to Dto
        return orikaTransformService.map(application, ApplicationDto.class);
    }
}
