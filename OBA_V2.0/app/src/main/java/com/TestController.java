package com;

import com.dto.AccountDTO;
import com.dto.RoleDTO;
import com.dto.UserDTO;
import com.service.AccountService;
import com.service.PersonService;
import com.service.RoleService;
import com.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/save-user")
    public UserDTO saveUser(@RequestBody UserDTO userDTO) throws NotFoundException {
        return userService.save(userDTO);
    }

    @GetMapping("/get-user")
    public UserDTO getUser(@RequestParam("id") Long id) throws NotFoundException {
        return userService.get(id);
    }

    @GetMapping("/list-user")
    public List<UserDTO> list() {
        return userService.list();
    }

    @DeleteMapping("/delete-user")
    public void deleteUser(@RequestParam("id") Long id) throws NotFoundException {
        userService.delete(id);
    }

    @GetMapping("/list-role")
    public List<RoleDTO> listRole() {
        return roleService.list();
    }

    @GetMapping("/get-account")
    public AccountDTO getAccount(@RequestParam("id") Long id) throws NotFoundException {
        return accountService.get(id);
    }

    @PostMapping("/save-account")
    public AccountDTO saveAccount(@RequestBody AccountDTO accountDTO) throws NotFoundException {
        return accountService.save(accountDTO);
    }

    @DeleteMapping("/delete-account")
    public void deleteAccount(@RequestParam Long id) throws NotFoundException {
        accountService.delete(id);
    }
}
