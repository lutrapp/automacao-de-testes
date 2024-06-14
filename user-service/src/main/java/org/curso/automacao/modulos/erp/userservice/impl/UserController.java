package org.curso.automacao.modulos.erp.userservice.impl;

import java.util.List;
import java.util.Map;

import org.curso.automacao.modulos.erp.userservice.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController<UserService, JpaRepository<User,Long>, User>{

	@Autowired
	UserService userService;
	
	@Override
	@GetMapping("/all")
	public final ResponseEntity<List<User>> findAll() {
		return super.findAll();
	}
	
	@GetMapping("/find-by/user-name/")
	public ResponseEntity<User> findByUserName(@RequestParam(name="username") String userName){
		return new ResponseEntity<User>(userService.findByUserName(userName), HttpStatus.OK);
	}
	
	@Override
	@GetMapping("/find-by/id/{id}")
	public ResponseEntity<User> findById(@PathVariable("id") long id){
		return super.findById(id);
	}

	@Override
	@PutMapping("/save")
	public ResponseEntity<User> save(@RequestBody @Validated User entity) {
		return super.save(entity);
	}

	@Override
	@PostMapping("/update")
	public ResponseEntity<User> update(@RequestBody @Validated User entity) {
		return super.update(entity);
	}
	
	@Override
	@DeleteMapping("/delete-by/id/{id}")
	public ResponseEntity<User> deleteById(@PathVariable(name = "id") long id) {
		return super.deleteById(id);		
	}
	
}
