package io.humourmind.cnspringweather.repository;

import io.humourmind.cnspringweather.domain.Weather;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "weather", collectionResourceRel = "weather")
public interface WeatherRepository extends PagingAndSortingRepository<Weather, Long> {

    @RestResource(path = "name",rel = "name")
    Page<Weather> findByNameIgnoreCase(@Param("name") String name, Pageable pageable);

    @RestResource(path="postalCode", rel = "postalCode")
    Page<Weather> findByPostalCodeIgnoreCase(@Param("postalCode") String postalCode, Pageable pageable);

}
