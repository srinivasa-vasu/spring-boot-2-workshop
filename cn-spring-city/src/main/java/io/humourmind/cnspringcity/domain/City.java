package io.humourmind.cnspringcity.domain;

import java.io.Serializable;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "city")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class City implements Serializable {

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
	@Column
	private String latitude;
	@Column
	private String longitude;

}
