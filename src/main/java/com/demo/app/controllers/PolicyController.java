package com.demo.app.controllers;

import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.app.dtos.PolicyRequestDto;

@RestController
@RequestMapping(path = "policies", produces = "application/json")
public class PolicyController {
 
  @Autowired
  private Enforcer enforcer;

  @PostMapping
  @Transactional
  public ResponseEntity<?> addPolicy(@RequestBody PolicyRequestDto policyRequest) {
    System.err.println(policyRequest);
    boolean success = enforcer.addPolicy(policyRequest.getSubject(), policyRequest.getObject(), policyRequest.getAction());
    
    if (success) {
      enforcer.savePolicy();
    }
    
    return new ResponseEntity<>(success, HttpStatus.OK);
    
  }
  
}
