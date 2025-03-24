package net.axel.sharehope.service.impl;

import lombok.RequiredArgsConstructor;
import net.axel.sharehope.domain.entities.Category;
import net.axel.sharehope.security.domain.entity.AppUser;
import net.axel.sharehope.service.AttachmentService;
import net.axel.sharehope.service.CategoryService;
import net.axel.sharehope.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional

@RequiredArgsConstructor
public class ActionServiceDefault {

    private final CategoryService categoryService;
    private final UserService userService;
    public final AttachmentService attachmentService;

    public Category getCategory(Long id) {
        return categoryService.findEntityById(id);
    }

    public AppUser getUser(String username) {
        return userService.findUserEntity(username);
    }
}
