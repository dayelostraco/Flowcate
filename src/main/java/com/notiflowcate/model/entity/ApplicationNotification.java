package com.notiflowcate.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Dayel Ostraco
 * 5/25/16
 */
@Entity
@DiscriminatorValue(value = "APPLICATION")
public class ApplicationNotification extends Notification {

    private static final long serialVersionUID = -2687670806010634661L;
}
