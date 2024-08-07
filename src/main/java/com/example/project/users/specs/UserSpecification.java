package com.example.project.users.specs;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.example.project.users.dto.UserFilterDTO;
import com.example.project.users.entity.User;

import jakarta.persistence.criteria.Predicate;

public class UserSpecification {

    public static Specification<User> filterUser(UserFilterDTO filterDTO) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction(); // Start with a true predicate
            
            // Filter for firstname contains
            if (StringUtils.isNotBlank(filterDTO.getFirstnameContains())) {
                predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(root.get("firstname"), likePattern(filterDTO.getFirstnameContains())));
            }
            
            // Filter for firstname starts with
            if (StringUtils.isNotBlank(filterDTO.getFirstnameStartsWith())) {
                predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(root.get("firstname"), startsWithPattern(filterDTO.getFirstnameStartsWith())));
            }
            
            // Filter for lastname contains
            if (StringUtils.isNotBlank(filterDTO.getLastnameContains())) {
                predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(root.get("lastname"), likePattern(filterDTO.getLastnameContains())));
            }
            
            // Filter for lastname starts with
            if (StringUtils.isNotBlank(filterDTO.getLastnameStartsWith())) {
                predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(root.get("lastname"), startsWithPattern(filterDTO.getLastnameStartsWith())));
            }
            
            // Filter for email contains
            if (StringUtils.isNotBlank(filterDTO.getEmailContains())) {
                predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(root.get("email"), likePattern(filterDTO.getEmailContains())));
            }
            
            // Filter for email starts with
            if (StringUtils.isNotBlank(filterDTO.getEmailStartsWith())) {
                predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(root.get("email"), startsWithPattern(filterDTO.getEmailStartsWith())));
            }
            
            // Filter for role
            // if (filterDTO.getRole() != null) {
            //     predicate = criteriaBuilder.and(predicate,
            //         criteriaBuilder.equal(root.get("role"), filterDTO.getRole()));
            // }
            
            // Filter for id less than
            if (filterDTO.getIdLessThan() != null) {
                predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.lessThan(root.get("id"), filterDTO.getIdLessThan()));
            }
            
            // Filter for id greater than
            if (filterDTO.getIdGreaterThan() != null) {
                predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.greaterThan(root.get("id"), filterDTO.getIdGreaterThan()));
            }

            return predicate;
        };
    }

    private static String likePattern(String value) {
        return "%" + value + "%";
    }

    private static String startsWithPattern(String value) {
        return value + "%";
    }
}
