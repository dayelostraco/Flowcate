package com.notiflowcate.service;

import java.util.List;
import java.util.Set;

/**
 * @author Dayel Ostraco
 * 10/2/15
 */
public interface TransformService {

    <F, T> T map(F mapFromObject, Class mapToClass);

    <T> T mapList(List<?> mapFromList, Class mapToClass);

    <T> T mapSet(Set<?> mapFromSet, Class mapToClass);
}
