package io.humourmind.cnspringweather.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "city_weather")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Weather implements Serializable {

	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String country;
	@Column(nullable = false)
	private String stateCode;
	@Column(nullable = false)
	private String postalCode;
	@Column(nullable = false)
	private String weather;

}
