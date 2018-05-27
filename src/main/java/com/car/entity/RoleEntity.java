package com.car.entity;

import java.util.List;




import com.car.dto.RoleDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {


		private int draw;
		private int recordsTotal;
		private int recordsFiltered;
		private List<RoleDto> roles;
}
