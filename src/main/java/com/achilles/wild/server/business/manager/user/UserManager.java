package com.achilles.wild.server.business.manager.user;

import com.achilles.wild.server.entity.user.User;

public interface UserManager {

    User getUserByEmail(String email);
}
