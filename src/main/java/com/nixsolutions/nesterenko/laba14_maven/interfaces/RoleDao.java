package com.nixsolutions.nesterenko.laba14_maven.interfaces;

import com.nixsolutions.nesterenko.laba16_hibernate.entity.Role;


public interface RoleDao {

    public void create(Role role) throws Exception;
    public void update(Role role) throws Exception;
    public void remove(Role role) throws Exception;
    public Role findByName(String name) throws Exception;
    
}
