package com.demo.app.dtos;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PolicyRequestDto {
  
  @NotBlank
  private String subject;
  
  @NotBlank
  private String object;
  
  @NotBlank
  private String action;

}
