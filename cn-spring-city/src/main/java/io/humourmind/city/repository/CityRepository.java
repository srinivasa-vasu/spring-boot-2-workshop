package io.humourmind.city.repository;

import io.humourmind.city.domain.City;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "cities", collectionResourceRel = "cities")
public interface CityRepository extends PagingAndSortingRepository<City, Long> {

	@RestResource(path = "name", rel = "name")
	Page<City> findByNameIgnoreCase(@Param("name") String name, Pageable pageable);

	@RestResource(path = "nameContains", rel = "nameContains")
	Page<City> findByNameContainsIgnoreCase(@Param("name") String name,
			Pageable pageable);

	@RestResource(path = "postalCode", rel = "postalCode")
	Page<City> findByPostalCodeIgnoreCase(@Param("postalCode") String postalCode,
			Pageable pageable);

}
