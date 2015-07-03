package com.quantium.web.controller.social;

import com.quantium.web.bean.Url;
import com.quantium.web.bean.security.UserSecurity;
import com.quantium.web.bean.social.UserProfile;
import com.quantium.web.bean.view.ClientPage;
import com.quantium.web.bean.view.Page;
import com.quantium.web.bean.view.PageContext;
import com.quantium.web.controller.Pool;
import com.quantium.web.errors.controllers.ProfileErrors;
import com.quantium.web.service.SocialService;
import com.quantium.web.util.UserHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by FREEMAN on 27.09.14.
 */
@Controller
public class Profiles extends Pool {

    @RequestMapping(value = "/id{userId:\\d{1,10}}")
    public String getProfile(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable int userId) {
        PageContext PC = PageContext.newInstance(request, response, model);

        UserProfile profile = getProfile(PC, userId);
        PC.initPage(profile.getFullName(), null, null);

        return PC.setView("profile", "view").render();
    }

    public UserProfile getProfile(PageContext PC, int userId) {
        UserProfile user = SocialService.profileService.getProfile(userId, true);

        if (user == null)
            PC.addError(ProfileErrors._NAME, ProfileErrors.PROFILE_NOT_FOUND);
        else {
            boolean authorized = UserHelper.isAuthorized();

            if (authorized) {
                if (UserHelper.getUserId() == user.getUserId())
                    user.setOwner(UserProfile.Owner.MY);
                else
                    user.setOwner(SocialService.userListService.userToMe(userId));
            } else
                user.setOwner(UserProfile.Owner.NOT_FRIEND);



        }

        return user;
    }

}
