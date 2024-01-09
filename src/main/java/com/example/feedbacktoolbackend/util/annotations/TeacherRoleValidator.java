package com.example.feedbacktoolbackend.util.annotations;

import com.example.feedbacktoolbackend.enums.Role;

public class TeacherRoleValidator extends BaseRoleValidator<AuthorizedTeacher> {
    @Override
    public void initializeRole() {
        super.expectedRole = Role.TEACHER;
    }
}
